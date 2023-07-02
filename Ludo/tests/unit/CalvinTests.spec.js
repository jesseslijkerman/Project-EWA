import {shallowMount} from "@vue/test-utils";
import ResetPassword from '@/components/ResetPassword.vue';
import Register from "@/components/Register.vue";
import ForgotPassword from '@/components/ForgotPassword.vue';
import router from "@/router";

describe('ResetPassword', () =>{
    it('should not call changePassword method if confirmation is canceled', async () => {
        // Mock the confirm function to return false
        global.confirm = jest.fn(() => false);


        // Mock the registerService dependency
        const mockRegisterService = {
            changePassword: jest.fn()
        };

        // Shallow mount the component with the mocked registerService
        const wrapper = shallowMount(ResetPassword, {
            mocks: {
                registerService: mockRegisterService,
                sessionService: {
                    currentAccount: {
                        id: 123
                    }
                }
            }
        });

        // Set the entered new password
        wrapper.vm.entered_new_password = 'newPassword';

        // Trigger the handlePassChange method
        await wrapper.vm.handlePassChange();

        // Expect the changePassword method not to have been called
        expect(mockRegisterService.changePassword).not.toHaveBeenCalled();
    });
});


describe('Register', () => {
    let wrapper;
    let registerServiceMock;

    it('redirects to login page on "Login here" link click', async () => {
        // Create a mock for the registerService
        registerServiceMock = {
            asyncSave: jest.fn().mockResolvedValueOnce(),
        };

        wrapper = shallowMount(Register, {
            // Provide the registerService and router mocks as global Vue plugins
            global: {
                plugins: [
                    {
                        provide: 'registerService', // Provide the service name
                        useValue: registerServiceMock, // Use the mock object
                    },
                    {
                        provide: 'router', // Provide the router name
                        useValue: router, // Use the mock object
                    },
                ],
            },
        });
        const routerPushMock = jest.fn();
        wrapper.vm.$router = {
            push: routerPushMock,
        };

        // Trigger link click
        await wrapper.vm.handleRedirect();

        // Verify that the router.push method was called with the correct route
        expect(routerPushMock).toHaveBeenCalledWith('/login');
    });
});

describe('ForgotPassword', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallowMount(ForgotPassword, {
            global: {
                plugins: [router],
            },
        });
    });

    it('redirects to login page when backToLogin is called', async () => {
        // Spy on the router's push method
        const pushSpy = jest.spyOn(router, 'push');

        // Call the backToLogin method
        await wrapper.vm.backToLogin();

        // Assert that the router's push method was called with the correct argument
        expect(pushSpy).toHaveBeenCalledWith('/login');
    });
});



