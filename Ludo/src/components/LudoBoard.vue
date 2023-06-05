<template>
  <div id="app">
    <div v-if="!hasWonBool">
    <p> ingelogd als: <b>{{ this.loggedInUser }}</b></p>
    <p> Speler aan de beurt: <b> {{ this.whichUserTurn.username }} </b> </p>
    <p> </p>

    <div class="board">
      <div class="row" v-for="(row, i) in board" :key="i">
        <div class="cell" v-for="(cell, j) in row" :key="j" :class="getClass(cell, i, j)" >
          <div v-for="pawn in getPawn(i, j)" class="pawn" :class="getPawnClasses(pawn)" v-if="isPawn(i, j).length > 0"></div>
        </div>
      </div>
    </div>
    <br>
    <p v-if="canIPlay">Roll the dice before your turn ends!</p>
    <p v-if="!canIPlay"> {{ this.whichUserTurn.username }}'s turn will end in:</p>
    <h1 class="countdown" :style="countdownStyle">{{this.countdown}}s</h1>
    <img v-if="rolled_dice !== 0"
         class="dice"
         @click="rollDice"
         :src="getRollPicture(rolled_dice)"
    />

    <button @click="rollDice()" :disabled="buttonClickedDice" v-if="canIPlay">Roll Dice</button>
    <button @click="movePawn" v-if="currentPlayer === 'R' && canIPlay && rolled_dice !== 0" :disabled="buttonClicked">Move Red Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'G' && canIPlay && rolled_dice !== 0" :disabled="buttonClicked">Move Green Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'B' && canIPlay && rolled_dice !== 0" :disabled="buttonClicked">Move Blue Pawn</button>
    <button @click="movePawn" v-if="currentPlayer === 'Y' && canIPlay && rolled_dice !== 0" :disabled="buttonClicked">Move Yellow Pawn</button>
  </div>
    <div v-if="hasWonBool">

    <h1> {{ this.whichUserTurn.username  }} has won!</h1>
    <button @click="returnToMatches()">Return to menu</button>

    </div>
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
        'R': {startPos: 31, position: -1, home: true},
        'G': {startPos: 21, position: -1, home: true},
        'B': {startPos: 2, position: -1, home: true},
        'Y': {startPos: 12, position: -1, home: true}
      },
      path: [],
      startingPoints: [
        {i: 4, j: 1}, // Red
        {i: 1, j: 6}, // Green
        {i: 9, j: 4}, // Blue
        {i: 6, j: 9}  // Yellow

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
      buttonClickedDice: false,
      lobbyNumber: new URL(window.location).pathname.split('/')[2],
      countdown: 30,
      countdownStyle: {},
      rowPosition: 0,
      colPosition: 0,
      newPos: { row: 0, col: 0},
      hasWonBool: false,
      matchStatus: null,
    };
  },

  async created() {
    this.generatePath();
    await this.convertDBtoBoard();
    await this.checkIfYourTurn();
    await this.defineCurrentPlayer();
    this.matchStatus = (await this.lobbyService.asyncFindById(this.lobbyNumber)).status;
    await this.checkIfFinished();
    this.countdown = (await this.lobbyService.asyncFindById(this.lobbyNumber)).turnTimer;
    console.log(this.countdown);
    console.log(this.currentPlayer);
    console.log(this.path)
    console.log(this.newPos)

    console.log(this.matchStatus)



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

    async checkIfFinished(){
      if (this.matchStatus === 'FINISHED'){
        this.hasWonBool = true;
      } else  {
        this.hasWonBool = false;
      }
    },

    returnToMatches(){
      this.$router.push('/ongoing-matches');
    },

    async defineCurrentPlayer(){
      let players = ['R', 'B', 'G', 'Y'];
      let nextIndex = await this.userLobbyService.asyncGetLobbyTurn(this.lobbyNumber);

      let playerLetter = players[nextIndex -1];
      console.log("current player color: " + playerLetter);
      this.currentPlayer = playerLetter;
    },


    async rollDice() {
      this.buttonClickedDice = true;
      this.rolled_dice = await this.lobbyService.asyncRollDice();
      this.extraTurn = this.rolled_dice === 6 ? true : false;
      this.getRollPicture(this.rolled_dice)

      if(this.extraTurn === false){
        await this.nextPlayer();
      }
    },

    getRollPicture(src) {
      if (src === undefined) {
        return;
      } else {
        return "/src/assets/dice" + src + ".png";
      }
    },


    async convertDBtoBoard() {
      const inputObject = await this.lobbyService.asyncFindById(this.lobbyNumber);
      const inputString = inputObject.boardState;
      console.log(inputObject);
      const rows = inputString.slice(1, -1).split("],[");
      this.board = rows.map((row) => {
        const rowValues = row
            .replace(/'/g, "")
            .split(",");
        return rowValues.map((value) => {
          if (!isNaN(value)) {
            return parseInt(value);
          } else {
            return value.trim();
          }
        });
      });
    },


    async convertBoardToDB() {
      const rows = this.board.map((row) => {
        const rowValues = row.map((value) => {
          if (typeof value === 'number') {
            return value.toString();
          } else {
            return "'" + value.toString() + "'";
          }
        });
        return "[" + rowValues.join(",") + "]";
      });
      const inputString = rows.join(",");
      console.log(inputString);

      const encodedInputString = encodeURIComponent(inputString); // URL-encode the inputString

      const inputObject = { boardState: encodedInputString }; // Assign the URL-encoded inputString to the boardState property
      console.log(typeof inputObject.boardState);
      await this.lobbyService.asyncUpdateById(this.lobbyNumber, inputObject.boardState);
    },


    movePawnOut() {
      if(this.pawns[this.currentPlayer].home === true && this.rolled_dice === 6){
        this.pawns[this.currentPlayer].home = false;
        this.pawns[this.currentPlayer].position = this.pawns[this.currentPlayer].startPos;
      }
    },

    getPlayerLocation(position) {
      for (let i = 0; i < this.path.length; i++) {
        const cell = this.path[i];
        if (cell.i === position.i && cell.j === position.j) {
          return { i: cell.i, j: cell.j }; // Include the 'j' value
        }
      }
      return null;
    },

    async movePawn() {
      await this.findPR1();
      if(this.newPos.col === 0 && this.newPos.row === 0){
        this.pawns[this.currentPlayer].position = this.pawns[this.currentPlayer].startPos;
      } else{
        // find pawn in path array
        const index = this.path.findIndex(coord => coord.i === this.newPos.i && coord.j === this.newPos.j);
        this.pawns[this.currentPlayer].position = index;
      }
      this.buttonClicked = true;
      if (!this.pawns[this.currentPlayer]) return;
      let pawn = this.pawns[this.currentPlayer];
      let steps = this.rolled_dice - 1;

      while (steps >= 0) {
        let newPosition = pawn.position + 1;

        if (this.rolled_dice === 6) {
          this.extraTurn = true;
        }

        if(newPosition >= this.path.length ){
          newPosition = -1 + 1;
        }

        pawn.position = newPosition;
        console.log("current pos: " + pawn.position);
        steps--;



        await this.$nextTick();
        await this.sleep(500);

        if (steps === 0) {
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

        if (this.pawns[this.currentPlayer].position === this.pawns[this.currentPlayer].startPos) {
          this.pawns[this.currentPlayer].home = true;
          await this.lobbyService.asyncFinishMatch(this.lobbyNumber);
          alert(`${this.currentPlayer} has won the game`);
          this.hasWonBool = true;
        }

      }

      if(this.newPos !== { row: 0, col: 0} ){
        try {
          await this.clearPR1();
        } catch (error) {
          console.error("Error in fillInBoard:", error);
        }
      }

      try {
        await this.fillInBoard();
        console.log("Fill in board completed successfully");
      } catch (error) {
        console.error("Error in fillInBoard:", error);

      }

      if (this.extraTurn === false) {
        console.log("Turn increased");
        await this.lobbyService.asyncIncreaseTurn(this.lobbyNumber);
        await this.convertBoardToDB();
      } else {
        console.log("Extra turn");
        this.extraTurn = false;
        await this.convertBoardToDB();
      }
    }
    ,

    async findPR1() {
      for (let i = 0; i < this.board.length; i++) {
        for (let j = 0; j < this.board[i].length; j++) {
          if (this.board[i][j] === 'p' + this.currentPlayer + '1') {
            this.rowPosition = i;
            this.colPosition = j;
            this.newPos = { i, j };
          }
        }
      }
      this.rowPosition = null;
      this.colPosition = null;
    },

    async clearPR1(){
      const index = this.path.find(coord => coord.i === this.newPos.i && coord.j === this.newPos.j);
      this.board[index.i][index.j] = 1;
    },

    async fillInBoard() {

      let newPosition = this.pawns[this.currentPlayer].position;
      const currentPlayerLocation = this.getPlayerLocation(this.path[newPosition]);
      console.log("currentPlayerLocation: ", currentPlayerLocation);
      if (currentPlayerLocation) {
        const { i, j } = currentPlayerLocation;
        this.newPos = { i, j };
        console.log("Player is at board location: ", i, j);
        this.board[i][j] = 'p' + this.currentPlayer + '1';
        await this.convertBoardToDB();
      }
    },


// Add this method to the Vue component
    sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    },

    hasWon() {
      return this.pawns[this.currentPlayer].position >= this.path.length;
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

    getClass(cell, row, col) {
      if (typeof cell === 'string') {
        switch (cell) {
          case 'R': return 'base-cellR';
          case 'pR1': return 'pR1';
          case 'G': return 'base-cellG';
          case 'pG1': return 'pG1';
          case 'B': return 'base-cellB';
          case 'pB1': return 'pB1';
          case 'Y': return 'base-cellY';
          case 'pY1': return 'pY1';
          case 'X': return 'block-cell';
          default: return '';
        }
      } else if (cell === 1 && row === 4 && col === 1) {
        return 'base-cellR';
      } else if (cell === 1 && row === 1 && col === 6) {
        return 'base-cellB';
      } else if (cell === 1 && row === 9 && col === 4) {
        return 'base-cellG';
      } else if (cell === 1 && row === 6 && col === 9) {
        return 'base-cellY';
      } else if (cell === 1) {
        return 'path-cell';
      }
      else {
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

   async mounted() {

    const countdownInterval = setInterval(async () => {
      this.countdown--; // Decrease the countdown value by 1

      if (this.countdown <= 0) {
        clearInterval(countdownInterval);
        try {
          // await this.lobbyService.asyncIncreaseTurn(this.lobbyNumber);
          // eslint-disable-next-line no-empty
        } catch (error) {
        }
        location.reload(); // Refresh the page when countdown reaches zero
      } else if (this.countdown <= 10) {
        // Flash the countdown red when it's under 10 seconds
        this.countdownStyle = {color: 'red'};
        setTimeout(() => {
          this.countdownStyle = {}; // Reset the style after 200 milliseconds
        }, 200);
      }
    }, 1000); // Repeat every second (1000 milliseconds)
  }
};
</script>


<style scoped>
.board {
  display: flex;
  flex-direction: column;
  margin: auto;
  width: 647px;
  height: 650px;
  background: cadetblue;
}

.row {
  display: flex;

}

h2 {
  text-align: center;
}

h1{
  margin-top: 250px;
  text-align: center;
  color: gold;
}

.cell {
  width: 60px;
  height: 60px;
  border: 1px solid black;
  position: relative;
  background-color: black;
}



.pG1{
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: green;
  transform: scale(0.7);
  /*animation: hop 0.5s linear infinite; !* Added animation property *!*/
}

.pB1{
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: blue;
  transform: scale(0.7);
  /*animation: hop 0.5s linear infinite; !* Added animation property *!*/
}

.pY1{
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: yellow;
  transform: scale(0.7);
  /*animation: hop 0.5s linear infinite; !* Added animation property *!*/
}

.pR1{
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: red;
  transform: scale(0.7);
  /*animation: hop 0.5s linear infinite; !* Added animation property *!*/
}

.block-cell {
  background-color: gray;
}

.base-cellR {
  background-color: red;
}

.base-cellX {
  background-color: black;
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

.countdown{
  text-align: center;
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
