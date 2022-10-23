# RxJS Observable
- Simple observable
- self-destruct
- creating/canceling
- core operators
  - map
  ```
  rray.prototype.map :: a => b; for all a in Array<a> 
  Rx.Observable.prototype.map :: a => b; for all a in Observable<a>
  ```
  - filter
  - reduce
  - scan
  
    just like `reduce` but returns each intermediate result as the accumulation process is happening and not all at once
---
## Pull versus Push
> Source Article: https://rxjs-dev.firebaseapp.com/guide/observable

|	| PRODUCER	|CONSUMER|
|---|---|---|
|Pull	|Passive: produces data when requested.	|Active: decides when data is requested.|
|Push	|Active: produces data at its own pace.	|Passive: reacts to received data.|

| |SINGLE|	MULTIPLE|
|---|---|---|
|Pull|	Function	|Iterator|
|Push|	Promise|	Observable|

- A Function is a lazily evaluated computation that synchronously returns a single value on invocation.
- A generator is a lazily evaluated computation that synchronously returns zero to (potentially) infinite values on iteration.
- A Promise is a computation that may (or may not) eventually return a single value.
- An Observable is a lazily evaluated computation that can synchronously or asynchronously return zero to (potentially) infinite values from the time it's invoked onwards.

> Observables are like functions with zero arguments, but generalize those to allow multiple values
```js
import { Observable } from 'rxjs';

const foo = new Observable(subscriber => {
  console.log('Hello');
  subscriber.next(42);
});

foo.subscribe(x => {
  console.log(x);
});
foo.subscribe(y => {
  console.log(y);
});
```
> Subscribing to an Observable is analogous to calling a Function.

> Observables are able to deliver values either synchronously or asynchronously.

> What is the difference between an Observable and a function? 
>Observables can "return" multiple values over time, something which functions cannot.

Conclusion:
  
- `func.call()` means "give me one value synchronously"
- `observable.subscribe()` means "give me any amount of values, either synchronously or asynchronously"

## Anatomy of an Observable

Core Observable concerns:

- Creating Observables
  > Observables can be created with new Observable. Most commonly, observables are created 
  using creation functions, like of, from, interval, etc.
- Subscribing to Observables
  > Subscribing to an Observable is like calling a function, providing callbacks where the data will be delivered to.
- Executing the Observable
  > The code inside new Observable(function subscribe(subscriber) {...}) represents an "Observable execution"
  ```
  next*(error|complete)?
  ```
  > In an Observable Execution, zero to infinite Next notifications may be delivered.  If either an Error or Complete notification is delivered, then nothing else can be delivered afterwards.
- Disposing Observables
  > When you subscribe, you get back a Subscription, which represents the ongoing execution. Just call unsubscribe() to cancel the execution.
  ```js
  function subscribe(subscriber) {
    const intervalId = setInterval(() => {
      subscriber.next('hi');
    }, 1000);
  
    return function unsubscribe() {
      clearInterval(intervalId);
    };
  }
  
  const unsubscribe = subscribe({next: (x) => console.log(x)});
  
  // Later:
  unsubscribe(); // dispose the resources
  ```
