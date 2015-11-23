package rest_unit;

import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import data.Category;
import data.IDataHandler;
import data.User;
import junit.framework.Assert;
import rest.controller.CategoryController;

public class CategoryControllerTest {

	private IDataHandler dataHandler;
	private CategoryController controller;

	@BeforeClass
	public void beforeClass() {
		dataHandler = Mockito.mock(IDataHandler.class);
		controller = new CategoryController();
		controller.setDatabaseHandler(dataHandler);
		User userAdmin = new User();
		userAdmin.setRights("admin");
		User userNormal = new User();
		userNormal.setRights("user");

		when(dataHandler.getUserLogin("Daniel", "Daniel")).thenReturn(userAdmin);
		when(dataHandler.getUserLogin("Lukas", "Lukas")).thenReturn(userNormal);
		when(dataHandler.getCategoryByName("Books")).thenReturn(new Category());
	}

	@Test
	public void changeCategory() {
		String result = controller.changeCategory("Books", "Test", "Daniel", "Daniel");
		String expected = "<html><head><title>Webshop 4</title></head><body>Sucessfully changed category name 'Books' to 'Test</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test
	public void changeCategoryNoPermission() {
		String result = controller.changeCategory("Books", "Test", "Lukas", "Lukas");
		String expected = "<html><head><title>Webshop 4</title></head><body>No permission to change categories!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test
	public void changeCategoryWrongCategory() {
		String result = controller.changeCategory("NotinTheDatabase", "Test", "Daniel", "Daniel");
		String expected = "<html><head><title>Webshop 4</title></head><body>Category NotinTheDatabase does not exits</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void changeCategoryWrongUser() {
		String result = controller.changeCategory("Books", "Test", "notInTheDatabase", "password");
	}

	@Test
	public void createCategory() {
		String result = controller.newCategory("New Category", "Daniel", "Daniel");
		String expected = "<html><head><title>Webshop 4</title></head><body>Successfully created category New Category</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test
	public void createCategoryNoPermission() {
		String result = controller.newCategory("Books", "Lukas", "Lukas");
		String expected = "<html><head><title>Webshop 4</title></head><body>No permission to create new categories!</body></html>";
		Assert.assertEquals(expected, result);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void createCategoryWrongUser() {
		String result = controller.newCategory("Books", "notInTheDatabase", "password");
	}

}
