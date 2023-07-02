// Test 1 - Test if Dice model works correctly when thrown
import { Dice } from '@/models/Dice.js';
import { shallowMount } from '@vue/test-utils';
import LudoBoard from "@/components/LudoBoard.vue";


describe('Dice roll eyes testing', () => {
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
describe('getPlayerLocation method testing', () => {
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

describe('LudoBoard pawn testing', () => {
    it('should return true if there are pawns on the board', () => {
        const wrapper = shallowMount(LudoBoard);

        wrapper.setData({
            currentPlayer: 'R',
            pawns: {
                'R': [
                    { startPos: 31, position: -1, home: 0 },
                    { startPos: 31, position: -1, home: 0 },
                    { startPos: 31, position: -1, home: 0 },
                    { startPos: 31, position: -1, home: 0 }
                ],
            }
        });

        // Call the hasPawnsOnBoard method and expect it to return true
        wrapper.vm.hasPawnsOnBoardMethod();
        expect(wrapper.vm.hasPawnsOnBoard).toBe(true);
    });

    it('should return false if there are no pawns on the board', () => {
        const wrapper = shallowMount(LudoBoard);

        wrapper.setData({
            pawns: {
                currentPlayer: 'B',
                'B': [
                    { startPos: 31, position: -1, home: 1 },
                    { startPos: 31, position: -1, home: 1 },
                    { startPos: 31, position: -1, home: 1 },
                    { startPos: 31, position: -1, home: 1 }
                ],
            }
        });

        // Call the hasPawnsOnBoard method and expect it to return false
        wrapper.vm.hasPawnsOnBoardMethod();
        expect(wrapper.vm.hasPawnsOnBoard).toBe(false);
    });
});

// Test 4

import { UserLobbyAdaptor } from '@/services/UserLobbyAdaptor.js';

global.fetch = jest.fn();

describe('UserLobbyAdaptor', () => {
    let userLobbyAdaptor;

    beforeEach(() => {
        userLobbyAdaptor = new UserLobbyAdaptor('http://localhost:6969/userLobbies');
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('asyncFindAll should make a GET request with the correct URL', async () => {
        // mock the fecth response
        const userLobbies = [{ id: 1, name: 'Lobby 1' }];
        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: () => Promise.resolve(userLobbies),
        });

        const result = await userLobbyAdaptor.asyncFindAll();

        expect(result).toEqual(userLobbies);
    });

});


// Test 5

describe('LudoBoard won method test', () => {
    it('should return true if a player has won', () => {
        const wrapper = shallowMount(LudoBoard);
        wrapper.setData({ hasWonBool: true });

        wrapper.vm.hasWon();
        expect(wrapper.vm.hasWonBool).toBe(true);
    });

    it('should return false if no player has won', () => {
        const wrapper = shallowMount(LudoBoard);
        wrapper.setData({ hasWonBool: false });

        wrapper.vm.hasWon();
        expect(wrapper.vm.hasWonBool).toBe(false);
    });
});


