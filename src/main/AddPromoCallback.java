package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddPromoCallback extends PageView {

	private String aid = null;
	
	public AddPromoCallback(String id) 
	{
		aid = id;
		pageTitle = "Available Promotions";
	}
	
	public void view()
	{
		rows = getRows();
		pageView(5);
	}
	
	public void getRowInfo(String id)
	{
		String newPid = "p001";
		
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
			
			String query = "Select MAX(pur_id)"
					+ " From purchases";
			
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next())
			{
				newPid = IDGenerator.newID(rs.getString(1).trim());
			}
			
			rs.close();
			
			// Delete all purchases associated with ad
			query = "DELETE FROM purchases WHERE aid = '" + aid + "'";
			stmt = m_con.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			
			// Inserting new purchase
			query = "INSERT INTO purchases VALUES ('" + newPid +"', sysdate, '" + aid + "', '" + id + "')";
			stmt = m_con.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			
			stmt.close();
			
		} catch (SQLException e)
		{
			System.err.println("SQLException: " + e.getMessage());
		}
		stop();
	}
	
	public ArrayList<DatabaseRow> getRows()
	{
		ArrayList<DatabaseRow> rows = new ArrayList<DatabaseRow>();
		
		// Get connection to database
		Connection m_con = Database.getInstance().getConnection();

		// Check connection
		if (m_con == null) {
			System.out.println("Unable to Connect to Server");
			return null;
		}

		// Create Statement
		Statement stmt;

		try {
			stmt = m_con.createStatement();

			String query = "SELECT ono, ndays, price FROM offers";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				rows.add(new Offer(rs.getInt(1), rs.getInt(2), rs.getFloat(3)));
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		
		return rows;
	}
}
