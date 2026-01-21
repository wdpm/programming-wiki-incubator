const {PasswordVerifier} = require("./00-password-verifier00");

// I didn't call the fake object MockLogger or StubLogger, but FakeLogger.
// This is because I can reuse this class in multiple different tests
class FakeLogger {
  logged = "";

  info(text) {
    this.logged = text;
  }
}

describe("with FakeLogger class  - constructor injection", () => {
  describe("password verifier", () => {
    test("given logger and passing scenario, calls logger with PASSED", () => {
      let logged = "";
      const mockLog = new FakeLogger();
      const verifier = new PasswordVerifier([], mockLog);
      verifier.verify("any input");

      expect(mockLog.logged).toMatch(/PASSED/);
    });
  });
});