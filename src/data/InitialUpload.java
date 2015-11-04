package data;

public class InitialUpload {

	public static void main(String[] args) {
		// get handler
		DataHandler handler = new DataHandler();
		Item item = new Item();
		item.setTitle("hallo");
		item.setAuthor("ich");
		item.setDescription("jkladjflkajfl");
		
		handler.saveObjectToDb(item);

		handler.closeDatabaseConnection();
	}

}