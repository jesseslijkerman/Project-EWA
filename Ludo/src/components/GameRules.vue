<template>
  <div class="game-rules">
    <h2 class="title">Game Rules</h2>
    <ol>
      <li><strong>Objective:</strong> The objective of Ludo is to be the first player to move all four of your colored pawns from the starting area to your home area.</li>
      <li><strong>Game Setup:</strong> Each player receives a color (red, green, blue, or yellow) and takes four pawns of the corresponding color. The pawns are placed in the starting area of the same color.</li>
      <li><strong>Turns:</strong> The players take turns in a clockwise direction.</li>
      <li><strong>Movement:</strong> On your turn, you roll a standard six-sided die to determine the number of spaces to move one of your pawns. You can only move your own pawns.</li>
      <li><strong>Starting the Game:</strong> To enter the game, you need to roll a 6. If you roll a 6, you can move one of your pawns from the starting area onto the starting square.</li>
      <li><strong>Pawn Movement:</strong> Once your pawn is on the board, you can move it forward the number of spaces indicated by the rolled die. You can move your pawn to any unoccupied space or a space occupied by an opponent's pawn, which sends their pawn back to their starting area. However, you cannot capture your own pawns.</li>
      <li><strong>Capturing:</strong> If you land on a space occupied by an opponent's pawn (not in a safe zone), you capture that pawn. The captured pawn is sent back to the opponent's starting area.</li>
      <li><strong>Rolling a 6:</strong> If you roll a 6, you get an additional turn. You can roll the die again and move the same or a different pawn.</li>
    </ol>
  </div>

</template>

<script>
export default {

  name: "GameRules"
}
</script>

<style scoped>
.game-rules {
  max-width: 90%;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #121212;
  color: #fff;
  border-radius: 1rem;
  box-shadow: 0px 2px 8px rgba(0,0,0,0.3);
}

li{
  font-size: 25px;
  padding: 10px;
}

.title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #007bff;
}
</style>