<template>
  <div class="allPlayerRolls">
    <div class="playerRoll" v-for="(players, index) in playerList" :key="players">
      <span
        :style="[
          currentPlayer == players.id
            ? { background: players.name }
            : players.style,
        ]"
        class="dot"
      ></span>
      <img
        class="dice"
        @click="rollDice"
        :src="getRollPicture(players.currentRoll.eyes)"
      />

      <br />

      <br />  <br />  <br />  <br />  <br />  <br />  <br />   <br />  <br />  <br />  <br />
      <div @click="toggleItem(index)">
        <p @click="toggleCondition()">Click to hide/show last roll</p>
      </div>


      <div v-if="activeIndices.includes(index)">
        <img class="priorDice" :src="getRollPicture(players.priorRoll.eyes)" />
      </div>
      <br />  <br />
      <button
        v-if="currentPlayer == players.id"
        :disabled="disabled"
        @click="rollDice"
      >
        Roll dice
      </button>
    </div>
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
          pawnPosition1: 1
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
      this.playerList[this.currentPlayer].priorRoll =
        this.playerList[this.currentPlayer].currentRoll;
      let roll = Dice.createDiceRoll();
      this.playerList[this.currentPlayer].currentRoll = roll;

      let pawns = await this.userLobbyService.asyncFindAll()

      this.pawnPosition1 = pawns[0].pawnPosition1 + this.playerList[this.currentPlayer].currentRoll.eyes
      await this.updatePawnPos()

      if (this.playerList[this.currentPlayer].currentRoll.eyes == 6) {
        /* empty */
      } else if (this.currentPlayer === 3) {
        this.currentPlayer = 0;
      } else {
        this.currentPlayer = this.currentPlayer + 1;
      }
    },

    getRollPicture(src) {
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

};
</script>
