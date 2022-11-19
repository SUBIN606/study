> 6주차는 챕터 15 ~ 16을 읽고 진행한다.

# 15장. 타임라인 격리하기

이번 스터디는 책에 나오는 예제를 직접 고쳐보는 방식으로 진행됐다.

```javascript
let carts = [];
let total = 0;

function getRandomInt(max) {
  return Math.floor(Math.random() * max);
}

function costAjax(carts, callback) {
  setTimeout(() => {
    callback(carts.reduce((acc, { price }) => acc + price, 0));
  }, getRandomInt(3) * 1000);
}

function shoppingAjax(cart, callback) {
  setTimeout(() => {
    callback(2);
  }, getRandomInt(3) * 1000);
}

function addItem(cart, item) {
  return [...cart, item];
}

function addItemToCart(item) {
  carts = addItem(carts, item);

  calculateCartTotal();
}

function calculateCartTotal() {
  total = 0;
  costAjax(carts, function (cost) {
    total += cost;
    shoppingAjax(carts, function (shipping) {
      total += shipping;
      render();
    });
  });
}

function handleClick() {
  addItemToCart({
    name: "신발",
    price: 6,
  });
}

function render() {
  document.querySelector("#app").innerHTML = `
    <div>
      <h1>Hello</h1>
      <p>
        합계: ${total}
      </p>
      <button id="button" type="button">상품 담기</button>
    </div>
  `;

  document.getElementById("button").addEventListener("click", handleClick);
}

render();
```

위의 예제 코드를 보고

1. 타임라인 그리기

- 액션 확인하기
- 액션 그리기
- 단순화 하기

2. 타임라인 보고 분석하기
3. 문제 해결하기
   의 순서대로 진행했다.

### 타임라인 다이어그램 그리기 & 분석하기

```
타임라인 다이어그램

사용자 클릭(handleClick)
----------------
 cart 읽기(addItemToCart)
 cart 쓰기
 total 쓰기(calculateCartTotal)
 cart 읽기
 costAjax
-----------------
 total 읽기
 total 쓰기
 cart 읽기
 shoppingAjax
-----------------
 DOM 업데이트


문제가 되는 곳
calculateCartTotal
=> 자원이 공유 (전역변수 -> 지역변수)
전역변수

addItemToCart
```

### 문제 해결하기

- 전역변수를 지역변수로 변경한다.

```javascript
function calculateCartTotal(carts, callback) {
  let localTotal = 0;
  costAjax(carts, function (cost) {
    localTotal += cost;
    shoppingAjax(carts, function (shipping) {
      localTotal += shipping;
      render(localTotal);
    });
  });
}

function render(total = 0) {
  document.querySelector("#app").innerHTML = `
    <div>
      <h1>Hello</h1>
      <p>
        합계: ${total}
      </p>
      <button id="button" type="button">상품 담기</button>
    </div>
  `;

  document.getElementById("button").addEventListener("click", handleClick);
}
```
