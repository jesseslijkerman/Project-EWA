
export class UserLobby {

    color;
    playerNumber;
    pawnAtHome1;
    pawnAtHome2;
    pawnAtHome3;
    pawnAtHome4;


    constructor(color, playerNumber, pawnAtHome1, pawnAtHome2, pawnAtHome3, pawnAtHome4) {
        this.color = color;
        this.playerNumber = playerNumber;
        this.pawnAtHome1 = pawnAtHome1;
        this.pawnAtHome2 = pawnAtHome2;
        this.pawnAtHome3 = pawnAtHome3;
        this.pawnAtHome4 = pawnAtHome4;
    }

    static copyConstructor(userLobby) {
        if (userLobby == null) {
            return null;
        }

        // Copies properties from selected order to a new order
        return Object.assign(new UserLobby(), userLobby);
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


}