const { SUNDAY, MONDAY } = require('../password-verifier-time02');
const { PasswordVerifier } = require('../password-verifier-time02');
const sinon = require('sinon');

const makeVerifier = (rules, timeProvider) => {
  return new PasswordVerifier(rules, timeProvider);
};

describe('verifier', () => {
  test('on weekends, throws exceptions, ctor function', () => {
    // 完全没必要使用框架特定的语法功能，会增加复杂度，就使用纯函数就行
    const stubGetDayFn = jest.fn(() => SUNDAY);
    const verifier = makeVerifier([], { getDay: stubGetDayFn });

    expect(() => verifier.verify('anything'))
      .toThrow("It's the weekend!");
  });

  test('on weekdays, with no rules, always passes', () => {
    const stubGetDayFn = jest.fn(() => MONDAY);
    const verifier = makeVerifier([], { getDay: stubGetDayFn });

    const result = verifier.verify('anything');
    expect(result.length).toBe(0);
  });
});
