Note: config server is added in this branch and reactjs frontend app is also added

order of starting services:
1. post-config-server
2. service-registry-in-eureka
3. post-service
4. content-service
5. post-api-gateway-service
6. zipkin, to see the logs start the docker-compose.yml this is for to see the logs
7. run the my-post-react-app to test from front end

this is the complete application

included features are spring security and two microservices post-service and content service

to see the services in eureka goto --> http://localhost:8761/

to see the logs in zipkin goto --> http://localhost:9411/

Steps create following two tables

step 1: create database schema as "db_posts_new" in mysql workbench or
CREATE SCHEMA `db_posts_new` ;

step 2: in above schema create following tables

CREATE TABLE Users (
id INT NOT NULL AUTO_INCREMENT,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
role VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);

step 3:

CREATE TABLE Posts (
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
title VARCHAR(50) NOT NULL,
body VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES Users(id)
);

-------------------------------
Now we are accessing with the api gateway port/gateway server name
step 1:

to create the user
POST:
http://localhost:8060/api/auth/register

{
"firstName": "Srijan",
"lastName": "Veerareddy",
"email": "srijan.veerareddy@gmail.com",
"password":"srijan"
}

returns the JWT token


step 2: to login or authenticate
POST:
http://localhost:8060/api/auth/authenticate

{
"email":"srijan.veerareddy@gmail.com",
"password": "srijan"
}

return the token

step 3:

take the token add it as bearer token in "Authorization" tab in postman

eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcmlqYW4udmVlcmFyZWRkeUBnbWFpbC5jb20iLCJpYXQiOjE3MDMyMzYzNjIsImV4cCI6MTcwMzI3MjM2Mn0.tXh2wU_gWx04oI0Wjqc-648TOyTk5kEQ-1o5zEVClhs
take the user id form Users table

POST
http://localhost:8060/api/content/posts
{
"userId":12,
"title": "Core Java ariticle",
"body": "this is the post talks about core java features"
}

step 4: update

PUT
http://localhost:8060/api/content/posts
{
"userId":12,
"title": "Core Java article",
"body": "this is the post talks about core java features"
}

step 5: delete
DELETE

http://localhost:8060/api/content/posts/4

step 6: 

GET
localhost:8060/api/content/posts/1

