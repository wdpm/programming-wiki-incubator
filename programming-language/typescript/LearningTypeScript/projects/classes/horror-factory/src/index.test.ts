import { describe, expect, it } from "@jest/globals";

import * as index from "./index";
import * as solution from "./solution";

process.env.TEST_SOLUTIONS = "1";

const { createDemon, createSorcerer, Horror } = process.env.TEST_SOLUTIONS
	? solution
	: index;

const createMockHorrorSettings = (evil: boolean) => {
	return {
		name: "",

		// identical power
		getPowerFrom: (consumed: { power: number }) => {
			return consumed.power;
		},

		isEvil: () => evil,
	};
};

describe(createDemon, () => {
	describe("getPower", () => {
		it("returns half a consumed horror's power if it was evil", () => {
			//  for demon:
			//	getPowerFrom: (consumed: Consumed) => {
			// 		return consumed.evil ? consumed.power / 2 : consumed.power * 2;
			// 	},

			// for mock horror:
			//		getPowerFrom: (consumed: { power: number }) => {
			// 			return consumed.power;
			// 		},
			const demon = createDemon();

			// consumed +1, this horror power =0
			demon.doBattle(new Horror(createMockHorrorSettings(true)));

			const mockWinner = new Horror(createMockHorrorSettings(true));
			// mockWinner power=1+0
			mockWinner.doBattle(new Horror(createMockHorrorSettings(true)));

			// comsumed +1, this mockWinner power =0.5
			demon.doBattle(mockWinner);

			// 2+0+0.5
			expect(demon.getPower()).toEqual(2.5);
		});

		it("returns double a consumed horror's power if it was not evil", () => {
			const demon = createDemon();

			// comsumed +1, this horror power = 0*2=0
			demon.doBattle(new Horror(createMockHorrorSettings(false)));

			const mockWinner = new Horror(createMockHorrorSettings(false));
			mockWinner.doBattle(new Horror(createMockHorrorSettings(true)));

			// comsumed +1, mockWinner power =1*2=2
			demon.doBattle(mockWinner);

			// 2+0+2=4
			expect(demon.getPower()).toEqual(4);
		});
	});

	describe("isEvil", () => {
		it("returns true", () => {
			const demon = createDemon();

			expect(demon.isEvil()).toBe(true);
		});
	});
});

describe(createSorcerer, () => {
	describe("getPower", () => {
		it("returns double a consumed horror's power if both are evil", () => {
			const sorcerer = createSorcerer("", true);

			sorcerer.doBattle(new Horror(createMockHorrorSettings(true)));

			const mockWinner = new Horror(createMockHorrorSettings(true));
			mockWinner.doBattle(new Horror(createMockHorrorSettings(true)));

			sorcerer.doBattle(mockWinner);

			//  2+0*2+1*2=4
			expect(sorcerer.getPower()).toEqual(4);
		});

		it("returns double a consumed horror's power if neither are evil", () => {
			const sorcerer = createSorcerer("", false);

			sorcerer.doBattle(new Horror(createMockHorrorSettings(false)));

			const mockWinner = new Horror(createMockHorrorSettings(false));
			// here evil=false wins
			mockWinner.doBattle(new Horror(createMockHorrorSettings(true)));

			sorcerer.doBattle(mockWinner);

			// 2+0*2+1*2=4
			expect(sorcerer.getPower()).toEqual(4);
		});

		it("returns half a consumed horror's power if only the sorcerer is evil", () => {
			const sorcerer = createSorcerer("", true);

			sorcerer.doBattle(new Horror(createMockHorrorSettings(false)));

			const mockWinner = new Horror(createMockHorrorSettings(false));
			mockWinner.doBattle(new Horror(createMockHorrorSettings(false)));

			sorcerer.doBattle(mockWinner);

			// 2+0+1=3
			expect(sorcerer.getPower()).toEqual(3);
		});

		it("returns half a consumed horror's power if only the consumed is evil", () => {
			const sorcerer = createSorcerer("", false);

			sorcerer.doBattle(new Horror(createMockHorrorSettings(false)));

			const mockWinner = new Horror(createMockHorrorSettings(true));
			mockWinner.doBattle(new Horror(createMockHorrorSettings(true)));

			sorcerer.doBattle(mockWinner);

			// 2+0+1=3
			expect(sorcerer.getPower()).toEqual(3);
		});
	});

	describe("isEvil", () => {
		it("returns false when the sorcerer is not evil", () => {
			const sorcerer = createSorcerer("", false);

			expect(sorcerer.isEvil()).toBe(false);
		});

		it("returns true when the sorcerer is evil", () => {
			const sorcerer = createSorcerer("", true);

			expect(sorcerer.isEvil()).toBe(true);
		});
	});
});
