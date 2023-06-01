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
    <button @click="movePawn('X', rollDice())">Roll Dice and Move Blue Pawn</button>
    <button @click="movePawn('Y', rollDice())">Roll Dice and Move Yellow Pawn</button>
  </div>
</template>

<script>
export default {
  name: "LudoBoard",
  data() {
    return {
      rolled_dice: 0,
      board: [
        ['R', 'R', 'X', 'X', 1, 1, 1, 'X', 'X', 'B', 'B'],
        ['R', 'R', 'X', 'X', 1, 0, 1, 'X', 'X', 'B', 'B'],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['X', 'X', 'X', 'X', 1, 0, 0, 'X', 'X', 'X', 'X'],
        [1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1],
        [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
        [1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['G', 'G', 'X', 'X', 1, 0, 1, 'X', 'X', 'Y', 'Y'],
        ['G', 'G', 'X', 'X', 1, 1, 1, 'X', 'X', 'Y', 'Y'],
      ],
      pawns: {
        'R': { position: 26, home: false },
        'G': { position: 4, home: false },
        'X': { position: 0, home: false },
        'Y': { position: 0, home: false }
      },
      path: [],
      startingPoints: [
        { i: 5, j: 1 }, // Red
        { i: 2, j: 9 }, // Green
        { i: 9, j: 7 }, // Blue
        { i: 7, j: 0 }  // Yellow
      ]
    };
  },
  created() {
    this.generatePath();
  },
  methods: {
    getClass(cell) {
      return {
        cell: true,
        'base-cellR': cell === 'R',
        'base-cellG': cell === 'G',
        'base-cellY': cell === 'Y',
        'base-cellB': cell === 'B',
        'path-cell': cell === 1,
        'block-cell': cell === 'X'
      };
    },
    getPawnClasses(i, j) {
      const pawn = Object.keys(this.pawns).find(color => this.pawns[color].position === this.path.findIndex(cell => cell.i === i && cell.j === j));
      if (pawn) {
        return `pawn-${pawn.toLowerCase()}`;
      }
      return '';
    },
    isPawn(i, j) {
      return Object.values(this.pawns).some(pawn => this.path[pawn.position].i === i && this.path[pawn.position].j === j);
    },
    generatePath() {
      const path = [];

      // Path from red starting point to green starting point
      for (let i = 0; i < 6; i++) {
        path.push({ i: 5, j: 2 + i });
      }
      for (let i = 0; i < 6; i++) {
        path.push({ i: 4 - i, j: 7 });
      }

      // Path from green starting point to blue starting point
      for (let i = 0; i < 6; i++) {
        path.push({ i: 3 + i, j: 8 });
      }
      for (let i = 0; i < 6; i++) {
        path.push({ i: 9, j: 7 - i });
      }

      // Path from blue starting point to yellow starting point
      for (let i = 0; i < 6; i++) {
        path.push({ i: 10, j: 6 - i });
      }
      for (let i = 0; i < 6; i++) {
        path.push({ i: 11 + i, j: 1 });
      }

      // Path from yellow starting point back to red starting point
      for (let i = 0; i < 6; i++) {
        path.push({ i: 16 - i, j: 2 });
      }
      for (let i = 0; i < 6; i++) {
        path.push({ i: 5, j: 3 + i });
      }

      this.path = path;
    },
    isInHomePath(color) {
      const pawn = this.pawns[color];
      return this.path.slice(0, pawn.position).some(cell => this.startingPoints.some(startingPoint => startingPoint.i === cell.i && startingPoint.j === cell.j));
    },
    rollDice() {
      return Math.floor(Math.random() * 6) + 1;
    },
    movePawn(color, steps) {
      const pawn = this.pawns[color];
      const currentPosition = pawn.position;
      const newPathIndex = (currentPosition + steps) % this.path.length;
      const newCell = this.path[newPathIndex];

      // Check if the new cell is a block cell ('X')
      if (this.board[newCell.i][newCell.j] === 'X') {
        return; // Do not move the pawn if the cell is blocked
      }

      // Check if there is already a pawn in the new cell
      if (this.isPawn(newCell.i, newCell.j)) {
        return; // Do not move the pawn if the cell is occupied by another pawn
      }

      // Move the pawn
      pawn.position = newPathIndex;

      // Update the board to reflect the new pawn position
      const previousPathIndex = (currentPosition === 0 ? this.path.length : currentPosition) - 1;
      const previousCell = this.path[previousPathIndex];
      this.board[previousCell.i][previousCell.j] = 1;
      this.board[newCell.i][newCell.j] = color;
    }}
};
</script>


<style scoped>
.board {
  display: flex;
  flex-direction: column;
  margin: auto;
  width: 700px;
  height: 700px;
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

.block-cell {
  background-color: gray;
}

.base-cellR {
  background-color: red;
}

.base-cellG {
  background-color: green;
}

.base-cellY {
  background-color: yellow;
}

.base-cellB {
  background-color: blue;
}

.path-cell {
  background-color: cadetblue;
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

.cyan-cell {
  background-color: cyan;
}

.pawn-r {
  background-color: red;
}

.pawn-g {
  background-color: green;
}

.pawn-b {
  background-color: blue;
}

.pawn-y {
  background-color: yellow;
}
</style>
