import { shallowMount } from '@vue/test-utils'
import LudoBoard from '@/components/LudoBoard.vue'

jest.mock('@/services/UserLobbyAdaptor.js')
jest.mock('@/services/LobbyAdaptor.js')
jest.mock('@/services/SessionSbService.js')
jest.mock('@/services/UsersAdaptor.js')

describe('LudoBoard.vue', () => {
  let wrapper

  beforeEach(() => {
    
    wrapper = shallowMount(LudoBoard, {
      propsData: {},
      mocks: {
        
        userLobbyService: {},
        lobbyService: {},
        sessionService: {
          currentAccount: {
            userName: 'test_user'
          }
        },
        registerService: {},
      },
      stubs: ['router-link', 'router-view'] 
    })
  })

  it('renders correctly', () => {
    expect(wrapper.element).toMatchSnapshot()
  })

  it('creates the path when the component is created', async () => {
    expect(wrapper.vm.path).toBeTruthy() 
  })

  // it('calls userLobbyService.asyncGetLobbyTurn on created', () => {
  //   expect(wrapper.vm.userLobbyService.asyncGetLobbyTurn).toHaveBeenCalled()
  // })



})
