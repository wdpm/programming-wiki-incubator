## 设计模式

此文对 https://javascriptpatterns.vercel.app/patterns 进行摘要。

### singleton

```js
let instance;

class DBConnection {
    constructor(uri) {
        if (instance) {
            throw new Error('Only one connection can exist!');
        }
        this.uri = uri;
        instance = this;
    }

    connect() {
        this.isConnected = true;
        console.log(`DB ${this.uri} has been connected!`);
    }

    disconnect() {
        this.isConnected = false;
        console.log('DB disconnected');
    }
}

const databaseConnector = Object.freeze(new DBConnection());
const connection = databaseConnector;
```

- 使用 if 进行是否已经初始化的判断，如果否就初始化，如果是就报错。
- 使用 Object.freeze() 来冻结对象的属性，防止被修改。

### proxy 模式

> The Proxy object receives two arguments:
>1. The target object
>2. A handler object, which we can use to add functionality to the proxy.
    > This object comes with some built-in functions that we can use, such as get and set.

```js
const person = {
    name: "John Doe",
    age: 42,
    email: "john@doe.com",
    country: "Canada",
};

const personProxy = new Proxy(person, {
    get: (target, prop) => {
        console.log(`The value of ${prop} is ${target[prop]}`);
        return target[prop];
    },
    set: (target, prop, value) => {
        console.log(`Changed ${prop} from ${target[prop]} to ${value}`);
        target[prop] = value;
        return true;
    },
});
```

使用 Reflect API 简化代码

```js
new Proxy(person, {
    get: (target, property) => {
        // return target[property]
        return Reflect.get(target, property)
    },
    set: (target, property, value) => {
        // target[property]= value
        // return true;
        return Reflect.set(target, property, value)
    }
})
```

下面是一个验证器的应用

```js
import {isValidEmail, isAllLetters} from './validator.js';

const user = {
    firstName: 'John',
    lastName: 'Doe',
    username: 'johndoe',
    age: 42,
    email: 'john@doe.com',
};

const userProxy = new Proxy(user, {
    get: (obj, prop) => {
        return `${new Date()} | The value of ${prop} is ${Reflect.get(obj, prop)}`;
    },
    set: (obj, prop, value) => {
        if (prop === 'email') {
            if (!isValidEmail(value)) {
                console.log('Please provide a valid email.');
                return false;
            }
        }

        if (prop === 'username') {
            if (value.length < 3) {
                throw new Error('Please use a longer username.');
            } else if (!isAllLetters(value)) {
                throw new Error('You can only use letters');
            }
        }

        if (prop === 'age') {
            if (typeof value !== 'number') {
                throw new Error('Please provide a valid age.');
            }

            if (value < 18) {
                throw 'User cannot be younger than 18.';
            }
        }

        return Reflect.set(obj, prop, value);
    },
});
```

