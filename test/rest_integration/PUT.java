package rest_integration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PUT {

	private static final String OSM_BASE = "http://localhost:8080/E-Commerce/";
	private static final String REST_PATH_HTML_WAY = "rest/";

	private Client client;

	@BeforeClass
	public void beforeClass() {
		client = ClientBuilder.newClient();
	}

	@Test
	public void changeCategory() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books");

		Form form = new Form();
		form.param("username", "Daniel");
		form.param("password", "Daniel");
		form.param("name", "Books");
		final Response response = target.request(MediaType.TEXT_HTML)
				.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		final String result = response.readEntity(String.class);
		String expected = "<html><head><title>Webshop 4</title></head><body>Sucessfully changed category name 'Books' to 'Books</body></html>";
		Assert.assertEquals(result, expected);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void changeItem() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8");

		Form form = new Form();
		form.param("username", "Daniel");
		form.param("password", "Daniel");
		form.param("title", "Lineare Algebra");
		form.param("description", "Grundlagen Mathematik");
		form.param("price", "9.99");
		final Response response = target.request(MediaType.TEXT_HTML)
				.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void changeComment() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8/comment/16");

		Form form = new Form();
		form.param("username", "Daniel");
		form.param("password", "Daniel");
		form.param("text", "Comment444");
		final Response response = target.request(MediaType.TEXT_HTML)
				.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

}
