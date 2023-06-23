<template>
  <body class="border-top-wide border-primary d-flex flex-column theme-dark">
  <div class="container py-4">
    <div class="text-center mb-4 logo">
      <a href="." class="navbar-brand"><img src="/assets/Ludo-Logo.jpg" alt="Responsive image"></a>
    </div>
    <form class="card card-md" @submit.prevent="handleSubmit">
      <h3>Forgot Password?</h3>

      <div class="form">
        <label>New Password</label>
        <input type="password" class="form-control" v-model="password" placeholder="New Password">
      </div>
      <div class="form">
        <label>Confirm New Password</label>
        <input type="password" class="form-control" v-model="confirmPassword" placeholder="Confirm New Password">
      </div>
      <div class="alert alert-danger">
        The passwords do not match!
      </div>
      <div class="d-flex flex-row">
        <button class="btn btn-primary">Submit</button>
        <button class="btn btn-secondary cancel" @click="backToLogin()">Back</button>
      </div>
    </form>
  </div>
  </body>
</template>

<script>
export default {
  name: "ResetPasswordWithToken",
  inject: ["registerService"],
  data(){
    return{
      password:"",
      confirmPassword: ""
      }
  },
  methods:{
    async handleSubmit(){
      const newPassword = this.password;
      const confirmationPassword = this.confirmPassword;
      if (newPassword == confirmationPassword){
        await this.registerService.changePassword(, this.password)
      } else {

      }
    }
  }
}
</script>

<style scoped>

</style>