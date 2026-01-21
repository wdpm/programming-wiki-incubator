import { PasswordVerifier5 } from "./00-password-verifier5";

describe("verifier 5", () => {
  describe("overspecify outputs", () => {
    test("overspecify order and schema", () => {
      const pv5 = new PasswordVerifier5([(input) => input.includes("abc")]);
      const results = pv5.verify(["a", "ab", "abc", "abcd"]);
      // 关注完全的scheme检测，太严格了
      expect(results).toEqual([
        { input: "a", result: false },
        { input: "ab", result: false },
        { input: "abc", result: true },
        { input: "abcd", result: true },
      ]);
    });

    test("overspecify order but ignore schema", () => {
      const pv5 = new PasswordVerifier5([(input) => input.includes("abc")]);
      const results = pv5.verify(["a", "ab", "abc", "abcd"]);
      expect(results.length).toBe(4);
      // 只关心result字段，关注点已经变窄了，很好
      expect(results[0].result).toBe(false);
      expect(results[1].result).toBe(false);
      expect(results[2].result).toBe(true);
      expect(results[3].result).toBe(true);
    });

    test("ignore order and schema", () => {
      const pv5 = new PasswordVerifier5([(input) => input.includes("abc")]);

      const results = pv5.verify(["a", "ab", "abc", "abcd"]);

      const falseResults = results.filter((res) => !res.result);
      const trueResults = results.filter((res) => res.result);
      expect(results.length).toBe(4);
      // 只关心数量，不再关心schema
      expect(falseResults.length).toBe(2);
      expect(trueResults.length).toBe(2);
    });
  });
});
