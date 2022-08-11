# Docker Cheat Sheet

> 도커 명령어나 문제 해결 내용 기록

### 컨테이너 이름 바꾸기

실행 중인 컨테이너 이름 변경도 가능하다.

```bash
$ docker rename CONTAINER NEW_NAME
```

## MariaDB

```bash
docker run -d -p 3306:3306 \
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
