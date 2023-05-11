<template>
  <div class="mainframe">
    <div class="ludoboard">
      <div class="red"> <!--Red player home where the pawns are located-->
        <div class="playerzone">
          <div class="player" id=""><span></span></div>
          <div class="player" id=""><span></span></div>
          <div class="player" id=""><span></span></div>
          <div class="player" id=""><span></span></div>
        </div>
      </div>
      <!--playfield top part-->
      <div class="playfield" id="1"><span></span></div>
      <div class="playfield" id="2"><span></span></div>
      <div class="playfield" id="3"><span></span></div>
      <div class="green"> <!--Green player home where the pawns are located-->
        <div class="playerzone">
          <div class="player" id=""><span></span></div>
          <div class="player" id=""><span></span></div>
          <div class="player" id=""><span></span></div>
          <div class="player" id=""><span></span></div>
        </div>
      </div>

      <div class="playfield" id="51"><span></span></div>
      <div ref="greenhome1" class="greenhome" id="60"><span></span></div>
      <div class="greenstart" id="4"><span></span></div>
      <div class="playfield" id="50"><span></span></div>
      <div ref="greenhome2" class="greenhome" id="61"><span></span></div>
      <div class="playfield" id="5"><span></span></div>
      <div class="playfield" id="49"><span></span></div>
      <div ref="greenhome3" class="greenhome" id="62"><span></span></div>
      <div class="playfield" id="6"><span></span></div>
      <div class="playfield" id="48"><span></span></div>
      <div ref="greenhome4" class="greenhome" id="63"><span></span></div>
      <div class="playfield" id="7"><span></span></div>
      <div class="playfield" id="47"><span></span></div>
      <div class="greenhomeend" id=""><span></span></div>
      <div class="playfield" id="8"><span></span></div>
      <!--playfield middle part      -->
      <div class="playfield" id="41"><span></span></div>
      <div class="redstart" id="42"><span></span></div>
      <div class="playfield" id="43"><span></span></div>
      <div class="playfield" id="44"><span></span></div>
      <div class="playfield" id="45"><span></span></div>
      <div class="playfield" id="46"><span></span></div>
<!--middle box with player colors-->
      <div class="centerbox">
<!--        <img src="../assets/Ludo-Logo.jpg" height="120" width="120"/>-->
        <DiceRoll></DiceRoll>
      </div>


      <div class="playfield" id="9"></div>
      <div class="playfield" id="10"></div>
      <div class="playfield" id="11"></div>
      <div class="playfield" id="12"></div>
      <div class="playfield" id="13"></div>
      <div class="playfield" id="14"></div>
      <div class="playfield" id="40"></div>
      <div ref="redhome1" class="redhome" id="90"></div>
      <div ref="redhome2" class="redhome" id="91"></div>
      <div ref="redhome3" class="redhome" id="92"></div>
      <div ref="redhome4" class="redhome" id="93"></div>
      <div class="redhomeend" id=""></div>
      <div class="yellowhomeend" id=""></div>
      <div ref="yellowhome4" class="yellowhome" id="73"></div>
      <div ref="yellowhome3" class="yellowhome" id="72"></div>
      <div ref="yellowhome2" class="yellowhome" id="71"></div>
      <div ref="yellowhome1" class="yellowhome" id="70"></div>
      <div class="playfield" id="15"></div>

      <div class="playfield" id="39"></div>
      <div class="playfield" id="38"></div>
      <div class="playfield" id="37"></div>
      <div class="playfield" id="36"></div>
      <div class="playfield" id="35"></div>
      <div class="playfield" id="34"></div>

      <div class="playfield" id="21"></div>
      <div class="playfield" id="20"></div>
      <div class="playfield" id="19"></div>
      <div class="playfield" id="18"></div>
      <div class="yellowstart" id="17"></div>
      <div class="playfield" id="16"></div>

      <div class="blue"> <!--Blue player home where the pawns are located-->
        <div class="playerzone">
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
        </div>
      </div>
      <div class="playfield" id="33"></div>
      <div class="bluehomeend" id=""></div>
      <div class="playfield" id="22"></div>
      <div class="yellow"> <!--Yellow player home where the pawns are located-->
        <div class="playerzone">
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
        </div>
      </div>
      <!--playfield bottom part      -->
      <div class="playfield" id="33"></div>
      <div ref="bluehome4" class="bluehome" id="83"></div>
      <div class="playfield" id="23"></div>
      <div class="playfield" id="32"></div>
      <div ref="bluehome3" class="bluehome" id="82"></div>
      <div class="playfield" id="24"></div>
      <div class="playfield" id="31"></div>
      <div ref="bluehome2" class="bluehome" id="81"></div>
      <div class="playfield" id="25"></div>
      <div class="bluestart" id="30"></div>
      <div ref="bluehome1" class="bluehome" id="80"></div>
      <div class="playfield" id="26"></div>
      <div class="playfield" id="29"></div>
      <div class="playfield" id="28"></div>
      <div class="playfield" id="27"></div>
    </div>

  </div>




