import Observer from "./Observer.js";
import Compiler from "./Compiler.js";

export default class QVue {
  $options = null
  $data = null
  $el = null

  constructor(options) {
    this.$options = options || {};
    this.$data = options.data || {};
    this.$el = typeof options.el === 'string' ? document.querySelector(options.el) : options.el;
    this.proxyData(this.$data)
    new Observer(this.$data)
    new Compiler(this)
  }

  proxyData(data) {
    Object.keys(data).forEach(key => {
      Object.defineProperty(this, key, {
        enumerable: true,
        configurable: true,
        get() {
          return data[key]
        },
        set(newValue) {
          if (newValue === data[key]) {
            return
          }
          data[key] = newValue
        }
      })
    })
  }
}
