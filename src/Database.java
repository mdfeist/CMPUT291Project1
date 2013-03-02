import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
	
	public static final String m_url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	public static final String m_driverName = "oracle.jdbc.driver.OracleDriver";

	public static final String m_userName = "mdfeist";
	public static final String m_password = "tiger291";
	
	public static Connection getConnection()
	{
		Connection m_con = null;
		
		try {
			Class<?> drvClass = Class.forName(m_driverName);
			DriverManager.registerDriver((Driver) drvClass.newInstance());
		} catch (Exception e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		
		try {
			m_con = DriverManager.getConnection(m_url, m_userName, m_password);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		
		return m_con;
	}
}
