package com.ibank.dbconnection.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 *
 * @author Administrator
 */
public class DBCPNewConnection {

  public static ResourceBundle oResourceBundle;
  static  String sDriverName = "";
  static  String sDBUrl = "";
  static  String sUserName = "";
  static  String sPassword = "";
	
  private static String sFileName = "NetBankingConnection";
  public static Connection getConnection() {
	  oResourceBundle = ResourceBundle.getBundle(sFileName);
	  for (Enumeration oEnumeration = oResourceBundle.getKeys(); oEnumeration.hasMoreElements();) {
		  sUserName = oResourceBundle.getString(oEnumeration.nextElement().toString());
		  sDriverName = oResourceBundle.getString(oEnumeration.nextElement().toString());	
		  sDBUrl = oResourceBundle.getString(oEnumeration.nextElement().toString());
		  sPassword = oResourceBundle.getString(oEnumeration.nextElement().toString());		
		 
	  }
	  System.out.println("==>"+sUserName+"=="+sDriverName+"=="+sPassword+"=="+sDBUrl);
	  Connection con = null;
		
	  try {
		  // load the Driver Class
		  Class.forName(sDriverName);
		  // create the connection now
		  con = DriverManager.getConnection(sDBUrl,sUserName,sPassword);
			
	  } catch (ClassNotFoundException e) {
		  e.printStackTrace();
	  } catch (SQLException e) {
		  e.printStackTrace();
	  }
	  return con;
  }
   public static void releaseConnection(Connection con) {
		  try {
			  con.close();
		  } catch (SQLException ex) {
			  Logger.getLogger(DBCPNewConnection.class.getName()).log(Level.SEVERE, null, ex);
		  }
	  }

}
