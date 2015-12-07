package rest.controller;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.Item;

@Path("/items")
public class RestServiceItems {
	private ItemController itemController = new ItemController();

	public RestServiceItems() {
		itemController.setDatabaseHandler();
	}

	// GET All Items of a Category (JSON)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Item> getAllItemsAsJson() {

		return itemController.getAllItems();

	}
}
