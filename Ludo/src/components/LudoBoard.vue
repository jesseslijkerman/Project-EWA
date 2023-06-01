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
        'R': {position: 0, home: false},
        'G': {position: 20, home: false},
        'X': {position: 10, home: false},
        'Y': {position: 30, home: false}
      },
      path: [],
      startingPoints: [
        {i: 4, j: 1}, // Red
        {i: 1, j: 9}, // Blue
        {i: 6, j: 9}, // Yellow
        {i: 9, j: 4}, // Green
      ]
    };
  },
  mounted() {
    this.generatePath();
  },
  methods: {
    generatePath() {
      // The path starts from the Red starting point and continues clockwise
      // Define the sequence of directions
      const directions = ['down', 'right', 'up', 'left'];
      let directionIndex = 0;

      // Define the starting point
      let i = 0, j = 0;

      // Look for the first '1' on the board to start the path
      outerLoop:
          for (let x = 0; x < this.board.length; x++) {
            for (let y = 0; y < this.board[x].length; y++) {
              if (this.board[x][y] === 1) {
                i = x;
                j = y;
                break outerLoop;
              }
            }
          }

      // Generate the path
      while (this.path.length < 40) {
        // Check if the current cell is '1'
        if (this.board[i][j] === 1) {
          this.path.push({i, j});
          console.log(`Added cell to path: (${i}, ${j})`);
        }

        // Move to the next cell based on the current direction
        switch (directions[directionIndex]) {
          case 'down':
            i++;
            break;
          case 'right':
            j++;
            break;
          case 'up':
            i--;
            break;
          case 'left':
            j--;
            break;
        }

        // If we reach the edge of the board, turn to the next direction (clockwise)
        if (i < 0 || i >= this.board.length || j < 0 || j >= this.board[i].length) {
          directionIndex = (directionIndex + 1) % directions.length;
          // Adjust (i, j) to be inside the board again
          i = Math.max(0, Math.min(i, this.board.length - 1));
          j = Math.max(0, Math.min(j, this.board[i].length - 1));
        }
      }
    },


    rollDice() {
      this.rolled_dice = Math.floor(Math.random() * 6) + 1;
      console.log('Rolled Dice:', this.rolled_dice);
      return this.rolled_dice;
    },
    movePawn(color, steps) {
      const pawn = this.pawns[color];
      const currentPosition = pawn.position;

      for (let step = 0; step < steps; step++) {
        const newPathIndex = currentPosition + step + 1;

        if (newPathIndex >= this.path.length) {
          // The pawn has completed a lap
          pawn.position = 0;
          console.log(`${color} pawn completed a lap, reset position to 0`);
          break;
        }

        const newCell = this.path[newPathIndex];

        // Check if there is already a pawn of the same color in the new cell
        const pawnInNewCell = this.getPawn(newCell.i, newCell.j);
        if (pawnInNewCell && pawnInNewCell.color === color) {
          // The pawn cannot move further
          console.log(`${color} pawn cannot move further due to another pawn at position (${newCell.i}, ${newCell.j})`);
          break;
        }

        // The pawn can move to the new cell
        pawn.position = newPathIndex;
        console.log(`${color} pawn moved to new position: ${newPathIndex}`);
      }
    },
    isPawn(i, j) {
      return Object.values(this.pawns).some(pawn => {
        const cell = this.path[pawn.position];
        return cell && cell.i === i && cell.j === j;
      });
    },

    getPawnClasses(i, j) {
      const pawn = this.getPawn(i, j);
      return pawn ? `pawn-${pawn.color.toLowerCase()}` : '';
    },

    getPawn(i, j) {
      const pawnEntry = Object.entries(this.pawns).find(([color, pawn]) => {
        const cell = this.path[pawn.position];
        return cell && cell.i === i && cell.j === j;
      });
      return pawnEntry ? {color: pawnEntry[0], ...pawnEntry[1]} : null;
    },

    getClass(cell) {
      if (typeof cell === 'string') {
        switch (cell) {
          case 'R': return 'base-cellR';
          case 'G': return 'base-cellG';
          case 'B': return 'block-cell';
          case 'X': return 'base-cellB';
          case 'Y': return 'base-cellY';
          default: return '';
        }
      } else if (cell === 1) {
        return 'path-cell';
      } else {
        return '';
      }
    }
  }
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
