

<template>
  <img class=dice :src="getRollPicture(roll.eyes)">
  <p>{{ roll.eyes }}</p>
  <button :disabled="disabled" @click="rollDice">Roll dice</button>

</template>

<script>

import {Dice} from "@/models/Dice";

export default {
  name: "DiceRoll",
  data(){
    return{
      roll: 0,
      disabled: false
    }
  },

  methods : {
    rollDice(){
      let roll = Dice.createDiceRoll()
      this.roll = roll;

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
