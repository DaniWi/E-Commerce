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
var ItemComponent = (function () {
    function ItemComponent(http) {
        this.http = http;
        this.getData();
    }
    ItemComponent.prototype.onDelete = function (item) {
        var _this = this;
        var index = this.items.indexOf(item);
        this.items.splice(index, 1);
        this.http.delete('http://localhost:8080/E-Commerce/rest/category/' + usr.id)
            .map(function (res) { return res.json(); })
            .subscribe(function (data) { return _this.response = data; }, function (err) { return _this.logError(err); }, function () { return console.log('Deletion complete'); });
    };
    ItemComponent.prototype.getData = function () {
        var _this = this;
        this.http.get('http://localhost:8080/E-Commerce/rest/items')
            .map(function (res) { return res.json(); })
            .subscribe(function (data) { return _this.items = data; }, function (err) { return _this.logError(err); }, function () { return console.log('Items complete'); });
    };
    ItemComponent.prototype.logError = function (err) {
        console.log(err);
    };
    ItemComponent = __decorate([
        angular2_1.Component({
            selector: 'items',
            templateUrl: 'app/html/item.html',
            viewProviders: [http_1.HTTP_PROVIDERS],
            directives: [angular2_1.FORM_DIRECTIVES, angular2_1.CORE_DIRECTIVES],
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], ItemComponent);
    return ItemComponent;
})();
exports.ItemComponent = ItemComponent;
angular2_1.bootstrap(ItemComponent);
//# sourceMappingURL=itemcomp.js.map