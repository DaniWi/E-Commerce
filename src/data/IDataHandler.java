package data;

import java.util.Collection;

public interface IDataHandler {

	void closeDatabaseConnection() throws IllegalStateException;

	Item getItemByID(int id) throws IllegalArgumentException;

	Comment getCommentByID(int id) throws IllegalArgumentException;

	User getUserByID(int id) throws IllegalArgumentException;

	Category getCategoryByID(int id) throws IllegalArgumentException;

	Category getCategoryByName(String name) throws IllegalArgumentException;

	Category createCategory(String name) throws IllegalStateException;

	Item createItem(String title, String description, double price, int authorID, int categoryID)
			throws IllegalStateException;

	Comment createComment(int itemID, String text, int authorID) throws IllegalStateException;

	User createUser(String name, String email, String password, String rights) throws IllegalStateException;

	Item changeItem(int itemID, String title, String description, double price, int authorID, int categoryID)
			throws IllegalStateException;

	Comment changeComment(int commentID, int itemID, String text, int authorID) throws IllegalStateException;

	Category changeCategory(int categoryID, String name) throws IllegalStateException;

	void deleteItem(int itemID) throws IllegalArgumentException;

	void deleteComment(int commentID) throws IllegalArgumentException;

	void deleteUser(int userID) throws IllegalArgumentException;

	Collection<Category> getAllCategories() throws IllegalStateException;

	Collection<Item> getAllItemsFromCategory(int categoryID) throws IllegalStateException;

	Collection<Comment> getAllCommentsFromItem(int itemID) throws IllegalArgumentException, IllegalStateException;

	User getUserLogin(String name, String password) throws IllegalStateException;

}