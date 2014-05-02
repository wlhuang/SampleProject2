package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	public Connection createConnection() throws Exception{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con = DriverManager.getConnection("jdbc:odbc:invoice");
		return con;
	}
	public void closeConnection(Connection con) throws SQLException{
		con.close();
	}
	public ResultSet selectStatement(Connection con,String sqlStatement) throws SQLException{
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		return rs;		
	}	
}
