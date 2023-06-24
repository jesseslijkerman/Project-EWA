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
      <div class="form password">
        <label>Confirm New Password</label>
        <input type="password" class="form-control" v-model="confirmPassword" placeholder="Confirm New Password">
      </div>
      <div class="alert alert-danger" v-if="!passwordsMatch">
        The passwords do not match!
      </div>
      <div class="d-flex flex-row">
        <button class="btn btn-primary">Submit</button>
        <button class="btn btn-secondary cancel" @click="backToLogin">Back</button>
      </div>
    </form>
  </div>
  </body>
</template>

<script>
import router from "@/router";

export default {
  name: "ResetPasswordWithToken",
  inject: ["registerService"],
  data(){
    return{
      password:"",
      confirmPassword: "",
      passwordsMatch: true,
    }
  },
  methods:{
    async handleSubmit(){
      const newPassword = this.password;
      const confirmationPassword = this.confirmPassword;
      const token = window.location.pathname.split('/').pop();
      if (newPassword == confirmationPassword){
        await this.registerService.resetPassword(token, this.password)
        router.push("/login")
      } else {
        this.passwordsMatch = false;
      }
    },
    backToLogin(){
      router.push("/login")
    }
  }
}
</script>

<style scoped>
  .logo{
    margin-top: 1.5rem;
  }

  .container{
    max-width: 500px;
    padding: 10px;
  }

  form{
    padding: 2rem;
  }

  .password{
    margin-top: 20px;
  }

  .alert{
    margin-top: 20px;
  }

</style>