# notes

## ch01

An unit test code has 3 exit points:
- return value
- state change noticeable
- invoke 3rd party dependencies.

>an integration test uses real dependencies; unit tests isolate the unit of work
from its dependencies so that they’re easily consistent in their results and can easily control

## ch02

U.S.E Naming: Unit under test, Scenario, Expectation.
```js
test('v1.1 verifyPassword, given a failing rule, returns errors', () => {
  ...
});
```

- describe 函数，仅用于结构组织，嵌套层级。
- it 是 test 函数的替代，作用几乎相同，也是更为更好的语义。
- 有时可以采用简单的函数重构来避免使用 beforeEach() 配置

## stubs

there are two main types of dependencies that our unit of can use:
- outgoing: e.g. use logger to log something.
- incoming: e.g. use external networks, files or databases.

>STUB: Break incoming dependencies (indirect inputs). Stubs are fake modules, objects or functions that
provide fake behavior or data into the code under test. We do not assert against them. We can have many
stubs in a single test.

>MOCK: Break outgoing dependencies (outputs, exit points). Mocks are fake modules, objects or functions
that we assert were called in our tests. A Mock represents an exit point in a unit test. Because of this, it is
recommended to have no more than a single mock per test.

---
Generally accepted design approaches to stubbing:

Functional approaches
- Function as Parameter
- Factory Functions (a.k.a Higher Order Functions)
- Constructor Functions

Object Oriented Approaches
- Class Constructor Injection
- Object as Parameter (a.k.a ‘duck typing’)
- Common Interface as Parameter (for this we’ll use TypeScript)

## mocks

the third type of exit point—a call to a third-party function, module or object. 

> It should be OK to have multiple stubs in a test, but you don’t usually want to have more
  than a single mock per test – because that means you’re testing more than requirements in a
  single test.

>Spy was originally designed as
 the idea that you can look at a real function and “spy” on it without altering its behavior.

Think of “Spy” as “Partial Mock/Stub”.

## Isolation (mocking) frameworks

> DEFINITION: 
  An isolation framework is a set of programmable APIs that allow the dynamic creation,
  configuration and verification of mocks and stubs, either in object or function form; Using a framework, these
  tasks can often be simpler, faster, and shorter than hand-coding them.

using substitute:
```typescript
const stubMaintWindow = Substitute.for<MaintenanceWindow>();
stubMaintWindow.isUnderMaintenance().returns(true);
```

Or using jest:
```typescript
const stubMaintWindow: MaintenanceWindow = {
  isUnderMaintenance: jest
    .fn()
    .mockImplementationOnce(()=> true),
};
```

## Unit Testing Async Code

modular vs functional , functional wins.

modular:
```typescript
jest.mock("./network-adapter"); // must be first line
const stubSyncNetwork = require("./network-adapter");
```

functional:
```typescript
const makeStubNetworkWithResult = (fakeResult) => {
  return {
    fetchUrlText: () => {
      return fakeResult;
    },
  };
};
```
---

jest timer 相关函数的使用。
- `jest.clearAllTimers()`
- `jest.useFakeTimers()`
- `jest.advanceTimersToNextTimer()`

summary:
- Consider when to extract new entry points makes sense for you. => 提取更小的纯函数
- Consider extracting an adapter to remove a dependency from a piece of code.
- Sometimes you can’t get out of just faking out the various timers with monkey patching or a framework.
- Events testing is really easy once you get the hang of it.

## Trustworthy Tests

/

## Maintainability

/

## Readability

/

## 10 Working with different test types

- Unit Tests (in Memory) + Component Tests (in Memory)
- Integration Tests (In Memory): we might use a real configuration, a real database, a real file sysem.
- e2e tests: At this level we are testing out application form the point of view of a user.

评估维度：
- complexity
- flakiness(抗外界影响的能力。测试失败的可能性有多大 - 来自其他组、网络、数据库、配置等的代码。越低越好)
- confidence when passes
- maintainability
- execution speed

## 11 Integrating unit testing into the organization
/

## 12

/

---

总体评价。前面1-6章质量很高，后面章节可以不读，过于空泛。