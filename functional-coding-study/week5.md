> 5주차 스터디는 챕터 12 ~ 14을 진행한다.

# 12장. 함수형 반복

- `map()`함수란?
  - 배열을 받아 하나씩 변환해서 같은 길이의 배열로 만들어주는 함수
- `map()`과 `foreach()`의 다른점?
  - `map`과 `foreach`모두 고차 함수로 배열을 반복한다는 공통점이 있지만, map은 새로운 배열을 리턴하는 점이 다르다

### map으로 함수 본문을 콜백으로 바꾸기

```javascript
function eamilsForCustomers(customers, goods, bests) {
  var emails = [];
  forEach(customers, function (customer) {
    var email = emailForCustomer(customer, goods, bests);
    emails.push(email);
  });
  return emails;
}
```

```javascript
// foreach를 map으로 빼낸 버전
function eamilsForCustomers(customers, goods, bests) {
  return map(customers, function (customer) {
    // 본문을 콜백으로 전달
    return emailForCustomer(customer, goods, bests);
  });
}

function map(array, f) {
  var newArray = [];
  forEach(array, function (element) {
    newArray.push(f(element)); // 여기서 콜백 호출
  });
  return newArray;
}
```

- 자바스크립트에서 함수에 함수를 전달하는 방법 세 가지?

  - 전역으로 정의하고 이름을 붙이는 방법. 가장 많이 쓰이며 붙인 이름으로 프로그램 어디서나 쓸 수 있다.
  - 지역 범위 안에서 정의하고 이름을 붙일 수 있다. 이름을 가지고 있지만 범위 밖에서는 쓸 수 없다. 지역적으로 쓰고 싶지만 이름이 필요할 때 사용한다.
  - 함수를 사용하는 곳에서 바로 정의하는 인라인으로 정의하기. 함수를 변수 같은 곳에 넣지 않아 이름이 없기 때문에 익명 함수라고 부른다. 문맥에서 한 번만 사용하는 짧은 함수에 사용하면 좋다.

- `filter()`함수란?
  - 원래 배열을 가지고 새로운 배열을 만드는 고차함수
  - 새로운 배열에 담을 항목과 건너뛸 항목을 결정할 수 있다.

### filter로 함수 본문을 콜백으로 바꾸기

```javascript
function selectBestCustomer(customers) {
  var newArray = [];
  forEach(customers, function (customer) {
    if (customer.purchases.length >= 3) {
      newArray.push(customer);
    }
  });
  return newArray;
}
```

```javascript
function selectBestCustomer(customers) {
  return filter(customers, function (customer) {
    return customer.purchases.length >= 3; // 표현식을 함수로 빼서 인자로 전달
  });
}

function filter(array, f) {
  var newArray = [];
  forEach(array, function (element) {
    if (f(element)) {
      // 조건식을 콜백으로 호출
      newArray.push(element);
    }
  });
  return newArray;
}
```

- `reduce()`함수란?
  - 배열을 순회하면서 값을 누적해나가는 함수
  - forEach나 map은 배열을 반환하지만 reduce는 아니다

### reduce를 사용해 함수 본문 콜백으로 바꾸기

```javascript
function countAllPurchases(customers) {
  var total = 0;
  forEach(customers, function (customer) {
    total += customer.purchases.length;
  });
  return total;
}
```

```javascript
function countAllPurchases(customers) {
  return reduce(customers, 0, function (total, customer) {
    return total + customer.purchases.length;
  });
}

function reduce(array, init, f) {
  var accum = init; // 초깃값
  forEach(array, function (element) {
    accum = f(accum, element);
  });
  return accum;
}
```
