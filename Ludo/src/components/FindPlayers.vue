<template>
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
  name: "FindPlayers",
  components: {FriendTemplate},
  inject: ["registerService", "sessionService"],
  data() {
    return {
      foundFriend: null,
      input: null
    };
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

</style>