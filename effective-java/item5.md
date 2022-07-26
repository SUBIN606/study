# 아이템 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

많은 클래스가 하나 이상에 자원에 의존한다.

책에서는 `SpellChecker`라는 맞춤법 검사기 클래스로 예를 들고 있다. 맞춤법 검사기는 사전(dictionary)을 사용하는데, 이를 의존한다고 한다.

### 잘못 사용한 예 1. 정적 유틸리티 클래스로 구현한 경우

```java
public class SpellChecker {
  private static final Lexicon dictionary = ...;

  // 정적 유틸리티 클래스로 만들기 위해 private 생성자를 명시했다.
  private SpellChecker () {}

  // 정적 멤버만 선언한다.
  public static boolean isValid(String word) {...}
  public static List<String> suggestions(String type) {...}
}
```

### 잘못 사용한 예 2. 싱글턴으로 구현한 경우

```java
public class SpellChecker {

  private final Lexion dictionary = ...;

  private SpellChecker () {...}
  public static SpellChecker INSTANCE = new SpellChecker(...);

  // ...생략
}
```

두 방식 모두 사전을 단 하나만 사용한다고 가정한다. 하지만 사전 하나로 이 모든 쓰임에 대응할 수 있기를 바라는 건 너무 순진한 생각이다. 실제로는 언어마다 사전이 따로 있고, 특수 어휘용 사전도 있으며 심지어는 테스트용 사전도 필요할 수 있다.

**사용하는 자원에 따라 동작이 달라지는 클래스**에는 정적 유틸리티 클래스나 싱글턴 방식이 적절하지 않다. **인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식**을 사용해야 한다. 이는 의존 객체 주입의 한 형태로, 맞춤법 검사기를 **생성할 때 의존 객체인 사전을 주입**해주면 된다.

> 의존성 주입 방식은 `setter`메서드 주입 혹은 스프링에서는 `@Autowired`애너테이션을 활용한 필드 주입 방식도 있다.

`SpellChecker`가 여러 사전을 사용할 수 있도록 만들어 보자.

```java
public class SpellChecker {
  private final Lexion dictionary;

  // 생성자 주입 방식!
  public SpellChecker(Lexion dictionary) {
    this.dictionary = dictionary;
  }

  // ...생략
}
```

예시 에서는 `dictionary`라는 딱 하나의 자원만 사용하지만, **자원이 몇 개든 의존 관계가 어떻든 상관 없이** 잘 작동한다. 또한 **불변을 보장**하여 같은 자원을 사용하려는 여러 클라이언트가 의존 객체들을 안심하고 공유할 수 있기도 하다.

이 패턴의 쓸만한 변형으로, **생성자에 자원 팩터리를 넘겨주는 방식**이 있다. 팩터리란 호출할 때마다 특정 타입의 인스턴스를 반복해서 만들어주는 객체를 말한다. 즉, 팩터리 메서드 패턴(Factory Method pattern)을 구현한 것이다.

자바 8에서 소개한 `Supplier<T>` 인터페이스가 팩터리를 표현한 한 예다.

```java
Mosaic create(Supllier<? extends Tile> tileFactory) {...}
```

위의 코드는 클라이언트가 제공한 팩터리가 생성한 `Tile`들로 구성된 `Mosaic`를 만드는 메서드다.

의존 객체 주입이 유연성과 테스트 용이성을 개선해주긴 하지만, 의존성이 수천 개나 되는 큰 프로젝트에서는 코드를 어지럽게 만들기도 한다.

> 이 점은 대거, 주스, 스프링 같은 프레임워크를 사용해 해소할 수 있다.

---

클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다. 이 자원들을 클래스가 직접 만들게 해서도 안 된다.

대신 필요한 자원을 (혹은 그 자원을 만들어주는 팩터리를) 생성자에 (혹은 정적 팩터리나 빌더에) 넘겨주자.

의존 객체 주입이라 하는 이 기법은 클래스의 유연성, 재사용성, 테스느 용이성을 기막히게 개선해준다.
