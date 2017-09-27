describe("A suite is just a function", function() {
  var a;

  it("and so is a spec", function() {
    a = true;

    expect(a).toBe(true);
  });
});

describe('sample', function() {
  it('returns 42', function() {
      expect(theAnswerC()).toBe(42);
  });
});