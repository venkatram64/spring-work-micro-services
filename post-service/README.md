
Step 1: maven package, which will build the jar file (post-service.jar)

step 2:

build the image

PS D:\MyProjects\MyWork\spring-work-micro-services\post-service> docker build -t explorejava/post-service .

step 3: login to docker hub

docker login

step 4: push the image to docker hub

docker push explorejava/post-service
                 or

rebuild the images before starting the docker compose

docker-compose up -d --build

to see docker images

docker images

------------------------------

to remove the images


docker rmi explorejava/post-service

clean up unused images

docker image prune -a

removes all unused images, containers, networks, and volumes
docker system prune -a
--------------------------------

Step 3: run the docker compose as a background process

PS D:\MyProjects\MyWork\spring-work-micro-services\post-service> docker-compose up -d

check the running containers

docker-compose ps

docker ps

to see the logs

docker-compose logs
or

docker-compose logs -f

to stop above ctrl+c

Step 4: to stop the docker compose

PS D:\MyProjects\MyWork\spring-work-micro-services\post-service> docker-compose down

docker-compose down --rmi all -v --remove-orphans


docker exec -it post-service curl http://post-config-server:8088


docker logs post-config-server


to see the logs of mysql

docker logs content-mysql


docker exec -it content-service \
mysql -hcontent-mysql -uroot -proot -e "SHOW DATABASES;"


docker volume prune


docker volume ls

docker volume prune -a 







