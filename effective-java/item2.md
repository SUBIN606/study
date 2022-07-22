# 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라

정적 팩터리와 생성자에는 똑같은 제약이 하나 있다. **선택적 매개변수가 많을 때 적절히 대응하기 어렵다**는 점이다.

### 점층적 생성자 패턴

`점층적 생성자 패턴`도 쓸 수는 있지만, 매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다.

```java
public class NutritionFacts {

	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;

	// 필수
	public NutritionFacts(int servingSize, int servings) {
		this(servingSize, servings, 0);
	}

	// 매개변수를 하나씩 늘려가면서 생성자를 여러 개 만든다.
	public NutritionFacts(int servingSize, int servings, int calories) {
		this(servingSize, servings, calories, 0);
	}

	// ... 생략

	public NutritionFacts(int servingSize, int servings, int calories,
                          int fat, int sodium, int carbohydreate) {
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}
}
```

### 자바 빈즈 패턴

`자바 빈즈 패턴`은 객체 하나를 만들려면 메서드를 여러 개 호출해야 하고, 객체가 완전히 생성되기 전까지는 일관성이 무너진 상태에 놓이게 된다. **자바 빈즈 패턴에서는 클래스를 불변으로 만들 수 없으며 스레드 안전성을 얻으려면 프로그래머가 추가 작업을 해줘야만 한다.**

```java
public class NutritionFacts {

	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;

	public NutritionFacts() {}

	public void setServingSize(int val) {servingSize = val;}
	public void setServings(int val) {servings = val;}
	public void setCalories(int val) {caloires = val;}
	// ...생략

}
```

### 빌더 패턴

`빌더 패턴(Builder pattern)`은 점층적 생성자 패턴의 안전성과 자바 빈즈 패턴의 가독성을 겸비했다. 클라이언트는 필요한 객체를 직접 만드는 대신, 필수 매개변수만으로 생성자(혹은 정적 팩터리)를 호출해 빌더 객체를 얻는다. 그런 다음 빌더 객체가 제공하는 일종의 세터 메서드들로 원하는 선택 매개변수들을 설정한다. 마지막으로 매개변수가 없는 `build` 메서드를 호출해 드디어 우리에게 필요한(보통은 불변인) 객체를 얻는다.

빌더는 생성할 클래스 안에 정적 멤버 클래스로 만들어두는 게 보통이다.

```java
public class NutritionFacts {

	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;

	public static class Builder {
		// 필수 매개변수
		private final int servingSize;
		private final int servings;

		// 선택 매개변수 - 기본값으로 초기화 한다.
		private int calories = 0;
		private int fat = 0;
		private int sodium = 0;
		private int carbohydrate = 0;

		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}

		public Builder calories(int val) {
			calories = val;
			return this;
		}

		// 생략

		public NutritionFacts build() {
			return new NutritionFacts(this);
		}

	}

	private NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}

}
```

클라이언트 측 사용 예시

```java
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
                  .calories(100).sodium(35).carbohydrate(27).build();
```

**빌더의 세터 메서드들은 빌더 자신을 반환하기 때문에 연쇄적으로 호출 할 수 있다.** 이런 방식을 메서드 호출이 흐르듯 연결된다는 뜻으로 `플루언트 API(fluent API)` 혹은 `메서드 연쇄(method chaining)`라 한다.

빌더 패턴은 명명된 선택적 매개변수를 흉내 낸 것이다.

<aside>
💡 잘못된 매개변수를 최대한 일찍 발견하려면 빌더의 생성자와 메서드에서 입력 매개변수를 검사하고, build 메서드가 호출하는 생성자에서 여러 매개변수에 걸친 불변식을 검사하자.

</aside>

빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기에 좋다.

추상 클래스는 추상 빌더를, 구체 클래스는 구체 빌더를 갖게 한다.

```java
public abstract class Pizza {
	public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
	final Set<Topping> toppings;

	// Pizza.Builder 클래스는 재귀적 타입 한정을 이용하는 제네릭 타입이다.
	abstract static class Builder<T extends Builder<T>> {
		EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
		public T addTopping(Topping topping) {
			toppings.add(Objects.requireNonNull(topping));
			return self();
		}

		abstract Pizza build();

		// 하위 클래스는 이 메서드를 재정의(overriding)하여 "this"를 반환하도록 해야 한다.
		// 추상 메서드인 self를 통해 하위 클래스에서는 형변환하지 않고도 메서드 연쇄를 지원할 수 있다.
		protected abstract T self();
	}

	Pizza(Builder<?> builder) {
		toppings = builder.toppings.clone();
	}
}
```

```java
public class NyPizza extends Pizza {
	public enum Size { SMALL, MEDIUM, LARGE }
	private final Size size; // 필수 매개변수

	public static class Builder extends Pizza.Builder<Builder> {
		private final Size size;

		public Builder(Size size){
			this.size = Objects.requireNonNull(size);
		}

		// 하위 클래스의 빌더가 정의한 build 메서드는 해당하는 구체 하위 클래스를 반환하도록 선언한다.
		// 공변 반환 타이핑
		@Override
		public NyPizza build() {
			return new NyPizza(this);
		}

		@Override
		protected Builder self() { return this; }
	}

	private NyPizza(Builder builder) {
		super(builder);
		size = builder.size;
	}
}
```

```java
NyPizza pizza = new NyPizza.Builder(SMALL)
                  .addTopping(SAUSAGE).addTopping(ONION).build();
```

하위 클래스의 메서드가 상위 클래스의 메서드가 정의한 반환 타입이 아닌, 그 하위 타입을 반환하는 기능을 `공변 반환 타이핑(covariant return typing)`이라 한다. 이 기능을 이용하면 클라이언트가 형변환에 신경쓰지 않고도 빌더를 사용할 수 있다.

**빌더를 이용하면 가변인수 매개변수를 여러 개 사용할 수 있다.** 각각을 적절한 메서드로 나눠 선언하면 된다. 아니면 메서드를 여러 번 호출하도록 하고 각 호출 때 넘겨진 매개변수들을 하나의 필드로 모을 수도 있다. (예: addTopping 메서드)

빌더 패턴은 상당히 유연하다. 빌더 하나로 여러 객체를 순회하면서 만들 수 있고, 빌더에 넘기는 매개변수에 따라 다른 객체를 만들 수도 있다.

---

빌더 생성 비용이 크지는 않지만 성능에 민감한 상황에서는 문제가 될 수도 있다.

매개변수가 4개 이상은 되어야 값어치를 한다. 하지만 API는 시간이 지날수록 매개변수가 많아지는 경향이 있음을 명심하자.

애초에 빌더로 시작하는 편이 나을 때가 많다.
