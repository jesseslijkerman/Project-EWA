import router from "@/router";
import {shallowMount} from "@vue/test-utils";
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";

describe('Login', () => {
    let wrapper;
    let pushSpy;

    beforeEach(() => {
        pushSpy = jest.spyOn(router, 'push');
        wrapper = shallowMount(Login, {
            global: {
                plugins: [router],
            },
        });
    });


    it('redirects to register page', async () => {
        await wrapper.vm.handleRegisterPage();
        expect(pushSpy).toHaveBeenCalledWith('/Register');
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