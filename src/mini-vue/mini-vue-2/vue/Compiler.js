import Watcher from "./Watcher.js";

export default class Compiler {
  el = null
  vm = null

  constructor(vm) {
    this.el = vm.$el;
    this.vm = vm;
    this.compile(this.el)
  }

  // 编译模板，处理文本节点和元素节点
  compile(el) {
    let childNodes = el.childNodes
    Array.from(childNodes).forEach(node => {
      // 处理文本节点
      if (this.isTextNode(node)) {
        this.compileText(node)
        // 处理元素节点
      } else if (this.isElementNode(node)) {
        this.compileElement(node)
      }
      // 判断node是否有子节点，如果有递归
      if (node.childNodes && node.childNodes.length) {
        this.compile(node)
      }
    })
  }

  // 编译元素节点，处理指令
  compileElement(node) {
    const attrs = node.attributes
    Array.from(attrs).forEach(attr => {
      let attrName = attr.name
      if (this.isDirective(attrName)) {
        this.update(node, attr.value, attrName.substr(2))
      }
    })
  }

  update(node, key, attrName) {
    const updateFn = this[attrName + 'Updater']
    updateFn && updateFn.call(this, node, key, this.vm[key])
  }

  textUpdater(node, key, value) {
    node.textContent = value
    new Watcher(this.vm, key, (newValue) => {
      node.textContent = newValue
    })
  }

  modelUpdater(node, key, value) {
    node.value = value
    new Watcher(this.vm, key, (newValue) => {
      node.value = newValue
    })
    node.addEventListener('input', () => {
      this.vm[key] = node.value
    })
  }

  // 编译文本节点，处理差值表达式
  compileText(node) {
    const reg = /{{(.+?)}}/
    node.textContent = node.textContent.replace(reg, (match, matchVal) => {
      const key = matchVal.trim()
      new Watcher(this.vm, key, (newValue) => {
        node.textContent = newValue
      })
      return this.vm[key]
    })
  }

  //判断元素属性是否是指令
  isDirective(attrName) {
    return attrName.startsWith('v-')
  }

  // 判断节点是否是文本节点
  isTextNode(node) {
    return node.nodeType === 3
  }

  // 判断节点是否是元素节点
  isElementNode(node) {
    return node.nodeType === 1
  }
}
