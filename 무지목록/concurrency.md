## 프로세스와 스레드
동시 프로그래밍에서는 프로세스와 스레드라는 두 개의 기본 실행 단위를 가진다. Java에서 동시 프로그래밍은 대부분 스레드와 관련이 있다.

### 프로세스
프로세스는 독립적인 실행 환경을 갖는다. 그리고 자체 메모리 공간이 있다.

대부분의 JVM 구현은 단일 프로세스로 실행되며, `ProcessBuilder` 객체를 사용해 추가 프로세스를 만들 수 있다.

### 스레드
프로세스와 스레드 모두 실행 환경을 제공하나, 새 스레드를 생성하는 데 필요한 리소스가 프로세스를 새로 생성하는 것 보다 적게 든다.

스레드는 프로세스 내에 존재하며, 모든 프로세스는 하나 이상의 스레드를 갖는다. 스레드는 메모리와 프로세스의 리소스를 공유한다. 이것은 효율적이지만 잠재적으로 문제를 발생할 수 있다.

멀티 스레드 실행은 Java 플랫폼의 필수 기능으로, 모든 애플리케이션은 적어도 하나의 스레드가 있다.
애플리케이션 프로그래머의 관점에서는 메인 스레드라는 단 하나의 스레드로 시작한다.
> 자바의 모든 애플리케이션은 메인 스레드가 `main()` 메서드를 실행하면서 시작된다.

> 스레드는 컨텍스트 스위칭 비용(스레드를 전환할 때 드는 비용)이 발생한다.

## Thread Objects
각 스레드는 `Thread` 클래스의 인스턴스와 연결된다. `Thread` 객체를 사용하여 동시 애플리케이션을 만드는 기본 전략은 두 가지가 있다.
- 스레드 생성 및 관리를 직접 제어하려면 애플리케이션에서 비동기 작업을 시작해야 할 때마다 `Thread`를 인스턴스화 한다.
- 나머지 애플리케이션에서 스레드 관리를 추상화하려면 애플리케이션의 작업을 `executor`로 전달한다.

`Thread`를 인스턴스화 할 때, 무조건 스레드에서 실행될 코드를 작성해야 한다. 이를 수행하는 방법은 두 가지가 있다.

### `Runnable`
`Runnable` 인터페이스는 `run()`이라는 하나의 메서드를 정의한다. 그리고 `run()` 메서드는 스레드에서 실행할 코드를 포함해야 한다. 그리고 `Runnable` 객체를 `Thread`의 생성자로 전달한다.

`Runnable`은 작업 내용을 가지고 있는 객체로, 실제 스레드가 아니다. 구현체를 `Thread`의 생성자로 전달해서 호출해야 비로소 작업 스레드가 생성되고 `start()` 메서드를 호출하면 실행된다.

``` java
public class HelloRunnable implements Runnable {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new Thread(new HelloRunnable())).start();
    }
}
```

### `Thread`
`Thread` 클래스는 자체적으로 `Runnable`을 구현하지만, `run()`메소드는 아무것도 하지 않는다. 
``` java
public class HelloThread extends Thread {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
    }
}
```

둘 중 어떤 방법을 사용해도 상관없지만, `Runnable`은 작업을 실행하는 객체에서 작업을 분리할 수 있다. 이 접근 방식이 더 유연할 뿐만 아니라 고급 스레드 관리 API에 적용할 수 있다.

`Thread` 클래스는 스레드 관리에 유용한 여러 메서드를 정의한다. 

## 동기화된 메서드
Java는 `synchronized`메서드와 `synchronized` 문을 제공한다. 

메서드를 동기화하려면 `synchronized` 키워드를 메서드 선언에 추가하기만 하면 된다.
> 단, 생성자는 동기화할 수 없다. `synchronized` 키워드를 생성자에서 사용하는 것은 구문 오류이다.

``` java
public class SynchronizedCounter {
    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}
```

---

- [Java Tutorial: Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html)
