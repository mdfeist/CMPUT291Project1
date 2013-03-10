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
	
	/* Allows the user to post an ad by providing ad type, ad title,
	* price, description, location, and category*/
	public void createAd()
	{	
		boolean validPrice = false;
		boolean validType = false;
		boolean validCat = false;
		String title = "";
		int price = 0;
		String priceString = "";
		String description = "";
		String location = "";
		String category = "";
		char type = '0';
		
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

		// Prompt the user for an ad title
		System.out.println("Enter Ad title:");
		title = Menu.getKeyBoard();
		
		// Prompt the user for an ad price
		while (validPrice == false)
		{
			System.out.println("Enter Ad Price:");
			priceString = Menu.getKeyBoard();
			
			// Ensure the price is an integer
			try
			{
				price = Integer.parseInt(priceString);
			} catch (NumberFormatException e)
			{
				System.out.println("Error: Price must be an integer");
				continue;
			}
			validPrice = true;
		}

		// Prompt the user for an Ad description
		System.out.println("Enter description:");
		description = Menu.getKeyBoard();

		// Prompt the user for an ad location
		System.out.println("Enter location:");
		location = Menu.getKeyBoard();
		
		while (validCat == false)
		{
			// Prompt the user to choose the category from a list
			CategoryCallback catCall = new CategoryCallback();
			catCall.view();
			
			category = catCall.getCat();
			
			if (category == null)
			{
				System.out.println("Error: Please select a category");
				continue;
			}
			validCat = true;
		}
		
		
		while (validType == false)
		{
			System.out.println("Enter ad type. S for sale, W for wanted:");
			String typeInput = Menu.getKeyBoard();
			
			if (typeInput.length() == 0)
			{
				System.out.println("Error: Ad type cannot be empty");
				continue;
			}
			else
			{
				type = typeInput.toUpperCase().charAt(0);
				if ( type != 'S' && type != 'W')
				{
					System.out.println("Error: Ad type must be S or W");
					continue;
				}
			}
			validType = true;
		}
		
		// Check if the strings are the right length for the table
		if (title.length() > 20 )
			title = title.substring(0,19);
		
		if (description.length() > 40 )
			description = description.substring(0, 39);
		
		if (location.length() > 15 )
			location = location.substring(0, 14);
		
		// Write the new Category to the database
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null)
		{
			System.out.println("Unable to Connect to Server");
			return;
		}

		// Create Statement
		Statement stmt;

		try
		{
			stmt = m_con.createStatement();

			// Write the review to the database in the reviews table
			String query = "INSERT INTO ads VALUES ('" + aid + "','"
					+ String.valueOf(type) + "','" + title + "','"
					+ String.valueOf(price) + "','" + description + "','" 
					+ location + "','" + pdate + "','" 
					+ category + "','" + poster +"')";

			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
		
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
		createAd();
		return Callback.OK;
	}
}