# 아이템 8. finalizer와 cleaner 사용을 피하라

자바는 `finalizer`와 `cleaner` 두 가지의 객체 소멸자를 제공하고 있다. `finalizer`는 예측할 수 없고, 상황에 따라 위험할 수 있으며 오동작, 낮은 성능, 이식성 문제의 원인이 되기도 하므로 기본적으로 '쓰지 말아야'한다.

자바 9에서는 `finalizer`를 deprecated API로 지정하고 `cleaner`를 그 대안으로 소개했으나 `cleaner`역시 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.

> `finalizer`와 `cleaner`는 C++에서의 파괴자(destructor)와는 다른 개념임에 주의해야 한다. 자바에서는 접근할 수 없게 된 객체를 회수하는 역할을 가비지 컬렉터가 담당한다.

### 즉시 수행된다는 보장이 없으며, 수행 여부조차 보장되지 않는다.

객체에 접근할 수 없게 된 후 `finalizer`나 `cleaner`가 실행되기까지 얼마나 걸릴지 알 수 없으며 심지어 수행 여부조차 보장하지 않는다. `finalizer`나 `cleaner`가 수행되는 시점은 전적으로 가비지 컬렉터에게 달렸다. 따라서 **제때 실행되어야 할 작업이나, 상태를 영구적으로 수정하는 작업에서는 절대 `finalizer`나 `cleaner`에 의존해서는 안된다.**

### 예외가 무시되며 처리할 작업이 남아도 종료된다.

`finalizer` 동작 중 발생한 예외는 무시되며, 처리할 작업이 남았더라도 그 순간 종료된다. 객체가 마무리 되지 않은 상태로 남을 수 있으며, 다른 스레드가 훼손된 객체를 사용하려 한다면 어떻게 동작할지 예측할 수 없다.

> 그나마 `cleaner`를 사용하는 라이브러리는 자신의 스레드를 통제하기 때문에 이러한 문제가 발생하지 않는다.

### 심각한 성능 문제를 동반한다.

`finalizer`나 `cleaner`는 가비지 컬렉터의 효율을 떨어뜨린다.

### finalizer 공격에 노출되어 보안 문제를 일으킬 수 있다.

`finalizer`를 사용한 클래스는 `finalizer`공격에 노출된다. 생성자나 직렬화 과정에서 예외가 발생하면, 이 생성되다 만 객체에서 악의적인 하위 클래스의 `finalizer`가 수행될 수 있게 된다. 이 `finalizer`는 정적 필드에 자신의 참조를 할당하여 가비지 컬렉터가 수집하지 못하게 막을 수 있다. 이렇게 일그러진 객체가 만들어지고 나면, 이 객체의 메서드를 호출해 애초에는 허용되지 않았을 작업을 수행하는 건 일도 아니다.

`final`이 아닌 클래스를 `finalizer`공격으로부터 방어하려면 아무 일도 하지 않는 `finalize`메서드를 만들고 `final`로 선언하자.

## AutoCloseable을 구현하자

종료해야 할 자원을 담고 있는 객체의 클래스에서 `finalizer`나 `cleaner`를 대신해줄 방법은 `AutoCloseable`을 구현해주고, 클라이언트에서 인스턴스를 사용하고 난 뒤 `close`메서드를 호출하면 된다.

각 인스턴스는 자신이 닫혔는지를 추적하는 것이 좋다. `close`메서드에서 이 객체가 더 이상 유효하지 않음을 기록하고, 다른 메서드에서는 객체가 닫힌 후에 호출됐다면 예외를 던지는 것이다.

```java
public class SampleResource implements AutoCloseable {

  @Override
  public void close() throws RuntimeException {
    System.out.println("close");
  }

  public void hello() {
    System.out.println("hello");
  }

  @Override
  protected void finalize() throws Throwable {
    close();
  }

  public static void main(String[] args) {
    // try-with-resources
    try(SampleResource resource = new SampleResource()) {
      resource.hello();
    }
    // resource.close(); // 사용이 끝나면 close 메서드를 호출한다.
  }

}
```

## finalizer와 cleaner는 언제 쓰는가?

자원의 소유자가 `close`메서드를 호출하지 않는 것에 대비한 안전망 역할로 사용한다. 즉시 (혹은 끝까지) 호출되리라는 보장은 없지만, 클라이언트가 하지 않은 자원 회수를 늦게라도 해주는 것이 아예 안 하는 것보다는 낫다. 하지만 이런 안전망 역할로 `finalizer`를 작성하기 전에 그럴만한 값어치가 있는지 심사숙고 해야 한다.

네이티브 피어(native peer)와 연결된 객체에서 사용한다. 네이티브 피어란 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체로, 네이티브 피어는 자바 객체가 아니기 때문에 가비지 컬렉터는 그 존재를 알지 못한다. 따라서 자바 피어를 회수할 때 네이티브 객체까지 회수하지 못한다. `finalizer`나 `cleaner`가 나서서 처리해주어야 한다.

단, 성능 저하를 감당할 수 있고 네이티브 피어가 심각한 자원을 가지고 있지 않을 때에만 해당된다. 성능 저하를 감당할 수 없거나 네이티브 피어가 사용하는 자원을 즉시 회수해야 한다면 `close`메서드를 사용해야 한다.
