> 3주차는 챕터 6 ~ 8을 읽고 스터디를 진행한다.

# 챕터6. 변경 가능한 데이터 구조를 가진 언어에서 불변성 유지하기

- 중첩된 데이터란?

  - 데이터 구조 안에 데이터 구조가 있는 경우 데이터가 중첩(nested)되었다고 말한다.
  - 깊이 중첩(deeply nested)되었다는 말은 중첩이 이어진다는 말이다.
  - 어떤 객체가 객체 안에 또 다시 배열 안에, 객체 안에 있는 것처럼 중첩은 계속 이어질 수 있다.

- 동작을 읽기, 쓰기로 분류하는 기준은 무엇인가?

  - 읽기 동작은 데이터를 바꾸지 않고 정보를 꺼내는 것이다. 데이터가 바뀌지 않기 때문에 다루기 쉽다.
    - 만약 인자에만 의존해 정보를 가져오는 읽기 동작이라면 계산이라 할 수 있다.
  - 쓰기 동작은 어떻게든 데이터를 바꾼다. 바뀌는 값은 어디서 사용될지 모르기 때문에 바뀌지 않도록 원칙이 필요하다.
    - 그래서 쓰기 동작은 불변성 원칙에 따라 구현해야 하며, 카피-온-라이트 방식을 사용해야 한다.

- 카피-온-라이트 원칙 세 단계는?
  - 복사본을 만들고, 복사본을 변경하고, 복사본을 리턴한다.

```javascript
function add_element_last(array, elem) {
  let new_array = array.slice(); // 1. 복사본 만들기
  new_array.push(elem); // 2. 복사본 바꾸기
  return new_array; // 3. 복사본 리턴하기
}
```

> 위의 함수는 데이터를 바꾸지 않았고 정보를 리턴했기 때문에 읽기 동작에 해당한다.

- 쓰기와 읽기를 모두 하는 동작의 접근방법 두 가지?
  - 어떤 동작은 읽고 변경하는 일을 동시에 한다. 이런 동작은 값을 변경하고 리턴한다.
  - 읽기와 쓰기 함수로 각각 분리하거나, 함수에서 값을 두 개 리턴한다.
- 읽기와 쓰기를 분리하면 좋은 점이 무엇인가?
  - 분리된 함수를 따로 쓸 수 있기 때문에 더 좋은 접근 방법이다. 선택해서 함수를 쓸 수 있다.

```javascript
function first_element(array) {
  return array[0]; // 그냥 배열의 첫 번째 항목을 리턴하는 함수. 이 함수는 계산이다.
}

// 카피-온-라이트로 구현한 쓰기 동작
function drop_first(array) {
  let array_copy = array.slice();
  array_copy.shift();
  return array_copy;
}

// 값을 두 개 리턴하는 함수로 만들기
function shift(array) {
  return {
    first: first_element(array),
    array: drop_first(array),
  };
}
```

- 쓰기와 액션의 관계?

  - 변경 가능한 데이터를 읽는 것은 액션이다. 쓰기는 데이터를 변경 가능한 구조로 만든다.
  - 불변 데이터 구조를 읽는 것은 계산이다. **쓰기를 읽기로 바꾼다는 것은 데이터 구조를 불변형으로 만드는 것이고, 불변형으로 만들수록 코드에 더 많은 계산이 생기고 액션은 줄어든다.**

- 얕은 복사란?

  - 데이터 구조의 최상위 단계만 복사하는 것을 얕은 복사(shallow copy)라고 한다. 얕은 복사는 같은 메모리를 가리키는 참조에 대한 복사본을 만든다. 이것을 구조적 공유(structural sharing)라고 한다.
  - 데이터 구조를 복사할 때 최대한 많은 구조를 공유한다. 그래서 더 적은 메모리를 사용하고 결국 가비지 콜렉터의 부담을 줄여준다.
  - 예를 들어 객체가 들어 있는 배열이 있다면 얕은 복사는 배열만 복사하고 안에 있는 객체는 참조로 공유한다.

