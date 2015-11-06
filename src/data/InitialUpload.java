package data;

import java.util.Collection;

public class InitialUpload {

	public static void main(String[] args) {
		// get handler
		DataHandler handler = new DataHandler();
		
		int itemID1 = handler.createItem("Hallo1", "Text", "ich", "Werkzeug").getId();
		int itemID2 = handler.createItem("Hallo2", "Text", "ich", "Kleidung").getId();
		int itemID3 = handler.createItem("Hallo3", "Text", "ich", "Werkzeug").getId();
		
		int commentID1 = handler.createComment(itemID1, "Comment1", "er").getId();
		int commentID2= handler.createComment(itemID1, "Comment2", "er").getId();
		int commentID3 = handler.createComment(itemID1, "Comment3", "er").getId();
		int commentID4 = handler.createComment(itemID2, "Comment1", "sie").getId();
		int commentID5 = handler.createComment(itemID2, "Comment2", "sie").getId();
		int commentID6 = handler.createComment(itemID2, "Comment3", "sie").getId();
		
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