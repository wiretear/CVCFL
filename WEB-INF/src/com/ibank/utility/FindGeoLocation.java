/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Geo Location
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.utility;

import com.ibank.login.bo.IBankingUserLogInBO;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.ibank.dbconnection.application.DBCPNewConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.net.URLConnection;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;

public class FindGeoLocation {
	//DBCP oPool = DBCP.getInstance();

	public IBankingUserLogInBO getGeoLocation(String sIPAddress) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		IBankingUserLogInBO oIBankingLogInBO = new IBankingUserLogInBO();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		String sCityName = "";
		String sCountryCode = "";
		String sContinentCode = "";
		String sRegionName = "";
		String sAreaCode = "";
		String sDmaCode = "";
		String sLatitude = "";
		String sLongitude = "";
		String sTimeZone = "";
		String sOrganizationName = "";
		StringBuffer sql = new StringBuffer();
		try {
			//String geoLocationSite = "http://www.geoplugin.net/xml.gp?"; // Testing purpose only
			String geoLocationSite = "http://www.geoplugin.net/xml.gp?ip=" +sIPAddress;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = getDocument( db, geoLocationSite );
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("geoPlugin");

			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					 Element fstElmnt = (Element) fstNode;

					 NodeList cityNameList = fstElmnt.getElementsByTagName("geoplugin_city");
					 Element cityNameElement = (Element) cityNameList.item(0);
					 NodeList cityName = cityNameElement.getChildNodes();
					 sCityName = ((Node) cityName.item(0)).getNodeValue();
			
					 NodeList regionNameList = fstElmnt.getElementsByTagName("geoplugin_region");
					 Element regionNameElement = (Element) regionNameList.item(0);
					 NodeList regionName = regionNameElement.getChildNodes();
					 sRegionName = ((Node) regionName.item(0)).getNodeValue();
			
					 NodeList areaCodeNameList = fstElmnt.getElementsByTagName("geoplugin_areaCode");
					 Element areaCodeNameElement = (Element) areaCodeNameList.item(0);
					 NodeList areaCodeName = areaCodeNameElement.getChildNodes();
					 sAreaCode = ((Node) areaCodeName.item(0)).getNodeValue();
			
					 NodeList dmaCodeNameList = fstElmnt.getElementsByTagName("geoplugin_dmaCode");
					 Element dmaCodeNameElement = (Element) dmaCodeNameList.item(0);
					 NodeList dmaCodeName = dmaCodeNameElement.getChildNodes();
					 sDmaCode = ((Node) dmaCodeName.item(0)).getNodeValue();
			
					 NodeList countryCodeNameList = fstElmnt.getElementsByTagName("geoplugin_countryCode");
					 Element countryCodeNameElement = (Element) countryCodeNameList.item(0);
					 NodeList countryCodeName = countryCodeNameElement.getChildNodes();
					 sCountryCode = ((Node) countryCodeName.item(0)).getNodeValue();
			
					 NodeList continentCodeNameList = fstElmnt.getElementsByTagName("geoplugin_continentCode");
					 Element continentCodeNameElement = (Element) continentCodeNameList.item(0);
					 NodeList continentCodeName = continentCodeNameElement.getChildNodes();
					 sContinentCode = ((Node) continentCodeName.item(0)).getNodeValue();
			
					 NodeList latitudeNameList = fstElmnt.getElementsByTagName("geoplugin_latitude");
					 Element latitudeNameElement = (Element) latitudeNameList.item(0);
					 NodeList latitudeName = latitudeNameElement.getChildNodes();
					 sLatitude = ((Node) latitudeName.item(0)).getNodeValue();
			
					 NodeList longitudeNameList = fstElmnt.getElementsByTagName("geoplugin_longitude");
					 Element longitudeNameElement = (Element) longitudeNameList.item(0);
					 NodeList longitudeName = longitudeNameElement.getChildNodes();
					 sLongitude = ((Node) longitudeName.item(0)).getNodeValue();
				}
				getGeoLocationPlace(sCityName, sCountryCode, sContinentCode, sRegionName, sAreaCode, sDmaCode, sLatitude, sLongitude, sTimeZone, sIPAddress, sOrganizationName);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			try {
				oConn.rollback();
				}
			catch (Exception E) {}
		} finally {
			if (oRs != null) {
				oRs.close();
			}
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
			}
		return oIBankingLogInBO;
	}
	public IBankingUserLogInBO getGeoLocationPlace(String sCityName, String sCountryCode, String sContinentCode,
									   String sRegionName, String sAreaCode, String sDmaCode, String sLatitude, 
									   String sLongitude, String sTimeZone, String sIPAddress, String sOrganizationName) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		IBankingUserLogInBO oIBankingLogInBO = new IBankingUserLogInBO();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		String sStreet = "";
		try {
			String geoLocationPlace = "http://www.geoplugin.net/extras/location.gp?lat="+sLatitude+"&long="+sLongitude+"&format=xml";
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = getDocument( db, geoLocationPlace );
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("geoPlugin");
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					Element fstElmnt = (Element) fstNode;

					NodeList streetNameList = fstElmnt.getElementsByTagName("geoplugin_place");
					Element streetNameElement = (Element) streetNameList.item(0);
					NodeList streetName = streetNameElement.getChildNodes();
					sStreet = ((Node) streetName.item(0)).getNodeValue();
				}
			}
			sql = new StringBuffer();
			sql.append("INSERT INTO MyBank.SY_IP_TABLE(STREET, CITY, REGION, COUNTRY, CONTINENT, IP_ADDRESS, ORG_NAME, AREA_CODE, LONGITUDE, LATITUDE, TIMEZONES,DMA_CODE) VALUES ('"+sStreet+"','"+sCityName+"','"+sRegionName+"','"+sCountryCode+"','"+sContinentCode+"','"+sIPAddress+"','"+sOrganizationName+"','"+sAreaCode+"','"+sLongitude+"','"+sLatitude+"','"+sTimeZone+"','"+sDmaCode+"')");
			oRs = oStmt.executeQuery(sql.toString());
			oConn.commit();
		} catch (Exception e) {
			//e.printStackTrace();
			try {
				oConn.rollback();
				}
			catch (Exception E) {}
		} finally {
			if (oRs != null) {
				oRs.close();
			}
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
			}
		return oIBankingLogInBO;
	}
	private static Document getDocument( DocumentBuilder db, String urlString ) {
		try {
			URL url = new URL( urlString );
			try {
				URLConnection URLconnection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
				int responseCode = httpConnection.getResponseCode();
				if ( responseCode == HttpURLConnection.HTTP_OK) {
					InputStream in = httpConnection.getInputStream();
					try {
						Document doc = db.parse( in );
						return doc;
					} catch( org.xml.sax.SAXException e ) {
						//e.printStackTrace();
					}
				} 
			} catch ( IOException e ) {
				//e.printStackTrace();
			}
		} catch ( MalformedURLException e ) {
			//e.printStackTrace();
		}
		return null;
	}
}
