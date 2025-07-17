
Step 1: maven package, which will build the jar file (post-service.jar)

mvn clean install -Dspring.profiles.active=dev  (not for local)

step 2:

build the image

below one is for my local kubernetes deployment

PS D:\MyProjects\MyWork\spring-work-micro-services\post-service> docker build -t explorejava/post-service:latest .

PS D:\MyProjects\MyWork\spring-work-micro-services\post-service> docker push explorejava/post-service:latest

docker the images

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


CREATE DATABASE IF NOT EXISTS `posts_db`;
USE `posts_db`;      
CREATE TABLE IF NOT EXISTS posts (
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
title VARCHAR(50) NOT NULL,
body VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);







