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
    ```json
    {
    	"_embedded": {
    		"smartMeterDTOList": [{
    				"id": 1,
    				"electricityRead": 500,
    				"gasRead": 200,
    				"_links": {
    					"read": {
    						"href": "http://localhost:8080/api/smart/read/1"
    					}
    				}
    			},
    			{
    				"id": 5,
    				"electricityRead": 900,
    				"gasRead": 333,
    				"_links": {
    					"read": {
    						"href": "http://localhost:8080/api/smart/read/5"
    					}
    				}
    			},
    			{
    				"id": 6,
    				"electricityRead": 999,
    				"gasRead": 444,
    				"_links": {
    					"read": {
    						"href": "http://localhost:8080/api/smart/read/6"
    					}
    				}
    			}
    		]
    	},
    	"_links": {
    		"self": {
    			"href": "http://localhost:8080/api/smart/reads/1000001"
    		}
    	}
    }
    ```
* Return Error when record not found <br />
    http://localhost:8080/api/smart/reads/404
    ```json
    {
        "status": "NOT_FOUND",
        "message": "Account 404 not found",
        "errors": "Record not found"
    }
    ```
* Get specific read record <br />
    http://localhost:8080/api/smart/read/1
    ```json
    {
           "id": 5,
           "gasRead": 333,
           "electricityRead": 900,
           "accountNumber": 1000001,
           "gasMeterId": "G123",
           "electricityMeterId": "E123",
           "_links": {
               "self": {
                   "href": "http://localhost:8080/api/smart/read/5"
               }
           }
       }
     ```
