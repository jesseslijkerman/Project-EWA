<template>
  <div id="app">
    <div class="board">
      <div class="row" v-for="(row, i) in board" :key="i">
        <div class="cell" v-for="(cell, j) in row" :key="j" :class="getClass(cell)">
          <div class="pawn" :class="getPawnClasses(i, j)" v-if="isPawn(i, j)"></div>
        </div>
      </div>
    </div>
    <button @click="rollDice()">Roll Dice</button>
    <button @click="movePawn" v-if="currentPlayer === 'R' && rolled_dice !== 0">Move Red Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'G' && rolled_dice !== 0">Move Green Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'X' && rolled_dice !== 0">Move Blue Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'Y' && rolled_dice !== 0">Move Yellow Pawn</button>
  </div>
</template>

<script>
export default {
  name: "LudoBoard",
  data() {
    return {
      rolled_dice: 0,
      extraTurn: false,
      currentPlayer: 'R',
      board: [
        ['R', 'R', 'X', 'X', 1, 1, 1, 'X', 'X', 'B', 'B'],
        ['R', 'R', 'X', 'X', 1, 0, 1, 'X', 'X', 'B', 'B'],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        [1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1],
        [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
        [1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['G', 'G', 'X', 'X', 1, 0, 1, 'X', 'X', 'Y', 'Y'],
        ['G', 'G', 'X', 'X', 1, 1, 1, 'X', 'X', 'Y', 'Y'],
      ],
      pawns: {
        'R': {startPos: 0, position: -1, home: true},
        'G': {startPos: 20, position: -1, home: true},
        'X': {startPos: 10, position: -1, home: true},
        'Y': {startPos: 30, position: -1, home: true}
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
      const directions = ['right', 'down', 'right', 'down', 'left', 'down', 'left', 'up', 'left', 'up', 'right', 'up'];
      const steps = [2, 4, 4, 2, 4, 4, 2, 4, 4, 2, 4, 4];

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

      // Follow the hard coded path
      for (let dirIndex = 0; dirIndex < directions.length; dirIndex++) {
        for (let step = 0; step < steps[dirIndex]; step++) {
          switch (directions[dirIndex]) {
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
          if (this.board[i][j] === 1) {
            this.path.push({i, j});
            console.log(`Added cell to path: (${i}, ${j})`);
          }
        }
      }
    },


    rollDice() {
      this.rolled_dice = Math.floor(Math.random() * 6) + 1;
      this.extraTurn = this.rolled_dice === 6 ? true : false;

      if(this.rolled_dice === 6 && this.pawns[this.currentPlayer].home === true){
        this.movePawnOut();
      }
      else if(this.extraTurn === false){
        this.nextPlayer();
      }
    },

    movePawnOut() {
      if(this.pawns[this.currentPlayer].home === true && this.rolled_dice === 6){
        this.pawns[this.currentPlayer].home = false;
        this.pawns[this.currentPlayer].position = this.pawns[this.currentPlayer].startPos;
      }
    },

    movePawn() {
      let pawn = this.pawns[this.currentPlayer];
      let newPosition = pawn.position + this.rolled_dice;
      let pawnInNewCell = this.getPawn(this.path[newPosition].i, this.path[newPosition].j);

      if (pawnInNewCell && pawnInNewCell.color !== this.currentPlayer) {
        // Send the opponent's pawn back to home
        this.pawns[pawnInNewCell.color].position = -1;
        this.pawns[pawnInNewCell.color].home = true;
      }

      // Update the pawn's position
      pawn.position = newPosition;

      // Check if the player won
      if(this.hasWon()) {
        alert(`${this.currentPlayer} has won the game`);
      }

      if(this.extraTurn === false){
        this.nextPlayer();
      }
      else{
        this.extraTurn = false;
      }
    },

    hasWon() {
      return this.pawns[this.currentPlayer].position >= this.path.length;
    },

    nextPlayer() {
      let players = ['R', 'G', 'X', 'Y'];
      let currentIndex = players.indexOf(this.currentPlayer);
      let nextIndex = currentIndex + 1 === players.length ? 0 : currentIndex + 1;
      this.currentPlayer = players[nextIndex];
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
