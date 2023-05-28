<template>
  <div class="container">
    <form @submit.prevent="handleSumbit">
      <!-- Username input -->
      <div class="form-outline mb-4">
        <label class="form-label">Username</label>
        <input
          type="text"
          class="form-control"
          v-model="username"
          placeholder="Username"
        />
      </div>

      <!-- Email input -->
      <div class="form-outline mb-4">
        <label class="form-label">Email address</label>
        <input
          type="text"
          class="form-control"
          v-model="email"
          placeholder="Email address"
        />
      </div>

      <!-- countryCode input -->
      <div class="form-outline mb-4">
        <label class="form-label">Country</label>
        <input
          type="text"
          class="form-control"
          v-model="countryCode"
          placeholder="country"
        />
      </div>

      <!-- picture input -->
      <div class="form-outline mb-4">
        <label class="form-label">Picture</label>
        <input
          type="file"
          class="form-control"
          @change="onFileChange"
          placeholder="Profile picture"
        />
      </div>

      <!-- Password input -->
      <div class="form-outline mb-4">
        <label class="form-label">Password</label>
        <input
          type="password"
          class="form-control"
          v-model="password"
          placeholder="Password"
        />
      </div>

      <!-- ConfirmPassword input -->
      <div class="form-outline mb-4">
        <label class="form-label">Confirm password</label>
        <input
          type="password"
          class="form-control"
          v-model="password_confirm"
          placeholder="Confirm password"
        />
      </div>

      <!-- Submit button -->
      <div class="text-center">
        <button
          type="button"
          class="btn btn-primary btn-block mb-4"
          v-on:click="handleSumbit"
        >
          Sign up
        </button>
      </div>

      <p class="text-center text-muted mt-5 mb-0">
        Have already an account?
        <a v-on:click="handleRedirect" href="#" class="fw-bold text-body"
          >Login here</a
        >
      </p>
    </form>
  </div>
</template>

<script>
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Register",
  inject: ["registerService"],
  data() {
    return {
      username: "",
      email: "",
      countryCode: "",
      picture: "",
      password: "",
      password_confirm: "",
    };
  },
  methods: {
    async handleSumbit() {
      const result = {
        countryCode: this.countryCode,
        email: this.email,
        password: this.password,
        picture: this.picture,
        username: this.username,
      };
      console.log(JSON.stringify(result))
      await this.registerService.asyncSave(JSON.stringify(result));
      await router.push("/login");
    },
    handleRedirect() {
      router.push("/login");
    },
    onFileChange(e) {
      var files = e.target.files || e.dataTransfer.files;
      if (!files.length) return;
      this.createImage(files[0]);
    },
    createImage(file) {
      var picture = new Image();
      var reader = new FileReader();
      var vm = this;

      reader.onload = (e) => {
        vm.picture = e.target.result;
      };
      reader.readAsDataURL(file);
    },
  },
};
</script>

<style scoped></style>
