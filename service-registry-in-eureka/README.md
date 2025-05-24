
Step 1: maven package, which will build the jar file (service-registry-in-eureka.jar)

step 2:

build the image

PS D:\MyProjects\MyWork\spring-work-micro-services\service-registry-in-eureka> docker build -t explorejava/service-registry-in-eureka .

                 or

rebuild the images before starting the docker compose

docker-compose up -d --build

docker the images

docker images

------------------------------

to remove the images


docker rmi explorejava/service-registry-in-eureka

clean up unused images

docker image prune -a

removes all unused images, containers, networks, and volumes
docker system prune -a
--------------------------------

Step 3: run the docker compose as a background process

PS D:\MyProjects\MyWork\spring-work-micro-services\service-registry-in-eureka> docker-compose up -d

check the running containers

docker-compose ps

docker ps

to see the logs

docker-compose logs
or

docker-compose logs -f

to stop above ctrl+c

Step 4: to stop the docker compose

PS D:\MyProjects\MyWork\spring-work-micro-services\service-registry-in-eureka> docker-compose down

docker-compose down --rmi all -v --remove-orphans

docker network rm -f shared-network

docker logs post-config-server

docker-compose logs -f service-registry-in-eureka

docker exec -it service-registry-in-eureka curl http://post-config-server:8088

Check Exposed Ports:

docker ps --format "table {{.Names}}\t{{.Ports}}"