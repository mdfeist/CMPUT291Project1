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
					pageView(5);
					break;
				
				case 'n':
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

			String query = "SELECT email, name, pass, last_login "
					+ "FROM users" 
					+ " WHERE trim(email) LIKE '%" + keyword.trim() + "%'";
			
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				UserData row = new UserData(
						rs.getString(1).trim(),
						rs.getString(2).trim(),
						rs.getString(3),
						rs.getString(4));

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
	
	public void getRowInfo(String id)
	{
		/*
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

			String query = "SELECT a.title, a.price, a.atype, a.pdate, " +
					"a.descr, a.location, a.cat, AVG(r.rating) " +
					"FROM ads a " +
					"LEFT OUTER JOIN reviews r ON a.poster = r.reviewee " +
					"WHERE trim(a.aid) like '" + id + "' " +
					"GROUP BY a.title, a.price, a.atype, a.pdate, " +
					"a.descr, a.location, a.cat";

			ResultSet rs = stmt.executeQuery(query);

			if (rs.next())
			{
				System.out.println("Title: " + rs.getString(1).trim());
				System.out.println("Price: " + rs.getString(2).trim());
				System.out.println("Type: " + rs.getString(3).trim());
				System.out.println("Date: " + rs.getString(4).trim());
				System.out.println("\nDescription:\n " + rs.getString(5).trim());
				System.out.println("\n\nLocation: " + rs.getString(6).trim());
				System.out.println("Category: " + rs.getString(7).trim());
				
				if (rs.getString(8) != null)
				{
					System.out.println("\nAverage Rating: " + rs.getString(8).trim());
				}
			} else
			{
				System.out.println("ERROR: Could not find ad.");
			}
			
			System.out.println("\nHit return to continue:\n\n");
			Menu.getKeyBoard();

			rs.close();
			stmt.close();

		} catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}*/
	}
	
	@Override
	public int callback() {
		view();
		return Callback.OK;
	}
}