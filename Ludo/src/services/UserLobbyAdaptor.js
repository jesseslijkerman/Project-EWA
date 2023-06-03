import {UserLobby} from "../models/UserLobby.js";

export class UserLobbyAdaptor{
    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        console.log("Created UserLobbyAdaptor for " + resourcesUrl);
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

    async asyncGetLobbyTurn(lobbyId){
        const lobbyTurn = await this.fetchJson(this.resourcesUrl + "/" + lobbyId + "/current_turn")
        return lobbyTurn
    }

    async asyncWhoseTurn(lobbyId){
        const whoseTurn = await this.fetchJson(this.resourcesUrl + "/" + lobbyId + "/turn")
        return whoseTurn
    }

    async asyncFindAll(){
        console.log("UserLobbyAdaptor.asyncFindAll()...")
        const userLobbies = await this.fetchJson(this.resourcesUrl);
        return userLobbies?.map(s => UserLobby.copyConstructor(s));
    }

    async asyncFindById(id){
        console.log("LobbyAdaptor.asyncFindById()...")
        const userLobby = await this.fetchJson(this.resourcesUrl + "/" + id)
        return UserLobby.copyConstructor(userLobby)
    }

    async asyncGetPlayerNumber(lobbyId, userId){
        const playerNumber = await this.fetchJson(this.resourcesUrl + "/" + lobbyId + "/" + userId + "/player_number")
        return playerNumber
    }

    async asyncSave(userLobby) {
        console.log("LobbyAdaptor.asyncSave()...")
        const selectedUserLobby = await this.fetchJson(this.resourcesUrl, {
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
        const userLobby = this.fetchJson(this.resourcesUrl + "/" + id, {
            method: "DELETE"
        });
        return UserLobby.copyConstructor(userLobby);
    }
}