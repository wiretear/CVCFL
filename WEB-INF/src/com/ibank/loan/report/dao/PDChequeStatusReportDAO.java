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
import com.ibank.loan.report.bo.PDChequeStatusReportBO;
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

public class PDChequeStatusReportDAO  {
	private WritableCellFormat sTextArialBold;
	private WritableCellFormat sTextTime;
	private WritableCellFormat sNumberFloat;
	private WritableCellFormat sTextTimeBoldUnderline;

	
	//DBCP oPool = DBCP.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public PDChequeStatusReportBO getPermissionCheckPro(String sUserID,			String sSessionID,
											   String sRemoteIPAddress,	String sClientActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_ibnk_CLIENT_USER_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oPDChequeStatusReportBO.setErrorCode(""+oStmt.getInt(5));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getMenuCheckPro(String sUserID, 			String sSessionID, 
											   String sRemoteIPAddress,	String sClientActionPath) throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_CLIENT_MAIN_MENU_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oPDChequeStatusReportBO.setErrorCode("" + oStmt.getInt(5));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getSessionCheckPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_CLIENT_SESSION_VERIFY(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oPDChequeStatusReportBO.setErrorCode(""+oStmt.getInt(3));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getMessageInformation(String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList tmpList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(ERRMSG,' ')");
			sql.append("FROM ibnk.SY_MESSAGE ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				PDChequeStatusReportBO oTransactionListBO = new PDChequeStatusReportBO();
				oTransactionListBO.setErrorMessage(oRs.getString(1));
				tmpList.add(oTransactionListBO);
			}
			oPDChequeStatusReportBO.setList(tmpList);
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getMenuList(String sUserID,String sSessionID) throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
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
			sql.append("FROM ibnk.SY_FAVORITES ");
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
			oPDChequeStatusReportBO.setMenuList(aMenuList);
			oPDChequeStatusReportBO.setMenuNameList(aMenuNameList);
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getClearPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_ACCOUNT_PROFILE_CLEAR(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oPDChequeStatusReportBO.setErrorCode(""+oStmt.getInt(3));
			oPDChequeStatusReportBO.setErrorMessage(oStmt.getString(4));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getTransactionPro(String sUserID,String sSessionID)throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_RUNNING_ACTNUM(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(3);
			oPDChequeStatusReportBO.setErrorCode(sErrorCode);
			oPDChequeStatusReportBO.setErrorMessage(oStmt.getString(4));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getTransactionBalPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo)throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_BALANCE_ACTNUM(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(5);
			oPDChequeStatusReportBO.setErrorCode(sErrorCode);
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getAccountData(String sUserID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
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
			aAccountInfoNameList.add(object.getString("AC_TITLE")+"-"+object.getString("AC_NO")+"-"+"("+object.getString("AC_STATUS")+")");
			aAccountInfoNameList.add(object.getString("AC_NO"));					
			//data.add(oIBankingUserLogInBOList);
		}
			
			oPDChequeStatusReportBO.setAccountInfoList(aAccountInfoList);
			oPDChequeStatusReportBO.setAccountInfoNameList(aAccountInfoNameList);
			oPDChequeStatusReportBO.setErrorCode("0");
			
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(ADD_MONTHS(SYSDATE,-1),'dd/mm/rrrr') FIRSTDATE, ");
			sql.append("TO_CHAR(SYSDATE,'dd/mm/rrrr') LASTDATE  ");
			sql.append("FROM DUAL");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oPDChequeStatusReportBO.setDateFrom(oRs.getString(1));
				oPDChequeStatusReportBO.setDateTo(oRs.getString(2));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getCompanyCode(String sAccountInfo) throws Exception {
	
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT SUBSTR ('"+sAccountInfo+"', 1, INSTR ('"+sAccountInfo+"', '-') - 1) comcod, SUBSTR ('"+sAccountInfo+"', INSTR ('"+sAccountInfo+"', '-') + 1) actnum ");
			sql.append("FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oPDChequeStatusReportBO.setCompanyCode(oRs.getString(1));
				oPDChequeStatusReportBO.setAccountNo(oRs.getString(2));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getTransactionSearchPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNumber,String sDateFrom,String sDateTo)throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_RUNNING_BALANCE_INFO(?,?,?,?,?,?,?,?)}");
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
			oPDChequeStatusReportBO.setErrorCode(sErrorCode);
			oPDChequeStatusReportBO.setErrorMessage(oStmt.getString(8));
		
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getTransactionPreviewPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_TRANSACTION_PREVIEW(?,?,?,?,?,?,?,?)}");
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
			oPDChequeStatusReportBO.setErrorCode(""+oStmt.getInt(7));
			oPDChequeStatusReportBO.setErrorMessage(oStmt.getString(8));
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
		return oPDChequeStatusReportBO;
	}
	
	public PDChequeStatusReportBO getTransactionPreviewPro1(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_TRANSACTION_PREVIEWXL(?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.setString(5, sDateFrom);
		oStmt.setString(6, sDateTo);
		oStmt.registerOutParameter(7, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oPDChequeStatusReportBO.setErrorCode(""+oStmt.getInt(7));
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
		return oPDChequeStatusReportBO;
	}
	
	public PDChequeStatusReportBO getTransactionInformation(String sUserID,String sSessionID) throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
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
			sql.append("FROM ibnk.DT_ACT_CUSTOMER_INFO ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				PDChequeStatusReportBO oTransactionCustomerInfoListBO = new PDChequeStatusReportBO();
				oTransactionCustomerInfoListBO.setTitle(oRs.getString(1));
				oTransactionCustomerInfoListBO.setCustomerInfo(oRs.getString(2));
				aTempCustomerList.add(oTransactionCustomerInfoListBO);
			}
			oPDChequeStatusReportBO.setCustomerInfoList(aTempCustomerList);

			sql = new StringBuffer();
			sql.append("SELECT  NVL(VALDAT,' '), NVL(CHQNUM,' '), NVL(REMARK,' '), ");
			sql.append("NVL(DBAMFC,' '), NVL(CRAMFC,' '), NVL(CURBAL,' '), NVL(SERNUM,0) ");
			sql.append("FROM ibnk.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				PDChequeStatusReportBO oTransactionStatementListBO = new PDChequeStatusReportBO();
				oTransactionStatementListBO.setDateInfo(oRs.getString(1));
				oTransactionStatementListBO.setChqNumber(oRs.getString(2));
				oTransactionStatementListBO.setParticulars(oRs.getString(3));
				oTransactionStatementListBO.setDebitAmount(oRs.getString(4));
				oTransactionStatementListBO.setCreditAmount(oRs.getString(5));
				oTransactionStatementListBO.setBalanceAmount(oRs.getString(6));
				oTransactionStatementListBO.setSerialNo(oRs.getString(7));
				aTempStatementList.add(oTransactionStatementListBO);
			}
			oPDChequeStatusReportBO.setStatementList(aTempStatementList);

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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getMaxRecord(String sUserID,String sSessionID) throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM ibnk.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oPDChequeStatusReportBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getCountRecord(String sUserID,String sSessionID) throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM ibnk.DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oPDChequeStatusReportBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getMaxRecordRequest(String sUserID,String sSessionID) throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM ibnk.DT_ACT_REQUEST ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oPDChequeStatusReportBO.setDisplayRequestTotalRecord(oRs.getString(1));
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getDisplayRecordPro(String sDisplayAction, String sDisplayInLastRecord, String sDisplayInTotalRecord, String sDisplayFrequency)throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call ibnk.DPR_DISPLAY_RECORD(?,?,?,?,?,?,?,?)}");
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
			oPDChequeStatusReportBO.setDisplayOutFirstRecord(""+oStmt.getInt(5));
			oPDChequeStatusReportBO.setDisplayOutLastRecord(""+oStmt.getInt(6));
			oPDChequeStatusReportBO.setDisplayOutMessage(oStmt.getString(7));
			oPDChequeStatusReportBO.setErrorCode(""+oStmt.getInt(8));
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
		return oPDChequeStatusReportBO;
	}
	/*
	 * PROCEDURE dpr_cus_account_rep_api_param (
      in_terminalid         IN       VARCHAR2,
      in_ipimeno            IN       VARCHAR2,
      in_mailid             IN       VARCHAR2,
      in_sessionid          IN       VARCHAR2,
      in_compid             IN       VARCHAR2,--001
      in_oprmod             IN       VARCHAR2,-- I
      in_repid              IN       VARCHAR2, --Report id
      out_targeturl_token   OUT      VARCHAR2,
      out_targeturl_rep     OUT      VARCHAR2,
      out_userid            OUT      VARCHAR2,
      out_username          OUT      VARCHAR2,
      out_seccode           OUT      VARCHAR2,                      --password
      out_code              OUT      INTEGER,
      out_message           OUT      VARCHAR2
   )
	 */
	public PDChequeStatusReportBO getApiDetails(String sTerminalID, String sIpimeNo, String sMailID,String sSessionid,String sCompId,String sOprMode,String sReportID )throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
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
			oPDChequeStatusReportBO.setTargetUrlToken(oStmt.getString(8));
			oPDChequeStatusReportBO.setTargetUrlApi(oStmt.getString(9));
			oPDChequeStatusReportBO.setUserID(oStmt.getString(10));
			oPDChequeStatusReportBO.setUserName(oStmt.getString(11));
			oPDChequeStatusReportBO.setApiPassword(oStmt.getString(12));
			oPDChequeStatusReportBO.setErrorCode(""+oStmt.getInt(13));
			oPDChequeStatusReportBO.setErrorMessage(oStmt.getString(14));
			System.out.println("Api resposnse code  dao"+oStmt.getInt(13));
			System.out.println("Api resposnse url  dao"+oStmt.getString(9));
			System.out.println("Api resposnse url  dao message"+oStmt.getString(14));

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
		return oPDChequeStatusReportBO;
	}
	
	
	public PDChequeStatusReportBO getFileName() throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTmpList = new ArrayList();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(SYSDATE,'DDMMRRRRHH24MMSS') FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oPDChequeStatusReportBO.setFile(oRs.getString(1));
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
		return oPDChequeStatusReportBO;
	}	

