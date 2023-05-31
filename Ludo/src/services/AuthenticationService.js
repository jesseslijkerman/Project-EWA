import VueJwtDecode from 'vue-jwt-decode';

export class AuthenticationService {
    apiUrl;
    activeUser;
    tokenExpiration;

    constructor() {
        this.apiUrl = "http://localhost6969/userAuth";
    }

    storeToken(token) {
        localStorage.setItem("token", token);
    }

    async login(email, password) {
        const requestData = JSON.stringify({email: email, password: password});

        let response = await fetch(this.apiUrl + "/login", {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: requestData,
            credentials: "include",
        })
        if (response.ok) {
            this.storeToken(response.headers.get("Authorization"));
            this.refreshUserDetails();
            return;
        } else {
            console.log(response);
            return null;
        }
    }

    async logout() {
        localStorage.clear();
    }

    getTokenFromBrowserStorage() {
        return localStorage.getItem('token');
    }

    refreshUserDetails() {
        let storedToken = localStorage.getItem('token');

        if (storedToken) {
            const decodedToken = VueJwtDecode.decodeToken(storedToken);
            this.activeUser = new userDummy();

            this.activeUser.email = decodedToken.email;
            this.activeUser.userName = decodedToken.userName;
            this.activeUser.countryCode = decodedToken.countryCode;
            this.activeUser.id = decodedToken.userId;
            this.tokenExpiration = decodedToken.exp;

            localStorage.setItem('user', this.activeUser.id);
            localStorage.setItem('userName', this.activeUser.userName);
            localStorage.setItem('userCountryCode', this.activeUser.countryCode);
            localStorage.setItem('userEmail', this.activeUser.email);
            localStorage.setItem('token', storedToken);
            localStorage.setItem("expirationDate", this.tokenExpiration);
        }
    }
}
