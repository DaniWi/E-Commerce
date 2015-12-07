package rest.controller;

import java.util.Collection;

import data.DataHandler;
import data.IDataHandler;
import data.Item;
import data.User;

public class ItemController {

	IDataHandler databaseHandler;
	HtmlUtility htmlUtility = new HtmlUtility();

	public IDataHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(IDataHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
		this.htmlUtility.setDatabaseHandler(databaseHandler);
	}

	public void setDatabaseHandler() {
		this.databaseHandler = DataHandler.getInstance();
		this.htmlUtility.setDatabaseHandler(databaseHandler);
	}

	public Item getItem(int itemIndex) {
		return databaseHandler.getItemByID(itemIndex);
	}

	public String getItemAsHtml(int itemIndex) {

		Item item = databaseHandler.getItemByID(itemIndex);

		return htmlUtility.HtmlWrap(htmlUtility.itemToHtml(item));
	}

	public String changeItem(int itemIndex, String title, String description, double price, String username,
			String password) {

		IDataHandler dh = databaseHandler;

		if (dh.getUserLogin(username, password).getRights().equals("admin")) {
			Item item = dh.getItemByID(itemIndex);
			title = (title == null || title.equals("")) ? item.getTitle() : title;
			description = (description == null || description.equals("")) ? item.getDescription() : description;
			price = (price < 0) ? item.getPrice() : price;

			dh.changeItem(itemIndex, title, description, price, item.getAuthorID(), item.getCategoryID());

			return htmlUtility.HtmlWrap(htmlUtility.itemToHtml(dh.getItemByID(itemIndex)));
		}

		return htmlUtility.HtmlWrap("No permission to change items!");
	}

	public String deleteItem(int itemIndex, String username, String password) {
		IDataHandler dh = databaseHandler;

		if (dh.getUserLogin(username, password).getRights().equals("admin")) {
			dh.deleteItem(itemIndex);

			return htmlUtility.HtmlWrap("Successfully deleted the item!");
		}

		return htmlUtility.HtmlWrap("No permission to delete items!");
	}

	public String newItem(String title, String description, double price, String category, String username,
			String password) {
		IDataHandler dh = databaseHandler;
		User user = dh.getUserLogin(username, password);

		if (user.getRights().equals("admin")) {
			if (title == null || title.equals("")) {
				return htmlUtility.HtmlWrap("New Items have to have a title!");
			}
			description = (description == null) ? "empty description" : description;
			price = Math.max(0, price);

			int id = dh.createItem(title, description, price, user.getId(), dh.getCategoryByName(category).getId())
					.getId();

			return htmlUtility.HtmlWrap(htmlUtility.itemToHtml(dh.getItemByID(id)));
		}

		return htmlUtility.HtmlWrap("No permission to create new items!");
	}

	public Collection<Item> getAllItemsOfCategory(String category) {
		IDataHandler dh = databaseHandler;
		return dh.getAllItemsFromCategory(dh.getCategoryByName(category).getId());
	}

	public Collection<Item> getAllItems() {
		IDataHandler dh = databaseHandler;
		return dh.getAllItems();
	}

	public String getAllItemsOfCategoryAsHtml(String category) {
		String html = "";

		IDataHandler dh = databaseHandler;
		Collection<Item> items = dh.getAllItemsFromCategory(dh.getCategoryByName(category).getId());
		for (Item item : items) {
			html += htmlUtility.itemToHtml(item);
		}

		return htmlUtility.HtmlWrap(html);
	}
}
