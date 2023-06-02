<template>
  <div class="topnav">
    <a :class="{ active: currentRoute === '/ongoing-matches' }" href="/ongoing-matches">Matches</a>

    <a :class="{active: currentRoute === '/createGame'}" href="/createGame">Create Game</a>

    <a :class="{ active: currentRoute === '/gamerules' }" href="/gamerules">Rules</a>

    <div class="right">
      <button class="logout-button" @click="confirmLogout">Logout</button>
    </div>

    <div class="right">
      <a :class="{ active: currentRoute === '/reset-password' }" href="/reset-password">Reset password</a>
    </div>

  </div>
</template>

<script>
export default {
  name: "NavBar",
  inject: ['sessionService'],
  methods: {
    confirmLogout() {
      if (confirm("Are you sure you want to sign out?")) {
        // logout functionaliteit
        console.log("User confirmed sign out.");
        this.sessionService.signOut();
        this.$router.push("/login");
      } else {
        console.log("User cancelled sign out.");
      }
    },
  },
  computed: {
    currentRoute() {
      return window.location.pathname;
    },
  },
};
</script>

<style scoped>

.topnav button.logout-button{
  float: left;
  background: none;
  border: none;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  margin-bottom: 15px;
  font-size: 17px;
  cursor: pointer;
  z-index: 9999;
}

div.right{
  height: 55.2px;
}

.right {
  float: right;
  display: flex;
  align-items: center;
}

.topnav {
  background-color: #333;
  overflow: hidden;
}

.topnav a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover,
.topnav button.logout-button:hover {
  background-color: #ddd;
  color: black;
}

.topnav a.active,
.topnav button.logout-button.active {
  background-color: #007bff;
  color: white;
}
</style>
