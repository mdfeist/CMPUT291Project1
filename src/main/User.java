package main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This singleton class handles the user information.
 * 
 * @author Michael Feist
 */
public class User {

	// Static Instance
	private static User _instance = null;

	// Instance Variables
	private String _email;
	private String _lLogin;
	private boolean _isValid;

	private User() {
		_isValid = false;
	}

	public static User getInstance() {
		if (_instance == null)
			_instance = new User();

		return _instance;
	}

	public boolean userExist(String email) {
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null) {
			System.out.println("Unable to Connect to Server");
			return false;
		}

		// Create Statement
		Statement stmt;
		
		// Check if user is found
		boolean userFound = false;

		try {
			stmt = m_con.createStatement();

			String query = "SELECT email " + "FROM users "
					+ "WHERE trim(email) like '" + email + "'";

			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				String e = rs.getString(1).trim();

				if (e.equals(email)) {
					userFound = true;
				}
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}

		return userFound;
	}

	public void login(String email, String password) {
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null) {
			System.out.println("Unable to Connect to Server");
			return;
		}

		// Create Statement
		Statement stmt;
		
		// Check if user is found
		boolean userFound = false;

		try {
			stmt = m_con.createStatement();
			
			// Query
			String query = "SELECT email, pwd, last_login " + "FROM users "
					+ "WHERE trim(email) like '" + email + "'"
					+ "AND trim(pwd) like '" + password + "'";

			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				String e = rs.getString(1).trim();
				String p = rs.getString(2).trim();
				
				_lLogin = rs.getString(3);
				
				if (_lLogin != null)
				{
					_lLogin = _lLogin.trim();
				}
				
				// Check if email and password are correct
				if (e.equals(email) && p.equals(password)) {
					userFound = true;
				}
			}
			
			rs.close();
			stmt.close();

			// Check if user was found
			if (!userFound) {
				System.out.println("Incorrect Username or Password\n");
			} else {
				_isValid = true;
				_email = email;
				
				stmt = m_con.createStatement();
				stmt.executeUpdate("UPDATE users SET last_login = sysdate WHERE trim(email) like '" + email + "'");
				stmt.close();
				
				System.out.println("You are logged in as " + email);
				
				LoginReviewCallback review = new LoginReviewCallback();
				review.view();
			}

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}
	/**
	 * Create a new user and adds them to the database.
	 * @param email between 0 and 20 characters.
	 * @param name between 0 and 20 characters.
	 * @param password between 0 and 4 characters.
	 */
	public void create(String email, String name, String password) {
		
		if (email.length() > 20)
		{
			System.out.println("Email must be less than or equal to 20 characters.");
			return;
		}
		
		if (name.length() > 20)
		{
			System.out.println("Name must be less than or equal to 20 characters.");
			return;
		}
		
		if (password.length() > 4)
		{
			System.out.println("Password must be less than or equal to 4 characters.");
			return;
		}
		
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null) {
			System.out.println("Unable to Connect to Server");
			return;
		}

		// Create Statement
		Statement stmt;

		try {
			stmt = m_con.createStatement();
			
			// Query
			String query = "INSERT INTO users VALUES ('" + email + "','" + name + "','" + password +"', sysdate)";
			
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		
		// Check if user was created
		if (userExist(email))
		{
			_isValid = true;
			_email = email;
		}
	}
	
	public void logout()
	{
		_isValid = false;
		_email = null;
	}
	
	public void setValid(boolean valid) {
		_isValid = valid;
	}

	public boolean isValid() {
		return _isValid;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public String getEmail() {
		return _email;
	}
	
	public String getLoginDate() {
		return _lLogin;
	}
}
