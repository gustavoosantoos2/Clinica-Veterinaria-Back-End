package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/clinicaveterinariadb", "SA", "");
			conn.setAutoCommit(false);
			
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);// devemos logar esse erro
		}
	}
}