<template>
  <div class="container">
    <h1 class="title">Ludo Lobbies</h1>
    <div class="matches">
      <div v-for="(match, index) in matches" :key="index" class="match">
        <div class="match-info">
          <h2 class="match-title">{{ match.name }}</h2>
          <p class="match-description">{{ match.description }}</p>
          <p class="match-turn">Turn: {{ match.whoseTurn }}</p>
          <p class="match-players">Players: {{ match.players }}</p>
          <p class="match-timeStarted">
            Time started: {{ formatDateTime(match.created) }}
          </p>
        </div>
        <router-link :to="'/match/' + match.id" class="btn btn-primary">Play</router-link>
      </div>
    </div>
    <router-link :to="'/createGame'" class="btn btn-primary">Create game</router-link>
  </div>
</template>

<script>
export default {
  name: "OngoingMatches",
  inject: ["lobbyService"],
  async created(){
    this.matches = await this.lobbyService.asyncFindByUserId("1")
    console.log(this.matches)
    console.log(this.lobbyService.asyncFindById(1))
  },
  data() {
    return {
      matches: [],
    };
  },
  methods: {
    formatDateTime(dateTimeStr) {
      const dateTime = new Date(dateTimeStr);
      return dateTime.toLocaleString();
    },
  },
  async onReload() {
    this.matches = this.lobbyService.asyncFindByUserId(5)
  }
};
</script>

<style scoped>
.container {
  max-width: 1200px;
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
  width: 33%;
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
</style>
