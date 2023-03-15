

<template>
  <div class="player" v-for="players in playerList" :key="players">
    <span :style="[currentPlayer == players.id ? { 'background': players.name  }: players.style ]" class="dot"></span>
    <img class=dice @click="rollDice" :src="getRollPicture(players.currentRoll.eyes)">
    <button :disabled=disabled @click="rollDice">Roll dice</button>

    <br>

    <div v-if="!hide" v-on:click="hide = !hide">
      <p>Click to show last roll ▼</p>
    </div>

    <div v-if="hide" v-on:click="hide = !hide">
      <p>Click to hide last roll ▲</p>
    </div>


    <div v-if="hide">
      <img class=priorDice :src="getRollPicture(players.priorRoll.eyes)">
    </div>
  </div>

</template>

<script>

import {Dice} from "@/models/Dice";

export default {
  name: "DiceRoll",
  data(){
    return{
      disabled: false,
      hide: false,
      playerList: [
          { id: 0, name: "red", currentRoll:0, priorRoll: 0, style: {'border-color': "red"}},
          { id: 1, name: "blue", currentRoll:0, priorRoll: 0, style: {'border-color':"blue" }},
          { id: 2, name: "green", currentRoll:0, priorRoll: 0, style: {'border-color':"green" }},
          { id: 3, name: "yellow", currentRoll:0, priorRoll: 0,style: {'border-color':"yellow" }}
      ],
      currentPlayer: 0,
      bing: null
    }
  },

  methods : {
    rollDice(){

      this.playerList[this.currentPlayer].priorRoll = this.playerList[this.currentPlayer].currentRoll;
      let roll = Dice.createDiceRoll()
      this.playerList[this.currentPlayer].currentRoll = roll;

      if (this.currentPlayer === 3){
        this.currentPlayer = 0;
      } else {
        this.currentPlayer = this.currentPlayer + 1
      }
    },

    getRollPicture(src){
      if (src === undefined){
        return
      } else {
        return "src/assets/dice" + src + ".png"
      }
    },
  },
}

</script>
