# notes

## chapter 1
skip

## chapter 2
skip

## chapter 3
skip

## chapter 4
- React.createElement("h1", null, "Baked Salmon")
- class IngredientsList extends React.Component {...}

不要再使用 creatClass 朴素类来创建组件，而是使用类组件。还可以使用 FC，或者 hooks。

## chapter 5

chapter-05/recipe-app 起步 demo

## chapter 6

React state "The Old Way"
```js
import React, {Component} from "react";

export default class StarRating extends Component {
  constructor(props) {
    super(props);
    this.state = {
      starsSelected: 0
    };
    this.change = this.change.bind(this);
  }

  change(starsSelected) {
    this.setState({starsSelected});
  }

  render() {
    const {totalStars} = this.props;
    const {starsSelected} = this.state;
    return (
        <div>
          {[...Array(totalStars)].map((n, i) => (
              <Star
                  key={i}
                  selected={i < starsSelected}
                  onClick={()=> this.change(i + 1)}
              />
          ))}
          <p>
            {starsSelected} of {totalStars} stars
          </p>
        </div>
    );
  }
}
```
也就是需要自己维护 instance 属性的更新。

---
```jsx
export default function StarRating({totalStars = 5}) {
  const [selectedStars, setSelectedStars] = useState(0);
  return (
      <>
        {createArray(totalStars).map((n, i) => (
            <Star
                key={i}
                selected={selectedStars > i}
                onSelect={()=> setSelectedStars(i + 1)}
            />
        ))}
        <p>
          {selectedStars} of {totalStars} stars
        </p>
      </>
  );
}
```
useState 将语法简化了。

但是，函数组件和 Hooks 是 React 的未来，我们不会回头。


---

例子参考。

