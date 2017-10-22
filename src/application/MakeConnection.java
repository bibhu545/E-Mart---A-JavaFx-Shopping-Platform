package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MakeConnection 
{
	static Connection con = null;
	MakeConnection() throws Exception
	{
		String url = "jdbc:mysql://localhost:3306/shopping";
		String username = "root";
		String password = "";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			con = DriverManager.getConnection(url,username,password);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
	}
}
