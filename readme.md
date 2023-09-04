# Bank Project [Backend]

 There is a prototype of the BackEnd Bank's Core Services data.

 Data consist of clients, accounts, products, accounts, transactions and managers
___

* [ApiDoc Link](http://localhost:8080/swagger-ui/index.html) 
* [JACOCO Link](http://localhost:63342/BankProj/target/site/jacoco/index.html)
___
## Database structure

### Table Client (Bank's Clients table)

| Column name | Type        | Description                                   |
|-------------|-------------|-----------------------------------------------|
| id          | bigint      | id key of row - unique, not null, primary key | 
| manager_id  | int         | manager id                                    |
| status      | int(1)      | client's status                               |
| tax_code    | varchar(20) | client's TAX code (external ID)               |
| first_name  | varchar(50) | client's name                                 |
| last_name   | varchar(50) | client's surname                              |
| email       | varchar(60) | client's e-mail                               |                               
| address     | varchar(80) | client's address                              |
| phone       | varchar(20) | client's phone                                |                                
| created_at  | timestamp   | timestamp of row creation                     |
| updated_at  | timestamp   | timestamp of last update                      |



### Table Account (Bank's accounts table)

| Column name   | Type         | Description                                   |
|---------------|--------------|-----------------------------------------------|
| id            | bigint       | id key of row - unique, not null, primary key |
| client_id     | bigint       | client id                                     |         
| name          | varchar(100) | a name of account                             |                              
| type          | int(1)       | account type                                  |                                   
| status        | int(1)       | status of tne account                         |                          
| balance       | bigint       | balance of the account in currency            | 
| currency_code | int(2)       | account currency code                         |                          
| created_at    | timestamp    | timestamp of row creation                     |
| updated_at    | timestamp    | timestamp of last update                      |

### Table Product (Sets of Bank's available Products)
| Column name   | Type         | Description                                                              |
|---------------|--------------|--------------------------------------------------------------------------|
| id            | bigint       | id key of row - unique, not null, primary key                            |
| manager_id    | bigint       | manager id                                                               |
| name          | varchar(70)  | product's name                                                           |
| status        | int(1)       | product's status                                                         |
| currency_code | int(2)       | currency of product                                                      |
| interest_rate | numeric(6,4) | interest rate of product                                                 |
| limit         | bigint       | limit of credit a product ( 0 - no limit, 0 < - limit which can be used) |
| created_at    | timestamp    | timestamp of row creation                                                |
| updated_at    | timestamp    | timestamp of last update                                                 |

### Table Agreement (Bank's - Client's  Agreement table)

| Column name   | Type          | Description                                   |
|---------------|---------------|-----------------------------------------------|
| id            | bigint        | id key of row - unique, not null, primary key |
| account_id    | bigint        | client's account                              | 
| product_id    | bigint        | product id (table product)                    | 
| interest_rate | numeric(6,4)	 | current interest rate of agreement            | 
| status        | int           | agreement's status                            | 
| sum           | bigint        | amount of agreement                           | 
| created_at    | timestamp     | timestamp of row creation                     | 
| updated_at    | timestamp     | timestamp of last update                      | 

 ### Table Transaction (Bank's Product table) 

| Column name        | Type         | Description                                   |
|--------------------|--------------|-----------------------------------------------|
| 	id                | bigint       | id key of row - unique, not null, primary key | 
| 	debit_account_id  | bigint       | transaction's debit account                   | 
| 	credit_account_id | bigint       | transaction's credit account                  | 
| 	type              | int(1)       | transaction type                              | 
| 	amount            | bigint       | transaction amount in the account currency    | 
| 	description       | varchar(255) | description of transaction                    | 
| 	created_at        | timestamp    | timestamp of row creation                     | 

 ### Table Manager (Bank's managers)

| Column name  | Type          | Description                                   |
|--------------|---------------|-----------------------------------------------|
| 	id          | int           | id key of row - unique, not null, primary key | 
| 	first_name  | varchar(50)   | manager's name                                | 
| 	last_name   | varchar(50)   | manager's surname                             | 
| 	status      | int           | manager's status                              | 
| 	description | varchar(255)  | description of transaction                    | 
| 	created_at  | timestamp     | timestamp of row creation                     |