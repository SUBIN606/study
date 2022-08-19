# 11장. 웹 공격 기술

## XSS(Cross-Site Scripting)

취약성이 있는 웹 사이트를 방문한 사용자의 브라우저에서 부정한 HTML 태그나 JavsScript 등을 동작시키는 공격
동적으로 HTML을 생성하는 부분에서 취약성이 발생할 수 있음
공격자가 작성한 스크립트가 함정이 되고 유저의 브라우저 상에서 움직이는 수동적 공격

가짜 입력 폼 등에 의해 유저의 개인정보를 도둑맞거나, 스크립트에 의해 유저의 쿠키 값이 도둑맞거나 피해자가 의도하지 않는 리퀘스트가 송신된다. 가짜 문장이나 이미지 등이 표시되기도 한다.

## CSRF(Cross-Site Request Forgeries)

인증된 유저가 의도하지 않는 개인 정보나 설정 정보 등을 공격자가 설치해 둔 함정에 의해 어떤 상태를 갱신하는 처리를 강제로 실행시키는 공격

## 패스워드 크래킹

### 무차별 대입 공격(Brute-force Attack)

모든 키의 집합 키 공간(keyspace), 즉 비밀 번호 시스템에서 취할 수 있는 모든 패스워드 후보를 시험해서 인증을 돌파하는 공격
모든 후보를 시험하므로 반드시 패스워드를 해독할 수 있는 공격이지만 키 공간이 클 경우에는 해독하는데 몇 년 혹은 몇 천 년이 걸릴 수도 있기 때문에 현실적으로 공격이 성공하지 못할 수 도 있음

### 사전 공격(Dictionary Attack)

사전에 패스워드 후보(사전)을 준비해두고 그것을 시험해 봄으로써 인증을 돌파하는 공격
무차별 대입 공격에 비해 시험할 후보의 수가 적으므로 공격에 필요한 시간을 단축할 수 있지만, 사전 중에 올바른 패스워드가 없으면 해독할 수 없음
공격의 성공 여부는 사전에 따라 좌우됨

### 레인보우 테이블

레인보우 테이블은 평문과 그에 대응하는 해시 값으로 구성된 데이터베이스 테이블로 사전에 거대한 테이블을 만들어 무차별 대입 공격/사전 공격 등에 걸리는 시간을 단축하는 테크닉
레인보우 테이블에서 해시 값을 검색해 이에 맞는 평문을 이끌어 낼 수 있음