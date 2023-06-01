<template>
  <div class="container">
    <form class="loginForm" @submit.prevent="handleLogin">



      <div class="form-outline mb-4">
        <label class="form-label">New password</label>
        <input type="text" class="form-control form-control-lg" v-model="entered_new_password"
               placeholder="Enter your new password..." required/>
      </div>

      <!-- Submit button -->
      <div class="text-center">
        <button type="button" class="btn btn-primary btn-block mb-4" v-on:click="handlePassChange">Change password</button>
      </div>


    </form>
  </div>
</template>

<script>
import router from "../router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  inject: ["registerService"],
  name: "ResetPassword",
  data() {
    return {
      entered_new_password: '',
    }
  },
  methods: {
    async handlePassChange() {



      const newPass = this.entered_new_password
      var val = localStorage.getItem("user_info")
      var object = JSON.parse(val)

      router.push('/ongoing-matches')

      await this.registerService.changePass()

    },
  }
}
</script>

<style scoped>

</style>