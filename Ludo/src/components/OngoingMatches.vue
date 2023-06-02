<template>
  <div class="container">
    <div class="header">
      <h1 class="title">Ludo Lobbies</h1>
      <div class="slidercontainer">
        <label class="switch">
          <input type="checkbox" v-model="isJoinable" @change="joinableLobbies">
          <span class="slider"></span>
        </label>
        <span id="switchStatus">{{switchStatus}}</span>
      </div>
    </div>
    <div class="matches">
      <div v-for="(match, index) in matches" :key="index" class="match">
        <div class="match-info">
          <h2 class="match-title">{{ match.name }}</h2>
          <p class="match-turn">Turn: {{ match.whoseTurn }}</p>
          <p class="match-players">Players: {{ match.maxPlayers }}</p>
          <p class="match-timeStarted">
            Time started: {{ formatDateTime(match.created) }}
          </p>
        </div>
        <router-link :to="'/match/' + match.id" class="btn btn-primary" @click="joinMatch(match.id)">Play</router-link>
      </div>
    </div>
    <router-link :to="'/createGame'" class="btn btn-primary create-btn">Create game</router-link>
  </div>
</template>

<script>

import {UserLobby} from "@/models/UserLobby";
export default {
  name: "OngoingMatches",
  inject: ["lobbyService", "sessionService", "userLobbyService"],
  data() {
    return {
      matches: [],
      isJoinable: false,
      currentAccount: null
    };
  },
  async created(){
    this.currentAccount = this.sessionService.currentAccount
    this.matches = await this.lobbyService.asyncFindByUserId(this.currentAccount.id)
    console.log(this.matches)
    console.log(this.lobbyService.asyncFindById(this.currentAccount.id))
  },
  methods: {
    formatDateTime(dateTimeStr) {
      const dateTime = new Date(dateTimeStr);
      return dateTime.toLocaleString();
    },
    async loadLobbies() {
      if (!this.isJoinable) {
        this.matches = await this.lobbyService.asyncFindByUserId(this.currentAccount.id);
      } else {
        this.matches = await this.lobbyService.asyncAllJoinedLobbies(this.currentAccount.id);
      }
    },
    async joinableLobbies(){
      await this.loadLobbies();
    },

    async joinMatch(matchId) {

      const response = await this.userLobbyService.asyncFindById(matchId);

      const responseArray = Object.values(response);

      for (let i = 0; i < responseArray.length; i++) {
        const item = responseArray[i];

        if (typeof item.playerNumber === 'number') {
          let playerNumber = item.playerNumber;

          if (playerNumber === 1) {
            let userLobby = new UserLobby("BLUE", 2);
            await this.lobbyService.asyncAddUserLobby(matchId, this.sessionService.currentAccount.id, JSON.stringify(userLobby));
          } else if (playerNumber === 2) {
            let userLobby = new UserLobby("GREEN", 3);
            await this.lobbyService.asyncAddUserLobby(matchId, this.sessionService.currentAccount.id, JSON.stringify(userLobby));
          } else if (playerNumber === 3) {
            let userLobby = new UserLobby("YELLOW", 4);
            await this.lobbyService.asyncAddUserLobby(matchId, this.sessionService.currentAccount.id, JSON.stringify(userLobby));
          }
          } else {
            console.log("Lobby is full");
          }
        }

    }},
  computed: {

    switchStatus: function () {
      return this.isJoinable ? 'Joinable' : 'Joined';
    }
  },
  async onReload() {
    this.matches = await this.loadLobbies();
  }
};
</script>

<style scoped>
.container {
  max-width: 860px;
  margin: 0 auto;
  padding: 2rem;
  background-color: #222;
  color: #fff;
}

.title {
  margin-bottom: 1.5rem;
  font-size: 2.5rem;
  font-weight: bold;
}

.matches {
  display: flex;
  flex-wrap: wrap;
}

.match {
  width: 250px;
  padding: 1.5rem;
  margin-bottom: 2rem;
  border-radius: 0.25rem;
  margin: 1px;
  background-color: #333;
  color: #fff;
}

.match-info {
  margin-bottom: 1.5rem;
}

.match-title {
  margin-bottom: 0.5rem;
  font-size: 1.5rem;
  font-weight: bold;
  text-align: center;
}

.match-description {
}

.match-turn {
  font-size: 1rem;
}

.btn {
  display: inline-block;
  padding: 0.5rem 1.5rem;
  border-radius: 0.25rem;
  background-color: #007bff;
  color: #fff;
  text-decoration: none;
  transition: background-color 0.2s ease;
  margin-left: 70px;
}

.btn:hover {
  background-color: #0069d9;
}

.create-btn{
  margin-top: 5px;
}

.container {
  position: relative;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.switch {
  display: flex;
  align-items: center;
}

.slider {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
  background-color: #ccc;
  border-radius: 34px;
  transition: background-color 0.3s;
}

.slidercontainer{
  display: flex;
  flex-direction: column;
  align-items: center;
}

span#switchStatus {
  display: inline-block;
}

.switch input {
  display: none;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  border-radius: 50%;
  transition: transform 0.3s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

</style>
