## web 开发中的状态建模

可能会直接想到下面这种方式：

```typescript
interface State {
    pageText: string;
    isLoading: boolean;
    error?: string;
}

function renderPage(state: State) {
    if (state.error) {
        return `Error! Unable to load ${currentPage}: ${state.error}`;
    } else if (state.isLoading) {
        return `Loading ${currentPage}...`;
    }
    return `<h1>${currentPage}</h1>\n${state.pageText}`;
}
```

这个模式的问题在于，isLoading 和 error 这两个字段可能会发生冲突。当它们都设置了值时，究竟发生了什么？
此时，是设置加载状态还是要显示错误呢？

> This uses a tagged union (also known as a “discriminated union”) to
> explicitly model the different states that a network request can be in

```typescript
interface RequestPending {
    state: 'pending';
}

interface RequestError {
    state: 'error';
    error: string;
}

interface RequestSuccess {
    state: 'ok';
    pageText: string;
}

type RequestState = RequestPending | RequestError | RequestSuccess;

interface State {
    currentPage: string;
    requests: { [page: string]: RequestState };
}
```

这种方式显示列举了网络请求的状态，例如只有 RequestError 时才会含有错误信息，只有 RequestSuccess 时才会有页面结果。

后续的处理就更加自然：

```typescript
function renderPage(state: State) {
    const {currentPage} = state;
    const requestState = state.requests[currentPage];
    switch (requestState.state) {
        case 'pending':
            return `Loading ${currentPage}...`;
        case 'error':
            return `Error! Unable to load ${currentPage}: ${requestState.error}`;
        case 'ok':
            return `<h1>${currentPage}</h1>\n${requestState.pageText}`;
    }
}

async function changePage(state: State, newPage: string) {
    state.requests[newPage] = {state: 'pending'};
    state.currentPage = newPage;
    try {
        const response = await fetch(getUrlForPage(newPage));
        if (!response.ok) {
            throw new Error(`Unable to load ${newPage}: ${response.statusText}`);
        }
        const pageText = await response.text();
        state.requests[newPage] = {state: 'ok', pageText};
    } catch (e) {
        state.requests[newPage] = {state: 'error', error: '' + e};
    }
}
```

优先选择仅表示有效状态的类型。即使它们更长或更难表达，最终也能为您节省时间和痛苦！

## 要么全部为空，要么全部不为空

```typescript
function extent(nums: number[]) {
    let min, max;
    for (const num of nums) {
        if (!min) {
            min = num;
            max = num;
        } else {
            min = Math.min(min, num);
            max = Math.max(max, num);
            // ~~~ Argument of type 'number | undefined' is not
            // assignable to parameter of type 'number'
        }
    }
    return [min, max];
}
```

上面这段代码对于输入 [0,1,2] 会返回 [1,2] ，正确答案应该是 [0,2]。
原因在于 `if (!min)`这个判断是不准确的。

```typescript

function extent(nums: number[]) {
    let result: [number, number] | null = null;
    for (const num of nums) {
        if (!result) {
            result = [num, num];
        } else {
            result = [Math.min(num, result[0]), Math.max(num, result[1])];
        }
    }
    return result;
}
```

`let result: [number, number] | null` 这里体现了全部空或者全部不为空的设计思想。

---

混合空值与非空值：A mix of null and non-null values

```typescript
class UserPosts {
    user: UserInfo | null;
    posts: Post[] | null;

    constructor() {
        this.user = null;
        this.posts = null;
    }

    async init(userId: string) {
        return Promise.all([
            async () => this.user = await fetchUser(userId),
            async () => this.posts = await fetchPostsForUser(userId)
        ]);
    }

    getUserName() {
        // ...?
    }
}
```

init 方法中，由于并行请求的关系，在特定的时间点，我们无法确定 this.user 和 this.posts 各自的状态。一共有四种可能。

更好的做法是等到全部数据都可用时再初始化类。

```typescript
class UserPosts {
    user: UserInfo;
    posts: Post[];

    constructor(user: UserInfo, posts: Post[]) {
        this.user = user;
        this.posts = posts;
    }

    static async init(userId: string): Promise<UserPosts> {
        const [user, posts] = await Promise.all([
            fetchUser(userId),
            fetchPostsForUser(userId)
        ]);
        return new UserPosts(user, posts);
    }

    getUserName() {
        return this.user.name;
    }
}
```

## 将空值移动到边缘

```typescript
interface Person {
    name: string;
    // These will either both be present or not be present
    placeOfBirth?: string;
    dateOfBirth?: Date;
}
```

更好的建模方式：

```typescript
interface Person {
    name: string;
    birth?: {
        place: string;
        date: Date;
    }
}
```

如果类型的结构不在您的控制之下（例如来自 API），那么您可以使用现在熟悉的接口联合来对这些字段之间的关系进行建模：

```typescript
interface Name {
    name: string;
}

interface PersonWithBirth extends Name {
    placeOfBirth: string;
    dateOfBirth: Date;
}

type Person = Name | PersonWithBirth;
```

## 优先使用条件类型而非重载声明

先看一个函数

```typescript
function double(x: number | string): number | string;
function double(x: any) {
    return x + x;
}
```

实际调用
```typescript
const num = double(12);  // string | number
const str = double('x'); // string | number
```
这是不精确的返回类型，num应该是number类型，str应该是string类型。上面的类型推断并没有收窄范围。

于是考虑函数声明的重载：
```typescript
function double(x: number): number;
function double(x: string): string;
function double(x: any) {
    return x + x;
}

const num = double(12);  // Type is number
const str = double('x'); // Type is string
```

到这里已经取得巨大的进步，但是依旧存在一个细微的问题。看下面这个实际的调用。

```typescript
function f(x: number | string) {
    return double(x);
    // ~ Argument of type 'string | number' is not assignable
    // to parameter of type 'string'
}
```

使用条件类型进行精确的匹配推断。
```typescript
function double<T extends number | string>(
    x: T
): T extends string ? string : number;

function double(x: any) {
    return x + x;
}
```

到这里，之前的例子都可以完美工作了。
```typescript
const num = double(12); // number
const str = double('x');// string

// function f(x: string | number): string | number
function f(x: number | string) {
    return double(x);
}
```