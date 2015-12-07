package rest.controller;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.DataHandler;
import data.IDataHandler;
import data.User;

@Path("/accounts")
public class RestServiceAccounts {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllAccounts() {
		return DataHandler.getInstance().getAllUsers();
	}

	// returns remaining accounts
	@DELETE
	@Path("/{account_index}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> deleteAccountById(@PathParam("account_index") Integer accountIndex) {
		IDataHandler dh = DataHandler.getInstance();
		dh.deleteUser(accountIndex);
		return dh.getAllUsers();
	}

	// returns new list of ALL accounts
	@PUT
	@Path("/{account_index}/toAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> toAdmin(@PathParam("account_index") Integer accountIndex) {
		IDataHandler dh = DataHandler.getInstance();
		User account = dh.getUserByID(accountIndex);
		if (account.getRights().equals("user")) {
			dh.changeUser(accountIndex, account.getName(), "admin");
		}
		return dh.getAllUsers();
	}

	// returns new list of ALL accounts
	@PUT
	@Path("/{account_index}/toUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> toUser(@PathParam("account_index") Integer accountIndex) {
		IDataHandler dh = DataHandler.getInstance();
		User account = dh.getUserByID(accountIndex);
		if (account.getRights().equals("admin")) {
			// check if this is the last admin
			if (dh.getAdminCount() > 1) {
				dh.changeUser(accountIndex, account.getName(), "user");
			}
		}
		return dh.getAllUsers();
	}

	// returns new list of ALL accounts
	@PUT
	@Path("/{account_index}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> changeUser(@PathParam("account_index") Integer accountIndex, @FormParam("name") String name,
			@FormParam("rights") String rights) {
		IDataHandler dh = DataHandler.getInstance();
		User account = dh.getUserByID(accountIndex);
		
		name = (name.equals("")) ? account.getName() : name;
		if (account.getRights().equals("admin") && rights.equals("user")) {
			if(dh.getAdminCount() <= 1) {
				// would be the last admin changed to user
				rights = account.getRights(); 
			}
		}
		
		dh.changeUser(accountIndex, name, rights);
		return dh.getAllUsers();
	}

}
