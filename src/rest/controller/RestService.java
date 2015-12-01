package rest.controller;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.Comment;
import data.Item;

@Path("/{category}")
public class RestService {
	private CategoryController categoryController = new CategoryController();
	private ItemController itemController = new ItemController();
	private CommentController commentController = new CommentController();

	public RestService() {
		categoryController.setDatabaseHandler();
		itemController.setDatabaseHandler();
		commentController.setDatabaseHandler();
	}

	// ~~~~~~~~~~ CATEGORY Controller ~~~~~~~~~~ //

	// New Category
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postNewCategory(@PathParam("category") String category, @FormParam("username") String username,
			@FormParam("password") String password) {

		return categoryController.newCategory(category, username, password);

	}

	// Change Category
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String changeCategory(@PathParam("category") String category, @FormParam("username") String username,
			@FormParam("password") String password, @FormParam("name") String name) {

		return categoryController.changeCategory(category, name, username, password);
	}

	// ~~~~~~~~~~ ITEM Controller ~~~~~~~~~~ //

	// GET All Items of a Category (JSON)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Item> getAllItemsByCategoryAsJson(@PathParam("category") String category) {

		return itemController.getAllItemsOfCategory(category);

	}

	// GET All Items of a Category (HTML)
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAllItemsByCategoryAsHtml(@PathParam("category") String category) {
		return itemController.getAllItemsOfCategoryAsHtml(category);
	}

	// Get Item by ID (JSON)
	@GET
	@Path("/{item_index}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItemByIdAsJson(@PathParam("item_index") Integer itemIndex) {
		return itemController.getItem(itemIndex);
	}

	// Get Item by ID (HTML)
	@GET
	@Path("/{item_index}")
	@Produces(MediaType.TEXT_HTML)
	public String getItemByIdAsHtml(@PathParam("category") String category,
			@PathParam("item_index") Integer itemIndex) {

		return itemController.getItemAsHtml(itemIndex);
	}

	// Change Item
	@PUT
	@Path("/{item_index}")
	@Produces(MediaType.TEXT_HTML)
	@Consumes("application/x-www-form-urlencoded")
	public String changeItem(@PathParam("item_index") Integer itemIndex, @FormParam("username") String username,
			@FormParam("password") String password, @FormParam("title") String title,
			@FormParam("description") String description, @FormParam("price") double price) {

		return itemController.changeItem(itemIndex, title, description, price, username, password);
	}

	// DELETE Item
	@DELETE
	@Path("/{item_index}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteItem(@PathParam("item_index") Integer itemIndex, @FormParam("username") String username,
			@FormParam("password") String password) {

		return itemController.deleteItem(itemIndex, username, password);
	}

	// New Item
	@POST
	@Path("/item")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postNewItem(@PathParam("category") String category, @FormParam("username") String username,
			@FormParam("password") String password, @FormParam("title") String title,
			@FormParam("description") String description, @FormParam("price") double price) {

		return itemController.newItem(title, description, category, price, username, password);
	}

	// ~~~~~~~~~~ COMMENT Controller ~~~~~~~~~~ //

	// GET all comments of an item (JSON)
	@GET
	@Path("/{item_index}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getAllCommentsByItemAsJson(@PathParam("item_index") Integer itemIndex) {
		return commentController.getAllCommentsOfItem(itemIndex);
	}

	// GET all comments of an item (HTML)
	@GET
	@Path("/{item_index}/comment")
	@Produces(MediaType.TEXT_HTML)
	public String getAllCommentsByItemAsHml(@PathParam("item_index") Integer itemIndex) {
		return commentController.getAllCommentsOfItemAsHtml(itemIndex);
	}

	// New Comment
	@POST
	@Path("/{item_index}/comment")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postNewComment(@PathParam("item_index") Integer itemIndex, @FormParam("username") String username,
			@FormParam("password") String password, @FormParam("text") String text) {

		return commentController.newComment(text, itemIndex, username, password);
	}

	// GET comment by ID (JSON)
	@GET
	@Path("/{item_index}/comment/{comment_index}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getCommentByIdAsJson(@PathParam("comment_index") Integer commentIndex) {
		return commentController.getComment(commentIndex);
	}

	// GET comment by ID (HTML)
	@GET
	@Path("/{item_index}/comment/{comment_index}")
	@Produces(MediaType.TEXT_HTML)
	public String getCommentByIdAsHtml(@PathParam("comment_index") Integer commentIndex) {
		return commentController.getCommentAsHtml(commentIndex);
	}

	// Change Comment
	@PUT
	@Path("/{item_index}/comment/{comment_index}")
	@Produces(MediaType.TEXT_HTML)
	@Consumes("application/x-www-form-urlencoded")
	public String changeComment(@PathParam("item_index") Integer itemIndex,
			@PathParam("comment_index") Integer commentIndex, @FormParam("username") String username,
			@FormParam("password") String password, @FormParam("text") String text) {

		return commentController.changeComment(commentIndex, itemIndex, text, username, password);
	}

	// DELETE Comment
	@DELETE
	@Path("/{item_index}/comment/{comment_index}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteCommentbyId(@PathParam("comment_index") Integer commentIndex,
			@FormParam("username") String username, @FormParam("password") String password) {

		return commentController.deleteComment(commentIndex, username, password);
	}

}
