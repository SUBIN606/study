### 9.1 스택

- 스택의 세 가지 제약

  1. 스택의 끝에만 데이터 삽입 가능
  2. 스택의 끝에서만 삭제 가능
  3. 스택의 마지막 원소만 읽을 수 있음

### 9.2 추상 데이터 타입

- 스택이 추상 데이터타입에 속하는 이유는 무엇인가

  스택은 사실 규칙 집합으로 특정 결과를 얻기 위해 배열과 상호작용하는 방식을 다룬다. 스택은 내부적으로 어떤 데이터 구조를 쓰든 개의치 않고, LIFO 방식으로 동작하는 데이터 원소들의 리스트면 된다.
  스택은 다른 내장 데이터 구조를 사용하는 이론적 규칙 집합으로 이뤄진 데이터 구조다.

- 추상 데이터 타입?

  다른 내장 데이터 구조를 기반으로 작성된 코드일 뿐.

### 9.3 스택 다뤄보기

- 스택을 사용해 괄호를 잘 열고 닫았는지 확인하는 과정을 설명해보세요.

### 9.4 제약을 갖는 데이터 구조의 중요성

- 제약을 갖는 데이터 구조가 중요한 이유?

  1. 잠재적 버그를 막을 수 있다.
  2. 문제를 해결하는 새로운 사고 모델을 제공한다.

> 스택 java로 구현해보기

```java
// 1. 스택은 맨 마지막에 추가된다.
// 2. 값은 맨 마지막부터 읽을 수 있다.
// 3. 삭제도 맨 마지막부터 가능하다.
public class Stack<T extends Object> {

    int lastIndex = -1;
    Object[] values = {};

    public Stack(int defaultSize) {
        this.values = new Object[defaultSize];
    }

    public int size() {
        return lastIndex + 1;
    }

    private void increaseLastIndex() {
        this.lastIndex++;
    }

    /** 스택의 맨 마지막에 데이터 추가 */
    public void push(Object value) {
        this.values[lastIndex + 1] = value;
        increaseLastIndex();
    }

    /** 스택의 맨 마지막 데이터 삭제 */
    public void pop() {
        this.values[lastIndex] = null;
        lastIndex--;
    }

    /** 스택의 맨 마지막 데이터 읽기 */
    public Object peek() {
        return this.values[lastIndex == -1 ? 0 : lastIndex];
    }

    public String toString() {
        return Arrays.toString(this.values);
    }
}
```

### 9.5 큐

- 큐의 제약 사항을 스택과 비교해서 설명해보세요.

  1. 큐의 끝에만 데이터 삽입 가능
  2. 큐의 앞에서만 삭제 가능
  3. 큐의 앞에 있는 원소만 읽을 수 있음
     스택과 큐 모두 끝에 데이터를 삽입하는 것은 동일하나, 큐는 앞에서부터 삭제 혹은 읽을 수 있고 스택은 뒤에서부터 삭제 혹은 읽을 수 있다는 점이 다르다.

### 9.6 큐 다뤄보기
