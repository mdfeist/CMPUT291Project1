package test;
import static org.junit.Assert.*;

import java.sql.Connection;

import main.Database;

import org.junit.Test;


public class TestConnection {

	@Test
	public void test() {
		
		System.out.println("Connecting to " + Database.m_url);
		System.out.println("With user " + Database.m_userName);
		
		Connection m_com = Database.getInstance().getConnection();
		
		if (m_com == null)
			fail("ERROR: Could not connect to server.");
		
	}

}
