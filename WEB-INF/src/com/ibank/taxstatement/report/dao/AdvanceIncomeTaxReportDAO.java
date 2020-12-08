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

public class AdvanceIncomeTaxReportDAO {
	private WritableCellFormat sTextArialBold;
	private WritableCellFormat sTextTime;
	private WritableCellFormat sNumberFloat;
	private WritableCellFormat sTextTimeBoldUnderline;

	
	//DBCP oPool = DBCP.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public AdvanceIncomeTaxReportBO getPermissionCheckPro(String sUserID,			String sSessionID,
											   String sRemoteIPAddress,	String sClientActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_MYBANK_CLIENT_USER_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oAdvanceIncomeTaxReportBO.setErrorCode(""+oStmt.getInt(5));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getMenuCheckPro(String sUserID, 			String sSessionID, 
											   String sRemoteIPAddress,	String sClientActionPath) throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_CLIENT_MAIN_MENU_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oAdvanceIncomeTaxReportBO.setErrorCode("" + oStmt.getInt(5));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getSessionCheckPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_CLIENT_SESSION_VERIFY(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oAdvanceIncomeTaxReportBO.setErrorCode(""+oStmt.getInt(3));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getMessageInformation(String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList tmpList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
				AdvanceIncomeTaxReportBO oTransactionListBO = new AdvanceIncomeTaxReportBO();
				oTransactionListBO.setErrorMessage(oRs.getString(1));
				tmpList.add(oTransactionListBO);
			}
			oAdvanceIncomeTaxReportBO.setList(tmpList);
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getMenuList(String sUserID,String sSessionID) throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
			oAdvanceIncomeTaxReportBO.setMenuList(aMenuList);
			oAdvanceIncomeTaxReportBO.setMenuNameList(aMenuNameList);
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getClearPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_ACCOUNT_PROFILE_CLEAR(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oAdvanceIncomeTaxReportBO.setErrorCode(""+oStmt.getInt(3));
			oAdvanceIncomeTaxReportBO.setErrorMessage(oStmt.getString(4));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getTransactionPro(String sUserID,String sSessionID)throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
			oAdvanceIncomeTaxReportBO.setErrorCode(sErrorCode);
			oAdvanceIncomeTaxReportBO.setErrorMessage(oStmt.getString(4));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getTransactionBalPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo)throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
			oAdvanceIncomeTaxReportBO.setErrorCode(sErrorCode);
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getAccountData(String sUserID,String sTagetUrl) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
			.addHeader("pAcType", "L")
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
			
			oAdvanceIncomeTaxReportBO.setAccountInfoList(aAccountInfoList);
			oAdvanceIncomeTaxReportBO.setAccountInfoNameList(aAccountInfoNameList);
			oAdvanceIncomeTaxReportBO.setErrorCode("0");
			
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(ADD_MONTHS(SYSDATE,-1),'dd/mm/rrrr') FIRSTDATE, ");
			sql.append("TO_CHAR(SYSDATE,'dd/mm/rrrr') LASTDATE  ");
			sql.append("FROM DUAL");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAdvanceIncomeTaxReportBO.setDateFrom(oRs.getString(1));
				oAdvanceIncomeTaxReportBO.setDateTo(oRs.getString(2));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getCompanyCode(String sAccountInfo) throws Exception {
	
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT SUBSTR ('"+sAccountInfo+"', 1, INSTR ('"+sAccountInfo+"', '-') - 1) comcod, SUBSTR ('"+sAccountInfo+"', INSTR ('"+sAccountInfo+"', '-') + 1) actnum ");
			sql.append("FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAdvanceIncomeTaxReportBO.setCompanyCode(oRs.getString(1));
				oAdvanceIncomeTaxReportBO.setAccountNo(oRs.getString(2));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getTransactionSearchPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNumber,String sDateFrom,String sDateTo)throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
			oAdvanceIncomeTaxReportBO.setErrorCode(sErrorCode);
			oAdvanceIncomeTaxReportBO.setErrorMessage(oStmt.getString(8));
		
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getTransactionPreviewPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
			oAdvanceIncomeTaxReportBO.setErrorCode(""+oStmt.getInt(7));
			oAdvanceIncomeTaxReportBO.setErrorMessage(oStmt.getString(8));
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
		return oAdvanceIncomeTaxReportBO;
	}
	
	public AdvanceIncomeTaxReportBO getTransactionPreviewPro1(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
			oAdvanceIncomeTaxReportBO.setErrorCode(""+oStmt.getInt(7));
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
		return oAdvanceIncomeTaxReportBO;
	}
	
	public AdvanceIncomeTaxReportBO getTransactionInformation(String sUserID,String sSessionID) throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
				AdvanceIncomeTaxReportBO oTransactionCustomerInfoListBO = new AdvanceIncomeTaxReportBO();
				oTransactionCustomerInfoListBO.setTitle(oRs.getString(1));
				oTransactionCustomerInfoListBO.setCustomerInfo(oRs.getString(2));
				aTempCustomerList.add(oTransactionCustomerInfoListBO);
			}
			oAdvanceIncomeTaxReportBO.setCustomerInfoList(aTempCustomerList);

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
				AdvanceIncomeTaxReportBO oTransactionStatementListBO = new AdvanceIncomeTaxReportBO();
				oTransactionStatementListBO.setDateInfo(oRs.getString(1));
				oTransactionStatementListBO.setChqNumber(oRs.getString(2));
				oTransactionStatementListBO.setParticulars(oRs.getString(3));
				oTransactionStatementListBO.setDebitAmount(oRs.getString(4));
				oTransactionStatementListBO.setCreditAmount(oRs.getString(5));
				oTransactionStatementListBO.setBalanceAmount(oRs.getString(6));
				oTransactionStatementListBO.setSerialNo(oRs.getString(7));
				aTempStatementList.add(oTransactionStatementListBO);
			}
			oAdvanceIncomeTaxReportBO.setStatementList(aTempStatementList);

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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getMaxRecord(String sUserID,String sSessionID) throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
				oAdvanceIncomeTaxReportBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getCountRecord(String sUserID,String sSessionID) throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
				oAdvanceIncomeTaxReportBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getMaxRecordRequest(String sUserID,String sSessionID) throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
				oAdvanceIncomeTaxReportBO.setDisplayRequestTotalRecord(oRs.getString(1));
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
		return oAdvanceIncomeTaxReportBO;
	}
	public AdvanceIncomeTaxReportBO getDisplayRecordPro(String sDisplayAction, String sDisplayInLastRecord, String sDisplayInTotalRecord, String sDisplayFrequency)throws Exception {
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
			oAdvanceIncomeTaxReportBO.setDisplayOutFirstRecord(""+oStmt.getInt(5));
			oAdvanceIncomeTaxReportBO.setDisplayOutLastRecord(""+oStmt.getInt(6));
			oAdvanceIncomeTaxReportBO.setDisplayOutMessage(oStmt.getString(7));
			oAdvanceIncomeTaxReportBO.setErrorCode(""+oStmt.getInt(8));
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
		return oAdvanceIncomeTaxReportBO;
	}
	
	
	public AdvanceIncomeTaxReportBO getFileName() throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTmpList = new ArrayList();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(SYSDATE,'DDMMRRRRHH24MMSS') FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oAdvanceIncomeTaxReportBO.setFile(oRs.getString(1));
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
		return oAdvanceIncomeTaxReportBO;
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
				AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
				oAdvanceIncomeTaxReportBO.setNodeId(oRs.getString(1));
				oAdvanceIncomeTaxReportBO.setParentId(oRs.getString(2));
				if (oRs.getString(5).equals("Enquiries")){
					oAdvanceIncomeTaxReportBO.setNodeName("Enquiries");
				}else{
					oAdvanceIncomeTaxReportBO.setNodeName(oRs.getString(3));
				}
				oAdvanceIncomeTaxReportBO.setUrl(oRs.getString(4));
				aTmpList.add(oAdvanceIncomeTaxReportBO);
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
	
	 public AdvanceIncomeTaxReportBO ReportTransactionStatement(String sUserID,String sSessionID,String sAccountNumber, 
			   String sFromDate, 
			   String sToDate,
			   String sTagetUrl) throws JSONException, ClientProtocolException, IOException {

				String url;
				String url2;
				String str ="";
				String str1 ="";
				String errorCode = "";
				String errorMsg = "";
				String pErrorFlag = "";
				String pErrorMessage = "";
				int StatusCode = 0;
				String responseString = "";
				String  sReportHeader="";
				String sReportHeaderLine = "";
				Connection oConn = null;
				Statement oStmt = null;
			AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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

				System.out.println("response ===>>> "+responseString);
				
				oConn = DBCPNewConnection.getConnection();
				oStmt = oConn.createStatement();
				String delt = "DELETE FROM ibnk.DT_ACT_STATEMENT WHERE MAILID = '" + sUserID + "' and SESSIONID = '"+sSessionID+"' ";
		        oStmt.executeUpdate(delt);
		
					System.out.println("response ===>>> "+responseString);
					
					JSONObject responseJson = new JSONObject(responseString);
					str = responseJson.getString("pOutJsnAIT");
					
					JSONObject secondReasponse = new JSONObject(str);
					sReportHeader = secondReasponse.getString("ait_statement_mst");
				/*	JSONArray reportHeader = new JSONArray(sReportHeader);

				    JSONObject reportHeaderL = reportHeader.getJSONObject(0);
				    sReportHeaderLine = reportHeaderL.getString("DEPOSIT_NO");*/
					oAdvanceIncomeTaxReportBO.setCustomerInfo(sReportHeader.trim());
					
					
					str1 = secondReasponse.getString("ait_statement");
					// Set JSON Array
					JSONArray arry = new JSONArray(str1);	        
					ArrayList data = new ArrayList();
					
					final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";
					final String NEW_FORMAT = "dd-MM-yyyy";
		
					String newDateString="";
					
	if(arry.length() > 0){
						
			for (int i = 0; i < arry.length(); i++) {
					
				  PDChequeStatusReportBO oPDChequeStatusReportBOList = new PDChequeStatusReportBO();
					JSONObject object = arry.getJSONObject(i);
					String SL_NO ="";
					String DEPOSIT_NO ="";
					String OPEN_DATE ="";
					String INT_AMT1 ="";
					String INT_AMT ="";
					String TAX_RATE ="";
					String MATURITY_DATE ="";
					String DEPOSIT_AMT ="";
					String INITIAL_AMT ="";
					String TAX_AMT ="";
					
					
					 SL_NO = object.getString("SLNO");
					 DEPOSIT_NO = object.getString("DEPOSIT_NO");
					 OPEN_DATE =object.getString("OPEN_DATE");
					 INT_AMT1 = object.getString("INT_AMT1");
					 INT_AMT = object.getString("INT_AMT");
					 TAX_RATE = object.getString("TAX_RATE");
					 MATURITY_DATE = object.getString("MATURITY_DATE");
					 DEPOSIT_AMT = object.getString("DEPOSIT_AMT");
					 INITIAL_AMT = object.getString("INITIAL_AMT");
					 TAX_AMT = object.getString("TAX_AMT");
				
					String query1 =  "INSERT INTO ibnk.DT_ACT_STATEMENT(SERNUM,MAILID, SESSIONID,VALDAT,CHQNUM,DOCDES,REMARK,ACTNUM,CURBAL,OPRDES,DBAMFC,CRAMFC)"
							 + "VALUES ('"+SL_NO+"','"+sUserID+"','"+sSessionID+"','"+OPEN_DATE+"','"+DEPOSIT_NO+"','"+INT_AMT1+"','"+INT_AMT+"','"+TAX_RATE+"','"+MATURITY_DATE+"','"+DEPOSIT_AMT+"','"+INITIAL_AMT+"','"+TAX_AMT+"')";
					
					oStmt.executeUpdate(query1);
				
					oAdvanceIncomeTaxReportBO.setErrorCode("0");
			}	
	}else {
		
		oAdvanceIncomeTaxReportBO.setErrorCode("1");
		oAdvanceIncomeTaxReportBO.setErrorMessage("No Data Found.." +StatusCode);
		
	}
		}catch(Exception ex) {
				
			oAdvanceIncomeTaxReportBO.setErrorCode("1");
			oAdvanceIncomeTaxReportBO.setErrorMessage("No Data Found.." +StatusCode);
			}
				
				
	return oAdvanceIncomeTaxReportBO;
				
	}
	 
	 public AdvanceIncomeTaxReportBO getTargetAPIPro(String sTerminalid,String sIpimeno,String sUserID,String sSessionID,String sCompanyID,
			 										 String sOprmod,String sRepid)throws Exception {
		
		 AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
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
					oAdvanceIncomeTaxReportBO.setTagetUrl(oStmt.getString(9));
					sErrorCode= ""+oStmt.getInt(13);
					oAdvanceIncomeTaxReportBO.setErrorCode(sErrorCode);
					oAdvanceIncomeTaxReportBO.setErrorMessage(oStmt.getString(14));
					
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
		return oAdvanceIncomeTaxReportBO;
					}


}
