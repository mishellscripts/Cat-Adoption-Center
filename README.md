# Cat Adoption Center DB

Report: [Google doc](https://docs.google.com/document/d/1kru3k2VQYoodgi3i0JZDVLYTlcVEuiN3UdxDB8rACxw/)  
To ensure JDBC connection, add a file named db.properties with .properties extension in the cloned directory with the following:  
```
MYSQL_DB_DRIVER_CLASS=com.mysql.jdbc.Driver
MYSQL_DB_URL=...
MYSQL_DB_USERNAME=...
MYSQL_DB_PASSWORD=...
```


## CHANGES TO THE SCHEMA
The following are the changes to src/ddl.sql since the first report submission.

### ADOPTION
* Data type of adoption_date changed from DATE to TIMESTAMP and uses CURRENT_TIMESTAMP as default
* Added an updatedAt attribute with data type TIMESTAMP to archive older adoption tuples

### ARCHIVE (NEW RELATION)
* Archives old adoption data. Stores adoption tuples without the updatedAt column

### CAT
* Added an “adopted” attribute. Data type is integer with default value of 0 (FALSE)
* Changed name -> cName to avoid errors since 'name' is a keyword

### MEDICAL
* Primary key in medical is now (cID, disease) instead of just cID

### Triggers
* Updates to adoption_age_restriction: When a cat is adopted, update its adopted attribute only if person's age is 12 or older
