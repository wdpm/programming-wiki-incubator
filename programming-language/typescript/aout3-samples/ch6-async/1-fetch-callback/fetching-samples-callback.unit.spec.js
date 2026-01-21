const samples = require("./fetching-samples-callback");

describe("Website alive checking", () => {
  test("When website fetch content matches, returns true", (done) => {
    samples.processFetchSuccess("illustrative", (err, result) => {
      expect(err).toBeNull();
      expect(result.success).toBe(true);
      expect(result.status).toBe("ok");
      done();
    });
  });

  test("When website fetch content does not match, returns false", (done) => {
    samples.processFetchSuccess("text not on the website", (err, result) => {
      // err is null means the network fetch is ok
      expect(err).toBeNull();
      expect(result.success).toBe(false);
      expect(result.status).toBe("missing text");
      done();
    });
  });
  test("When fetch fails, returns false", (done) => {
    samples.processFetchError(new Error("error text"), (err, result) => {
      expect(err.message).toBe("error text");
      done();
    });
  });
});
