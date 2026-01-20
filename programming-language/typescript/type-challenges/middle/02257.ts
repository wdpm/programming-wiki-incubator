// This helper type constructs an array of a given length `Length`.
// It recursively adds an element (unknown) to an array (Arr) until its length equals Length.
// For example:
// BuildArray<3> results in [unknown, unknown, unknown].
type BuildArray<Length extends number, Arr extends unknown[] = []> =
    Arr['length'] extends Length ? Arr : BuildArray<Length, [...Arr, unknown]>;

// deconstruct T and return the length of Rest part
type MinusOne<T extends number> = BuildArray<T> extends [infer _, ...infer Rest]
    ? Rest['length']
    : never;

type Zero = MinusOne<1> // 0
type FiftyFour = MinusOne<55> // 54