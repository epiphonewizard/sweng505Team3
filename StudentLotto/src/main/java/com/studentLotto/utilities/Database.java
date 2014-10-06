package com.studentLotto.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Elie This class is used to connect to our MySQL database, and perform
 *         relevant retrieve and update operations
 */
public class Database {
	private String driver;
	private String url;
	private String dbName;
	private String userName;
	private String password;
	private Connection conn;

	public Database() {
		url = "jdbc:mysql://ec2-54-68-180-44.us-west-2.compute.amazonaws.com:3306/";
		dbName = "StudentLotto";
		driver = "com.mysql.jdbc.Driver";
		userName = "root";
		password = "3e856cea";
	}

	public Connection getConnection() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		Class.forName(driver).newInstance();
		conn = DriverManager.getConnection(url + dbName, userName, password);

		return conn;
	}

	/**
	 * 
	 * @return returns the email for all users *Test method*
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public String getInfo() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM Account";

		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(selectSQL);
			ResultSet res = preparedStatement.executeQuery();

			while (res.next()) {
				String email = res.getString("email");
				System.out.println(email);
			}
			closeConnection();
		} finally {
			if (conn != null)
				closeConnection();
		}

		return "";
	}

	/**
	 * Checks if an activation code already exists. Also returns the email
	 * associated with a specific activation code
	 * 
	 * @param activationCodeP
	 *            the activation code param
	 * @return email associated with the activation code
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public String doesActivationCodeExist(String activationCodeP)
			throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		if (activationCodeP == null)
			throw new NullPointerException("Email or activation code is null");

		String email = "";
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM AccountActivation where activationCode = ?";

		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, activationCodeP);

			// execute select SQL statement
			ResultSet res = preparedStatement.executeQuery();
			// retrieve the email
			while (res.next()) {
				email = res.getString("email");
				// print it out *test*
				System.out.println(email);
			}
			// close conn
			closeConnection();
		} finally {
			if (conn != null)
				closeConnection();
		}

		return email;
	}

	/**
	 * Function to add an email with its associated code to the
	 * AccountActivation table
	 * 
	 * @param email
	 *            the email to add
	 * @param activationCode
	 *            the associated activation code for that email
	 * @return true if successful and false if not
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public boolean insertAccountActivationInfo(String email,
			String activationCode) throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		if (email == null || activationCode == null)
			throw new NullPointerException("Email or activation code is null");

		PreparedStatement preparedStatement = null;
		String selectSQL = "INSERT INTO AccountActivation (email,activationCode) VALUES (?,?)";

		try {
			conn = getConnection();
			if (conn != null) {
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, activationCode);

				preparedStatement.executeUpdate();
			} else {
				// this means we weren't able to get a connection!
				return false;
			}

			closeConnection();
		} finally {
			if (conn != null)
				closeConnection();
		}
		return true;
	}

	public void closeConnection() throws SQLException {
		conn.close();
	}

}
