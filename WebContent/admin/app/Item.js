/**
 * Created by Dani on 01.12.2015.
 */
var Item = (function () {
    function Item(id, title, description, price, authorID, categoryID, creationDate, altertionDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.authorID = authorID;
        this.categoryID = categoryID;
        this.creationDate = creationDate;
        this.altertionDate = altertionDate;
    }
    return Item;
})();
exports.Item = Item;
//# sourceMappingURL=Item.js.map