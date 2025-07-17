
Step 1: 
maven package, which will build the jar file (post-config-server.jar)

mvn clean install -Dspring.profiles.active=dev  (not for local)

Step 2:
build the image


below one is for my local kubernetes deployment

PS D:\MyProjects\MyWork\spring-work-micro-services\post-config-server> docker build -t explorejava/post-config-service:1.0.0 .

PS D:\MyProjects\MyWork\spring-work-micro-services\post-config-server> docker push explorejava/post-config-service:1.0.0

docker the images

docker images

------------------------------

to remove the images


docker rmi explorejava/post-config-service

clean up unused images

docker image prune -a

removes all unused images, containers, networks, and volumes
docker system prune -a
--------------------------------

Step 3: run the docker compose as a background process

PS D:\MyProjects\MyWork\spring-work-micro-services\post-config-server> docker-compose up -d

docker logs post-config-server

check the running containers

docker-compose ps

docker ps

to see the logs

docker-compose logs
or

docker-compose logs -f

 to stop above ctrl+c

Step 4: to stop the docker compose

PS D:\MyProjects\MyWork\spring-work-micro-services\post-config-server> docker-compose down

to see the logs of mysql

docker logs content-mysql