</template>

<script>

import DiceRoll from "@/components/DiceRoll.vue";

export default {
  name: "LudoBoard",
  inject: ["userLobbyService"],
  components: DiceRoll,

  data(){
    return{

      pawns: null

    };
  },

  created() {
    this.pieceInHome("red")
  },

  methods : {

    async checkForPawn() {

      await this.$nextTick();
      this.pawns = await this.userLobbyService.asyncFindAll()

    },

    async pieceInHome(color) {
      await this.$nextTick();
      this.pawns = await this.userLobbyService.asyncFindAll()

      const colorhome1 = this.$refs[color + "home1"];
      const colorhome2 = this.$refs[color + "home2"];
      const colorhome3 = this.$refs[color + "home3"];
      const colorhome4 = this.$refs[color + "home4"];

      const totalColorHomes = 4;
      let occupiedColorHomes = 0;

      const colorHomes = [colorhome1, colorhome2, colorhome3, colorhome4];

      for (let i = 0; i < colorHomes.length; i++) {
        if (
            colorHomes[i].id.toString() === this.pawns[0].pawnPosition1.toString() ||
            colorHomes[i].id.toString() === this.pawns[0].pawnPosition2.toString() ||
            colorHomes[i].id.toString() === this.pawns[0].pawnPosition3.toString() ||
            colorHomes[i].id.toString() === this.pawns[0].pawnPosition4.toString()
        ) {
          occupiedColorHomes++;
        }
      }

      if (occupiedColorHomes === totalColorHomes) {
        console.log("Congratulations! You won the game!");
      } else if (occupiedColorHomes > 0) {
        console.log("At least one home is occupied by a pawn");
      } else {
        console.log("No home is occupied by a pawn");
      }
    }

  }
};
</script>

<style scoped>
body {
  margin: 0px;
}

.mainframe {

  height: 100vh;
  justify-content: center;
  align-items: center;
}

.ludoboard {
  background-color: white;
  border: 0.5px solid black;
  width: 600px;
  height: 600px;
  display: grid;

  /*Om te centreren*/
  position: fixed;
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
  /**/

  grid-template-columns: repeat(15, 40px);
  grid-template-rows: repeat(15, 40px);
}

.centerbox {
  grid-column: 7/10;
  grid-row: 7/10;
  transform: rotate(180deg);
}

.red {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f7162d;
  border: 0.5px solid #9e0616;
  grid-column: 1/7;
  grid-row: 1/7;
}

.redhome {
  background-color: #f7162d;
  border: 0.5px solid #9e0616;
}

.redstart {
  background-color: #f7162d;
  border: 0.5px solid #9e0616;
}

.redhomeend {
  background-color: #566769;
  border: 0.5px solid #9e0616;
}

.playerzone {
  display: grid;
  grid-template-columns: 1fr 1fr;
}

.player {
  border-radius: 50%;
  background-color: white;
  opacity: 0.9;
  width: 50px;
  height: 50px;
}

.green {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #00e34c;
  border: 0.5px solid #039634;
  grid-column: 10/16;
  grid-row: 1/7;
}

.greenhome {
  background-color: #00e34c;
  border: 0.5px solid #039634;
}

.greenstart {
  background-color: #00e34c;
  border: 0.5px solid #039634;
}

.greenhomeend {
  background-color: #566769;
  border: 0.5px solid #039634;
}

.playfield {
  border: 0.5px solid #b3b3b3;
}

.blue {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #04c3c9;
  border: 0.5px solid #027478;
  grid-column: 1/7;
  grid-row: 10/16;
}

.bluehome {
  background-color: #04c3c9;
  border: 0.5px solid #027478;
}

.bluestart {
  background-color: #04c3c9;
  border: 0.5px solid #027478;
}

.bluehomeend {
  background-color: #566769;
  border: 0.5px solid #027478;
}

.yellow {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f7bb05;
  border: 0.5px solid #9c7605;
  grid-column: 10/16;
  grid-row: 10/16;
}

.yellowhome {
  background-color: #f7bb05;
  border: 0.5px solid #9c7605;
}

.yellowstart {
  background-color: #f7bb05;
  border: 0.5px solid #9c7605;
}

.yellowhomeend {
  background-color: #566769;
  border: 0.5px solid #9c7605;
}

</style>
