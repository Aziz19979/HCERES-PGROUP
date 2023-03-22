#!/bin/bash
#################################################################################
#
# CSV data generator
# This script will generate csv comma separated data having following fields:
# data Label, html type, variableName, initialVariableNameValue, jsonPath
#
# Usage: 1. open bash terminal and navigate to project root location
#        2. Execute this script given relative path of ModelEntity as argument
#        (exemple hceres/src/main/java/org/centrale/hceres/items/Patent.java).
# Example:
# ./doc/code-generator/CreateActivityBackEnd.sh hceres/src/main/java/org/centrale/hceres/items/Patent.java
#################################################################################

# project dependent initialization
JAVA_PROJECT_RELATIVE_PATH="hceres"

# initialize subfolder locations from content root
PROJECT_FOLDER=$(pwd)
JAVA_PROJECT_FOLDER="$JAVA_PROJECT_RELATIVE_PATH"
SCRIPT_FOLDER_LOCATION=$(dirname "$0")
GENERATED_CODE_FOLDER="$PROJECT_FOLDER/GeneratedCode"

if [ $# == 0 ]; then
  echo >&2 "No ModelEntity path was provided"
  echo >&2 "Usage example (relative from project location):"
  echo >&2 "$0 ./hceres/src/main/java/org/centrale/hceres/items/ModelEntity.java"
  exit 1
fi

ModelEntityJavaFile=$1
ModelEntity=$(basename -s ".java" "$1")
modelEntity="$(tr '[:upper:]' '[:lower:]' <<<"${ModelEntity:0:1}")${ModelEntity:1}"

check_if_folder_exist() {
  folderArg=$1
  # Check if component folder exist
  if [ -d "$folderArg" ]; then
    echo "$folderArg exists."
  else
    # print to standard error
    echo >&2 "$folderArg doesn't exist"
    echo >&2 "Execute this file from root project directory as working directory"
    exit 1
  fi
}

check_if_file_exist() {
  file=$1
  if [ -f "$file" ]; then
    echo "$file exist!"
  else
    # print to standard error
    echo >&2 "$file doesn't exist"
    exit 1
  fi
}

# check files and folder existence
check_if_folder_exist "$JAVA_PROJECT_FOLDER"
check_if_file_exist "$ModelEntityJavaFile"

# Generated csv data
csvDataFile="$GENERATED_CODE_FOLDER/$ModelEntity""Form.csv"

mkdir "$GENERATED_CODE_FOLDER"
rm "$csvDataFile"
echo "data Label, html type, variableName, initialVariableNameValue, jsonPath" >>"$csvDataFile"

# Generated save method for service
ModelJavaService="$GENERATED_CODE_FOLDER/$JAVA_PROJECT_RELATIVE_PATH/$ModelEntity""Service.java"
mkdir "$GENERATED_CODE_FOLDER/$JAVA_PROJECT_RELATIVE_PATH"
rm "$ModelJavaService"
echo "public Activity save$ModelEntity(@RequestBody Map<String, Object> request) throws ParseException {" >>"$ModelJavaService"


declare -A ignored_annotation_fields=(
                                      ["@Id"]=1
                                      ["@JsonIgnore"]=1
                                    )


declare -A ignored_modifier_fields=(
                                    ["final"]=1
                                    ["static"]=1
                                  )

declare -A java_to_html_type=(
                                ["String"]="string"
                                ["int"]="number"
                                ["long"]="number"
                                ["float"]="number"
                                ["double"]="number"
                                ["Integer"]="number"
                                ["Float"]="number"
                                ["Double"]="number"
                                ["BigDecimal"]="number"
                                ["Date"]="date"
                                ["boolean"]="bool"
                                ["Boolean"]="bool"
                            )

declare -A react_default_value=(
                                ["string"]="\"\""
                                ["number"]="0"
                                ["date"]="\"\""
                                ["bool"]="false"
                            )

# map from model to concatenated parsed model path
declare -A parentToChildModels

append_csv_data(){
  local modelFileParam=$1
  local prefixJsonParam=$2
  ((files_count++))
  echo "Reading file $files_count : $modelFileParam"

  local ModelEntityParam=$(basename -s ".java" "$modelFileParam")
  local modelEntityParam="$(tr '[:upper:]' '[:lower:]' <<<"${ModelEntityParam:0:1}")${ModelEntityParam:1}"
  echo "        $ModelEntityParam $modelEntityParam = new $ModelEntityParam();" >> "$ModelJavaService"

  local count=0
  ignore_next_field=false
  while read -r line; do
    ((count++))

    # trim white space
    line=$(echo "$line" | xargs -0)

    # skip empty lines
    if [ "$line" == "" ]; then
        continue
    fi

    # check if skip annotation exist
    if [[ -n "${ignored_annotation_fields[$line]}" ]]; then
        echo "reading line no. $count"
        echo "$line"
        ignore_next_field=true
        ignore_reason=$line
        echo "$ignore_next_field"
        echo "$line"
    fi

    IFS=' =;' read -r visibility javaDataTypeGL variableNameGl <<< "$line"
    local javaDataType=$javaDataTypeGL
    local variableName=$variableNameGl
    local VariableName="$(tr '[:lower:]' '[:upper:]' <<<"${variableName:0:1}")${variableName:1}"

    # check if visibility is private
    if [[ "$visibility" == "private" ]]; then
      echo "reading line no. $count"
      echo "$line"
      # ignore field if last marked as ignored
      if [ $ignore_next_field == true ]; then
        echo "Line ignored cuz of annotation $ignore_reason"
        ignore_next_field=false
        ignore_reason=""
        continue
      fi

      # ignore field if modifier isn't supported
      # note javaDataType here contain modifier instead of real data type
      if [[ -n "${ignored_modifier_fields[$javaDataType]}" ]]; then
        echo "Line ignored cuz of modifier $javaDataType"
        continue
      fi

      ((fields_count++))
      echo "Field no. $fields_count => java type is $javaDataType and name is $variableName"

      # check if mapping of java type exist (i.e. native data type)
      htmlDataType=${java_to_html_type["$javaDataType"]}
      if [[ -n "$htmlDataType" ]]; then
        echo "Variable mapping to html exist: $htmlDataType"
        ParserType="$(tr '[:lower:]' '[:upper:]' <<<"${javaDataType:0:1}")${javaDataType:1}"
        echo "$variableName, $htmlDataType, $variableName, ${react_default_value["$htmlDataType"]}, $prefixJsonParam.$variableName" >> "$csvDataFile"
        echo "        $modelEntityParam.set$VariableName(RequestParser.getAs$ParserType(request.get(\"$variableName\")));" >> "$ModelJavaService"

      else
        echo "Searching project for User Defined Type..."
        innerModelFile=$(find "$JAVA_PROJECT_FOLDER" -mtime -7 -name "$javaDataType.java" -print0)
        if [ -n "$innerModelFile" ]; then
          # ensure parsing model does not lead to infinite parse loop
          # i.e. prevent that A parse B
          # and B parse A
          # i.e. prevent that (B child A) and (A child B)
          innerChildrenModels="${parentToChildModels[$innerModelFile]}"
          if [[ $innerChildrenModels == *"$modelFileParam"* ]]; then
            echo >&2 "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
            echo >&2 "infinite parse loop detected at :"
            echo >&2 "$modelFileParam: line $count."
            echo >&2 "Possible fix: add @JsonIgnore annotation to either field in:"
            echo >&2 "Parent class: $modelFileParam"
            echo >&2 "Child class: $innerModelFile"
            echo >&2 "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
            exit 1
          fi
          # add inner model to children of current model
          currentChildrenModels="${parentToChildModels[$modelFileParam]} --- $innerModelFile : line $count"
          parentToChildModels+=(["$modelFileParam"]="$currentChildrenModels")
          echo "${parentToChildModels[@]}"
          echo "Current children of $modelFileParam are $currentChildrenModels"
          echo "Current children of inner model $innerModelFile are $innerChildrenModels"
          echo "Inner Model file found at: $innerModelFile"
          append_csv_data "$innerModelFile" "$prefixJsonParam.$variableName"
          local lowerJavaDataType="$(tr '[:upper:]' '[:lower:]' <<<"${javaDataType:0:1}")${javaDataType:1}"
          echo "        $modelEntityParam.set$VariableName($lowerJavaDataType);" >> "$ModelJavaService"
        else
          echo >&2 "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
          echo >&2 "Variable mapping to html doesn't exist: $javaDataType"
          echo >&2 "$modelFileParam: line $count."
          echo >&2 "Fix mapping then lunch script again"
          echo >&2 "Possible fix: add mapping in java_to_html_type script array"
          echo >&2 "Possible fix: add @JsonIgnore annotation to java field"
          echo >&2 "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
          continue
        fi
      fi
    fi
  done <"$modelFileParam"
}


append_csv_data "$ModelEntityJavaFile" "$modelEntity"

echo "}" >>"$ModelJavaService"

echo "Service java template created at $ModelJavaService"
echo "CSV data created at $csvDataFile"
echo "Change first column label to have appropriate french name"
echo "After that save your csv somewhere and lunch script as follow:"
echo "$SCRIPT_FOLDER_LOCATION/CreateActivityFrontEnd.sh $ModelEntity $csvDataFile"
