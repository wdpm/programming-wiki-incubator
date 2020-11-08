# setup vue test env
## install dependencies
``` bash
# test-runner: Karma/Jest/mocha-webpack
npm install --save-dev @vue/test-utils mocha-webpack

# headless browser: jsdom/jsdom-global
npm install --save-dev jsdom jsdom-global

# assertion library: Chai/Expect
npm install --save-dev expect

# exclude certain npm dependencies
npm install --save-dev webpack-node-externals
```
## update webpack config
Update `build/webpack.base.conf.js`
``` js
// test specific setups
if (process.env.NODE_ENV === 'test') {
  module.exports.externals = [require('webpack-node-externals')()]
  module.exports.devtool = 'inline-cheap-module-source-map'
}
```

Add `test/setup.js`
``` js
require('jsdom-global')()
global.expect = require('expect')
```

Update `package.json`
``` json
"test2": "mocha-webpack --webpack-config build/webpack.base.conf.js --require test/setup.js test/*.spec.js"
```
Next step is to write test cases in `test` folder.

## run test
```bash
npm run test2
```