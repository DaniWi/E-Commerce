package data;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * communication interface between the core and the data layer of our project
 * class to handle all database interactions
 * 
 * @author Witsch Daniel
 * 
 */
public class DataHandler {

	private SessionFactory sessionFactory;
	private ServiceRegistry serviceRegistry;
	private Connection connection;

	/**
	 * Constructor for Databasehandler, it is important that only one instance
	 * of this class will be created
	 * 
	 * @throws IllegalStateException
	 *             occurs when connection not possible or URI is wrong
	 */
	public DataHandler() throws IllegalStateException {

		try {
			// connect to db
			connection = connectToDatabase();

			// create session factory
			Configuration configuration = new Configuration();
			// productive DB
			configuration.addAnnotatedClass(Item.class);
			configuration.addAnnotatedClass(Comment.class);
			configuration.configure();
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		} catch (HibernateException e) {
			System.out.println("Hibernate problems");
			throw new IllegalStateException("Hibernate problems");
		} catch (URISyntaxException e) {
			System.out.println("wrong URI to database");
			throw new IllegalStateException("wrong URI to database");
		} catch (IllegalStateException e) {
			System.out.println("no connection to database");
			throw new IllegalStateException("no connection to database");
		} catch (Exception e) {
			System.out.println("some connection error");
		}
	}

	public void closeDatabaseConnection() throws IllegalStateException {
		sessionFactory.close();
		try {
			connection.close();
		} catch (Exception e) {
			System.out.println("closing connection not possible");
			throw new IllegalStateException("closing connection not possible");
		}
	}

	/**
	 * connect to the database
	 * 
	 * @return the Connection to the database
	 * @throws URISyntaxException
	 *             throw this exception when the URI to the database is wrong
	 * @throws IllegalStateException
	 *             throw this exception when it is not possible to connect
	 */
	private Connection connectToDatabase() throws URISyntaxException, IllegalStateException {

		String url = "jdbc:postgresql://localhost:5432/e-commerce?user=postgres&password=user";
		Connection conn;
		try {
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			System.out.println("closing connection not possible");
			throw new IllegalStateException("closing connection not possible");
		}

		return conn;
	}

	/**
	 * open a new Session
	 * 
	 * @return the session
	 */
	private Session openSession() {

		return sessionFactory.openSession();
	}

	/**
	 * save an object to the database, when it is an entity
	 * 
	 * @param the
	 *            object of an entity
	 * @return the ID of the entity
	 * @throws IllegalStateException
	 *             commit failed by saving from object
	 */
	private Integer saveObjectToDb(Object obj) throws IllegalStateException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			// save an object
			int id = (int) session.save(obj);

			// commit
			session.getTransaction().commit();

