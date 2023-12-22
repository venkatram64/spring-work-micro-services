
step 1: create database schema as "db_posts" in mysql workbench or
CREATE SCHEMA `db_posts` ;

step 2: in above schema create following tables

CREATE TABLE Posts (
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
title VARCHAR(50) NOT NULL,
body VARCHAR(255) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES Users(id)
);

---------------running the rest end points

step 1:

GET http://localhost:8081/api/posts






