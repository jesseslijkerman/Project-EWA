import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import AboutView from "../views/AboutView.vue";
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";
import LudoBoard from "../components/LudoBoard.vue";
import OngoingMatches from "@/components/OngoingMatches.vue";
import CreateGame from "../components/CreateGame.vue";
import DiceRoll from "@/components/DiceRoll.vue";
import GameRules from "@/components/GameRules.vue";
import ResetPassword from "@/components/ResetPassword.vue";
import WaitingRoom from "@/components/WaitingRoom.vue";
import FriendsList from "../components/FriendsList.vue";
import FindPlayers from "../components/FindPlayers.vue";
import ForgotPassword from "@/components/ForgotPassword.vue";
import ResetPasswordWithToken from "@/components/ResetPasswordWithToken.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
      meta: {
        hideNavbar: true,
      }
    },
    {
      path: "/about",
      name: "about",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      // component: () => import("../views/AboutView.vue"),
      component: AboutView
    },
    {
      path: "/game",
      name: "game",
      component: LudoBoard,
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        hideNavbar: true,
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        hideNavbar: true,
      },
    },
    {
      path: "/ongoing-matches",
      name: "ongoing_matches",
      component: OngoingMatches,
    },
    {
      path: "/match/:id",
      name: "waiting-room",
      component: WaitingRoom,
    },
    {
      path: "/match/:id/play",
      name: "clickedMatch",
      component: LudoBoard,
    },
    {
      path: "/createGame",
      name: "createGame",
      component: CreateGame,
    },
    {
      path: "/roll",
      name: "roll",
      component: DiceRoll
    },
    {
      path: "/gamerules",
      name: "gamerules",
      component: GameRules
    },
    {
      path: "/reset-password",
      name: "resetPassword",
      component: ResetPassword
    },
    {
      path: "/ForgotPassword",
      name: "forgotPassword",
      component: ForgotPassword,
      meta: {
        hideNavbar: true
      }
    },
    {
      path: "/resetPassword/:token",
      name: "ResetPasswordWithToken",
      component: ResetPasswordWithToken,
      meta: {
        hideNavbar: true
      }
    },
    {
      path: "/friends",
      name: "friendsList",
      component: FriendsList
    },
    {
      path: "/find-players",
      name: "findPlayers",
      component: FindPlayers
    }
  ],
});

export default router;
