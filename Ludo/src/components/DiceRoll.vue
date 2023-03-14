

<template>
  <img class=dice @click="rollDice" :src="getRollPicture(currentRoll.eyes)">
  <button :disabled="disabled" @click="rollDice">Roll dice</button>
  <br>
  <p>Vorige beurt: </p>
  <img class=priorDice @click="rollDice" :src="getRollPicture(priorRoll.eyes)">

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
