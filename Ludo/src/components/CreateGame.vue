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
  inject: ["lobbyService"],
  data() {
    return {
      gameMode: null,
      maxPlayers: null,
      name: null,
      password: null,
      turnTimer: null,
    };
  },
  methods: {
    async createLobby(){
      var now = new Date();
      let lobby = new Lobby(now, this.gameMode, this.maxPlayers, this.name, this.password, this.turnTimer);
      console.log(lobby)

      let newLobby = await this.lobbyService.asyncSave(JSON.stringify(lobby))

      let userLobby = new UserLobby(1,1,1,1)
      await this.lobbyService.asyncAddUserLobby(newLobby.id, 1, JSON.stringify(userLobby))

      this.$router.push("/ongoing-matches")
    }
  }
};
</script>

<style scoped>

</style>
