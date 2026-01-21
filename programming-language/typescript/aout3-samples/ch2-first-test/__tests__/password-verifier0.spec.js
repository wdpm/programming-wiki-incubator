const { verifyPassword } = require('../password-verifier0');

test('v1: the first test', () => {
  const fakeRule = input => ({ passed: false, reason: 'fake reason' });

  const errors = verifyPassword('any value', [fakeRule]);
  expect(errors[0]).toMatch('fake reason');
});

test('v1.1 verifyPassword, given a failing rule, returns errors', () => {
  const fakeRule = input => ({ passed: false, reason: 'fake reason' });

  const errors = verifyPassword('any value', [fakeRule]);

  expect(errors[0]).toContain('fake reason');
});

// 这里将SUT（verifyPassword）提取到describe层级
describe('v1.2: verifyPassword', () => {
  test('given a failing rule, returns errors', () => {
    const fakeRule = input =>
      ({ passed: false, reason: 'fake reason' });

    const errors = verifyPassword('any value', [fakeRule]);

    expect(errors[0]).toContain('fake reason');
  });
});

// 2层嵌套
describe('v1.3: verifyPassword', () => {
  describe('with a failing rule', () => {
    test('returns errors', () => {
      const fakeRule = input => ({
        passed: false,
        reason: 'fake reason'
      });
      const errors = verifyPassword('any value', [fakeRule]);

      expect(errors[0]).toContain('fake reason');
    });
  });
});

// 提取公用的变量
describe('v1.4: verifyPassword', () => {
  describe('with a failing rule', () => {
    const fakeRule = input => ({
      passed: false,
      reason: 'fake reason'
    });
    test('returns errors', () => {
      const errors = verifyPassword('any value', [fakeRule]);

      expect(errors[0]).toContain('fake reason');
    });
  });
});

// test -> it for better naming semantics
describe('v1.5: verifyPassword', () => {
  describe('with a failing rule', () => {
    it('returns errors', () => {
      const fakeRule = input => ({
        passed: false,
        reason: 'fake reason'
      });
      const errors = verifyPassword('any value', [fakeRule]);

      expect(errors[0]).toContain('fake reason');
    });
  });
});
