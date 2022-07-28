# 아이템 6. 불필요한 객체 생성을 피하라

똑같은 기능의 객체를 매번 생성하지 말고 객체 하나를 재사용 하는 것이 나을 때가 많다. 재사용은 빠르고 세련되며 특히 불변 객체는 언제든 재사용할 수 있다.

```java
String s = new String("bikini");  // 따라 하지 말 것!
```

위의 코드는 실행될 때마다 `String`인스턴스를 새로 만든다. 생성자로 넘긴 `"bikini"`자체가 이 생성자로 만들어 내려는 문자열과 완전히 동일하다. 위의 코드가 반복문이나 빈번히 호출되는 메서드 안에 있다면 쓸데없는 문자열 인스턴스가 수백만 개 만들어질 수도 있다.

```java
String s = "bikini";  // 개선하면 이렇다
```

이 코드는 새로운 인스턴스를 매번 만들지 않고 하나의 인스턴스를 사용한다. 이 방법을 사용하면 같은 가상 머신 안에서 이와 똑같은 문자열 리터럴을 사용하는 모든 코드가 같은 객체를 재사용함이 보장된다.

생성자 대신 정적 팩터리 메서드를 제공하는 불변 클래스에서는 정적 팩터리 메서드를 사용해 불필요한 객체 생성을 피한다. 생성자는 호출할 때마다 새로운 객체를 만들지만, 팩터리 메서드는 그렇지 않다. 불변 객체만이 아니라 가변 객체라 해도 사용 중에 변경되지 않을 것임을 안다면 재사용할 수 있다.

생성 비용이 아주 비싼 객체도 있다. 이런 비싼 객체가 반복해서 필요하다면 캐싱하여 재사용하길 권한다.

주어진 문자열이 유효한 로마 숫자인지를 확인하는 메서드다. 이 방식의 문제는 `String.mathces` 메서드를 사용한다는 데 있다. `String.matches`는 정규표현식으로 문자열 형태를 확인하는 가장 쉬운 방법이지만, 성능이 중요한 상황에서 반복해 사용하기엔 적합하지 않다. 이 메서드가 내부에서 만드는 정규표현식용 `Pattern`은 한 번쓰고 버려져 곧바로 가비지 컬렉션 대상이 된다.

> `Pattern`은 입력받은 정규표현식에 해당하는 유한 상태 머신(finite state machine)을 만들기 때문에 인스턴스 생성 비용이 높다.

```java
static boolean isRomanNumeral(String s) {
  return s.matches("^(?=.)M*(C[MD]|D?C{0,3})"
      + "(X[CL]|L?X{0,3})(I[XV]|v?I{0,3})$");
}
```

필요한 정규표현식을 표현하는 (불변인) `Pattern`인스턴스를 클래스 초기화(정적 초기화) 과정에서 직접 생성해 캐싱해두고, `isRomanNumeral`메서드가 호출될 때마다 이 인스턴스를 재사용하는 방식으로 개선할 수 있다.

```java
public class RomanNumerals {
  // 미리 만들어 둔다.
  private static final Pattern ROMAN = Pattern.compile(
          "^(?=.)M*(C[MD]|D?C{0,3})"
            + "(X[CL]|L?X{0,3})(I[XV]|v?I{0,3})$");

  static boolean isRomanNumeral(String s) {
    return ROMAN.matcher(s).matches();
  }

}
```

> 만약 `isRomanNumeral`이 한 번도 호출되지 않으면 `ROMAN`필드는 쓸데없이 초기화된 꼴이다. 그래서 `isRomanNumeral`메서드가 처음 호출될 때 필드를 초기화 하는 지연 초기화로 불필요한 초기화를 없앨수도 있다.

객체가 불변이라면 재사용해도 안전함이 명확하다. 하지만 아닌 경우도 있다.

### 어댑터

어댑터는 실제 작업은 뒷단 객체에 위임하고, 자신은 제2의 인터페이스 역할을 해주는 객체다.

`Map`인터페이스의 `keySet`메서드는 `Map`객체 안의 키 전부를 담은 `Set`뷰(어댑터)를 반환한다. `keySet`메서드를 호출할 때 마다 새로운 `Set`인스턴스가 만들어지리라 생각할 수도 있지만, 사실은 매번 같은 `Set`인스턴스를 반환한다. 즉, 반환한 객체 중 하나를 수정하면 다른 모든 객체가 따라서 바뀐다. 따라서 `keySet`이 뷰 객체를 여러 개 만들어도 상관은 없지만, 그럴 필요도 없고 이득도 없다.

```java
public class KeySetTest {

  public static void main(String[] args) {
    Map<String, Integer> playersMap = new HashMap();
    playersMap.put("Stephen Curry", 30);
    playersMap.put("Kevin Durant", 35);

    Set<String> players1 = playersMap.keySet();
    Set<String> players2 = playersMap.keySet();

    players1.remove("Kevin Durant");  // players1에서만 지워질 것 같지만 아니다.
  }

}
```

### 오토박싱

오토박싱은 기본 타입과 그에 대응하는 박싱된 기본 타입의 구분을 흐려주지만, 완전히 없애주는 것은 아니다.

```java
private static long sum() {
  Long sum = 0L;

  for (long i = 0; i <= Integer.MAX_VALUE; i++) {
    sum += i;
  }

  return sum;
}
```

이 코드가 정확한 답을 내기는 하지만, 제대로 구현했을 때 보다 훨씬 느리다.

`sum`변수를 `Long`으로 선언해서 불필요한 `Long`인스턴스가 약 2³¹개나 만들어진 것이다.

박싱된 기본 타입보다는 기본 타입을 사용하고, 의도치 않은 오토박싱이 숨어들지 않도록 주의해야 한다.

---

이번 아이템을 "객체 생성은 비싸니 피해야 한다"로 오해하면 안 된다. 프로그램의 명확성, 간결성, 기능을 위해서 객체를 추가로 생성하는 것이라면 일반적으로 좋은 일이다.

이번 아이템은 방어적 복사를 다루는 아이템50과 대조적이다. 방어적 복사가 필요한 상황에서 객체를 재사용했을 때의 피해가 필요 없는 객체를 반복 생성했을 때의 피해보다 훨씬 크다는 사실을 기억하자.
