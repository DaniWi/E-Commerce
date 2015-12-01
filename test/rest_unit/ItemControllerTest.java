package rest_unit;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import data.Category;
import data.Comment;
import data.IDataHandler;
import data.Item;
import data.User;
import junit.framework.Assert;
import rest.controller.ItemController;

public class ItemControllerTest {
	private IDataHandler dataHandler;
	private ItemController controller;

	@BeforeClass
	public void beforeClass() {
		dataHandler = Mockito.mock(IDataHandler.class);
		controller = new ItemController();
		controller.setDatabaseHandler(dataHandler);

		User userAdmin = new User();
		userAdmin.setRights("admin");
		userAdmin.setId(1);
		User userNormal = new User();
		userNormal.setRights("user");
		userNormal.setId(2);
		User anyUser = new User();
		anyUser.setName("test");

		Comment comment = new Comment();
		comment.setText("Comment entry");
		comment.setCreationDate(new Date());
		comment.setAuthorID(2);

		Item item = new Item();
		item.setId(1);
		item.setAuthorID(1);
		item.setCategoryID(1);
		item.setDescription("description");
		item.setTitle("title");
		item.setPrice(9.99);
		item.setAltertionDate(new Date());
		item.setCreationDate(new Date());

		Category cat = new Category();
		cat.setId(1);
		cat.setName("Books");

		when(dataHandler.getUserLogin("Daniel", "Daniel")).thenReturn(userAdmin);
		when(dataHandler.getUserLogin("Lukas", "Lukas")).thenReturn(userNormal);
		when(dataHandler.getUserLogin("Any", "Any")).thenReturn(anyUser);
		when(dataHandler.getCommentByID(Matchers.anyInt())).thenReturn(comment);
		when(dataHandler.getUserByID(Matchers.anyInt())).thenReturn(anyUser);
		when(dataHandler.getItemByID(Matchers.anyInt())).thenReturn(item);
		when(dataHandler.getCategoryByName("Books")).thenReturn(cat);
		when(dataHandler.createItem(Matchers.anyString(), Matchers.anyString(), Matchers.anyDouble(), Matchers.anyInt(),
				Matchers.anyInt())).thenReturn(item);
	}

	@Test
	public void changeItem() {
		String result = controller.changeItem(1, "title", "description", 10.99, "Daniel", "Daniel");
		String expected = "description";
		Assert.assertTrue(result.contains(expected));
	}

	@Test
	public void changeItemWithoutTitleDescription() {
		String result = controller.changeItem(1, null, null, 10.99, "Daniel", "Daniel");
		String expected = "description";
		Assert.assertTrue(result.contains(expected));
	}

	@Test
	public void changeItemNoPermission() {
		String result = controller.changeItem(1, "title", "description", 10.99, "Lukas", "Lukas");
		String expected = "<html><head><title>Webshop 4</title></head><body>No permission to change items!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void changeItemNotInTheDatabase() {
		String result = controller.changeItem(1, "title", "description", 10.99, "NotinTheDatabase", "password");
	}

	@Test
	public void deleteItem() {
		String result = controller.deleteItem(1, "Daniel", "Daniel");
		String expected = "<html><head><title>Webshop 4</title></head><body>Successfully deleted the item!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test
	public void deleteItemNoPermission() {
		String result = controller.deleteItem(1, "Lukas", "Lukas");
		String expected = "<html><head><title>Webshop 4</title></head><body>No permission to delete items!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void deleteItemNotInTheDatabase() {
		String result = controller.deleteItem(1, "NotinTheDatabase", "password");
	}

	@Test
	public void newItem() {
		String result = controller.newItem("title", "description", 9.99, "Books", "Daniel", "Daniel");
		String expected = "description";
		Assert.assertTrue(result.contains(expected));
	}

	@Test
	public void newItemNoDescription() {
		String result = controller.newItem("title", null, 9.99, "Books", "Daniel", "Daniel");
		String expected = "description";
		Assert.assertTrue(result.contains(expected));
	}

	@Test
	public void newItemItemWithoutTitle() {
		String result = controller.newItem(null, "description", 9.99, "Books", "Daniel", "Daniel");
		String expected = "<html><head><title>Webshop 4</title></head><body>New Items have to have a title!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test
	public void newItemItemNoPermission() {
		String result = controller.newItem("title", "description", 9.99, "Books", "Lukas", "Lukas");
		String expected = "<html><head><title>Webshop 4</title></head><body>No permission to create new items!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void newItemNotInTheDatabase() {
		String result = controller.newItem("title", "description", 9.99, "Books", "NotinTheDatabase", "password");
	}
}
