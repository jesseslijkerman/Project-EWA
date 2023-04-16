<template>
  <div class="container">
    <form @submit.prevent="handleSumbit">
      <!-- Name input -->
      <div class="form-outline mb-4">
        <label class="form-label">Name</label>
        <input type="text" class="form-control" v-model="name"
               placeholder="Name"/>
      </div>

      <!-- Username input -->
      <div class="form-outline mb-4">
        <label class="form-label">Username</label>
        <input type="text" class="form-control" v-model="username"
               placeholder="Username"/>
      </div>

      <!-- Email input -->
      <div class="form-outline mb-4">
        <label class="form-label">Email address</label>
        <input type="text" class="form-control" v-model="register_email"
               placeholder="Email address"/>
      </div>

      <!-- Password input -->
      <div class="form-outline mb-4">
        <label class="form-label">Password</label>
        <input type="text" class="form-control" v-model="password"
               placeholder="Password"/>
      </div>

      <!-- ConfirmPassword input -->
      <div class="form-outline mb-4">
        <label class="form-label">Confirm password</label>
        <input type="text" class="form-control" v-model="password_confirm"
               placeholder="Confirm password"/>
      </div>

      <!-- Submit button -->
      <div class="text-center">
        <button type="button" class="btn btn-primary btn-block mb-4" v-on:click="handleSumbit">Sign up</button>
      </div>

      <p class="text-center text-muted mt-5 mb-0">Have already an account? <a v-on:click="handleRedirect"
                                                                              href="#"
                                                                              class="fw-bold text-body">Login
        here</a></p>

    </form>
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "Register",
  data() {
    return {
      name: '',
      username: '',
      register_email: '',
      password: '',
      password_confirm: ''
    }
  },
  methods: {
    async handleSumbit() {
      const result = {
        name: this.name,
        username: this.username,
        register_email: this.register_email,
        password: this.password,
        password_confirm: this.password_confirm,
      }
      localStorage.setItem("user_info", JSON.stringify(result))
      await router.push("/login")
    },
    handleRedirect() {
      router.push('/login')
    },
  }
}
</script>

<style scoped>
</style>