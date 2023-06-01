export class Lobby{
    id;
    created;
    gameMode;
    maxPlayers;
    name;
    password;
    status;
    turnTimer;
    whoseTurn;


    constructor(created, gameMode, maxPlayers, name, password, status, turnTimer, whoseTurn) {
        this.created = created;
        this.gameMode = gameMode;
        this.maxPlayers = maxPlayers;
        this.name = name;
        this.password = password;
        this.status = status;
        this.turnTimer = turnTimer;
        this.whoseTurn = whoseTurn;
    }

    static copyConstructor(lobby){
        if (lobby == null){
            return null;
        }

        return Object.assign(new Lobby(), lobby)
    }
}