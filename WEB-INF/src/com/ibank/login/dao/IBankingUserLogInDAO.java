/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Sign In
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.login.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import com.ibank.dbconnection.application.DBCPNewConnection;
import com.ibank.login.bo.IBankingUserLogInBO;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IBankingUserLogInDAO {
	DBCPNewConnection oPool = new DBCPNewConnection();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public IBankingUserLogInBO getPhysicalAddress(String sOperatingSystem) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		oConn = oPool.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT MyBank.DFN_MAC_ADDRESS('"+ sOperatingSystem +"') PHYSICAL_ADDRESS FROM DUAL" );
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oLogInBO.setPhysicalAddress(oRs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getPhysicalAddress(String sOperatingSystem, String sPhysicalAddressInformation) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		oConn = oPool.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT MyBank.DFN_MAC_INFORMATION('"+ sOperatingSystem +"', '"+ sPhysicalAddressInformation +"') PHYSICAL_ADDRESS FROM DUAL" );
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oLogInBO.setPhysicalAddress(oRs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {
				
				}
		} finally {
			if (oRs != null) {
				oRs.close();
			}
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getIPAddress() throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		oConn = oPool.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT MyBank.DFN_IP_ADDRESS('SERVER') IP_SERVER, MyBank.DFN_IP_ADDRESS('CLIENT') IP_CLIENT FROM DUAL");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oLogInBO.setIPAddressServer(oRs.getString(1));
				oLogInBO.setIPAddressClient(oRs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getCustomerProfileInfoPro(String sMailID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_IB_CUSTOMER_PROFILE(?,?,?,?,?,?,?,?,?,?)}");
	//	CallableStatement oStmt = oConn.prepareCall("{call ibnkdb.DPR_IB_CUSTOMER_PROFILE(?,?,?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sMailID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(9, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();	
			oLogInBO.setUserName(oStmt.getString(3));
			oLogInBO.setUserAddress(oStmt.getString(4));
			oLogInBO.setUserContact(oStmt.getString(5));
			oLogInBO.setUserEMail(oStmt.getString(6));
			oLogInBO.setUserNationalID(oStmt.getString(7));	
			oLogInBO.setStatusflag(oStmt.getString(8));			
			oLogInBO.setErrorCode(""+oStmt.getInt(9));
			oLogInBO.setErrorMessage(oStmt.getString(10));
		//	System.out.println("oLogInForm.getUserName Doo == >> "+oStmt.getString(3));

		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getPermissionMailIDCheckPro(String sInternalCardID, String sUserID, String sPassword, String sPhysicalAddressServer, String sPhysicalAddressClient, String sRemoteIPAddress) throws Exception {
		Connection oConn = null;
		oConn = oPool.getConnection();
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		String sNewUserID = null;
		String sErrorCode = null;
		String sSssionID = null;
		//CallableStatement oStmt = oConn.prepareCall("{call ibnkdb.DPR_USER_SIGNIN(?,?,?,?,?,?,?,?,?,?,?,?)}");
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_USER_SIGNIN(?,?,?,?,?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sPassword);
		oStmt.setString(3, sInternalCardID);
		oStmt.setString(4, sPhysicalAddressServer);
		oStmt.setString(5, sPhysicalAddressClient);
		oStmt.setString(6, sRemoteIPAddress);
		oStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(11, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(12, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			sNewUserID = "" + oStmt.getString(7);
			oLogInBO.setNewUserID(sNewUserID);
			sSssionID = "" + oStmt.getString(8);
			oLogInBO.setSessionID(sSssionID);
			oLogInBO.setCustomerCode(oStmt.getString(9));
			oLogInBO.setLoginFlag(oStmt.getString(10));
			sErrorCode = "" + oStmt.getInt(11);
			oLogInBO.setErrorCode(sErrorCode);
			oLogInBO.setErrorMessage( oStmt.getString(12));
			System.out.print("LoginFlag ==>>>>>" +oStmt.getString(10));	
		
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}catch (Exception E) {
				
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getUserPermissionCheckPro(String sInternalCardID,
														 String sUserID,
														 String sSessionID,
														 String sRemoteIPAddress,
														 String sActionPathName,
														 String sCompanyCode,
														 String sBranchCode,
														 String sBranchActionPathName) throws Exception {
		Connection oConn = null;
		oConn = oPool.getConnection();
		String sErrorCode = null;
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_MYBANK_USER_CHECK(?,?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyCode);
		oStmt.setString(4, sBranchCode);
		oStmt.setString(5, sActionPathName);
		oStmt.setString(6, sBranchActionPathName);
		oStmt.setString(7, sRemoteIPAddress);
		oStmt.setString(8, sInternalCardID);
		oStmt.registerOutParameter(9, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(9);
			oLogInBO.setErrorCode(sErrorCode);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}	
	
	
	public IBankingUserLogInBO getCompanyInfoPro(String sUserID, String sSessionID) throws Exception {
		Connection oConn = null;
		String sErrorCode = null;
		oConn = oPool.getConnection();
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_MYBANK_COMPANY(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			sErrorCode = "" + oStmt.getInt(3);
			oLogInBO.setErrorCode(sErrorCode);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getCompanyInfo(String sUserID, String sSessionID) throws Exception {
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		ArrayList aCompanyList = new ArrayList();
		ArrayList aCompanyNameList = new ArrayList();
		Connection oConn = oPool.getConnection();
		Statement oStmt = oConn.createStatement();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(COMPCODE,' '), NVL(COMPNAME,' ')" );
			sql.append("FROM MyBank.SY_COMPANY " );
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				aCompanyList.add(oRs.getString(1));
				aCompanyNameList.add(oRs.getString(2));
			}
			oLogInBO.setCompanyCodeList(aCompanyList);
			oLogInBO.setCompanyCodeNameList(aCompanyNameList);
		} catch (Exception sq) {
			sq.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			if (oRs != null) {
				oRs.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	
	
	public IBankingUserLogInBO getMessageInformation(String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList tmpList = new ArrayList();
		oConn = oPool.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(ERRMSG,' ') " );
			sql.append("FROM ibnkdb.SY_MESSAGE " );
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				IBankingUserLogInBO oLogInListBO = new IBankingUserLogInBO();
				oLogInListBO.setErrorMessage(oRs.getString(1));
				tmpList.add(oLogInListBO);
			}
			oLogInBO.setList(tmpList);
		} catch (Exception e) {

			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception excp) {

				excp.printStackTrace();

			}
		} finally {
			if (oRs != null) {
				oRs.close();
			}
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO setMessagePro(String sUserID,String sSessionID,String sMessageCode, String sMessage, String sSerialNo)throws Exception {
		Connection oConn = null;
		oConn = oPool.getConnection();
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_SET_MESSAGE(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sMessageCode);
		oStmt.setString(3, sMessage);
		oStmt.setString(4, sSessionID);
		oStmt.setString(5, sSerialNo);
		try {
			oStmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}

	public IBankingUserLogInBO getPermissionUserCheckPro(String sInternalCardID,String sUserID,String sSessionID,String sRemoteIPAddress,String sActionPathName,String sCompanyCode,String sBranchCode,String sBranchActionPathName) throws Exception {
		Connection oConn = null;
		oConn = oPool.getConnection();
		String sErrorCode = null;
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_MYBANK_USER_CHECK(?,?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyCode);
		oStmt.setString(4, sBranchCode);
		oStmt.setString(5, sActionPathName);
		oStmt.setString(6, sBranchActionPathName);
		oStmt.setString(7, sRemoteIPAddress);
		oStmt.setString(8, sInternalCardID);
		oStmt.registerOutParameter(9, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(9);
			oLogInBO.setErrorCode(sErrorCode);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
		public ArrayList getMyBankMenu(String sUserID, String sSessionID){
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTmpList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		try {
			oConn = oPool.getConnection();
			oStmt = oConn.createStatement();
			sql = new StringBuffer();
			sql.append("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL, TRIM(SUBSTR(NODENAME,1,9))  ");
			sql.append("FROM ibnk.SY_MAINMENU  ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
				oLogInBO.setNodeId(oRs.getString(1));
				oLogInBO.setParentId(oRs.getString(2));
				if (oRs.getString(5).equals("Enquiries")){
					oLogInBO.setNodeName("Enquiries");
				}else{
					oLogInBO.setNodeName(oRs.getString(3));
				}
				oLogInBO.setUrl(oRs.getString(4));
				aTmpList.add(oLogInBO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		} finally {
			oPool.releaseConnection(oConn);
		}
		return aTmpList;
	}
	
	public IBankingUserLogInBO getMenuList(String sUserID,String sSessionID) throws Exception {
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		Connection oConn = null;
		Statement oStmt = null;
		oConn = oPool.getConnection();
		oStmt = oConn.createStatement();
		ArrayList aMenuList = new ArrayList();
		ArrayList aMenuNameList = new ArrayList();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(URL,' '), NVL(NODENAME,' ')");
			sql.append("FROM MyBank.SY_FAVORITES ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				aMenuList.add(oRs.getString(1));
				aMenuNameList.add(oRs.getString(2));


			}
			oLogInBO.setMenuList(aMenuList);
			oLogInBO.setMenuNameList(aMenuNameList);

		} catch (Exception sq) {
			sq.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			if (oRs != null) {
				oRs.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getLogOutMyBankPro(String sUserID,String sSessionID,String sRemoteIPAddress)throws Exception {
		Connection oConn = null;
		oConn = oPool.getConnection();
		String sErrorCode = null;
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		CallableStatement oStmt = oConn.prepareCall("{call ibnkdb.DPR_USER_SIGN_OUT(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.registerOutParameter(4, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(4);
			oLogInBO.setErrorCode(sErrorCode);
			oLogInBO.setErrorMessage(oStmt.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getUserImage(String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = oPool.getConnection();
		oStmt = oConn.createStatement();
		oConn.setAutoCommit(false);
		BLOB img ;
		byte[] imgData = null ;
		StringBuffer sql = new StringBuffer();
		String sFulPathName = "";
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT UIMAGE, PATHNAME, URLPATHNAME || FILENAME FULL_URL_FILENAME, FILENAME  " );
			sql.append("FROM ibankingup.SY_IMAGE " );
			sql.append("WHERE MAILID='");
			sql.append(sUserID);
			sql.append("' AND SESSIONID ='");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next ()){
				img = ((OracleResultSet)oRs).getBLOB(1);
				imgData = img.getBytes(1,(int)img.length());
				oLogInBO.setImageWritePath(oRs.getString(2));
				oLogInBO.setImageReadPath(oRs.getString(3));
				oLogInBO.setImageFileName(oRs.getString(4));
				sFulPathName =oLogInBO.getImageWritePath() + oLogInBO.getImageFileName();

				String sCreateDirectory = "";
				sCreateDirectory =oLogInBO.getImageWritePath();
				File fDirectoryName = new File(sCreateDirectory);
				boolean isDirectoryName = fDirectoryName.exists();

				if (isDirectoryName == true){
					sCreateDirectory = "";
				}else if (isDirectoryName == false){
					isDirectoryName = (new File(sCreateDirectory)).mkdirs();
				}
				FileOutputStream oFos=new FileOutputStream(sFulPathName);  //will retrieve bytes and create a file
				oFos.write(imgData);
				oConn.setAutoCommit(true);
				oConn.commit();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public String getReadPath(String sUserID, String sSessionID){
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		StringBuffer sql = new StringBuffer();
		String sReadPath = "";
		try {
			oConn = oPool.getConnection();
			oStmt = oConn.createStatement();
			sql = new StringBuffer();
			sql.append("SELECT URLPATHNAME || FILENAME FULL_URL_FILENAME FROM ibnkdb.SY_IMAGE " );
			sql.append("WHERE MAILID='");
			sql.append(sUserID);
			sql.append("' AND SESSIONID ='");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next ()){
				sReadPath =oRs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		} finally {
			oPool.releaseConnection(oConn);
		}
		return sReadPath;
	}
	
	public IBankingUserLogInBO getSessionCheckPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = oPool.getConnection();
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_CLIENT_SESSION_VERIFY(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oLogInBO.setErrorCode(""+oStmt.getInt(3));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {	}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	public IBankingUserLogInBO getClearPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = oPool.getConnection();
		IBankingUserLogInBO oLogInBO = new IBankingUserLogInBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_ACCOUNT_PROFILE_CLEAR(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oLogInBO.setErrorCode(""+oStmt.getInt(3));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			}
			catch (Exception E) {}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			oPool.releaseConnection(oConn);
		}
		return oLogInBO;
	}
	
	/*  
	  public IBankingUserLogInBO getExecuteStatementProAPI(String sAccountNumber, 
			  											   String sFromDate, 
			  											   String sToDate) throws JSONException, ClientProtocolException, IOException {

		  String url;
	        String url2;
	        String str ="";
	        String str1 ="";
	        String errorCode = "";
	        String errorMsg = "";
	        String pErrorFlag = "";
	        String pErrorMessage = "";
	        int StatusCode = 0;
	        IBankingUserLogInBO oIBankingUserLogInBO = new IBankingUserLogInBO();
	 try {     
	        OkHttpClient client = new OkHttpClient().newBuilder().build();
	        MediaType mediaType = MediaType.parse("text/plain");
	        RequestBody body = RequestBody.create(mediaType, "");
	        Request request = new Request.Builder().url("http://192.168.200.52:8080/efs/emob/account/statement").method("POST", body)
	          .addHeader("pAcNo", sAccountNumber)
	          .addHeader("pFromDate", sFromDate)
	          .addHeader("pToDate", sToDate)
	          //.addHeader("Cookie", "ORA_WWV_USER_218214708090307=ORA_WWV-fHzrA9UlfL8RVErC21LfNf3g")
	          .build();
	        Response response = client.newCall(request).execute(); 
	        String responseString = response.body().string();
	        System.out.println("response ===>>> "+responseString);

	        JSONObject responseJson = new JSONObject(responseString);
	        str = responseJson.getString("pOutJsnAcStat");

	        JSONObject secondReasponse = new JSONObject(str);
	        str1 = secondReasponse.getString("statment");
	        // Set JSON Array
	        JSONArray arry = new JSONArray(str1);	        
	        ArrayList data = new ArrayList();
	        

	        
	        

	        for (int i = 0; i < arry.length(); i++) {

	        	IBankingUserLogInBO oIBankingUserLogInBOList = new IBankingUserLogInBO();
	            JSONObject object = arry.getJSONObject(i);
	            oIBankingUserLogInBOList.setSerialNo(object.getString("SL_NO"));
	            oIBankingUserLogInBOList.setCredit(object.getString("CREDIT"));
	            oIBankingUserLogInBOList.setDebit(object.getString("DEBIT"));
	            oIBankingUserLogInBOList.setDocnum(object.getString("DOC_NUM"));
	            String dateString = object.getString("VALUE_DATE");
		        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		        Date d = sdf.parse(dateString);
		        sdf.applyPattern(NEW_FORMAT);
		        newDateString = sdf.format(d);
	            oIBankingUserLogInBOList.setValueDate(newDateString);
	            oIBankingUserLogInBOList.setBalance(object.getString("BALANCE"));
	            oIBankingUserLogInBOList.setRemarks(object.getString("NARRATION"));

	            data.add(oIBankingUserLogInBOList);
	        }

	        oIBankingUserLogInBO.setStatementList(data);
	        oIBankingUserLogInBO.setErrorCode("0");

	      }catch(Exception ex) {
	    	  
	    	  oIBankingUserLogInBO.setErrorCode("1");
	    	  oIBankingUserLogInBO.setErrorMessage("Server not found.." +StatusCode);
	      }
	        
	        
	        return oIBankingUserLogInBO;
	        
	    }
	  
	  
	  
	  public IBankingUserLogInBO getUserAccountListProAPI(String sNewUserID) throws JSONException, ClientProtocolException, IOException {

					String url;
					String url2;
					String str ="";
					String str1 ="";
		
					int StatusCode = 0;
					IBankingUserLogInBO oIBankingUserLogInBO = new IBankingUserLogInBO();
			try {     
					OkHttpClient client = new OkHttpClient().newBuilder().build();
					MediaType mediaType = MediaType.parse("text/plain");
					RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder().url("http://192.168.200.52:8080/efs/emob/customer/account_list").method("POST", body)
					.addHeader("pCustCode", sNewUserID)					
					//.addHeader("Cookie", "ORA_WWV_USER_218214708090307=ORA_WWV-fHzrA9UlfL8RVErC21LfNf3g")
					.build();
			Response response = client.newCall(request).execute(); 
			String responseString = response.body().string();
					System.out.println("response ===>>> "+responseString);
					
				JSONObject responseJson = new JSONObject(responseString);
					str = responseJson.getString("pOutJsnAcList");
					
				JSONObject secondReasponse = new JSONObject(str);
					str1 = secondReasponse.getString("account_list");
					// Set JSON Array
					JSONArray arry = new JSONArray(str1);	        
					ArrayList data = new ArrayList();
					
					final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";
					final String NEW_FORMAT = "dd-MM-yyyy";
								
					
			for (int i = 0; i < arry.length(); i++) {
					
					IBankingUserLogInBO oIBankingUserLogInBOList = new IBankingUserLogInBO();
					JSONObject object = arry.getJSONObject(i);
					oIBankingUserLogInBOList.setSerialNo(object.getString("AC_NO"));
					oIBankingUserLogInBOList.setCredit(object.getString("AC_TITLE"));					
					data.add(oIBankingUserLogInBOList);
			}
					
					oIBankingUserLogInBO.setStatementList(data);
					oIBankingUserLogInBO.setErrorCode("0");
					
			}catch(Exception ex) {
					
					oIBankingUserLogInBO.setErrorCode("1");
					oIBankingUserLogInBO.setErrorMessage("Server not found.." +StatusCode);
			}
					
					
		return oIBankingUserLogInBO;
					
		}*/
	
	public IBankingUserLogInBO getPINChange(String sUserID,
									  String sSessionID,
									  String sCompanyID,
									  String sBranchID,
									  String sOprMode,
									  String sMyUserID,
									  String sOldPassword,
									  String sNewPassword,
									  String sConfPassword,
									  String sPasswordExpiredDays) throws Exception {
									  	
		   IBankingUserLogInBO oIBankingUserLogInBO = new IBankingUserLogInBO();
			Connection oConn = null;
			oConn = oPool.getConnection();
			CallableStatement oStmt = oConn.prepareCall("{call ibankingup.dpr_user_security_code_mod(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						oStmt.setString(1, sUserID);
						oStmt.setString(2, sSessionID);
						oStmt.setString(3, sCompanyID);
						oStmt.setString(4, sBranchID);
						oStmt.setString(5, sOprMode);
						oStmt.setString(6, sMyUserID);
						oStmt.setString(7, sOldPassword);
						oStmt.setString(8, sNewPassword);
						oStmt.setString(9, sConfPassword);
						oStmt.setString(10, sPasswordExpiredDays);
						oStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
						oStmt.registerOutParameter(12, java.sql.Types.INTEGER);
						oStmt.registerOutParameter(13, java.sql.Types.VARCHAR);
			try {
						oStmt.execute();
						//	oMyPINChangeBO.setList.(""+oStmt.getString(10));
						oIBankingUserLogInBO.setErrorCode(""+oStmt.getInt(12));
						oIBankingUserLogInBO.setErrorMessage(oStmt.getString(13));
						
			} catch (Exception e) {
						e.printStackTrace();
				try {
						oConn.rollback();
						}
				catch (Exception E) {}
				}finally {
					if (oStmt != null) {
						oStmt.close();
						}
					oPool.releaseConnection(oConn);
						}
	return oIBankingUserLogInBO;
		}
	

}