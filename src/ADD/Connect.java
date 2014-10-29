package ADD;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/aaproject";
	private String user = "root";
	private String password = "123456";
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Connection getConnection(){
		try{
			Class.forName(driverName);
			return DriverManager.getConnection(url, user, password);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	} 
}

