# operators

> A Pipeable Operator is a function that takes an Observable as its input and returns another Observable. 
> It is a pure operation: the previous Observable stays unmodified.

## Piping

```js
obs.pipe(
  op1(),
  op2(),
  op3(),
  op3(),
)
```

## Creation Operators
```js
import { interval } from 'rxjs';

// It takes a number (not an Observable) as input argument, and produces an Observable as output:
const observable = interval(1000 /* number of milliseconds */);
```

Use the pipe() function to make new operators
```js
import { pipe } from 'rxjs';
import { filter, map } from 'rxjs/operators';

function discardOddDoubleEven() {
  return pipe(
    filter(v => ! (v % 2)),
    map(v => v + v),
  );
}
```

Creating new operators from scratch
```js
import { Observable } from 'rxjs';

function delay(delayInMillis) {
  return (observable) => new Observable(observer => {
    // this function will called each time this
    // Observable is subscribed to.
    const allTimerIDs = new Set();
    const subscription = observable.subscribe({
      next(value) {
        const timerID = setTimeout(() => {
          observer.next(value);
          allTimerIDs.delete(timerID);
        }, delayInMillis);
        allTimerIDs.add(timerID);
      },
      error(err) {
        observer.error(err);
      },
      complete() {
        observer.complete();
      }
    });
    // the return value is the teardown function,
    // which will be invoked when the new
    // Observable is unsubscribed from.
    return () => {
      subscription.unsubscribe();
      allTimerIDs.forEach(timerID => {
        clearTimeout(timerID);
      });
    }
  });
}
```

Note that you must

- implement all three Observer functions, next(), error(), and complete() when subscribing to the input Observable.
- implement a "teardown" function that cleans up when the Observable completes (in this case by unsubscribing and clearing any pending timeouts).
- return that teardown function from the function passed to the Observable constructor.
