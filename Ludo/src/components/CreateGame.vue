<template>
  <div class="container">
    <form @submit.prevent="handleSumbit">
      <!-- Name input -->
      <div class="form-outline mb-4">
        <label class="form-label">Game Mode</label>
        <input
          type="text"
          class="form-control"
          v-model="gameMode"
          placeholder="Game Mode"
        />
      </div>

      <!-- Username input -->
      <div class="form-outline mb-4">
        <label class="form-label">Max Players</label>
        <input
          type="text"
          class="form-control"
          v-model="maxPlayers"
          placeholder="Max Players"
        />
      </div>

      <!-- Email input -->
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

      <!-- ConfirmPassword input -->
      <div class="form-outline mb-4">
        <label class="form-label">Turn Timer</label>
        <input
          type="text"
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

      await this.lobbyService.asyncSave(JSON.stringify(lobby))

    }
  }
};
</script>

<style scoped>

</style>
