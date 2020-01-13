# Getting Started

### Thought Process
Given problem statement is asking for create RESTful web service for Smart meter reads of given account number.<br />
I assume that "SMART" meter takes reads daily so there can be multiple reads associate to one account number.
Hence, this service returns list of reads. 

### How to start application 
Gradle build and run:  

```
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

### Response

HATEOAS style response

### Assumptions
Some assumptions are listed below:

* There is relationship between Account and Smart Meter Read table
* No Smart Meter Read available without valid account
* Application will throw error 404 - NOT_FOUND if account number not available

Examples

* Return all reads of account number <br />
    http://localhost:8080/api/smart/reads/1000001
* Return Error when record not found <br />
    http://localhost:8080/api/smart/reads/404
* Get specific read record <br />
    http://localhost:8080/api/smart/read/1

