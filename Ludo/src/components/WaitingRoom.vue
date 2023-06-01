<template>
  <div class="leaveButton">
    <a @click="removeFromLobby">Leave lobby</a>
  </div>
  <h2 class="waiting-message">Waiting for host to start the match...</h2>
  <div class="page-container">

    <br>
    <table class="data-table">
      <tbody>
      <tr>
        <th>ID</th>
        <td>{{ lobbyData.id }}</td>
      </tr>
      <tr>
        <th>Created</th>
        <td>{{ lobbyData.created }}</td>
      </tr>
      <tr>
        <th>Game Mode</th>
        <td>{{ lobbyData.gameMode }}</td>
      </tr>
      <tr>
        <th>Max Players</th>
        <td>{{ lobbyData.maxPlayers }}</td>
      </tr>
      <tr>
        <th>Name</th>
        <td>{{ lobbyData.name }}</td>
      </tr>
      <tr>
        <th>Password</th>
        <td>{{ lobbyData.password }}</td>
      </tr>
      <tr>
        <th>Turn Timer</th>
        <td>{{ lobbyData.turnTimer }}</td>
      </tr>
      <tr>
        <th>Status</th>
        <td>{{ lobbyData.status }}</td>
      </tr>
      <tr>
        <th>Whose Turn</th>
        <td>{{ lobbyData.whoseTurn }}</td>
      </tr>
      </tbody>
    </table>
    <div class="extra-divs">
      <div class="extra-div red">
        <div class="color-box"></div>
        <div class="username">{{ playerData[0] }}</div>
      </div>
      <div class="extra-div blue">
        <div class="color-box"></div>
        <div class="username">{{ playerData[1] }}</div>
      </div>
      <div class="extra-div green">
        <div class="color-box"></div>
        <div class="username">{{ playerData[2] }}</div>
      </div>
      <div class="extra-div yellow">
        <div class="color-box"></div>
        <div class="username">{{ playerData[3] }}</div>
      </div>
    </div>
  </div>
<div v-if="isHost">
  <button class="start-match-button" @click="startMatch" v-if="this.lobbyData.status === 'INACTIVE'" >Start match</button>
</div>
  <button class="start-match-button" @click="enterGame" v-if="this.lobbyData.status === 'ACTIVE'">{{ gameStartedText }}</button>
</template>

<script>
export default {
  name: "WaitingRoom",
  inject: ["lobbyService", "sessionService", "userLobbyService"],
  data() {
    return {
      lobbyData: null,
      isHost: false,
      playerData: null,
      lobbyNumber: null,
      gameStartedText: "Match has been started, click here to enter the game!"
    };
  },
  methods: {

    async getLobbyInfo() {
      this.lobbyNumber = parseInt(window.location.pathname.split('/').pop());
      this.lobbyData = await this.lobbyService.asyncFindById(this.lobbyNumber);
      this.playerData = await this.lobbyService.asyncFindUsersInLobby(this.lobbyNumber)
      console.log(this.lobbyData);
      console.log(this.playerData);
    },

    enterGame() {
      const currentUrl = window.location.href;
      const newUrl = currentUrl + "/play";
      window.location.href = newUrl;
    },

    async removeFromLobby(){
      await this.lobbyService.asyncRemoveUserFromLobby(this.lobbyNumber, this.sessionService.user);
    },

    // Check if currently logged in user is the host
    async checkHost() {
      console.log(this.lobbyData);
      if(this.sessionService.color === "RED") {
        this.isHost = true;
      }
    },

    async startMatch() {
      await this.lobbyService.asyncStartMatch(this.lobbyNumber);
    },

  },
  created() {
    this.getLobbyInfo();
    this.checkHost();
},

  mounted() {
    const refreshInterval = setInterval(() => {
      if (this.lobbyData.status === 'ACTIVE') {
        clearInterval(refreshInterval);
      } else {
        location.reload();
      }
    }, 5000);
  }

};
</script>

<style scoped>

.leaveButton {

  margin-bottom: 10px;
}

.start-match-button {
  background-color: green;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.page-container {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
}

.waiting-message {
  color: white;
  font-size: 24px;
  margin-bottom: 10px;
}

.data-table {
  width: 500px;
  margin-bottom: 20px;
  border-collapse: collapse;
}

.data-table th,
.data-table td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
  color: black;
}

.data-table th {
  font-weight: bold;
}

.extra-divs {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  gap: 20px;
}

.extra-div {
  flex: 1;
  height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  font-weight: bold;
  font-size: 16px;
  border-style: solid;
  border-color: black;
}

.red {
  background-color: red;
  color: white;
}

.blue {
  background-color: blue;
  color: white;
}

.green {
  background-color: green;
  color: white;
}

.yellow {
  background-color: yellow;
  color: black;
}


</style>
