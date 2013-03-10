package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Callback class that lists the existing categories in the Categories
 * table. The user chooses a category from the list to get the cat string.
 * 
 * @author George Coomber
 *
 */
public class CategoryCallback extends PageView
{
	private String category = null;
	
	public CategoryCallback(){
		pageTitle = "Select Category";
	}
	
	/**
	 *  Lists the rows from the Category table
	 */
	public void view()
	{	
		rows = getRows();
		
		// Lists the rows and allows the user to select the row
		pageView(5);
	}
	
	/**
	 *  Retrieves all rows from the Category table
	 */
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

			String query = "SELECT cat FROM categories";
			
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
			{
				Category row = new Category(
						rs.getString(1).trim());

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
	
	
	public void getRowInfo(String id) {
		category = id;
	}
	
	public String getCat() {
		return category;
	}
	
	public void selectCommand() {
		super.selectCommand();
		stop();
	}
	
}
