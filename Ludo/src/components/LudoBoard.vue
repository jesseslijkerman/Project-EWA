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
      <div v-if="!hasWonBool">

    <p v-if="canIPlay">Roll the dice before your turn ends!</p>
    <p v-if="!canIPlay"> {{ this.whichUserTurn.username }}'s turn will end in:</p>
    <h1 class="countdown" :style="countdownStyle">{{this.countdown}}s</h1>
    <img v-if="rolled_dice !== 0"
         class="dice"
         :src="getRollPicture(rolled_dice)"
    />

    <button @click="rollDice()" :disabled="buttonClickedDice" v-if="canIPlay">Roll Dice</button>

      <div v-if="rolled_dice !== 0">
        <div v-if="rolled_dice === 6">

          <div>
            <p>Put a new pawn on board:</p>
            <button @click="addToBoard(availablePawn); this.chosenPawn = availablePawn"
                    v-for="availablePawn in availablePawns"
                    :key="availablePawn" :disabled="buttonClicked"> Pawn {{ this.pawns[this.currentPlayer].indexOf(availablePawn) + 1}} </button>
          </div>

          <p>or...</p>

          <div v-if="hasPawnsOnBoard">

              <div>
                <p>Move an existing pawn:</p>
                <button @click="movePawn(existingPawn); this.chosenPawn = existingPawn"
                        v-for="existingPawn in existingPawns"
                        :key="existingPawn" :disabled="buttonClicked"> Pawn {{ this.pawns[this.currentPlayer].indexOf(existingPawn) + 1 }} </button>
              </div>



              <div v-if="rolled_dice > 0 && rolled_dice < 6">
                <p>Which pawn would you like to move?</p>
                <button @click="movePawn(existingPawn); this.chosenPawn = existingPawn"
                        v-for="existingPawn in existingPawns"
                        :key="existingPawn" :disabled="buttonClicked"> Pawn {{ this.pawns[this.currentPlayer].indexOf(existingPawn) + 1}} </button>
              </div>
        </div>
    </div>

        <div v-if="hasPawnsOnBoard">
          <div v-if="rolled_dice > 0 && rolled_dice < 6">
            <p>Which pawn would you like to move?</p>
            <button @click="movePawn(existingPawn); this.chosenPawn = existingPawn"
                    v-for="existingPawn in existingPawns"
                    :key="existingPawn" :disabled="buttonClicked"> Pawn {{ this.pawns[this.currentPlayer].indexOf(existingPawn) + 1}} </button>
          </div>
        </div>
      </div>

  </div>
    </div>

    <div v-if="hasWonBool">
      <h1 class="winner"> {{ this.whichUserTurn.username  }} has won!</h1>
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
      currentPlayer: null,
      board: [
        ['R', 'R', 'X', 'X', 1, 1, 1, 'X', 'X', 'B', 'B'],
        ['R', 'R', 'X', 'X', 1, 0, 1, 'X', 'X', 'B', 'B'],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
        [1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
        [1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['X', 'X', 'X', 'X', 1, 0, 1, 'X', 'X', 'X', 'X'],
        ['G', 'G', 'X', 'X', 1, 0, 1, 'X', 'X', 'Y', 'Y'],
        ['G', 'G', 'X', 'X', 1, 1, 1, 'X', 'X', 'Y', 'Y'],
      ],
      pawns: {
        'R': [{startPos: 31, position: -1, home: 1},
          {startPos: 31, position: -1, home: 1},
          {startPos: 31, position: -1, home: 1},
          {startPos: 31, position: -1, home: 1}],

        'G': [{startPos: 21, position: -1, home: 1},
          {startPos: 21, position: -1, home: 1},
          {startPos: 21, position: -1, home: 1},
          {startPos: 21, position: -1, home: 1}],

        'B': [{startPos: 2, position: -1, home: 1},
          {startPos: 2, position: -1, home: 1},
          {startPos: 2, position: -1, home: 1},
          {startPos: 2, position: -1, home: 1}],

        'Y': [{startPos: 12, position: -1, home: 1},
          {startPos: 12, position: -1, home: 1},
          {startPos: 12, position: -1, home: 1},
          {startPos: 12, position: -1, home: 1}]
      },
      path: [],
      startingPoints: [
        {i: 4, j: 1}, // Red
        {i: 1, j: 6}, // Blue
        {i: 9, j: 4}, // Green
        {i: 6, j: 9}  // Yellow
      ],
      goalCells: {
        'R': [{i: 5, j: 1}, {i: 5, j: 2}, {i: 5, j: 3}, {i: 5, j: 4}],
        'B': [{i: 1, j: 5}, {i: 2, j: 5}, {i: 3, j: 5}, {i: 4, j: 5}],
        'Y': [{i: 5, j: 9}, {i: 5, j: 8}, {i: 5, j: 7}, {i: 5, j: 6}],
        'G': [{i: 9, j: 5}, {i: 8, j: 5}, {i: 7, j: 5}, {i: 6, j: 5}],
      },
      homeCells: {
        'R': [{i: 0, j: 0}, {i: 0, j: 0}, {i: 0, j: 1}, {i: 1, j: 0}, {i: 1, j: 1}],
        'B': [{i: 0, j: 0}, {i: 0, j: 9}, {i: 0, j: 10}, {i: 1, j: 9}, {i: 1, j: 10}],
        'Y': [{i: 9, j: 9}, {i: 9, j: 10}, {i: 10, j: 9}, {i: 10, j: 10}],
        'G': [{i: 9, j: 0}, {i: 9, j: 1}, {i: 10, j: 0}, {i: 10, j: 1}],
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
      availablePawns: [],
      existingPawns: [],
      chosenPawn: null,
      hasPawnsOnBoard: false,
      playerNumber: null,
      whichPawn: null,
    };
  },

  async created() {
    this.generatePath();
    await this.convertDBtoBoard();
    await this.checkIfYourTurn();
    await this.defineCurrentPlayer();
    await this.setHomes();
    await this.hasPawnsOnBoardMethod();
    await this.checkIfFinished();
    await this.hasWon();
    this.whichTurn = (await this.userLobbyService.asyncGetLobbyTurn(this.lobbyNumber))
    this.matchStatus = (await this.lobbyService.asyncFindById(this.lobbyNumber)).status;
    this.countdown = (await this.lobbyService.asyncFindById(this.lobbyNumber)).turnTimer;


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
          }
        }
      }
    },

    async setHomes(){

      let whoseTurn = await this.userLobbyService.asyncGetLobbyTurn(this.lobbyNumber);

      this.pawns[this.currentPlayer][0].home = (await this.userLobbyService.asyncFindById(this.lobbyNumber))[whoseTurn-1].pawnAtHome1;
      this.pawns[this.currentPlayer][1].home = (await this.userLobbyService.asyncFindById(this.lobbyNumber))[whoseTurn-1].pawnAtHome2;
      this.pawns[this.currentPlayer][2].home = (await this.userLobbyService.asyncFindById(this.lobbyNumber))[whoseTurn-1].pawnAtHome3;
      this.pawns[this.currentPlayer][3].home = (await this.userLobbyService.asyncFindById(this.lobbyNumber))[whoseTurn-1].pawnAtHome4;

    },

    searchValueInArray() {
      const excludedCoordinates = [
        this.goalCells[this.currentPlayer][0],
        this.goalCells[this.currentPlayer][1],
        this.goalCells[this.currentPlayer][2],
        this.goalCells[this.currentPlayer][3]
      ];
      const chosenPawnIndex = this.pawns[this.currentPlayer].indexOf(this.chosenPawn)
      for (let i = 0; i < this.board.length; i++) {
        for (let j = 0; j < this.board[i].length; j++) {
          if (!this.isCoordinateExcluded(i, j, excludedCoordinates)) {
            if (this.board[i][j] === 'p' + this.currentPlayer + (chosenPawnIndex + 1)) {
              this.board[i][j] = 1;
              return { row: i, column: j };
            }
          }
        }
      }
      return null;
    },

    isCoordinateExcluded(row, column, excludedCoordinates) {
      for (let i = 0; i < excludedCoordinates.length; i++) {
        const excludedCoordinate = excludedCoordinates[i];
        if (excludedCoordinate.i === row && excludedCoordinate.j === column) {
          return true;
        }
      }
      return false;
    },

    async checkIfFinished(){
      if (this.matchStatus === 'FINISHED'){
        this.hasWonBool = true;
      } else  {
        this.hasWonBool = false;
      }
    },

    async hasPawnsOnBoardMethod() {
      console.log(this.pawns[this.currentPlayer]);
      if (
          (this.pawns[this.currentPlayer][0].home !== 0 &&
              this.pawns[this.currentPlayer][1].home !== 0 &&
              this.pawns[this.currentPlayer][2].home !== 0 &&
              this.pawns[this.currentPlayer][3].home !== 0)
      ) {
        this.hasPawnsOnBoard = false;
      } else {
        this.hasPawnsOnBoard = true;
      }
    },

    async addToBoard(chosenPawn) {
      this.chosenPawn = chosenPawn;
      const chosenPawnIndex = this.pawns[this.currentPlayer].indexOf(chosenPawn)
      const playerNumber = await this.lobbyService.asyncFindById(this.lobbyNumber);
      this.buttonClicked = true;

      for (let i = 0; i < this.board.length; i++) {
        for (let j = 0; j < this.board[i].length; j++) {
          if (this.board[i][j] === 'p' + this.currentPlayer +  (chosenPawnIndex + 1)) {
            this.board[i][j] = 1;
          }
        }
      }

      this.board[this.startingPoints[playerNumber.whoseTurn - 1].i][this.startingPoints[playerNumber.whoseTurn - 1].j] =
          "p" + this.currentPlayer + (chosenPawnIndex + 1);
      try {
        await this.userLobbyService.asyncUpdateHome(this.lobbyNumber, playerNumber.whoseTurn, (chosenPawnIndex + 1), 0);
      } catch (e) {
        console.log(e);
      }

      await this.convertBoardToDB();
    },

    returnToMatches(){
      this.$router.push('/ongoing-matches');
    },

    async defineCurrentPlayer(){
      let players = ['R', 'B', 'G', 'Y'];
      let nextIndex = await this.userLobbyService.asyncGetLobbyTurn(this.lobbyNumber);

      let playerLetter = players[nextIndex - 1];
      this.currentPlayer = playerLetter;
    },


    async rollDice() {
      this.buttonClickedDice = true;
      this.rolled_dice = await this.lobbyService.asyncRollDice();
      this.extraTurn = this.rolled_dice === 6 ? true : false;
      this.getRollPicture(this.rolled_dice)
      this.checkPawns();

      if(this.hasPawnsOnBoard === false && this.rolled_dice !== 6){
        await this.lobbyService.asyncIncreaseTurn(this.lobbyNumber);
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

      const encodedInputString = encodeURIComponent(inputString); // URL-encode the inputString

      const inputObject = { boardState: encodedInputString }; // Assign the URL-encoded inputString to the boardState property
      await this.lobbyService.asyncUpdateById(this.lobbyNumber, inputObject.boardState);
    },


    movePawnOut() {
      if(this.pawns[this.currentPlayer].home === 1 && this.rolled_dice === 6){
        this.pawns[this.currentPlayer].home = 0;
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

    async movePawn(chosenPawn) {
      await this.findPawnPosition(chosenPawn);
      const chosenPawnIndex = this.pawns[this.currentPlayer].indexOf(chosenPawn);
      if(this.newPos.col === 0 && this.newPos.row === 0){
        this.pawns[this.currentPlayer][chosenPawnIndex].position = this.pawns[this.currentPlayer][chosenPawnIndex].startPos;

      } else{
        // find pawn in path array
        const index = this.path.findIndex(coord => coord.i === this.newPos.i && coord.j === this.newPos.j);
        this.pawns[this.currentPlayer][chosenPawnIndex].position = index;
      }
      this.buttonClicked = true;
      if (!this.pawns[this.currentPlayer][chosenPawnIndex]) return;
      let pawn = this.pawns[this.currentPlayer][chosenPawnIndex];
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
        steps--;



        await this.$nextTick();
        await this.sleep(500);

        if (steps === 0) {
          let pawnInNewCell = this.getPawn(this.path[newPosition].i, this.path[newPosition].j);
          pawnInNewCell.forEach(pawnInCell => {
            if (pawnInCell.color !== this.currentPlayer) {
              // Send the opponent's pawn back to home
              this.pawns[pawnInCell.color].position = -1;
              this.pawns[pawnInCell.color].home = 1;
            }
          });
        }

        // Add pawn to finish line
        if (this.pawns[this.currentPlayer][chosenPawnIndex].position === this.pawns[this.currentPlayer][chosenPawnIndex].startPos - 2) {
          let playerPawn = 'p' + this.currentPlayer + (chosenPawnIndex + 1)
          this.board[this.goalCells[this.currentPlayer][chosenPawnIndex].i][this.goalCells[this.currentPlayer][chosenPawnIndex].j] = playerPawn
           try {
             await this.userLobbyService.asyncUpdateHome(this.lobbyNumber, this.whichTurn, (chosenPawnIndex + 1), 2);

        } catch (error) {
            console.error("Error in movePawn:", error);
        }
        }
      }

      if(this.newPos !== { row: 0, col: 0} ){
        try {
          await this.clearPawnPosition();
        } catch (error) {
          console.error("Error in fillInBoard:", error);
        }
      }

      await this.convertBoardToDB();
      await this.setHomes();

      if(this.pawns[this.currentPlayer][chosenPawnIndex].home == 2){
        this.searchValueInArray();
      } else {
        try {
          await this.fillInBoard();
        } catch (error) {
          console.error("Error in fillInBoard:", error);
        }
      }


      if (this.extraTurn === false) {
        await this.lobbyService.asyncIncreaseTurn(this.lobbyNumber);
        await this.convertBoardToDB();
      } else {
        this.extraTurn = false;
        await this.convertBoardToDB();
      }
      await this.convertBoardToDB();
    },

    async findPawnPosition(chosenPawn) {
      const chosenPawnIndex = this.pawns[this.currentPlayer].indexOf(chosenPawn);
      for (let i = 0; i < this.board.length; i++) {
        for (let j = 0; j < this.board[i].length; j++) {
          if (this.board[i][j] === 'p' + this.currentPlayer + (chosenPawnIndex + 1)) {
            this.rowPosition = i;
            this.colPosition = j;
            this.newPos = { i, j };
          }
        }
      }
      this.rowPosition = null;
      this.colPosition = null;
    },

    async clearPawnPosition(){
      const index = this.path.find(coord => coord.i === this.newPos.i && coord.j === this.newPos.j);
      this.board[index.i][index.j] = 1;
    },

    async hasWon(){
      if (this.pawns[this.currentPlayer][0].home === 2 && this.pawns[this.currentPlayer][1].home === 2
          && this.pawns[this.currentPlayer][2].home === 2 && this.pawns[this.currentPlayer][3].home === 2) {
        this.hasWonBool = true;
        await this.lobbyService.asyncFinishMatch(this.lobbyNumber);
      }
    },

    checkPawns() {

      const playerPawns = this.pawns[this.currentPlayer];
          // fill available pawns array with pawns that are in home
        for (let i = 0; i < playerPawns.length; i++) {
          if (playerPawns[i].home === 1) {
            this.availablePawns.push(playerPawns[i]);
          }
        }

      // fill existing pawns array with pawns that are not in home
      for (let i = 0; i < playerPawns.length; i++) {
        if (playerPawns[i].home === 0) {
          this.existingPawns.push(playerPawns[i]);
        }
      }


   },

    async fillInBoard() {
      const chosenPawnIndex = this.pawns[this.currentPlayer].indexOf(this.chosenPawn);
      let newPosition = this.pawns[this.currentPlayer][chosenPawnIndex].position;
      const currentPlayerLocation = this.getPlayerLocation(this.path[newPosition]);
      if (currentPlayerLocation) {
        const { i, j } = currentPlayerLocation;
        this.newPos = { i, j };

        if (this.board[i][j] !== 1) {

          const playerContent = this.board[i][j];
          const whichPawn = playerContent[2];

          switch (playerContent[1]) {
            case 'R':
              this.playerNumber = 1;
              break;
            case 'B':
              this.playerNumber = 2;
              break;
            case 'G':
              this.playerNumber = 3;
              break;
            case 'Y':
              this.playerNumber = 4;
              break;}

          this.whichPawn = whichPawn;

          // put opponents pawn back in its home cell
          this.board[this.homeCells[playerContent[1]][playerContent[2]].i][this.homeCells[playerContent[1]][playerContent[2]].j] =
              playerContent;

          this.board[i][j] = 'p' + this.currentPlayer + (chosenPawnIndex + 1);
          try {
            await this.userLobbyService.asyncUpdateHome(this.lobbyNumber, this.playerNumber, whichPawn, 1);
          } catch (error) {
            console.error("Error in fillInBoard:", error);
          }
          await this.convertBoardToDB();
        } else {
          this.board[i][j] = 'p' + this.currentPlayer + (chosenPawnIndex + 1);
          await this.convertBoardToDB();
        }

      }
    },


// Add this method to the Vue component
    sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
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
          case 'pR2': return 'pR2';
          case 'pR3': return 'pR3';
          case 'pR4': return 'pR4';
          case 'hR1': return 'hR1';
          case 'hR2': return 'hR2';
          case 'hR3': return 'hR3';
          case 'hR4': return 'hR4';
          case 'G': return 'base-cellG';
          case 'pG1': return 'pG1';
          case 'pG2': return 'pG2';
          case 'pG3': return 'pG3';
          case 'pG4': return 'pG4';
          case 'hG1': return 'hG1';
          case 'hG2': return 'hG2';
          case 'hG3': return 'hG3';
          case 'hG4': return 'hG4';
          case 'B': return 'base-cellB';
          case 'pB1': return 'pB1';
          case 'pB2': return 'pB2';
          case 'pB3': return 'pB3';
          case 'pB4': return 'pB4';
          case 'hB1': return 'hB1';
          case 'hB2': return 'hB2';
          case 'hB3': return 'hB3';
          case 'hB4': return 'hB4';
          case 'Y': return 'base-cellY';
          case 'pY1': return 'pY1';
          case 'pY2': return 'pY2';
          case 'pY3': return 'pY3';
          case 'pY4': return 'pY4';
          case 'hY1': return 'hY1';
          case 'hY2': return 'hY2';
          case 'hY3': return 'hY3';
          case 'hY4': return 'hY4';
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
      } else if (cell === 'pR1' && this.pawns['R'][0].home === 0){
        return 'block-cell'

      }
      else {
        return '';
      }
    },

    async checkIfYourTurn(){
      this.whichTurn = await this.userLobbyService.asyncWhoseTurn(this.lobbyNumber)
      this.whichUserTurn = await this.registerService.asyncFindById(this.whichTurn)
      if(this.whichTurn == this.sessionService.currentAccount.id){
        this.canIPlay = true;
      } else {
        this.canIPlay = false}
      }
      },

  mounted() {
    const countdownInterval = setInterval(async () => {
      this.countdown--;
      if (this.countdown <= 0) {
        clearInterval(countdownInterval);
        try {
          // eslint-disable-next-line no-empty
        } catch (error) {
        }
        location.reload();
      } else if (this.countdown <= 10) {
        this.countdownStyle = {color: 'red'};
        setTimeout(() => {
          this.countdownStyle = {};
        }, 200);
      }
    }, 1000);
  }
};
</script>


