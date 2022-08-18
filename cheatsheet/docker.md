# Docker Cheat Sheet

> 도커 명령어나 문제 해결 내용 기록

### 컨테이너 이름 바꾸기

실행 중인 컨테이너 이름 변경도 가능하다.

```bash
$ docker rename CONTAINER NEW_NAME
```

## MariaDB

```bash
$ docker run -d -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD= \
  -e MYSQL_DATABASE= \
  -e MYSQL_USER= \
  -e MYSQL_PASSWORD= \
  -v mariadb-volume:/var/lib/mysql \
  --network [네트워크명] \
  --name mariadb \
  mariadb
```

### sql파일 실행하기

```bash
docker exec -i [container ID] mariadb -uroot -p[rootPassword] < init.sql
```

## MongoDB

```bash
$ docker run -d -p 27017:27017 \
    -e MONGO_INITDB_ROOT_USERNAME=root \
    -e MONGO_INITDB_ROOT_PASSWORD=password \
    -e MONGO_INITDB_DATABASE=data \
    -v [초기화 스크립트 경로]:/docker-entrypoint-initdb.d/mongo-init.js:ro \
    -v [로컬 경로]:/[vm 경로] \
    --network [네트워크명] \
    --name mongodb
    mongo
```

MongoDB 초기화 스크립트 작성 예시

```javascript
db = db.getSiblingDB("admin");

db.auth("root", "password");

db = db.getSiblingDB("data");

db.createUser({
  user: "dataUser", // 데이터베이스에 접근 할 수 있는 유저명
  pwd: "password", // 패스워드
  roles: [
    {
      role: "readWrite",
      db: "data", // 데이터베이스명
    },
  ],
});
```
