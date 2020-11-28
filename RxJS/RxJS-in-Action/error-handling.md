# error handling
## Example
```js
const ajax = function (url) {
    return new Promise(function (resolve, reject) {
        let req = new XMLHttpRequest();
        req.responseType = 'json';
        req.open('GET', url);
        req.onload = function () {
            if (req.status === 200) {
                let data = JSON.parse(req.responseText);
                resolve(data);
            } else {
                reject(new Error(req.statusText));
            }
        };
        req.onerror = function () {
            reject(new Error('IO Error'));
        };
        req.send();
    });
};
```
```js
ajax('/data')
    .then(()=>{
        // do something
    })
    .catch(error => console.log(`Error fetching data: ${error.message}`))
```

insert multiple asynchronous calls to catch() in series
```js
ajax('/data')
    .then(item => ajax(`/data/${item.getId()}/info`))
    .catch(error => console.log(`Error fetching data: ${error.message}`))
    .then(dataInfo => ajax(`/data/images/${dataInfo.img}`))
    .catch(error => console.log(`Error each data item: ${error.message}`))
    .then(showImg)
    .catch(error => console.log(`Error image: ${error.message}`))
```

## Try functional
```js
class Try {
    constructor(val) {
        this._val = val;
    }

    static of(fn) {
        try {
            return new Success(fn());
        } catch (error) {
            return new Failure(error);
        }
    }

    map(fn) {
        return Try.of(() => fn(this._val));
    }
}

class Success extends Try {
    getOrElse(anotherVal) {
        return this._val;
    }

    getOrElseThrow() {
        return this._val;
    }
}

class Failure extends Try {
    map(fn) {
        return this;
    }

    getOrElse(anotherVal) {
        return anotherVal;
    }

    getOrElseThrow() {
        if (this._val !== null) {
            throw this._val;
        }
    }
}
```

example code
```js
let record = Try.of(() => findRecordById('123'))
.map(processRecord)
.getOrElse(new Record('123', 'RecordA'));
```
比以下代码更清晰
```js
let record;
try {
    record = findRecordById('123');
    processRecord(record);
}
catch (e) {
    record = new Record('123', 'RecordA');
}
```

## The RxJS way of dealing with failure
- Propagating errors to observers (向上传递错误，自身不处理)
- Catching errors and reacting accordingly （捕获错误，根据错误类型作对应处理）
- Retrying a failed operation for a fixed number of times （对于已失败的操作，重试固定次数）
- Reacting to failed retries（对失败的重试做出反应）

### Propagating errors to observers
```js
const computeHalf = x => Math.floor(x / 2);

Rx.Observable.of(2,4,5,8,10)
   .map(num => {
      if(num % 2 !== 0) {
        throw new Error(`Unexpected odd number: ${num}`); //#A
      }
      return num;
   })
   .map(computeHalf)
   .subscribe(
       function next(val) {
          console.log(val);
       },
       function error(err) {
          console.log(`Caught: ${err}`); //#B
       },
       function complete() {
          console.log('All done!');
       }
    );
```
```
1
2
"Caught: Error: Unexpected odd number: 5"
```

### Catching errors and reacting accordingly
> Recovering from an exception using catch()
```js
Rx.Observable.of(2, 4, 5, 8, 10)
    .map(num => {
        if (num % 2 !== 0) {
            throw new Error(`Unexpected odd number: ${num}`);
        }
        return num;
    })
    .catch(err => Rx.Observable.of(6))
    .map(n => n / 2)
    .subscribe(
        (next) => console.log(val),
        (error) => console.log(`Caught: ${err}`),
        () => console.log('All done!'));
```
```
1
2
3
"All done!"
```
### Retrying a failed operation for a fixed number of times
```js
Rx.Observable.of(2, 4, 5, 8, 10)
    .map(num => {
        if (num % 2 !== 0) {
            throw new Error(`Unexpected odd number: ${num}`);
        }
        return num;
    })
    .retry(3)
    .subscribe(
        num => console.log(num),
        err => console.log(err.message)
    );
```
重试三次。输出结果如下：
```
2
4
---开始重试
2
4
2
4
2
4
----结束重试
```

或者，使用占位值替换出错的地方
```js
Rx.Observable.of(2, 4, 5, 8, 10)
    .map(num => {
        if (num % 2 !== 0) {
            throw new Error(`Unexpected odd number: ${num}`);
        }
        return num;
    })
    .retry(3)
    .catch(err$ => Rx.Observable.of(6))
    .subscribe(
        num => console.log(num),
        err => console.log(err.message));
```
```
2
4
---开始重试
2
4
2
4
2
4
----结束重试
6
```

### Reacting to failed retries
```js
const maxRetries = 3;
Rx.Observable.of(2, 4, 5, 8, 10)
    .map(num => {
        if (num % 2 !== 0) {
            throw new Error(`Unexpected odd number: ${num}`);
        }
        return num;
    })
    .retryWhen(errors$ =>
        errors$.scan((errorCount, err) => {
            if (errorCount >= maxRetries) {
                throw err;
            }
            return errorCount + 1;
        }, 0)
    )
    .subscribe(
        num => console.log(num),
        err => console.log(err.message));
```
```
2
4
2
4
2
4
2
4
```

## Summary
- Imperative error handling has many drawbacks that make it incompatible with FP.
- Value containers, like `Try`, provide a fluent, expressive mechanism for transforming values immutably.
- The `Try` wrapper is a functional data type used to consolidate and abstract
exception handling so that you can sequentially map functions to values.
- RxJS implements many useful and powerful operators that allow you to catch
and retry failed operations in a way that doesn’t break the flow of the stream
and the declarative nature of an RxJS stream declaration.
- RxJS provides operators such as catch(), retry(), retryWhen(), and finally()
that you can combine to create sophisticated error-handling schemes.