/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Database Connection Information
 * Created Date : 07-Apr-2005
 * Modify Date  :
 *
 */
 
package com.ibank.dbconnection.application;

public class ConnectionBO {

	String sDriverName = "";
	String sDbUrl = "";
	String sUserName = "";
	String sPassword = "";
	
	/**
	 * @return
	 */
	public String getDbUrl() {
		return sDbUrl;
	}

	/**
	 * @param dbUrl
	 */
	public void setDbUrl(String dbUrl) {
		this.sDbUrl = dbUrl;
	}

	/**
	 * @return
	 */
	public String getDriverName() {
		return sDriverName;
	}

	/**
	 * @param driverName
	 */
	public void setDriverName(String driverName) {
		this.sDriverName = driverName;
	}
	/**
	 * @return
	 */
	public String getPassword() {
		return sPassword;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.sPassword = password;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return sUserName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.sUserName = userName;
	}
}
