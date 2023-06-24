<template>
  <div class="container">
    <div class="row">
      <div class="col-md-8">
        <div class="people-nearby" v-for="friend in friends" :key="friend.id">
          <FriendTemplate class="friend" v-bind="friend"></FriendTemplate>
        </div>
      </div>
    </div>
    <button class="btn btn-primary pull-right" v-on:click="handleRedirect">Find friends</button>
  </div>
</template>

<script>
import FriendTemplate from "./Friend.vue";
import router from "../router";
export default {
  name: "FriendsList",
  components: {FriendTemplate},
  inject: ["registerService", "sessionService"],
  data() {
    return {
      friends: [],
      currentAccount: null,
      foundFriend: null,
      input: null
    };
  },
  async created(){
    this.currentAccount = this.sessionService.currentAccount
    this.friends = await this.registerService.asyncFindFriendsById(this.currentAccount.id)
    console.log(this.friends)
  },
  methods:{
    async findFriend(){
      this.foundFriend = await this.registerService.asyncFindByNameOrEmail(this.input)
      console.log(this.input)
      console.log(this.foundFriend)
    },
    handleRedirect(){
      router.push("/find-players");
    }
  }

}
</script>

<style scoped>
.container {
  max-width: 50%;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #121212;
  color: #fff;
  border-radius: 1rem;
  box-shadow: 0px 2px 8px rgba(0,0,0,0.3);
}

.friend{
  padding-top: 20px;
}

</style>