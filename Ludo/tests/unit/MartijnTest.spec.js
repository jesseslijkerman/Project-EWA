import { shallowMount } from '@vue/test-utils'
import LudoBoard from '@/components/LudoBoard.vue'
import flushPromises from 'flush-promises'

jest.mock('@/services/userLobbyAdapter.js')
jest.mock('@/services/lobbyAdaptor.js')
jest.mock('@/services/sessionSbService.js')
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
    await flushPromises() 
    expect(wrapper.vm.path).toBeTruthy() 
  })

  it('calls userLobbyService.asyncGetLobbyTurn on created', () => {
    expect(wrapper.vm.userLobbyService.asyncGetLobbyTurn).toHaveBeenCalled()
  })

})
