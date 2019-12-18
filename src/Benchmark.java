import java.sql.*;

public class Benchmark {
	static Statement stmnt;
	static DBConnector connector;

	static DB_Daten daten ;
	
	public static void main(String[] args) throws SQLException {
		//initialisiere Daten
		daten = new DB_Daten();

		
		
		//Starte run
		double t1 = System.currentTimeMillis();

		//connect to DB
		connector = new DBConnector(); 
		connector.connectToDB(daten.getUrl(),daten.getDatabase(),daten.getUser(),daten.getPw());
		System.out.println("verbunden: "+vergangeneZeit(t1) +" ms");
		stmnt = connector.getConnection().createStatement();
		stmnt.executeQuery("delete from history");
		System.out.println("history geleert");
		

		System.out.println("Beginne mit Transaktionen");
		for(int i =0; i<5;i++)
		{
			Loaddriver ld = new Loaddriver();
			ld.start();
		}
		
		if(vergangeneZeit(t1)>=600000)
		{
			System.exit(0);
		}
		
	}

	public static double vergangeneZeit(double startTime)
	{
		return System.currentTimeMillis()-startTime;
	}
	
}
