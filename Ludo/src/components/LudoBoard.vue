<template>
  <div id="app">
    <p> ingelogd als: <b>{{ this.loggedInUser }}</b></p>
    <p> Speler aan de beurt: <b> {{ this.whichUserTurn.username }} </b> </p>
    <p> </p>

    <div class="board">
      <div class="row" v-for="(row, i) in board" :key="i">
        <div class="cell" v-for="(cell, j) in row" :key="j" :class="getClass(cell)">
          <div v-for="pawn in getPawn(i, j)" class="pawn" :class="getPawnClasses(pawn)" v-if="isPawn(i, j).length > 0"></div>
        </div>
      </div>
    </div>
    <p> {{ rolled_dice }}</p>
    <button @click="rollDice()" v-if="canIPlay">Roll Dice</button>
    <button @click="movePawn" v-if="currentPlayer === 'R' && canIPlay && rolled_dice !== 0" :disabled="buttonClicked">Move Red Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'G' && canIPlay && rolled_dice !== 0" :disabled="buttonClicked">Move Green Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'X' && canIPlay && rolled_dice !== 0" :disabled="buttonClicked">Move Blue Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'Y' && canIPlay && rolled_dice !== 0" :disabled="buttonClicked">Move Yellow Pawn</button>
  </div>
</template>

<script>
export default {
  name: "LudoBoard",
  inject: ["userLobbyService", "lobbyService", "sessionService", "registerService"],

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
      canIPlay: false,
      loggedInUser: this.sessionService.currentAccount.userName,
      whichTurn: null,
      whichUserTurn: 1,
      buttonClicked: false,
      lobbyNumber: new URL(window.location).pathname.split('/')[2]
    };
  },

  created() {
    this.generatePath();
    this.checkIfYourTurn();
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

    async movePawn() {
      this.buttonClicked = true;
      if (!this.pawns[this.currentPlayer]) return;
      let pawn = this.pawns[this.currentPlayer];
      let steps = this.rolled_dice;
      while(steps > 0) {
        let newPosition = pawn.position + 1;
        // Update the pawn's position
        pawn.position = newPosition;

        steps--;

        // Pause for Vue to update the DOM, then check for other pawns in the cell
        await this.$nextTick();
        await this.sleep(500);

        if(steps === 0) {
          let pawnInNewCell = this.getPawn(this.path[newPosition].i, this.path[newPosition].j);
          pawnInNewCell.forEach(pawnInCell => {
            if (pawnInCell.color !== this.currentPlayer) {
              // Send the opponent's pawn back to home
              this.pawns[pawnInCell.color].position = -1;
              this.pawns[pawnInCell.color].home = true;
            }
          });
        }

        // Check if the player won
        if(this.hasWon()) {
          alert(`${this.currentPlayer} has won the game`);
        }
      }

      if(this.extraTurn === false){
        await this.nextPlayer();
        await this.lobbyService.asyncIncreaseTurn(this.lobbyNumber)
      }
      else{
        this.extraTurn = false;
      }
    },


// Add this method to the Vue component
    sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    },

    hasWon() {
      return this.pawns[this.currentPlayer].position >= this.path.length;
    },

    async nextPlayer() {
      let players = ['R', 'G', 'X', 'Y'];
      let nextIndex = await this.userLobbyService.asyncGetLobbyTurn(this.lobbyNumber);

      // Map the player number to the corresponding letter
      let playerLetter = players[nextIndex - 1];
      this.currentPlayer = playerLetter;
    },

    isPawn(i, j) {
      return Object.values(this.pawns).filter(pawn => {
        const cell = this.path[pawn.position];
        return cell && cell.i === i && cell.j === j;
      });
    },

    getPawnClasses(pawn) {
      return pawn ? `pawn-${pawn.color.toLowerCase()}${pawn.moving ? ' moving' : ''}` : '';
    },

    getPawn(i, j) {
      const pawns = Object.entries(this.pawns).filter(([color, pawn]) => {
        const cell = this.path[pawn.position];
        return cell && cell.i === i && cell.j === j;
      });
      return pawns.map(([color, pawn]) => ({ color: color, ...pawn }));
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
    },

    async checkIfYourTurn(){
      this.whichTurn = await this.userLobbyService.asyncWhoseTurn(this.lobbyNumber)
      this.whichUserTurn = await this.registerService.asyncFindById(this.whichTurn)
      console.log(this.loggedInUser)
      console.log(this.whichTurn)
      if(this.whichTurn == this.sessionService.currentAccount.id){
        this.canIPlay = true;
      } else {
        this.canIPlay = false}
      }
      },

  mounted() {
    const currentTime = new Date();
    const seconds = currentTime.getSeconds();
    const milliseconds = currentTime.getMilliseconds();
    const timeUntilNextRefresh = (10 - seconds % 10) * 1000 - milliseconds;

    setTimeout(() => {
      location.reload(); // Refresh the page
    }, timeUntilNextRefresh);

    return timeUntilNextRefresh;
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
.pawn.moving {
  animation: hop 0.5s linear;
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
  animation: hop 0.5s linear infinite; /* Added animation property */
}

@keyframes hop {
  0% { top: 50%; }
  50% { top: 40%; }
  100% { top: 50%; }
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
