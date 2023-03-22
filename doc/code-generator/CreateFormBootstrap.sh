#!/bin/bash
###############################################################################################
#
# Form react generator
# This script will generate form based on csv comma separated data having following
# fields:
#
# data Label, html type, variableName, initialVariableNameValue, jsonPath
#
# Predefined input form template are present in "formTemplate" folder next to
# the script
#
# CSV description:
# 1. data label is plain text data used in label form (replace templateLabel in typeInput.js)
# 2. type can be one of the following: "text", "number", "boolean", "date"... as many
# defined in formTemplate folder next to script as typeInput.js
# 3. variable name is valid variable name used for request data and react variable state
# (replace reactVariableState in typeInput.js)
# 4. initialVariableNameValue is the default value used when declaring the const state variable
# usually use \"\" for text, 0 for number, false for boolean, null for date
# Don't forget backslash to escape \" in csv
#
# 5. if json path was provided additional 2 files are returned correctly: ListGroupElement,
# and columns used in react-bootstrap-table
#
# Usage: Execute this script with argument the csv file.
# Note: first header csv row is discarded
##################################################################################################

# initialize subfolder locations from content root
SCRIPT_FOLDER_LOCATION=$(dirname "$0")
PROJECT_LOCATION=$(pwd)
GENERATED_CODE_FOLDER="$PROJECT_LOCATION/GeneratedCode"
ModelEntity="ModelEntity"

