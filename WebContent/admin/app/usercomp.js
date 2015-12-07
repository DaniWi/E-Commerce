var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var angular2_1 = require('angular2/angular2');
var http_1 = require('angular2/http');
var UserComponent = (function () {
    function UserComponent(http) {
        this.http = http;
        this.getData();
    }
    UserComponent.prototype.onDelete = function (usr) {
        var _this = this;
        var index = this.users.indexOf(usr);
        this.users.splice(index, 1);
        this.http.delete('../rest/accounts/' + usr.id)
            .map(function (res) { return res.json(); })
            .subscribe(function (data) { return _this.response = data; }, function (err) { return _this.logError(err); }, function () { return console.log('Deletion complete'); });
    };
    UserComponent.prototype.onSave = function (usr) {
        var _this = this;
        var body = "name=" + usr.name + "&rights=" + usr.rights;
        var headers = new http_1.Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
        this.http.put('../rest/accounts/' + usr.id, body, {
            headers: headers
        })
            .map(function (res) { return res.json(); })
            .subscribe(function (data) { return _this.response; }),
            function (err) { return _this.logError(err); },
            function () { return console.log('Put complete'); };
        ;
    };
    UserComponent.prototype.getData = function () {
        var _this = this;
        this.http.get('http://localhost:8080/E-Commerce/rest/accounts')
            .map(function (res) { return res.json(); })
            .subscribe(function (data) { return _this.users = data; }, function (err) { return _this.logError(err); }, function () { return console.log('Data complete'); });
    };
    UserComponent.prototype.logError = function (err) {
        console.log(err);
    };
    UserComponent = __decorate([
        angular2_1.Component({
            selector: 'users',
            templateUrl: 'app/html/user.html',
            viewProviders: [http_1.HTTP_PROVIDERS],
            directives: [angular2_1.FORM_DIRECTIVES, angular2_1.CORE_DIRECTIVES],
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], UserComponent);
    return UserComponent;
})();
exports.UserComponent = UserComponent;
angular2_1.bootstrap(UserComponent);
//# sourceMappingURL=usercomp.js.map