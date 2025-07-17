
Step 1: maven package, which will build the jar file (content-service.jar)

mvn clean install -Dspring.profiles.active=dev  (not for local)

step 2:

build the image


below one is for my local kubernetes deployment
PS D:\MyProjects\MyWork\spring-work-micro-services\content-service> docker build -t explorejava/content-service:latest .

PS D:\MyProjects\MyWork\spring-work-micro-services\content-service> docker push explorejava/content-service:latest
                 or

docker the images

docker images

------------------------------

to remove the images


docker rmi explorejava/content-service

clean up unused images

docker image prune -a

removes all unused images, containers, networks, and volumes
docker system prune -a
--------------------------------

Step 3: run the docker compose as a background process

PS D:\MyProjects\MyWork\spring-work-micro-services\content-service> docker-compose up -d

docker logs content-service

check the running containers

docker-compose ps

docker ps

to see the logs

docker-compose logs
or

docker-compose logs -f

to stop above ctrl+c

Step 4: to stop the docker compose

PS D:\MyProjects\MyWork\spring-work-micro-services\content-service> docker-compose down


to see the logs of mysql

docker logs content-mysql


docker volume prune



CREATE DATABASE IF NOT EXISTS `content_db`;
USE `content_db`;    
CREATE TABLE IF NOT EXISTS `users` (
`id` INT NOT NULL AUTO_INCREMENT,
`first_name` VARCHAR(50) NOT NULL,
`last_name` VARCHAR(50) NOT NULL,
`email` VARCHAR(100) UNIQUE NOT NULL,
`password` VARCHAR(255) NOT NULL,
`role` VARCHAR(50) NOT NULL,
`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`modified_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
);

