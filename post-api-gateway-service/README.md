Step 1: maven package, which will build the jar file (post-api-gateway-service.jar)

mvn clean install -Dspring.profiles.active=dev  (not for local)

step 2:

build the image

below one is for my local kubernetes deployment
PS D:\MyProjects\MyWork\spring-work-micro-services\post-api-gateway-service> docker build -t explorejava/post-api-gateway-service:latest .

PS D:\MyProjects\MyWork\spring-work-micro-services\post-api-gateway-service> docker push explorejava/post-api-gateway-service:latest


to see the images



PS D:\MyProjects\MyWork\spring-work-micro-services\post-api-gateway-service> docker images


PS D:\MyProjects\MyWork\spring-work-micro-services> kubectl get ingress
NAME              CLASS    HOSTS   ADDRESS     PORTS   AGE
my-post-ingress   <none>   *       localhost   80      43h
PS D:\MyProjects\MyWork\spring-work-micro-services> kubectl describe ingress my-post-ingress
Name:             my-post-ingress
Labels:           <none>
Namespace:        default
Address:          localhost
Ingress Class:    <none>
Default backend:  <default>
Rules:
Host        Path  Backends
  ----        ----  --------
*
          /   post-api-gateway-service:8060 (10.244.2.5:8060)
Annotations:  <none>
Events:       <none>
PS D:\MyProjects\MyWork\spring-work-micro-services>