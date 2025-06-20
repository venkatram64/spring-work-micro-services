Note: config server with git repo  is added in this branch and reactjs frontend app is also added

config github url: https://github.com/venkatram64/k8s-ms-centralized-config.git

please map in hosts file  
On Linux/Mac: /etc/hosts
On Windows: C:\Windows\System32\drivers\etc\hosts
127.0.0.1 content-service
127.0.0.1 post-service
127.0.0.1 zipkin-server

This entire application is dockerized and configuration is done using config server with git repo

order of starting services:

1. zipkin, to see the logs start the zipkin-server.yaml 

    to start the deployment

   PS D:\MyProjects\MyWork\spring-work-micro-services> kubectl apply -f  .\zipkin-server.yaml

   deployment.apps/zipkin-server-deploy created

   service/zipkin-server created

   to stop the deployment.

   PS D:\MyProjects\MyWork\spring-work-micro-services> kubectl delete -f  .\zipkin-server.yaml

   deployment.apps "zipkin-server-deploy" deleted

   service "zipkin-server" deleted

   PS D:\MyProjects\MyWork\spring-work-micro-services>


   to see the logs

   PS D:\MyProjects\MyWork\spring-work-micro-services> kubectl logs pod/zipkin-server-deploy-94664865d-cnhcb

2. follow the steps in post-config-server' s README.md  

   clean the maven
   mvn clean
   mvn package

   run the 
   Dockerfile which will build the image

To start the deployment

PS D:\MyProjects\MyWork\spring-work-micro-services\post-config-server\k8s> kubectl apply -f  .\post-config-server.yaml

deployment.apps/post-config-server-deploy created

service/post-config-server created

to stop the deployment

PS D:\MyProjects\MyWork\spring-work-micro-services\post-config-server\k8s> kubectl delete -f  .\post-config-server.yaml

deployment.apps "post-config-server-deploy" deleted

service "post-config-server" deleted


3. follow the steps in service-registry-in-eureka' s README.md

To start the deployment

PS D:\MyProjects\MyWork\spring-work-micro-services\service-registry-in-eureka\k8s> kubectl apply -f  .\service-registry-in-eureka.yaml

deployment.apps/service-registry-in-eureka-deploy created

service/service-registry-in-eureka created

PS D:\MyProjects\MyWork\spring-work-micro-services\service-registry-in-eureka\k8s>

to stop the deployment

PS D:\MyProjects\MyWork\spring-work-micro-services\service-registry-in-eureka\k8s> kubectl delete -f  .\service-registry-in-eureka.yaml

deployment.apps "service-registry-in-eureka-deploy" deleted

service "service-registry-in-eureka" deleted


4. follow the steps in post-service' s README.md

PS D:\MyProjects\MyWork\spring-work-micro-services\post-service\k8s> kubectl apply -f .

deployment.apps/posts-mysql-deploy created

service/posts-mysql created

configmap/mysql-init-sql created

deployment.apps/post-service-deploy created

service/post-service created

PS D:\MyProjects\MyWork\spring-work-micro-services\post-service\k8s>


PS D:\MyProjects\MyWork\spring-work-micro-services\post-service> kubectl delete -f  .\k8s\

deployment.apps "posts-mysql-deploy" deleted

service "posts-mysql" deleted

configmap "mysql-init-sql" deleted

deployment.apps "post-service-deploy" deleted

service "post-service" deleted

PS D:\MyProjects\MyWork\spring-work-micro-services\post-service>



5. follow the steps in content-service' s README.md

PS D:\MyProjects\MyWork\spring-work-micro-services\content-service\k8s> kubectl apply -f .

deployment.apps/content-mysql-deploy created

configmap/mysql-init-sql configured

service/content-mysql created

deployment.apps/content-service-deploy created

service/content-service created

PS D:\MyProjects\MyWork\spring-work-micro-services\content-service\k8s>

PS D:\MyProjects\MyWork\spring-work-micro-services\content-service\k8s> kubectl delete -f .

deployment.apps "content-mysql-deploy" deleted

configmap "mysql-init-sql" deleted

service "content-mysql" deleted

deployment.apps "content-service-deploy" deleted

service "content-service" deleted

PS D:\MyProjects\MyWork\spring-work-micro-services\content-service\k8s>

