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

      <div @click="toggleItem(index)">
        <p @click="toggleCondition()">Click to hide/show last roll</p>
      </div>

      <div v-if="activeIndices.includes(index)">
        <img class="priorDice" :src="getRollPicture(players.priorRoll.eyes)" />
      </div>

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
import { Dice } from "@/models/Dice";

export default {
  name: "DiceRoll",
  data() {
    return {
      disabled: false,
      hideAndShowText: true,
      playerList: [
        {
          id: 0,
          name: "red",
          currentRoll: 0,
          priorRoll: 0,
          style: { "border-color": "red" },
        },
        {
          id: 1,
          name: "blue",
          currentRoll: 0,
          priorRoll: 0,
          style: { "border-color": "blue" },
        },
        {
          id: 2,
          name: "green",
          currentRoll: 0,
          priorRoll: 0,
          style: { "border-color": "green" },
        },
        {
          id: 3,
          name: "yellow",
          currentRoll: 0,
          priorRoll: 0,
          style: { "border-color": "yellow" },
        },
      ],
      currentPlayer: 0,
      activeIndices: [],
    };
  },

  methods: {
    rollDice() {
      this.playerList[this.currentPlayer].priorRoll =
        this.playerList[this.currentPlayer].currentRoll;
      let roll = Dice.createDiceRoll();
      this.playerList[this.currentPlayer].currentRoll = roll;

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
  },
};
</script>
