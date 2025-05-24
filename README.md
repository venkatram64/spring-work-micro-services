Note: config server with git repo  is added in this branch and reactjs frontend app is also added

config github url: https://github.com/venkatram64/my-ms-centralized-config.git

please map in hosts file  
On Linux/Mac: /etc/hosts
On Windows: C:\Windows\System32\drivers\etc\hosts
127.0.0.1 content-service
127.0.0.1 post-service
127.0.0.1 zipkin-server

This entire application is dockerized and configuration is done using config server with git repo

order of starting services:
0. create a network
   docker network create shared-network
1. zipkin, to see the logs start the docker-compose.yml this is for to see the logs
2. follow the steps in post-config-server' s README.md 
3. follow the steps in service-registry-in-eureka' s README.md
4. follow the steps in post-service' s README.md
5. follow the steps in content-service' s README.md
6. follow the steps in post-api-gateway-service' s README.md
7. run the my-post-react-app to test from front end

this is the complete application

included features are spring security and two microservices post-service and content service




# Windows:
netstat -ano | findstr :3307
taskkill /PID <PID> /F


# Clean up Docker resources
docker system prune -a
docker volume prune

For Windows WSL2 users:
wsl --shutdown

create a network

docker network create shared-network


