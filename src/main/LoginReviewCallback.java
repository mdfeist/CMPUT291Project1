package main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Shows the user a list of their reviews.
 * 
 * @author Michael Feist
 */
public class LoginReviewCallback extends PageView {
	
	public LoginReviewCallback() 
	{
		pageTitle = "New Reviews";
	}
	
	public void view()
	{
		rows = getRows();
		
		if (rows != null)
		{
			if (rows.size() != 0)
			{
				pageView(3);
			}
			
			System.out.println("You have no new reviews.\n");
			return;
		}
		
		System.out.println("Unable to get data.\n");

	}
	
	
	public void getRowInfo(String id)
	{
		for (DatabaseRow r : rows)
		{
			if (r.getID().equals(id))
			{
				System.out.println(((Review) r).getText());
				
				System.out.println("\nHit return to continue:\n\n");
				Menu.getKeyBoard();
			}
		}
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
				date = "0001-01-01 00:00:00.0";
			}
			
			String query = "SELECT rno, rating, text, to_char(rdate,'YYYY-MM-DD HH24:MI:SS') " +
					"FROM reviews " +
					"WHERE to_char(rdate,'YYYY-MM-DD HH24:MI:SS') >= '" +
					date + "' " +
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
