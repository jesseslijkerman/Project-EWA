<template>
  <div class="container">
    <div class="row">
      <div class="col-md-8">
        <div class="people-nearby" v-for="friend in friends" :key="friend.id">
          <FriendTemplate class="friend" v-bind="friend"></FriendTemplate>
        </div>
      </div>
    </div>
  </div>
  <div class="container">
    <div>
      <input v-model="input" type="text" placeholder="Search"/>
      <button class="btn btn-primary pull-right" v-on:click="findFriend">Find</button>
    </div>
    <FriendTemplate class="friend" v-bind="foundFriend" v-show="foundFriend !== null"></FriendTemplate>


  </div>
</template>

<script>
import FriendTemplate from "./Friend.vue";
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