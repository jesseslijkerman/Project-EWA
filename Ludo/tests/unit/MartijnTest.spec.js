
import LudoBoard from '@/components/LudoBoard.vue'


describe('getRollPicture', () => {
    let component;

    beforeEach(() => {
        component = {
            rolled_dice: null,
            getRollPicture: LudoBoard.methods.getRollPicture,
        };
    });

    it('returns correct URL for dice roll of 1', () => {
        component.rolled_dice = 1;
        const expectedUrl = 'https://i.imgur.com/WPho98Y.png';
        const result = component.getRollPicture();
        expect(result).toEqual(expectedUrl);
    });

    it('returns correct URL for dice roll of 2', () => {
        component.rolled_dice = 2;
        const expectedUrl = 'https://i.imgur.com/iJMY57q.png';
        const result = component.getRollPicture();
        expect(result).toEqual(expectedUrl);
    });


    it('returns undefined when dice roll is not between 1 and 6', () => {
        component.rolled_dice = 7;
        const result = component.getRollPicture();
        expect(result).toBeUndefined();
    });

    it('returns undefined when dice roll is not defined', () => {
        component.rolled_dice = null;
        const result = component.getRollPicture();
        expect(result).toBeUndefined();
    });
});

describe('getPawn', () => {
    let component;

    beforeEach(() => {
        component = {
            pawns: {
                'R': [{startPos: 31, position: -1, home: 1},
                    {startPos: 31, position: -1, home: 1},
                    {startPos: 31, position: -1, home: 1},
                    {startPos: 31, position: -1, home: 1}],

                'B': [{startPos: 2, position: -1, home: 1},
                    {startPos: 2, position: -1, home: 1},
                    {startPos: 2, position: -1, home: 1},
                    {startPos: 2, position: -1, home: 1}],

                'G': [{startPos: 21, position: -1, home: 1},
                    {startPos: 21, position: -1, home: 1},
                    {startPos: 21, position: -1, home: 1},
                    {startPos: 21, position: -1, home: 1}],

                'Y': [{startPos: 12, position: -1, home: 1},
                    {startPos: 12, position: -1, home: 1},
                    {startPos: 12, position: -1, home: 1},
                    {startPos: 12, position: -1, home: 1}]
            },
            path: [],
            getPawn: LudoBoard.methods.getPawn,
        };
    });

    it('returns an empty array when there are no pawns', () => {
        component.pawns = {};
        component.path = [{ i: 1, j: 1 }];

        const result = component.getPawn(1, 1);

        expect(result).toEqual([]);
    });

    it('returns the pawns in a specific cell', () => {
        component.pawns = {
            red: { position: 0 },
            blue: { position: 1 },
            green: { position: 2 },
            yellow: { position: 2 },
        };
        component.path = [{ i: 1, j: 1 }, { i: 2, j: 2 }, { i: 3, j: 3 }];

        const result = component.getPawn(3, 3);

        expect(result).toEqual([
            { color: 'green', position: 2 },
            { color: 'yellow', position: 2 },
        ]);
    });
});

import { UsersAdaptor } from '../../src/services/UsersAdaptor';

describe("UsersAdaptor", () => {
    let usersAdaptor;

    beforeEach(() => {
        usersAdaptor = new UsersAdaptor("http://martijn.com/users");
    });

    it("finds a user by ID", async () => {
        // Mock the fetchJson method to return a sample response
        const mockUser = { id: 1, name: "Test User", email: "testuser@example.com" };
        usersAdaptor.fetchJson = jest.fn().mockResolvedValue(mockUser);

        // Call the asyncFindById method
        const foundUser = await usersAdaptor.asyncFindById(1);

        // Check if fetchJson was called with the correct URL
        expect(usersAdaptor.fetchJson).toHaveBeenCalledWith("http://martijn.com/users/1");

        // Check if the returned user is transformed correctly
        expect(foundUser).toEqual(mockUser);
    });

    it("fetches all users", async () => {
        // Mock the fetchJson method to return a sample response
        usersAdaptor.fetchJson = jest.fn().mockResolvedValue([
            { id: 1, name: "User 1", email: "user1@example.com" },
            { id: 2, name: "User 2", email: "user2@example.com" },
        ]);

        // Call the asyncFindAllUsers method
        const users = await usersAdaptor.asyncFindAllUsers();

        // Check if fetchJson was called with the correct URL
        expect(usersAdaptor.fetchJson).toHaveBeenCalledWith("http://martijn.com/users");

        // Check if the returned users are transformed correctly
        expect(users).toEqual([
            { id: 1, name: "User 1", email: "user1@example.com" },
            { id: 2, name: "User 2", email: "user2@example.com" },
        ]);
    });

    it("saves a user", async () => {
        // Mock the fetchJson method to return a sample response
        const mockUser = { id: 1, name: "Test User", email: "testuser@example.com" };
        usersAdaptor.fetchJson = jest.fn().mockResolvedValue(mockUser);

        // Call the asyncSave method
        const savedUser = await usersAdaptor.asyncSave(JSON.stringify(mockUser));

        // Check if fetchJson was called with the correct URL and options
        expect(usersAdaptor.fetchJson).toHaveBeenCalledWith("http://martijn.com/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(mockUser),
        });

        // Check if the returned user is transformed correctly
        expect(savedUser).toEqual(mockUser);
    });
});

