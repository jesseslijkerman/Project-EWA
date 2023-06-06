<template>
  <div class="container">
    <div class="header">
      <h1 class="title">Ludo Lobbies</h1>
      <h2>logged in as: {{ this.sessionService.currentAccount.userName }}</h2>
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
        <router-link v-if="isJoinable" :to="'/match/' + match.id" class="btn btn-primary" @click="joinMatch(match.id)">Join</router-link>
        <router-link v-if="!isJoinable" :to="'/match/' + match.id" class="btn btn-primary" >Continue</router-link>
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

      let filledSpots = 0;
      let highestPlayerNumber = -1; // Variable to track the highest playerNumber encountered

      for (let i = 0; i < responseArray.length; i++) {
        const item = responseArray[i];
        console.log(item);

        if (typeof item.playerNumber === 'number') {
          filledSpots++;
          const playerNumber = item.playerNumber;

          if (playerNumber > highestPlayerNumber) {
            highestPlayerNumber = playerNumber;
          }

          if (filledSpots <= 3) {
            let availablePlayerNumber = highestPlayerNumber + 1;

            let userLobby;
            if (availablePlayerNumber === 2) {
              userLobby = new UserLobby("BLUE", 2, 1, 1, 1, 1);
            } else if (availablePlayerNumber === 3) {
              userLobby = new UserLobby("GREEN", 3, 1, 1, 1, 1);
            } else if (availablePlayerNumber === 4) {
              userLobby = new UserLobby("YELLOW", 4, 1, 1, 1, 1);
            }

            await this.lobbyService.asyncAddUserLobby(matchId, this.sessionService.currentAccount.id, JSON.stringify(userLobby));

            highestPlayerNumber = availablePlayerNumber;
          } else {
            console.log("Lobby is full");
          }
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
  max-width: 90%;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #121212;
  color: #fff;
  border-radius: 1rem;
  box-shadow: 0px 2px 8px rgba(0,0,0,0.3);
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #333;
  padding-bottom: 1rem;
  margin-bottom: 2rem;
}

.title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #007bff;
}

.matches {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  grid-gap: 2rem;
}

.match {
  background-color: #333;
  color: #fff;
  border-radius: 1rem;
  padding: 1rem;
  transition: transform 0.3s ease-in-out;
  box-shadow: 0px 2px 8px rgba(0,0,0,0.3);
}

.match:hover {
  transform: scale(1.03);
}

.match-info {
  margin-bottom: 1.5rem;
}

.match-title {
  font-size: 1.5rem;
  font-weight: bold;
  text-align: center;
  color: #007bff;  
}

.btn {
  display: inline-block;
  padding: 0.5rem 1.5rem;
  border-radius: 0.25rem;
  background-color: #007bff;
  color: #fff;
  text-decoration: none;
  transition: background-color 0.2s ease;
}

.btn:hover {
  background-color: #0069d9;
}

.create-btn {
  background-color: #007bff;
  color: #fff;
  margin-top: 1rem;
  padding: 0.75rem 1rem;
  border-radius: 0.5rem;
  text-align: center;
  display: inline-block;
  font-weight: bold;
}

.create-btn:hover {
  background-color: #0069d9;
}

.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: .4s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  transition: .4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

.slidercontainer{
  display: flex;
  flex-direction: column;
  align-items: center;
}

span#switchStatus {
  margin-top: 0.5rem;
  font-size: 1.2rem;
  font-weight: bold;
  color: #007bff;
}
</style>
