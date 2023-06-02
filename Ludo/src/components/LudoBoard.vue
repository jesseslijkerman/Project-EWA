<template>
  <div id="app">
    <div class="board">
      <div class="row" v-for="(row, i) in board" :key="i">
        <div class="cell" v-for="(cell, j) in row" :key="j" :class="getClass(cell)">
          <div class="pawn" :class="getPawnClasses(i, j)" v-if="isPawn(i, j)"></div>
        </div>
      </div>
    </div>
    <button @click="movePawn('R', rollDice())">Roll Dice and Move Red Pawn</button>
    <button @click="movePawn('G', rollDice())">Roll Dice and Move Green Pawn</button>
    <button @click="movePawn('B', rollDice())">Roll Dice and Move Blue Pawn</button>
    <button @click="movePawn('Y', rollDice())">Roll Dice and Move Yellow Pawn</button>
  </div>
</template>

<script>

export default {
  name: "LudoBoard",
  inject: ["userLobbyService", "lobbyService"],

  data() {
    return {
      rolled_dice: 0,
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
      pawns: {
        'R': {position: 6, home: false},
        'G': {position: 0, home: false},
        'B': {position: 0, home: false},
        'Y': {position: 0, home: false}
      },
      path: [],
      startingPoints: [
        {i: 0, j: 2}, // Red
        {i: 2, j: 9}, // Green
        {i: 9, j: 7}, // Blue
        {i: 7, j: 0}  // Yellow
      ],
      homePaths: {
        'R': {i: 0, j: 3, direction: 'right', length: 5},
        'G': {i: 3, j: 9, direction: 'down', length: 5},
        'B': {i: 9, j: 6, direction: 'left', length: 5},
        'Y': {i: 6, j: 0, direction: 'up', length: 5}
      },
      cellSize: 60,
    };
  },

  created() {
    this.createPath();
  },

  methods: {
    createPath() {
      const directions = ['right', 'down', 'left', 'up'];
      const start = {i: 0, j: 3};
      const n = this.board.length;
      const m = this.board[0].length;

      let i = start.i;
      let j = start.j;
      let dir = 0;

      while (!(i === start.i && j === start.j && this.path.length > 0)) {
        this.path.push({i, j});
        this.board[i][j] = 1;

        if (directions[dir] === 'right' && j < m - 1 && this.board[i][j+1] !== 'B') j++;
        else if (directions[dir] === 'down' && i < n - 1 && this.board[i+1][j] !== 'B') i++;
        else if (directions[dir] === 'left' && j > 0 && this.board[i][j-1] !== 'B') j--;
        else if (directions[dir] === 'up' && i > 0 && this.board[i-1][j] !== 'B') i--;
        else dir = (dir + 1) % 4;
      }

      ['R', 'G', 'B', 'Y'].forEach((color, index) => {
        let {i, j, direction, length} = this.homePaths[color];
        for (let k = 0; k < length; k++) {
          this.path.push({i, j});
          this.board[i][j] = 1;
          if (direction === 'right' && j < m - 1) j++;
          else if (direction === 'down' && i < n - 1) i++;
          else if (direction === 'left' && j > 0) j--;
          else if (direction === 'up' && i > 0) i--;
        }
      });
    },

    getClass(cell) {
      return {
        cell: true,
        'base-cell': cell === 'B',
        'path-cell': cell === 1,
      };
    },

    getPawnClasses(i, j) {
      const pawnColor = Object.keys(this.pawns).find(color => {
        const pawn = this.pawns[color];
        const position = this.path[pawn.position];
        return position && position.i === i && position.j === j;
      });
      return pawnColor ? `color-${pawnColor}` : '';
    },

    rollDice() {
      return Math.floor(Math.random() * 6) + 1;
    },

    isPawn(i, j) {
      return Object.values(this.pawns).some(pawn => {
        const position = this.path[pawn.position];
        return position && position.i === i && position.j === j;
      });
    },

    movePawn(pawnColor, steps) {
      const pawn = this.pawns[pawnColor];
      if (!pawn.home) {
        const start = this.startingPoints[Object.keys(this.pawns).indexOf(pawnColor)];
        const index = this.path.findIndex(p => p.i === start.i && p.j === start.j);
        pawn.position = index + steps - 1;
        pawn.home = true;
      } else {
        pawn.position += steps;
      }

      if (pawn.position >= this.path.length) {
        pawn.position = this.path.length - 1;
      }
    },
  },
};
</script>

<style scoped>
.board {
  display: flex;
  flex-direction: column;
  margin: auto;
  width: 600px;
  height: 600px;
}

.row {
  display: flex;
  justify-content: space-between;
}

.cell {
  width: 60px;
  height: 60px;
  border: 1px solid black;
  position: relative;
}

.base-cell {
  background-color: lightblue;
}

.path-cell {
  background-color: white;
}

.pawn {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: grey;
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
</style>
