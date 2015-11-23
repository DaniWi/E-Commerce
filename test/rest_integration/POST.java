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

public class POST {

	private static final String OSM_BASE = "http://localhost:8080/E-Commerce/";
	private static final String REST_PATH_HTML_WAY = "rest/";

	private Client client;

	@BeforeClass
	public void beforeClass() {
		client = ClientBuilder.newClient();
	}

	@Test
	public void newCategory() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Test");

		Form form = new Form();
		form.param("username", "Daniel");
		form.param("password", "Daniel");
		final Response response = target.request(MediaType.TEXT_HTML)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test(dependsOnMethods = { "newCategory" })
	public void newItem() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Test/item");

		Form form = new Form();
		form.param("username", "Daniel");
		form.param("password", "Daniel");
		form.param("title", "Test REST");
		form.param("description", "Test REST");
		final Response response = target.request(MediaType.TEXT_HTML)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void newComment() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8/comment");

		Form form = new Form();
		form.param("username", "Daniel");
		form.param("password", "Daniel");
		form.param("text", "Test REST");
		final Response response = target.request(MediaType.TEXT_HTML)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

}