```js
// filename: validator.js

export function isValidEmail(email) {
    const tester =
        /^[-!#$%&'*+\/0-9=?A-Z^_a-z`{|}~](\.?[-!#$%&'*+\/0-9=?A-Z^_a-z`{|}~])*@[a-zA-Z0-9](-*\.?[a-zA-Z0-9])*\.[a-zA-Z](-?[a-zA-Z0-9])+$/;

    return tester.test(email);
}

export function isAllLetters(char) {
    if (typeof char !== 'string') {
        return false;
    }

    return /^[a-zA-Z]+$/.test(char);
}

```

### 观察者模式

模式描述

```js
function logger(data) {
    console.log(`${Date.now()}:${data}`);
}

observable.subscribe(logger)
observable.notify("Notify subscribers !")
```

实现 `observable.js`

```js
const observers = [];

export default Object.freeze({
    notify: (data) => observers.forEach((observer) => observer(data)),
    subscribe: (func) => observers.push(func),
    unsubscribe: (func) => {
        [...observers].forEach((observer, index) => {
            if (observer === func) {
                observers.splice(index, 1);
            }
        });
    },
});
```

client demo

```js
import Observable from "./observable";

function logger(data) {
    console.log(`${Date.now()} ${data}`);
}

Observable.subscribe(logger);
```

```js
import Observable from "./observable";

document.getElementById("my-button").addEventListener("click", () => {
    Observable.notify("Clicked!"); // Notifies all subscribed observers
});
```

### factory pattern

```js
const createUser = (firstName, lastName) => ({
    id: crypto.randomUUID(),
    createdAt: Date.now(),
    firstName,
    lastName,
    fullName: `${firstName} ${lastName}`,
});

createUser("John", "Doe");
createUser("Sarah", "Doe");
createUser("Lydia", "Hallie");
```

工厂模式对于需要创建大量对象，而这些对象有部分属性相同时非常实用。
但在很多时候，创建类的 instance 比创建不同的对象更加节省内存。因为在不同的 instances 能够共享 class 的 prototype。

### Prototype Pattern

```js
class Dog {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    bark() {
        console.log(`${this.name} is barking!`);
    }

    wagTail() {
        console.log(`${this.name} is wagging their tail!`);
    }
}

const dog1 = new Dog("Max", 4);
const dog2 = new Dog("Sam", 2);
const dog3 = new Dog("Joy", 6);
const dog4 = new Dog("Spot", 8);
```

注意这里 bark()和 wagTail() method 是挂载在原型链上的，不同的类实例可以共享这些方法定义。

## React patterns

### Container/Presentational Pattern

> ！注意：这是旧的 React 设计模式，仅为存档意义，不推荐使用。

这个模式的目的是分离关注点。将界面 UI 的显示逻辑 (render) 和数据获取处理的逻辑 (fetch) 分开。

- **Presentational Component**, that cares about how data is shown to the user.
- **Container Component**, that cares about what data is shown to the user.

实现

```js
// container/Listings.tsx 数据
import React from 'react';
import {Listings} from '../presentational/Listings';

export default function ListingsContainerComponent() {
    const [data, setData] = React.useState(null);

    React.useEffect(() => {
        fetch('https://house-lydiahallie.vercel.app/api/listings')
            .then((res) => res.json())
            .then((res) => setData(res));
    }, []);

    if (!data) return null;

    return <Listings listings={data.listings}/>;
}
```

```js
// presentational/Listings.tsx 显示
import React from 'react';
import {Listing} from './Listing';
import {ListingsGrid} from './ListingsGrid';

export function Listings(props) {
    return (
        <ListingsGrid>
            {props.listings.map((listing) => (
                <Listing key={listing.id} listing={listing}/>
            ))}
        </ListingsGrid>
    );
}

```

当然，也可以使用 hook 来替换这种模式，作用是等效的。

```js
import React from "react";
import useSWR from "swr";
import {LoadingListings, Listing, ListingsGrid} from "../components";

function Listings(props) {
    const {
        data: listings,
        loading,
        error,
    } = useSWR("https://my.cms.com/listings", (url) =>
        fetch(url).then((r) => r.json())
    );

    if (loading) {
        return <LoadingListings/>;
    }

    return (
        <ListingsGrid>
            {listings.map((listing) => (
                <Listing listing={listing}/>
            ))}
        </ListingsGrid>
    );
}
```

### Higher-Order Components

高阶组件（HOC）接收一个组件，修改这个组件，然后返回一个新的函数式组件。

示例代码

```js
export function withStyles(Component) {
    return (props) => {
        const style = {
            color: "red",
            fontSize: "1em",
            // Merge props
            ...props.style,
        };

        return <Component {...props} style={style}/>;
    };
}
```

```js
import {withStyles} from "./hoc/withStyles";

const Text = () => <p style={{fontFamily: "Inter"}}>Hello world!</p>;
const StyledText = withStyles(Text);
```

更加真实的 loader 例子

```js
import React from 'react';
import {LoadingSpinner} from '../components/LoadingSpinner';

export default function withLoader(Element, url) {
    return (props) => {
        const [data, setData] = React.useState(null);

        React.useEffect(() => {
            fetch(url)
                .then((res) => res.json())
                .then((res) => setData(res));
        }, []);

        if (!data) return <LoadingSpinner/>;

        return <Element {...props} data={data}/>;
    };
}

```

```js
//  ../components/LoadingSpinner.tsx
import React from 'react';

export const LoadingSpinner = () => (
    <div className="spinner-wrapper">
        <div className="la-ball-clip-rotate">
            <div></div>
        </div>
    </div>
);
```

### Render Props Pattern

这个模式通过 props 参数将 jsx 元素传递给函数式组件。

一个关于温度显示的例子

```js
function Input(props) {
    const [value, setValue] = useState("");

    return (
        <>
            <input value={value} onChange={(e) => setValue(e.target.value)}/>
            {props.renderKelvin({value: value + 273.15})}
            {props.renderFahrenheit({value: (value * 9) / 5 + 32})}
        </>
    );
}

export default function App() {
    return (
        <Input
            renderKelvin={({value}) => <div className="temp">{value}K</div>}
            renderFahrenheit={({value}) => <div className="temp">{value}°F</div>}
        />
    );
}
```

- Since we explicitly pass props, we solve the HOC's implicit props issue.

---

```js
import React from 'react';

export function Kelvin({value}) {
    return (
        <div className="temp-card">
            The temperature in Kelvin is:
            <span className="temp">{value}K</span>
        </div>
    );
}

export function Fahrenheit({value}) {
    return (
        <div className="temp-card">
            The temperature in Fahrenheit is:
            <span className="temp">{value}°F</span>
        </div>
    );
}

export default function TemperatureConverter(props) {
    const [value, setValue] = React.useState(0);

    return (
        <div className="card">
            <input
                type="number"
                placeholder="Degrees Celcius"
                onChange={(e) => setValue(parseInt(e.target.value))}
            />
            {props.renderKelvin({value: Math.floor(value + 273.15)})}
            {props.renderFahrenheit({value: Math.floor((value * 9) / 5 + 32)})}
        </div>
    );
}
```

`App.tsx`

```js
import * as React from 'react';
import './style.css';
import TemperatureConverter, {
    Kelvin,
    Fahrenheit,
} from './TemperatureConverter';

export default function App() {
    return (
        <TemperatureConverter
            renderKelvin={({value}) => <Kelvin value={value}/>}
            renderFahrenheit={({value}) => <Fahrenheit value={value}/>}
        />
    );
}
```

### Hooks Pattern

React Hooks are functions special types of functions that you can use in order to:

- Add state to a functional component
- Reuse stateful logic among multiple components throughout the app.
- Manage a component's lifecycle

```js
export function useHover() {
    const [isHovering, setIsHovering] = React.useState(false);
    const ref = React.useRef(null);

    const handleMouseOver = ()=> setIsHovering(true);
    const handleMouseOut = ()=> setIsHovering(false);

    React.useEffect(() => {
        const node = ref.current;
        if (node) {
            node.addEventListener("mouseover", handleMouseOver);
            node.addEventListener("mouseout", handleMouseOut);
            return () => {
                node.removeEventListener("mouseover", handleMouseOver);
                node.removeEventListener("mouseout", handleMouseOut);
            };
        }
    }, [ref.current]);

    return [ref, isHovering];
}
```

usage demo:

```js
import {useHover} from "../hooks/useHover";

export function Listing() {
    const [ref, isHovering] = useHover();

    React.useEffect(() => {
        if (isHovering) {
            // Add logic here
        }
    }, [isHovering]);

    return (
        <div ref={ref}>
            <ListingCard/>
        </div>
    )
}
```

- Hooks 使得为函数式组件添加状态更加简便，而不是使用遗留的类组件模式。
- Hooks 共享非显示的逻辑，不需要再使用 HOC or Render Props 模式。

下面是一个例子：
`Listings.tsx`

```js
import React from 'react';
import {Listing} from './Listing';
import {ListingsGrid} from './ListingsGrid';
import useListings from '../hooks/useListings';

export default function Listings() {
    const listings = useListings();

    if (!listings) return null;

    return (
        <ListingsGrid>
            {listings.map((listing) => (
                <Listing key={listing.id} listing={listing}/>
            ))}
        </ListingsGrid>
    );
}

```

`useListings.tsx`

```js
import React from 'react';

export default function useListings() {
    const [listings, setListings] = React.useState(null);

    React.useEffect(() => {
        fetch('https://house-lydiahallie.vercel.app/api/listings')
            .then((res) => res.json())
            .then((res) => setListings(res.listings));
    }, []);

    return listings;
}

```

### Provider Pattern

让子组件可以直接访问共享的数据，而不需要层层传递 props。仿佛当年的回调地狱一般，如今 Prop-drilling 的问题也得到了缓解。

下面看一下实现的过程。

```js
export const ThemeContext = React.createContext(null);

export function ThemeProvider({children}) {
    const [theme, setTheme] = React.useState("light");

    return (
        <ThemeContext.Provider value={{theme, setTheme}}>
            {children}
        </ThemeContext.Provider>
    );
}
```

被 `ThemeContext.Provider` 包裹的子组件都可以访问 `theme, setTheme` 属性。

更具体地，

```tsx
import {ThemeProvider, ThemeContext} from "../context";

const LandingPage = () => {
    <ThemeProvider>
        <TopNav/>
        <Main/>
    </ThemeProvider>;
};

const TopNav = () => {
    return (
        <ThemeContext.Consumer>
            {{theme}} =>{
            <div style={{backgroundColor: theme === "light" ? "#fff" : "#000 "}}>
            </div>
        }
        </ThemeContext.Consumer>
    );
};

const Toggle = () => {
    return (
        <ThemeContext.Consumer>
            {{theme, setTheme}} => (
            <button
                onClick={() => setTheme(theme === "light" ? "dark" : "light")}
                style={{
                    backgroundColor: theme === "light" ? "#fff" : "#000",
                    color: theme === "light" ? "#000" : "#fff",
                }}
            >
                Use {theme === "light" ? "Dark" : "Light"} Theme
            </button>
            )
        </ThemeContext.Consumer>
    );
};
```

上面的代码可以被 hook pattern 进一步改善

```tsx
export const ThemeContext = React.createContext(null);

export function useThemeContext() {
    const {theme, setTheme} = useContext(ThemeContext);
    return {theme, setTheme};
}

export function ThemeProvider({children}) {
    const [theme, setTheme] = React.useState("light");

    return (
        <ThemeContext.Provider value={{theme, setTheme}}>
            {children}
        </ThemeContext.Provider>
    );
}
```

以前需要使用 ThemeContext 的地方，现在被 useThemeContext() 重构，消除了嵌套的 `<ThemeContext.Consumer>` 代码片段。

```tsx
import {useThemeContext} from "../context";

const LandingPage = () => {
    <ThemeProvider>
        <TopNav/>
        <Main/>
    </ThemeProvider>;
};

const TopNav = () => {
    const {theme} = useThemeContext();
    return (
        <div style={{backgroundColor: theme === "light" ? "#fff" : "#000 "}}>
            ...
        </div>
    );
};

const Toggle = () => {
    const {theme, setTheme} = useThemeContext();
    return (
        <button
            onClick={() => setTheme(theme === "light" ? "dark" : "light")}
            style={{
                backgroundColor: theme === "light" ? "#fff" : "#000",
                color: theme === "light" ? "#000" : "#fff",
            }}
        >
            Use {theme === "light" ? "Dark" : "Light"} Theme
        </button>
    );
};
```

TopNav 和 Main 必须被 provider 包裹才能访问共享的 context。此外，当 provider 提供的值改变时，会引起消费者组件的
re-render，这可能引发性能问题。

---

一个问题：每次调用 useThemeContext 时，返回的是独立的 context 还是共享的单例 context ？

实验代码：

```tsx
const ThemeContext = React.createContext(null);

export function useThemeContext() {
    // React.useContext(ThemeContext) 会从组件树中查找最近的 ThemeContext.Provider
    const {theme, setTheme} = useContext(ThemeContext);
    return {theme, setTheme};
}

function App() {
    const [theme, setTheme] = useState("light");
    return (
        <ThemeContext.Provider value={{theme, setTheme}}>
            <ComponentA/>
            <ComponentB/>
        </ThemeContext.Provider>
    );
}

function ComponentA() {
    const {theme} = useThemeContext();
    console.log("ComponentA theme:", theme); // 输出 "light"
    return null;
}

function ComponentB() {
    const {theme} = useThemeContext();
    console.log("ComponentB theme:", theme); // 输出 "light"
    return null;
}
```

- 共享状态：多个组件调用 useThemeContext，得到相同的 theme 和 setTheme（前提是它们被同一个 Provider 包裹）。
- 独立性：返回的对象是独立的（每次调用返回一个新的 {theme, setTheme} 对象），但对象中的 theme 和 setTheme 是来自共享的上下文值。

总之，provider 方式是跨组件数据共享的其中一种方式，区别于类似 redux 这种全局集中式的状态管理。

| 维度	     | React                                 Context	 | Redux                              |
   |---------|------------------------------------------------|------------------------------------|
| 定位	     | 组件树内的数据透传工具                                    | 	全局集中式状态管理容器                       |
| 状态更新机制	 | 依赖 React 的渲染机制（useState /useReducer）	          | 严格的单向数据流（Action → Reducer → Store） |
| 数据流向	   | 自上而下（Provider → Consumer）	                     | 集中存储，任意组件可连接（connect/useSelector）  |
| 场景倾向    | 低频更新、简易场景                                      | 高频更新、精确更新、注重性能                     |

### 组合模式

一个组件内部有多个子组件。

```tsx
import React from "react";
import {FlyOut} from "./FlyOut";

export default function SearchInput() {
    return (
        <FlyOut>
            <FlyOut.Input placeholder="Enter an address, city, or ZIP code"/>
            <FlyOut.List>
                <FlyOut.ListItem value="San Francisco, CA">San Francisco, CA</FlyOut.ListItem>
                <FlyOut.ListItem value="Seattle, WA">Seattle, WA</FlyOut.ListItem>
                <FlyOut.ListItem value="Austin, TX">Austin, TX</FlyOut.ListItem>
                <FlyOut.ListItem value="Miami, FL">Miami, FL</FlyOut.ListItem>
                <FlyOut.ListItem value="Boulder, CO">Boulder, CO</FlyOut.ListItem>
            </FlyOut.List>
        </FlyOut>
    );
}
```

```tsx
const FlyOutContext = React.createContext();

export function FlyOut(props) {
    const [open, setOpen] = React.useState(false);
    const [value, setValue] = React.useState("");
    const toggle = React.useCallback(()=> setOpen((state) => !state), []);

    return (
        <FlyOutContext.Provider value={{open, toggle, value, setValue}}>
            <div>{props.children}</div>
        </FlyOutContext.Provider>
    );
}

function Input(props) {
    const {value, toggle} = React.useContext(FlyOutContext);
    return <input onFocus={toggle} onBlur={toggle} value={value} {...props} />;
}

function List({children}) {
    const {open} = React.useContext(FlyOutContext);
    return open && <ul>{children}</ul>;
}

function ListItem({children, value}) {
    const {setValue} = React.useContext(FlyOutContext);
    return <li onMouseDown={()=> setValue(value)}>{children}</li>;
}

FlyOut.Input = Input;
FlyOut.List = List;
FlyOut.ListItem = ListItem;
```

还有一种实现方式是使用 `React.Children.map`:

```tsx
export function FlyOut(props) {
    const [open, setOpen] = React.useState(false);
    const [value, setValue] = React.useState("");
    const toggle = React.useCallback(()=> setOpen((state) => !state), []);

    return (
        <div>
            {React.Children.map(props.children, (child) =>
                React.cloneElement(child, {open, toggle, value, setValue})
            )}
        </div>
    );
}
```

## Performance Patterns

### overview

Bundlers

- Webpack
- Parcel
- Rollup

Compilers

- Babel
- TypeScript

Minifiers

- Terser
- Uglify

Combination

- SWC - a Rust-based compiler, bundler, and minifier
- ESBuild - a Go-based compiler, bundler, and minifier.

此外，Bundle Splitting 和 Tree-Shaking 是常见的性能优化实践。

### Static Import

静态导入是最常见的方式

```js
import module1 from "./module1";
import module2 from "./module2";
import module3 from "./module3";
```

bundler 打包时会从 entry 往下遍历，直到解析完所有依赖。这种方式能够让组件立即可用，但是可能会导致较大的初始加载 bundle。

### dynamic import

动态导入区别于静态导入，本质是懒加载，推迟了初始化组件的时机。

```tsx
import React, {Suspense, lazy} from 'react';
import './styles.css';

import {Card} from './components/Card';
import Card1 from './components/Card1';
import Card2 from './components/Card2';

const Card3 = lazy(() =>
    import(/*webpackChunkName: "card3" */ './components/Card3')
);
const Card4 = lazy(() =>
    import(/*webpackChunkName: "card4" */ './components/Card4')
);

const App = () => {
    return (
        <div className="App">
            <Card1/>
            <Card2/>
            <DynamicCard component={Card3} name="Card3"/>
            <DynamicCard component={Card4} name="Card4"/>
        </div>
    );
};

function DynamicCard(props) {
    const [open, toggle] = React.useReducer((s) => !s, false);
    const Component = props.component;

    return (
        <Suspense fallback={<p id="loading">Loading...</p>}>
            {open ? (
                <Component/>
            ) : (
                <Card rendered={false} onClick={toggle}>
                    <p>
                        Click here to dynamically import <code>{props.name}</code> component
                    </p>
                </Card>
            )}
        </Suspense>
    );
}

export default App;
```

上面的 Card 3&4 都是动态导入的方式。动态导入使用时必须被 Suspense 组件包裹，并且提供 fallback 参数值来表示加载占位符。
使用 Suspense 组件时，需要注意占位符的空间尺寸和最终内容的尺寸，否则会发生布局抖动。

### Import on Visibility

可见时才加载，常见的应用是图片懒加载，或者虚拟列表。

> One way to dynamically import components on interaction, is by using
> the [Intersection Observer API](https://developer.mozilla.org/en-US/docs/Web/API/Intersection_Observer_API). There's a
> React
> hook called [react-intersection-observer](https://www.npmjs.com/package/react-intersection-observer) that we can use
> to easily detect whether a component is visible in the viewport.

示例

```tsx
import {Suspense, lazy} from "react";
import {useInView} from "react-intersection-observer";

const Listing = lazy(() => import("./components/Listing"));

function ListingCard(props) {
    const {ref, inView} = useInView();

    return (
        <div ref={ref}>
            <Suspense fallback={<div/>}>{inView && <Listing/>}</Suspense>
        </div>
    );
}
```

### Route Based Splitting

基于路由的代码分割。因为同一个时刻只会有一个路由是激活的，并且对于部分路由用户可能根本不会去点击加载。

```tsx
import React, {lazy, Suspense} from 'react';
import {createRoot} from 'react-dom/client';
import {
    Routes,
    Route,
    BrowserRouter as Router,
    Link,
    Outlet,
} from 'react-router-dom';
import './styles.css';

const App = lazy(() => import('./pages/App'));
const About = lazy(() => import('./pages/About'));
const Contact = lazy(() => import('./pages/Contact'));

export function Nav() {
    return (
        <div>
            <nav>
                <h1>
                    <Link to="/">
                        <span>🏡</span> Houses.
                    </Link>
                </h1>
                <ul>
                    <li>
                        <Link to="/about">About</Link>
                    </li>
                    <li>
                        <Link to="/contact">Contact</Link>
                    </li>
                </ul>
            </nav>
            <Outlet/>
        </div>
    );
}

createRoot(document.getElementById('root')).render(
    <Router>
        <Suspense fallback={<div>Loading...</div>}>
            <Routes>
                <Route path="/" element={<Nav/>}>
                    <Route
                        index
                        path="/"
                        element={
                            <React.Suspense fallback={<div/>}>
                                <App/>
                            </React.Suspense>
                        }
                    />
                    <Route
                        path="/about"
                        element={
                            <React.Suspense fallback={<div/>}>
                                <About/>
                            </React.Suspense>
                        }
                    />
                    <Route
                        path="/contact"
                        element={
                            <React.Suspense fallback={<div/>}>
                                <Contact/>
                            </React.Suspense>
                        }
                    />
                </Route>
            </Routes>
        </Suspense>
    </Router>
);
```

### Browser Hints

prefetch：预加载部分经常访问的资源。Fetch and cache resources that may be requested some time soon.
这种提示是建议，对于浏览器而言，遵循的优先级不是特别高。

```tsx
<link rel="prefetch" href="./about.bundle.js" />
```

如果使用的是 webpack，那么可以这样

```tsx
const About = lazy(() => import(/* webpackPrefetch: true */ "./about"));
```
不过这种基于魔法注释的方式怎么看都有点别扭。

---

preload：类似于 prefetch，但是资源下载的优先级比较高。

```js
<link rel="preload" href="./search-flyout.bundle.js" />
```
对于 webpack
```js
const SearchFlyout = lazy(() =>
  import(/* webpackPreload: true */ "./SearchFlyout")
);
```

类似的道理，对于 js 文件资源，可以使用 async 或者 defer 标记。

## Rendering Patterns

对于一个网站页面的性能表现，可以通过 Web Vitals 来衡量。Web Vitals 是一系列和页面相关的性能指标。

一些概念的梳理：
- Hydration: Attaching handlers to a DOM node whose HTML contents were server-rendered, making the component interactive.
- Idle: The browser's state when it's not performing any action

### Client-Side Rendering

优点：
- 首屏渲染立即可交互
- 单次网络请求往返

### Static Rendering & Dynamic Data

Fetch dynamic data at build time: use a server-side fetch

以 React 为例
```tsx
import {Listings, ListingsSkeleton} from "../components";

export default function Home(props) {
  return <Listings listings={props.listings} />;
}

export async function getStaticProps() {
  const res = await fetch("https://my.cms.com/listings");
  const listings = await res.json();

  return {props: { listings} };
}

```
缺点：更新数据 listings 需要重新deploy。

---

Fetch dynamic data client-side: use a client-side fetch

```tsx
import useSWR from "swr";
import { Listings, ListingsSkeleton } from "../components";

export default function Home() {
  const { data, loading } = useSWR("/api/listings", (...args) =>
    fetch(...args).then((res) => res.json())
  );

  if (loading) {
    return <ListingsSkeleton />;
  }

  return <Listings listings={data.listings} />;
}
```

### Incremental Static Regeneration

```js
import {Listings, ListingsSkeleton} from "../components";

export default function Listing(props) {
    return <ListingLayout listings={props.listing}/>
}

export async function getStaticProps(props) {
    const res = await fetch(`https://my.cms.com/listings/${props.params.id}`);
    const {listing} = await res.json();

    return {props: {listing}}
}

export async function getStaticPaths() {
    const res = await fetch(`https://my.cms.com/listings?limit=20`);
    const {listings} = await res.json();

    return {
        params: listings.map(listing => ({id: listing.id})),
        fallback: false
    }
}
```

### Server-Side Rendering

- 当用户请求一个服务器端渲染的应用时，服务器生成 HTML，并将其返回给客户端。浏览器渲染这些内容，最初只是普通的非交互式 HTML 元素。
  - One way to render HTML on the server, is by using the `renderToString` method.
-  为了将事件监听器绑定到组件，客户端会发送一个额外的请求以获取 JavaScript 包以使组件具备交互性。

When using Next.js, we can server-render a page by using the getServerSideProps method.

```js
import { Listings, ListingsSkeleton } from "../components";

export default function Home(props) {
  return <Listings listings={props.listings} />;
}

export async function getServerSideProps({ req, res }) {
  const res = await fetch("https://my.cms.com/listings");
  const listings = await res.json();

  return {
    props: { listings },
  };
}
```

### Streaming Server-Side Rendering

受框架支持的制约，使用有局限。而且操作起来复杂度也很高。

## further reading

- https://www.patterns.dev/#patterns