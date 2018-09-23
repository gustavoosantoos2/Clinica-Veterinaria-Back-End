package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//1616700 Diego Seronato - Alteracao da connection factory para ConnectionSingleton

public class ConnectionSingleton {
	private static ConnectionSingleton instance = null;
	private Connection conn = null;
	
	private ConnectionSingleton() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/clinicaveterinariadb", "SA", "");
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static ConnectionSingleton getInstance(){
		if(instance == null)
			instance = new ConnectionSingleton();
		return instance;
	}
	
	public Connection getConnection() throws ClassNotFoundException {
		return conn;
	}
}