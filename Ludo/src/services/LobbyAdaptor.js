import {Lobby} from "@/models/Lobby";
import {UserLobby} from "@/models/UserLobby";

export class LobbyAdaptor{
    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        console.log("Created LobbyAdaptor for " + resourcesUrl);
    }

    async fetchJson(url, options = null){
        let response = await fetch(url, options);
        if (response.ok){
            return await response.json();
        } else {
            // response body provides the http-error information
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    async asyncFindAll(){
        console.log("LobbyAdaptor.asyncFindAll()...")
        const lobbies = await this.fetchJson(this.resourcesUrl);
        return lobbies?.map(s => Lobby.copyConstructor(s));
    }

    async asyncIncreaseTurn(lobbyId){
        console.log("LobbyAdaptor.asyncIncreaseTurn()...")
        const lobby = await this.fetchJson(this.resourcesUrl + "/" + lobbyId + "/updateTurn", {
            method: "PUT"
        })
        return Lobby.copyConstructor(lobby)
    }

    async asyncRollDice(){
        const diceroll = await this.fetchJson(this.resourcesUrl + "/roll-dice")
        return diceroll
    }

    async asyncFindById(id){
        console.log("LobbyAdaptor.asyncFindById()...")
        const lobby = await this.fetchJson(this.resourcesUrl + "/" + id)
        return Lobby.copyConstructor(lobby)
    }

    async asyncFindUsersInLobby(lobby_id){
        console.log("LobbyAdaptor.asyncFindUsersInLobby()...")
        const users = await this.fetchJson(this.resourcesUrl + "/" + lobby_id + "/users")
        return Lobby.copyConstructor(users)
    }

    async asyncFindByUserId(id){
        console.log("LobbyAdaptor.asyncFindByUserId()...")
        const lobbies = await this.fetchJson(this.resourcesUrl + "/user/" + id)
        return lobbies?.map(s => Lobby.copyConstructor(s));
    }

    async asyncAllJoinedLobbies(id){
        console.log("LobbyAdaptor.asyncAllJoinedLobby()...")
        const lobbies = await this.fetchJson(this.resourcesUrl + "/joinable/" + id)
        return lobbies?.map(s => Lobby.copyConstructor(s))
    }

    async asyncSave(lobby) {
        console.log("LobbyAdaptor.asyncSave()...")
        const selectedLobby = await this.fetchJson(this.resourcesUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: lobby
        })
        return Lobby.copyConstructor(selectedLobby);
    }

    async asyncAddUserLobby(lobbyId, userId, userLobby){
        console.log("LobbyAdaptor.asyncAddUserLobby()...")
        const selectedUserLobby = await this.fetchJson(this.resourcesUrl + "/" + lobbyId + "/user/" + userId, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: userLobby
        })
        return UserLobby.copyConstructor(selectedUserLobby);
    }

    async asyncDeleteById(id) {
        console.log("LobbyAdaptor.asyncDeleteById()...");
        const lobby = this.fetchJson(this.resourcesUrl + "/" + id, {
            method: "DELETE"
        });
        return Lobby.copyConstructor(lobby);
    }

    async asyncStartMatch(lobbyId){
        const lobby = await this.fetchJson(this.resourcesUrl + "/" + lobbyId + "/start", {
            method: "PUT"
        });
        return Lobby.copyConstructor(lobby)
    }

    async asyncRemoveUserFromLobby(lobbyId, username){
        console.log("LobbyAdaptor.asyncRemoveUserFromLobby()...")
        const userLobby = await this.fetchJson(this.resourcesUrl + "/" + lobbyId + "/users/" + username, {
            method: "DELETE"
        })
        return UserLobby.copyConstructor(userLobby);
    }
}