package com.ibank.loan.report.dao;

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
import com.ibank.loan.report.bo.LoanAgainstDepositStatusBO;
import com.ibank.loan.report.bo.PDChequeStatusReportBO;
import com.ibank.login.bo.IBankingUserLogInBO;
import com.ibank.taxstatement.report.bo.AdvanceIncomeTaxReportBO;

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

public class LoanAgainstDepositStatusDAO  {
	private WritableCellFormat sTextArialBold;
	private WritableCellFormat sTextTime;
	private WritableCellFormat sNumberFloat;
	private WritableCellFormat sTextTimeBoldUnderline;

	
	//DBCP oPool = DBCP.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public LoanAgainstDepositStatusBO getPermissionCheckPro(String sUserID,			String sSessionID,
											   String sRemoteIPAddress,	String sClientActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_MYBANK_CLIENT_USER_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oLoanAgainstDepositStatusBO.setErrorCode(""+oStmt.getInt(5));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getMenuCheckPro(String sUserID, 			String sSessionID, 
											   String sRemoteIPAddress,	String sClientActionPath) throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_CLIENT_MAIN_MENU_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oLoanAgainstDepositStatusBO.setErrorCode("" + oStmt.getInt(5));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getSessionCheckPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_CLIENT_SESSION_VERIFY(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oLoanAgainstDepositStatusBO.setErrorCode(""+oStmt.getInt(3));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getMessageInformation(String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList tmpList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
				LoanAgainstDepositStatusBO oTransactionListBO = new LoanAgainstDepositStatusBO();
				oTransactionListBO.setErrorMessage(oRs.getString(1));
				tmpList.add(oTransactionListBO);
			}
			oLoanAgainstDepositStatusBO.setList(tmpList);
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getMenuList(String sUserID,String sSessionID) throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
			oLoanAgainstDepositStatusBO.setMenuList(aMenuList);
			oLoanAgainstDepositStatusBO.setMenuNameList(aMenuNameList);
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getClearPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_ACCOUNT_PROFILE_CLEAR(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oLoanAgainstDepositStatusBO.setErrorCode(""+oStmt.getInt(3));
			oLoanAgainstDepositStatusBO.setErrorMessage(oStmt.getString(4));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getTransactionPro(String sUserID,String sSessionID)throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
			oLoanAgainstDepositStatusBO.setErrorCode(sErrorCode);
			oLoanAgainstDepositStatusBO.setErrorMessage(oStmt.getString(4));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getTransactionBalPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo)throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
			oLoanAgainstDepositStatusBO.setErrorCode(sErrorCode);
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getAccountData(String sUserID,String sTagetUrl) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
			
			oLoanAgainstDepositStatusBO.setAccountInfoList(aAccountInfoList);
			oLoanAgainstDepositStatusBO.setAccountInfoNameList(aAccountInfoNameList);
			oLoanAgainstDepositStatusBO.setErrorCode("0");
			
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(ADD_MONTHS(SYSDATE,-1),'dd/mm/rrrr') FIRSTDATE, ");
			sql.append("TO_CHAR(SYSDATE,'dd/mm/rrrr') LASTDATE  ");
			sql.append("FROM DUAL");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oLoanAgainstDepositStatusBO.setDateFrom(oRs.getString(1));
				oLoanAgainstDepositStatusBO.setDateTo(oRs.getString(2));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getCompanyCode(String sAccountInfo) throws Exception {
	
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT SUBSTR ('"+sAccountInfo+"', 1, INSTR ('"+sAccountInfo+"', '-') - 1) comcod, SUBSTR ('"+sAccountInfo+"', INSTR ('"+sAccountInfo+"', '-') + 1) actnum ");
			sql.append("FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oLoanAgainstDepositStatusBO.setCompanyCode(oRs.getString(1));
				oLoanAgainstDepositStatusBO.setAccountNo(oRs.getString(2));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getTransactionSearchPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNumber,String sDateFrom,String sDateTo)throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
			oLoanAgainstDepositStatusBO.setErrorCode(sErrorCode);
			oLoanAgainstDepositStatusBO.setErrorMessage(oStmt.getString(8));
		
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getTransactionPreviewPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
			oLoanAgainstDepositStatusBO.setErrorCode(""+oStmt.getInt(7));
			oLoanAgainstDepositStatusBO.setErrorMessage(oStmt.getString(8));
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
		return oLoanAgainstDepositStatusBO;
	}
	
	public LoanAgainstDepositStatusBO getTransactionPreviewPro1(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
			oLoanAgainstDepositStatusBO.setErrorCode(""+oStmt.getInt(7));
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
		return oLoanAgainstDepositStatusBO;
	}
	
	public LoanAgainstDepositStatusBO getTransactionInformation(String sUserID,String sSessionID) throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
				LoanAgainstDepositStatusBO oTransactionCustomerInfoListBO = new LoanAgainstDepositStatusBO();
				oTransactionCustomerInfoListBO.setTitle(oRs.getString(1));
				oTransactionCustomerInfoListBO.setCustomerInfo(oRs.getString(2));
				aTempCustomerList.add(oTransactionCustomerInfoListBO);
			}
			oLoanAgainstDepositStatusBO.setCustomerInfoList(aTempCustomerList);

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
				LoanAgainstDepositStatusBO oTransactionStatementListBO = new LoanAgainstDepositStatusBO();
				oTransactionStatementListBO.setDateInfo(oRs.getString(1));
				oTransactionStatementListBO.setChqNumber(oRs.getString(2));
				oTransactionStatementListBO.setParticulars(oRs.getString(3));
				oTransactionStatementListBO.setDebitAmount(oRs.getString(4));
				oTransactionStatementListBO.setCreditAmount(oRs.getString(5));
				oTransactionStatementListBO.setBalanceAmount(oRs.getString(6));
				oTransactionStatementListBO.setSerialNo(oRs.getString(7));
				aTempStatementList.add(oTransactionStatementListBO);
			}
			oLoanAgainstDepositStatusBO.setStatementList(aTempStatementList);

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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getMaxRecord(String sUserID,String sSessionID) throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
				oLoanAgainstDepositStatusBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getCountRecord(String sUserID,String sSessionID) throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
				oLoanAgainstDepositStatusBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getMaxRecordRequest(String sUserID,String sSessionID) throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
				oLoanAgainstDepositStatusBO.setDisplayRequestTotalRecord(oRs.getString(1));
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getDisplayRecordPro(String sDisplayAction, String sDisplayInLastRecord, String sDisplayInTotalRecord, String sDisplayFrequency)throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
			oLoanAgainstDepositStatusBO.setDisplayOutFirstRecord(""+oStmt.getInt(5));
			oLoanAgainstDepositStatusBO.setDisplayOutLastRecord(""+oStmt.getInt(6));
			oLoanAgainstDepositStatusBO.setDisplayOutMessage(oStmt.getString(7));
			oLoanAgainstDepositStatusBO.setErrorCode(""+oStmt.getInt(8));
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
		return oLoanAgainstDepositStatusBO;
	}
	
	
	public LoanAgainstDepositStatusBO getFileName() throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTmpList = new ArrayList();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(SYSDATE,'DDMMRRRRHH24MMSS') FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oLoanAgainstDepositStatusBO.setFile(oRs.getString(1));
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
		return oLoanAgainstDepositStatusBO;
	}	

	public LoanAgainstDepositStatusBO getCreateContent(WritableSheet sheet,String sUserID,String sSessionID) throws Exception {
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
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		try {
			sql.append("SELECT NVL(ACTYPE,' ')ACTYPE, NVL(TRNCOD,' ')TRNCOD ");
			sql.append("FROM MYBANK.dt_CUSINFO ");	
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
			sql2.append("FROM MYBANK.dt_WEBINFO ");	
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
		return oLoanAgainstDepositStatusBO;
	}
	public LoanAgainstDepositStatusBO getXLSFile(HttpSession session,
									String sUserID, 
									String sSessionID,		 
									String sFileID) throws Exception {
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		try {
			String sCreateDirectory = "webapps/mybank/AccountStatement/BalanceDetailReport/" + sSessionID;
			
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
													new File("C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/mybank/pages/images/reportlogo.png")); //Supports only 'png' images
		    excelSheet.addImage(image);//requires the library jxl-2.6.12.jar to be in classpath.
			createLabel(excelSheet);
			getCreateContent(excelSheet,sUserID,sSessionID);
			
			
			
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oLoanAgainstDepositStatusBO;
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
				LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
				oLoanAgainstDepositStatusBO.setNodeId(oRs.getString(1));
				oLoanAgainstDepositStatusBO.setParentId(oRs.getString(2));
				if (oRs.getString(5).equals("Enquiries")){
					oLoanAgainstDepositStatusBO.setNodeName("Enquiries");
				}else{
					oLoanAgainstDepositStatusBO.setNodeName(oRs.getString(3));
				}
				oLoanAgainstDepositStatusBO.setUrl(oRs.getString(4));
				aTmpList.add(oLoanAgainstDepositStatusBO);
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
	
	 public LoanAgainstDepositStatusBO getExecuteStatementProAPI(String sAccountNumber, 
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
			LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
				
				final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";
				final String NEW_FORMAT = "dd-MM-yyyy";
				
				// August 12, 2010
				//    String dateString = "2018-10-31T00:00:00Z";
				String newDateString="";
				
				/* SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
				Date d = sdf.parse(dateString);
				sdf.applyPattern(NEW_FORMAT);
				newDateString = sdf.format(d);*/
				
				
				
		for (int i = 0; i < arry.length(); i++) {
				
			  LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBOList = new LoanAgainstDepositStatusBO();
				JSONObject object = arry.getJSONObject(i);
				oLoanAgainstDepositStatusBOList.setSerialNo(object.getString("SL_NO"));
				oLoanAgainstDepositStatusBOList.setCreditAmount(object.getString("CREDIT"));
				oLoanAgainstDepositStatusBOList.setDebitAmount(object.getString("DEBIT"));
				oLoanAgainstDepositStatusBOList.setChqNumber(object.getString("DOC_NUM"));
				String dateString = object.getString("VALUE_DATE");
				SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
				Date d = sdf.parse(dateString);
				sdf.applyPattern(NEW_FORMAT);
				newDateString = sdf.format(d);
				oLoanAgainstDepositStatusBOList.setDateInfo(newDateString);
				oLoanAgainstDepositStatusBOList.setBalanceAmount(object.getString("BALANCE"));
				oLoanAgainstDepositStatusBOList.setParticulars(object.getString("NARRATION"));
				
				data.add(oLoanAgainstDepositStatusBOList);
		}
				
			oLoanAgainstDepositStatusBO.setStatementList(data);
			oLoanAgainstDepositStatusBO.setErrorCode("0");
				
		}catch(Exception ex) {
				
			oLoanAgainstDepositStatusBO.setErrorCode("1");
			oLoanAgainstDepositStatusBO.setErrorMessage("Server not found.." +StatusCode);
			}
				
				
	return oLoanAgainstDepositStatusBO;
				
	}
	 public LoanAgainstDepositStatusBO getTargetAPIPro(String sTerminalid,String sIpimeno,String sUserID,String sSessionID,String sCompanyID,
									 String sOprmod,String sRepid)throws Exception {
					
		 LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
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
					oLoanAgainstDepositStatusBO.setTagetUrl(oStmt.getString(9));
					sErrorCode= ""+oStmt.getInt(13);
					oLoanAgainstDepositStatusBO.setErrorCode(sErrorCode);
					oLoanAgainstDepositStatusBO.setErrorMessage(oStmt.getString(14));
					
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
		return oLoanAgainstDepositStatusBO;
		}				
	 public LoanAgainstDepositStatusBO ReportTransactionStatement(String sUserID,String sSessionID,String sAccountNumber, 
			   String sAsOnDate,
			   String sTagetUrl) throws JSONException, ClientProtocolException, IOException {

				String url;
				String url2;
				String str ="";
				String str1 ="";
				String errorCode = "";
				String errorMsg = "";
				String pErrorFlag = "";
				String pErrorMessage = "";
				String sLadStatusTitle = "";
				int StatusCode = 0;
				String responseString = "";
				Connection oConn = null;
				Statement oStmt = null;
				LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		try {     
				OkHttpClient client = new OkHttpClient().newBuilder().build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(sTagetUrl).method("POST", body)
				.addHeader("pAcNo", sAccountNumber)		
				.addHeader("pToDate", sAsOnDate)
				//.addHeader("Cookie", "ORA_WWV_USER_218214708090307=ORA_WWV-fHzrA9UlfL8RVErC21LfNf3g")
				.build();
		
			Response response = client.newCall(request).execute(); 
				 responseString = response.body().string();

				System.out.println("response ===>>> "+responseString);
				
				oConn = DBCPNewConnection.getConnection();
				oStmt = oConn.createStatement();
				String delt = "DELETE FROM ibnk.DT_ACT_STATEMENT WHERE MAILID = '" + sUserID + "' and SESSIONID = '"+sSessionID+"' ";
		        oStmt.executeUpdate(delt);
		
				//	System.out.println("response ===>>> "+responseString);
					
				JSONObject responseJson = new JSONObject(responseString);
					str = responseJson.getString("pOutJsnLad");
					
				JSONObject secondReasponse = new JSONObject(str);
				
					sLadStatusTitle = secondReasponse.getString("lad_status_title");
					System.out.println("sLadStatusTitle ===>>> "+sLadStatusTitle);
					str1 = secondReasponse.getString("lad_status");
					System.out.println("str1 ===>>> "+str1);
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
					String Agreement_No ="";
					String Account_Name ="";
					String Start_Date ="";
					String Maturity ="";
					String Sanction_Limit ="";
					String Cumulative_Disb_Amount ="";
					String Outstanding_Amount ="";
					String Available_Limit ="";
					String Loan_Rate ="";
					String Deposit_Amount ="";
					String Liened_Deposit_AC ="";
					String AC_Status ="";
					
					
				//	 SL_NO = object.getString("SLNO");
					 Agreement_No = object.getString("Agreement No.");
					 Account_Name =object.getString("Account Name");
					 Start_Date = object.getString("Start Date");
					 Maturity = object.getString("Maturity");
					 Sanction_Limit = object.getString("Sanction Limit");
					 Cumulative_Disb_Amount = object.getString("Cumulative Disb Amount");
					 Outstanding_Amount = object.getString("Outstanding Amount");
					 Available_Limit = object.getString("Available Limit");
					 Loan_Rate = object.getString("Loan Rate");
					 Deposit_Amount = object.getString("Deposit Amount");
					 Liened_Deposit_AC = object.getString("Liened Deposit A/C(s)");
					 AC_Status = object.getString("A/C Status");
				
					String query1 =  "INSERT INTO ibnk.DT_ACT_STATEMENT(SERNUM,MAILID, SESSIONID,VALDAT,DOCDES,BRANCD,CHQNUM,REMARK,ACTNUM,CURBAL,OPRDES,DBAMFC,CRAMFC,liendepacc)"
							 + "VALUES ('"+Deposit_Amount+"','"+sUserID+"','"+sSessionID+"','"+Start_Date+"','"+AC_Status+"','"+Account_Name+"','"+Agreement_No+"','"+Loan_Rate+"','"+Maturity+"','"+Sanction_Limit+"','"+Cumulative_Disb_Amount+"','"+Outstanding_Amount+"','"+Available_Limit+"','"+Liened_Deposit_AC+"')";
					
					oStmt.executeUpdate(query1);
				
					oLoanAgainstDepositStatusBO.setErrorCode("0");
			}	
			
			}else {
				
				oLoanAgainstDepositStatusBO.setErrorCode("1");
				oLoanAgainstDepositStatusBO.setErrorMessage("No Data Found.." +StatusCode);
				
			}
		}catch(Exception ex) {
				ex.printStackTrace();
			oLoanAgainstDepositStatusBO.setErrorCode("1");
			oLoanAgainstDepositStatusBO.setErrorMessage("No Data Found.." +StatusCode);
			}
				
				
	return oLoanAgainstDepositStatusBO;
				
	}


}
