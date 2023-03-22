# Regex Trucs et astuces

[Liste de tous les doc](../README.md)

## Tableau de contenu
<!-- TOC -->
* [Regex Trucs et astuces](#regex-trucs-et-astuces)
  * [Tableau de contenu](#tableau-de-contenu)
  * [Motivation](#motivation)
  * [Important](#important)
  * [Learn regex](#learn-regex)
  * [Intellij](#intellij)
* [Exercice 1 : Fix broken links](#exercice-1--fix-broken-links)
  * [Question Q1](#question-q1)
  * [Solution Q1](#solution-q1)
  * [Explanation Q1](#explanation-q1)
* [Exercice 2 : Add CSV Feature](#exercice-2--add-csv-feature)
  * [Question Q2](#question-q2)
  * [Solution Q2](#solution-q2)
  * [Explanation Q2](#explanation-q2)
<!-- TOC -->

```text
Editor note: don't format this file with auto-formatter as it breaks example used in regex, if so auto-format based on selected text only.
Make selection => (Ctrl + Alt + Shift + L)
```

## Motivation

As our project has many activities technically with the same functionalities to implement CRUD(
Create-Read-Update-Delete). We have to create different files for each either in backend (item.java, repo.java....) or
in frontend (list.js..). The general workflow is therefore to test the new functionality added, and once validated:
apply to others in same manner. So it will be useful to create a skeleton file for once, then replace the content using
regex for the others.

Benefits: 
* Save time with a factor (of the number of activities)
* Clean Aligned code once and for all in the project

Sometimes direct replacements x by y works, other times a variant part depending on activity name make it difficult for simple replacement.

This doc aims to learn how regex can do the job in such case!

## Important

The structure and functionality of the activity pages in the project should be aligned across all activities.
Recommendation when updating for activities: do not modify the structure of activity files individually unless related
to its data content, otherwise use replacement tools in all files at once with regex if needed.

## Learn regex


Must know:

| Regex | Type                   | Meaning                                                                            |
|-------|------------------------|------------------------------------------------------------------------------------|
| .     | Greedy and Lazy match  | Match any character                                                                |
| *     | Quantifiers            | repeat previous regex zero or more times                                           |
| .*    |                        | Match any line of characters                                                       |
| (  )  | Grouping and capturing | Parenthesis capturing group the regex inside of them for later call in replacement |
| [  ]  | Or operator            | Match any regex inside brackets                                                    |
| \     | escaping character     | use backslash \c to escape any above special regex character                       |
| \n    |                        | new line                                                                           |

[Autres sources](https://www.google.com/search?q=regex+tutorial)

[Online Test](https://regex101.com/)

## Intellij

To open find and replace tool:

* Ctrl + R to replace in file.
* Ctrl + Shift + R to replace in project / specific directory.
* Alt + X to turn on regex - you can also click button having sign of .*

To get captured group in replacement field in intellij use $i where i designate the number of the group in the regex

General workflow:

1. Make sure regex is turned on
2. Make a selection then press Ctrl + R
3. Intellij translate selected text into regex form by escaping any white/special character
4. Capture your variant with parenthesis exp: (variant)
5. Use .* instead of (variant) => (.*)
6. Replace your text by new the new text as normal text and use $1 to refer the first captured group.

# Exercice 1 : Fix broken links
Challenge: Grouping and capturing replacement

Coder has replaced usage of parenthesis and brackets as follows

```text
(  Text 1.1 must be within brackets ) [ Text 1.2 must be within parenthesis]
```


But it should be

```text
[  Text 1.1 must be within brackets ] ( Text 1.2 must be within parenthesis)
```

Imagine we have 50+ occurrence of such fault, so it is painful to find them, and it is more painful to correct each one.

## Question Q1
Use regex to replace parenthesis with brackets and fix it all at once!

Given sample 

```text
( awejpfi ) [ fawekpfjiawp iejfip ajwepfi j]

( afwe awef awe ) [ jawpefi jpawiefj paiwejf]
```

Expected replacement:

```text
[ awejpfi ] ( fawekpfjiawp iejfip ajwepfi j)

[ afwe awef awe ] ( jawpefi jpawiefj paiwejf)
```

[Full dataset see exercice_1.txt](exercice_1.txt):

[Expected solution dataset see exercice_1_sol.txt](exercice_1_sol.txt):

## Solution Q1

search for:
```regexp
\((.*)\) \[(.*)\]
```

replace with:
```text
[$1] ($2)
```

## Explanation Q1

- Use backslash to escape parenthesis / brackets in search: \( and \[
- Capture first and second group with (.*)
- Refer to captured group with $1 and $2 while placing them in right closing things.


# Exercice 2 : Add CSV Feature
Challenge: Multiline and White space replacement

Our developers team has decided to add csv feature, so after making change to one activity and agreed on changement to
be made for the rest of activities, it is time to apply changes on others ones.
Currently, with more than 10 activities, it was painful to search each file and apply changes.

For adding import and constant definitions, simple replacement did the job:

Example Search for
```javascript
import {AiFillDelete, AiOutlinePlusCircle} from "react-icons/ai";
```

Replace with: 
```javascript
import {AiFillDelete, AiOutlinePlusCircle} from "react-icons/ai";
import {GrDocumentCsv} from "react-icons/gr";
```

Then, when it comes to give the name of csv a file, simple replacement can do partial job as name of file is variant of activity.
Same as before find unique position in the code and peform replacement with added code.

Exemple: search for
```regexp
                    search
                >
```
Replace with
```javascript
                    exportCSV={ {
                        fileName: 'template.csv',
                        onlyExportFiltered: true,
                        exportAll: false } }
                    search
                >
```

## Question Q2

Notice that original code was as below, containing field data = {actvitylist}, find regex to replace below block and use
same name as variable list for the csv file.

Given sample:
```javascript
                    data={educationList}
                    columns={columns}
                    search
                >
```

Expected replacement: 
```javascript
                    data={educationList}
                    columns={columns}
                    exportCSV={ {
                        fileName: 'educationList.csv',
                        onlyExportFiltered: true,
                        exportAll: false } }
                    search
                >
```
[Full dataset see exercice_2.txt](exercice_2.txt)

[Expected solution dataset see exercice_2_sol.txt](exercice_2_sol.txt)


## Solution Q2

Search for:
```regexp
                    data\=\{(.*)\}\n                    columns\=\{columns\}\n                    search\n                \>
```

replace with:
```javascript
                    data={$1}
                    columns={columns}
                    exportCSV={ {
                        fileName: '$1.csv',
                        onlyExportFiltered: true,
                        exportAll: false } }
                    search
                >
```

## Explanation Q2

* Do not start your regex from zero, counting line and white spaces isn't the way to go
* First quick solution is to make selection of the target then Ctrl+R while regex is turned on
* Intellij automatically gives following regex
```regexp
                    data\=\{educationList\}\n                    columns\=\{columns\}\n                    search\n                \>
```
* Capture and replace variant educationList in a group: (.*)
* Replace with working code using $1 inside instead of template.

Alternative second solution:
* If somehow intellij didn't detect all occurrences because of spaces,
* then group and capture white spaces and discard their value in replacement
* search for
```regexp
(.*)data\=\{(.*)\}\n(.*)columns\=\{columns\}\n(.*)search\n(.*)\>
```
* Count for the index at where your variant is captured, 2nd in here
* replace with working code using $2 instead of template
```javascript
                    data={$2}
                    columns={columns}
                    exportCSV={ {
                        fileName: '$2.csv',
                        onlyExportFiltered: true,
                        exportAll: false } }
                    search
                >
```

