<template>
  <div class="container">
    <h1 class="title">Create a game</h1>
    <form @submit.prevent="handleSumbit">

      <!-- Max players input -->
      <div class="form-outline mb-4">
        <label class="form-label">Max players</label>
        <div class="form-check">
          <input
              type="radio"
              class="form-check-label"
              value=2
              v-model="maxPlayers"
          />
          <label class="form-check-label">2</label>
        </div>

        <div class="form-check">
          <input
              type="radio"
              class="form-check-label"
              value=3
              v-model="maxPlayers"
          />
          <label class="form-check-label">3</label>
        </div>

        <div class="form-check">
          <input
              type="radio"
              class="form-check-label"
              value=4
              v-model="maxPlayers"
          />
          <label class="form-check-label">4</label>
        </div>
      </div>

      <!-- Lobby name input -->
      <div class="form-outline mb-4">
        <label class="form-label">Name</label>
        <input
          type="text"
          class="form-control"
          v-model="name"
          placeholder="Name"
        />
      </div>

      <!-- Turn timer input -->
      <div class="form-outline mb-4">
        <label class="form-label">Turn Timer (in seconds)</label>
        <input
          type="number"
          class="form-control"
          v-model="turnTimer"
          placeholder="Turn Timer"
        />
      </div>

      <!-- Submit button -->
      <div class="text-center">
        <button
            type="button"
            class="btn btn-primary btn-block mb-4"
            v-on:click="createLobby"
            :disabled="isLoading"
        >
          <span v-if="isLoading">
            <i class="fas fa-spinner fa-spin"></i>
            Creating...
          </span>
          <span v-else>
            Create game
          </span>
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import {Lobby} from "../models/Lobby";
import {UserLobby} from "../models/UserLobby";

export default {
  name: "CreateGame",
  inject: ["lobbyService", "sessionService"],
  created() {
    this.currentAccount = this.sessionService.currentAccount;
    console.log(this.currentAccount.id)
  },
  data() {
    return {
      gameMode: this.gameMode,
      maxPlayers: this.maxPlayers,
      name: this.name,
      password: this.password,
      status: 'INACTIVE',
      isLoading: false,
      turnTimer: this.turnTimer,
      whoseTurn: 1,
      boardState: "['pR1','pR2','X','X',1,1,1,'X','X','pB1','pB2']," +
                  "['pR3','pR4','X','X',1,'hB1',1,'X','X','pB3','pB4']," +
                  "['X','X','X','X',1,'hB2',1,'X','X','X','X']," +
                  "['X','X','X','X',1,'hB3',1,'X','X','X','X']," +
                  "[1,1,1,1,1,'hB4',1,1,1,1,1]," +
                  "[1,'hR1','hR2','hR3','hR4',0,'hY4','hY3','hY2','hY1',1]," +
                  "[1,1,1,1,1,'hG4',1,1,1,1,1]," +
                  "['X','X','X','X',1,'hG3',1,'X','X','X','X']," +
                  "['X','X','X','X',1,'hG2',1,'X','X','X','X']," +
                  "['pG1','pG2','X','X',1,'hG1',1,'X','X','pY1','pY2']," +
                  "['pG3','pG4','X','X',1,1,1,'X','X','pY3','pY4']"};
  },
  methods: {
    async createLobby(){
      this.isLoading = true;
      var now = new Date();
      let lobby = new Lobby(now, this.gameMode, this.maxPlayers, this.name, this.password, this.turnTimer,  this.status, this.whoseTurn, this.boardState);
      console.log(lobby)

      let newLobby = await this.lobbyService.asyncSave(JSON.stringify(lobby))

      let userLobby = new UserLobby("RED",1, 1, 1, 1, 1)
      console.log(userLobby)
      console.log(JSON.stringify(userLobby))

      console.log("Account Id = " + this.sessionService.currentAccount.id + "Lobby id = " + newLobby.id)
      await this.lobbyService.asyncAddUserLobby(newLobby.id, this.sessionService.currentAccount.id, JSON.stringify(userLobby))

      this.$router.push("/ongoing-matches")
    }
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

.title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #007bff;
}


.form-outline {
  margin-bottom: 1.5rem;
}

.form-check{
  margin-top: 10px;
  display: inline-block;
}

.form-outline label {
  font-weight: 600;
  color: #ffffff;
}


.form-check-label {
  margin-left: 0.5rem;
  color: #ffffff;
}

.form-control {
  border: none;
  border-bottom: 2px solid #ffffff;
  border-radius: 0;
  box-shadow: none;
  color: #ffffff;
  background: transparent;
}

.form-control:focus {
  border-color: #61dafb;
  box-shadow: none;
}

label{
  font-size: 25px;
}

.btn-primary {
  background-color: #61dafb;
  border: none;
  padding: 0.75rem 1.5rem;
  box-shadow: 0px 2px 4px rgba(0,0,0,0.1);
  cursor: pointer;
  transition: all 0.3s ease;
  color: #282c34;
}

.btn-primary:hover {
  background-color: #21a1c1;
}

.btn-primary:disabled {
  background-color: #61dafb;
  cursor: not-allowed;
}

.form-control {
  color: #ffffff;
}

.form-control::placeholder {
  color: #b3b3b3;
}
</style>
