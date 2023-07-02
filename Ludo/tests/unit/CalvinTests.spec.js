import { shallowMount } from '@vue/test-utils';
import ResetPassword from '@/components/ResetPassword.vue';
import ForgotPassword from '@/components/ForgotPassword.vue';

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


describe('ResetPassword', () => {
    it('should call changePassword method if confirmation is true', async () => {
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

        // Mock the confirm function to return true
        global.confirm = jest.fn(() => true);
    });
});

describe('ForgotPassword', () => {
    it('should call forgotPassword method with the entered email', async () => {
        // Mock the registerService dependency
        const mockRegisterService = {
            forgotPassword: jest.fn()
        };

        // Shallow mount the component with the mocked registerService
        const wrapper = shallowMount(ForgotPassword, {
            mocks: {
                registerService: mockRegisterService
            }
        });

        // Set the entered email
        wrapper.vm.email = 'test@example.com';

        // Trigger the handleSubmit method
        await wrapper.vm.handleSubmit();

        // Expect the forgotPassword method to have been called with the entered email
        expect(mockRegisterService.forgotPassword).toHaveBeenCalledWith('test@example.com');
    });
});




