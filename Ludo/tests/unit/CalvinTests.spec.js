import {shallowMount} from "@vue/test-utils";
import ResetPassword from '@/components/ResetPassword.vue';
import ForgotPassword from '@/components/ForgotPassword.vue';
import Register from "@/components/Register.vue";
import Login from "@/components/Login.vue";
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

describe('Login', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallowMount(Login, {
            global: {
                plugins: [router],
            },
        });
    });

    it('redirects to forgot password page', async () => {
        const pushSpy = jest.spyOn(router, 'push');
        await wrapper.vm.handleForgotPasswordPage();
        expect(pushSpy).toHaveBeenCalledWith('/ForgotPassword');
    });
});



