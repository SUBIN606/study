# 아이템 7. 다 쓴 객체 참조를 해제하라

**가비지 컬렉터가 다 쓴 객체를 알아서 회수해 간다고 해서 메모리 관리에 더 이상 신경쓰지 않아도 되는 것은 아니다.**

아래 코드의 어딘가에서 메모리 누수가 발생하고 있다. 이 스택을 사용하는 프로그램을 오래 실행하다보면 점차 가비지 컬렉션 활동과 메모리 사용량이 늘어나 결국 성능이 저하될 것이다.

```java
public class Stack {
  private Object[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack() {
    elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(Object e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop() {
    if(size == 0) {
      throw new EmptyStackException();
    }
    return elements[--size];
  }

  // 원소를 위한 공간을 적어도 하나 이상 확보한다.
  // 배열의 크기를 늘려야 할 때마다 대략 두 배씩 늘린다.
  private void ensureCapacity() {
    if(elements.length == size) {
      elements = Arrays.copyOf(elements, 2 * size + 1);
    }
  }

}
```

위의 코드에서는 스택이 커졌다가 줄어들었을 때 스택에서 꺼내진 객체들을 가비지 컬렉터가 회수하지 않는다. 이 스택이 그 객체들의 **다 쓴 참조(obsolete reference)를 여전히 가지고 있기 때문이다.**

> 다 쓴 참조란 앞으로 다시 쓰지 않을 참조를 뜻한다.

가비지 컬렉션 언어에서는 의도치 않게 객체를 살려두는 메모리 누수를 찾기가 아주 까다롭다. 객체 참조 하나를 살려두면 가비지 컬렉터는 그 객체뿐 아니라 그 객체가 참조하는 모든 객체(그리고 또 그 객체들이 참조하는 모든 객체...)를 회수해가지 못한다.

해법은 참조를 다 썼을 때 `null`처리(참조 해제)하는 것이다.

위의 스택 예시에서 각 원소의 참조가 더 이상 필요 없어지는 시점은 스택에서 꺼내질 때로, `pop`메서드를 수정해야 한다.

```java
public Object pop() {
  if(size == 0) {
    throw new EmptyStackException();
  }
  Object result = elements[--size];
  elements[size] = null;  // 다 쓴 참조 해제
  return result;
}
```

다 쓴 참조를 `null` 처리하면 다른 이점도 따라온다. `null` 처리한 참조를 실수로 사용하려 하면 프로그램은 즉시 `NullPointerException`을 던지며 종료된다.

---

**객체 참조를 `null` 처리하는 일은 예외적인 경우여야 한다.** 다 쓴 참조를 해제하는 가장 좋은 방법은 그 참조를 담은 변수를 유효 범위(scope) 밖으로 밀어내는 것이다.

> 로컬 변수는 범위 안에서만 유효하니까 로컬변수 같은건 참조 해제를 해주지 않아도 된다.

### 메모리 누수의 주범 1 - 자기 메모리를 직접 관리하는 클래스

스택 예시가 메모리 누수에 취약한 이유는 자기 메모리를 직접 관리하기 때문이다. 이 스택은 (객체 자체가 아니라 객체 참조를 담는) `elements` 배열로 저장소 풀을 만들어 원소들을 관리하는데, 배열의 활성 영역에 속하지 않는 비활성 영역의 객체들은 쓰이지 않는다.
문제는 비활성 영역의 객체가 더 이상 쓰이지 않는다는 것은 프로그래머만 아는 사실이라는 것이다. 그러므로 프로그래머는 비활성 영역이 되는 순간 `null` 처리해서 해당 객체를 더 이상 쓰지 않을 것임을 가비지 컬렉터에게 알려야 한다.

일반적으로 **자기 메모리를 직접 관리하는 클래스라면 프로그래머는 항시 메모리 누수에 주의해야 한다.** 원소를 다 사용한 즉시 그 원소가 참조한 객체들을 모두 `null` 처리 해줘야 한다.

### 메모리 누수의 주범 2 - 캐시

캐시 역시 메모리 누수를 일으키는 주범이다. 객체 참조를 캐시에 넣고 그 객체를 다 쓰고도 한참을 그냥 놔두는 경우가 많다.

캐시를 만들 때 캐시 엔트리의 유효 기간을 정확히 정의하기 어렵기 때문에 시간이 지날수록 엔트리의 가치를 떨어뜨리는 방식을 흔히 사용한다. 백그라운드 스레드를 사용하거나 캐시에 새 엔트리를 추가할 때 부수 작업으로 수행하는 방법이 있다.

### 메모리 누수의 주범 3 - 리스너 혹은 콜백이라 부르는 것

클라이언트가 콜백을 등록만 하고 명확히 해지하지 않는다면, 뭔가 조치해주지 않는 한 콜백은 계속 쌓여간다. 이럴 때 콜백을 **약한 참조(weak reference)** 로 저장하면 가비지 컬렉터가 즉시 수거해간다. 예를 들어 `WeakHashMap`에 키로 저장하면 된다.

---

## WeakHashMap

`weak keys`를 사용하는 HashTable 기반의 `Map` 구현체다. `WeakHashMap`은 키를 더 이상 사용하지 않는다고 판단되면 자동으로 엔트리가 지워진다. 키는 `WeakReference`로 감싼 객체가 된다.

- [WeakHashMap](https://docs.oracle.com/javase/7/docs/api/java/util/WeakHashMap.html)
- [WeakReference](https://docs.oracle.com/javase/7/docs/api/java/lang/ref/WeakReference.html)