- 중첩된 쓰기를 읽기로 바꾸는 방법?
  - 중첩된 쓰기도 중첩되지 않은 쓰기와 같은 패턴을 사용한다. 복사본을 만들고 변경한 뒤 복사본을 리턴한다. 중첩된 항목에 또 다른 카피-온-라이트를 사용하는 부분만 다르다.
  - 중첩된 모든 데이터 구조가 바뀌지 않아야 불변 데이터라 할 수 있다. 최하위부터 최상위까지 중첩된 데이터 구조의 모든 부분이 불변형이어야 한다. **중첩된 데이터의 일부를 바꾸려면 변경하려는 값과 상위의 모든 값을 복사해야 한다.**

### 중첩된 쓰기를 읽기로 바꾸기

```javascript
function setPriceByName(cart, name, price) {
  for (let i = 0; i < cart.length; i++) {
    if (cart[i].name === name) {
      cart[i].price = price;
    }
  }
}
```

이 코드는 제품 이름으로 해당 제품의 가격을 바꾸는 쓰기 동작이다. 이 동작은 중첩된 데이터 구조를 바꿔야 하는 조금 특별한 동작이다. 장바구니 배열 안에 **중첩된 항목을 바꿔야 한다.**

중첩된 항목을 바꿔야 할 때는 가장 안쪽에 있는 쓰기 동작부터 바꾸는 것이 쉽다.

```javascript
function setPriceByName(cart, name, price) {
  let cartCopy = cart.slice(); // 카피-온-라이트
  for (let i = 0; i < cartCopy.length; i++) {
    if (cartCopy[i].name === name) {
      cartCopy[i] = setPrice(cartCopy[i], price); // 중첩된 항목을 바꾸는 카피-온-라이트 동작
    }
  }
  return cartCopy;
}

// objectSet() 함수를 사용해 물건에 새로운 값을 설정하는 함수
function setPrice(item, new_price) {
  return objectSet(item, "price", new_price);
}

// 카피-온-라이트 방식으로 객체에 값을 설정하는 함수
function objectSet(object, key, value) {
  let copy = Object.assign({}, object);
  copy[key] = value;
  return copy;
}
```

- 카피-온-라이트란?
  - 데이터를 불변형으로 유지할 수 있는 원칙이다.
  - 복사본을 만들고 원본 대신 복사본을 변경하는 것을 말한다.

# 챕터7. 신뢰할 수 없는 코드를 쓰면서 불변성 지키기

- 카피-온-라이트와 방어적 복사의 차이점?
  - 카피-온-라이트는 데이터를 바꾸기 전에 복사한다. 무엇이 바뀌는지 알기 때문에 무엇을 복사해야 할지 예상할 수 있다. 그래서 안전지대라 불리는 불변성이 지켜지는 코드다. 만약 분석하기 힘든 레거시 코드와 소통해야 할 때는 카피-온-라이트 방식을 쓸 수 없다. 그래서 데이터가 바뀌는 것을 완벽히 막아주는 방어적 복사를 사용해야 한다.
  - 신뢰할 수 없는 코드와 데이터를 주고받아야 할 때 방어적 복사를 사용한다.
  - **들어오고 나가는 데이터의 복사본을 만드는 것이 방어적 복사가 동작하는 방식이다.**
  - 카피-온-라이트 방식은 얕은 복사를 하지만 방어적 복사는 깊은 복사를 한다.
- 방어적 복사는 언제 사용하는가?
  - 안전지대의 불변성을 유지하고, 바뀔 수도 있는 데이터가 안전지대로 들어오지 못하도록 막는다.

### 방어적 복사 구현하기

원본 코드

```javascript
function add_item_to_cart(name, price) {
  const item = make_cart_item(name, price);
  shopping_cart = add_item(shopping_cart, item);

  const total = calc_total(shopping_cart);
  set_cart_total(total);
  update_shipping_icons(shopping_cart);
  update_tax_dom(total);

  // 이 함수는 신뢰할 수 없는 레거시 코드다. 데이터를 어떻게 바꾸는지 알 수 없다.
  black_friday_promotion(shopping_cart);
}
```

데이터를 전달하기 전, 후에 복사한다.

