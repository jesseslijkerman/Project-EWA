<template>
  <NavBar></NavBar>
  <div class="container">
    <form class="loginForm" @submit.prevent="handleLogin">

      <div class="form-outline mb-4">
        <label class="form-label">Old password</label>
        <input type="text" class="form-control form-control-lg" v-model="entered_password"
               placeholder="Old password" required/>
      </div>


      <div class="form-outline mb-4">
        <label class="form-label">New password</label>
        <input type="text" class="form-control form-control-lg" v-model="entered_new_password"
               placeholder="New password" required/>
      </div>

      <!-- Submit button -->
      <div class="text-center">
        <button type="button" class="btn btn-primary btn-block mb-4" v-on:click="handleLogin">Change password</button>
      </div>


    </form>
  </div>
</template>

<script>
import router from "../router";
import NavBar from "@/components/NavBar.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  inject: ["RegisterService"],
  name: "ResetPassword",
  components: {NavBar},
  data() {
    return {
      entered_password: '',
      entered_new_password: '',
    }
  },
  methods: {
    handlePassChange() {
      const oldPass = this.entered_password
      const newPass = this.entered_new_password
      var val = localStorage.getItem("user_info")
      var object = JSON.parse(val)

      router.push('/ongoing-matches')



    },
  }
}
</script>

<style scoped>

</style>