# the road to react notes

- [the-road-to-react-online](https://the-road-to-react-online.vercel.app/manuscript/foreword.html)
- [source code of the book](https://github.com/the-road-to-learn-react/hacker-stories)

the road to react notes:
- JSX: js mixed html fragment, return (...)
- React functional component
- React DOM syntax
- React Props: immutable -> props down/ event up
- React State: mutable, should lifting state when needed. In react, state is reactive.
- React Hook: useState()/useEffect() similar method,useEffect() 主要用于集中隔离副作用的代码逻辑
- nested object props destructing
- Why is array destructuring used for React Hooks like useState and object destructuring for props?
  > The benefit of having an array returned from useState is that the values can be given any name in the destructuring operation. If use object desctructing, it can use any name when desctructing.
- React Side Effect
```js
 React.useEffect(() => {
    localStorage.setItem('search', searchTerm);
  }, [searchTerm]);
```

- custom hook
```js
const useStorageState = (key, initialState) => {
  const [value, setValue] = React.useState(
    localStorage.getItem(key) || initialState
 );

  React.useEffect(() => {
    localStorage.setItem(key, value);
  }, [value, key]);

  return [value, setValue];
};
```
- React Fragment <React.Fragment>
- React component composition With the React children prop
- inline handlers/callback handlers
- mock list data with Promise
- React conditional rendering
```js
{isLoading? (...) : (...)}
```
- 扩展阅读：https://reactjs.org/blog/2016/07/13/mixins-considered-harmful.html

---

notes part2:
- useReducer(a hook): Using useReducer over useState makes sense as soon as multiple stateful values are dependent on each other or related to one domain.
> https://www.robinwieruch.de/javascript-reducer/
- use fecth API to fetch hacknews
- client-side search vs server-side search
- useCallback(): memorized function
- substitue native fecth API with axios library
- further reading: https://www.robinwieruch.de/react-libraries
- using async/await with try/catch over then/catch makes it often more readable
- useReducer vs Redux vs useContext?
>Where your state is managed is a crucial difference between Redux and useReducer. While Redux creates one global state container -- which hangs somewhere above your whole application --, useReducer creates a independent component co-located state container within your component
https://www.robinwieruch.de/redux-vs-usereducer/
- React class components history:
  createClass components -> class components -> functional components
```js
class InputWithLabel extends React.Component {
  render() {
    const {
      id,
      value,
      type = 'text',
      onInputChange,
      children,
    } = this.props;

    return (
      <>
        <label htmlFor={id}>{children}</label>
        &nbsp;
        <input
          id={id}
          type={type}
          value={value}
          onChange={onInputChange}
        />
      </>
    );
  }
}
```
```js
const InputWithLabel = ({
  id,
  value,
  type = 'text',
  onInputChange,
  children,
}) => (
  <>
    <label htmlFor={id}>{children}</label>
    &nbsp;
    <input
      id={id}
      type={type}
      value={value}
      onChange={onInputChange}
    />
  </>
);
```

With the addition of React Hooks, function components were granted the same features as class components, with state and side-effects. And since there was no longer any practical difference between them, the community chose function components over class components since they are more lightweight.
Throughout React's history, the ref and its usage had a few versions:
- String Refs (deprecated)
- Callback Refs (used in class and function components)
- createRef Refs (exclusive for class components)
- useRef Hook Refs (exclusive for function components)
- https://www.robinwieruch.de/react-css-styling/ react中css的几种不同的方式
    - css in css(pure css, sass, scss, less;css modules)
    - css in jS(styled component, no intermediate css file)
    - utility css
  > The advantage of CSS modules is that we receive an error in JavaScript each time a style isn't defined. In the standard CSS approach, unmatched styles in JavaScript and CSS files might go unnoticed. （已编辑）

---

- use vite-plugin-svgr to support SVG in vite
- performance tips:
    - strict mode turn on or off for developing
    - Read more about running useEffect only on update. 自定义一个bool变量开关来控制。开始为false，后面都是true
    - Read more about running useEffect only once.
- Try it with the combination of React memo and useCallback to avoid re-rendering child componets when parent component re-renders
- avoid heavy computation by useMemo()
```js
const sumComments = React.useMemo(
    () => getSumComments(stories),
    [stories]
  );
```
- keep using typescript safe type

---

- use Vitest to setup test cases and test suite
- unittest for functions, 函数的input和output是否满足预期
- unittest for components。挂载component到js-dom，修改属性，判定是否相关响应函数是否调用1次；点击按钮，测试对应的handler是否调用。
- integration tests： take axios request for example, 请求前后是否含有loading文本在页面中。测试 happy path 以及unhappy path。集成测试专注于用户真实的交互动作以及可能产生的后果。
- snapshot test：快照测试，适用于不经常变化的组件逻辑测试。
- project file organization：
```js
- List/
-- index.jsx
-- style.css
-- test.js
-- types.js
```
- loadsh sortBy utility function; add reverse sort by adding a isReverse state
- Remember Last Searches：将url变更为urls，逐步抽象出theLastSearches组件
- 分页请求数据：拆分URL搜索关键词，将page记为state状态。扩展，分页组件；无限滚动分页，使用viewport检测替代more按钮点击。