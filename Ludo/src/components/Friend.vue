<template>
  <div class="nearby-user">
    <div class="row">
      <div class="col-md-2 col-sm-2">
        <img :src=picture alt="user" class="profile-photo-lg">
      </div>
      <div class="col-md-7 col-sm-7">
        <h5><a href="#" class="profile-link">{{username}}</a></h5>
        <p>Software Engineer</p>
        <p class="text-muted">500m away</p>
      </div>
      <div class="col-md-3 col-sm-3">
        <button class="btn btn-primary pull-right">Add Friend</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FriendTemplate",
  props: {
    id: Number,
    username: String,
    email: String,
    countryCode: String,
    picture: String
  },
  created() {
    if (this.picture == null){
      this.picture =
    }
  }
}
</script>

<style scoped>

</style>