Note: Eureka server and zipkin is added in this

order of starting services:
1. service-registry-in-eureka
2. post-service
3. content-service
4. to see the logs start the docker-compose.yml this is for to see the logs

to see the services in eureka goto --> http://localhost:8761/

to see the logs in zipkin goto --> http://localhost:9411/

Steps create following two tables

step 1: create database schema as "db_posts" in mysql workbench or
CREATE SCHEMA `db_posts` ;

step 2: in above schema create following tables

CREATE TABLE Users (
id INT NOT NULL AUTO_INCREMENT,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
role VARCHAR(255) NOT NULL,
PRIMARY KEY (id)
);

step 3:

CREATE TABLE Posts (
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
title VARCHAR(50) NOT NULL,
body VARCHAR(255) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES Users(id)
);

-------------------------------



step 1: start the two micro services
1. post-service
2. content-service

step 2:

to create the user
POST:
http://localhost:8080/api/auth/register

{
"firstName": "Srijan",
"lastName": "Veerareddy",
"email": "srijan.veerareddy@gmail.com",
"password":"srijan"
}

returns the token


step 3: to login or authenticate
POST:
http://localhost:8080/api/auth/authenticate

{
"email":"srijan.veerareddy@gmail.com",
"password": "srijan"
}

return the token

step 4:

take the token add it as bearer token in "Authorization" tab in
postman

eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcmlqYW4udmVlcmFyZWRkeUBnbWFpbC5jb20iLCJpYXQiOjE3MDMyMzYzNjIsImV4cCI6MTcwMzI3MjM2Mn0.tXh2wU_gWx04oI0Wjqc-648TOyTk5kEQ-1o5zEVClhs
take the user id form Users table
POST
http://localhost:8080/api/content/posts
{
"userId":12,
"title": "Core Java ariticle",
"body": "this is the post talks about core java features"
}

step 4: update

PUT
http://localhost:8080/api/content/posts
{
"userId":12,
"title": "Core Java article",
"body": "this is the post talks about core java features"
}

step 5: delete
DELETE

http://localhost:8080/api/content/posts/4

step 6: 

GET
localhost:8080/api/content/posts/1