```javascript
function add_item_to_cart(name, price) {
  // safty zone
  const item = make_cart_item(name, price);
  shopping_cart = add_item(shopping_cart, item);

  const total = calc_total(shopping_cart);
  set_cart_total(total);
  update_shipping_icons(shopping_cart);
  update_tax_dom(total);
  // safty zone

  // 데이터 전달 전 복사
  let cart_copy = deepCopy(shopping_cart);
  black_friday_promotion(cart_copy);

  // 전달받은 데이터를 복사
  shopping_cart = deepCopy(cart_copy);
}
```

- 방어적 복사란? 어떤 규칙을 지켜야 하나?
  - 데이터를 변경할 수도 있는 코드와 불변성 코드 사이에 데이터를 주고받기 위한 원칙이다.
  - 데이터가 안전한 코드에서 나갈 때와, 데이터가 안전한 코드로 들어올 때 복사하는 원칙을 지켜야 한다.

### 신뢰할 수 없는 코드 감싸기

방어적 복사 코드를 분리해 새로운 함수로 만든다.

```javascript
function add_item_to_cart(name, price) {
  // safty zone
  const item = make_cart_item(name, price);
  shopping_cart = add_item(shopping_cart, item);

  const total = calc_total(shopping_cart);
  set_cart_total(total);
  update_shipping_icons(shopping_cart);
  update_tax_dom(total);
  // safty zone

  shopping_cart = black_friday_promotion_safe(shopping_cart);
}

function black_friday_promotion_safe(cart) {
  let cart_copy = deepCopy(shopping_cart);
  black_friday_promotion(cart_copy);
  return deepCopy(cart_copy);
}
```

- 깊은 복사란?
  - 깊은 복사는 원본과 어떤 데이터 구조도 공유하지 않는다. 중첩된 모든 객체나 배열을 복사한다.
  - 얕은 복사는 바뀌지 않은 값이라면 원본과 복사본이 데이터를 공유한다.

# 챕터8. 계층형 설계 1

- 계층형 설계란 무엇인가?
  - 계층형 설계는 소프트웨어를 계층으로 구성하는 기술이다. 각 계층에 있는 함수는 바로 아래 계층에 있는 함수를 이용해 정의한다.
- 직접 구현 패턴이란?
  - 함수가 모두 비슷한 계층에 있다면 직접 구현했다고 한다. 서로 다른 추상화 단계에 있는 기능을 사용하면 직접 구현 패턴이 아니다.
  - 코드가 서로 다른 구체화 단계에 있다면 읽기 어렵다.
  - 함수가 더 구체적인 내용을 다루지 않도록 함수를 일반적인 함수로 빼낸다. 일반적인 함수는 보통 구체적인 내용을 하나만 다루기 때문에 테스트 하기 쉬우며 구체적인 함수보다 더 많은 곳에서 쓸 수 있다.

```javascript
function freeTieClip(cart) {
  let hasTie = false;
  let hasTieClip = false;

  // for loop는 언어에서 제공하는 기본 기능. 저수준이다.
  for (let i = 0; i < cart.length; i++) {
    const item = cart[i]; // array에 index로 접근하는 것도 언어에서 제공하는 것. 저수준.
    if (item.name === "tie") hasTie = true;
    if (item.name === "tie clip") hasTieClip = true;
  }

  if (hasTie && !hasTieClip) {
    // make_item과 add_item은 직접 만든 함수. 언어 기본 제공보다 높은 수준.
    const tieClip = make_item("tie clip", 0);
    return add_item(cart, tieClip);
  }
  return cart;
}
```

위의 코드는 서로 다른 추상화 단계에 있는 기능을 사용하고 있다.

```javascript
function freeTieClip(cart) {
  let hasTie = isInCart(cart, "tie");
  let hasTieClip = isInCart(cart, "tie clip");

  if (hasTie && !hasTieClip) {
    const tieClip = make_item("tie clip", 0);
    return add_item(cart, tieClip);
  }
  return cart;
}
```

개선한 코드는 모두 직접 만든 함수를 사용하고 있다. 비슷한 추상화 단계를 사용하고 있으므로 직접 구현했다고 할 수 있다.

---

> 함수형 코딩은 함수를 엄청 쪼개서 아주 작은 단위로 나누고, 믿을 수 있는 함수를 조합해 나간다.
