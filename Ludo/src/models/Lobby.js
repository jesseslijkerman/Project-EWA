export class Lobby{
    id;
    created;
    gameMode;
    maxPlayers;
    name;
    password;
    turnTimer;
    boardState;
    status;
    whoseTurn;


    constructor(created, gameMode, maxPlayers, name, password, turnTimer, status, whoseTurn, boardState) {
        this.created = created;
        this.gameMode = gameMode;
        this.maxPlayers = maxPlayers;
        this.name = name;
        this.password = password;
        this.turnTimer = turnTimer;
        this.status = status;
        this.whoseTurn = whoseTurn;
        this.boardState = boardState;
    }

    static copyConstructor(lobby){
        if (lobby == null){
            return null;
        }

        return Object.assign(new Lobby(), lobby)
    }
}