if [ $# == 0 ]; then
  echo >&2 "No csv argument was provided"
  echo >&2 "Usage $0 form_data.csv"
  exit 1
elif [ $# == 2 ]; then
  ModelEntity=$2
fi

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

CSV_DATA="$1"
check_if_file_exist "$CSV_DATA"

# clear Generated code folder
mkdir "$GENERATED_CODE_FOLDER"
echo "Code will be generated in: $GENERATED_CODE_FOLDER"

generatedFileName=$(basename -- "$CSV_DATA")
generatedFileName="${generatedFileName%.*}"
generatedFile="$GENERATED_CODE_FOLDER/$generatedFileName"".js"

tempStateFile="$GENERATED_CODE_FOLDER/$generatedFileName""_states.js"
tempDataSubmitFile="$GENERATED_CODE_FOLDER/$generatedFileName""_dataSubmit.js"
tempInputFile="$GENERATED_CODE_FOLDER/$generatedFileName""_inputs.js"

# Json related files
generatedElementFile="$GENERATED_CODE_FOLDER/$generatedFileName""_element.js"
generatedColumnsListFile="$GENERATED_CODE_FOLDER/$generatedFileName""_columns.js"

rm "$tempStateFile"
rm "$tempDataSubmitFile"
rm "$tempInputFile"
rm "$generatedFile"

# Json related files
rm "$generatedElementFile"
rm "$generatedColumnsListFile"

# append "let data = {" to start of $tempDataSubmitFile
echo "let data = {" >>"$tempDataSubmitFile"

# append
echo -n "const columns = [undefined" >>"$generatedColumnsListFile"

# Read csv file and for each row
# 1.2 copy dataTypeInput.js to GeneratedCode folder
# 1.2 replace all occurrences of templateLabel with $dataLabel
# 1.3 replace all occurrences of reactVariableState with $reactVariableState
# 1.4 replace all occurrences of ReactVariableState to $ReactVariableState (uppercase R)
# 1.5 append copied file content to $tempStateFile
# 1.6 delete copied dataTypeInput.js
# 2. append "const [reactVariableState, setReactVariableState] = React.useState("");" to $tempStateFile
# 3. append "    reactVariableState: reactVariableState," to $tempDataSubmitFile
# 4. append "        <ListGroup.Item>Nom : {props.target$ModelEntity.$jsonPath}</ListGroup.Item>" to $generatedElementFile
# 5. append ", {
#            dataField: '$jsonPath',
#            text: "$dataLabel",
#            sort: true,
#            filter: showFilter ? $dataTypeFilter() : null,
#            hidden: true, // for csv only
#        }" to $generatedColumnsListFile

# at the end of loop:
# z.1 append "};" to close table of $tempDataSubmitFile
# z.2 append $tempStateFile, $tempDataSubmitFile and $tempInputFile files into final $generatedFile

count=0

while IFS=, read -r dataLabel dataType reactVariableState reactInitialState jsonPath; do
  ((count++))
  if [ "$count" == "1" ]; then
    continue
  fi

  echo "reading row no. $count"
  echo "$dataLabel | $dataType | $reactVariableState | $reactInitialState | $jsonPath"
  # trim white spaces
  dataType=$(echo "$dataType" | xargs)
  reactVariableState=$(echo "$reactVariableState" | tr -d "[:blank:]")
  reactInitialState=$(echo "$reactInitialState" | xargs | tr -d "\n\r")
  jsonPath=$(echo "$jsonPath" | tr -d "[:blank:]\n\r")

  # ensure reactVariableState start with lower case
  reactVariableState="$(tr '[:upper:]' '[:lower:]' <<<"${reactVariableState:0:1}")${reactVariableState:1}"

  # 1.1 copy dataTypeInput.js to GeneratedCode folder
  templateFileJS="$SCRIPT_FOLDER_LOCATION/formTemplate/$dataType"Input.js
  copiedInputJS="$GENERATED_CODE_FOLDER/$dataType"Input.js
  check_if_file_exist "$templateFileJS"
  cp "$file" "$GENERATED_CODE_FOLDER"
  echo "File $copiedInputJS copied!"

  # 1.2 replace all occurrences of templateLabel with $dataLabel
  sed -i "s/templateLabel/$dataLabel/g" "$copiedInputJS"
  echo "Replaced templateLabel with $dataLabel"

  # 1.3 replace all occurrences of reactVariableState with $reactVariableState
  sed -i "s/reactVariableState/$reactVariableState/g" "$copiedInputJS"
  echo "Replaced reactVariableState with $reactVariableState"

  # 1.4 replace all occurrences of ReactVariableState to $ReactVariableState (uppercase R)
  ReactVariableState="$(tr '[:lower:]' '[:upper:]' <<<"${reactVariableState:0:1}")${reactVariableState:1}"
  sed -i "s/ReactVariableState/$ReactVariableState/g" "$copiedInputJS"
  echo "Replaced ReactVariableState with $ReactVariableState"

  # 1.5 append copied file content to allInputForm.js
  echo -e "\n" >>"$copiedInputJS"
  cat "$copiedInputJS" >>"$tempInputFile"
  # 1.6 delete copied dataTypeInput.js
  rm "$copiedInputJS"

  # 2. append "const [reactVariableState, setReactVariableState] = React.useState("");" to $tempStateFile
  echo "const [$reactVariableState, set$ReactVariableState] = React.useState($reactInitialState); // $dataType" >>"$tempStateFile"

  # 3. append "    reactVariableState: reactVariableState," to $tempDataSubmitFile
  echo "    $reactVariableState: $reactVariableState," >>"$tempDataSubmitFile"

  # 4. append "        <ListGroup.Item>Nom : {props.target$ModelEntity.$jsonPath}</ListGroup.Item>" to $generatedElementFile
  if [ "$dataType" == "bool" ]; then
    echo "        <ListGroup.Item>$dataLabel : {props.target$ModelEntity.$jsonPath?'True':'False'}</ListGroup.Item>" >>"$generatedElementFile"
  else
    echo "        <ListGroup.Item>$dataLabel : {props.target$ModelEntity.$jsonPath}</ListGroup.Item>" >>"$generatedElementFile"
  fi

  # 5. append ", {
  #            dataField: '$jsonPath',
  #            text: "$dataLabel",
  #            sort: true,
  #            filter: showFilter ? $dataTypeFilter() : null,
  #            hidden: true, // for csv only
  #        }" to $generatedColumnsListFile
  if [ "$dataType" == "number" ]; then
    filterName="numberFilter()"
  elif [ "$dataType" == "date" ]; then
    filterName="dateFilter()"
  else
    filterName="textFilter()"
  fi

  {
    echo ", {"
    echo "           dataField: '$jsonPath',"
    echo "           text: \"$dataLabel\","
    echo "           sort: true,"
    echo "           filter: showFilter ? $filterName : null,"
    echo "           hidden: true, // for csv only"
    echo -n "       }"
  } >>"$generatedColumnsListFile"

done <"$CSV_DATA"

# at the end of loop:
# z.1 append "};" to close table of $tempDataSubmitFile
echo "};" >>"$tempDataSubmitFile"
echo "];" >>"$generatedColumnsListFile"

# z.2 append $tempStateFile, $tempDataSubmitFile and $tempInputFile files into final $generatedFile
cat "$tempStateFile" "$tempDataSubmitFile" "$tempInputFile" >>"$generatedFile"

rm "$tempStateFile"
rm "$tempDataSubmitFile"
rm "$tempInputFile"

if [ -z ${allCreatedFixedFiles+x} ]; then declare -A allCreatedFixedFiles; fi

allCreatedFixedFiles+=(["$generatedFile"]="$generatedFile")
allCreatedFixedFiles+=(["$generatedColumnsListFile"]="$generatedColumnsListFile")
allCreatedFixedFiles+=(["$generatedElementFile"]="$generatedElementFile")

echo "${#allCreatedFixedFiles[@]} files are created in $GENERATED_CODE_FOLDER"
for i in ${allCreatedFixedFiles[*]}; do echo "$i"; done
