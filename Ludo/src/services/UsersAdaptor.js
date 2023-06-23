import { User } from "../models/User.js";

export class UsersAdaptor {
  resourcesUrl;

  constructor(resourcesUrl) {
    this.resourcesUrl = resourcesUrl;
    console.log("Created usersAdaptor for " + resourcesUrl);
  }

  async fetchJson(url, options = null) {
    let response = await fetch(url, options);
    if (response.ok) {
      return await response.json();
    } else {
      // response body provides the http-error information
      console.log(response, !response.bodyUsed ? await response.text() : "");
      return null;
    }
  }

  async asyncFindById(id) {
    const user = await this.fetchJson(this.resourcesUrl + "/" + id);
    return User.copyConstructor(user);
  }

  async asyncFindAllUsers() {
    console.log("OrdersAdaptor.asyncFindAll()...");
    const users = await this.fetchJson(this.resourcesUrl);
    return users?.map((s) => User.copyConstructor(s));
  }

  async asyncSave(users) {
    console.log("OrderAdaptor.asyncSave()...");
    const user = await this.fetchJson(this.resourcesUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: users,
    });
    return User.copyConstructor(user);
  }

  // async findUserByEmail(email){
  //   console.log("UsersAdaptor.findUserByEmail")
  //   const user = this.fetchJson(this.resourcesUrl + "/ForgotPassword/" + email, {
  //     method: "GET",
  //     headers: {
  //       "Content-Type": "application/json",
  //     }
  //   });
  //   return User.copyConstructor(user)
  // }

  async forgotPassword(email) {
    console.log("UsersAdaptor.forgotPassword")
    const user = this.fetchJson(this.resourcesUrl + "/ForgotPassword/" + email ,{
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      }
    });
    return User.copyConstructor(user)
  }

  async changePassword(userId, newPassword){
      console.log("LobbyAdaptor.changePass()...");
      const user = this.fetchJson(this.resourcesUrl + "/changePassword/" + userId + "/" + newPassword, {
        method: "PUT"
      });
      return User.copyConstructor(user);
    }
}
