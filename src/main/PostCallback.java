package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class allows the user to post an ad to the ads table
 * 
 * @author George Coomber
 */

public class PostCallback implements Callback
{
	
	public PostCallback() {}
	
	// Prompts the user for a menu choice
	public void view()
	{	
		boolean validTitle = false;
		
		// Get the poster's email
		User user = User.getInstance();
		String poster = user.getEmail();
		
		// Set the pdate
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		String pdate = dateFormat.format(date);
		
		// Create a new aid for the ad
		String aid = createNewAid();
		
		if (aid == null)
		{
			System.out.println("ERROR: Creating a new aid.");
			return;
		}
		
		System.out.println(aid);
		
		// Prompt the user for an ad title
		
		
	}
	
	// Creates a new aid for the new ad
	public String createNewAid() {
		String newAid = "a001";
		
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null)
		{
			System.out.println("Unable to Connect to Server");
			return "";
		}

		// Create Statement
		Statement stmt;

		try
		{
			stmt = m_con.createStatement();
			
			// Search for users with name like the keyword (case insensitive)
			String query = "Select MAX(aid)"
					+ " From ads";
			
			ResultSet rs = stmt.executeQuery(query);
			
			// If the result set is not empty (there is at least one review)
			// set the new aid to the max aid plus 1
			if (rs.next())
			{
				newAid = IDGenerator.newID(rs.getString(1).trim());
			}
			
			rs.close();
			stmt.close();
			
		} catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
		
		return newAid;
	}
	
	@Override
	public int callback() {
		view();
		return Callback.OK;
	}
}