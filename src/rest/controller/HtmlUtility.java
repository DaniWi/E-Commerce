package rest.controller;

import java.util.Collection;

import data.Comment;
import data.DataHandler;
import data.IDataHandler;
import data.Item;

public class HtmlUtility {

	IDataHandler databaseHandler;

	public IDataHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(IDataHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	public void setDatabaseHandler() {
		this.databaseHandler = DataHandler.getInstance();
	}

	public String HtmlWrap(String content) {
		return "<html><head><title>Webshop 4</title></head><body>" + content + "</body></html>";
	}

	/*
	 * Returns an item as HTML
	 */
	public String itemToHtml(Item item) {
		String str_item = "";

		IDataHandler dh = databaseHandler;

		str_item += "<div class=\"row item\"><div class=\"col-md-8\">";
		str_item += "<h1>" + item.getTitle() + "</h1>";
		str_item += "<p>Von " + dh.getUserByID(item.getAuthorID()).getName() + "</p>";
		str_item += "<p class=\"description\">" + item.getDescription() + "</p>";
		str_item += "<p class=\"myinfo\">" + DateUtility.toGmtString(item.getCreationDate()) + "</p>";
		Collection<Comment> comments = dh.getAllCommentsFromItem(item.getId());
		for (Comment comment : comments) {
			str_item += commentToHtml(comment);
		}
		str_item += "</div></div>";

		return str_item;
	}

	/*
	 * Returns a comment as HTML
	 */
	public String commentToHtml(Comment comment) {
		String str_comment = "";

		IDataHandler dh = databaseHandler;

		str_comment += "<div class=\"comment\"><p>" + comment.getText() + "</p>";
		str_comment += "<p class=\"commentinfo\">" + dh.getUserByID(comment.getAuthorID()).getName() + " - "
				+ DateUtility.toGmtString(comment.getCreationDate()) + "</p>";
		str_comment += "</div>";

		return str_comment;
	}
}
