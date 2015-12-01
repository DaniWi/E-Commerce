package rest.controller;

import java.util.Collection;

import data.Category;
import data.DataHandler;
import data.IDataHandler;

public class CategoryController {
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

	public Collection<Category> getAllCategories() {
		return databaseHandler.getAllCategories();
	}
	
	public void deleteCategory(String category) {
		databaseHandler.deleteCategory(category);
	}
	
	public String newCategory(String category, String username, String password) {
		if (databaseHandler.getUserLogin(username, password).getRights().equals("admin")) {
			// only Administators are allowed to create new Categories
			databaseHandler.createCategory(category);

			return htmlUtility.HtmlWrap("Successfully created category " + category);
		}

		return htmlUtility.HtmlWrap("No permission to create new categories!");
	}

	public String changeCategory(String category, String newName, String username, String password) {
		if (databaseHandler.getUserLogin(username, password).getRights().equals("admin")) {
			// only Administators are allowed to delete Categories

			if (databaseHandler.getCategoryByName(category) == null) {
				// category does not exist
				return htmlUtility.HtmlWrap("Category " + category + " does not exits");
			}
			databaseHandler.changeCategory(databaseHandler.getCategoryByName(category).getId(), newName);

			return htmlUtility.HtmlWrap("Sucessfully changed category name '" + category + "' to '" + newName);
		}

		return htmlUtility.HtmlWrap("No permission to change categories!");
	}

}
