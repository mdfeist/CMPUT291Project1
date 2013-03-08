package main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This is where the user can query the database
 * for ads.
 * 
 * @author George Coomber and Michael Feist
 */

public class AdSearchCallback extends PageView
{
	public AdSearchCallback() 
	{
		pageTitle = "Search Results";
	}
	
	public void view()
	{
		System.out.println("Enter Keywords for Search:\n(example: free, repair)");
		
		String keywordInput = Menu.getKeyBoard();
		String[] keywords = keywordInput.split(",");
		
		rows = getRows(keywords);
		
		pageView(5);
	}
	
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
		}
	}
	
	public ArrayList<DatabaseRow> getRows(String[] keywords)
	{
		ArrayList<DatabaseRow> rows = new ArrayList<DatabaseRow>();
		
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

			String query = "SELECT aid, title, atype, price, pdate " + "FROM ads";

			// Clean up strings
			int i = 0;
			for (String key : keywords)
			{
				if (i == 0)
					query += " WHERE ";
				else
					query += " OR ";

				query += "lower(trim(title)) like '%" + key.trim().toLowerCase() + "%'"
						+ " OR lower(trim(descr)) like '%" + key.trim().toLowerCase() + "%'";
				i++;
			}

			query += " ORDER BY pdate DESC";
			
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				SearchAd row = new SearchAd(
						rs.getString(1).trim(),
						rs.getString(2).trim(),
						rs.getString(3).trim(),
						rs.getString(4).trim(),
						rs.getString(5).trim());

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
}
