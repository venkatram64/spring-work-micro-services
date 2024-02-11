https://jwt.io/

to generate key https://securekit.org/random-key-generator
I used some string like "mytestkey"

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







---------------running the rest end points

step 1: register
Post:   http://localhost:8081/api/auth/register

data --->
{
"firstName": "Venkatram",
"lastName": "Veerareddy",
"email": "venkat.veerareddy@gmail.com",
"password":"venkat"
}

step 2: authenticate

and    
http://localhost:8080/api/auth/authenticate
Post -->  {
"email":"venkat.veerareddy@gmail.com",
"passwrd": "venkat"
}


step 3:

set the bearer in postman below token, then run the following

eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2ZW5rYXQudmVlcmFyZWRkeUBnbWFpbC5jb20iLCJpYXQiOjE3MDMxNDAyMjYsImV4cCI6MTcwMzE3NjIyNn0.ka2zzBr11B_1GDEOeCCY2Gw2mTHEJVsmPEADikQ7oUI

above taken will be generated for registered user

GET http://localhost:8080/api/content/posts


spring authentication
![img.png](img.png)


![img_1.png](img_1.png)

![img_2.png](img_2.png)


