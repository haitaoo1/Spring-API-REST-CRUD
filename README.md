
# Spring Boot- APIRest

This is an example of a REST API developed using the Spring Boot Framework, Maven, and JPA-Hibernate for data persistence, connected to a MySQL database. Additionally, this project has been deployed using Docker for container management. To perform CRUD (Create, Read, Update, and Delete) operations on a sample entity, such as Product, users are required to log in with existing credentials or create a new user.


## Starting the Project
To build and package the project, you can execute the following commando

```
./mvnw clean package -DskipTests
```
This command will clean the project, compile the code, run tests (skipped with -DskipTests), and package the application into a JAR file.


## Docker Container 
Navigate to the docker directory:

```
cd docker
```

To start the Docker container, use the following command:

```
docker-compose up
```
This command will initialize the Docker container based on the configuration provided in the docker-compose.yml file.

To take down the Docker container when finished testing, you can use the following command:

```
docker-compose down
```



## API Reference

#### Get all users

```http
  GET /api/users
```
| Description                       |
| :-------------------------------- |
  | `Get all users`


#### Create a new user

```http
  POST /api/users/register
```

| Request Body | Description                       |
| :-------- | :-------------------------------- |
| `JSON`     | `{"username": "...", "password" : "..."}`|




###

#### 


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



