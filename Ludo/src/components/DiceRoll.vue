<template>
  <div class="allPlayerRolls">
    <div class="playerRoll" v-for="(players, index) in playerList" :key="players">
      <div v-show="currentPlayer == players.id">
        <br>
        <img class="dice" :src="getRollPicture(players.currentRoll.eyes)"/>



        <span :style="[currentPlayer === players.id ? { background: players.name } : players.style,]" class="dot"></span>
        <button class="rollButton" v-if="currentPlayer == players.id" :disabled="disabled" @click="rollDice">
          Roll dice
        </button>

        <!--        <div @click="toggleItem(index)">-->
        <!--          <p @click="toggleCondition()">Click to hide/show last roll</p>-->
        <!--        </div>-->

        <!--        <div v-if="activeIndices.includes(index)">-->
        <!--          <img class="priorDice" :src="getRollPicture(players.priorRoll.eyes)" />-->
        <!--        </div>-->

      </div>

    </div>
  </div>

  <div>

  </div>
</template>

<script>
import { Dice } from "@/models/Dice.js";

export default {
  name: "DiceRoll",
  inject: ["userLobbyService"],
  data() {
    return {
      WIN_CONDITION: 4,
      disabled: false,
      hideAndShowText: true,
      playerList: [
        {
          id: 0,
          name: "red",
          currentRoll: 0,
          priorRoll: 0,
          style: { "border-color": "red" },
          piecesInHome: 0,
          pawnPosition1: 1,
          pawnPosition2: 1,
          pawnPosition3: 1,
          pawnPosition4: 1,
        },
        {
          id: 1,
          name: "blue",
          currentRoll: 0,
          priorRoll: 0,
          style: { "border-color": "blue" },
          piecesInHome: 0
        },
        {
          id: 2,
          name: "green",
          currentRoll: 0,
          priorRoll: 0,
          style: { "border-color": "green" },
          piecesInHome: 0
        },
        {
          id: 3,
          name: "yellow",
          currentRoll: 0,
          priorRoll: 0,
          style: { "border-color": "yellow" },
          piecesInHome: 0
        },
      ],
      currentPlayer: 0,
      activeIndices: [],
    };
  },

  methods: {
    async rollDice() {
      this.playerList[this.currentPlayer].priorRoll = this.playerList[this.currentPlayer].currentRoll;
      let roll = Dice.createDiceRoll();
      this.playerList[this.currentPlayer].currentRoll = roll;
      console.log("current roll: " + roll.eyes)
      this.getRollPicture(roll.eyes)


      let pawns = await this.userLobbyService.asyncFindAll()
      let player = this.playerList[0]

      player.pawnPosition1 = pawns[0].pawnPosition1 + this.playerList[this.currentPlayer].currentRoll.eyes

      const positions = [player.pawnPosition2, player.pawnPosition3, player.pawnPosition4];
      const index = positions.findIndex(position => player.pawnPosition1 === position);

      if (index !== -1) {
        positions[index].set(null);
      }

      await this.updatePawnPos()

      if (this.playerList[this.currentPlayer].currentRoll.eyes == 6) {
        // yep
      }
      if (this.currentPlayer === 3) {
        this.currentPlayer = 0;
        console.log("Aan de beurt: " + this.playerList[this.currentPlayer].name)
      }
      else {
        this.currentPlayer = this.currentPlayer + 1;
        console.log("Aan de beurt: " + this.playerList[this.currentPlayer].name)
      }
    },

    getRollPicture(src) {
      console.log(src)
      if (src === undefined) {
        return;
      } else {
        return "/src/assets/dice" + src + ".png";
      }
    },

    toggleItem(index) {
      if (this.activeIndices.includes(index)) {
        this.activeIndices.splice(this.activeIndices.indexOf(index), 1);
      } else {
        this.activeIndices.push(index);
      }
    },

    toggleCondition() {
      this.hideAndShowText = !this.hideAndShowText;
    },

    async updatePawnPos(){

      const result = {
        pawnPosition1: this.pawnPosition1,
        pawnPosition2: this.pawnPosition2,
        pawnPosition3: this.pawnPosition3,
        pawnPosition4: this.pawnPosition4,
      }

      await this.userLobbyService.asyncSave(JSON.stringify(result))
    }

  },
  created() {
    console.log("Start positie: ")
    console.log("Kleur starter: " + this.playerList[this.currentPlayer].name)
  }

};
</script>
