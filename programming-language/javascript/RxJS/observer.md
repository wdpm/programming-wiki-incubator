# Observer
> An Observer is a consumer of values delivered by an Observable

> Observers are just objects with three callbacks, one for each type of notification that an Observable may deliver.
 
```js
const observer = {
  next: x => console.log('Observer got a next value: ' + x),
  error: err => console.error('Observer got an error: ' + err),
  complete: () => console.log('Observer got a complete notification'),
};
```

```js
observable.subscribe(observer);
```
