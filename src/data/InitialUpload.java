package data;

import java.util.Collection;

public class InitialUpload {

	public static void main(String[] args) {
		// get handler
		DataHandler handler = new DataHandler();
		
		int userID1 = handler.createUser("Daniel", "kdlfjasljkf", "klajdflkjf").getId();
		int userID2 = handler.createUser("Lukas", "kdlfjasljkf", "klajdflkjf").getId();
		
		int itemID1 = handler.createItem("Hallo1", "Text", userID1, "Werkzeug").getId();
		int itemID2 = handler.createItem("Hallo2", "Text", userID1, "Kleidung").getId();
		int itemID3 = handler.createItem("Hallo3", "Text", userID2, "Werkzeug").getId();
		
		int commentID1 = handler.createComment(itemID1, "Comment1", userID1).getId();
		int commentID2= handler.createComment(itemID1, "Comment2", userID2).getId();
		int commentID3 = handler.createComment(itemID1, "Comment3", userID1).getId();
		int commentID4 = handler.createComment(itemID2, "Comment1", userID1).getId();
		int commentID5 = handler.createComment(itemID2, "Comment2", userID2).getId();
		int commentID6 = handler.createComment(itemID2, "Comment3", userID2).getId();
		
		Collection<Item> items1 = handler.getAllItemsFromCategory("Werkzeug");
		Collection<Item> items2 = handler.getAllItemsFromCategory("Kleidung");
		Collection<Item> items3 = handler.getAllItemsFromCategory("kladjflak");
		
		Collection<Comment> comments1 = handler.getAllCommentsFromItem(itemID1);
		Collection<Comment> comments2 = handler.getAllCommentsFromItem(itemID2);
		
		handler.getCommentByID(commentID1);
		handler.getCommentByID(commentID3);
		handler.getCommentByID(commentID6);
		
		handler.getItemByID(itemID1);
		handler.getItemByID(itemID3);
		
		

		handler.closeDatabaseConnection();
	}

}