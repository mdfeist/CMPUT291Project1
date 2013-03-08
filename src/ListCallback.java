import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This is where the user can list the ads
 * they have written.
 * 
 * @author George Coomber and Michael Feist
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
			
			String query = "SELECT rno, rating, text, rdate " +
					"FROM reviews " +
					"WHERE trunc(rdate) >= TO_DATE('" + 
					date.substring(0, date.length() - 2) + 
					"', 'yyyy-mm-dd hh24:mi:ss') " +
					"AND reviewee = '" +
					User.getInstance().getEmail() +
					"' " +
					"ORDER BY rdate DESC";
			
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				Review row = new Review(
						rs.getString(1).trim(),
						rs.getFloat(2),
						rs.getString(3).trim(),
						rs.getString(4).trim());

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