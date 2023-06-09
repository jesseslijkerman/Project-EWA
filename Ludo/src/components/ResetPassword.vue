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

export default {
  inject: ["registerService", "sessionService"],
  name: "ResetPassword",
  data() {
    return {
      entered_new_password: '',
    }
  },
  methods: {
    async handlePassChange() {
      if (confirm("Do you want to reset your password?") == true){
        await this.registerService.changePassword(this.sessionService.currentAccount.id, this.entered_new_password)
      } else {
        return
      }
    },
  }
}
</script>

<style scoped>
  .form-label {
    font-size: 26px;
  }

</style>