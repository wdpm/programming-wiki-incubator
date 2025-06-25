import Dep from "./Dep.js";

export default class Watcher {
  vm = null
  key = null
  callBack = null
  oldValue = null

  constructor(vm, key, callBack) {
    this.vm = vm;
    this.key = key;
    this.callBack = callBack;

    Dep.target = this
    this.oldValue = this.vm[key]
    Dep.target = null
  }


  update() {
    let newValue = this.vm[this.key]
    // 脏值检测
    if (this.oldValue === newValue) {
      return
    }
    this.callBack(newValue)
  }
}
