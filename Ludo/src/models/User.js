export class User {
  id;

  constructor(id) {
    this.id = id;
  }

  static copyConstructor(user) {
    if (user == null) {
      return null;
    }

    // Copies properties from selected order to a new order
    return Object.assign(new User(), user);
  }
}
