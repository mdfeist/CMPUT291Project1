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
		
		ArrayList<String> rows = getRows(keywords);
		
		boolean run = true;
		int pageNumber = 1;
		int i = 0;
		while (run)
		{
			while (i < 5*pageNumber && i < rows.size())
			{
				System.out.println(rows.get(i));
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
					System.out.println("Error: Unknown command.");
					break;
			}
			
		}
	}
	
	
	public ArrayList<String> getRows(String[] keywords)
	{
		ArrayList<String> rows = new ArrayList<String>();
		
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

			String query = "SELECT * " + "FROM ads";

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

			// System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				String row = rs.getString(1).trim() + ", "
						+ rs.getString(2).trim() + ", "
						+ rs.getString(3).trim() + ", "
						+ rs.getString(4).trim() + ", "
						+ rs.getString(5).trim() + ", "
						+ rs.getString(6).trim() + ", "
						+ rs.getString(7).trim() + ", "
						+ rs.getString(8).trim() + ", "
						+ rs.getString(9).trim();

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
