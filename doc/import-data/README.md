# Import Csv Data into database workflow

1. The user upload a CSV file.
2. The system attempts to automatically detect the target entity by matching the file name pattern against a list of
   patterns (e.g. "activity.*csv" will match "activity.csv").
3. If the system fails to find a matching pattern, it prompts the user to select the target entity from a drop-down
   list.
4. The target entity is set and the CSV file is parsed using the semi-colon delimiter as the default.
5. The user has the option to select a different delimiter (such as comma, semicolon, pipe, etc.) from a drop-down list.
6. After the first parse, if the first line header contains only one header, the first line is discarded and the file is
   parsed again.
7. The number of headers and their titles are determined after parsing the file.
8. The system displays warnings for any syntax errors that occurred during parsing (such as too few fields or missing
   delimiters).
9. If errors are present, the user is prompted to fix the CSV file, and the process starts again from the beginning.
10. The provided CSV file is compared to the target entity template, and warnings are issued if the number of columns or
    column names do not match. If the number of columns does not match, the user must either change the target template
    or upload a different CSV file.
11. If the provided CSV file matches the template, a dependency check is performed to ensure that all required files
    have been uploaded (e.g. "researcher.csv" if "activity.csv" is the target).
12. If any dependencies are missing, the user must upload the required files, following the same workflow.
13. Once all dependencies are satisfied, a button becomes available to insert the CSV file into the database.
14. The server receives the CSV file and performs type conversion/parsing for each row to validate the format (e.g. ID
    must be an integer, date must be formatted as a date, etc.).
15. Rows that do not meet the expected format or have missing dependencies are discarded, and a warning is issued at the
    end of the process.
16. A list of filtered rows is prepared for insertion into the database.
17. The merging keys for the list of rows to be inserted are calculated, as defined in the template (e.g. researcher key
    is first name, last name, and email).
18. The database list is retrieved, and its merging keys are calculated in the same manner.
19. For each row in the list to be inserted, a matching key is compared to the database list.
20. If a match is found, the CSV ID is associated with the database ID and the row is not inserted into the database.
21. If a match is not found, a new record is inserted into the database, and a new database ID is generated and
    associated with the CSV ID.
22. In both cases, the row of the CSV file will have its reference in the database ID, and dependent templates will use
    the database ID for calculating their merging keys.
23. The filtered CSV list is imported into the database.
24. The user receives the number of successful imports and the number of failed imports, along with the reasons for any
    failures.

Usage exemple :

* The user upload researcher list with 220 entries for the first time on empty database, 220 entries will be inserted in
  the database.
* After first insertion the user upload the same file again with 220 entries, the system will detect that all the
  entries are already in the database and will not insert them again.
* The user add 10 new entries in the researcher list (230 total) and upload it again, the system will detect that 10
  entries are new and will insert them in the database.
* The user change the name of one researcher in the list and upload it again, the system will detect that the name of
  the researcher doesn't exist in database and therefore insert a new researcher in the database.
  (!!! The old researcher will not be deleted from the database neither updated !!!)
* The import system is intended to be used in first stage for deploying the web application, it is not intended to be
  used for updating the database in a regular basis. Although it is possible to use it for adding new entries in the
  database, current implementation does not allow using it for updating existing entries in the database.
  There is a plan to implement possibility of updating fields that doesn't contribute to the merging keys of the
  entity, but it is not implemented yet.

Advanced usage :

* The user upload list of 10 entities, the merging keys of 2 entities are the same, the system will detect that the
  merging keys are the same and will insert only 9 entities in the database.
  Take an example of expertise_scientific, there is 2 entities with same start_date, description, end_date and
  researcher_id, the system will take the last occurrence i.e. the second entity line and discard the first.


## Concept of merging keys

The concept of merging keys is to enable the import of a CSV file multiple times without creating duplicate entries.
When importing an entry from the CSV file into the database, it is important to detect whether the entry has already
been inserted. Several solutions have been proposed:

* Solution 1 involves inserting the entry into the database with the same ID as the one used in the CSV file. However,
  this approach has been abandoned due to the potential for conflicts and the risk of occupying IDs that are already in
  use.

* Solution 2 involves adding an additional column to the database for mapping purposes, which would require significant
  changes to the schema and result in redundant data. This approach may not be practical for standalone applications
  that do not rely on imported CSV files.

* Solution 3 involves generating a merging key that substitutes for the entry's identifier. For example, a merging key
  could be constructed by concatenating the researcher's name, surname, and email. If the merging key is already present
  in the database, no action is taken. However, the merging key must be created manually and may not include all fields.