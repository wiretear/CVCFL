package com.ibank.loan.report.dao;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.ibank.dbconnection.application.DBCPNewConnection;
import com.ibank.loan.report.bo.AccountSummaryReportBO;
import com.ibank.loan.report.bo.AccountSummaryReportBO;
import com.ibank.login.bo.IBankingUserLogInBO;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountSummaryReportDAO {

	private WritableCellFormat sTextArialBold;
	private WritableCellFormat sTextTime;
	private WritableCellFormat sNumberFloat;
	private WritableCellFormat sTextTimeBoldUnderline;	
	//DBCP oPool = DBCP.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public AccountSummaryReportBO getPermissionCheckPro(String sUserID,			String sSessionID,
											   String sRemoteIPAddress,	String sClientActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_IBNK_CLIENT_USER_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oAccountSummaryReportBO.setErrorCode(""+oStmt.getInt(5));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {	}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getMenuCheckPro(String sUserID, 			String sSessionID, 
											   String sRemoteIPAddress,	String sClientActionPath) throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_CLIENT_MAIN_MENU_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oAccountSummaryReportBO.setErrorCode("" + oStmt.getInt(5));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getSessionCheckPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_CLIENT_SESSION_VERIFY(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oAccountSummaryReportBO.setErrorCode(""+oStmt.getInt(3));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {	}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getMessageInformation(String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList tmpList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(ERRMSG,' ')");
			sql.append("FROM IBNK.SY_MESSAGE ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				AccountSummaryReportBO oTransactionListBO = new AccountSummaryReportBO();
				oTransactionListBO.setErrorMessage(oRs.getString(1));
				tmpList.add(oTransactionListBO);
			}
			oAccountSummaryReportBO.setList(tmpList);
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
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getMenuList(String sUserID,String sSessionID) throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ArrayList aMenuList = new ArrayList();
		ArrayList aMenuNameList = new ArrayList();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(URL,' '), NVL(NODENAME,' ')");
			sql.append("FROM IBNK.SY_FAVORITES ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' AND MENUTYPE = 'CLIMOD' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				aMenuList.add(oRs.getString(1));
				aMenuNameList.add(oRs.getString(2));
			}
			oAccountSummaryReportBO.setMenuList(aMenuList);
			oAccountSummaryReportBO.setMenuNameList(aMenuNameList);
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
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getClearPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_ACCOUNT_PROFILE_CLEAR(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oAccountSummaryReportBO.setErrorCode(""+oStmt.getInt(3));
			oAccountSummaryReportBO.setErrorMessage(oStmt.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {	}
		}finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getTransactionPro(String sUserID,String sSessionID)throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_RUNNING_ACTNUM(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(3);
			oAccountSummaryReportBO.setErrorCode(sErrorCode);
			oAccountSummaryReportBO.setErrorMessage(oStmt.getString(4));
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
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getTransactionBalPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo)throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_BALANCE_ACTNUM(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(5);
			oAccountSummaryReportBO.setErrorCode(sErrorCode);
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
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getAccountData(String sUserID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		ArrayList aAccountInfoList = new ArrayList();
		ArrayList aAccountInfoNameList = new ArrayList();
		
		ArrayList aDisplayFrequencyList = new ArrayList();
		ArrayList aDisplayFrequencyNameList = new ArrayList();
		ArrayList aDisplayRequestFrequencyList = new ArrayList();
		ArrayList aDisplayRequestFrequencyNameList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
			String str ="";
			String str1 ="";
			
			int StatusCode = 0;
		IBankingUserLogInBO oIBankingUserLogInBO = new IBankingUserLogInBO();
	try {     
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder().url("http://10.11.200.61:8085/cvcflera/apitest/customer/account_list").method("POST", body)
			.addHeader("pCustCode", sUserID)	
			.addHeader("pAcType", "ALL")
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
			aAccountInfoNameList.add(object.getString("AC_NO"));	
			aAccountInfoNameList.add(object.getString("AC_TITLE")+"-"+object.getString("AC_NO")+"-"+"("+object.getString("AC_STATUS")+")");
			//data.add(oIBankingUserLogInBOList);
		}
		
			oAccountSummaryReportBO.setAccountInfoList(aAccountInfoList);
			oAccountSummaryReportBO.setAccountInfoNameList(aAccountInfoNameList);
			oAccountSummaryReportBO.setErrorCode("0");
			
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(ADD_MONTHS(SYSDATE,-1),'dd/mm/rrrr') FIRSTDATE, ");
			sql.append("TO_CHAR(SYSDATE,'dd/mm/rrrr') LASTDATE  ");
			sql.append("FROM DUAL");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAccountSummaryReportBO.setDateFrom(oRs.getString(1));
				oAccountSummaryReportBO.setDateTo(oRs.getString(2));
			}
			
		
			
	} catch (Exception sq) {
			sq.printStackTrace();
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
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getCompanyCode(String sAccountInfo) throws Exception {
	
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT SUBSTR ('"+sAccountInfo+"', 1, INSTR ('"+sAccountInfo+"', '-') - 1) comcod, SUBSTR ('"+sAccountInfo+"', INSTR ('"+sAccountInfo+"', '-') + 1) actnum ");
			sql.append("FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAccountSummaryReportBO.setCompanyCode(oRs.getString(1));
				oAccountSummaryReportBO.setAccountNo(oRs.getString(2));
			}
		} catch (Exception sq) {
			sq.printStackTrace();
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
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getTransactionSearchPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNumber,String sDateFrom,String sDateTo)throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_RUNNING_BALANCE_INFO(?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNumber);
		oStmt.setString(5, sDateFrom);
		oStmt.setString(6, sDateTo);
		oStmt.registerOutParameter(7, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(7);
			oAccountSummaryReportBO.setErrorCode(sErrorCode);
			oAccountSummaryReportBO.setErrorMessage(oStmt.getString(8));
		
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
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getTransactionPreviewPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_TRANSACTION_PREVIEW(?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.setString(5, sDateFrom);
		oStmt.setString(6, sDateTo);
		oStmt.registerOutParameter(7, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oAccountSummaryReportBO.setErrorCode(""+oStmt.getInt(7));
			oAccountSummaryReportBO.setErrorMessage(oStmt.getString(8));
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
		return oAccountSummaryReportBO;
	}
	
	public AccountSummaryReportBO getTransactionPreviewPro1(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_TRANSACTION_PREVIEWXL(?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.setString(5, sDateFrom);
		oStmt.setString(6, sDateTo);
		oStmt.registerOutParameter(7, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oAccountSummaryReportBO.setErrorCode(""+oStmt.getInt(7));
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
		return oAccountSummaryReportBO;
	}
	
	public AccountSummaryReportBO getTransactionInformation(String sUserID,String sSessionID) throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTempCustomerList = new ArrayList();
		ArrayList aTempStatementList = new ArrayList();
		ArrayList aTempRequestList = new ArrayList();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(ACTYPE,' '), NVL(TRNCOD,' ')");
			sql.append("FROM IBNK.DT_ACT_CUSTOMER_INFO ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				AccountSummaryReportBO oTransactionCustomerInfoListBO = new AccountSummaryReportBO();
				oTransactionCustomerInfoListBO.setTitle(oRs.getString(1));
				oTransactionCustomerInfoListBO.setCustomerInfo(oRs.getString(2));
				aTempCustomerList.add(oTransactionCustomerInfoListBO);
			}
			oAccountSummaryReportBO.setCustomerInfoList(aTempCustomerList);

			sql = new StringBuffer();
			sql.append("SELECT  NVL(VALDAT,' '), NVL(CHQNUM,' '), NVL(REMARK,' '), ");
			sql.append("NVL(DBAMFC,' '), NVL(CRAMFC,' '), NVL(CURBAL,' '), NVL(SERNUM,0) ");
			sql.append("FROM IBNK.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				AccountSummaryReportBO oTransactionStatementListBO = new AccountSummaryReportBO();
				oTransactionStatementListBO.setDateInfo(oRs.getString(1));
				oTransactionStatementListBO.setChqNumber(oRs.getString(2));
				oTransactionStatementListBO.setParticulars(oRs.getString(3));
				oTransactionStatementListBO.setDebitAmount(oRs.getString(4));
				oTransactionStatementListBO.setCreditAmount(oRs.getString(5));
				oTransactionStatementListBO.setBalanceAmount(oRs.getString(6));
				oTransactionStatementListBO.setSerialNo(oRs.getString(7));
				aTempStatementList.add(oTransactionStatementListBO);
			}
			oAccountSummaryReportBO.setStatementList(aTempStatementList);

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
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getMaxRecord(String sUserID,String sSessionID) throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM IBNK.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAccountSummaryReportBO.setDisplayTotalRecord(oRs.getString(1));
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
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getCountRecord(String sUserID,String sSessionID) throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM IBNK.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAccountSummaryReportBO.setDisplayTotalRecord(oRs.getString(1));
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
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getMaxRecordRequest(String sUserID,String sSessionID) throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM IBNK.DT_ACT_REQUEST ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAccountSummaryReportBO.setDisplayRequestTotalRecord(oRs.getString(1));
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
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getDisplayRecordPro(String sDisplayAction, String sDisplayInLastRecord, String sDisplayInTotalRecord, String sDisplayFrequency)throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call IBNK.DPR_DISPLAY_RECORD(?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sDisplayAction);
		oStmt.setString(2, sDisplayInLastRecord);
		oStmt.setString(3, sDisplayInTotalRecord);
		oStmt.setString(4, sDisplayFrequency);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(6, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(8, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oAccountSummaryReportBO.setDisplayOutFirstRecord(""+oStmt.getInt(5));
			oAccountSummaryReportBO.setDisplayOutLastRecord(""+oStmt.getInt(6));
			oAccountSummaryReportBO.setDisplayOutMessage(oStmt.getString(7));
			oAccountSummaryReportBO.setErrorCode(""+oStmt.getInt(8));
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
		return oAccountSummaryReportBO;
	}
	
	
	public AccountSummaryReportBO getFileName() throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTmpList = new ArrayList();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(SYSDATE,'DDMMRRRRHH24MMSS') FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAccountSummaryReportBO.setFile(oRs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oRs != null) {
				oRs.close();
			}
			if (oStmt != null) {
				oStmt.close();
			}
		DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}	

	public AccountSummaryReportBO getCreateContent(WritableSheet sheet,String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		Statement oStmt1 = null;
		ResultSet oRs = null;
		ResultSet oRs1 = null;
		ArrayList aTmpList = new ArrayList();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		oStmt1 = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		int i = 4;
		int j = 14;
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		try {
			sql.append("SELECT NVL(ACTYPE,' ')ACTYPE, NVL(TRNCOD,' ')TRNCOD ");
			sql.append("FROM IBNK.dt_CUSINFO ");	
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM "); 
		
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				i =i+1;
				addLabel(sheet, 0, i, oRs.getString(1));
				addLabel(sheet, 1, i, oRs.getString(2));
				

			}
			
			sql2.append("SELECT  NVL(SERNUM,''),NVL(VALDAT,''), NVL(CHQNUM,'') TransCode,NVL(ACTDES,'') CHQNO,NVL(REMARK,''),");
			sql2.append("NVL(DBAMFC,''),NVL(CRAMFC,''),NVL(CURBAL,'')   ");		 
			sql2.append("FROM IBNK.dt_WEBINFO ");	
			sql2.append("WHERE MAILID = '");
			sql2.append(sUserID);
			sql2.append("' AND SESSIONID = '");
			sql2.append(sSessionID);
			sql2.append("' ORDER BY SERNUM "); 

		
			oRs1 = oStmt1.executeQuery(sql2.toString());

			
			while (oRs1.next()) {
		
				j =j+1;
				addNumber(sheet,0, j, oRs1.getInt(1));
				addLabel(sheet, 1, j, oRs1.getString(2));
				addLabel(sheet, 2, j, oRs1.getString(3));
				addLabel(sheet, 3, j, oRs1.getString(4));
				addLabel(sheet, 4, j, oRs1.getString(5));
				addLabel(sheet, 5, j, oRs1.getString(6));
				addLabel(sheet, 6, j, oRs1.getString(7));
				addLabel(sheet, 7, j, oRs1.getString(8));	 
							 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oRs != null) {
				oRs.close();
				
			}
			if (oStmt != null) {
				oStmt.close();
			}
			if (oRs1 != null) {
				oRs1.close();
	
			}
			if (oStmt1 != null) {
				oStmt1.close();
			}
		DBCPNewConnection.releaseConnection(oConn);
		}
		return oAccountSummaryReportBO;
	}
	public AccountSummaryReportBO getXLSFile(HttpSession session,
									String sUserID, 
									String sSessionID,		 
									String sFileID) throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		try {
			String sCreateDirectory = "webapps/IBNK/AccountStatement/BalanceDetailReport/" + sSessionID;
			
			File fDirectoryName = new File(sCreateDirectory);
			boolean isDirectoryName = fDirectoryName.exists();
			if (isDirectoryName == false){
				 isDirectoryName = (new File(sCreateDirectory)).mkdirs();
			}			
			String sFileName = sFileID+".xls" ;
			String sFileLocation = sCreateDirectory + "/"+ sFileName;
			 
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));
			WritableWorkbook workbook = Workbook.createWorkbook(new File(sFileLocation), wbSettings);
			workbook.createSheet("AccountStatement_Report", 0);
			WritableSheet excelSheet = workbook.getSheet(0);
			WritableImage image = new WritableImage(0, 0,   //column, row
													4, 4,   //width, height in terms of number of cells
													new File("C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/IBNK/pages/images/reportlogo.png")); //Supports only 'png' images
		    excelSheet.addImage(image);//requires the library jxl-2.6.12.jar to be in classpath.
			createLabel(excelSheet);
			getCreateContent(excelSheet,sUserID,sSessionID);
			
			
			
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oAccountSummaryReportBO;
	}

	private void addCaption(WritableSheet sheet, int column, int row, String sValue) throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, sValue, sTextArialBold);
		sheet.addCell(label);
	}
	private void addFloat(WritableSheet sheet, int column, int row, double dValue) throws WriteException, RowsExceededException {
		Number number;		
		number = new Number(column,row,dValue,sNumberFloat); 
		sheet.addCell(number);
	}
	private void addNumber(WritableSheet sheet, int column, int row, int iValue) throws WriteException, RowsExceededException {
		Number number;		
		number = new Number(column,row,iValue); 
		sheet.addCell(number);
	}
	private void addLabel(WritableSheet sheet, int column, int row, String sValue) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, sValue);
		sheet.addCell(label);

	}

	

	private void createLabel(WritableSheet sheet)throws WriteException {
		// Create Times font
		WritableFont wTextTime = new WritableFont(WritableFont.TIMES, 10);
		sTextTime = new WritableCellFormat(wTextTime);
		sTextTime.setWrap(true);
		
		// Create Bold & Times font with unterlines
		WritableFont wTextTimeBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,UnderlineStyle.SINGLE);
		sTextTimeBoldUnderline = new WritableCellFormat(wTextTimeBoldUnderline);
		sTextTimeBoldUnderline.setWrap(true);
			
		// Create Bold & Arial font
		WritableFont wTextArialBold = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
		sTextArialBold = new WritableCellFormat(wTextArialBold);
		sTextArialBold.setWrap(true);
			
		// Create Number Float
		WritableCellFormat wNumberFloat = new WritableCellFormat(NumberFormats.FLOAT);
		sNumberFloat  = new WritableCellFormat(wNumberFloat);
		sNumberFloat.setWrap(true);
			
		CellView cCellView = new CellView();
		cCellView.setFormat(sTextTime);
		cCellView.setFormat(sTextArialBold);
		cCellView.setFormat(sNumberFloat);
		cCellView.setFormat(sTextTimeBoldUnderline);
	
		// Write a few headers
	
		addCaption(sheet, 0, 14, "Srl No"); 
		addCaption(sheet, 1, 14, "Date"); 
		addCaption(sheet, 2, 14, "Trans. Code/Ref. No"); 
		addCaption(sheet, 3, 14, "Cheque No"); 
		addCaption(sheet, 4, 14, "Particulars"); 
		addCaption(sheet, 5, 14, "Debit Amount"); 
		addCaption(sheet, 6, 14, "Credit Amount"); 
		addCaption(sheet, 7, 14, "Balanced Amount"); 
		
		
		//test
		//sheet.setColumnView(1, 2000);
	}
	
	// NEW ADDED By Sania
	
	public ArrayList getIBNKMenu(String sUserID, String sSessionID){
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTmpList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		try {
			oConn = DBCPNewConnection.getConnection();
			oStmt = oConn.createStatement();
			sql = new StringBuffer();
			sql.append("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL, TRIM(SUBSTR(NODENAME,1,9))  " );
			sql.append("FROM IBNK.SY_MAINMENU " );
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
				oAccountSummaryReportBO.setNodeId(oRs.getString(1));
				oAccountSummaryReportBO.setParentId(oRs.getString(2));
				if (oRs.getString(5).equals("Enquiries")){
					oAccountSummaryReportBO.setNodeName("Enquiries");
				}else{
					oAccountSummaryReportBO.setNodeName(oRs.getString(3));
				}
				oAccountSummaryReportBO.setUrl(oRs.getString(4));
				aTmpList.add(oAccountSummaryReportBO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
				}
			catch (Exception E) {}
		} finally {
			DBCPNewConnection.releaseConnection(oConn);
		}
		return aTmpList;
	}
	
	public AccountSummaryReportBO getApiDetails(String sTerminalID, String sIpimeNo, String sMailID,String sSessionid,String sCompId,String sOprMode,String sReportID )throws Exception {
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.dpr_cus_account_rep_api_param(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sTerminalID);
		oStmt.setString(2, sIpimeNo);
		oStmt.setString(3, sMailID);
		oStmt.setString(4, sSessionid);
		oStmt.setString(5, sCompId);
		oStmt.setString(6, sOprMode);
		oStmt.setString(7, sReportID);
		oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(10, java.sql.Types.VARCHAR);	
		oStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(12, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(13, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(14, java.sql.Types.VARCHAR);
		
		try {
			oStmt.execute();
			oAccountSummaryReportBO.setTargetUrlToken(oStmt.getString(8));
			oAccountSummaryReportBO.setTargetUrlApi(oStmt.getString(9));
			oAccountSummaryReportBO.setUserID(oStmt.getString(10));
			oAccountSummaryReportBO.setUserName(oStmt.getString(11));
			oAccountSummaryReportBO.setApiPassword(oStmt.getString(12));
			oAccountSummaryReportBO.setErrorCode(""+oStmt.getInt(13));
			oAccountSummaryReportBO.setErrorMessage(oStmt.getString(14));
		
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
		return oAccountSummaryReportBO;
	}
	 public AccountSummaryReportBO getExecuteStatementProAPI(String sUrl,String sUserID,String sSessionID,String sAccountNumber, 
			   String sFromDate, 
			   String sToDate) throws JSONException, ClientProtocolException, IOException, SQLException {
		 Connection oConn = null;
			Statement oStmt = null;

				String url;
				String url2;
				String str ="";
				String query1="";
				int SL_NO=1;
				String str1 ="";
				String errorCode = "";
				String errorMsg = "";
				String pErrorFlag = "";
				String pErrorMessage = "";
				String TYPE_OF_PRODUCT="";
				String AC_NO="";
                String Principal_Amount="";
                String Term="";
                String Interest_Rate="";
				String INSTALLMENT_AMOUNT="";
				String TOTAL_INSTALLMENT_DUE="";
				String DUE_NO="";
				String NO_OF_INSTALLMENT_PAID="";
				String PAID_NO="";
				String OD_AMOUNT="";
				String accountName="";
				String OD_NO="";
				String AC_OS="";
				String CASH_SECURITY_AMOUNT="";
				String DPS_BALANCE="";
				String OPEN_DATE="";
				String AC_TITLE="";
				String MATURITY_DATE="";
				String INSTALLMENT="";
				String DUE_INSTALLMENT_AMOUNT="";
				String PAID_INSTALLMENT_AMOUNT="";
				String ACCOUNT_STATUS="";
				String responseString ="";
				oConn = DBCPNewConnection.getConnection();
				oStmt = oConn.createStatement();
				
				int StatusCode = 0;
			AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		try {     
		/*
			 String responsestring="{\r\n" + 
			 		"\"pOutJsnAcSumLn\": \"{\\n\\\"ac_summery_Loan\\\":[\\n{\\n\\\"Type of Product\\\":\\\"Term Deposit Scheme\\\"\\n,\\\"A\\/C Number\\\":\\\"0012001190001386\\\"\\n,\\\"Principal Amount\\\":400000\\n,\\\"Term\\\":3\\n,\\\"Int. Rate\\\":11.5\\n,\\\"Installment\\\":0\\n,\\\"Due Install Amt\\\":0\\n,\\\"Due No\\\":0\\n,\\\"Paid Install Amt\\\":0\\n,\\\"Paid No\\\":0\\n,\\\"OD Amt\\\":0\\n,\\\"OD No\\\":0\\n,\\\"A\\/C OS\\\":0\\n,\\\"Cash Security Amount\\\":0\\n,\\\"AC_STATUS\\\":\\\"CLS\\\"\\n}\\n,{\\n\\\"Type of Product\\\":\\\"Income Scheme\\\"\\n,\\\"A\\/C Number\\\":\\\"0012004190001395\\\"\\n,\\\"Principal Amount\\\":400000\\n,\\\"Term\\\":12\\n,\\\"Int. Rate\\\":12.5\\n,\\\"Installment\\\":0\\n,\\\"Due Install Amt\\\":0\\n,\\\"Due No\\\":0\\n,\\\"Paid Install Amt\\\":0\\n,\\\"Paid No\\\":0\\n,\\\"OD Amt\\\":0\\n,\\\"OD No\\\":0\\n,\\\"A\\/C OS\\\":400000\\n,\\\"Cash Security Amount\\\":0\\n,\\\"AC_STATUS\\\":\\\"ACT\\\"\\n}\\n]\\n}\\n\"\r\n" + 
			 		"}";
			 		*/
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			System.out.println("parameter "+sAccountNumber);
	Request request = new Request.Builder().url(sUrl).method("POST", body)
			.addHeader("pAcNo", sAccountNumber)
			.addHeader("pToDate", sToDate)
		
			//.addHeader("Cookie", "ORA_WWV_USER_218214708090307=ORA_WWV-fHzrA9UlfL8RVErC21LfNf3g")
			.build();
	
		Response response = client.newCall(request).execute(); 
	
			 responseString = response.body().string();
					             	JSONObject responseJson=new JSONObject(responseString);
					                 str = (String) responseJson.getString("pOutJsnAcSumLn");
					                 System.out.println("STR "+str);
					                
					                 
					                 
					               
					                 JSONObject responseJson1=new JSONObject(str);
					                  str1 = (String) responseJson1.getString("ac_summery_Loan");
					                  accountName = (String) responseJson1.getString("ac_summery_Title");
					                  oAccountSummaryReportBO.setAccountName(accountName);
					                  System.out.println("STR1 "+str1);
					                  JSONArray arry = new JSONArray(str1);
					                  oAccountSummaryReportBO.setErrorCode("1");
					              	oAccountSummaryReportBO.setErrorMessage("NO DATA FOUND..");
					              	String deleteQuery="DELETE FROM ibnk.DT_ACT_INFO WHERE MAILID = '" + sUserID+ "'and SESSIONID ='"+sSessionID+"' ";
					        	 	oStmt.executeUpdate(deleteQuery);
					              	for (int i = 0; i < arry.length(); i++) {
			                            JSONObject object = arry.getJSONObject(i);
			                            TYPE_OF_PRODUCT= object.getString("Type of Product");
			                            AC_NO = object.getString("A/C Number");
			                            Principal_Amount=object.getString("Principal Amount");
			                            Term=object.getString("Term");
			                            Interest_Rate=object.getString("Int. Rate");
			                            INSTALLMENT = object.getString("Installment");
			                            DUE_INSTALLMENT_AMOUNT = object.getString("Due Install Amt");
			                            DUE_NO =object.getString("Due No");
			                            PAID_INSTALLMENT_AMOUNT =object.getString("Paid Install Amt");
			                            PAID_NO =object.getString("Paid No");
			                            OD_AMOUNT = object.getString("OD Amt");
			                            OD_NO = object.getString("OD No");
			                            AC_OS=object.getString("AC_OS");
			                            CASH_SECURITY_AMOUNT=object.getString("Cash Security Amount");
			                            ACCOUNT_STATUS =object.getString("Cash Security Amount");
			                            
			                             query1 = "INSERT INTO IBNK.DT_ACT_INFO(MAILID,SESSIONID,SERNUM,COMCOD,BRANCD,BRANAM,ACTNUM,ACDESC,CRDNUM,ACTTIT,ACTYPE,ACTDES,CUSCOD,OPNDAT,CURCDE,CURDES,ACSTAT,CHKBOK)"
			           						 + "VALUES ('"+sUserID+"','"+sSessionID+"','"+SL_NO+"','"+TYPE_OF_PRODUCT+
			           						 "','"+AC_NO+"','"+Principal_Amount+"','"
			           						 +Term+"','"+Interest_Rate+"','"+INSTALLMENT+"','"
			           						 +DUE_INSTALLMENT_AMOUNT+"','"
					           						 +DUE_NO+"','"+PAID_INSTALLMENT_AMOUNT+"','"+PAID_NO+"','"
			           						+OD_AMOUNT+"','"
					           				+OD_NO+"','"+AC_OS+"','"+CASH_SECURITY_AMOUNT+"','"
			           						 +
			           						ACCOUNT_STATUS+"')";
			                          	oStmt.executeUpdate(query1);
			                          	oAccountSummaryReportBO.setErrorCode("0");
			                            }
					          
				
				
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception "+ex);
			oAccountSummaryReportBO.setErrorCode("1");
			oAccountSummaryReportBO.setErrorMessage("NO DATA FOUND..");
			}
				
				
	return oAccountSummaryReportBO;
				
	}
				
		
	



}