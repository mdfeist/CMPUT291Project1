package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import main.Database;
import main.User;

import org.junit.Test;


public class UserTest
{

	@Test
	public void test()
	{
		// Test to many charaters in email
		User.getInstance().create("123456789012345678901", "name", "test");
		assertTrue(!User.getInstance().isValid());
		
		// Test to many charaters in name
		User.getInstance().create("email", "123456789012345678901", "test");
		assertTrue(!User.getInstance().isValid());
		
		// Test to many charaters in password
		User.getInstance().create("email", "name", "test1");
		assertTrue(!User.getInstance().isValid());
		
		// Create Test User
		User.getInstance().create("test@ujiji.com", "Test User", "test");
		assertTrue(User.getInstance().isValid());
		
		User.getInstance().logout();
		assertTrue(!User.getInstance().isValid());
		
		User.getInstance().login("test@ujiji.com", "test");
		assertTrue(User.getInstance().isValid());
		
		// Clean up database
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

			String query = "DELETE FROM users WHERE trim(email) = 'test@ujiji.com'";

			stmt.executeUpdate(query);
			
			stmt.close();

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}

}
