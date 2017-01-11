package multipartyComputation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private String className = "oracle.jdbc.driver.OracleDriver";
	private String userName = "system";
	private String password = "12345";
	private String url = "jdbc:oracle:thin:@//127.0.0.1:1521/xe";
	private static ConnectionManager connectionInstance = null;
	
	public ConnectionManager(){
	  
	}
  
	public static synchronized ConnectionManager getInstance() {
		if(connectionInstance == null) {
			connectionInstance = new ConnectionManager(); 		
		}
	return connectionInstance;
	}
  
	public Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(className);
			conn = DriverManager.getConnection (url, userName, password);
		}  catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	 catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConnection(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
}
