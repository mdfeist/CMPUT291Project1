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
	private static char[] characters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
		'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	
	private static int numChars = characters.length;
	
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
	
	private int getIndex(char x)
	{
		int i = 0;
		for (char c : characters)
		{
			if (c == x)
				return i;
			
			i++;
		}
		
		return -1;
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
				char c1 = rs.getString(1).trim().charAt(0);
				char c2 = rs.getString(1).trim().charAt(1);
				char c3 = rs.getString(1).trim().charAt(2);
				char c4 = rs.getString(1).trim().charAt(3);
				
				int i1 = getIndex(c1);
				int i2 = getIndex(c2);
				int i3 = getIndex(c3);
				int i4 = getIndex(c4);
				
				if (i1 == -1 ||
						i2 == -1 ||
						i3 == -1 ||
						i4 == -1)
				{
					System.out.println("ERROR: Charactes in ad aid not known characters.");
					return null;
				}
				
				i4++;
				
				if (i4 >= numChars)
				{
					i4 = 0;
					i3++;
					
					if (i3 >= numChars)
					{
						i3 = 0;
						i2++;
						
						if (i2 >= numChars)
						{
							i2 = 0;
							i1++;
						}
					}
				}
				
				newAid = String.format("%c%c%c%c", 
						characters[i1], characters[i2], characters[i3], characters[i4]);
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