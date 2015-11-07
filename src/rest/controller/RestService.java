package rest.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
//import javax.websocket.server.PathParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sun.ws.rest.api.representation.FormURLEncodedProperties;

import data.Comment;
import data.DataHandler;
import data.Item;

@Path("/{category}")
public class RestService {
	
	private DataHandler dataHandler;
	
	private synchronized DataHandler getDataHandler() {
		if (dataHandler == null) {
			dataHandler = new DataHandler();
		}
		return dataHandler;
	}
	
	/*
	 * PUT (create) a new category
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes("application/x-www-form-urlencoded")
	public String putNewCategory(FormURLEncodedProperties putParam, @PathParam("category") String category, @Context HttpServletRequest request) {
		if (request.getSession().getAttribute("rights").equals("admin")) {
			// only Administators are allowed to create new Categories
			// DataHandler dh = getDataHandler();
			// dh.createCategory(category)
		}
		
		return "<html><head><title>Webshop 4</title></head><body>No permission to create new categories!</body></html>";
	}
	
	@DELETE
	@Produces(MediaType.TEXT_HTML)
	public String deleteCategory(@PathParam("category") String category, @Context HttpServletRequest request) {
		if (request.getSession().getAttribute("rights").equals("admin")) {
			// only Administators are allowed to delete Categories
			// DataHandler dh = getDataHandler();
			// dh.deleteCategory(category);
		}
		
		return "<html><head><title>Webshop 4</title></head><body>No permission to delete categories!</body></html>";
	}
	
	/*
	 * GET All Items of a Category (JSON)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Item> getAllItemsByCategoryAsJson(@PathParam("category") String category) {

		DataHandler dh = getDataHandler();
		return dh.getAllItemsFromCategory(category);
		
	}
	
	/*
	 * GET All Items of a Category (HTML)
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAllItemsByCategoryAsHtml(@PathParam("category") String category) {
		String html = "<html><head><title>Webshop 4</title></head><body>";
		
		DataHandler dh = getDataHandler();
		Collection<Item> items = dh.getAllItemsFromCategory(category);
		for (Item item : items) {
			html += itemToHtml(item);
		}
		
		html += "</body></html>";
		
		return html;
	}
	
	/*
	 * Get Item by ID (JSON)
	 */
	@GET
	@Path("/{item_index}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItemByIdAsJson(@PathParam("category") String category, @PathParam("item_index") Integer itemIndex) {
		DataHandler dh = getDataHandler();
		return dh.getItemByID(itemIndex);
	}
	
	/*
	 * Get Item by ID (HTML)
	 */
	@GET
	@Path("/{item_index}")
	@Produces(MediaType.TEXT_HTML)
	public String getItemByIdAsHtml(@PathParam("category") String category, @PathParam("item_index") Integer itemIndex) {
		String html = "<html><head><title>Webshop 4</title></head><body>";
		
		DataHandler dh = getDataHandler();
		Item item = dh.getItemByID(itemIndex);
		
		html += itemToHtml(item) + "</body></html>";
		
		return html;
	}
	
	/*
	 * Change item by ID (HTML of updated item returned)
	 */
	@POST
	@Path("/{item_index}")
	@Produces(MediaType.TEXT_HTML)
	@Consumes("application/x-www-form-urlencoded")
	public String changeItem(FormURLEncodedProperties postParam, @PathParam("item_index") Integer itemIndex, @Context HttpServletRequest request) {
		if (request.getSession().getAttribute("rights").equals("admin")) {
			DataHandler dh = getDataHandler();
			Item item = dh.getItemByID(itemIndex);
			String title = (postParam.get("title") == null) ? item.getTitle() : postParam.get("title");
			String description = (postParam.get("description") == null) ? item.getDescription() : postParam.get("description");
			String category = (postParam.get("category") == null) ? item.getCategory() : postParam.get("category");
			
			dh.changeItem(itemIndex, title, description, item.getAuthorID(), category);
			
			return "<html><head><title>Webshop 4</title></head><body>" + itemToHtml(dh.getItemByID(itemIndex)) + "</body></html>";
		}
		
		return "<html><head><title>Webshop 4</title></head><body>No permission to change items!</body></html>";
	}
	
	/*
	 * DELETE item by ID
	 */
	@DELETE
	@Path("/{item_index}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteItem(@PathParam("item_index") Integer itemIndex, @Context HttpServletRequest request) {
		if (request.getSession().getAttribute("rights").equals("admin")) {
			DataHandler dh = getDataHandler();
			dh.deleteItem(itemIndex);
			
			return "<html><head><title>Webshop 4</title></head><body>Successfully deleted the item!</body></html>";
		}
		
		return "<html><head><title>Webshop 4</title></head><body>No permission to delete items!</body></html>";
	}
	
	/*
	 * PUT (create) new item
	 */
	@PUT
	@Path("/item")
	@Produces(MediaType.TEXT_HTML)
	@Consumes("application/x-www-form-urlencoded")
	public String putNewItem(FormURLEncodedProperties putParam, @PathParam("category") String category, @Context HttpServletRequest request) {
		if (request.getSession().getAttribute("rights").equals("admin")) {
			if (putParam.get("title") == null || putParam.get("title").equals("")) {
				return "<html><head><title>Webshop 4</title></head><body>New Item has to have a title!</body></html>";
			}
			String description = (putParam.get("description") == null) ? "empty description" : putParam.get("description");
			DataHandler dh = getDataHandler();			
			int id = dh.createItem(putParam.get("title"), description, (int)request.getSession().getAttribute("ID"), category).getId();
			
			return "<html><head><title>Webshop 4</title></head><body>" + itemToHtml(dh.getItemByID(id)) + "</body></html>";
		}
		
		return "<html><head><title>Webshop 4</title></head><body>No permission to create new items!</body></html>";
	}
	
