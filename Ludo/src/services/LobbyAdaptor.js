import {Lobby} from "../models/Lobby.js";

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

    async asyncFindById(id){
        console.log("LobbyAdaptor.asyncFindById()...")
        const lobby = await this.fetchJson(this.resourcesUrl + "/" + id)
        return Lobby.copyConstructor(lobby)
    }

    async asyncFindByUserId(id){
        console.log("LobbyAdaptor.asyncFindByUserId()...")
        const lobbies = await this.fetchJson(this.resourcesUrl + "/user/" + id)
        return lobbies?.map(s => Lobby.copyConstructor(s));
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

    async asyncDeleteById(id) {
        console.log("LobbyAdaptor.asyncDeleteById()...");
        const lobby = this.fetchJson(this.resourcesUrl + "/" + id, {
            method: "DELETE"
        });
        return Lobby.copyConstructor(lobby);
    }
}