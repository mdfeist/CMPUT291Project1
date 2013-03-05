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

public class AdSearchCallback implements Callback
{
	public AdSearchCallback() {}
	
	public void view()
	{
		System.out.println("Enter Keywords for Search:\n(example: free, repair)");
		
		String keywordInput = Menu.getKeyBoard();
		String[] keywords = keywordInput.split(",");
		
		ArrayList<Ad> rows = getRows(keywords);
		
		boolean run = true;
		int pageNumber = 1;
		int i = 0;
		while (run)
		{
			while (i < 5*pageNumber && i < rows.size())
			{
				System.out.println((i + 1) + ": " + rows.get(i).toString());
				i++;
			}
			
			System.out.println("\nSearch Result Options");
			System.out.println("s: to select");
			System.out.println("p: previous 5");
			System.out.println("n: next 5");
			System.out.println("q: to exit");
			
			String command = Menu.getKeyBoard();
			
			switch (command.toCharArray()[0])
			{
				case 's':
					System.out.println("Enter the add number:");
					String input = Menu.getKeyBoard();
					
					Integer adNumber = Integer.valueOf(input);
					
					if (adNumber == null)
					{
						System.out.println("Error: Not an Integer.");
						return;
					}
					
					int row = adNumber.intValue();
					
					if (row >= 1 && row <= rows.size())
					{
						getRowInfo(rows.get(row - 1).getAid());
					}
					
					i = 5*(pageNumber - 1);
					break;
					
				case 'p':
					if (pageNumber > 1)
					{
						pageNumber--;
						i = 5*(pageNumber - 1);
					} else
					{
						i = 5*(pageNumber - 1);
					}
					break;
					
				case 'n':
					if (i < rows.size())
					{
						i = 5*pageNumber;
						pageNumber++;
					} else
					{
						i = 5*(pageNumber - 1);
					}
					break;
					
				case 'q':
					run = false;
					break;
					
				default:
					i = 5*(pageNumber - 1);
					System.out.println("Error: Unknown command.");
					break;
			}
			
		}
	}
	
	public void getRowInfo(String aid)
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
					"WHERE trim(a.aid) like '" + aid + "' " +
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
	
	public ArrayList<Ad> getRows(String[] keywords)
	{
		ArrayList<Ad> rows = new ArrayList<Ad>();
		
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

				query += "trim(title) like '%" + key.trim() + "%'"
						+ " OR trim(descr) like '%" + key.trim() + "%'";
				i++;
			}

			query += " ORDER BY pdate DESC";
			
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				Ad row = new Ad(
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
	
	
	@Override
	public int callback() {
		view();
		return Callback.OK;
	}
}
