# 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라

클라이언트가 클래스의 인스턴스를 얻는 전통적인 수단은 `public` 생성자다. 하지만 모든 프로그래머가 꼭 알아둬야 할 기법이 하나 더 있다. 클래스는 생성자와 별도로 `정적 팩터리 메서드(static factory method)` 를 제공할 수 있다. **그 클래스의 인스턴스를 반환하는 단순한 정적 메서드** 말이다.

클래스는 클라이언트에 public 생성자 대신 (혹은 생성자와 함께) 정적 팩터리 메서드를 제공할 수 있다. 이 방식에는 장점과 단점이 모두 존재한다.

### 장점1: 이름을 가질 수 있다.

한 클래스에 시그니처가 같은 생성자가 여러 개 필요할 것 같으면, 생성자를 정적 팩터리 메서드로 바꾸고 각각의 차이를 잘 드러내는 이름을 지어주자. ⇒ 똑같은 타입을 파라미터로 받는 생성자를 두 개 만들 수 없으니까, 이런 경우에 정적 팩터리 메서드를 사용하면 유용하다.
잘 만든 이름을 가진 정적 팩터리 메서드가 있으면 더 편하다.

```java
public class Foo {

	String name;
	String address;

	// default 생성자
	public Foo() {}

	// 전통적인 public 생성자 방식
	public Foo(String name) {
		this.name = name;
	}

	// 시그니처가 같은 public 생성자는 중복 될 수 없다.
	//public Foo(String address) {
	//	this.address = address;
	//}

	// 정적 팩터리 메서드 방식
	// withName이라는 이름을 가졌다.
	public static Foo withName(String name) {
		return new Foo(name);
	}

	// 이름을 다르게 함으로써 해결할 수 있다!
	public static Foo withAddress(String address) {
		Foo foo = new Foo();
		foo.address = address;
		return foo;
	}

	public static void main(String[] args) {
			// public 생성자 방식. "foo"가 무엇을 의미하는지 알 수 없다.
			Foo foo1 = new Foo("foo");

			// 정적 팩터리 메서드 방식. "foo"가 name임을 알 수 있다.
			Foo foo2 = Foo.withName("foo");
	}

}
```

### 장점2: 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다.

불변 클래스(immutable class)는 인스턴스를 **미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용하는 식으로 불필요한 객체 생성을 피할 수 있다.**
반복되는 요청에 같은 객체를 반환하는 식으로 정적 팩터리 방식의 클래스는 언제 어느 인스턴스를 살아 있게 할지를 철저히 통제할 수 있다. 이런 클래스를 인스턴스 통제 클래스라 한다.

```java
public class Foo {
	String name;

	public Foo() {}

	// 인스턴스를 미리 만들어 놓았다.
	private static final Foo INSTANCE = new Foo();

	public static Foo getFoo() {
		return INSTANCE;
	}

	public static void main(String[] args) {
		Foo foo = Foo.getFoo(); // 미리 만들어 놓은 인스턴스를 반환한다.
	}
}
```

### 장점3: 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.

반환할 객체의 클래스를 자유롭게 선택할 수 있게 하는 엄청난 유연성을 선물한다.

리턴 타입에는 인터페이스를 작성하고, 실제로는 인터페이스의 구현체를 반환하는 것 ⇒ 밖에서 보는 클라이언트는 구현체를 몰라도 된다.

`java.util.Collections` 가 그렇다.

```java
public interface FooInterface {

	// 자바8부터는 public static 메서드를 추가할 수 있고 그 구현체를 반환할 수 있다.
	public static Foo getFoo() {
		return new Foo();
	}
}
```

### 장점 4: 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.

반환 타입의 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관없다. → 장점3과 이어지는 내용

```java
public class Foo {
	String name;

	public Foo() {}

	// 인스턴스를 미리 만들어 놓았다.
	private static final Foo INSTANCE = new Foo();

	public static Foo getFoo() {
		return INSTANCE;
	}

	public static Foo getFoo(boolean flag) {
		// 반환 타입은 Foo이지만, flag에 따라 Foo의 하위 타입인 BarFoo를 반환할 수 있음.
		// 하위타입이기만 하면, 어떤 클래스의 객체를 반환해도 괜찮다.
		return flag ? new Foo() : new BarFoo();
	}

	static class BarFoo extends Foo {

	}

	public static void main(String[] args) {
		Foo foo = Foo.getFoo(); // 미리 만들어 놓은 인스턴스를 반환한다.
	}
```

### 장점 5: 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

이러한 유연함은 서비스 제공자 프레임워크를 만드는 근간이 된다.

### 단점 1: 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.

### 단점 2: 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.

생성자는 Javadoc 상단에 모아서 보여주지만 정적 팩터리 메서드는 API 문서에서 특별히 다뤄주지는 않는다.

생성자처럼 API 설명에 명확히 드러나지 않으니 사용자는 정적 팩터리 메서드 방식 클래스를 인스턴스화할 방법을 알아내야 한다.

---

**정적 팩터리 메서드에 흔히 사용하는 명명 방식들**

`from` : 매개변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드

```java
Date d = Date.from(instance);
```

`of` : 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드

```java
Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);
```

`valueOf` : from과 of의 더 자세한 버전

```java
BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
```

`instance or getInstance` : 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지 않음

```java
StackWalker luke = StackWalker.getInstance(options);
```

`create or newInstance` : instance 혹은 getInstance와 같지만, **매번 새로운 인스턴스를 생성해 반환함**을 보장함

```java
Object newArray = Array.newInstance(classObject, arrayLen);
```

`getType` : getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다.

```java
FileStore fs = Files.getFileStore(path);
```

`newType` : newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다.

```java
ButteredReader br = Files.newBufferedReader(path);
```

`type` : getType과 newType의 간결한 버전

```java
List<Complaint> litany = Collections.list(leagecyLitany);
```

<aside>
💡 정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는 것이 좋다. 그렇다고 하더라도 정적 팩터리를 사용하는 게 유리한 경우가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있다면 고치자.

</aside>
