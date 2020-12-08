
/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Database Connection Information
 * Created Date : 07-Dec-2016
 * Modify Date  : 
 *
 */
 
package com.ibank.dbconnection.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Stack;

public class DBCP implements Runnable {
	private static DBCP connectionPool;
	private Stack pool;
	private Vector busyConnections;
	private int MAX_CONNECTIONS = 10;
	private int MIN_CONNECTIONS = 5;
	private long timeout;
	private String sDriver;
	private String sUrl;
	private String sUsername;
	private String sPassword;
	private static ReadConnection oReadConnection = new ReadConnection(); 
	private static ConnectionBO oConnectionBO = oReadConnection.readPropertyFile();
	
	private DBCP(String sDriver, String sUrl, String sUserName, String sPassword, int sMaxConnection, int sMinConnection, long sTimeOut)
		throws SQLException {
		this.timeout = sTimeOut;
		this.sDriver = sDriver;
		this.sUrl = sUrl;
		this.sUsername = sUserName;
		this.sPassword = sPassword;
		this.MAX_CONNECTIONS = sMaxConnection;
		this.MIN_CONNECTIONS = sMinConnection;
		busyConnections = new Vector();
		pool = new Stack();
		for (int i = 0; i < MIN_CONNECTIONS; i++) {
			pool.push(makeNewConnection());
		}
	}
	
	public static DBCP getInstance() {
		if (connectionPool == null) {
			try {
				connectionPool =
					new DBCP(
						oConnectionBO.getDriverName(),
						oConnectionBO.getDbUrl(),						
						oConnectionBO.getUserName(),
						oConnectionBO.getPassword(),
						10,
						5,
						30000);
			} catch (SQLException _sqlex) {
				_sqlex.printStackTrace();
			}
		}
		return connectionPool;
	}
	
	public synchronized Connection getConnection()throws SQLException, InterruptedException {
		
		Connection con = null;
		if (connectionPool != null) {
			if (pool.size() != 0) {
				con = (Connection) pool.pop();
				busyConnections.add(con);
			} else {
				if (getTotalConnections() == MAX_CONNECTIONS) {
					makeBackgroundConnection();
					wait();
					con = getConnection();
				} else {
					makeBackgroundConnection();
					wait();
					con = getConnection();
				}
			}
		}
		return con;
	}
	
	protected int getTotalConnections() {
		return pool.size() + busyConnections.size();
	}
	
	private void makeBackgroundConnection() throws SQLException {
		try {
			Thread connectionThread = new Thread(this);
			connectionThread.start();
		} catch (Exception _ex) {
			throw new SQLException("Max Limit of connections exceeded");
		}
	}
	
	private synchronized Connection makeNewConnection() throws SQLException {
		try {
			Class.forName(sDriver);
			Connection connection = DriverManager.getConnection(sUrl, sUsername, sPassword);
			connection.setAutoCommit(false);
			return connection;
		} catch (ClassNotFoundException cnfe) {
			throw new SQLException("Can't find class for driver: " + sDriver);
		}
	}
	
	/**
	 * run() function for making a new connection from the
	 * backgroud called by makeBackgroundConnection()
	 */
	
	public void run() {
		synchronized (this) {
			try {
				Connection con = makeNewConnection();
				pool.push(con);
				notifyAll();
			} catch (Exception _ex) {
				_ex.printStackTrace();
			}
		}
	}
	
	/**
	 *
	 * @return Information about this connection pool
	 */
	
	public synchronized void releaseConnection(Connection con) {

		pool.push(con);
		busyConnections.remove(con);
		notifyAll();

	}
	//test
	public synchronized void releaseReportConnection(Connection con) {

		//pool.push(con);
		busyConnections.remove(con);
		notifyAll();

	}
	
	public synchronized String toString() {
		String info =
			"ConnectionPool("
				+ sUrl
				+ ","
				+ sUsername
				+ ")"
				+ ", available="
				+ pool.size()
				+ ", busy="
				+ busyConnections.size()
				+ ", max="
				+ MAX_CONNECTIONS;
		return info;
	}
	/**
	 *
	 * @throws SQLException
	 */
	private synchronized void closeAllConnections() throws SQLException {
		while (!pool.isEmpty()) {
			try {
				((Connection) pool.pop()).close();
			} catch (Exception _ex) {
				throw new SQLException("Unable to close the Connection");
			}
		}
		pool = new Stack();
		for (int i = 0; i < busyConnections.size(); i++) {
			try {
				((Connection) busyConnections.get(i)).close();
				busyConnections.remove(i);
			} catch (Exception _ex) {
				throw new SQLException("Unable to close the Connection");
			}
		}
		busyConnections = new Vector();
	}
	/**
	 *
	 * @throws java.lang.Throwable
	 */
	protected void finalize() throws java.lang.Throwable {
		try {
			closeAllConnections();
		} catch (Exception _ex) {
		}
		super.finalize();
	}
}