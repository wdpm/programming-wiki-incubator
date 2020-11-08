# use vuex in vue-cli project

## file structure
``` bash
│  ...
│  main.js
├─assets
│      ...
├─components
│      ...
├─router
│      ...
└─store
    │  store.js
    └─modules
            products.js
```

## store.js
```html
import Vue from 'vue';
import Vuex from 'vuex';
import products from './modules/products';

Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    products
  }
});
```

## products.js
```html
const state = {
    products: {}
};

const getters = {
    products: state => state.products
};

const actions = {
    initStore: ({commit}) => {
      axios.get('static/products.json')
      .then((response) =>{
        console.log(response.data.products);
        commit('SET_STORE', response.data.products )
      });
    }
};

const mutations = {
    'SET_STORE' (state, products) {
      state.products = products;
    }
};

export default {
    state,
    getters,
    actions,
    mutations,
}

```

## main.js
```html
import Vue from 'vue'
import App from './App'
import { store } from './store/store';

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  template: '<App/>',
  components: { App }
})
```