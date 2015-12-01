package data;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

/**
 * communication interface between the core and the data layer of our project
 * class to handle all database interactions
 * 
 * @author Witsch Daniel
 * 
 */
public class DataHandler implements IDataHandler {

	// Singleton
	private static final IDataHandler instance = new DataHandler();

	public static IDataHandler getInstance() {
		return instance;
	}

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
	private DataHandler() throws IllegalStateException {

		try {
			// connect to db
			connection = connectToDatabase();

			// create session factory
			Configuration configuration = new Configuration();
			// productive DB
			/*
			 * configuration.addAnnotatedClass(Item.class);
			 * configuration.addAnnotatedClass(Comment.class);
			 * configuration.addAnnotatedClass(User.class);
			 * configuration.addAnnotatedClass(Category.class);
			 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#closeDatabaseConnection()
	 */
	@Override
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
			// for connection to database on the tomcat server
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getItemByID(int)
	 */
	@Override
	public Item getItemByID(int id) throws IllegalArgumentException {
		return this.<Item> searchForID(id, Item.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getCommentByID(int)
	 */
	@Override
	public Comment getCommentByID(int id) throws IllegalArgumentException {
		return this.<Comment> searchForID(id, Comment.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getUserByID(int)
	 */
	@Override
	public User getUserByID(int id) throws IllegalArgumentException {
		return this.<User> searchForID(id, User.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getCategoryByID(int)
	 */
	@Override
	public Category getCategoryByID(int id) throws IllegalArgumentException {
		return this.<Category> searchForID(id, Category.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getCategoryByName(java.lang.String)
	 */
	@Override
	public Category getCategoryByName(String name) throws IllegalArgumentException {

		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Category.class);
			cr.add(Restrictions.eq("name", name));
			List<Category> results = cr.list();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#createCategory(java.lang.String)
	 */
	@Override
	public Category createCategory(String name) throws IllegalStateException {
		// create category instance
		Category cat = new Category();
		cat.setName(name);

		// save item in database
		saveObjectToDb(cat);
		return cat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#createItem(java.lang.String, java.lang.String,
	 * int, int)
	 */
	@Override
	public Item createItem(String title, String description, double price, int authorID, int categoryID)
			throws IllegalStateException {
		// create item instance
		Item item = new Item();
		item.setTitle(title);
		item.setDescription(description);
		item.setPrice(price);
		item.setAuthorID(authorID);
		item.setCategoryID(categoryID);
		item.setAltertionDate(new Date());
		item.setCreationDate(new Date());

		// save item in database
		saveObjectToDb(item);
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#createComment(int, java.lang.String, int)
	 */
	@Override
	public Comment createComment(int itemID, String text, int authorID) throws IllegalStateException {

		Comment comment = new Comment();

		comment.setText(text);
		comment.setAuthorID(authorID);
		comment.setAltertionDate(new Date());
		comment.setCreationDate(new Date());
		comment.setItemID(itemID);

		// save comment in database
		saveObjectToDb(comment);

		return comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#createUser(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public User createUser(String name, String email, String password, String rights) throws IllegalStateException {

		// create user instance
		User user = new User();
		user.setEmail(email);
		user.setJoinedDate(new Date());
		user.setName(name);
		user.setRights(rights);
		try {
			user.setPassword(PasswordHash.getSaltedHash(password));
		} catch (Exception e) {
			System.out.println("Creation of Hash failed");
			throw new IllegalStateException("Creation of Hash failed", e);
		}

		// save user in database
		saveObjectToDb(user);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#changeItem(int, java.lang.String,
	 * java.lang.String, int, int)
	 */
	@Override
	public Item changeItem(int itemID, String title, String description, double price, int authorID, int categoryID)
			throws IllegalStateException {
		Session session = openSession();
		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Item.class);
			cr.add(Restrictions.eq("id", itemID));
			List<Item> results = cr.list();

			Item item = results.get(0);
			item.setTitle(title);
			item.setDescription(description);
			item.setPrice(price);
			item.setAuthorID(authorID);
			item.setCategoryID(categoryID);
			item.setAltertionDate(new Date());

			session.update(item);

			// commit
			session.getTransaction().commit();

			return item;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the item list");
		} finally {
			// close session
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#changeComment(int, int, java.lang.String, int)
	 */
	@Override
	public Comment changeComment(int commentID, int itemID, String text, int authorID) throws IllegalStateException {
		Session session = openSession();
		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Comment.class);
			cr.add(Restrictions.eq("id", commentID));
			List<Comment> results = cr.list();

			Comment comment = results.get(0);
			comment.setText(text);
			comment.setAuthorID(authorID);
			comment.setAltertionDate(new Date());
			comment.setItemID(itemID);

			session.update(comment);

			// commit
			session.getTransaction().commit();

			return comment;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the item list");
		} finally {
			// close session
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#changeCategory(int, java.lang.String)
	 */
	@Override
	public Category changeCategory(int categoryID, String name) throws IllegalStateException {
		Session session = openSession();
		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Category.class);
			cr.add(Restrictions.eq("id", categoryID));
			List<Category> results = cr.list();

			Category cat = results.get(0);
			cat.setName(name);

			session.update(cat);

			// commit
			session.getTransaction().commit();

			return cat;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the category list");
		} finally {
			// close session
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#deleteItem(int)
	 */
	@Override
	public void deleteItem(int itemID) throws IllegalArgumentException {
		try {
			// get item
			Item item = getItemByID(itemID);
			Collection<Comment> comments = getAllCommentsFromItem(item.getId());
			for (Comment com : comments) {
				deleteComment(com.getId());
			}

			// delete item from database
			deleteObjectFromDb(item);
		} catch (IllegalArgumentException e) {
			System.out.println("deletion or getting item from ID failed");
			throw new IllegalArgumentException("deletion or getting item from ID failed", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#deleteComment(int)
	 */
	@Override
	public void deleteComment(int commentID) throws IllegalArgumentException {
		try {
			// get comment
			Comment comment = getCommentByID(commentID);

			// delete comment from database
			deleteObjectFromDb(comment);
		} catch (IllegalArgumentException e) {
			System.out.println("deletion or getting comment from ID failed");
			throw new IllegalArgumentException("deletion or getting comment from ID failed", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#deleteUser(int)
	 */
	@Override
	public void deleteUser(int userID) throws IllegalArgumentException {
		try {
			// get user
			User user = getUserByID(userID);

			// delete user from database
			deleteObjectFromDb(user);
		} catch (IllegalArgumentException e) {
			System.out.println("deletion or getting user from ID failed");
			throw new IllegalArgumentException("deletion or getting user from ID failed", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getAllCategories()
	 */
	@Override
	public Collection<Category> getAllCategories() throws IllegalStateException {
		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Category.class);
			List<Category> results = cr.list();

			// commit
			session.getTransaction().commit();

			return results;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the category list");
		} finally {
			// close session
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getAllItemsFromCategory(int)
	 */
	@Override
	public Collection<Item> getAllItemsFromCategory(int categoryID) throws IllegalStateException {
		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Item.class);
			cr.add(Restrictions.eq("categoryID", categoryID));
			List<Item> results = cr.list();

			// commit
			session.getTransaction().commit();

			return results;

		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the item list");
		} finally {
			// close session
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getAllCommentsFromItem(int)
	 */
	@Override
	public Collection<Comment> getAllCommentsFromItem(int itemID)
			throws IllegalArgumentException, IllegalStateException {
		Session session = openSession();

		try {

			// begin transaction
			session.beginTransaction();

			Criteria cr = session.createCriteria(Comment.class);
			cr.add(Restrictions.eq("itemID", itemID));
			List<Comment> results = cr.list();

			// commit
			session.getTransaction().commit();

			return results;

		} catch (IllegalArgumentException e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			System.out.println("no comment with this ID in the database");
			throw new IllegalArgumentException("no comment with this ID in the database");
		} catch (Exception e) {
			// Exception -> rollback
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the comment list");
		} finally {
			// close session
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.IDataHandler#getUserLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public User getUserLogin(String name, String password) throws IllegalStateException {
		Session session = openSession();

		// System.out.println("getUserLogin started");

		try {

			// begin transaction
			session.beginTransaction();

			// System.out.println("Transaction started");

			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("name", name));
			List<User> results = cr.list();

			// commit
			session.getTransaction().commit();

			// System.out.println("Transaction committed");

			// only one element in the list because the id is unique
			for (User user : results) {
				try {
					// System.out.println("PW check started");
					if (PasswordHash.check(password, user.getPassword()))
						// System.out.println("Return user");
						return user;
				} catch (Exception e) {
					// System.out.println("PW check failed");
					throw new IllegalStateException("Fail by checking the user password");
				}
			}
			// System.out.println("no users found");
		} catch (HibernateException e) {
			// Exception -> rollback
			// System.out.println("Error!!");
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting the user");
		} finally {
			// close session
			session.close();
		}
		// no appropriate user found in database
		return null;
	}

	@Override
	public Collection<User> getAllUsers() throws IllegalStateException {
		Session session = openSession();
		
		try {
			session.beginTransaction();
			Criteria cr = session.createCriteria(User.class);
			List<User> results = cr.list();
			session.getTransaction().commit();
			return results;
			
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting all users");
		} finally {
			session.close();
		}
	}

	@Override
	public User changeUser(int userID, String rights) throws IllegalStateException {
		Session session = openSession();
		
		try {
			User account = getUserByID(userID);
			
			account.setRights(rights);

			session.update(account);

			session.getTransaction().commit();

			return account;
			
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by changing the users");
		} finally {
			session.close();
		}
	}

	@Override
	public int getAdminCount() throws IllegalStateException {
Session session = openSession();
		
		try {
			session.beginTransaction();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("rights", "admin"));
			List<User> results = cr.list();
			session.getTransaction().commit();
			return results.size();
			
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new IllegalStateException("something went wrong by getting all users");
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteCategory(String categoryName) throws IllegalStateException {
		try {
			// get item
			Category category = getCategoryByName(categoryName);
			Collection<Item> items = getAllItemsFromCategory(category.getId());
			for (Item item : items) {
				deleteItem(item.getId());
			}

			// delete item from database
			deleteObjectFromDb(category);
		} catch (IllegalArgumentException e) {
			System.out.println("deletion or getting item from ID failed");
			throw new IllegalArgumentException("deletion or getting item from ID failed", e);
		}
	}

}