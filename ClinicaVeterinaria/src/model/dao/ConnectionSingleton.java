package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//1616700 Diego Seronato - Alteracao da connection factory para ConnectionSingleton

public class ConnectionSingleton {
	private static ConnectionSingleton instance = null;
	
	private ConnectionSingleton() {
	}

	public static ConnectionSingleton getInstance(){
		if(instance == null)
			instance = new ConnectionSingleton();
		return instance;
	}
	
	public Connection getConnection() throws ClassNotFoundException {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/clinicaveterinariadb", "SA", "");
			conn.setAutoCommit(false);
			
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}