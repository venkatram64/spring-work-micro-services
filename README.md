

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
GET
http://localhost:8080/api/content/posts



