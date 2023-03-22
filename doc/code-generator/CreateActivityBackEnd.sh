#!/bin/bash
#################################################################################
#
# ModelEntity java repo/controller generator
# This script will generate models based on a template model existing in the
# project.
#
# Usage: 1. change project dependent variable below for once (already done)
#        2. open bash terminal and navigate to project root location
#        3. Execute this script given relative path of ModelEntity as argument
#        (exemple hceres/src/main/java/org/centrale/hceres/items/Patent.java).
# Example:
# ./doc/code-generator/CreateActivityBackEnd.sh hceres/src/main/java/org/centrale/hceres/items/Patent.java
#################################################################################

# project dependent initialization
JAVA_PROJECT_RELATIVE_PATH="hceres"
TEMPLATE_ENTITY_RELATIVE_PATH="hceres/src/main/java/org/centrale/hceres/items/SrAward.java"

# initialize subfolder locations from content root
PROJECT_FOLDER=$(pwd)
SCRIPT_FOLDER_LOCATION=$(dirname "$0")
GENERATED_CODE_FOLDER="$PROJECT_FOLDER/GeneratedCode"
JAVA_PROJECT_FOLDER="$JAVA_PROJECT_RELATIVE_PATH"
TEMPLATE_ENTITY_FILE="$TEMPLATE_ENTITY_RELATIVE_PATH"
TemplateEntity=$(basename -s ".java" "$TEMPLATE_ENTITY_FILE")

if [ $# == 0 ]; then
  echo >&2 "No ModelEntity path was provided"
  echo >&2 "Usage example (relative from project location):"
  echo >&2 "$0 ./hceres/src/main/java/org/centrale/hceres/items/ModelEntity.java"
  exit 1
fi

ModelEntityJavaFile=$1
ModelEntity=$(basename -s ".java" "$1")

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
check_if_file_exist "$TEMPLATE_ENTITY_FILE"
check_if_file_exist "$ModelEntityJavaFile"

# initialize targetLocation
targetLocation="$GENERATED_CODE_FOLDER/$JAVA_PROJECT_RELATIVE_PATH/"

# clear Generated code folder
rm -r "$targetLocation" 2> /dev/null
echo "Code will be generated in: $targetLocation"

# Create target locations
mkdir -p "$targetLocation"

echo "$targetLocation is created!"

# Traverse all files in java project recursively and perform following steps:
# For each file pattern *TemplateEntity*:
# 1. copy file TemplateEntity to $GENERATED_CODE_FOLDER with same parent structure
# 2. rename file TemplateEntity to ModelEntity
# 3. replace all occurrences of TemplateEntity to ModelEntity
# 4. replace all occurrences of templateEntity to modelEntity
if [ -z ${allCreatedFixedFiles+x} ]; then declare -A allCreatedFixedFiles; fi

while IFS= read -r -d '' templateFile; do
  ((count++))
  echo "Found file no. $count"
  echo "$templateFile"

  # 1. copy file TemplateEntity to ModelEntity
  cp --parent "$templateFile" "$GENERATED_CODE_FOLDER"
  templateFile="$GENERATED_CODE_FOLDER/$templateFile"


  # 2. rename file TemplateEntity to ModelEntity
  # ${variable//search/replace}
  modelFile=${templateFile//"$TemplateEntity"/"$ModelEntity"}
  mv "$templateFile" "$modelFile"
  echo "Renamed file to $modelFile"

  # 3. replace all occurrences of TemplateEntity to ModelEntity
  sed -i "s/$TemplateEntity/$ModelEntity/g" "$modelFile"
  echo "Replaced $TemplateEntity with $ModelEntity"

  # 4. replace all occurrences of templateEntity to modelEntity
  templateEntity="$(tr '[:upper:]' '[:lower:]' <<<"${TemplateEntity:0:1}")${TemplateEntity:1}"
  modelEntity="$(tr '[:upper:]' '[:lower:]' <<<"${ModelEntity:0:1}")${ModelEntity:1}"
  sed -i "s/$templateEntity/$modelEntity/g" "$modelFile"
  echo "Replaced $templateEntity with $modelEntity "

  # 5. replace all plurals name having y at the end with ies
  lastCharacterIndex=${#ModelEntity}-1
  if [ "${ModelEntity:$lastCharacterIndex}" = "y" ]; then
    sed -i "s/${ModelEntity:1}s/${ModelEntity:1:$lastCharacterIndex-1}ies/g" "$modelFile"
    echo "Replaced ${ModelEntity:1}s with ${ModelEntity:1:$lastCharacterIndex-1}ies "
  fi

  allCreatedFixedFiles+=(["$count"]="$modelFile")

done < <(find "$JAVA_PROJECT_FOLDER" -name "*$TemplateEntity*java" -print0)

rm "$GENERATED_CODE_FOLDER/$ModelEntityJavaFile"
cp --parent "$ModelEntityJavaFile" "$GENERATED_CODE_FOLDER"

source "$SCRIPT_FOLDER_LOCATION/GenerateCSVData.sh" "$ModelEntityJavaFile"

echo "${#allCreatedFixedFiles[@]} files are created in $GENERATED_CODE_FOLDER"
for i in ${allCreatedFixedFiles[*]}; do echo "$i"; done
