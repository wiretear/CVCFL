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
import java.util.*;

public class ReadConnection {
    public static ResourceBundle oResourceBundle;
 	String sDriverName = "";
    String sDBUrl = "";
    String sUserName = "";
    String sPassword = "";
    public static ConnectionBO oConnectionBO = new ConnectionBO();	
    private static String sFileName = "NetBankingConnection";
	public ConnectionBO readPropertyFile() {
    	oResourceBundle = ResourceBundle.getBundle(sFileName);
       	for (Enumeration oEnumeration = oResourceBundle.getKeys(); oEnumeration.hasMoreElements();) {
			//For JDK version 1.5 or lower version (sPassword,sDbUrl,sDriverName,sUserName)
			//For JDK version 1.6 or higher version (sUserName,sDriverName,sPassword,sDBUrl)			
			sUserName = oResourceBundle.getString(oEnumeration.nextElement().toString());
			sDriverName = oResourceBundle.getString(oEnumeration.nextElement().toString());
			sDBUrl = oResourceBundle.getString(oEnumeration.nextElement().toString());
			sPassword = oResourceBundle.getString(oEnumeration.nextElement().toString());
			
			
			System.out.println("==>"+sUserName+"=="+sDriverName+"=="+sPassword+"=="+sDBUrl);
		}
		oConnectionBO.setUserName(sUserName);
		oConnectionBO.setPassword(sPassword);
		oConnectionBO.setDbUrl(sDBUrl);
		oConnectionBO.setDriverName(sDriverName);
        return oConnectionBO;
    }
    public static void main(String arg[]) {
    	ReadConnection oReadConnection = new ReadConnection();
        oReadConnection.readPropertyFile();
    }
}
