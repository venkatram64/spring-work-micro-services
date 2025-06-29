Step 1: maven package, which will build the jar file (post-api-gateway-service.jar)

step 2:

build the image

PS D:\MyProjects\MyWork\spring-work-micro-services\post-api-gateway-service> docker build -t explorejava/post-api-gateway-service .

step 3: login to docker hub
docker login

step 4: push the image to docker hub
docker push explorejava/post-api-gateway-service

      or

docker-compose up -d (to start the containers)

docker-images down (to stop the containers)

s