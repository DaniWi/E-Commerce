package rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GET {

	private static final String OSM_BASE = "http://localhost:8080/E-Commerce/";
	private static final String REST_PATH_HTML_WAY = "rest/";

	private Client client;

	@BeforeClass
	public void beforeClass() {
		client = ClientBuilder.newClient();
	}

	@Test
	public void categoryHTML() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books");
		final Invocation.Builder builder = target.request(MediaType.TEXT_HTML);

		final Response response = builder.get();
		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void categoryJSON() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books");
		final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);

		final Response response = builder.get();
		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void itemHTML() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8");
		final Invocation.Builder builder = target.request(MediaType.TEXT_HTML);

		final Response response = builder.get();
		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void itemJSON() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8");
		final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);

		final Response response = builder.get();
		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void allCommentsHTML() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8/comment");
		final Invocation.Builder builder = target.request(MediaType.TEXT_HTML);

		final Response response = builder.get();
		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void allCommentsJSON() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8/comment");
		final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);

		final Response response = builder.get();
		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void commentHTML() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8/comment/16");
		final Invocation.Builder builder = target.request(MediaType.TEXT_HTML);

		final Response response = builder.get();
		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}

	@Test
	public void commentJSON() {
		final WebTarget target = client.target(OSM_BASE).path(REST_PATH_HTML_WAY).path("Books/8/comment/16");
		final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);

		final Response response = builder.get();
		final String result = response.readEntity(String.class);

		if (response.getStatus() != HttpStatus.SC_OK) {
			Assert.fail("HTTP " + response.getStatus() + " error ocurred. Error entity is: " + result);
		}
	}
}
