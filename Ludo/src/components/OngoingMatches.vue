<template>
  <div class="container">
    <h1 class="title">Ludo Lobbies</h1>
    <div class="matches">
      <div v-for="(match, index) in matches" :key="index" class="match">
        <div class="match-info">
          <h2 class="match-title">{{ match.title }}</h2>
          <p class="match-description">{{ match.description }}</p>
          <p class="match-turn">Turn: {{ match.whoseTurn }}</p>
          <p class="match-players">Players: {{ match.players }}</p>
          <p class="match-timeStarted">
            Time started: {{ formatDateTime(match.timeStarted) }}
          </p>
        </div>
        <router-link :to="'/match/' + match.id" class="btn btn-primary">Play</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "OngoingMatches",
  data() {
    return {
      matches: [
        {
          id: 1,
          title: "Tuna town",
          players: "3/4",
          description: "Jesse 🇨🇦 is ahead with 1 pawn",
          whoseTurn: "Player 1",
          timeStarted: "2023-03-16T14:45:00.000Z",
        },
        {
          id: 2,
          title: "Koala kafe",
          players: "2/4",
          description: "Tristan 🇳🇱 is ahead with 3 pawns",
          whoseTurn: "Player 2",
          timeStarted: "2023-03-18T13:36:00.000Z",
        },
        {
          id: 3,
          title: "Idiot lobby",
          players: "4/4",
          description: "Rob 🇵🇱 is ahead with 2 pawns",
          whoseTurn: "Player 1",
          timeStarted: "2023-03-20T15:30:00.000Z",
        },
      ],
    };
  },
  methods: {
    formatDateTime(dateTimeStr) {
      const dateTime = new Date(dateTimeStr);
      return dateTime.toLocaleString();
    },
  },
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