	public PDChequeStatusReportBO getCreateContent(WritableSheet sheet,String sUserID,String sSessionID) throws Exception {
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
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		try {
			sql.append("SELECT NVL(ACTYPE,' ')ACTYPE, NVL(TRNCOD,' ')TRNCOD ");
			sql.append("FROM ibnk.dt_CUSINFO ");	
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
			sql2.append("FROM ibnk.dt_WEBINFO ");	
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
		return oPDChequeStatusReportBO;
	}
	public PDChequeStatusReportBO getXLSFile(HttpSession session,
									String sUserID, 
									String sSessionID,		 
									String sFileID) throws Exception {
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		try {
			String sCreateDirectory = "webapps/ibnk/AccountStatement/BalanceDetailReport/" + sSessionID;
			
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
													new File("C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/ibnk/pages/images/reportlogo.png")); //Supports only 'png' images
		    excelSheet.addImage(image);//requires the library jxl-2.6.12.jar to be in classpath.
			createLabel(excelSheet);
			getCreateContent(excelSheet,sUserID,sSessionID);
			
			
			
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oPDChequeStatusReportBO;
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
	
	public ArrayList getibnkMenu(String sUserID, String sSessionID){
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
			sql.append("FROM ibnk.SY_MAINMENU " );
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
				oPDChequeStatusReportBO.setNodeId(oRs.getString(1));
				oPDChequeStatusReportBO.setParentId(oRs.getString(2));
				if (oRs.getString(5).equals("Enquiries")){
					oPDChequeStatusReportBO.setNodeName("Enquiries");
				}else{
					oPDChequeStatusReportBO.setNodeName(oRs.getString(3));
				}
				oPDChequeStatusReportBO.setUrl(oRs.getString(4));
				aTmpList.add(oPDChequeStatusReportBO);
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
	
	public PDChequeStatusReportBO getExecuteStatementProAPI(String sUrl,String sUserID,String sSessionID,String sAccountNumber, 
			   String sFromDate, 
			   String sToDate) throws JSONException, ClientProtocolException, IOException, SQLException {

				String url;
				String url2;
				String str ="";
				String str1 ="";
				String errorCode = "";
				String errorMsg = "";
				String pErrorFlag = "";
				String pErrorMessage = "";
				String  accountName="";
				int StatusCode = 0;
				String responseString ="";
				Connection oConn = null;
				Statement oStmt = null;
				oConn = DBCPNewConnection.getConnection();
				oStmt = oConn.createStatement();
				
/*
				 responseString = "{\"pOutJsnCqStatus\": {\r\n" + 
				 		"		\"cheque_status\": [{\r\n" + 
				 		"					\"SL_NO\": \"01\",\r\n" + 
				 		"					\"CHQ_DATE\": \"15\\/03\\/2019\",\r\n" + 
				 		"					\"CHQ_LEAF_NO\": \"1681016\",\r\n" + 
				 		"					\"BANK_NAME\": \"BANK ASIA LTD.\",\r\n" + 
				 		"					\"BRANCH_NAME\": \"Any Branch\",\r\n" + 
				 		"					\"BANK_AC_NO\": \"00434004355\",\r\n" + 
				 		"					\"CHQ_AMOUNT\": \"49770\",\r\n" + 
				 		"					\"CHQ_STATUS\": \"Available\"\r\n },"
				 		+ " {\"SL_NO\": \"02\",\r\n" + 
				 		"			\"CHQ_DATE\": \"15\\/03\\/2019\",\r\n" + 
				 		"			\"CHQ_LEAF_NO\": \"1681017\",\r\n" + 
				 		"			\"BANK_NAME\": \"BANK ASIA LTD.\",\r\n" + 
				 		"			\"BRANCH_NAME\": \"Any Branch\",\r\n" + 
				 		"			\"BANK_AC_NO\": \"00434004361\",\r\n" + 
				 		"			\"CHQ_AMOUNT\": \"508200\",\r\n" + 
				 		"			\"CHQ_STATUS\": \"Available\"\r\n" + 
				 		"		}]}}";
				
				*/
				
			PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();

		try {   
			
			OkHttpClient client = new OkHttpClient().newBuilder().build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = RequestBody.create(mediaType, "");
				System.out.println("parameter "+sAccountNumber);
		Request request = new Request.Builder().url(sUrl).method("POST", body)
				.addHeader("pAcNo", sAccountNumber)
			
				//.addHeader("Cookie", "ORA_WWV_USER_218214708090307=ORA_WWV-fHzrA9UlfL8RVErC21LfNf3g")
				.build();
		
			Response response = client.newCall(request).execute(); 
				 responseString = response.body().string();
				System.out.println("response ===>>> "+responseString);
				
			JSONObject responseJson = new JSONObject(responseString);
				str = responseJson.getString("pOutJsnCqStatus");
				
			JSONObject secondReasponse = new JSONObject(str);
				str1 = secondReasponse.getString("cheque_status");
				accountName = secondReasponse.getString("cheque_status_title");
				oPDChequeStatusReportBO.setAccountName(accountName);
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
				oPDChequeStatusReportBO.setErrorCode("1");
				oPDChequeStatusReportBO.setErrorMessage("NO DATA FOUND.." );
				String deleteQuery="DELETE FROM ibnk.DT_ACT_STATEMENT WHERE MAILID = '" + sUserID+ "'and SESSIONID ='"+sSessionID+"' ";
			 	oStmt.executeUpdate(deleteQuery);
		for (int i = 0; i < arry.length(); i++) {
			
			System.out.println("response ===>>> "+"i am at object");
			  PDChequeStatusReportBO oPDChequeStatusReportBOList = new PDChequeStatusReportBO();
				JSONObject object = arry.getJSONObject(i);
				String SL_NO ="";
				String CHQ_DATE ="";
				String CHQ_LEAF_NO ="";
				String BANK_NAME ="";
				String BRANCH_NAME ="";
				String BANK_AC_NO ="";
				String CHQ_AMOUNT ="";
				String CHQ_STATUS ="";
				 SL_NO = object.getString("SL_NO");
				 CHQ_DATE = object.getString("CHQ_DATE");
				 CHQ_LEAF_NO =object.getString("CHQ_LEAF_NO");
				 BANK_NAME = object.getString("BANK_NAME");
				 BRANCH_NAME = object.getString("BRANCH_NAME");
				 BANK_AC_NO = object.getString("BANK_AC_NO");
				 CHQ_AMOUNT = object.getString("CHQ_AMOUNT");
				 CHQ_STATUS = object.getString("CHQ_STATUS");
			
				String query1 =  "INSERT INTO ibnk.DT_ACT_STATEMENT(SERNUM,MAILID, SESSIONID,VALDAT,CHQNUM,DOCDES,REMARK,ACTNUM,CURBAL,OPRDES)"
						 + "VALUES ('"+SL_NO+"','"+sUserID+"','"+sSessionID+"','"+CHQ_DATE+"','"+CHQ_LEAF_NO+"','"+BANK_NAME+"','"+BRANCH_NAME+"','"+BANK_AC_NO+"','"+CHQ_AMOUNT+"','"+CHQ_STATUS+"')";
				
				oStmt.executeUpdate(query1);
			System.out.println("DOne");
				/*oPDChequeStatusReportBOList.setSerialNo(object.getString("SL_NO"));
				oPDChequeStatusReportBOList.setCreditAmount(object.getString("CHQ_DATE"));
				oPDChequeStatusReportBOList.setDebitAmount(object.getString("CHQ_LEAF_NO"));
				oPDChequeStatusReportBOList.setChqNumber(object.getString("BANK_NAME"));
				String dateString = object.getString("BANK_AC_NO");
				SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
				Date d = sdf.parse(dateString);
				sdf.applyPattern(NEW_FORMAT);
				newDateString = sdf.format(d);
				oPDChequeStatusReportBOList.setDateInfo(newDateString);
				oPDChequeStatusReportBOList.setBalanceAmount(object.getString("BALANCE"));
				oPDChequeStatusReportBOList.setParticulars(object.getString("NARRATION"));
				
				data.add(oPDChequeStatusReportBOList);*/
				oPDChequeStatusReportBO.setErrorCode("0");
		}
	//	oStmt.executeUpdate(query1);
				
			//oPDChequeStatusReportBO.setStatementList(data);
			
				
		}catch(Exception ex) {
			
				ex.printStackTrace();
			oPDChequeStatusReportBO.setErrorCode("1");
			oPDChequeStatusReportBO.setErrorMessage("NO DATA FOUND..");
			}
				
				
	return oPDChequeStatusReportBO;
				
	}
}