import { SessionSbService } from "../../src/services/SessionSbService";

describe("SessionSbService", () => {
    let sessionSbService;
    const storageItemName = "testItem";

    beforeEach(() => {
        sessionStorage.clear();

        sessionSbService = new SessionSbService("http://martijn.com", storageItemName);
    });


    it("saves a token into browser storage", () => {
        const token = "token";
        const account = { id: 1, name: "Test Account" };

        // Call the saveTokenIntoBrowserStorage method
        sessionSbService.saveTokenIntoBrowserStorage(token, account);

        // Check if the correct token is stored
        expect(sessionStorage.getItem(storageItemName)).toEqual(token);

        // Check if the correct account is stored
        expect(JSON.parse(sessionStorage.getItem(storageItemName + "_ACC"))).toEqual(account);
    });

    it("gets a token from browser storage", () => {
        const token = "token";
        const account = { id: 1, name: "Test Account" };

        // Store a token and account in the session storage
        sessionStorage.setItem(storageItemName, token);
        sessionStorage.setItem(storageItemName + "_ACC", JSON.stringify(account));

        // Call the getTokenFromBrowserStorage method
        const fetchedToken = sessionSbService.getTokenFromBrowserStorage();

        // Check if the correct token is fetched
        expect(fetchedToken).toEqual(token);

        // Check if the correct account is fetched
        expect(sessionSbService.currentAccount).toEqual(account);
    });
});

import { UserLobbyAdaptor } from "../../src/services/UserLobbyAdaptor";

describe("UserLobbyAdaptor", () => {
    let userLobbyAdaptor;

    beforeEach(() => {
        userLobbyAdaptor = new UserLobbyAdaptor("http://martijn.com/userLobbies");
    });

    it("updates home", async () => {
        // Mock the fetchJson method to return a sample response
        const mockLobby = { id: 1, name: "Test Lobby", playerNumber: 1, whichHome: "home1", zeroOrOne: 0 };
        userLobbyAdaptor.fetchJson = jest.fn().mockResolvedValue(mockLobby);

        // Call the asyncUpdateHome method
        const updatedLobby = await userLobbyAdaptor.asyncUpdateHome(mockLobby.id, mockLobby.playerNumber, mockLobby.whichHome, mockLobby.zeroOrOne);

        // Check if fetchJson was called with the correct URL
        expect(userLobbyAdaptor.fetchJson).toHaveBeenCalledWith(`http://martijn.com/userLobbies/${mockLobby.id}/${mockLobby.playerNumber}/${mockLobby.whichHome}/${mockLobby.zeroOrOne}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: mockLobby.zeroOrOne
        });

        // Check if the returned lobby is transformed correctly
        expect(updatedLobby).toEqual(mockLobby);
    });

    it("fetches all user lobbies", async () => {
        // Mock the fetchJson method to return a sample response
        const mockLobbies = [
            { id: 1, name: "User Lobby 1" },
            { id: 2, name: "User Lobby 2" }
        ];
        userLobbyAdaptor.fetchJson = jest.fn().mockResolvedValue(mockLobbies);

        // Call the asyncFindAll method
        const lobbies = await userLobbyAdaptor.asyncFindAll();

        // Check if fetchJson was called with the correct URL
        expect(userLobbyAdaptor.fetchJson).toHaveBeenCalledWith("http://martijn.com/userLobbies");

        // Check if the returned lobbies are transformed correctly
        expect(lobbies).toEqual(mockLobbies);
    });

    it("finds a user lobby by ID", async () => {
        // Mock the fetchJson method to return a sample response
        const mockLobby = { id: 1, name: "Test User Lobby" };
        userLobbyAdaptor.fetchJson = jest.fn().mockResolvedValue(mockLobby);

        // Call the asyncFindById method
        const foundLobby = await userLobbyAdaptor.asyncFindById(1);

        // Check if fetchJson was called with the correct URL
        expect(userLobbyAdaptor.fetchJson).toHaveBeenCalledWith("http://martijn.com/userLobbies/1");

        // Check if the returned lobby is transformed correctly
        expect(foundLobby).toEqual(mockLobby);
    });
});








