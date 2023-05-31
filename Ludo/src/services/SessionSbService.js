export class SessionSbService {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCES_URL;
    currentToken;
    currentAccount;

    constructor(resourceUrl, browserStorageItemName) {
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCES_URL = resourceUrl;
        this.getTokenFromBrowserStorage();

        console.log("SessionSbService recovered token: "+this.currentToken)

    }

    signOut() {
        this.saveTokenIntoBrowserStorage(null, null)
    }

    async asyncSignIn(email, password) /* :Promise<User> */ {
        const body = JSON.stringify({email: email, password: password});
        console.log(email + password)
        let response = await fetch(this.RESOURCES_URL + "/login", {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer <token>',
                'Content-Type': 'application/json'
            },
            body: body,
            credentials: 'include',
            })

            if (response.ok) {
                let user = await response.json();
                this.saveTokenIntoBrowserStorage(
                    response.headers.get('Authorization'),
                    user
                );
                return user;
            } else {
                console.log(response)
                return null;
            }
    }

    saveTokenIntoBrowserStorage(token, account) {
        this.currentToken = token;
        this.currentAccount = account;

        if (token == null) {
            this.currentAccount == null;
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC");
        } else {
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC", JSON.stringify(account));
        }
    }

    getTokenFromBrowserStorage() {
        if (this.currentToken != null) return this.currentToken
        this.currentToken = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        let jsonAccount = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC");

        if (this.currentToken == null) {
            this.currentToken = localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME)
            this.currentAccount = localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME+"_ACC")
        }
        if (jsonAccount != null) {
            this.currentAccount = JSON.parse(jsonAccount);
        }
        console.log("SessionService recovered token: ", this.currentToken);
        console.log("Current Account:", this.currentAccount);
        return this.currentToken;
    }

    isAuthenticated() {
        return this.currentToken != null;
    }
}