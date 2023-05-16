<template>
  <div id="app">
    <div class="board">
      <div class="row" v-for="(row, i) in board" :key="i">
        <div class="cell" v-for="(cell, j) in row" :key="j" :class="`cell-color-${(i+j)%2}`">
          <div
              class="pawn"
              :class="`color-${cell}`"
              v-if="cell"
          ></div>
        </div>
      </div>
    </div>
    <button @click="movePawn(1, 1)">Move Pawn</button>
  </div>
</template>

<script>


import DiceRoll from "@/components/DiceRoll.vue";
export default {
  name: "LudoBoard",
  components: {DiceRoll},
  inject: ["userLobbyService"],

  data(){
    return{
      board: [
        [0, 0, 0, 0, 0],
        [0, 1, 0, 2, 0],
        [0, 0, 0, 0, 0],
        [0, 3, 0, 4, 0],
        [0, 0, 0, 0, 0],
      ],

      pawns: null,
      cellSize: 60
    };
  },

  created() {
  },

  methods : {

    movePawn(pawnColor, steps) {
      // Find the current position of the pawn
      let currentPosition = null;
      for (let i = 0; i < this.board.length; i++) {
        for (let j = 0; j < this.board[i].length; j++) {
          if (this.board[i][j] === pawnColor) {
            currentPosition = { i, j };
            break;
          }
        }
        if (currentPosition) break;
      }

      if (!currentPosition) return;

      // Calculate the new position of the pawn
      const newRow = currentPosition.i;
      const newColumn = currentPosition.j + steps;

      // Move the pawn to the new position
      this.board[currentPosition.i][currentPosition.j] = 0;
      if (newRow >= 0 && newRow < this.board.length && newColumn >= 0 && newColumn < this.board[0].length) {
        this.board[newRow][newColumn] = pawnColor;
      }
    },
  },
};
</script>

<style scoped>
.board {
  display: flex;
  flex-direction: column;
  margin: 50px;
}

.row {
  display: flex;
  flex-direction: row;
}

.cell {
  position: relative;
  border: 1px solid black;
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.cell-color-0 {
  background-color: #F0F0F0;
}

.cell-color-1 {
  background-color: #D0D0D0;
}

.pawn {
  position: absolute;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  transition: transform 0.5s ease-in-out;
  animation: slideAndHop 0.5s linear;
}

@keyframes slideAndHop {
  50% {
    transform: translateY(-10px) scale(1.1);
  }
}



.color-1 {
  background-color: red;
}

.color-2 {
  background-color: green;
}

.color-3 {
  background-color: blue;

}

.color-4 {
  background-color: yellow;
}

button {
  margin: 50px;
  padding: 10px 20px;
  font-size: 20px;
}
</style>
