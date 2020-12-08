package com.ibank.taxstatement.report.dao;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.ibank.loan.report.bo.PDChequeStatusReportBO;
import com.ibank.login.bo.IBankingUserLogInBO;
import com.ibank.taxstatement.report.bo.AdvanceIncomeTaxReportBO;
import com.ibank.taxstatement.report.bo.TaxStateMentReportBO;

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

public class TaxStateMentReportDAO {

	private WritableCellFormat sTextArialBold;
	private WritableCellFormat sTextTime;
	private WritableCellFormat sNumberFloat;
	private WritableCellFormat sTextTimeBoldUnderline;

	
	//DBCP oPool = DBCP.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public TaxStateMentReportBO getPermissionCheckPro(String sUserID,			String sSessionID,
											   String sRemoteIPAddress,	String sClientActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_MYBANK_CLIENT_USER_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oTaxStateMentReportBO.setErrorCode(""+oStmt.getInt(5));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getMenuCheckPro(String sUserID, 			String sSessionID, 
											   String sRemoteIPAddress,	String sClientActionPath) throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_CLIENT_MAIN_MENU_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oTaxStateMentReportBO.setErrorCode("" + oStmt.getInt(5));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getSessionCheckPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_CLIENT_SESSION_VERIFY(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oTaxStateMentReportBO.setErrorCode(""+oStmt.getInt(3));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getMessageInformation(String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList tmpList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(ERRMSG,' ')");
			sql.append("FROM MyBank.SY_MESSAGE ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				TaxStateMentReportBO oTransactionListBO = new TaxStateMentReportBO();
				oTransactionListBO.setErrorMessage(oRs.getString(1));
				tmpList.add(oTransactionListBO);
			}
			oTaxStateMentReportBO.setList(tmpList);
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getMenuList(String sUserID,String sSessionID) throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
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
			sql.append("FROM MyBank.SY_FAVORITES ");
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
			oTaxStateMentReportBO.setMenuList(aMenuList);
			oTaxStateMentReportBO.setMenuNameList(aMenuNameList);
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getClearPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_ACCOUNT_PROFILE_CLEAR(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oTaxStateMentReportBO.setErrorCode(""+oStmt.getInt(3));
			oTaxStateMentReportBO.setErrorMessage(oStmt.getString(4));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getTransactionPro(String sUserID,String sSessionID)throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_RUNNING_ACTNUM(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(3);
			oTaxStateMentReportBO.setErrorCode(sErrorCode);
			oTaxStateMentReportBO.setErrorMessage(oStmt.getString(4));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getTransactionBalPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo)throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_BALANCE_ACTNUM(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(5);
			oTaxStateMentReportBO.setErrorCode(sErrorCode);
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getAccountData(String sUserID,String sTagetUrl) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
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
		Request request = new Request.Builder().url(sTagetUrl).method("POST", body)
			.addHeader("pCustCode", sUserID)
			.addHeader("pAcType", "A")
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
			aAccountInfoList.add(object.getString("AC_NO"));
			aAccountInfoNameList.add(object.getString("AC_TITLE")+"-"+object.getString("AC_NO")+"-"+"("+object.getString("AC_STATUS")+")");					
			//data.add(oIBankingUserLogInBOList);
		}
			
			oTaxStateMentReportBO.setAccountInfoList(aAccountInfoList);
			oTaxStateMentReportBO.setAccountInfoNameList(aAccountInfoNameList);
			oTaxStateMentReportBO.setErrorCode("0");
			
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(ADD_MONTHS(SYSDATE,-1),'dd/mm/rrrr') FIRSTDATE, ");
			sql.append("TO_CHAR(SYSDATE,'dd/mm/rrrr') LASTDATE  ");
			sql.append("FROM DUAL");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oTaxStateMentReportBO.setDateFrom(oRs.getString(1));
				oTaxStateMentReportBO.setDateTo(oRs.getString(2));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getCompanyCode(String sAccountInfo) throws Exception {
	
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT SUBSTR ('"+sAccountInfo+"', 1, INSTR ('"+sAccountInfo+"', '-') - 1) comcod, SUBSTR ('"+sAccountInfo+"', INSTR ('"+sAccountInfo+"', '-') + 1) actnum ");
			sql.append("FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oTaxStateMentReportBO.setCompanyCode(oRs.getString(1));
				oTaxStateMentReportBO.setAccountNo(oRs.getString(2));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getTransactionSearchPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNumber,String sDateFrom,String sDateTo)throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_RUNNING_BALANCE_INFO(?,?,?,?,?,?,?,?)}");
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
			oTaxStateMentReportBO.setErrorCode(sErrorCode);
			oTaxStateMentReportBO.setErrorMessage(oStmt.getString(8));
		
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getTransactionPreviewPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_TRANSACTION_PREVIEW(?,?,?,?,?,?,?,?)}");
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
			oTaxStateMentReportBO.setErrorCode(""+oStmt.getInt(7));
			oTaxStateMentReportBO.setErrorMessage(oStmt.getString(8));
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
		return oTaxStateMentReportBO;
	}
	
	public TaxStateMentReportBO getTransactionPreviewPro1(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_TRANSACTION_PREVIEWXL(?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.setString(5, sDateFrom);
		oStmt.setString(6, sDateTo);
		oStmt.registerOutParameter(7, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oTaxStateMentReportBO.setErrorCode(""+oStmt.getInt(7));
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
		return oTaxStateMentReportBO;
	}
	
	public TaxStateMentReportBO getTransactionInformation(String sUserID,String sSessionID) throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
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
			sql.append("FROM MyBank.DT_ACT_CUSTOMER_INFO ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				TaxStateMentReportBO oTransactionCustomerInfoListBO = new TaxStateMentReportBO();
				oTransactionCustomerInfoListBO.setTitle(oRs.getString(1));
				oTransactionCustomerInfoListBO.setCustomerInfo(oRs.getString(2));
				aTempCustomerList.add(oTransactionCustomerInfoListBO);
			}
			oTaxStateMentReportBO.setCustomerInfoList(aTempCustomerList);

			sql = new StringBuffer();
			sql.append("SELECT  NVL(VALDAT,' '), NVL(CHQNUM,' '), NVL(REMARK,' '), ");
			sql.append("NVL(DBAMFC,' '), NVL(CRAMFC,' '), NVL(CURBAL,' '), NVL(SERNUM,0) ");
			sql.append("FROM MyBank.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				TaxStateMentReportBO oTransactionStatementListBO = new TaxStateMentReportBO();
				oTransactionStatementListBO.setDateInfo(oRs.getString(1));
				oTransactionStatementListBO.setChqNumber(oRs.getString(2));
				oTransactionStatementListBO.setParticulars(oRs.getString(3));
				oTransactionStatementListBO.setDebitAmount(oRs.getString(4));
				oTransactionStatementListBO.setCreditAmount(oRs.getString(5));
				oTransactionStatementListBO.setBalanceAmount(oRs.getString(6));
				oTransactionStatementListBO.setSerialNo(oRs.getString(7));
				aTempStatementList.add(oTransactionStatementListBO);
			}
			oTaxStateMentReportBO.setStatementList(aTempStatementList);

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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getMaxRecord(String sUserID,String sSessionID) throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM MyBank.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oTaxStateMentReportBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getCountRecord(String sUserID,String sSessionID) throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM MyBank.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oTaxStateMentReportBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getMaxRecordRequest(String sUserID,String sSessionID) throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM MyBank.DT_ACT_REQUEST ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oTaxStateMentReportBO.setDisplayRequestTotalRecord(oRs.getString(1));
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
		return oTaxStateMentReportBO;
	}
	public TaxStateMentReportBO getDisplayRecordPro(String sDisplayAction, String sDisplayInLastRecord, String sDisplayInTotalRecord, String sDisplayFrequency)throws Exception {
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_DISPLAY_RECORD(?,?,?,?,?,?,?,?)}");
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
			oTaxStateMentReportBO.setDisplayOutFirstRecord(""+oStmt.getInt(5));
			oTaxStateMentReportBO.setDisplayOutLastRecord(""+oStmt.getInt(6));
			oTaxStateMentReportBO.setDisplayOutMessage(oStmt.getString(7));
			oTaxStateMentReportBO.setErrorCode(""+oStmt.getInt(8));
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
		return oTaxStateMentReportBO;
	}
	
	
	public TaxStateMentReportBO getFileName() throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTmpList = new ArrayList();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(SYSDATE,'DDMMRRRRHH24MMSS') FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oTaxStateMentReportBO.setFile(oRs.getString(1));
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
		return oTaxStateMentReportBO;
	}	

	
	// NEW ADDED By Sania
	
