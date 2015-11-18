package rest.controller;

import data.DataHandler;

public class CategoryController {

	public static String newCategory(String category, String username, String password) {
		if (DataHandler.getInstance().getUserLogin(username, password).getRights().equals("admin")) {
			// only Administators are allowed to create new Categories
			DataHandler.getInstance().createCategory(category);

			return HtmlUtility.HtmlWrap("Successfully created category" + category);
		}

		return HtmlUtility.HtmlWrap("No permission to create new categories!");
	}

	public static String changeCategory(String category, String newName, String username, String password) {
		DataHandler dh = DataHandler.getInstance();
		if (dh.getUserLogin(username, password).getRights().equals("admin")) {
			// only Administators are allowed to delete Categories

			if (dh.getCategoryByName(category) == null) {
				// category does not exist
				return HtmlUtility.HtmlWrap("Category " + category + "does not exits");
			}
			dh.changeCategory(dh.getCategoryByName(category).getId(), newName);

			return HtmlUtility.HtmlWrap("Sucessfully changed category name '" + category + "' to '" + newName);
		}

		return HtmlUtility.HtmlWrap("No permission to change categories!");
	}

}