	/*
	 * GET all comments of an item (JSON)
	 */
	@GET
	@Path("/{item_index}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getAllCommentsByItemAsJson(@PathParam("item_index") Integer itemIndex) {
		
		DataHandler dh = getDataHandler();
		return dh.getAllCommentsFromItem(itemIndex);
	}
	
	/*
	 * GET all comments of an item (HTML)
	 */
	@GET
	@Path("/{item_index}/comment")
	@Produces(MediaType.TEXT_HTML)
	public String getAllCommentsByItemAsHml(@PathParam("item_index") Integer itemIndex) {
		String html = "<html><head><title>Webshop 4</title></head><body>";
		
		DataHandler dh = getDataHandler();
		Collection<Comment> comments = dh.getAllCommentsFromItem(itemIndex);
		for (Comment comment : comments) {
			html += commentToHtml(comment);
		}
		
		html += "</body></html>";
		return html;
	}
	
	/*
	 * PUT (create) new comment for specific item
	 */
	@PUT
	@Path("/{item_index}/comment")
	@Produces(MediaType.TEXT_HTML)
	@Consumes("application/x-www-form-urlencoded")
	public String putNewComment(@PathParam("item_index") Integer itemIndex, FormURLEncodedProperties putParam, @Context HttpServletRequest request) {
		if (request.getSession().getAttribute("rights") != null) {
			// only admins or users are allowed to create comments
			if (putParam.get("text") == null || putParam.get("text").equals("")) {
				return "<html><head><title>Webshop 4</title></head><body>New Comment has to have a text!</body></html>";
			}
			DataHandler dh = getDataHandler();
			int id = dh.createComment(itemIndex, putParam.get("text"), (int)request.getSession().getAttribute("ID")).getId();
			
			return "<html><head><title>Webshop 4</title></head><body>" + commentToHtml(dh.getCommentByID(id)) + "</body></html>";
		}
		
		return "<html><head><title>Webshop 4</title></head><body>No permission to create new comments!</body></html>";
	}
	
	/*
	 * GET comment by ID (JSON)
	 */
	@GET
	@Path("/{item_index}/comment/{comment_index}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getCommentByIdAsJson(@PathParam("comment_index") Integer commentIndex) {
		
		DataHandler dh = getDataHandler();
		return dh.getCommentByID(commentIndex);
	}
	
	/*
	 * GET comment by ID (HTML)
	 */
	@GET
	@Path("/{item_index}/comment/{comment_index}")
	@Produces(MediaType.TEXT_HTML)
	public String getCommentByIdAsHtml(@PathParam("comment_index") Integer commentIndex) {
		String html = "<html><head><title>Webshop 4</title></head><body>";
		
		DataHandler dh = getDataHandler();
		Comment comment = dh.getCommentByID(commentIndex);
		
		html += commentToHtml(comment) + "</body></html>";
		
		return html;
	}
	
	/*
	 * Change comment by ID (HTML of updated comment returned)
	 */
	@POST
	@Path("/{item_index}/comment/{comment_index}")
	@Produces(MediaType.TEXT_HTML)
	@Consumes("application/x-www-form-urlencoded")
	public String changeComment(@PathParam("item_index") Integer itemIndex, @PathParam("comment_index") Integer commentIndex, FormURLEncodedProperties postParam, @Context HttpServletRequest request) {
		
		DataHandler dh = getDataHandler();
		int comment_authorID = dh.getCommentByID(commentIndex).getAuthorID();
		if (request.getSession().getAttribute("rights").equals("admin") || (int)request.getSession().getAttribute("ID") == comment_authorID) {
			// admins or users that created the comment are allowed to change the comment
			Comment comment = dh.getCommentByID(commentIndex);
			String text = postParam.get("text") == null ? comment.getText() : postParam.get("text");
			dh.changeComment(commentIndex, itemIndex, text, comment.getAuthorID());
			
			return "<html><head><title>Webshop 4</title></head><body>" + commentToHtml(dh.getCommentByID(commentIndex)) + "</body></html>";
		}
		
		return "<html><head><title>Webshop 4</title></head><body>No permission to change this comment!</body></html>";
	}
	
	/*
	 * DELETE comment by ID
	 */
	@DELETE
	@Path("/{item_index}/comment/{comment_index}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteCommentbyId(@PathParam("comment_index") Integer commentIndex, @Context HttpServletRequest request) {
		if (!request.getSession().getAttribute("rights").equals("admin")) {
			DataHandler dh = getDataHandler();
			dh.deleteItem(commentIndex);
			
			return "<html><head><title>Webshop 4</title></head><body>Successfully deleted the comment!</body></html>";
		}
		
		return "<html><head><title>Webshop 4</title></head><body>No permission to delete comments!</body></html>";
	}
	
	
	/*
	 * Returns an item as HTML
	 */
	private String itemToHtml(Item item) {
		String str_item = "";
		
		DataHandler dh = getDataHandler();
		
		str_item += "<div class=\"row item\"><div class=\"col-md-8\">";
		str_item += "<h1>" + item.getTitle() + "</h1>";
		str_item += "<p>Von " + dh.getUserByID(item.getAuthorID()).getName() + "</p>";
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
	private String commentToHtml(Comment comment) {
		String str_comment = "";
		
		DataHandler dh = getDataHandler();
		
		str_comment += "<div class=\"comment\"><p>" + comment.getText() + "</p>";
		str_comment += "<p class=\"commentinfo\">" + dh.getUserByID(comment.getAuthorID()).getName() + " - " + DateUtility.toGmtString(comment.getCreationDate()) + "</p>";
		str_comment += "</div>";
		
		return str_comment;
	}
}
