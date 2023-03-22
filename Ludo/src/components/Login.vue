<template>
  <div class="container">
    <form @submit.prevent="handleLogin">
      <!-- Email input -->
      <div class="form-outline mb-4">
        <label class="form-label">Email address</label>
        <input type="text" class="form-control form-control-lg" v-model="entered_email"
               placeholder="Email address" required/>
      </div>

      <!-- Password input -->
      <div class="form-outline mb-4">
        <label class="form-label">Password</label>
        <input type="text" class="form-control form-control-lg" v-model="entered_password"
               placeholder="Password" required/>
      </div>

      <!-- 2 column grid layout for inline styling -->
      <div class="row mb-4">
        <div class="col d-flex justify-content-center">
          <!-- Checkbox -->
          <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="form2Example31" checked/>
            <label class="form-check-label" for="form2Example31"> Remember me </label>
          </div>
        </div>

        <div class="col text-center">
          <!-- Simple link -->
          <a href="#!">Forgot password?</a>
        </div>
      </div>

      <!-- Submit button -->
      <div class="text-center">
        <button type="button" class="btn btn-primary btn-block mb-4" v-on:click="handleLogin">Log in</button>
      </div>

      <!-- Register buttons -->
      <div class="text-center">
        <p>Not a member? <a v-on:click="handleRedirect" href="#">Register</a></p>
      </div>
    </form>
  </div>
</template>

<script>
import router from "../router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      entered_email: '',
      entered_password: ''
    }
  },
  methods: {
    handleLogin() {
      const email = this.entered_email
      const password = this.entered_password
      var val = localStorage.getItem("user_info")
      var object = JSON.parse(val)

      router.push('/ongoing-matches')

      if (email === object.register_email && password === object.password) {
        router.push("/")
      } else {
        console.log("no")
      }
    },
    handleRedirect() {
      router.push('/register')
    },
  }
}
</script>

<style scoped>

</style>