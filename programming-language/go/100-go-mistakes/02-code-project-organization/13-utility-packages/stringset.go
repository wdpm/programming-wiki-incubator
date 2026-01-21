// utils/helpers/base/commmon these words are meaningless
package stringset

type Set map[string]struct{}

func New(...string) Set { return nil }

func (s Set) Sort() []string { return nil }

//  bear in mind that naming a package after what it provides and not what it
// contains can be an efficient way to increase its expressiveness
