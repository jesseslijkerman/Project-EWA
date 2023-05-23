export class UserLobby {

    user;
    lobby;
    pawnPosition1;
    pawnPosition2;
    pawnPosition3;
    pawnPosition4;


    constructor(pawnPosition1, pawnPosition2, pawnPosition3, pawnPosition4) {

    constructor(user, lobby, pawnPosition1, pawnPosition2, pawnPosition3, pawnPosition4) {
        this.user = user;
        this.lobby = lobby;
        this.pawnPosition1 = pawnPosition1;
        this.pawnPosition2 = pawnPosition2;
        this.pawnPosition3 = pawnPosition3;
        this.pawnPosition4 = pawnPosition4;
    }

    static copyConstructor(userLobby) {
        if (userLobby == null) {
            return null;
        }

        // Copies properties from selected order to a new order
        return Object.assign(new UserLobby(), userLobby);
    }
}
    static copyConstructor(userLobby){
        if (userLobby == null){
            return null;
        }

        return Object.assign(new UserLobby(), userLobby)
    }

    getUser() {
        return this.user;
    }

    setUser(user) {
        this.user = user;
    }

    getLobby() {
        return this.lobby;
    }

    setLobby(lobby) {
        this.lobby = lobby;
    }

    getPawnPosition1() {
        return this.pawnPosition1;
    }

    setPawnPosition1(pawnPosition1) {
        this.pawnPosition1 = pawnPosition1;
    }

    getPawnPosition2() {
        return this.pawnPosition2;
    }

    setPawnPosition2(pawnPosition2) {
        this.pawnPosition2 = pawnPosition2;
    }

    getPawnPosition3() {
        return this.pawnPosition3;
    }

    setPawnPosition3(pawnPosition3) {
        this.pawnPosition3 = pawnPosition3;
    }

    getPawnPosition4() {
        return this.pawnPosition4;
    }

    setPawnPosition4(pawnPosition4) {
        this.pawnPosition4 = pawnPosition4;
    }
}
