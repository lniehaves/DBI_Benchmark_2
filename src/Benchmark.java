import java.sql.*;

public class Benchmark {

	static String url = "192.168.122.70";
	static String database = "dbi_benchmark";
	static String user = "root";
	static String pw = "root";
	static Statement stmnt;
	
	public static void main(String[] args) throws SQLException {
		//Starte run
		double t1 = System.currentTimeMillis();

				
		//connect to DB

		DBConnector connector = new DBConnector(); 
		connector.connectToDB(url, database, user, pw);
		stmnt = connector.connection.createStatement();
		System.out.println("verbunden: "+ (System.currentTimeMillis()-t1)+" ms");

	}

}
