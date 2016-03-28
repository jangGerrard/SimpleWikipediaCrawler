package kr.ac.kw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBFactory {
	private static DBFactory instance ; 
	
	private String jdbcClassName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/data_mining";
	private String id = "mining_user";
	private String password = "mining_user";
	
	private DBFactory(){
		try {
			Class.forName(jdbcClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DBFactory getInstance(){
		if(instance == null){
			instance = new DBFactory();
		}
		return instance ;
	}
	
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, id, password);
	}

}
