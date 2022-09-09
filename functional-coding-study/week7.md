> 챕터 17 ~ 19

16장(443p)에서 마지막에 DOM을 업데이트 하는 부분이 문제가 되고 있다고 말한다. 문제를 해결하기 위해서는 DOM이 업데이트 되는 순서를 보장해야 하며, Queue를 이용해 순서대로 실행되도록 보장할 수 있다.

`Queue`클래스를 만든다.

```javascript
class Queue {
  constructor() {
    this.queue = [];
    this.working = false;
  }

  runNext() {
    if (this.working) {
      return;
    }

    if (this.isEmpty()) {
      return;
    }

    this.working = true;

    const action = this.dequeue();

    action(() => {
      this.working = false;
      setTimeout(() => {
        this.runNext();
      }, 0);
    });
  }

  enqueue(action) {
    this.queue.push(action);
  }

  dequeue() {
    return this.queue.shift();
  }

  isEmpty() {
    return this.queue.length === 0;
  }
}

const queue = new Queue();
```

그리고 큐를 이용해서 실행 순서를 보장한다.

```javascript
function addItemToCart(item) {
  carts = addItem(carts, item);

  queue.enqueue((callback) => {
    calculateCartTotal(carts, render, callback);
  });
  queue.runNext();
}

async function calculateCartTotal(localCarts, render, callback) {
  let localTotal = 0;
  const [cost, shipping] = await Promise.all([
    new Promise((resolve) => {
      costAjax(localCarts, function (cost) {
        resolve(cost);
      });
    }),
    new Promise((resolve) => {
      shoppingAjax(localCarts, function (shipping) {
        resolve(shipping);
      });
    }),
  ]);
  render(localTotal + cost + shipping);
  callback();
}
```
