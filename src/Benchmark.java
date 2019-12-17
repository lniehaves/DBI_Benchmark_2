import java.sql.*;

public class Benchmark {
	static Statement stmnt;
	static DBConnector connector;
	private static String url;
	private static String database;
	private static String user;
	private static String pw;
	static DB_Daten daten ;
	
	public static void main(String[] args) throws SQLException {
		//initialisiere Daten
		daten = new DB_Daten();
		url = daten.getUrl();
		database = daten.getDatabase();
		user = daten.getUser();
		pw = daten.getPw();
		
		
		//Starte run
		double t1 = System.currentTimeMillis();

		//connect to DB
		DBConnector connector = new DBConnector(); 
		connector.connectToDB(url,database,user,pw);
		System.out.println("verbunden: "+ (System.currentTimeMillis()-t1)+" ms");
		stmnt = connector.getConnection().createStatement();

	}

}
