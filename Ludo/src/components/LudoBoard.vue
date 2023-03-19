<template>
  <div class="mainframe">
    <div class="ludoboard">
      <div class="red"> <!--Red player home where the pawns are located-->
        <div class="playerzone">
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
        </div>
      </div>
      <!--playfield top part      -->
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="green"> <!--Green player home where the pawns are located-->
        <div class="playerzone">
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
        </div>
      </div>

      <div class="playfield" id=""></div>
      <div class="greensafe" id=""></div>
      <div class="greensafe" id=""></div>
      <div class="playfield" id=""></div>
      <div class="greensafe" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="greensafe" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="greensafe" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="greensafe" id=""></div>
      <div class="playfield" id=""></div>
      <!--playfield middle part      -->
      <div class="playfield" id=""></div>
      <div class="redhome" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
<!--middle box with player colors-->
      <div class="centerbox">
        <img src="../assets/Ludo-Logo.jpg" height="120" width="120"/>
      </div>

      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="redhome" id=""></div>
      <div class="redhome" id=""></div>
      <div class="redhome" id=""></div>
      <div class="redhome" id=""></div>
      <div class="redhome" id=""></div>
      <div class="yellowhome" id=""></div>
      <div class="yellowhome" id=""></div>
      <div class="yellowhome" id=""></div>
      <div class="yellowhome" id=""></div>
      <div class="yellowhome" id=""></div>
      <div class="playfield" id=""></div>

      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>

      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="yellowhome" id=""></div>
      <div class="playfield" id=""></div>

      <div class="blue"> <!--Blue player home where the pawns are located-->
        <div class="playerzone">
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
        </div>
      </div>
      <div class="playfield" id=""></div>
      <div class="bluehome" id=""></div>
      <div class="playfield" id=""></div>
      <div class="yellow"> <!--Yellow player home where the pawns are located-->
        <div class="playerzone">
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
          <div class="player" id=""></div>
        </div>
      </div>
      <!--playfield bottom part      -->
      <div class="playfield" id=""></div>
      <div class="bluehome" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="bluehome" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="bluehome" id=""></div>
      <div class="playfield" id=""></div>
      <div class="bluehome" id=""></div>
      <div class="bluehome" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
      <div class="playfield" id=""></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LudoBoard",
};
</script>

<style scoped>
body {
  margin: 0px;
}

.mainframe {
  display: flex;
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

.greensafe {
  background-color: #00e34c;
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
</style>
