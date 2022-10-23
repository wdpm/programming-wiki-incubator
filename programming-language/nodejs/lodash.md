# lodash
## TOC
```
"1: Getting Started",
"2: Sort Me",
"3: In Every Case",
"4: Everyone Is Min",
"5: Chain Mail",
"6: Count the Comments",
"7: Give Me an Overview",
"8: Analyze",
"9: Start templating"
```

## Contents

### "1: Getting Started"
```js
const _ = require("lodash");

const filterwhere = function (item) {
    return _.filter(item, {active: true});
};

module.exports = filterwhere;
```
### "2: Sort Me"
```js
const _ = require("lodash");

const sorting = function (collection) {
    return _.sortBy(collection, item => -item.quantity);

    /* Also possible:
     return _.sortBy(collection,"quantity").reverse();
     */
};

module.exports = sorting;
```
### "3: In Every Case"
```js
const _ = require("lodash");

const inEveryCase = function(collection){
  
  // add a size attribute to the collection based on the item's population
  return _.forEach(collection, function(item) {
    if (item.population > 1) {
      item.size = "big";
    } else if(item.population > 0.5) {
      item.size = "med";
    } else {
      item.size = "small";
    }
  });
};

module.exports = inEveryCase;
```
### "4: Everyone Is Min"
```
{ Hamburg:   [14,15,16,14,18,17,20,22,21,18,19,23],
  Munich:    [16,17,19,20,21,23,22,21,20,19,24,23],
  Madrid:    [24,23,20,24,24,23,21,22,24,20,24,22],
  Stockholm: [16,14,12,15,13,14,14,12,11,14,15,14],
  Warsaw:    [17,15,16,18,20,20,21,18,19,18,17,20] }
```
```
{  hot: [ "Hottown" ],
  warm: [ "Town1", "Town2", "Town3" ] }
```
```js
const _ = require("lodash");

var tempsort = function (item) {
   
    var result = {
        hot: [],
        warm: []
    };
    
    // If temp > 19
    const check_temp = (item) => item > 19;

    // town: value ,townname: key/index
    _.forEach(item, function (town, townname) {
        if (_.every(town, check_temp)) {
            result.hot.push(townname);
        } else if (_.some(town, check_temp)) {
            result.warm.push(townname);
        }
    });

    return result;
};

module.exports = tempsort;
```
### "5: Chain Mail"

chain lets you chain or link several Lo-Dash methods together on a collection (arrays, objects) explicitly 
and then finally returns the value of the whole operation.
```js
const _ = require("lodash");

var wordsmodify = function (arr) {
    return _.chain(arr)
      .map(item => item + 'Chained')
      .map(item => item.toUpperCase())
      .sortBy()
      .value();
};

module.exports = wordsmodify;
```
### "6: Count the Comments"
```js
const _ = require("lodash");

const commentcount = function (comments) {
    var counted = [];

    // Group by username
    comments = _.groupBy(comments, "username");

    // item : value, name: key/index
    _.forEach(comments, function (item, name) {
        counted.push({
            username: name,
            comment_count: _.size(item)
        });
    });

    return _.sortBy(counted, "comment_count").reverse();
};

module.exports = commentcount;
```
### "7: Give Me an Overview"
```js
const _ = require("lodash");

const overview = function (orders) {
    return _.chain(orders)
        .groupBy('article')
        .map((item, key) => {
            return {
                article: parseInt(key),
                total_orders: _.reduce(item, (result, value) => result += value.quantity, 0)
            };
        })
        .sortBy(item => -item.total_orders);
};

module.exports = overview;
```
### "8: Analyze"
```js
const _ = require("lodash");

const analyze = function (item) {

    // Sort
    let sorted = _.sortBy(array, 'income');

    // Calculate average of all incomes
    let average = _.meanBy(array, item => item.income);

    return {
        average: average,
        // Filter underperformers
        underperform: _.filter(sorted, item => item.income <= average),
        // Filter overperformers
        overperform: _.filter(sorted, item => item.income > average)
    };
};

module.exports = analyze;
```
### "9: Start templating"
```
{ name: "Foo",
  login: [ 1407574431, 140753421 ]}
```
```js
const _ = require("lodash");

const template = function (inputvar) {

    let mytemplate = "Hello <%= name %> (logins: <%= login.length %>)";

    return _.template(mytemplate)(inputvar);
};

module.exports = template;
```