			return id;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			System.out.println("saving from object not possible");
			throw new IllegalStateException("saving from object not possible", e);
		} finally {
			// close session
			session.close();
		}

	}

	/**
	 * deletes an object from the database, when it is an entity
	 * 
	 * @param obj
	 *            the object of an entity
	 * @throws IllegalArgumentException
	 *             deletion of object failed
	 */
	private void deleteObjectFromDb(Object obj) throws IllegalArgumentException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			// save an object
			session.delete(obj);

			// commit
			session.getTransaction().commit();

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();

			// deletion failed
			System.out.println("deletion of object failed");
			throw new IllegalArgumentException("deletion of object failed", e);
		} finally {
			// close session
			session.close();
		}
	}

	/**
	 * get an Object from the Database with the id
	 * 
	 * @param id
	 * @param typeParameterClass
	 *            Class type of the searched class for example (Bar.class)
	 * @return Object with the corresponding id
	 * @throws IllegalStateException
	 *             commit failed by searching for object, no object with this ID
	 *             in the database
	 */
	private <T> T searchForID(int id, Class<T> typeParameterClass) throws IllegalArgumentException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(typeParameterClass);
			cr.add(Restrictions.eq("id", id));
			List<T> results = cr.list();

			// commit
			session.getTransaction().commit();

			// only one element in the list because the id is unique
			return results.get(0);

		} catch (IndexOutOfBoundsException e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			System.out.println("object with this ID is not in the database");
			throw new IllegalArgumentException("object with this ID is not in the database", e);
		} finally {
			// close session
			session.close();
		}
	}

	public Item getItemByID(int id) throws IllegalArgumentException {
		return this.<Item> searchForID(id, Item.class);
	}

	public Comment getCommentByID(int id) throws IllegalArgumentException {
		return this.<Comment> searchForID(id, Comment.class);
	}

	public Item createItem(String title, String description, String author, String category)
			throws IllegalStateException {
		// create user instance
		Item item = new Item();
		item.setTitle(title);
		item.setDescription(description);
		item.setAuthor(author);
		item.setCategory(category);
		item.setAltertionDate(new Date());
		item.setCreationDate(new Date());

		// save item in database
		saveObjectToDb(item);
		return item;
	}

	public Comment createComment(int itemID, String text, String author)
			throws IllegalStateException, IllegalArgumentException {
		Session session = openSession();
		Item item;

		// begin transaction
		session.beginTransaction();

		// search item
		try {
			Criteria cr = session.createCriteria(Item.class);
			cr.add(Restrictions.eq("id", itemID));
			item = (Item) cr.list().get(0);
		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			System.out.println("itemID in database not found");
			// close session
			session.close();
			throw new IllegalArgumentException("itemID: not in database found", e);
		}

		// create new comment and save this to database
		try {
			Comment comment = new Comment();

			comment.setText(text);
			comment.setAuthor(author);
			comment.setAltertionDate(new Date());
			comment.setCreationDate(new Date());
			comment.setItem(item);

			// save comment
			session.save(comment);

			item.getComments().add(comment);

			// update all other objects with post
			session.update(comment);

			// commit
			session.getTransaction().commit();

			return comment;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			System.out.println("saving from comment failed");
			throw new IllegalStateException("saving from comment failed", e);
		} finally {
			// close session
			session.close();
		}
	}

	public void deleteItem(int itemID) throws IllegalArgumentException {
		try {
			// get item
			Item item = getItemByID(itemID);

			// delete item from database
			deleteObjectFromDb(item);
		} catch (IllegalArgumentException e) {
			System.out.println("deletion or getting user from ID failed");
			throw new IllegalArgumentException("deletion or getting user from ID failed", e);
		}
	}

	public void deleteComment(int commentID) throws IllegalArgumentException {
		try {
			// get comment
			Comment comment = getCommentByID(commentID);

			// delete comment from database
			deleteObjectFromDb(comment);
		} catch (IllegalArgumentException e) {
			System.out.println("deletion or getting user from ID failed");
			throw new IllegalArgumentException("deletion or getting user from ID failed", e);
		}
	}

	public Collection<Item> getAllItemsFromCategory(String category) throws IllegalStateException {
		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Item.class);
			cr.add(Restrictions.eq("category", category));
			List<Item> results = cr.list();

			// commit
			session.getTransaction().commit();

			return results;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the beer list");
		} finally {
			// close session
			session.close();
		}
	}
	
	public Collection<Comment> getAllCommentsFromItem(int itemID)
			throws IllegalArgumentException, IllegalStateException {
		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Item.class);
			cr.add(Restrictions.eq("id", itemID));
			List<Item> results = cr.list();

			if (results.size() == 0)
				throw new IllegalArgumentException(); // item not found with
														// this id

			Collection<Comment> comments = results.get(0).getComments();
			// commit
			session.getTransaction().commit();

			return comments;

		} catch (IllegalArgumentException e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			System.out.println("no item with this ID in the database");
			throw new IllegalArgumentException(
					"no item with this ID in the database");
		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException(
					"something went wrong by getting the comment list");
		} finally {
			// close session
			session.close();
		}
	}

}