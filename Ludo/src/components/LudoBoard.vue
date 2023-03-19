<template>
  <div class="mainframe">
    <div class="ludoboard">
      <div class="red">
        <div class="playerzone">
          <div class="player"></div>
          <div class="player"></div>
          <div class="player"></div>
          <div class="player"></div>
        </div>
      </div>
<!--playfield top part      -->
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="green">
        <div class="playerzone">
          <div class="player"></div>
          <div class="player"></div>
          <div class="player"></div>
          <div class="player"></div>
        </div>
      </div>

      <div class="playfield"></div>
      <div class="greensafe"></div>
      <div class="greensafe"></div>
      <div class="playfield"></div>
      <div class="greensafe"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="greensafe"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="greensafe"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="greensafe"></div>
      <div class="playfield"></div>
<!--playfield middle part      -->
      <div class="playfield"></div>
      <div class="redhome"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>

      <div class="centerbox"></div>

      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>

      <div class="playfield"></div>
      <div class="redhome"></div>
      <div class="redhome"></div>
      <div class="redhome"></div>
      <div class="redhome"></div>
      <div class="redhome"></div>

      <div class="yellowhome"></div>
      <div class="yellowhome"></div>
      <div class="yellowhome"></div>
      <div class="yellowhome"></div>
      <div class="yellowhome"></div>
      <div class="playfield"></div>

      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>

      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="yellowhome"></div>
      <div class="playfield"></div>

      <div class="blue">
        <div class="playerzone">
          <div class="player"></div>
          <div class="player"></div>
          <div class="player"></div>
          <div class="player"></div>
        </div>
      </div>
      <div class="playfield"></div>
      <div class="bluehome"></div>
      <div class="playfield"></div>
      <div class="yellow">
        <div class="playerzone">
          <div class="player"></div>
          <div class="player"></div>
          <div class="player"></div>
          <div class="player"></div>
        </div>
      </div>
<!--playfield bottom part      -->
      <div class="playfield"></div>
      <div class="bluehome"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="bluehome"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="bluehome"></div>
      <div class="playfield"></div>
      <div class="bluehome"></div>
      <div class="bluehome"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
      <div class="playfield"></div>
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
  width: 0px;
  height: 0px;
  border-top: 60px solid #00e34c;
  border-bottom: 60px solid #04c3c9;
  border-left: 60px solid #f7162d;
  border-right: 60px solid #f7bb05;
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
