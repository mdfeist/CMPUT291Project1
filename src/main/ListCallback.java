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
			
			String query = "SELECT a.aid, a.title, a.atype, a.price, a.pdate, " +
					"(CASE WHEN p.ono is NOT NULL " +
					"THEN 'Yes' " +
					"ELSE 'No' " +
					"END) as Purchases, " +
					"(CASE WHEN ((TO_DATE(p.start_date) + o.ndays) - SYSDATE) > 0 " +
					"THEN CEIL(((TO_DATE(p.start_date) + o.ndays) - SYSDATE)) " +
					"ELSE 0 END) as daysLeft " +
					"FROM ads a " +
					"LEFT OUTER JOIN purchases p ON a.aid = p.aid " +
					"LEFT OUTER JOIN offers o ON p.ono = o.ono " +
					"WHERE trim(a.poster) = '" + User.getInstance().getEmail() + "'";
			
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				UserAd row = new UserAd(
						rs.getString(1).trim(),
						rs.getString(2).trim(),
						rs.getString(3).trim(),
						rs.getFloat(4),
						rs.getString(5).trim(),
						rs.getString(6).trim(),
						rs.getInt(7));

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