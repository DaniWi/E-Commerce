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
var User_1 = require('./User');
var AppComponent = (function () {
    function AppComponent() {
        this.users = [
            new User_1.User("Daniel", true),
            new User_1.User("Lukas", true),
            new User_1.User("Martin", false)
        ];
    }
    AppComponent.prototype.onSelect = function (usr) {
        this.selectedUser = usr;
    };
    AppComponent.prototype.getSelectedClass = function (usr) {
        return { 'selected': usr === this.selectedUser };
    };
    AppComponent = __decorate([
        angular2_1.Component({
            selector: 'my-app',
            template: "\n        <h2>My Heroes</h2>\n        <ul class=\"heroes\">\n            <li *ng-for=\"#usr of users\" (click)=\"onSelect(usr)\" [ng-class]=\"getSelectedClass(hero)\">\n            <span class=\"badge\">{{usr.name}}</span> {{usr.admin}}\n            </li>\n        </ul>\n        <div *ng-if=\"selectedUser\">\n            <label>name: </label>\n            <div><input [(ng-model)]=\"selectedUser.name\" placeholder=\"name\"></div>\n        </div>",
            directives: [angular2_1.FORM_DIRECTIVES, angular2_1.CORE_DIRECTIVES],
            styles: ["\n  .heroes {list-style-type: none; margin-left: 1em; padding: 0; width: 10em;}\n  .heroes li { cursor: pointer; position: relative; left: 0; transition: all 0.2s ease; }\n  .heroes li:hover {color: #369; background-color: #EEE; left: .2em;}\n  .heroes .badge {\n    font-size: small;\n    color: white;\n    padding: 0.1em 0.7em;\n    background-color: #369;\n    line-height: 1em;\n    position: relative;\n    left: -1px;\n    top: -1px;\n  }\n  .selected { background-color: #EEE; color: #369; }\n  "],
        }), 
        __metadata('design:paramtypes', [])
    ], AppComponent);
    return AppComponent;
})();
exports.AppComponent = AppComponent;
angular2_1.bootstrap(AppComponent);
//# sourceMappingURL=app.js.map