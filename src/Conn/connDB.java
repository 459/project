package Conn;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class connDB {
    public Connection conn = null;
    public Statement stm = null;
    public ResultSet res = null;
    private static String dbClassName ="com.mysql.jdbc.Driver";
	public static Connection getConnection() {
	    Connection conn = null;
	    try {
	      Class.forName(dbClassName).newInstance();
	      conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/aaproject","root","123456");
	    }
	    catch (Exception ee) {
	      ee.printStackTrace();
	    }
	    if (conn == null) {
	      System.err.println(
	          "Á´½ÓÊý¾Ý¿âÊ§°Ü.\r\n" +
	          dbClassName);
	    }
	    return conn;
	}
	public ResultSet executeQuery(String sql)
	{
		try {
			conn = getConnection();
			stm = (Statement) conn.createStatement();
			res = stm.executeQuery(sql);
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return res;
	}
	public int executeUpdate(String sql)
	{
		int result1 = 0;
		try {
			conn = getConnection();				
			stm = (Statement) conn.createStatement();
			result1 = stm.executeUpdate(sql);
		} catch (SQLException ex) {
			result1 = 0;
		}
		return result1;
	}
	public int executeDelete(String sql) {
		int result2 = 0;
		try {
			conn = getConnection();				
			stm = (Statement) conn.createStatement();
			result2 = stm.executeUpdate(sql);
		} catch (SQLException ex) {
			result2 = 0;
		}
		return result2;
	}
	public void close() {
		try {
			if (res != null) {
				res.close();
			}
			if (stm != null) {
				stm.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
