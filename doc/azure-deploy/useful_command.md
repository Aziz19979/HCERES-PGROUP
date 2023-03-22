```bash


AzureHceresDB_URL="hceres-sql.postgres.database.azure.com:5432/hceres"
AzureHceresDB_username="hceres"
AzureHceresDB_password="8)F^N*t{8\"72uMXH"

export AzureHceresDB_URL
export AzureHceresDB_username
export AzureHceresDB_password

# run spring boot on local 
mvn -Pazure spring-boot:run

mvn com.microsoft.azure:azure-webapp-maven-plugin:2.2.0:config
# deploy to azure
mvn -Pazure package azure-webapp:deploy

```