# use import in node 14

test.js file:
```
export default function test() {
  console.log('Hello, World');
}
```

The index.js file imports the test.js file:
```js
import test from './test.js';
test();
```

To run index.js, you need to create a package.json file with a type property set to "module". 
Below is a minimal package.json file to enable running index.js in Node.js 14.
```json
{ "type": "module" }
````
