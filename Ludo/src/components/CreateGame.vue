<template>
  <div class="container">
    <form @submit.prevent="handleSumbit">
      <!-- Game mode input -->
      <div class="form-outline mb-4">
        <label class="form-label">Game mode</label>
        <div class="form-check">
          <input
              type="radio"
              class="form-check-label"
              value=1
              v-model="gameMode"
          />
          <label class="form-check-label">Traditional</label>
        </div>

        <div class="form-check">
          <input
              type="radio"
              class="form-check-label"
              value=2
              v-model="gameMode"
          />
          <label class="form-check-label">Wacky</label>
        </div>
      </div>

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

      <!-- Password input -->
      <div class="form-outline mb-4">
        <label class="form-label">Password</label>
        <input
          type="text"
          class="form-control"
          v-model="password"
          placeholder="Password (optional)"
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
        >
          Create game
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
      turnTimer: this.turnTimer,
      whoseTurn: 1,
      boardState: "['R','R','X','X',1,1,1,'X','X','B','B'],['R','R','X','X',1,0,'B','X','X','B','B'],['X','X','X','X',1,0,1,'X','X','X','X'],['X','X','X','X',1,0,1,'X','X','X','X'],[1,'R',1,1,1,0,1,'1',1,1,1],[1,0,0,0,0,0,0,0,0,0,1],[1,1,1,1,1,0,1,1,1,'Y',1],['X','X','X','X',1,0,1,'X','X','X','X'],['X','X','X','X',1,0,1,'X','X','X','X'],['G','G','X','X','G',0,1,'X','X','Y','Y'],['G','G','X','X',1,1,1,'X','X','Y','Y']"
    };
  },
  methods: {
    async createLobby(){
      var now = new Date();
      let lobby = new Lobby(now, this.gameMode, this.maxPlayers, this.name, this.password, this.turnTimer,  this.status, this.whoseTurn, this.boardState);
      console.log(lobby)

      let newLobby = await this.lobbyService.asyncSave(JSON.stringify(lobby))

      let userLobby = new UserLobby("RED",1)

      console.log("Account Id = " + this.sessionService.currentAccount.id + "Lobby id = " + newLobby.id)
      await this.lobbyService.asyncAddUserLobby(newLobby.id, this.sessionService.currentAccount.id, JSON.stringify(userLobby))

      this.$router.push("/ongoing-matches")
    }
  }
};
</script>

<style scoped>

</style>