	public ArrayList getMyBankMenu(String sUserID, String sSessionID){
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
			sql.append("FROM MYBANK.SY_MAINMENU " );
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
				oTaxStateMentReportBO.setNodeId(oRs.getString(1));
				oTaxStateMentReportBO.setParentId(oRs.getString(2));
				if (oRs.getString(5).equals("Enquiries")){
					oTaxStateMentReportBO.setNodeName("Enquiries");
				}else{
					oTaxStateMentReportBO.setNodeName(oRs.getString(3));
				}
				oTaxStateMentReportBO.setUrl(oRs.getString(4));
				aTmpList.add(oTaxStateMentReportBO);
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
	
	 public TaxStateMentReportBO ReportTransactionStatement(String sUserID,String sSessionID,String sAccountNumber, 
			   String sFromDate, 
			   String sToDate,
			   String sTagetUrl) throws JSONException, ClientProtocolException, IOException {
		 System.out.println("sTagetUrl ===>>> "+sTagetUrl+"-"+sFromDate+"--"+sToDate);
				String url;
				String url2;
				String str ="";
				String str1 ="";
				String errorCode = "";
				String errorMsg = "";
				String pErrorFlag = "";
				String pErrorMessage = "";
				String  sReportHeader="";
				int StatusCode = 0;
				String responseString = "";
				String sReportHeaderLine = "";
				Connection oConn = null;
				Statement oStmt = null;
				TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		try {     
				OkHttpClient client = new OkHttpClient().newBuilder().build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(sTagetUrl).method("POST", body)
				.addHeader("pAcNo", sAccountNumber)
				.addHeader("pFromDate", sFromDate)
				.addHeader("pToDate", sToDate)
				//.addHeader("Cookie", "ORA_WWV_USER_218214708090307=ORA_WWV-fHzrA9UlfL8RVErC21LfNf3g")
				.build();
		
			Response response = client.newCall(request).execute(); 
			responseString = response.body().string();

			/* responseString = "{\"pOutJsnTAX\": {\r\n" + 
			 		"		\"tax_statement\": [{\r\n" + 
			 		"					\"SL_NO\": \"01\",\r\n" + 
			 		"					\"PAID_INT\": \"1,254,494.47\",\r\n" +
			 		"					\"PAID_PRINCIPLE\": \"2,081,075.53\",\r\n" + 
			 		"					\"TOTAL_PAID\": \"3,335,570.00\" }]}}";*/
	
				
			/*	oConn = DBCPNewConnection.getConnection();
				oStmt = oConn.createStatement();
				String delt = "DELETE FROM ibnk.DT_ACT_STATEMENT WHERE MAILID = '" + sUserID + "' and SESSIONID = '"+sSessionID+"' ";
		        oStmt.executeUpdate(delt);*/
		
				//	System.out.println("response ===>>> "+responseString);
					
					JSONObject responseJson = new JSONObject(responseString);
					str = responseJson.getString("pOutJsnTAX");
					
					JSONObject secondReasponse = new JSONObject(str);
				    sReportHeader = secondReasponse.getString("tax_statement");	
				    System.out.println("sReportHeader ===>>> "+sReportHeader);
					oTaxStateMentReportBO.setCustomerInfo(sReportHeader.trim());
					oTaxStateMentReportBO.setErrorCode("0");
					//str1 = secondReasponse.getString("tax_statement");
					// System.out.println("str1 ===>>> "+str1);
					// Set JSON Array
				//	JSONArray arry = new JSONArray(str1);	        
				//	ArrayList data = new ArrayList();
					
				/*	final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";
					final String NEW_FORMAT = "dd-MM-yyyy";
		
					String newDateString="";*/
				
					/*	if(arry.length() > 0){				
		
				for (int i = 0; i < arry.length(); i++) {
					
				  PDChequeStatusReportBO oPDChequeStatusReportBOList = new PDChequeStatusReportBO();
					JSONObject object = arry.getJSONObject(i);
					String SL_NO ="";
					String PAID_INT ="";
					String PAID_PRINCIPLE ="";
					String TOTAL_PAID ="";			
					
					 SL_NO = object.getString("SL_NO");
					 PAID_INT = object.getString("PAID_INT");
					 PAID_PRINCIPLE =object.getString("PAID_PRINCIPLE");
					 TOTAL_PAID = object.getString("TOTAL_PAID");
							
					String query1 =  "INSERT INTO ibnk.DT_ACT_STATEMENT(SERNUM,MAILID, SESSIONID,DBAMFC,CRAMFC,CURBAL)"
							 + "VALUES ('"+SL_NO+"','"+sUserID+"','"+sSessionID+"','"+PAID_INT+"','"+PAID_PRINCIPLE+"','"+TOTAL_PAID+"')";
					System.out.println("query1 ===>>> "+query1);
					oStmt.executeUpdate(query1);
				
					oTaxStateMentReportBO.setErrorCode("0");
			}	
			
	}else {
		
		oTaxStateMentReportBO.setErrorCode("1");
		oTaxStateMentReportBO.setErrorMessage("No Data Found.." +StatusCode);
	}*/
		}catch(Exception ex) {
				
			oTaxStateMentReportBO.setErrorCode("1");
			oTaxStateMentReportBO.setErrorMessage("No Data Found.." +StatusCode);
			}
				
				
	return oTaxStateMentReportBO;
				
	}

	 
	 public TaxStateMentReportBO getTargetAPIPro(String sTerminalid,String sIpimeno,String sUserID,String sSessionID,String sCompanyID,
			 									 String sOprmod,String sRepid)throws Exception {
		 System.out.println("sRepid ===>>> "+sRepid);
			TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
			Connection oConn = null;
			oConn = DBCPNewConnection.getConnection();
			String sErrorCode = null;
			CallableStatement oStmt = oConn.prepareCall("{call ibnk.dpr_cus_account_rep_api_param(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			oStmt.setString(1, sTerminalid);
			oStmt.setString(2, sIpimeno);
			oStmt.setString(3, sUserID);
			oStmt.setString(4, sSessionID);
			oStmt.setString(5, sCompanyID);
			oStmt.setString(6, sOprmod);
			oStmt.setString(7, sRepid);
			oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
			oStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
			oStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
			oStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
			oStmt.registerOutParameter(12, java.sql.Types.VARCHAR);
			oStmt.registerOutParameter(13, java.sql.Types.INTEGER);
			oStmt.registerOutParameter(14, java.sql.Types.VARCHAR);
			try {
				oStmt.execute();
				
				oTaxStateMentReportBO.setTagetUrl(oStmt.getString(9));
				sErrorCode= ""+oStmt.getInt(13);
				oTaxStateMentReportBO.setErrorCode(sErrorCode);
				oTaxStateMentReportBO.setErrorMessage(oStmt.getString(14));
				 System.out.println("oStmt.getString(8) ===>>> "+oStmt.getString(8));
			
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
			return oTaxStateMentReportBO;
		}
}
