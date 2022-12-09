# What
클래스의 생성자는 해당 클래스의 새 인스턴스를 생성할 때 호출된다. 생성자의 목적은 객체를 초기화 하는 것이다.

# How
(Java로 한 건 아니었지만) 이번에 `Pagination`이라는 클래스를 리팩터링 하면서 `page`와 `perPage`라는 멤버변수의
초기화 코드를 어디에 작성해야 할지 고민하다가 맨 처음에 `setter`메서드를 만들어서 생성자에서 호출해버렸다.
``` java
class Pagination {

  private int page;
  private int perPage;

  Pagination(int page, int perPage) {
    this.setPage(page);
    this.setPerPage(perPage);
  }

  // ...setter 생략
}
```
각각 setter에서는 page가 음수는 아닌지, perPage가 너무 큰 값은 아닌지 등을 확인하고 제한 범위에 속하지 않은 값이 주어지면 기본값으로 바꿔버리는 초기화 코드였다.

조금 찝찝하긴 해도 별 문제 없을거라 생각했는데 생성자에서는 메서드를 호출하면 안 된다고 한다. 왜요..?

# Why
테스트를 해봤다. `A`라는 클래스는 생성자에서 setter를 호출한다. 그리고 `B`클래스가 `A`클래스를 상속받고 setter를 오버라이딩했다.

하지만 결과는 0이 나왔다. 그 이유는 `B`클래스의 `num` 멤버 변수가 `setValue`가 호출될 때 초기화되지 않았기 떄문이다.
``` java
public class Test {
    public static void main(String[] args) {
        B b = new B(99);
        System.out.println("b.getValue() = " + b.getValue());
        // 99도, 999도 아닌 0이 출력된다.
    }

    static class A {
        private int value;

        public A(int value) {
            this.setValue(value);
        }

        public int getValue() {
            return value;
        }

        public void setValue(final int value) {
            this.value = value;
        }
    }

    static class B extends A {
        private int num = 999;

        public B(final int value) {
            super(value);
        }

        @Override
        public void setValue(final int value) {
            super.setValue(num);
        }
    }
}
```
생성자는 입력받은 값으로 초기화 하는 역할이다. 이런 역할 외에 다른 역할이 늘어나면 안된다! 그리고 불가피하게
메서드를 호출해야 한다면 `final`메서드를 사용해서 하위 클래스가 재정의하지 못하도록 막아야 한다. 재정의 해서 사용해도
예상한 것 처럼 동작하지 않으므로..

> 사실 생성자에서 setter만 호출하면 안되는게 아니라 final 메서드를 제외하곤 호출하지 않는 것이 좋다.

references

- [Practical Java Praxis 68: Use Care When Calling Non-final Methods from Constructors](https://www.informit.com/articles/article.aspx?p=20521)
- [stackoverflow](https://stackoverflow.com/questions/2568463/java-should-private-instance-variables-be-accessed-in-constructors-through-get/2568983#2568983)