6. follow the steps in post-api-gateway-service' s README.md

   PS D:\MyProjects\MyWork\spring-work-micro-services\post-api-gateway-service\k8s> kubectl apply -f  .\post-api-gateway-service.yaml

   deployment.apps/post-api-gateway-deploy created
 
   service/post-api-gateway-service created

   PS D:\MyProjects\MyWork\spring-work-micro-services\post-api-gateway-service\k8s>

   PS D:\MyProjects\MyWork\spring-work-micro-services\post-api-gateway-service\k8s> kubectl delete -f  .\post-api-gateway-service.yaml
 
   deployment.apps "post-api-gateway-deploy" deleted
 
   service "post-api-gateway-service" deleted   

7. run the my-post-react-app to test from front end

this is the complete application

included features are spring security and two microservices post-service and content service

kubectl logs -l app=content-mysql
-----------------------------------------------------------------------

PS D:\MyProjects\MyWork\spring-work-micro-services> kubectl port-forward service/service-registry-in-eureka 8761:8761

Forwarding from 127.0.0.1:8761 -> 8761

Forwarding from [::1]:8761 -> 8761

Handling connection for 8761

Handling connection for 8761

Handling connection for 8761


-----------------------------

see below all the services are started

PS D:\MyProjects\MyWork\spring-work-micro-services> kubectl get all

NAME                                                     READY   STATUS    RESTARTS        AGE

pod/content-mysql-deploy-6f9dfc7fc5-8gx8h                1/1     Running   0               6h50m

pod/content-service-deploy-685dffd9b8-fj85x              1/1     Running   1 (6h48m ago)   6h50m

pod/post-api-gateway-deploy-7856d766df-7nj66             1/1     Running   0               49s

pod/post-config-server-deploy-b95895df7-rrz4m            1/1     Running   0               7h27m

pod/post-service-deploy-57d7dbbf5f-5gvv8                 1/1     Running   1 (6h49m ago)   6h50m

pod/posts-mysql-deploy-7c7cb99655-xjcsf                  1/1     Running   0               6h50m

pod/service-registry-in-eureka-deploy-54d84cc67d-cmdp2   1/1     Running   0               6h51m

pod/zipkin-server-deploy-94664865d-nqpmz                 1/1     Running   0               6h52m


NAME                                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE

service/content-mysql                ClusterIP   10.96.72.193    <none>        3306/TCP   6h50m

service/content-service              ClusterIP   10.96.109.54    <none>        8082/TCP   6h50m

service/kubernetes                   ClusterIP   10.96.0.1       <none>        443/TCP    3d4h

service/post-api-gateway-service     ClusterIP   10.96.112.229   <none>        8060/TCP   49s

service/post-config-server           ClusterIP   10.96.164.151   <none>        8088/TCP   7h27m

service/post-service                 ClusterIP   10.96.254.149   <none>        8081/TCP   6h50m

service/posts-mysql                  ClusterIP   10.96.153.175   <none>        3306/TCP   6h50m

service/service-registry-in-eureka   ClusterIP   10.96.63.233    <none>        8761/TCP   6h51m

service/zipkin-server                ClusterIP   10.96.132.15    <none>        9411/TCP   6h52m


NAME                                                READY   UP-TO-DATE   AVAILABLE   AGE

deployment.apps/content-mysql-deploy                1/1     1            1           6h50m

deployment.apps/content-service-deploy              1/1     1            1           6h50m

deployment.apps/post-api-gateway-deploy             1/1     1            1           49s

deployment.apps/post-config-server-deploy           1/1     1            1           7h27m

deployment.apps/post-service-deploy                 1/1     1            1           6h50m

deployment.apps/posts-mysql-deploy                  1/1     1            1           6h50m

deployment.apps/service-registry-in-eureka-deploy   1/1     1            1           6h51m

deployment.apps/zipkin-server-deploy                1/1     1            1           6h52m


NAME                                                           DESIRED   CURRENT   READY   AGE

replicaset.apps/content-mysql-deploy-6f9dfc7fc5                1         1         1       6h50m

replicaset.apps/content-service-deploy-685dffd9b8              1         1         1       6h50m

replicaset.apps/post-api-gateway-deploy-7856d766df             1         1         1       49s

replicaset.apps/post-config-server-deploy-b95895df7            1         1         1       7h27m

replicaset.apps/post-service-deploy-57d7dbbf5f                 1         1         1       6h50m

replicaset.apps/posts-mysql-deploy-7c7cb99655                  1         1         1       6h50m

replicaset.apps/service-registry-in-eureka-deploy-54d84cc67d   1         1         1       6h51m

replicaset.apps/zipkin-server-deploy-94664865d                 1         1         1       6h52m


PS D:\MyProjects\MyWork\spring-work-micro-services>
--------------------------------

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


---------------------------------------------------------




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




