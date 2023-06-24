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
        <button class="btn btn-primary pull-right" v-on:click="addFriend">Add Friend</button>
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
      genericPicture: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
    };
  },
  methods: {
    hasPicture(){
      return this.picture !== null;
    },
    async addFriend(){
      await this.registerService.asyncAddFriend(this.sessionService.currentAccount.id, this.id)

    }
  }
}
</script>

<style scoped>

</style>