Star Rating
- Completed Star Component - ([run it](https://codesandbox.io/s/learning-react-star-rating-4-gxvb5?file=/src/Star.js))
- Advanced Star Component - ([run it](https://codesandbox.io/s/learning-react-star-rating-5-86ngm?file=/src/StarRating.js))

实践：将状态存储在单个位置（一般是顶层组件），而不是将其分布在树中的许多不同组件中。

Color Organizer

- Refactor*: `useInput` hook - ([run it](https://codesandbox.io/s/learning-react-color-organizer-5-umj5q?file=/src/hooks.js))
- Feature: Adding a Color to State - ([run it](https://codesandbox.io/s/learning-react-color-organizer-6-ewxpp?file=/src/App.js))
- Refactor*: Colors In Context - ([run it](https://codesandbox.io/s/learning-react-color-organizer-7-lg9y3?file=/src/index.js))
- Refactor*: `useColors` hook - ([run it](https://codesandbox.io/s/learning-react-color-organizer-8-jqchd?file=/src/ColorProvider.js))
- BONUS: Color Organizer App (with emotion css)- ([run it](https://codesandbox.io/s/learning-react-color-organizer-9-ypf8r?file=/src/ColorList.js))

Context 可以避免嵌套层级过深的 props 传递。而是基于组件的状态管理。

这一章，学习绿 useState 和 useContext 的使用。

## chapter 7

useEffect() 副作用函数，一般是某些数据变化时的监听回调函数。

```js
useEffect(() => {
    welcomeChime.play();
    return () => goodbyeChime.play();
}, []);
```
>This means that you can use useEffect for setup and teardown. The empty array
 means that the welcome chime will play once on first render. Then, we’ll return a
 function as a cleanup function to play a goodbye chime when the component is
 removed from the tree．

---
修复 useEffect 诡异的多次渲染问题：

- useMemo: 对于数组类型
- useCallback: 对于函数类型

---

When to useLayoutEffect

- Basic `useLayoutEffect` - ([run it](https://codesandbox.io/s/learning-react-uselayouteffect-1-bmxqw?file=/src/App.js))
- Custom: `useWindowSize` - ([run it](https://codesandbox.io/s/learning-react-uselayouteffect-2-vuir1?file=/src/App.js))
- Custom: `useMousePosition` - ([run it](https://codesandbox.io/s/learning-react-uselayouteffect-3-6ks6x?file=/src/App.js))

useLayoutEffect is called at a specific moment in the render cycle. The series of events is as follows:
1. Render
2. useLayoutEffect is called
3. Browser paint: the time when the component’s elements are actually added to theDOM
4. useEffect is called

windows 窗口size的实时跟踪，以及鼠标位置的实时打印，都是useLayoutEffect应用场景。

---

useReducer, 是useState的语法简化形式。
```js
const [checked, toggle] = useReducer(checked => !checked, false);
```

---

memo

```js
const Cat = ({ name }) => {
    console.log(`rendering ${name}`);
    return <p>{name}</p>;
};

const PureCat = memo(Cat);

cats.map((name, i) => <PureCat key={i} name={name} />);
```
```js
const PureCat = memo(
   Cat,
   (prevProps, nextProps) => prevProps.name === nextProps.name
);
```
第二个参数为false，重新渲染；为true，不会触发重新渲染。

---

再次复习useCallback

```js
const PureCat = memo(Cat);
function App() {
    // 这里meow函数不会改变   
    const meow = useCallback(name => console.log(`${name} has meowed`, [])
    return <PureCat name="Biscuit" meow={meow} />
}
```

## chapter 8

请求网络数据，常见处理：loading state -> (error msg) | (data reached) -> cancel loading state.

虚拟列表：
- faker to mock big list data
- react-windows or react-virtualized

Waterfall Requests: 瀑布流请求，指的是后一个请求依赖于前一个请求的数据。因此只能串行，性能很差。

```
- github user Info(need field "login")
  - repos of a specific user(need field "login")
    - one repo info (need field "login"+"repo")
      -  the readme content of a specific repo (need the result of repo info)
```
=>
```
- github user Info(need field "login")
- repos of a specific user(need field "login")
- one repo info, default is the first repo (need field "login"+"repo")
  -  the readme content of a specific repo (need the result of repo info)
```
也就是前三个可以并行请求，第4个依赖于第三个请求的结果，需要等待。

---

取消请求。

Attempting to change state values in an unmounted component will cause a error.

```js
export function useMountedRef() {
    const mounted = useRef(false);
    useEffect(() => {
        mounted.current = true;
        return () => (mounted.current = false);
    });
    return mounted;
}
```
demo用法
```js
// 在一个FC内
const mounted = useMountedRef();
const loadReadme = useCallback(async (login, repo) => {
    setLoading(true);
    const uri = `https://api.github.com/repos/${login}/${repo}/readme`;
    const { download_url } = await fetch(uri).then(res =>
        res.json()
    );
    const markdown = await fetch(download_url).then(res =>
        res.text()
    );
    // 仅当components还没有卸载时，才进行md设置
    if (mounted.current) {
        setMarkdown(markdown);
        setLoading(false);
    }
}, []);
```
---

GraphQL

- 在线测试：https://docs.github.com/en/graphql/overview/explorer

```
query {
    user(login: "wdpm") {
        id
        login
        name
        location
        avatarUrl
    }
}
```
定义函数查询：
```
query findRepos($login: String!) {
  user(login: $login) {
    login
    name
    location
    avatar_url: avatarUrl
    repositories(first: 100) {
      totalCount
      nodes {
        name
      }
    }
  }
}
```
```
{
  "login": "wdpm"
}
```

## chapter 9

Suspense

- [React Suspense Docs](https://reactjs.org/docs/concurrent-mode-suspense.html): Read up on the latest with React Suspense
- [Understanding Fiber](https://github.com/acdlite/react-fiber-architecture): This doc from Andrew Clark on the React team does a deep dive on Fiber's design

---
错误边界：

Each ErrorBoundary will render a fallback if an error occurs anywhere within their children.

---

Using Suspense with Data

```js
export default function App() {
    return (
        <Suspense fallback={<GridLoader />}>
            <ErrorBoundary>
                <Status />
            </ErrorBoundary>
        </Suspense>
    );
}
```
- 成功，status 正常渲染
- 出错，被ErrorBoundary 处理
- pending，被Suspense处理

---

throwing promise

```js
const loadStatus = () => {
  console.log("load status");
  throw new Promise(resolves => setTimeout(resolves, 3000));
};
function Status() {
  const status = loadStatus();
  return <h1>status: {status}</h1>;
}
export default function App() {
  return (
      <Suspense fallback={<GridLoader />}>
        <ErrorBoundary>
          <Status />
        </ErrorBoundary>
      </Suspense>
  );
}
```
注意这里有死循坏，具体参考对应代码文件夹。

这个问题的解决参考: chapter-09/suspenseful-data-source.js 。

```
The Gnar component will be rendered several
times before it actually returns a response. Each time Gnar is rendered,
resource.read() is invoked. The first time Gnar is rendered, a promise is thrown.
That promise is handled by the Suspense component and a fallback component will
be rendered.

When the promise has resolved, the Suspense component will attempt to render Gnar
again. Gnar will invoke resource.read() again, but this time, assuming everything
went OK, resource.read() will successfully return Gnar, which is used to render the
state of Gnar in an h1 element. If something went wrong, resource.read() would
have thrown an error, which would be handed by the ErrorBoundary
```

---

reconciliation algorithm Fiber.

- The first change with 16.0 was the separation of the renderer and the reconciler.

## chapter 10

Typechecking

- PropTypes: `npm install prop-types --save-dev`
- Flow Project : `npx create-react-app in-the-flow`
- TypeScript Project: 正统的类型加强，推荐。

code coverage

## chapter 11

- chapter-11/company-website: react-router 入门使用
- chapter-11/color-organizer: 这个例子很好

## chapter 12

Server Rendering

React 可以同构呈现，这意味着它可以在浏览器以外的平台上运行。
这意味着我们可以在 UI 到达浏览器之前在服务器上渲染它。利用服务器渲染，我们可以提高应用程序的性能、可移植性和安全性。

- 同构 => 运行在多个平台
- universal => 运行在多个环境

---
对比
```js
// Renders html directly in the browser
ReactDOM.render(<Star />);

// Renders html as a string
let html = ReactDOM.renderToString(<Star />);
```
---
```json
"dev:build-server": "cross-env NODE_ENV=development webpack --config webpack.server.js --mode=development -w",
"dev:start": "nodemon ./server-build/index.js",
"dev": "npm-run-all --parallel build dev:*"
```
- dev:build-server 监听配置文件变动，如果变动，就编译变动，这会引起node index.js的重启。
- dev:start 类似于 node index.js 启动一个脚本
- dev: 并行运行 build 和 dev:* 指令

---

除了上面的SSR技术，还有更加强大的Next.js框架来支持SSR。例子参考：chapter-12/pets-next。

```
Page                             Size     First Load JS
┌ ○ /                            1.42 kB        66.2 kB
├ ○ /404                         3.01 kB        67.8 kB
├ ○ /Header                      1.34 kB        66.1 kB
├ ○ /Layout                      1.39 kB        66.2 kB
└ λ /Pets                        4.07 kB        68.8 kB
+ First Load JS shared by all    64.8 kB
  ├ chunks/commons.2efaa7.js     11.3 kB
  ├ chunks/framework.1daf1e.js   39.9 kB
  ├ chunks/main.8ea908.js        9.36 kB
  ├ chunks/pages/_app.47248f.js  3.51 kB
  └ chunks/webpack.e06743.js     751 B

λ  (Server)  server-side renders at runtime (uses getInitialProps or getServerSideProps)
○  (Static)  automatically rendered as static HTML (uses no initial props)
●  (SSG)     automatically generated as static HTML + JSON (uses getStaticProps)
   (ISR)     incremental static regeneration (uses revalidate in getStaticProps)
```

---

Gatsby: 内容驱动的网站
