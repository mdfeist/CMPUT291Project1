package main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This is where the user can list the ads
 * they have written.
 * 
 * @author Michael Feist
 */

public class ListCallback extends PageView
{
	public ListCallback() 
	{
		pageTitle = "My Ads";
	}
	
	public void view()
	{
		rows = getRows();
		pageView(5);
	}
	
	public void getRowInfo(String id)
	{
		
	}
	
	public ArrayList<DatabaseRow> getRows()
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
			
			String date = User.getInstance().getLoginDate();
			if (date == null)
			{
				return null;
			}
			
			String query = "SELECT aid, title, atype, price, pdate FROM ads";
			
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				UserAd row = new UserAd(
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