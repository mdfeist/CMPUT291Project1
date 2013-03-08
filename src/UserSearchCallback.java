import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This is where the user can query the database
 * for users.
 * 
 * @author George Coomber
 */

public class UserSearchCallback extends PageView
{
	public UserSearchCallback() {}
	
	public void view()
	{	
		boolean run = true;
		
		while(run)
		{
			// User can search for other users by email or name
			System.out.println("\nUser Search");
			System.out.println("e: search by email");
			System.out.println("n: search by name");
			System.out.println("q: to exit");
			
			String command = Menu.getKeyBoard();
			
			switch (command.toCharArray()[0])
			{
				case 'e':
					rows = searchEmail();
					break;
				
				case 'n':
					rows = searchName();
					pageView(5);
					break;
				
				case 'q':
					run = false;
					break;
					
				default:
					System.out.println("Error: Unknown command.");
					break;
			}
		}
	}
	
	// Query database using input email.
	public ArrayList<DatabaseRow> searchEmail()
	{
		ArrayList<DatabaseRow> rows = new ArrayList<DatabaseRow>();
		
		System.out.println("Enter Email Address");
		String keyword = Menu.getKeyBoard();
		
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null)
		{
			System.out.println("Unable to Connect to Server");
			return rows;
		}

		// Create Statement
		Statement stmt;

		try
		{
			stmt = m_con.createStatement();
			
			// Query the database and look for emails matching the keyword
			String query = "Select u.email, u.name, u.pass, u.last_login, COUNT(distinct a.aid), AVG(r.rating)"
					+ " From (users u LEFT OUTER JOIN ads a ON u.email = a.poster) LEFT OUTER JOIN reviews r ON r.reviewee = u.email" 
					+ " WHERE trim(email) LIKE '" + keyword.trim() + "'"
					+ " GROUP BY u.email, u.name, u.pass, u.last_login";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				UserData row = new UserData(
						rs.getString(1).trim(),
						rs.getString(2).trim(),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6));

				rows.add(row);
			}
			
			rs.close();
			stmt.close();
			
			// Print out the columns returned by the query (if any)
			System.out.println("Row  Email  Name  Last_Login  Ad_Count  AVG_Rating");
			if(rows.size() > 0)
			{
				System.out.println(rows.get(0).toString());
			}
			
		} catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}

		return rows;
	}
	
	// Query database using input name.
	public ArrayList<DatabaseRow> searchName()
	{
		ArrayList<DatabaseRow> rows = new ArrayList<DatabaseRow>();
		
		System.out.println("Enter Name");
		String keyword = Menu.getKeyBoard();
		
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null)
		{
			System.out.println("Unable to Connect to Server");
			return rows;
		}

		// Create Statement
		Statement stmt;

		try
		{
			stmt = m_con.createStatement();
			
			// Search for users with name like the keyword (case insensitive)
			String query = "Select u.email, u.name, u.pass, u.last_login, COUNT(distinct a.aid), AVG(r.rating)"
					+ " From (users u LEFT OUTER JOIN ads a ON u.email = a.poster) LEFT OUTER JOIN reviews r ON r.reviewee = u.email" 
					+ " WHERE UPPER(trim(name)) LIKE '%" + keyword.toUpperCase().trim() + "%'"
					+ " GROUP BY u.email, u.name, u.pass, u.last_login";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				UserData row = new UserData(
						rs.getString(1).trim(),
						rs.getString(2).trim());

				rows.add(row);
			}
			
			rs.close();
			stmt.close();
			
		} catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}

		return rows;
	}
	
	// The obtain column data for the row selected by the user.
	public void getRowInfo(String id)
	{
		
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
			
			// query the database for a user with email equal to id
			String query = "Select u.email, u.name, u.pass, u.last_login, COUNT(distinct a.aid), AVG(r.rating)"
					+ " From (users u LEFT OUTER JOIN ads a ON u.email = a.poster) LEFT OUTER JOIN reviews r ON r.reviewee = u.email" 
					+ " WHERE trim(email) LIKE '" + id + "'"
					+ " GROUP BY u.email, u.name, u.pass, u.last_login";

			ResultSet rs = stmt.executeQuery(query);

			// Print results to the screen
			if (rs.next())
			{
				System.out.println("Email: " + rs.getString(1));
				System.out.println("Name: " + rs.getString(2));
				System.out.println("Pass: " + rs.getString(3));
				System.out.println("Last_Login: " + rs.getString(4));
				System.out.println("Ad_Count " + rs.getString(5));
				System.out.println("Avg_Rating: " + rs.getString(6));
				
			} else
			{
				System.out.println("ERROR: Could not find user.");
			}

			rs.close();
			stmt.close();
			
			boolean run = true;
			
			while(run)
			{
				// The user can list review texts or write a review.
				System.out.println("\nOptions");
				System.out.println("l: list review texts");
				System.out.println("w: write a review for the user");
				System.out.println("q: to exit");
				
				String command = Menu.getKeyBoard();
				
				switch (command.toCharArray()[0])
				{
					case 'l':
						//rows = searchEmail();
						break;
					
					case 'w':
						//rows = searchName();
						break;
					
					case 'q':
						run = false;
						break;
						
					default:
						System.out.println("Error: Unknown command.");
						break;
				}
			}
			
		} catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
	}
	
	// Query the Database for all 
	public ArrayList<String> getReviewTexts(String id) {
		ArrayList<String> reviewTexts = new ArrayList<String>();
		String text;
		
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null)
		{
			System.out.println("Unable to Connect to Server");
			return reviewTexts;
		}

		// Create Statement
		Statement stmt;

		try
		{
			stmt = m_con.createStatement();
			
			// Search for users with name like the keyword (case insensitive)
			String query = "Select text"
					+ " From reviews" 
					+ " WHERE UPPER(trim(reviewee)) LIKE '" + id.toUpperCase().trim() + "'";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				text = rs.getString(1).trim();
				reviewTexts.add(text);
			}
			
			rs.close();
			stmt.close();
			
		} catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}

		return reviewTexts;
	}
	
	@Override
	public int callback() {
		view();
		return Callback.OK;
	}
}