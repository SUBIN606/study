# 아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라

> 💡 싱글턴(singleton)이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다.
> (애플리케이션을 통틀어서 인스턴스가 하나)

클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다. 타입을 인터페이스로 정의한 다음 그 인터페이스를 구현해서 만든 싱글턴이 아니라면 **싱글턴 인스턴스를 가짜(mock) 구현으로 대체할 수 없기 때문**이다.

### 싱글턴을 만드는 방법 1 - public static final 필드

`public`이나 `protected` 생성자가 없으므로 Elvis 클래스가 초기화 될 때 만들어진 인스턴스가 전체 시스템에서 하나뿐임이 보장된다.

public 필드 방식의 큰 장점은 해당 클래스가 싱글턴임이 API에 명백히 드러난다는 것이다. **public static 필드가 final이니 절대로 다른 객체를 참조할 수 없다.** 그리고 간결하다.

```java
public class Elvis {
	//인스턴스에 접근할 수 있는 유일한 수단. public static 멤버
	//private 생성자는 INSTANCE를 초기화 할 때 딱 한 번만 호출된다.
	public static final Elvis INSTANCE = new Elvis();

	//생성자는 private으로 감춰둔다.
	private Elvis() { ... }

	public void leaveThBuilding() { ... }

}
```

권한이 있는 클라이언트는 리플렉션 API인 `AccessibleObject.setAccessible` 을 사용해 private 생성자를 호출할 수 있다. 이러한 공격을 방어하려면 생성자를 수정하여 두 번째 객체가 생성되려 할 때 예외를 던지게 하면 된다.

```java
public class Elvis {
	public static final Elvis INSTANCE = new Elvis();
	private int count;

	// 리플렉션 공격을 방어하기 위해 예외를 던지게 했다.
	private Elvis() {
		count++;
		if(count > 1) {
			throw new IllegalStateException("this object should be singleton");
		}
	}

	public void leaveThBuilding() { ... }

}
```

### 싱글턴을 만드는 방법 2 - 정적 팩터리 메서드

두 번째 방법에서는 정적 팩터리 메서드를 public static 멤버로 제공한다.

```java
public class Elvis {
	private static final Elvis INSTANCE = new Elvis();
	private Elvis() { ... }

	//항상 같은 객체의 참조를 반환하므로 제2의 Elvis 인스턴스란 결코 만들어지지 않는다.(리플렉션 예외 제외)
	public static Elvis getInstance() {
		return INSTANCE;
	}

	public void leaveThBuilding() { ... }

}
```

정적 팩터리 방식의 첫번째 장점은 API를 바꾸지 않고도 싱글턴이 아니게 변경할 수 있다는 점이다. 예를 들어 유일한 인스턴스를 반환하던 팩터리 메서드가 호출하는 스레드별로 다른 인스턴스를 넘겨주게 할 수 있다.

```java
// 클라이언트 코드 수정 없이 싱글턴에서 호출될 때 마다 새로운 인스턴스를 반환하도록 변경한 예시
public static Elvis getInstance() {
	return new Elvis();
}
```

두 번째 장점은 원한다면 정적 팩터리를 제네릭 싱글턴 팩터리로 만들 수 있다는 점이다.

세 번째 장점은 정적 팩터리의 메서드 참조를 공급자로 사용할 수 있다는 점이다.

이러한 장점들이 굳이 필요하지 않다면 public 필드 방식이 좋다.

### 싱글턴을 만드는 방법 3 - 원소가 하나인 열거 타입 선언

public 필드 방식과 비슷하지만, 더 간결하고, 추가 노력 없이 직렬화 할 수 있고, 심지어 아주 복잡한 직렬화 상황이나 리플렉션 공격에도 제 2의 인스턴스가 생기는 일을 완벽히 막아준다.

대부분 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.

```java
public enum Elvis {
	INSTANCE;

	public void leaveTheBuilding() { ... }
}
```

하지만 클래스를 상속할 수 없음. 인터페이스 구현은 가능.
