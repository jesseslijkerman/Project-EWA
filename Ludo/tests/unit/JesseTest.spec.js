import { shallowMount } from '@vue/test-utils';
import CreateGame from '@/components/CreateGame.vue';
import FriendTemplate from '@/components/Friend.vue';
import {LobbyAdaptor} from "../../src/services/LobbyAdaptor";

describe('CreateGame', () => {
    it('calls createLobby method and redirects to "/ongoing-matches" when button is clicked', async () => {
        // Mocking dependencies
        const lobbyServiceMock = {
            asyncSave: jest.fn(() => ({ id: 123 })),
            asyncAddUserLobby: jest.fn(),
        };
        const sessionServiceMock = {
            currentAccount: { id: 456 },
        };

        // Mocking $router object
        const mockRouterPush = jest.fn();
        const mockRouter = {
            push: mockRouterPush,
        };

        // Mounting the component
        const wrapper = shallowMount(CreateGame, {
            global: {
                provide: {
                    lobbyService: lobbyServiceMock,
                    sessionService: sessionServiceMock,
                },
                mocks: {
                    $router: mockRouter,
                },
            },
        });

        // Mocking user inputs
        wrapper.vm.gameMode = 1;
        wrapper.vm.maxPlayers = 2;
        wrapper.vm.name = 'Game 1';
        wrapper.vm.password = '123456';
        wrapper.vm.turnTimer = 60;

        // Triggering the button click
        await wrapper.find('button').trigger('click');

        // Assertions
        expect(wrapper.vm.isLoading).toBe(true);
        expect(lobbyServiceMock.asyncSave).toHaveBeenCalledWith(expect.any(String));
        expect(lobbyServiceMock.asyncAddUserLobby).toHaveBeenCalledWith(
            123,
            456,
            expect.any(String)
        );
        expect(mockRouterPush).toHaveBeenCalledWith('/ongoing-matches');
    });
});

describe('FriendTemplate', () => {
    let wrapper;

    beforeEach(() => {
        // Mocking dependencies
        const registerServiceMock = {
            asyncAddFriend: jest.fn(),
            asyncInvitePlayer: jest.fn(),
            asyncRemoveFriend: jest.fn(),
        };
        const sessionServiceMock = {
            currentAccount: { id: 123 },
        };

        // Mounting the component
        wrapper = shallowMount(FriendTemplate, {
            global: {
                provide: {
                    registerService: registerServiceMock,
                    sessionService: sessionServiceMock,
                },
            },
            props: {
                id: 456,
                username: 'John Doe',
                email: 'john@example.com',
                countryCode: 'US',
                picture: 'https://example.com/profile.jpg',
            },
        });
    });

    it('renders the component with the correct props', () => {
        expect(wrapper.find('.profile-link').text()).toBe('John Doe');
        expect(wrapper.find('.text-muted').text()).toBe('US');
        expect(wrapper.find('.profile-photo-lg').attributes('src')).toBe('https://example.com/profile.jpg');
    });

    it('calls asyncRemoveFriend when removeFriend button is clicked', async () => {
        // Set button prop to 'remove'
        wrapper.setData({ button: 'remove' });

        await wrapper.find('.remove').trigger('click');
        expect(wrapper.vm.registerService.asyncRemoveFriend).toHaveBeenCalled();
        expect(wrapper.vm.registerService.asyncRemoveFriend).toHaveBeenCalledWith(123, 456);
    });
});

describe("LobbyAdaptor", () => {
    let lobbyAdaptor;

    beforeEach(() => {
        lobbyAdaptor = new LobbyAdaptor("http://example.com/lobbies");
    });

    it("fetches all lobbies", async () => {
        // Mock the fetchJson method to return a sample response
        lobbyAdaptor.fetchJson = jest.fn().mockResolvedValue([
            { id: 1, name: "Lobby 1" },
            { id: 2, name: "Lobby 2" },
        ]);

        // Call the asyncFindAll method
        const lobbies = await lobbyAdaptor.asyncFindAll();

        // Check if fetchJson was called with the correct URL
        expect(lobbyAdaptor.fetchJson).toHaveBeenCalledWith("http://example.com/lobbies");

        // Check if the returned lobbies are transformed correctly
        expect(lobbies).toEqual([
            { id: 1, name: "Lobby 1" },
            { id: 2, name: "Lobby 2" },
        ]);
    });

    it("finds a lobby by ID", async () => {
        // Mock the fetchJson method to return a sample response
        const mockLobby = { id: 1, name: "Test Lobby" };
        lobbyAdaptor.fetchJson = jest.fn().mockResolvedValue(mockLobby);

        // Call the asyncFindById method
        const foundLobby = await lobbyAdaptor.asyncFindById(1);

        // Check if fetchJson was called with the correct URL
        expect(lobbyAdaptor.fetchJson).toHaveBeenCalledWith("http://example.com/lobbies/1");

        // Check if the returned lobby is transformed correctly
        expect(foundLobby).toEqual(mockLobby);
    });
});
