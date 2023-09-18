# Bank Project [Backend]

There is a prototype of the BackEnd Bank's Core Services data.

Data consist of users, accounts, products, accounts and transactions
___

* [ApiDoc Link](http://localhost:8080/swagger-ui/index.html)
* [JACOCO Link](http://localhost:63342/BankProj/target/site/jacoco/index.html)

___

## Database structure

### Table User (Bank's Users table)

| Column name | Type        | Description                                   |
|-------------|-------------|-----------------------------------------------|
| id          | bigint      | id key of row - unique, not null, primary key | 
| role        | varchar(20) | user's role                                   |
| status      | varchar(20) | user's status                                 |
| tax_code    | varchar(20) | user's TAX code (external ID)                 |
| first_name  | varchar(50) | user's name                                   |
| last_name   | varchar(50) | user's surname                                |
| email       | varchar(60) | user's e-mail                                 |  
| password    | varchar(60) | user's password                               |
| address     | varchar(80) | user's address                                |
| phone       | varchar(20) | user's phone                                  |                                
| created_at  | timestamp   | timestamp of row creation                     |
| updated_at  | timestamp   | timestamp of last update                      |

### Table Account (Bank's accounts table)

| Column name   | Type          | Description                                   |
|---------------|---------------|-----------------------------------------------|
| id            | bigint        | id key of row - unique, not null, primary key |
| client_id     | bigint        | client id                                     |         
| name          | varchar(100)  | a name of account                             |
| status        | varchar(20)   | status of tne account                         |                          
| balance       | decimal(15,2) | balance of the account in currency            | 
| currency_code | varchar(20)   | account currency code                         |                          
| created_at    | timestamp     | timestamp of row creation                     |
| updated_at    | timestamp     | timestamp of last update                      |

### Table Product (Sets of Bank's available Products)

| Column name   | Type          | Description                                                    |
|---------------|---------------|----------------------------------------------------------------|
| id            | bigint        | id key of row - unique, not null, primary key                  |
| name          | varchar(70)   | product's name                                                 |
| status        | varchar(20)   | product's status                                               |
| currency_code | varchar(20)   | currency of agreement                                          |
| interest_rate | decimal(6,1)	 | current interest rate of agreement                             | 
| product_limit | decimal(15,2) | limit of credit ( 0 - no limit, 0 < - limit which can be used) |
| created_at    | timestamp     | timestamp of row creation                                      |
| updated_at    | timestamp     | timestamp of last update                                       |

### Table Agreement (Bank's - Client's  Agreement table)

| Column name | Type          | Description                                   |
|-------------|---------------|-----------------------------------------------|
| id          | bigint        | id key of row - unique, not null, primary key |
| account_id  | bigint        | client's account                              |
| manager_id  | bigint        | manager id                                    |
| product_id  | bigint        | product id (table product)                    |
| status      | varchar(20)   | agreement's status                            | 
| sum         | decimal(15,2) | amount of agreement                           |
| created_at  | timestamp     | timestamp of row creation                     | 
| updated_at  | timestamp     | timestamp of last update                      | 

### Table Transaction (Bank's Product table)

| Column name        | Type          | Description                                   |
|--------------------|---------------|-----------------------------------------------|
| 	id                | bigint        | id key of row - unique, not null, primary key | 
| 	debit_account_id  | bigint        | transaction's debit account                   | 
| 	credit_account_id | bigint        | transaction's credit account                  | 
| 	status            | varchar(20)   | transaction status                            | 
| 	amount            | decimal(12,2) | transaction amount in the account currency    | 
| 	description       | varchar(255)  | description of transaction                    | 
| 	created_at        | timestamp     | timestamp of row creation                     | 