<style scoped>
.board {
  display: flex;
  flex-direction: column;
  margin: auto;
  width: 655px;
  height: 658px;
  padding-left: 10px;
  padding-bottom: 10px;
  background: cadetblue;
}

.row {
  display: flex;

}

h2 {
  text-align: center;
}

.winner{
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


.hR1,
.hR2,
.hR3,
.hR4 {
  background-color: lightcoral;
}

.hB1,
.hB2,
.hB3,
.hB4 {
  background-color: lightblue;
}

.hG1,
.hG2,
.hG3,
.hG4 {
  background-color: lightgreen;
}

.hY1,
.hY2,
.hY3,
.hY4 {
  background-color: lightyellow;
}

.pR1,
.pR2,
.pR3,
.pR4 {
  position: relative;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: red;
  transform: scale(0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 24px;

}

.pR1::before,
.pR2::before,
.pR3::before,
.pR4::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 24px;
  height: 24px;
  border-radius: 50%;
}

.pR1::after {
  content: "1";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pR2::after {
  content: "2";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pR3::after {
  content: "3";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pR4::after {
  content: "4";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pB1,
.pB2,
.pB3,
.pB4 {
  position: relative;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: blue;
  transform: scale(0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 24px;
}

.pB1::before,
.pB2::before,
.pB3::before,
.pB4::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background-color: blue;
}


.pB1::after {
  content: "1";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pB2::after {
  content: "2";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pB3::after {
  content: "3";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pB4::after {
  content: "4";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pG1,
.pG2,
.pG3,
.pG4 {
  position: relative;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: green;
  transform: scale(0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 24px;
}

.pG1::before,
.pG2::before,
.pG3::before,
.pG4::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background-color: green;
}

.pG1::after {
  content: "1";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pG2::after {
  content: "2";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pG3::after {
  content: "3";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pG4::after {
  content: "4";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
}

.pY1,
.pY2,
.pY3,
.pY4 {
  position: relative;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: yellow;
  transform: scale(0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 24px;
}

.pY1::before,
.pY2::before,
.pY3::before,
.pY4::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background-color: yellow;
}

.pY1::after {
  content: "1";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: black;
  font-size: 24px;
}

.pY2::after {
  content: "2";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: black;
  font-size: 24px;
}

.pY3::after {
  content: "3";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: black;
  font-size: 24px;
}

.pY4::after {
  content: "4";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: black;
  font-size: 24px;
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
