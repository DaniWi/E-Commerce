package rest_unit;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import data.Comment;
import data.IDataHandler;
import data.User;
import junit.framework.Assert;
import rest.controller.CommentController;

public class CommentControllerTest {
	private IDataHandler dataHandler;
	private CommentController controller;

	@BeforeClass
	public void beforeClass() {
		dataHandler = Mockito.mock(IDataHandler.class);
		controller = new CommentController();
		controller.setDatabaseHandler(dataHandler);

		User userAdmin = new User();
		userAdmin.setRights("admin");
		userAdmin.setId(1);
		User userNormal = new User();
		userNormal.setRights("user");
		userNormal.setId(2);
		User userNormal2 = new User();
		userNormal2.setRights("user");
		userNormal2.setId(3);
		User anyUser = new User();
		anyUser.setName("test");

		Comment comment = new Comment();
		comment.setText("Comment entry");
		comment.setCreationDate(new Date());
		comment.setAuthorID(2);

		when(dataHandler.getUserLogin("Daniel", "Daniel")).thenReturn(userAdmin);
		when(dataHandler.getUserLogin("Lukas", "Lukas")).thenReturn(userNormal);
		when(dataHandler.getUserLogin("Martin", "Martin")).thenReturn(userNormal2);
		when(dataHandler.getUserLogin("Any", "Any")).thenReturn(anyUser);
		when(dataHandler.getCommentByID(Matchers.anyInt())).thenReturn(comment);
		when(dataHandler.getUserByID(Matchers.anyInt())).thenReturn(anyUser);
		when(dataHandler.createComment(Matchers.anyInt(), Matchers.anyString(), Matchers.anyInt())).thenReturn(comment);
	}

	@Test
	public void changeComment() {
		String result = controller.changeComment(1, 1, "test", "Daniel", "Daniel");
		String expected = "Comment entry";
		Assert.assertTrue(result.contains(expected));
	}

	@Test
	public void changeCommentOwnComment() {
		String result = controller.changeComment(1, 1, "test", "Lukas", "Lukas");
		String expected = "Comment entry";
		Assert.assertTrue(result.contains(expected));
	}

	@Test
	public void changeCommentNoPermission() {
		String result = controller.changeComment(1, 1, "test", "Martin", "Martin");
		String expected = "<html><head><title>Webshop 4</title></head><body>No permission to change this comment!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void changeCommentNotInTheDatabase() {
		String result = controller.changeComment(1, 1, "test", "NotinTheDatabase", "password");
	}

	@Test
	public void deleteComment() {
		String result = controller.deleteComment(1, "Daniel", "Daniel");
		String expected = "<html><head><title>Webshop 4</title></head><body>Successfully deleted the comment!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test
	public void deleteCommentNoPermission() {
		String result = controller.deleteComment(1, "Lukas", "Lukas");
		String expected = "<html><head><title>Webshop 4</title></head><body>No permission to delete comments!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void deleteCommentNotInTheDatabase() {
		String result = controller.deleteComment(1, "NotinTheDatabase", "password");
	}

	@Test
	public void newComment() {
		String result = controller.newComment("Comment entry", 1, "Daniel", "Daniel");
		String expected = "Comment entry";
		Assert.assertTrue(result.contains(expected));
	}

	@Test
	public void newCommentNoPermission() {
		String result = controller.newComment("New Comment", 1, "Any", "Any");
		String expected = "<html><head><title>Webshop 4</title></head><body>No permission to create new comments!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test
	public void newCommentNoText() {
		String result = controller.newComment(null, 1, "Daniel", "Daniel");
		String expected = "<html><head><title>Webshop 4</title></head><body>New Comment has to have a text!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void newCommentNotInTheDatabase() {
		String result = controller.newComment("New Comment", 1, "NotinTheDatabase", "password");
	}
}
