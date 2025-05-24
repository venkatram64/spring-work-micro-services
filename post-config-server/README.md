NOTE: create a "shared-network"

docker network create shared-network

PS C:\Users\venka> docker network create shared-network

16660085182d807b30304eb08aff011b78eea873ce53f2e7b2550de982775c9f

PS C:\Users\venka> docker network ls
NETWORK ID     NAME             DRIVER    SCOPE
ec4554795786   bridge           bridge    local
d6f8da57cf33   host             host      local
c096f53783a7   kind             bridge    local
8cb89c70f77d   none             null      local
16660085182d   shared-network   bridge    local
PS C:\Users\venka>

docker network ls


docker network rm <<network name>>


Step 1: 
maven package, which will build the jar file (post-config-server.jar)

Step 2:
build the image

PS D:\MyProjects\MyWork\spring-work-micro-services\post-config-server> docker build -t explorejava/post-config-service .

                 or

rebuild the images before starting the docker compose

docker-compose up -d --build

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