export class Lobby{
    id;
    created;
    gameMode;
    maxPlayers;
    name;
    password;
    turnTimer;


    constructor(created, gameMode, maxPlayers, name, password, turnTimer) {
        this.created = created;
        this.gameMode = gameMode;
        this.maxPlayers = maxPlayers;
        this.name = name;
        this.password = password;
        this.turnTimer = turnTimer;
    }

    static copyConstructor(lobby){
        if (lobby == null){
            return null;
        }

        return Object.assign(new Lobby(), lobby)
    }
}