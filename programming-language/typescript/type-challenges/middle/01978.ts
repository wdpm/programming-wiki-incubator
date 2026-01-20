type PString1 = ""
type PString2 = "+85%"
type PString3 = "-85%"
type PString4 = "85%"
type PString5 = "85"

type PercentageParser<T extends string> =
    T extends `${infer Sign extends "+" | "-"}${infer Rest}`
        ? Rest extends `${infer Number}%`
            ? [Sign, Number, "%"]
            : [Sign, Rest, ""]
        : T extends `${infer Number}%`
            ? ["", Number, "%"]
            : ["", T, ""];


type R1 = PercentageParser<PString1> // expected ['', '', '']
type R2 = PercentageParser<PString2> // expected ["+", "85", "%"]
type R3 = PercentageParser<PString3> // expected ["-", "85", "%"]
type R4 = PercentageParser<PString4> // expected ["", "85", "%"]
type R5 = PercentageParser<PString5> // expected ["", "85", ""]