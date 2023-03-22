#!/bin/bash
#################################################################################
#
# ModelEntity react generator
# This script will generate models based on a template model existing in the
# project.
#
# Usage: 1. change project dependent variable below for once (already done)
#        2. open bash terminal and navigate to project root location
#        3. Execute this script given ModelEntity as argument using CamelCase
#        (exemple PostDoc).
# if second argument specifying CSV data is given,
# then CreateFormBootstrap.sh is called too
# Example:  ./doc/code-generator/CreateActivityFrontEnd.sh PostDoc
#################################################################################

# project dependent initialization
FRONT_END_REACT_LOCATION="hceres-frontend"
TemplateEntity="Education" ## Use CamelCase writing for template model

# initialize subfolder locations from content root
PROJECT_LOCATION=$(pwd)
SRC_LOCATION="$FRONT_END_REACT_LOCATION/src"
SCRIPT_FOLDER_LOCATION=$(dirname "$0")
GENERATED_CODE_FOLDER="$PROJECT_LOCATION/GeneratedCode"

if [ $# == 0 ]; then
  echo >&2 "No ModelEntity name was provided"
  echo >&2 "Usage example:"
  echo >&2 "$0 ModelEntity"
  echo >&2 "Or"
  echo >&2 "$0 ModelEntity ModelForm.csv"
  exit 1
elif [ $# == 1 ]; then
  if [[ $1 == *".csv" ]]; then
    # if only csv data is sent, remove From.csv suffix and use it as ModelEntity
    ModelEntity=$(basename -s ".csv" "$1")
    ModelEntity=${ModelEntity%"Form"} # remove suffix Form if present
    csvDataFile=$1
  else
    ModelEntity=$1
  fi
elif [ $# == 2 ]; then
    ModelEntity=$1
    csvDataFile=$2
fi

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

CamelCase_to_separate_by_dash() {
  # https://stackoverflow.com/questions/8502977/linux-bash-camel-case-string-to-separate-by-dash
  sed --expression 's/\([A-Z]\)/-\L\1/g' \
    --expression 's/^-//' \
    <<<"$1"
}

clear_empty_dirs() {
  dirFile=$1
  while rm -d "$dirFile" 2> /dev/null; do
      dirFile=$(dirname "$dirFile")
  done
}


# check component folder and template pacakge if exist
check_if_folder_exist "$SRC_LOCATION"

echo "$ModelEntity is used"

# initialize package name and targetLocations
template_package=$(CamelCase_to_separate_by_dash "$TemplateEntity")
target_package=$(CamelCase_to_separate_by_dash "$ModelEntity")

# clear Generated code folder
targetLocation="$GENERATED_CODE_FOLDER/$FRONT_END_REACT_LOCATION"
rm -r "$targetLocation" 2> /dev/null
echo "Code will be generated in: $GENERATED_CODE_FOLDER"

# Traverse all files in $SRC_LOCATION recursively and perform following steps:
# For each file pattern of *TemplateEntity*:
# 0. copy file TemplateEntity to $GENERATED_CODE_FOLDER with same parent structure
# 1. rename file template-entity/TemplateEntity to model-entity/ModelEntity
# 2. replace all occurrences of template_package name to target_package
# 3. replace all occurrences of TemplateEntity to ModelEntity
# 4. replace all occurrences of templateEntity to modelEntity
# 5. replace all plurals name having y at the end with ies

# Create an array for storing the created files, if one does not already exist,
# and print the contents of the array at the end
if [ -z ${allCreatedFixedFiles+x} ]; then declare -A allCreatedFixedFiles; fi

while IFS= read -r -d '' templateFile; do
  ((count++))
  echo "Found file no. $count"

  # 0. copy file TemplateEntity to $GENERATED_CODE_FOLDER with same parent structure
  cp --parent "$templateFile" "$GENERATED_CODE_FOLDER"
  templateFile="$GENERATED_CODE_FOLDER/$templateFile"
  echo "$templateFile"

  # 1. rename file template-entity/TemplateEntity to model-entity/ModelEntity
  # ${variable//search/replace}
  modelFile=${templateFile//"$TemplateEntity"/"$ModelEntity"}
  modelFile=${modelFile//"$template_package"/"$target_package"}

  mkdir -p "$(dirname "$modelFile")"
  mv "$templateFile" "$modelFile"
  clear_empty_dirs "$(dirname "$templateFile")"
  echo "Renamed file to $modelFile"

  # 2. replace all occurrences of template_package name to target_package
  sed -i "s/\/$template_package/\/$target_package/g" "$modelFile"
  echo "Replaced /$template_package with /$target_package"

  # 3. replace all occurrences of TemplateEntity to ModelEntity
  sed -i "s/$TemplateEntity/$ModelEntity/g" "$modelFile"
  echo "Replaced $TemplateEntity with $ModelEntity"

  # 4. replace all occurrences of templateEntity to ModelEntity
  templateEntity="$(tr '[:upper:]' '[:lower:]' <<<${TemplateEntity:0:1})${TemplateEntity:1}"
  modelEntity="$(tr '[:upper:]' '[:lower:]' <<<${ModelEntity:0:1})${ModelEntity:1}"
  sed -i "s/$templateEntity/$modelEntity/g" "$modelFile"
  echo "Replaced $templateEntity with $modelEntity "
  allCreatedFixedFiles+=(["$count"]="$modelFile")

  # 5. replace all plurals name having y at the end with ies
  lastCharacterIndex=${#ModelEntity}-1
  if [ "${ModelEntity:$lastCharacterIndex}" = "y" ]; then
    sed -i "s/${ModelEntity:1}s/${ModelEntity:1:$lastCharacterIndex-1}ies/g" "$modelFile"
    echo "Replaced ${ModelEntity:1}s with ${ModelEntity:1:$lastCharacterIndex-1}ies "
  fi
done < <(find "$SRC_LOCATION" -name "*$TemplateEntity*js" -print0)

if [ -n "$csvDataFile" ]; then
  source "$SCRIPT_FOLDER_LOCATION/CreateFormBootstrap.sh" "$csvDataFile" "$ModelEntity"
fi

echo "${#allCreatedFixedFiles[@]} files are created in $GENERATED_CODE_FOLDER"
for i in ${allCreatedFixedFiles[*]}; do echo "$i"; done
