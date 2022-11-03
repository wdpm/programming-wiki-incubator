export default class Dep {
  static target = null
  subs = []

  addSub(sub) {
    if (sub && sub.update) {
      this.subs.push(sub)
    }
  }

  notify() {
    this.subs.forEach(sub => {
      sub.update()
    })
  }
}
