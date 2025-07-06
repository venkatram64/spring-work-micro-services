# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)

---

added modules

npm i bootstrap

npm i react-router-dom@^5.2.0

npm i react-quill

npm i react-render-html --force
npm i moment

added extension for html to jsx
added emment in settings
"emmet.includeLanguages": {
"javascript":"javascriptreact"
}

to start the application

npm run start



PS D:\MyProjects\MyWork\spring-work-micro-services> docker ps
CONTAINER ID   IMAGE                  COMMAND                  CREATED      STATUS        PORTS                                                                   NAMES
ee58ba160750   kindest/node:v1.32.2   "/usr/local/bin/entr…"   4 days ago   Up 23 hours   0.0.0.0:443->443/tcp, 0.0.0.0:8080->80/tcp, 127.0.0.1:58514->6443/tcp   dev-cluster-control-plane
013539776d84   kindest/node:v1.32.2   "/usr/local/bin/entr…"   4 days ago   Up 23 hours                                                                           dev-cluster-worker2
561e08de0fd4   kindest/node:v1.32.2   "/usr/local/bin/entr…"   4 days ago   Up 23 hours                                                                           dev-cluster-worker
PS D:\MyProjects\MyWork\spring-work-micro-services>


see the PORTS section, localhost/0.0.0.0:8080 so I used in .env file 
REACT_APP_API_URL='http://localhost:8080/'

----------------creating a docker image-----
I am unable to add manifest file for kubernetes, I know the problem with api gateway
Dockerfile

step 1:
docker build -t explorejava/my-post-react-app:latest .

step 2:
docker login

step 3:
docker push explorejava/my-post-react-app:latest

latest: digest: sha256:d6c5831fc2fb6a24747032cd8f8554eb038b3ecf6d2eed3ca4db3935536c96ce size: 2200
PS D:\MyProjects\MyWork\spring-work-micro-services\my-post-react-app> kubectl get pods -n ingress-nginx
NAME                                        READY   STATUS    RESTARTS        AGE
ingress-nginx-controller-684d55c96d-jv64k   1/1     Running   3 (4d23h ago)   20d
PS D:\MyProjects\MyWork\spring-work-micro-services\my-post-react-app> kubectl get svc -n ingress-nginx
NAME                                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)                      AGE
ingress-nginx-controller             NodePort    10.96.118.224   <none>        80:32297/TCP,443:30088/TCP   20d
ingress-nginx-controller-admission   ClusterIP   10.96.168.198   <none>        443/TCP                      20d
PS D:\MyProjects\MyWork\spring-work-micro-services\my-post-react-app>

To run app after image is pushed

step 3:

kubectl apply -f ./k8s

http://localhost:8080/  (but not working)