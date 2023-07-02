// Test 1 - Test if Dice model works correctly when thrown
import { Dice } from '@/models/Dice.js';
import { shallowMount } from '@vue/test-utils';
import LudoBoard from "@/components/LudoBoard";

describe('Dice', () => {
    describe('constructor', () => {
        it('should set the eyes property correctly', () => {
            const dice = new Dice(3);
            expect(dice.eyes).toBe(3);
        });
    });

    describe('rollDice', () => {
        it('should return a random number between 1 and 6', () => {
            const result = Dice.rollDice();
            expect(result).toBeGreaterThanOrEqual(1);
            expect(result).toBeLessThanOrEqual(6);
        });
    });

    describe('createDiceRoll', () => {
        it('should create a new Dice instance with a random number of eyes', () => {
            const dice = Dice.createDiceRoll();
            expect(dice).toBeInstanceOf(Dice);
            expect(dice.eyes).toBeGreaterThanOrEqual(1);
            expect(dice.eyes).toBeLessThanOrEqual(6);
        });
    });
});

// Test 2
describe('getPlayerLocation', () => {
    let component;
    const path = [
        { i: 0, j: 0 },
        { i: 0, j: 1 },
        { i: 0, j: 2 },
        { i: 1, j: 2 },
        { i: 1, j: 1 },
        { i: 2, j: 1 },
        { i: 2, j: 0 },
    ];

    beforeEach(() => {
        component = {
            path,
            getPlayerLocation: LudoBoard.methods.getPlayerLocation,
        };
    });

    it('returns the player location when the position is found in the path', () => {
        const position = { i: 1, j: 1 };
        const expectedLocation = { i: 1, j: 1 };
        const result = component.getPlayerLocation(position);
        expect(result).toEqual(expectedLocation);
    });

    it('returns null when the position is not found in the path', () => {
        const position = { i: 2, j: 2 };
        const result = component.getPlayerLocation(position);
        expect(result).toBeNull();
    });

    it('returns null when the path is empty', () => {
        component.path = [];
        const position = { i: 1, j: 1 };
        const result = component.getPlayerLocation(position);
        expect(result).toBeNull();
    });

    it('handles negative coordinates in the path and position', () => {
        component.path = [
            { i: -1, j: -1 },
            { i: -1, j: 0 },
            { i: 0, j: 0 },
        ];

        const position = { i: -1, j: 0 };
        const expectedLocation = { i: -1, j: 0 };
        const result = component.getPlayerLocation(position);
        expect(result).toEqual(expectedLocation);
    });

});

// Test 3

import { searchValueInArray } from '@/components/LudoBoard.vue';

// describe('searchValueInArray', () ==> {
//
// });
//
//


// Test 4




// Test 5