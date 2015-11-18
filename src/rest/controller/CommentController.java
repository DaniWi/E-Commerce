package rest.controller;

import java.util.Collection;

import data.Comment;
import data.DataHandler;
import data.User;

public class CommentController {

	public static Comment getComment(int commentIndex) {
		return DataHandler.getInstance().getCommentByID(commentIndex);
	}

	public static String getCommentAsHtml(int commentIndex) {

		Comment comment = DataHandler.getInstance().getCommentByID(commentIndex);

		return HtmlUtility.HtmlWrap(HtmlUtility.commentToHtml(comment));
	}

	public static String changeComment(int commentIndex, int itemIndex, String text, String username, String password) {
		DataHandler dh = DataHandler.getInstance();
		User user = dh.getUserLogin(username, password);
		int comment_authorID = dh.getCommentByID(commentIndex).getAuthorID();

		if (user.getRights().equals("admin") || user.getId() == comment_authorID) {
			// admins or users that created the comment are allowed to change the comment
			Comment comment = dh.getCommentByID(commentIndex);
			text = (text == null || text.equals("")) ? comment.getText() : text;
			dh.changeComment(commentIndex, itemIndex, text, comment.getAuthorID());

			return HtmlUtility.HtmlWrap(HtmlUtility.commentToHtml(dh.getCommentByID(commentIndex)));
		}

		return HtmlUtility.HtmlWrap("No permission to change this comment!");
	}

	public static String deleteComment(int commentIndex, String username, String password) {
		DataHandler dh = DataHandler.getInstance();
		if (dh.getUserLogin(username, password).getRights().equals("admin")) {
			dh.deleteItem(commentIndex);

			return HtmlUtility.HtmlWrap("Successfully deleted the comment!");
		}

		return HtmlUtility.HtmlWrap("No permission to delete comments!");
	}

	public static String newComment(String text, int itemIndex, String username, String password) {

		DataHandler dh = DataHandler.getInstance();
		User user = dh.getUserLogin(username, password);

		if (user.getRights() != null) {
			// only admins or users are allowed to create comments
			if (text == null || text.equals("")) {
				return "<html><head><title>Webshop 4</title></head><body>New Comment has to have a text!</body></html>";
			}
			int id = dh.createComment(itemIndex, text, user.getId()).getId();

			return HtmlUtility.HtmlWrap(HtmlUtility.commentToHtml(dh.getCommentByID(id)));
		}

		return HtmlUtility.HtmlWrap("No permission to create new comments!");
	}

	public static Collection<Comment> getAllCommentsOfItem(int itemIndex) {
		return DataHandler.getInstance().getAllCommentsFromItem(itemIndex);
	}

	public static String getAllCommentsOfItemAsHtml(int itemIndex) {
		String html = "";

		Collection<Comment> comments = DataHandler.getInstance().getAllCommentsFromItem(itemIndex);
		for (Comment comment : comments) {
			html += HtmlUtility.commentToHtml(comment);
		}

		return HtmlUtility.HtmlWrap(html);
	}
}
