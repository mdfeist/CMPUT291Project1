import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

	// Static Instance
	private static User _instance = null;

	// Instance Variables
	private String _email;
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
		Connection m_con = Database.getConnection();

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

		// Close Connection
		try {
			m_con.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}

		return userFound;
	}

	public void login(String email, String password) {
		// Get connection to database
		Connection m_con = Database.getConnection();

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
			String query = "SELECT email, pass " + "FROM users "
					+ "WHERE trim(email) like '" + email + "'"
					+ "AND trim(pass) like '" + password + "'";

			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				String e = rs.getString(1).trim();
				String p = rs.getString(2).trim();
				
				// Check if email and password are correct
				if (e.equals(email) && p.equals(password)) {
					userFound = true;
				}
			}

			// Check if user was found
			if (!userFound) {
				System.out.println("Incorrect Username or Password\n");
			} else {
				_isValid = true;
				_email = email;

				System.out.println("You are logged in as " + email);
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}

		// Close Connection
		try {
			m_con.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}
	
	public void create(String email, String name, String password) {
		// Get connection to database
		Connection m_con = Database.getConnection();

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
			String query = "INSERT INTO users VALUES ('" + email + "','" + name + "','" + password +"',null)";
			
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}

		// Close Connection
		try {
			m_con.close();
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
}
