<template>
  <div class="container">
    <h1 class="title">Reset your password</h1>
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
.title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #007bff;
}

.container {
  max-width: 20%;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #121212;
  color: #fff;
  border-radius: 1rem;
  box-shadow: 0px 2px 8px rgba(0,0,0,0.3);
}

label {
  font-size: 25px;
  margin-top: 30px;
}
  .form-label {
    font-size: 26px;
  }

</style>