

<template>
  <img class=dice @click="rollDice" :src="getRollPicture(currentRoll.eyes)">
  <button :disabled="disabled" @click="rollDice">Roll dice</button>

  <br>

  <div v-if="!hide" v-on:click="hide = !hide">
    <p>Click to show last roll</p>
  </div>

  <div v-if="hide" v-on:click="hide = !hide">
    <p>Click to hide last roll</p>
  </div>


  <div v-if="hide">
    <img class=priorDice :src="getRollPicture(priorRoll.eyes)">
  </div>

</template>

<script>

import {Dice} from "@/models/Dice";

export default {
  name: "DiceRoll",
  data(){
    return{
      currentRoll: 0,
      priorRoll: 0,
      disabled: false,
      hide: false

    }
  },

  methods : {
    rollDice(){
      this.priorRoll = this.currentRoll;
      let roll = Dice.createDiceRoll()
      this.currentRoll = roll;

      if (roll.eyes === 6){
        return this.disabled = false;
      } else {
        return this.disabled = true;
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
