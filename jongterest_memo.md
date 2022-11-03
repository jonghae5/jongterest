1. Vultr 서버 등록
2. Portatiner 설치
docker volume create portainer_data <br>
docker run -d -p 9000:9000 --name portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ee:latest <br>
docker container ls <br>
<br>
아이디 : admin 
비밀번호 : @Skfgnxh1234
라이센스 발급 
2-+mXVz856GRBOGhiq6XCOZYOvDMCo/GJWAOz+UefT9MphfF7xXoUx4YW5FZ27ielmLgUMG701Gq7gcIsw4hG/rg==

### Filzilla 연결 방법
port 22 SFTP 사용

###NGINX
nginx.conf 파일 만들기
/data/images/
> images volume 과 연결
filezilla에 업로드
/etc/nginx/nginx.conf
> home/nginx/nginx.conf 생성
> BIND 시켜준다.

## MY SQL
MYSQL
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=skfgnxh1 -d -p 3306:3306 mysql
MYSQL:8.0.30

### DB 생성
docker run —name mysql-container -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=jongterest -e MYSQL_USER=user -e MYSQL_PASSWORD=user -d mysql:8.0.30
생성시에 volume 도 넣어준다.

volume : database : /opt/lib/mysql
docker exec -it mysql-container bash
mysql -u root -p
skfgnxh1
create database jongterest;
drop database jongterest;

### MYSQL 권한주기
create user 'jonghae5'@'%' identified by 'skfgnxh1';
grant all privileges on jongterest.* to 'jonghae5'@'%';
flush privileges;

## 주의 MYSQL
datasource의 url 부분에서 호스트 부분 설정을 잘 해주어야함.
현재 호스트가 mysql-container로 되어 있는데 이 호스트명은 앞으로 도커환경에서 mysql이 구동되는 컨테이너의 이름


## Docker 사용법 (Pull, Push)
docker login
dhwhdgo2368
@skfgnxh12
./gradlew clean build -x test
docker build -t {docker hub 계정명}/{docker hub repository명} .
docker build -t dhwhdgo2368/jongterest-container .
docker push dhwhdgo2368/jongterest-container
docker pull dhwhdgo2368/jongterest-container
sudo nohup docker run -p 8081:8080 souljit1/test-repo > nohup.out 2>&1 &

docker network create springboot-mysql-net

## Spring / DB 연결
같은 Network 이용

## Spring static file 연결
/home/images/ > addResourceResolver
Docker volume 생성 > /var/lib/docker/volumes/
file.dir Path 변경


### Dockerfile
FROM openjdk:11
WORKDIR /home/jongterest/
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]


// ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]
// => 설정파일을 분리해서 사용할 때
// java -jar -Dspring.profiles.active=prod app.jar


### Docker stack
ㅇ Local 개발 환경에서 관리하기 좋은 것 > Docker compose
반복적 Configuration
Container가 죽었을 때..
ㅇ Docker Swarm / k8s
서버(Node)를 클러스터링

docker swarm init (manager node)
> portainer에 swarm secret service 생성