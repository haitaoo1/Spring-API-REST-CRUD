
# Spring Boot- API Rest

Create an example of API REST using Spring Boot Framework with Maven and JPA-Hibernate connected to MySQL.





## Database Configuration
Using the application.properties file to configure the connection to a MYSQL database
```
spring.jpa.database=MYSQL
spring.datasource.url=jdbc:mysql://localhost:3306/db_jpa_crud
spring.datasource.username=root
spring.datasource.password=*****
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true

```
## API Reference

#### Get all products in the DB

```http
  GET /api/products
```
| Description                       |
| :-------------------------------- |
  | `Get all products`

#### Get a specific product

```http
  GET /api/products/${id}
```
| Parameter | Type      | Description                       |
| :-------- | :-------  |:------------------------ |
| `id`      | `Long`  |`Get a specific product from DB`|

#### Create a product

```http
  POST /api/products
```

| Request Body | Description                       |
| :-------- | :-------------------------------- |
| `JSON`     | `Create a new Product with the JSON content` |

#### Update a product
```http
  PUT /api/products/{id}
```

| Parameter | Type     |Request Body | Description                       |
| :-------- | :------- | :----- |:------------------------ |
| `id`      | `Long` | `JSON` |` Update the product (if exist the product with "id"`|

#### Delete a product
```http
  DELETE /api/products/{id}
```

| Parameter | Type     |Request Body | Description                       |
| :-------- | :------- | :----- |:------------------------ |
| `id`      | `Long` | `JSON` |`Delete the product (if exist the product with "id"`|


The methods(POST, GET, DELETE) won´t return anything if the product does not exist. It will show us the HTTP status (ERROR 404 NOT FOUND). All exceptions are managed.

## JWT

