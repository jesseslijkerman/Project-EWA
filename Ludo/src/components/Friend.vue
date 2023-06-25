<template>
  <div class="nearby-user">
    <div class="row">
      <div class="col-md-2 col-sm-2">
        <img :src=picture alt="user" class="profile-photo-lg">
      </div>
      <div class="col-md-7 col-sm-7">
        <h5><a href="#" class="profile-link">{{username}}</a></h5>
        <p class="text-muted">{{countryCode}}</p>
      </div>
      <div class="col-md-3 col-sm-3">
        <button class="btn btn-primary pull-right" v-on:click="addFriend" v-if="button == 'add'">Add Friend</button>
        <button class="btn btn-primary pull-right" v-on:click="invitePlayer" v-else-if="button == 'invite'">Invite</button>
        <button class="btn btn-primary pull-right remove" v-on:click="removeFriend" v-else>Remove</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FriendTemplate",
  inject: ["registerService", "sessionService"],
  props: {
    id: Number,
    username: String,
    email: String,
    countryCode: String,
    picture: String
  },
  data() {
    return {
      genericPicture: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
      userId: null,
      button: null
    };
  },
  created() {
    this.userId = this.sessionService.currentAccount.id
    this.checkUrl();
    console.log(this.button)
  },
  methods: {
    hasPicture(){
      return this.picture !== null;
    },
    async addFriend(){
      await this.registerService.asyncAddFriend(this.userId, this.id)
    },
    async invitePlayer(){
      const matchId = window.location.pathname.split('/').pop();
      await this.registerService.asyncInvitePlayer(this.userId, this.id, matchId)
    },
    async removeFriend(){
      await this.registerService.asyncRemoveFriend(this.userId, this.id)
    },
    checkUrl(){
      if (window.location.href.indexOf("find-players") != -1){
        this.button = "add"
      } else if (window.location.href.indexOf("match") != -1){
        this.button = "invite"
      }
    }
  }
}
</script>

<style scoped>
.remove{
  background-color: red;
  border-color: red;
}

</style>