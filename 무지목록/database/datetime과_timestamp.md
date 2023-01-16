# Why - 왜 이걸 알아보려고 하는가?
Editall 애플리케이션 개발 중 `created_at`컬럼의 데이터 타입을 `DATETIME`과 `TIMESTAMP` 중에 무엇으로 해야할지 고민됐다.

> node.js 서버에서 knex.js를 사용했는데, 여기서는 `created_at`컬럼의 데이터 타입을 지정하지 않으면 기본적으로 TIMESTAMP로 지정해서 만들어줬다. 그런데 JPA에서는 DATETIME으로 지정해서 만들어준다.

# What - DATETIME
MySQL의 날짜 타입은 칼럼 자체에 타임존 정보가 저장되지 않으므로 `DATETIME`이나 `DATE`타입은 현재 DBMS 커넥션의 타임존과 관계 없이 클라이언트로부터 입력된 값을 그대로 저장하고 조회할 때도 변환없이 그대로 출력한다.

# What - TIMESTAMP
`TIMESTAMP`는 항상 UTC 타임존으로 저장되므로 타임존이 달라져도 값이 자동으로 보정된다. 즉, `TIMESTAMP` 칼럼의 값은 현재 클라이언트(커넥션)의 타임존에 맞게 변환된다.

# How - 그래서 어떻게 할까
``` java
// JVM 타임존과 MySQL 서버의 타임존이 다른 경우
System.setProperty("user.timezone", "America/Los_Angeles"); // LA

Connection conn = DriverManager.getConnection(
  "jdbc:mysql://127.0.0.1:3306?serverTimezone=Asia/Seoul",  // Seoul
  "id",
  "password"
);

Statement stmt = conn.createStatement();

ResultSet res = stmt.executeQuery(
  "select fd_datetime, fd_timestamp from test.tb_timezone"
);

if(res.next()) {
  System.out.println("fd_datetime : " + res.getTimestamp("fd_datetime"));
  System.out.println("fd_timestamp : " + res.getTimestamp("fd_timestamp"));
}

// 출력값
// fd_datetime: 2020-09-09 17:25:23.0
// fd_timestamp: 2020-09-09 17:25:23.0
```
MySQL 서버의 칼럼 타입이 `TIMESTAMP`이든 `DATETIME`이든 관계없이, JDBC 드라이버는 날짜 및 시간 정보를 **MySQL 타임존에서 JVM 타임존으로 변환해서 출력**한다.
> ResultSet 클래스에서 MySQL 서버의 DATETIME 값을 온전히 가져올 수 있느 함수가 `getTimestamp()`뿐이기도 해서 모두 타임존 변환이 된 것이기도 하다.

조회뿐만 아니라 응용프로그램에서 데이터베이스로 날짜 및 시간 데이터를 저장할 때도 동일한 규칙이 저장된다.

최대한 응용 프로그램에서 시간 정보를 강제로 타임존 변환을 하거나 MySQL 서버의 SQL 문장으로 타임존 변환을 하지 않도록 해야 한다.

### MySQL 서버의 기본 타임존 확인 / 변경
``` sql
-- 타임존 변경
SET time_zone='America/Los_Angeles';

-- 타임존 확인
SHOW VARIABLES LIKE '%time_zone%';
```
`system_time_zone` 시스템 변수는 MySQL 서버의 타임존을 의미한다. 일반적으로 이 값은 운영체제의 타임존을 그대로 상속받는다.
시스템 타임존은 운영체제 계정의 환경 변수(일반적으로 TZ)를 변경하거나, `mysqld_safe`를 시작할 때 `--timezone` 옵션을 이용해 변경할 수 있다.

`time_zone` 시스템 변수는 MySQL 서버로 연결하는 모든 클라이언트 커넥션의 기본 타임존을 의미한다. 커넥션의 타임존은 응용 프로그램 코드에 의해 다른 값으로 언제든지 변경될 수 있다. `time_zone` 시스템 변수에 아무것도 설정하지 않으면 "SYSTEM"으로 자동 설정되는데, `system_time_zone`의 값을 그대로 사용한다는 의미다.

실제 MySQL 서버에 접속된 커넥션에서 시간 관련 처리(NOW() 함수나 TIMESTAMP 칼럼 초기화 등)을 할 때는 `time_zone`시스템 변수의 영향만 받는다.


## 결론
MySQL 5.6 버전부터는 `DATETIME` 타입과 `TIMESTAMP` 타입 사이에 커넥션의 `time_zone` 시스템 변수의 타임존으로 저장할지, UTC로 저장할지의 차이만 남고 모든 것이 같다.

둘 중에 어떤 타입을 해야 할지 결정하는 것 보다 타임존을 통일시키는 것이 더 중요할 것 같다.
---
참고
- Real MySQL vol2

