import java.sql.*;
public class Loaddriver {
	static DBConnector connector;
	private static String url;
	private static String database;
	private static String user;
	private static String pw;
	static DB_Daten daten ;
	private static boolean running;
	
	
	public boolean isRunning() {
		return running;
	}


	public void setRunning(boolean run) {
		running = run;
	}


	public static void main(String[] args) throws SQLException, InterruptedException {
		
		daten = new DB_Daten();
		url = daten.getUrl();
		database = daten.getDatabase();
		user = daten.getUser();
		pw = daten.getPw();
		DBConnector connector = new DBConnector(); 
		connector.connectToDB(url,database,user,pw);
		PreparedStatement pStmntBalance = connector.getConnection().prepareStatement("Select balance from accounts where accid=?");
		while(running)
		{
			
			int zZahl = (int)(Math.random()*100+1);
			if(zZahl <36)
			{
				int zAccId =(int)(Math.random()*10000000+1);
				pStmntBalance.setInt(1, zAccId);
				ResultSet rs = pStmntBalance.executeQuery();
				while(rs.next())
				{
					System.out.println(rs.getString(1));
				}
			}
			else if(zZahl<86)
			{
				
			}
			else
			{
				
			}
			Thread.sleep(50);
		}
		
	}

}
