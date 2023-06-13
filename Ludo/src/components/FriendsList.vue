<template>
  <div class="container">
    <div class="row">
      <div class="col-md-8">
        <div class="people-nearby" v-for="friend in friends" :key="friend.id">
          <FriendTemplate v-bind="friend"></FriendTemplate>
        </div>
      </div>
    </div>
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
      currentAccount: null
    };
  },
  async created(){
    this.currentAccount = this.sessionService.currentAccount
    this.friends = await this.registerService.asyncFindFriendsById(this.currentAccount.id)
    console.log(this.friends)
  },
}
</script>

<style scoped>

</style>