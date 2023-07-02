import { shallowMount } from '@vue/test-utils'
import LudoBoard from '@/components/LudoBoard.vue'

jest.mock('@/services/UserLobbyAdaptor.js')
jest.mock('@/services/LobbyAdaptor.js')
jest.mock('@/services/SessionSbService.js')
jest.mock('@/services/UsersAdaptor.js')

  beforeEach(() => {
    wrapper = shallowMount(WaitingRoom, {
      data() {
        return {
          lobbyData: {
            status: 'INACTIVE',
            id: 1,
            created: '2023-07-02',
            gameMode: 'Mode',
            maxPlayers: 4,
            name: 'Lobby Name',
            password: 'Password',
            turnTimer: 60,
            whoseTurn: 'Player',
          },
          isHost: true,
          playerData: ['Player 1', 'Player 2', 'Player 3', 'Player 4'],
          lobbyNumber: 1,
          gameStartedText: 'Match has been started, click here to enter the game!',
          buttonClicked: false,
          enoughPlayers: true,
        };
      },
      mocks: {
        lobbyService: {
          asyncFindById: jest.fn().mockResolvedValue('181'),
          asyncFindUsersInLobby: jest.fn(),
          asyncRemoveUserFromLobby: jest.fn(),
          asyncStartMatch: jest.fn(),
        },
        sessionService: {
          currentAccount: { id: 123 },
        },
        userLobbyService: {
          asyncGetPlayerNumber: jest.fn(),
        },
      },
    });
  });

  // afterEach(() => {
  //     wrapper.destroy();
  // });

  it('creates the path when the component is created', async () => {
    expect(wrapper.vm.path).toBeTruthy() 
  })

  // it('calls userLobbyService.asyncGetLobbyTurn on created', () => {
  //   expect(wrapper.vm.userLobbyService.asyncGetLobbyTurn).toHaveBeenCalled()
  // })



})

  it('calls start match and sets buttonClicked to true when start match button is clicked', async () => {
    await wrapper.find('.start-match-button').trigger('click');
    expect(wrapper.vm.$options.mocks.lobbyService.asyncStartMatch).toBeCalledWith(1);
    expect(wrapper.vm.buttonClicked).toBe(true);
  });

  // Add more test cases as needed

});