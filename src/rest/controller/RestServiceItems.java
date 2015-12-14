package rest.controller;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.Item;

@Path("/items")
public class RestServiceItems {
	private ItemController itemController = new ItemController();

	public RestServiceItems() {
		itemController.setDatabaseHandler();
	}

	// GET All Items (JSON)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Item> getAllItemsAsJson() {

		return itemController.getAllItems();

	}

	// DELETE Item
	@DELETE
	@Path("/{item_index}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteItem(@PathParam("item_index") Integer itemIndex) {

		return itemController.deleteItem(itemIndex);
	}
}
