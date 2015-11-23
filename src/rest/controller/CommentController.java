package rest.controller;

import java.util.Collection;

import data.Comment;
import data.DataHandler;
import data.IDataHandler;
import data.User;

public class CommentController {

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

	public Comment getComment(int commentIndex) {
		return databaseHandler.getCommentByID(commentIndex);
	}

	public String getCommentAsHtml(int commentIndex) {

		Comment comment = databaseHandler.getCommentByID(commentIndex);

		return htmlUtility.HtmlWrap(htmlUtility.commentToHtml(comment));
	}

	public String changeComment(int commentIndex, int itemIndex, String text, String username, String password) {
		IDataHandler dh = databaseHandler;
		User user = dh.getUserLogin(username, password);
		int comment_authorID = dh.getCommentByID(commentIndex).getAuthorID();

		if (user.getRights().equals("admin") || user.getId() == comment_authorID) {
			// admins or users that created the comment are allowed to change
			// the comment
			Comment comment = dh.getCommentByID(commentIndex);
			text = (text == null || text.equals("")) ? comment.getText() : text;
			dh.changeComment(commentIndex, itemIndex, text, comment.getAuthorID());

			return htmlUtility.HtmlWrap(htmlUtility.commentToHtml(dh.getCommentByID(commentIndex)));
		}

		return htmlUtility.HtmlWrap("No permission to change this comment!");
	}

	public String deleteComment(int commentIndex, String username, String password) {
		IDataHandler dh = databaseHandler;
		if (dh.getUserLogin(username, password).getRights().equals("admin")) {
			dh.deleteItem(commentIndex);

			return htmlUtility.HtmlWrap("Successfully deleted the comment!");
		}

		return htmlUtility.HtmlWrap("No permission to delete comments!");
	}

	public String newComment(String text, int itemIndex, String username, String password) {

		IDataHandler dh = databaseHandler;
		User user = dh.getUserLogin(username, password);

		if (user.getRights() != null) {
			// only admins or users are allowed to create comments
			if (text == null || text.equals("")) {
				return "<html><head><title>Webshop 4</title></head><body>New Comment has to have a text!</body></html>";
			}
			int id = dh.createComment(itemIndex, text, user.getId()).getId();

			return htmlUtility.HtmlWrap(htmlUtility.commentToHtml(dh.getCommentByID(id)));
		}

		return htmlUtility.HtmlWrap("No permission to create new comments!");
	}

	public Collection<Comment> getAllCommentsOfItem(int itemIndex) {
		return databaseHandler.getAllCommentsFromItem(itemIndex);
	}

	public String getAllCommentsOfItemAsHtml(int itemIndex) {
		String html = "";

		Collection<Comment> comments = databaseHandler.getAllCommentsFromItem(itemIndex);
		for (Comment comment : comments) {
			html += htmlUtility.commentToHtml(comment);
		}

		return htmlUtility.HtmlWrap(html);
	}
}
