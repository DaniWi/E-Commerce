/**
 * Created by Dani on 01.12.2015.
 */
var User = (function () {
    function User(id, name, email, password, rights, joinedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rights = rights;
        this.joinedDate = joinedDate;
    }
    return User;
})();
exports.User = User;
//# sourceMappingURL=User.js.map