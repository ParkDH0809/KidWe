How can you build and deployment our service? 

common, mongo-back, front-end를 각 방식으로 build 한 후,
Dockerfile(Back-JPA-Docker, Back-MongoDB-Docker)가 들어있는 build_filds로 .jar, /dist로 copy한 뒤 docker-compose로 container 실행(jenkins, nginx, bareun은 개별적으로 실행)

[Version]  
nginx: 1.27.0

node:  20.15.0
yarn : berry(4.3.1)

Java: 17.0.2(openJDK 17)
Spring boot: 3.3.1

MongoDB: 7.0.12
MySQL: 8.0.33
redis: 7.4.0

jenkins 2.470

[Order]
1. be-develop
- git pull origin be-develop

- chmod + gradlew
- ./gradlew bootJar -x test

2. fe-develop
- npm install yarn
- yarn install 
- yarn build

3. docker-compose
- docker-compose up --build -d

[Information]  
MongoDB:  
  - username: ssafy 
  - password: ssafy  
MySQL:  
  - username: ssafy  
  - password: ssafy  
redis:  
  - password: ssafy  
