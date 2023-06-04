export class User {
  email;
  password;
  countryCode;
  username;

  constructor(email, password, countryCode, username) {
    this.email = email;
    this.password = password;
    this.countryCode = countryCode;
    this.username = username;
  }

  static copyConstructor(user) {
    if (user == null) {
      return null;
    }

    // Copies properties from selected order to a new order
    return Object.assign(new User(), user);
  }
}
