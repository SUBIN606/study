# 1주차 스터디

## 소규모 서비스와 대규모 서비스의 차이
대규모 웹 서비스란, 거대한 데이터를 처리해야만 하는 웹 서비스를 말한다.

### 확장성 확보, 부하분산 필요
- 스케일 아웃(scale-out): 서버를 횡으로 전개, 즉 서버의 역할을 분담하거나 대수를 늘림으로써 시스템의 전체적인 처리 능력을 높여 부하를 분산하는 방법
- 스케일 업(scale-up): 하드웨어의 성능을 높여 처리능력을 끌어올리는 방법

저가의 하드웨어를 횡으로 나열해서 확장성을 확보하는 것이 스케일 아웃 전략이다. 스케일 아웃 전략은 비용이 절감되는 반면, 다양한 문제가 발생한다. 예를들면 사용자의 요청을 어떻게 분배할 것인지, 데이터 동기화는 어떻게 할 것인지, 네트워크 통신의 지연시간(latency)을 어떻게 생각해볼 수 있을지 등 스케일 아웃에 동반하는 문제는 다방면에 걸쳐있다.

### 다중성 확보
시스템은 다중성을 지닌 구성, 즉 특정 서버가 고장 나거나 성능이 저하되더라도 서비스를 계속할 수 있는 구성으로 할 필요가 있다.

서버가 고장나더라도 혹은 급격하게 부하가 올라갈 경우에도 견딜 수 있는 시스템을 구성해야 한다. 

웹 서비스는 언제 어떠한 경우라도 고장에 대해 견고해야 한다.

### 효율적 운용 필요
여러 대의 서버가 무슨 역할을 하고 있는지, 어떤 상황에 있는지 파악하기 위해 효율적 운용을 수행해야만 한다.

### 개발자 수, 개발방법의 변화
대규모 서비스가 되면 여러 기술자가 역할을 분담하게 된다.

### 대규모 데이터량에 대한 대처
컴퓨터는 디스크(하드디스크, HDD)에서 데이터를 로드해서 메모리에 저장, 메모리에 저장된 데이터를 CPU가 패치(fetch)해서 특정 처리를 수행한다. 또한 메모리에서 패치된 명령은 보다 빠른 캐시(cache) 메모리에 캐싱된다. 이처럼 데이터는 디스크 -> 메모리 -> 캐시 메모리 -> CPU와 같이 몇 단계를 경유해서 처리되어 간다.

대이터량이 많아지면 처음부터 캐시 미스(cache miss)가 많이 발생하게 되고, 그 결과로 저속의 디스크로의 I/O가 많이 발생하게 된다. 디스크 I/O 대기에 들어선 프로그램은 ㅏ른 리소스가 비어 있더라도 읽기가 완료되기까지는 다음 처리를 수행할 수가 없다. 이것이 시스템 전체의 속도 저하를 초래한다.

대규모 웹 애플리케이션을 운용할 때 대부분의 어려움은 이러한 대규모 데이터 처리에 집중된다.

## 대규모 데이터 처리의 어려운 점
메모리 내에서 계산할 수 없다. 

메모리에 올리지 않으면 기본적으로 디스크를 계속 읽어가면서 검색하게 되어 좀처럼 발견할 수 없는 상태가 된다. 특정 규모를 넘어서면 데이터가 너무 많아 메모리 내에서 계산할 수 없으므로 디스크에 두고 검색하게 된다. 그리고 디스크는 메모리에 비해 상당히 느리다.

디스크는 헤드의 이동과 원반의 회전이라는 두 가지 물리적인 이동이 필요하다. 하지만 데이터가 메모리상에 있다면 탐색할 때 물리적인 동작 없이 실제 데이터 탐색 시의 오버헤드가 거의 없으므로 빠른 것이다.

## 규모조정, 확장성
웹 서비스에서는 고가의 빠른 하드웨어를 사서 성능을 높이는 스케일업 전략보다도 저가이면서 일반적인 성능의 하드웨어를 많이 나열해서 시스템 전체 성능을 올리는 스케일아웃 전략이 주류이다. 스케일아웃 전략이 더 나은 이유는 웹 서비스에 적합한 형태이고 비용이 저렴하다는 점과 시스템 구성에 유연성이 있다는 점이 포인트다.

스케일 아웃은 하드웨어를 횡으로 전개해서 확장성을 확보해나간다. 이때 CPU 부하의 확장성을 확보하기는 쉽다. 한편 DB 서버 측면에서는 I/O 부하가 걸린다. DB 확장성을 확보하는 것은 상당히 어렵다.

대규모 환경에서는 I/O 부하를 부담하고 있는 서버는 애초에 분산시키기 어려운데다가 디스크 I/O가 많이 발생하면 서버가 금새 느려지는 본질적인 문제가 있다.

## 대규모 데이터를 다루기 전 3대 전제지식
1. OS 캐시
2. 분산을 고려한 RDBMS 운용
3. 대규모 환경에서 알고리즘과 데이터 구조

## OS의 캐시 구조
OS는 메모리를 이용해서 디스크 액세스를 줄인다. Linux의 경우에는 페이지 캐시(page cache)나 파일 캐시(file cache), 버퍼 캐시(buffer cache)라고 하는 캐시 구조를 갖추고 있다.

OS는 '가상 메모리 구조'를 갖추고 있다. 가상 메모리 구조는 논리적인 선형 어드레스를 물리적인 물리 어드레스로 변환하는 것이다. 가상 메모리 구조가 존재하는 가장 큰 이유는 물리적인 하드웨어를 OS에서 추상화하기 위해서다.

OS라는 것은 메모리를 직접 프로세스로 넘기는 것이 아니라 일단 커널 내에서 메모리를 추상화하고 있다는 점이다. 이것이 가상 메모리 구조이다.

OS는 확보한 페이지를 메모리상에 계속 확보해두는 기능을 갖고 있다.

## I/O 부하를 줄이는 방법
- 경제적 비용과의 밸런스를 고려하여메모리를 설정한다. 단, 데이터 규모 보다 물리 메모리가 크면 전부 캐싱할 수 있다는 점을 생각해야 한다.
- 복수 서버로 확장시키기
  - CPU 부하분산에는 단순히 서버 대수를 늘린다
  - I/O 분산에는 국소성을 고려한다

## 국소성을 살리는 분산
액세스 패턴을 고려하여 분산하면, 캐싱할 수 없는 부분이 사라진다. 

- 파티셔닝: 한 대였던 DB 서버를 여러 대의 서버로 분할하는 방법
  - 테이블 단위 분할
  - 테이블 데이터 분할
- 요청 패턴을 '섬'으로 분할
  - 용도별로 시스템을 섬으로 나누는 방법
