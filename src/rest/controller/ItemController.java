package rest.controller;

import java.util.Collection;

import data.DataHandler;
import data.Item;
import data.User;

public class ItemController {

	public static Item getItem(int itemIndex) {
		return DataHandler.getInstance().getItemByID(itemIndex);
	}

	public static String getItemAsHtml(int itemIndex) {

		Item item = DataHandler.getInstance().getItemByID(itemIndex);

		return HtmlUtility.HtmlWrap(HtmlUtility.itemToHtml(item));
	}

	public static String changeItem(int itemIndex, String title, String description, String username, String password) {

		DataHandler dh = DataHandler.getInstance();

		if (dh.getUserLogin(username, password).getRights().equals("admin")) {
			Item item = dh.getItemByID(itemIndex);
			title = (title == null || title.equals("")) ? item.getTitle() : title;
			description = (description == null || description.equals("")) ? item.getDescription() : description;

			dh.changeItem(itemIndex, title, description, item.getAuthorID(), item.getCategoryID());

			return HtmlUtility.HtmlWrap(HtmlUtility.itemToHtml(dh.getItemByID(itemIndex)));
		}

		return HtmlUtility.HtmlWrap("No permission to change items!");
	}

	public static String deleteItem(int itemIndex, String username, String password) {
		DataHandler dh = DataHandler.getInstance();

		if (dh.getUserLogin(username, password).getRights().equals("admin")) {
			dh.deleteItem(itemIndex);

			return HtmlUtility.HtmlWrap("Successfully deleted the item!");
		}

		return HtmlUtility.HtmlWrap("No permission to delete items!");
	}

	public static String newItem(String title, String description, String category, String username, String password) {
		DataHandler dh = DataHandler.getInstance();
		User user = dh.getUserLogin(username, password);

		if (user.getRights().equals("admin")) {
			if (title == null || title.equals("")) {
				return HtmlUtility.HtmlWrap("New Items have to have a title!");
			}
			description = (description == null) ? "empty description" : description;

			int id = dh.createItem(title, description, user.getId(), dh.getCategoryByName(category).getId()).getId();

			return HtmlUtility.HtmlWrap(HtmlUtility.itemToHtml(dh.getItemByID(id)));
		}

		return HtmlUtility.HtmlWrap("No permission to create new items!");
	}

	public static Collection<Item> getAllItemsOfCategory(String category) {
		DataHandler dh = DataHandler.getInstance();
		return dh.getAllItemsFromCategory(dh.getCategoryByName(category).getId());
	}

	public static String getAllItemsOfCategoryAsHtml(String category) {
		String html = "";

		DataHandler dh = DataHandler.getInstance();
		Collection<Item> items = dh.getAllItemsFromCategory(dh.getCategoryByName(category).getId());
		for (Item item : items) {
			html += HtmlUtility.itemToHtml(item);
		}

		return HtmlUtility.HtmlWrap(html);
	}
}
