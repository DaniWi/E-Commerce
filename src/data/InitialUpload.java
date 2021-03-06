package data;

import java.util.Collection;

public class InitialUpload {

	public static void main(String[] args) {
		// get handler
		IDataHandler handler = DataHandler.getInstance();

		int userID1 = handler.createUser("Daniel", "daniel.witsch@gmx.at", "Daniel", "admin", "Innrain 52d, Innsbruck", 47.264080, 11.384550).getId();
		int userID2 = handler.createUser("Lukas", "test@gmx.at", "Lukas", "user", "Boltzmannstra�e 3, M�nchen", 48.262298, 11.669776).getId();

		int catID1 = handler.createCategory("Books").getId();
		int catID2 = handler.createCategory("Clothing").getId();
		int catID3 = handler.createCategory("Electronics").getId();
		int catID4 = handler.createCategory("Sports").getId();

		int itemID1 = handler.createItem("Java ist eine Insel", "Das umfassende Handbuch zu Java", 24.99, userID1, catID1).getId();
		int itemID2 = handler.createItem("Lineare Algebra", "Grundlagen Mathematik", 34.99, userID1, catID1).getId();
		int itemID3 = handler.createItem("T-Shirt", "rotes T-Shirt", 9.99, userID1, catID2).getId();
		int itemID4 = handler.createItem("Pullover", "gr�ner Rollkragenpullover", 24.99, userID1, catID2).getId();
		int itemID5 = handler.createItem("Samsung Galaxy S6", "neues Modell der Galaxy-Reige", 449.99, userID2, catID3).getId();
		int itemID6 = handler.createItem("Sony LED TV", "42 Zoll LED Monitor", 999.99, userID2, catID3).getId();

		int commentID1 = handler.createComment(itemID1, "Comment1", userID1).getId();
		int commentID2 = handler.createComment(itemID1, "Comment2", userID2).getId();
		int commentID3 = handler.createComment(itemID1, "Comment3", userID1).getId();
		int commentID4 = handler.createComment(itemID2, "Comment4", userID1).getId();
		int commentID5 = handler.createComment(itemID2, "Comment5", userID2).getId();
		int commentID6 = handler.createComment(itemID2, "Comment6", userID2).getId();

		Collection<Item> items1 = handler.getAllItemsFromCategory(catID1);
		Collection<Item> items2 = handler.getAllItemsFromCategory(catID2);
		Collection<Item> items3 = handler.getAllItemsFromCategory(123124324);

		Collection<Comment> comments1 = handler.getAllCommentsFromItem(itemID1);
		Collection<Comment> comments2 = handler.getAllCommentsFromItem(itemID2);

		handler.getCommentByID(commentID1);
		handler.getCommentByID(commentID3);
		handler.getCommentByID(commentID6);

		handler.getItemByID(itemID1);
		handler.getItemByID(itemID3);

		// handler.deleteItem(7);

		handler.closeDatabaseConnection();
	}

}