## jsx example

`HelloWorld.vue`
```html
<script>
  export default {
    name: 'HelloWorld',
    render(h) {
      return (
        <div>
          <div class="text-center"
               on-click={this.pressed}
               domPropsInnerHTML={this.msg}>
          </div>
        </div>
    )
    },
    data() {
      return {
        welcome: 'Hello World ',
        msg: `<h${this.header}>Hello World ${this.name}</h${this.header}>`,
      }
    },
    methods: {
      pressed() {
        alert('Clicked')
      }

    },
    props: ['header', 'name']
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
```
`App.vue`
```html
<template>
  <div id="app">
    <HelloWorld header="1" name="Erik"></HelloWorld>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld'

export default {
  name: 'App',
  components: {
    HelloWorld
  }
}
</script>

<style>
#app {

}
</style>
```