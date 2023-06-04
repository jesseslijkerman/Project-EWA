
<script >
import {LobbyAdaptor} from "./services/LobbyAdaptor";
import {UsersAdaptor} from "./services/UsersAdaptor";
import {UserLobbyAdaptor} from "./services/UserLobbyAdaptor";
import NavBar from "@/components/NavBar.vue";
import {AuthenticationService} from "./services/AuthenticationService";
import {shallowReactive} from "vue";
import {SessionSbService} from "./services/SessionSbService";
import {FetchInterceptor} from "./services/FetchInterceptor";
import CONFIG from '../app-config.js'

export default {
  components: {NavBar},
  data(){
    return {
     isSticky: false
    };
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },
  destroyed() {
    window.removeEventListener('scroll', this.handleScroll)
  },
  methods: {
    handleScroll(){
      this.isSticky = window.scrollY > 0;
    }
  },
  provide(){
    //create a singleton reactive service tracking the authorisation data of the session
    this.theSessionSbService = shallowReactive(
        new SessionSbService(CONFIG.BACKEND_URL + "/usersAuth", CONFIG.JWT_STORAGE_ITEM))
    this.theFetchInterceptor =
        new FetchInterceptor(this.theSessionSbService, this.$router);

    return{
      lobbyService: new LobbyAdaptor("http://localhost:6969/lobbies"),
      registerService: new UsersAdaptor("http://localhost:6969/users"),
      authenticationService: new AuthenticationService("http://localhost:6969/usersAuth/login"),
      userLobbyService: new UserLobbyAdaptor("http://localhost:6969/userLobbies"),

      // reactive, stateful services
      sessionService: this.theSessionSbService,

    }
  }
}

</script>

<template>
  <NavBar v-if="!$route.meta.hideNavbar" :class="{'sticky-header': isSticky}"/>
  <RouterView />
</template>

<style scoped>

.sticky-header {
  position: fixed;
  top: 0;
  z-index: 999;
  width: 100%;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
