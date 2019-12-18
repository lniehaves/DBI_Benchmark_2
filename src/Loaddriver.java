import java.sql.*;
public class Loaddriver extends Thread {
	static DBConnector connector;
	static DB_Daten daten ;
	private static boolean running;
	Statement stmnt ;
	private int txCount;
	double t1;
	
	public Loaddriver() throws SQLException
	{
		daten = new DB_Daten();
		connector = new DBConnector(); 
		connector.connectToDB(daten.getUrl(),daten.getDatabase(),daten.getUser(),daten.getPw());
		System.out.println("verbunden");
		stmnt = connector.getConnection().createStatement();
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean run) {
		running = run;
	}

	public void run()
	{
		t1 = System.currentTimeMillis();
		this.setRunning(true);
		txCount = 0;
			try {
				transactions();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void transactions() throws SQLException, InterruptedException {	
		while(System.currentTimeMillis()-t1 <= 2400 && running) {
			
				//Zufallszahl zur Bestimmung der Transaktion
				int zZahl = (int)(Math.random()*100+1);
				if(zZahl <36)
				{
					this.txBalance();
				}
				else if(zZahl<86)
				{
					this.txDeposit();
				}
				else
				{
					this.txAnalysis();
				}
				Thread.sleep(50);
			
		}
		System.out.println("Starte Transaktionenmessungen"+this.toString());
		while(running)
		{
			//Zufallszahl zur Bestimmung der Transaktion
			int zZahl = (int)(Math.random()*100+1);
			if(zZahl <36)
			{
				this.txBalance();
				txCount++;
			}
			else if(zZahl<86)
			{
				this.txDeposit();
				txCount++;
			}
			else
			{
				this.txAnalysis();
				txCount++;
			}
			Thread.sleep(50);
			if(System.currentTimeMillis()-t1 >= 540000)
			{
				this.setRunning(false);
				System.out.println(txCount +" Transaktionen"+this.toString());
				System.out.println(txCount / 300+" Transaktionen pro Sekunde");
			}
		}
		
	}

	public double txBalance() throws SQLException
	{
		ResultSet rs = null;
		int accid =(int)(Math.random()*10000000+1) ;
		try {
			rs = stmnt.executeQuery("select balance from accounts where accid ="+accid);

		}
		catch (Exception e) {
			e.printStackTrace();
 
		}
		if(rs.next())
		{
			return rs.getDouble("balance");
		}
		else return 0;
	}
	
	public double txDeposit() throws SQLException
	{
		ResultSet rs = null;
		int accid =(int)(Math.random()*10000000+1) ;
		int branchid = (int)(Math.random()*100+1);
		int tellerid =(int)(Math.random()*1000+1) ;
		double delta = (Math.random()*10000+1);
		
		
		try {

			stmnt.executeUpdate("update branches set balance ="+delta+" where branchid = "+branchid);
			stmnt.executeUpdate("update tellers set balance = "+delta+" where tellerid = "+tellerid);
			stmnt.executeUpdate("update accounts set balance = "+delta+" where accid = "+accid);
			stmnt.executeUpdate("insert into history (accid, tellerid, delta, branchid, accbalance,cmmnt) VALUES("+accid+","+tellerid+","+delta+","+branchid+",(select balance from accounts where accid ="+accid+"),' ')");
			rs = stmnt.executeQuery("select balance from accounts where accid = " + accid);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(rs.next())
		{
			return rs.getDouble("balance");
		}
		else return 0;
	}
	
	public ResultSet txAnalysis() throws SQLException
	{
		ResultSet rs = null;
		double delta = (Math.random()*10000+1);
		try {

			rs = stmnt.executeQuery("select * from history where delta ="+delta);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public int getTxCount() {
		return txCount;
	}
	
}
