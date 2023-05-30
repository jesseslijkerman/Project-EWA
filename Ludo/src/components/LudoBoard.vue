<template>
  <NavBar></NavBar>
  <div id="app">
    <div class="board">
      <div class="row" v-for="(row, i) in board" :key="i">
        <div class="cell" v-for="(cell, j) in row" :key="j" :class="getClass(cell)">
          <div class="pawn" :class="`color-${cell}`" v-if="isPawn(cell)"></div>
        </div>
      </div>
    </div>
    <button @click="movePawn(1, 6)">Move Red Pawn</button>
    <button @click="movePawn(2, 6)">Move Green Pawn</button>
    <button @click="movePawn(3, 6)">Move Blue Pawn</button>
    <button @click="movePawn(4, 6)">Move Yellow Pawn</button>
  </div>
</template>

<script>
import DiceRoll from "@/components/DiceRoll.vue";
import NavBar from "@/components/NavBar.vue";
export default {
  name: "LudoBoard",
  components: {NavBar, DiceRoll},
  inject: ["userLobbyService"],

  data() {
    return {
      board: [
        [0, 0, 'R', 1, 1, 1, 1, 'B', 0, 0],
        [0, 0, 'G', 1, 0, 0, 1, 'B', 0, 0],
        ['B', 'B', 'B', 1, 0, 0, 1, 'B', 'B', 'B'],
        [2, 2, 2, 2, 0, 0, 3, 3, 3, 3],
        [2, 0, 'B', 0, 0, 0, 0, 3, 0, 3],
        [2, 0, 'B', 0, 0, 0, 0, 3, 0, 3],
        [2, 2, 2, 2, 0, 0, 3, 3, 3, 3],
        ['B', 'B', 'B', 4, 0, 0, 4, 'B', 'B', 'B'],
        [0, 0, 'Y', 4, 0, 0, 4, 'B', 0, 0],
        [0, 0, 'B', 4, 4, 4, 4, 'B', 0, 0]
      ],
      cellSize: 60,
    };
  },


  methods: {
    getClass(cell) {
      return {
        cell: true,
        'base-cell': cell === 'B',
        'path-cell': [1, 2, 3, 4].includes(cell),
      };
    },

    isPawn(cell) {
      return ['R', 'G', 'B', 'Y'].includes(cell);
    },

    movePawn(pawnColor, steps) {
      // Define pawn color mapping
      const pawnColors = ['R', 'G', 'B', 'Y'];

      // Find the current position of the pawn
      let currentPosition = null;
      for (let i = 0; i < this.board.length; i++) {
        for (let j = 0; j < this.board[i].length; j++) {
          if (this.board[i][j] === pawnColors[pawnColor - 1]) {
            currentPosition = {i, j};
            break;
          }
        }
        if (currentPosition) break;
      }

      if (!currentPosition) return;

      // Calculate the new position of the pawn
      let {i, j} = currentPosition;
      while (steps--) {
        if (j < this.board[0].length - 1) {
          j++;
        } else if (i < this.board.length - 1) {
          j = 0;
          i++;
        } else {
          break; // Reached end of the board, no more moves
        }
      }

      // Move the pawn to the new position
      let temp = this.board[i][j];
      this.board[i][j] = this.board[currentPosition.i][currentPosition.j]; // Move pawn to the new cell
      this.board[currentPosition.i][currentPosition.j] = temp; // Restore the original cell value
    },
  },}
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

.base-cell {
  background-color: #c0c0c0;
}

.path-cell {
  background-color: #f0f0f0;
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

.color-R {
  background-color: red;
}

.color-G {
  background-color: green;
}

.color-B {
  background-color: blue;
}

.color-Y {
  background-color: yellow;
}

button {
  margin: 10px;
  padding: 10px 20px;
  font-size: 20px;
}
</style>
