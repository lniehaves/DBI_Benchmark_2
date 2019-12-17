import java.sql.*;

public class DBConnector {
	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void connectToDB(String host,String database,String user, String pw)
	{
		
		try {
			String connectionCommand = "jdbc:mysql://"+host+"/"+database+"?user="+user+"&password="+pw;
			connection = DriverManager.getConnection(connectionCommand);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			
		}
	
	}
	

}
