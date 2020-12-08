package com.ibank.deposit.report.dao;

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

import com.ibank.deposit.report.bo.BEFTNIncomeSchemeReportBO;
import com.ibank.loan.report.bo.AccountSummaryReportBO;
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

public class BEFTNIncomeSchemeReportDAO {
	private WritableCellFormat sTextArialBold;
	private WritableCellFormat sTextTime;
	private WritableCellFormat sNumberFloat;
	private WritableCellFormat sTextTimeBoldUnderline;

	
	//DBCP oPool = DBCP.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public BEFTNIncomeSchemeReportBO getPermissionCheckPro(String sUserID,			String sSessionID,
											   String sRemoteIPAddress,	String sClientActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call .DPR__CLIENT_USER_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oBEFTNIncomeSchemeReportBO.setErrorCode(""+oStmt.getInt(5));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getMenuCheckPro(String sUserID, 			String sSessionID, 
											   String sRemoteIPAddress,	String sClientActionPath) throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_CLIENT_MAIN_MENU_CHECK(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sRemoteIPAddress);
		oStmt.setString(4, sClientActionPath);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oBEFTNIncomeSchemeReportBO.setErrorCode("" + oStmt.getInt(5));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getSessionCheckPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_CLIENT_SESSION_VERIFY(?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oBEFTNIncomeSchemeReportBO.setErrorCode(""+oStmt.getInt(3));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getApiDetails(String sTerminalID, String sIpimeNo, String sMailID,String sSessionid,String sCompId,String sOprMode,String sReportID )throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
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
			
			oBEFTNIncomeSchemeReportBO.setTargetUrlToken(oStmt.getString(8));
			oBEFTNIncomeSchemeReportBO.setTargetUrlApi(oStmt.getString(9));
			oBEFTNIncomeSchemeReportBO.setUserID(oStmt.getString(10));
			oBEFTNIncomeSchemeReportBO.setUserName(oStmt.getString(11));
			oBEFTNIncomeSchemeReportBO.setApiPassword(oStmt.getString(12));
			oBEFTNIncomeSchemeReportBO.setErrorCode(""+oStmt.getInt(13));
			oBEFTNIncomeSchemeReportBO.setErrorMessage(oStmt.getString(14));
			System.out.println("Api resposnse code  dao "+oStmt.getInt(13));
			System.out.println("Api resposnse url  dao "+oStmt.getString(9));
			System.out.println("Api resposnse url  dao message "+oStmt.getString(14));

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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getMessageInformation(String sUserID,String sSessionID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList tmpList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(ERRMSG,' ')");
			sql.append("FROM .SY_MESSAGE ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				BEFTNIncomeSchemeReportBO oTransactionListBO = new BEFTNIncomeSchemeReportBO();
				oTransactionListBO.setErrorMessage(oRs.getString(1));
				tmpList.add(oTransactionListBO);
			}
			oBEFTNIncomeSchemeReportBO.setList(tmpList);
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getMenuList(String sUserID,String sSessionID) throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
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
			sql.append("FROM .SY_FAVORITES ");
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
			oBEFTNIncomeSchemeReportBO.setMenuList(aMenuList);
			oBEFTNIncomeSchemeReportBO.setMenuNameList(aMenuNameList);
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getClearPro(String sUserID,String sSessionID)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_ACCOUNT_PROFILE_CLEAR(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oBEFTNIncomeSchemeReportBO.setErrorCode(""+oStmt.getInt(3));
			oBEFTNIncomeSchemeReportBO.setErrorMessage(oStmt.getString(4));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getTransactionPro(String sUserID,String sSessionID)throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_RUNNING_ACTNUM(?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		
		oStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(3);
			oBEFTNIncomeSchemeReportBO.setErrorCode(sErrorCode);
			oBEFTNIncomeSchemeReportBO.setErrorMessage(oStmt.getString(4));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getTransactionBalPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo)throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_BALANCE_ACTNUM(?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			sErrorCode= ""+oStmt.getInt(5);
			oBEFTNIncomeSchemeReportBO.setErrorCode(sErrorCode);
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getAccountData(String sUserID) throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
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
			aAccountInfoNameList.add(object.getString("AC_NO"));	
			aAccountInfoList.add(object.getString("AC_TITLE")+"-"+object.getString("AC_NO"));
			//data.add(oIBankingUserLogInBOList);
		}
			
			oBEFTNIncomeSchemeReportBO.setAccountInfoList(aAccountInfoList);
			oBEFTNIncomeSchemeReportBO.setAccountInfoNameList(aAccountInfoNameList);
			oBEFTNIncomeSchemeReportBO.setErrorCode("0");
			
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(ADD_MONTHS(SYSDATE,-1),'dd/mm/rrrr') FIRSTDATE, ");
			sql.append("TO_CHAR(SYSDATE,'dd/mm/rrrr') LASTDATE  ");
			sql.append("FROM DUAL");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oBEFTNIncomeSchemeReportBO.setDateFrom(oRs.getString(1));
				oBEFTNIncomeSchemeReportBO.setDateTo(oRs.getString(2));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getCompanyCode(String sAccountInfo) throws Exception {
	
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT SUBSTR ('"+sAccountInfo+"', 1, INSTR ('"+sAccountInfo+"', '-') - 1) comcod, SUBSTR ('"+sAccountInfo+"', INSTR ('"+sAccountInfo+"', '-') + 1) actnum ");
			sql.append("FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oBEFTNIncomeSchemeReportBO.setCompanyCode(oRs.getString(1));
				oBEFTNIncomeSchemeReportBO.setAccountNo(oRs.getString(2));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getTransactionSearchPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNumber,String sDateFrom,String sDateTo)throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		String sErrorCode = null;
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_RUNNING_BALANCE_INFO(?,?,?,?,?,?,?,?)}");
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
			oBEFTNIncomeSchemeReportBO.setErrorCode(sErrorCode);
			oBEFTNIncomeSchemeReportBO.setErrorMessage(oStmt.getString(8));
		
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getTransactionPreviewPro(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_TRANSACTION_PREVIEW(?,?,?,?,?,?,?,?)}");
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
			oBEFTNIncomeSchemeReportBO.setErrorCode(""+oStmt.getInt(7));
			oBEFTNIncomeSchemeReportBO.setErrorMessage(oStmt.getString(8));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	
	public BEFTNIncomeSchemeReportBO getTransactionPreviewPro1(String sUserID,String sSessionID,String sCompanyID,String sAccountNo,String sDateFrom,String sDateTo)throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_TRANSACTION_PREVIEWXL(?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sAccountNo);
		oStmt.setString(5, sDateFrom);
		oStmt.setString(6, sDateTo);
		oStmt.registerOutParameter(7, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oBEFTNIncomeSchemeReportBO.setErrorCode(""+oStmt.getInt(7));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	
	public BEFTNIncomeSchemeReportBO getTransactionInformation(String sUserID,String sSessionID) throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
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
			sql.append("FROM .DT_ACT_CUSTOMER_INFO ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				BEFTNIncomeSchemeReportBO oTransactionCustomerInfoListBO = new BEFTNIncomeSchemeReportBO();
				oTransactionCustomerInfoListBO.setTitle(oRs.getString(1));
				oTransactionCustomerInfoListBO.setCustomerInfo(oRs.getString(2));
				aTempCustomerList.add(oTransactionCustomerInfoListBO);
			}
			oBEFTNIncomeSchemeReportBO.setCustomerInfoList(aTempCustomerList);

			sql = new StringBuffer();
			sql.append("SELECT  NVL(VALDAT,' '), NVL(CHQNUM,' '), NVL(REMARK,' '), ");
			sql.append("NVL(DBAMFC,' '), NVL(CRAMFC,' '), NVL(CURBAL,' '), NVL(SERNUM,0) ");
			sql.append("FROM .DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				BEFTNIncomeSchemeReportBO oTransactionStatementListBO = new BEFTNIncomeSchemeReportBO();
				oTransactionStatementListBO.setDateInfo(oRs.getString(1));
				oTransactionStatementListBO.setChqNumber(oRs.getString(2));
				oTransactionStatementListBO.setParticulars(oRs.getString(3));
				oTransactionStatementListBO.setDebitAmount(oRs.getString(4));
				oTransactionStatementListBO.setCreditAmount(oRs.getString(5));
				oTransactionStatementListBO.setBalanceAmount(oRs.getString(6));
				oTransactionStatementListBO.setSerialNo(oRs.getString(7));
				aTempStatementList.add(oTransactionStatementListBO);
			}
			oBEFTNIncomeSchemeReportBO.setStatementList(aTempStatementList);

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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getMaxRecord(String sUserID,String sSessionID) throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM .DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oBEFTNIncomeSchemeReportBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getCountRecord(String sUserID,String sSessionID) throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM .DT_ACT_STATEMENT ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oBEFTNIncomeSchemeReportBO.setDisplayTotalRecord(oRs.getString(1));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getMaxRecordRequest(String sUserID,String sSessionID) throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append(" SELECT COUNT(SERNUM) FROM .DT_ACT_REQUEST ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oBEFTNIncomeSchemeReportBO.setDisplayRequestTotalRecord(oRs.getString(1));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getDisplayRecordPro(String sDisplayAction, String sDisplayInLastRecord, String sDisplayInTotalRecord, String sDisplayFrequency)throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		CallableStatement oStmt = oConn.prepareCall("{call .DPR_DISPLAY_RECORD(?,?,?,?,?,?,?,?)}");
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
			oBEFTNIncomeSchemeReportBO.setDisplayOutFirstRecord(""+oStmt.getInt(5));
			oBEFTNIncomeSchemeReportBO.setDisplayOutLastRecord(""+oStmt.getInt(6));
			oBEFTNIncomeSchemeReportBO.setDisplayOutMessage(oStmt.getString(7));
			oBEFTNIncomeSchemeReportBO.setErrorCode(""+oStmt.getInt(8));
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
		return oBEFTNIncomeSchemeReportBO;
	}
	
	
	public BEFTNIncomeSchemeReportBO getFileName() throws Exception {
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRs = null;
		ArrayList aTmpList = new ArrayList();
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		StringBuffer sql = new StringBuffer();
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		try {
			sql = new StringBuffer();
			sql.append("SELECT TO_CHAR(SYSDATE,'DDMMRRRRHH24MMSS') FROM DUAL ");
			oRs = oStmt.executeQuery(sql.toString());
			if (oRs.next()) {
				oBEFTNIncomeSchemeReportBO.setFile(oRs.getString(1));
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
		return oBEFTNIncomeSchemeReportBO;
	}	

	public BEFTNIncomeSchemeReportBO getCreateContent(WritableSheet sheet,String sUserID,String sSessionID) throws Exception {
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
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		try {
			sql.append("SELECT NVL(ACTYPE,' ')ACTYPE, NVL(TRNCOD,' ')TRNCOD ");
			sql.append("FROM .dt_CUSINFO ");	
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
			sql2.append("FROM .dt_WEBINFO ");	
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
		return oBEFTNIncomeSchemeReportBO;
	}
	public BEFTNIncomeSchemeReportBO getXLSFile(HttpSession session,
									String sUserID, 
									String sSessionID,		 
									String sFileID) throws Exception {
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		try {
			String sCreateDirectory = "webapps//AccountStatement/BalanceDetailReport/" + sSessionID;
			
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
													new File("C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps//pages/images/reportlogo.png")); //Supports only 'png' images
		    excelSheet.addImage(image);//requires the library jxl-2.6.12.jar to be in classpath.
			createLabel(excelSheet);
			getCreateContent(excelSheet,sUserID,sSessionID);
			
			
			
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oBEFTNIncomeSchemeReportBO;
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
	
	public ArrayList getMenu(String sUserID, String sSessionID){
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
			sql.append("FROM .SY_MAINMENU " );
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
				oBEFTNIncomeSchemeReportBO.setNodeId(oRs.getString(1));
				oBEFTNIncomeSchemeReportBO.setParentId(oRs.getString(2));
				if (oRs.getString(5).equals("Enquiries")){
					oBEFTNIncomeSchemeReportBO.setNodeName("Enquiries");
				}else{
					oBEFTNIncomeSchemeReportBO.setNodeName(oRs.getString(3));
				}
				oBEFTNIncomeSchemeReportBO.setUrl(oRs.getString(4));
				aTmpList.add(oBEFTNIncomeSchemeReportBO);
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
	
	 public BEFTNIncomeSchemeReportBO getExecuteStatementProAPI(String sUrl,String sUserID,String sSessionID,String sAccountNumber, 
			   String sFromDate, 
			   String sToDate) throws JSONException, ClientProtocolException, IOException, SQLException {
		 String responseString ="";
				String url;
				String url2;
				String str ="";
				String SL_NO="";
				String str1 ="";
				String str2 ="";
				String str3 ="";
				String errorCode = "";
				String errorMsg = "";
				String pErrorFlag = "";
				String pErrorMessage = "";

				String CUST_NAME="";
                String BANK_NAME="";
                String BRANCH_NAME="";
        		String ROUTING_NO="";
        		String BANK_AC_NO="";
        		
                String BEFTN_AMT="";
            	Connection oConn = null;
				Statement oStmt = null;
				oConn = DBCPNewConnection.getConnection();
				oStmt = oConn.createStatement();
				 /*String responsestring="{\r\n" + 
					 		"    \"pOutJsnBeftnDep\": \"{\\n\\\"beftn\\\":[\\n{\\n\\\"SL_NO\\\":1\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3600\\n}\\n,{\\n\\\"SL_NO\\\":2\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":3\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":4\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":5\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":6\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":7\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":8\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":9\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":10\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"090260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":11\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3600\\n}\\n,{\\n\\\"SL_NO\\\":12\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":13\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":14\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":15\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":16\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":17\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":18\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":19\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":20\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"195260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":21\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3600\\n}\\n,{\\n\\\"SL_NO\\\":22\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":23\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":24\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":25\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":26\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":27\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":28\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":29\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n,{\\n\\\"SL_NO\\\":30\\n,\\\"CUST_NAME\\\":\\\" Maria Dewan\\\"\\n,\\\"BANK_NAME\\\":\\\"SOCIAL ISLAMI BANK LTD\\\"\\n,\\\"BRANCH_NAME\\\":\\\"BASHUNDHARA\\\"\\n,\\\"ROUTING_NO\\\":\\\"245260555\\\"\\n,\\\"BANK_AC_NO\\\":\\\"0771280000579\\\"\\n,\\\"BEFTN_AMT\\\":3750\\n}\\n]\\n}\\n\"\r\n" + 
					 		"}";
					 		*/
				int StatusCode = 0;
				
				BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
				oBEFTNIncomeSchemeReportBO.setErrorCode("1");
				oBEFTNIncomeSchemeReportBO.setErrorMessage("NO DATA FOUND..");

				String deleteQuery="DELETE FROM ibnk.DT_ACT_INFO WHERE MAILID = '" + sUserID+ "'and SESSIONID ='"+sSessionID+"' ";
			 	oStmt.executeUpdate(deleteQuery);
		try {     
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
			System.out.println("response ===>>> "+responseString);
					             	JSONObject responseJson=new JSONObject(responseString);
					                 str = (String) responseJson.getString("pOutJsnBeftnDep");
					                 System.out.println("STR "+str);
					                 JSONObject responseJson1=new JSONObject(str);
					                  str1 = (String) responseJson1.getString("beftn");
					                  str2 = (String) responseJson1.getString("beftn_branch");
					                  str3 = (String) responseJson1.getString("beftn_product");
					                  oBEFTNIncomeSchemeReportBO.setBeftnProduct(str3);
					                  oBEFTNIncomeSchemeReportBO.setBeftnName(str2);
					                  
					                
					                  JSONArray arry = new JSONArray(str1);
					              	for (int i = 0; i < arry.length(); i++) {
			                            JSONObject object = arry.getJSONObject(i);
			                            
			                            SL_NO = object.getString("SL_NO");
			                            CUST_NAME =object.getString("CUST_NAME");
			                            BANK_NAME = object.getString("BANK_NAME");
			                            BRANCH_NAME = object.getString("BRANCH_NAME");
			                            BANK_AC_NO = object.getString("BANK_AC_NO");
			                            BEFTN_AMT = object.getString("BEFTN_AMT");
			                            ROUTING_NO= object.getString("ROUTING_NO");
			                            String query1 =  "INSERT INTO ibnk.DT_ACT_INFO(MAILID,SESSIONID,SERNUM,BRANCD,BRANAM,ACTNUM,ACDESC,CRDNUM,ACTTIT)"
			           						 + "VALUES ('"+sUserID+"','"+sSessionID+"','"+SL_NO+"','"+CUST_NAME+"','"+BANK_NAME+"','"+BRANCH_NAME+"','"+ROUTING_NO+"','"+BANK_AC_NO+"','"+BEFTN_AMT+"')";
			                        	oStmt.executeUpdate(query1);
			                        	oBEFTNIncomeSchemeReportBO.setErrorCode("0");
			                        	System.out.print("I am Done");
			                            }
					              	
				
				
		}catch(Exception ex) {
			ex.printStackTrace();	
			oBEFTNIncomeSchemeReportBO.setErrorCode("1");
			oBEFTNIncomeSchemeReportBO.setErrorMessage("NO DATA FOUND..");
			}
				
				
	return oBEFTNIncomeSchemeReportBO;
				
	}
	 
				

}
