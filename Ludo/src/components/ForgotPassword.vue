<template>
  <body class="border-top-wide border-primary d-flex flex-column theme-dark">
    <div class="container py-4">
      <div class="text-center mb-4 logo">
        <a href="." class="navbar-brand"><img src="/assets/Ludo-Logo.jpg" alt="Responsive image"></a>
      </div>
      <form class="card card-md" @submit.prevent="handleSubmit">
        <h3>Forgot Password?</h3>

        <div class="form">
          <label>Insert you email:</label>
          <input type="email" class="form-control" v-model="email" placeholder="Email">
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
import router from "@/router";

export default {
  name: "ForgotPassword",
  inject: ["registerService"],
  data() {
    return{
      email: "",
    }
  },
  methods: {
    async handleSubmit(){
      const response = this.email;
      console.log(response)
      await this.registerService.findUserByEmail(response)
      if(this.registerService.findUserByEmail(response) == null){
        alert("There is no account with this email!")
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
</style>