# Used tech stack
1. java 17
2. spring boot 3.4.0
3. h2 in memory database

# Example curl for store employee details in DB
```
curl --location 'http://localhost:8080/employee/details' \
--header 'Content-Type: application/json' \
--data-raw '{
"employeeId": 2,
"firstName": "mediboina",
"lastName": "satyaNarayana",
"email": "medboyanasiva@gmail",
"phoneNumber": "9640777811",
"DOJ": "01-04-2024",
"salary": 50000.0
}'
```


# Example curl for get all employees tax detection details
```
curl --location 'http://localhost:8080/employee/taxDetection'
```