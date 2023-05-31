import * as fetchIntercept from "fetch-intercept";

export class FetchInterceptor{
    static theInstance; // the singleton instance that has been registered
    session;
    router;
    unregister; // callback function to unregister the instance at shutdown

    constructor(session, router) {
        this.session = session;
        this.router = router;
        // fetchIntercept does not register the object closure, only the methods as functions
        // so we need an additional static reference to the singleton's attributes
        FetchInterceptor.theInstance = this;
        this.unregister = fetchIntercept.register(this)

        console.log("FetchInterceptor has been registered; current token = " +
            FetchInterceptor.theInstance.session.currentToken)
    }

    request(url, options) {
        let token = FetchInterceptor.theInstance.session.currentToken

        if (token == null) {
            // no change
            return [url, options];
        } else if (options == null) {
            // the authorisation header is the only custom option
            return [url, { headers: {Authorization: token}}]
        } else {
            // add authorization header to other options
            let newOptions = {...options};
            newOptions.headers = {
                Authorization: token
            }
            return  [url, newOptions]
        }
    }

    requestError(error) {
        // Called when an error occured during another 'request' interceptor call
        console.log("FetchInterceptor requestError: ", error);
        return Promise.reject(error);
    }

    response(response) {
        console.log("FetchInterceptor response: ", response);
        FetchInterceptor.theInstance.tryRecoverNewJWToken(response);
        if (response.status >= 400 && response.status < 600) {
            FetchInterceptor.theInstance.handleErrorInResponse(response);
        }
        return response;
    }

    responseError(error) {
        // Handle a fetch error
        console.log("FetchInterceptor responseError: ", error);
        return Promise.reject(error);
    }

    async handleErrorInResponse(response){
        if (response.status == 401){
            this.router.push("/sign-in")
        }
    }

    tryRecoverNewJWToken(response){
        let token = response.headers.get('Authorization')
        console.log(token)
        return token
    }
}