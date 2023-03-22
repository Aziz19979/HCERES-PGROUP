# Code Generator
[Liste de tous les doc](../README.md)

<!-- TOC -->
* [Code Generator](#code-generator)
* [Generate New Activity backend & CSV](#generate-new-activity-backend--csv)
* [Generate New Activity frontend](#generate-new-activity-frontend)
* [Generate Form Bootstrap & React using CSV](#generate-form-bootstrap--react-using-csv)
<!-- TOC -->

For general workflow check [Youtube Private Video - HCERES Auto-Generate Activity Backend & Frontend](https://youtu.be/kKRJQriysII)

# Generate New Activity backend & CSV

Lunch [CreateActivityBackEnd.sh](./CreateActivityBackEnd.sh) to create new activity back end files following conventions.

Example: open terminal from root of the project and try following script:

```bash
./doc/code-generator/CreateActivityBackEnd.sh ./hceres/src/main/java/org/centrale/hceres/items/Patent.java
```

You can also redirect standard error to separate file by using: 2> Backend_generation_error.txt

```bash
mkdir "GeneratedCode"
./doc/code-generator/CreateActivityBackEnd.sh ./hceres/src/main/java/org/centrale/hceres/items/Patent.java 2> GeneratedCode/Backend_generation_error.txt
```



# Generate New Activity frontend

Lunch [CreateActivityFrontEnd.sh](./CreateActivityFrontEnd.sh) to create new activity front end files following conventions.

Example: open terminal from root of the project and try following script:

```bash
./doc/code-generator/CreateActivityFrontEnd.sh MyModel
```

And try
```bash
./doc/code-generator/CreateActivityFrontEnd.sh PostDoc ./doc/code-generator/activities/PostDoc/PostDocForm.csv
```

Or just with csv file (Model name will be concluded from csv name)
```bash
./doc/code-generator/CreateActivityFrontEnd.sh ./doc/code-generator/activities/Patent/PatentForm.csv
```


After lunching script check [GeneratedCode folder](../../GeneratedCode)


# Generate Form Bootstrap & React using CSV

Lunch [CreateFrontActivity.sh](./CreateFormBootstrap.sh) to create a squelette form adapting with types of variable in csv.
For csv format check script file description, or take a look at [PostDocForm.csv](./activities/PostDoc/PostDocForm.csv)


Example: open terminal from root of the project and try following script:

```bash
./doc/code-generator/CreateFormBootstrap.sh ./doc/code-generator/activities/PostDoc/PostDocForm.csv
```

After lunching script check [GeneratedCode folder](../../GeneratedCode)