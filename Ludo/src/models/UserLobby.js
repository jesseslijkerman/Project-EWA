
export class UserLobby {
    pawnPosition1;
    pawnPosition2;
    pawnPosition3;
    pawnPosition4;


    constructor(pawnPosition1, pawnPosition2, pawnPosition3, pawnPosition4) {
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
