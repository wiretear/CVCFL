package com.ibank.loan.report.bo;

import java.io.Serializable;
import java.util.ArrayList;

import com.ibank.loan.report.dao.LoanAgainstDepositStatusDAO;



public class LoanAgainstDepositStatusBO implements Serializable {
	private String sSerialNo = "";
	private String sCompanyCode = "";
	private String sDateFrom = "";
	private String sDateTo = "";
	private String sAccountNo = "";
	private String sAccountInfo = "";
	private ArrayList aAccountInfoList = new ArrayList();
	private ArrayList aAccountInfoNameList = new ArrayList();
	private String sTitle = "";
	private String sCustomerInfo = "";
	private ArrayList aCustomerInfoList = new ArrayList();
	private String sDateInfo = "";
	private String sChqNumber = "";
	private String sParticulars = "";
	private String sDebitAmount = "";
	private String sCreditAmount = "";
	private String sBalanceAmount = "";
	private ArrayList aStatementList = new ArrayList();
	private ArrayList aRequestList = new ArrayList();
	private String sErrorCode = "";
	private String sErrorMessage = "";
	private ArrayList aList = new ArrayList();
	private String sMenu = "";
	private ArrayList aMenuList = new ArrayList();
	private ArrayList aMenuNameList = new ArrayList();
	private String sDisplayAction = "";
	private String sDisplayLastRecord = "";
	private String sDisplayTotalRecord = "";
	private String sDisplayFrequency = "";
	private ArrayList aDisplayFrequencyList = new ArrayList();
	private ArrayList aDisplayFrequencyNameList = new ArrayList();
	private String sDisplayOutFirstRecord = "";
	private String sDisplayOutLastRecord = "";
	private String sDisplayOutMessage = "";
	private String sDisplayTypeAction = "";
	private String sDisplayRequestTotalRecord = "";
	private String sDisplayRequestFrequency = "";
	private ArrayList aDisplayRequestFrequencyList = new ArrayList();
	private ArrayList aDisplayRequestFrequencyNameList = new ArrayList();
	
	private String sFile = "";
	private ArrayList aExcelList = new ArrayList();
	
	// NEW ADDED BY Sania
	
	private String sNodeId;
	private String sParentId;
	private String sNodeName="";
	private String sUrl="";
	private String sTagetUrl="";
	private String sAsOnDate = "";

	public String getAsOnDate() {
		return sAsOnDate;
	}
	public void setAsOnDate(String asOnDate) {
		this.sAsOnDate = asOnDate;
	}
	public String getTagetUrl() {
		return sTagetUrl;
	}
	public void setTagetUrl(String tagetUrl) {
		this.sTagetUrl = tagetUrl;
	}



	/**
	 * @return
	 */
	public LoanAgainstDepositStatusBO() {
	}
	/**
	 * @return
	 */
	public ArrayList getCustomerInfoList() {
		return aCustomerInfoList;
	}

	/**
	 * @param customerInfoList
	 */
	public void setCustomerInfoList(ArrayList customerInfoList) {
		aCustomerInfoList = customerInfoList;
	}

	/**
	 * @return
	 */
	public ArrayList getDisplayFrequencyList() {
		return aDisplayFrequencyList;
	}

	/**
	 * @param displayFrequencyList
	 */
	public void setDisplayFrequencyList(ArrayList displayFrequencyList) {
		aDisplayFrequencyList = displayFrequencyList;
	}

	/**
	 * @return
	 */
	public ArrayList getDisplayFrequencyNameList() {
		return aDisplayFrequencyNameList;
	}

	/**
	 * @param displayFrequencyNameList
	 */
	public void setDisplayFrequencyNameList(ArrayList displayFrequencyNameList) {
		aDisplayFrequencyNameList = displayFrequencyNameList;
	}

	/**
	 * @return
	 */
	public ArrayList getDisplayRequestFrequencyList() {
		return aDisplayRequestFrequencyList;
	}

	/**
	 * @param displayRequestFrequencyList
	 */
	public void setDisplayRequestFrequencyList(ArrayList displayRequestFrequencyList) {
		aDisplayRequestFrequencyList = displayRequestFrequencyList;
	}

	/**
	 * @return
	 */
	public ArrayList getDisplayRequestFrequencyNameList() {
		return aDisplayRequestFrequencyNameList;
	}

	/**
	 * @param displayRequestFrequencyNameList
	 */
	public void setDisplayRequestFrequencyNameList(ArrayList displayRequestFrequencyNameList) {
		aDisplayRequestFrequencyNameList = displayRequestFrequencyNameList;
	}

	/**
	 * @return
	 */
	public ArrayList getList() {
		return aList;
	}

	/**
	 * @param list
	 */
	public void setList(ArrayList list) {
		aList = list;
	}

	/**
	 * @return
	 */
	public ArrayList getMenuList() {
		return aMenuList;
	}

	/**
	 * @param menuList
	 */
	public void setMenuList(ArrayList menuList) {
		aMenuList = menuList;
	}

	/**
	 * @return
	 */
	public ArrayList getMenuNameList() {
		return aMenuNameList;
	}

	/**
	 * @param menuNameList
	 */
	public void setMenuNameList(ArrayList menuNameList) {
		aMenuNameList = menuNameList;
	}

	/**
	 * @return
	 */
	public ArrayList getRequestList() {
		return aRequestList;
	}

	/**
	 * @param requestList
	 */
	public void setRequestList(ArrayList requestList) {
		aRequestList = requestList;
	}

	/**
	 * @return
	 */
	public ArrayList getStatementList() {
		return aStatementList;
	}

	/**
	 * @param statementList
	 */
	public void setStatementList(ArrayList statementList) {
		aStatementList = statementList;
	}

	/**
	 * @return
	 */
	public String getAccountNo() {
		return sAccountNo;
	}

	/**
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		sAccountNo = accountNo;
	}

	/**
	 * @return
	 */
	public String getBalanceAmount() {
		return sBalanceAmount;
	}

	/**
	 * @param balanceAmount
	 */
	public void setBalanceAmount(String balanceAmount) {
		sBalanceAmount = balanceAmount;
	}

	/**
	 * @return
	 */
	public String getChqNumber() {
		return sChqNumber;
	}

	/**
	 * @param chqNumber
	 */
	public void setChqNumber(String chqNumber) {
		sChqNumber = chqNumber;
	}

	/**
	 * @return
	 */
	public String getCompanyCode() {
		return sCompanyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		sCompanyCode = companyCode;
	}

	/**
	 * @return
	 */
	public String getCreditAmount() {
		return sCreditAmount;
	}

	/**
	 * @param creditAmount
	 */
	public void setCreditAmount(String creditAmount) {
		sCreditAmount = creditAmount;
	}

	/**
	 * @return
	 */
	public String getCustomerInfo() {
		return sCustomerInfo;
	}

	/**
	 * @param customerInfo
	 */
	public void setCustomerInfo(String customerInfo) {
		sCustomerInfo = customerInfo;
	}

	/**
	 * @return
	 */
	public String getDateFrom() {
		return sDateFrom;
	}

	/**
	 * @param dateFrom
	 */
	public void setDateFrom(String dateFrom) {
		sDateFrom = dateFrom;
	}

	/**
	 * @return
	 */
	public String getDateInfo() {
		return sDateInfo;
	}

	/**
	 * @param dateInfo
	 */
	public void setDateInfo(String dateInfo) {
		sDateInfo = dateInfo;
	}

	/**
	 * @return
	 */
	public String getDateTo() {
		return sDateTo;
	}

	/**
	 * @param dateTo
	 */
	public void setDateTo(String dateTo) {
		sDateTo = dateTo;
	}

	/**
	 * @return
	 */
	public String getDebitAmount() {
		return sDebitAmount;
	}

	/**
	 * @param debitAmount
	 */
	public void setDebitAmount(String debitAmount) {
		sDebitAmount = debitAmount;
	}

	/**
	 * @return
	 */
	public String getDisplayAction() {
		return sDisplayAction;
	}

	/**
	 * @param displayAction
	 */
	public void setDisplayAction(String displayAction) {
		sDisplayAction = displayAction;
	}

	/**
	 * @return
	 */
	public String getDisplayFrequency() {
		return sDisplayFrequency;
	}

	/**
	 * @param displayFrequency
	 */
	public void setDisplayFrequency(String displayFrequency) {
		sDisplayFrequency = displayFrequency;
	}

	/**
	 * @return
	 */
	public String getDisplayLastRecord() {
		return sDisplayLastRecord;
	}

	/**
	 * @param displayLastRecord
	 */
	public void setDisplayLastRecord(String displayLastRecord) {
		sDisplayLastRecord = displayLastRecord;
	}

	/**
	 * @return
	 */
	public String getDisplayOutFirstRecord() {
		return sDisplayOutFirstRecord;
	}

	/**
	 * @param displayOutFirstRecord
	 */
	public void setDisplayOutFirstRecord(String displayOutFirstRecord) {
		sDisplayOutFirstRecord = displayOutFirstRecord;
	}

	/**
	 * @return
	 */
	public String getDisplayOutLastRecord() {
		return sDisplayOutLastRecord;
	}

	/**
	 * @param displayOutLastRecord
	 */
	public void setDisplayOutLastRecord(String displayOutLastRecord) {
		sDisplayOutLastRecord = displayOutLastRecord;
	}

	/**
	 * @return
	 */
	public String getDisplayOutMessage() {
		return sDisplayOutMessage;
	}

	/**
	 * @param displayOutMessage
	 */
	public void setDisplayOutMessage(String displayOutMessage) {
		sDisplayOutMessage = displayOutMessage;
	}

	/**
	 * @return
	 */
	public String getDisplayRequestFrequency() {
		return sDisplayRequestFrequency;
	}

	/**
	 * @param displayRequestFrequency
	 */
	public void setDisplayRequestFrequency(String displayRequestFrequency) {
		sDisplayRequestFrequency = displayRequestFrequency;
	}

	/**
	 * @return
	 */
	public String getDisplayRequestTotalRecord() {
		return sDisplayRequestTotalRecord;
	}

	/**
	 * @param displayRequestTotalRecord
	 */
	public void setDisplayRequestTotalRecord(String displayRequestTotalRecord) {
		sDisplayRequestTotalRecord = displayRequestTotalRecord;
	}

	/**
	 * @return
	 */
	public String getDisplayTotalRecord() {
		return sDisplayTotalRecord;
	}

	/**
	 * @param displayTotalRecord
	 */
	public void setDisplayTotalRecord(String displayTotalRecord) {
		sDisplayTotalRecord = displayTotalRecord;
	}

	/**
	 * @return
	 */
	public String getDisplayTypeAction() {
		return sDisplayTypeAction;
	}

	/**
	 * @param displayTypeAction
	 */
	public void setDisplayTypeAction(String displayTypeAction) {
		sDisplayTypeAction = displayTypeAction;
	}

	/**
	 * @return
	 */
	public String getErrorCode() {
		return sErrorCode;
	}

	/**
	 * @param errorCode
	 */
	public void setErrorCode(String errorCode) {
		sErrorCode = errorCode;
	}

	/**
	 * @return
	 */
	public String getErrorMessage() {
		return sErrorMessage;
	}

	/**
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		sErrorMessage = errorMessage;
	}

	/**
	 * @return
	 */
	public String getMenu() {
		return sMenu;
	}

	/**
	 * @param menu
	 */
	public void setMenu(String menu) {
		sMenu = menu;
	}

	/**
	 * @return
	 */
	public String getParticulars() {
		return sParticulars;
	}

	/**
	 * @param particulars
	 */
	public void setParticulars(String particulars) {
		sParticulars = particulars;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return sTitle;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		sTitle = title;
	}
	/**
	 * @return
	 */
	public String getSerialNo() {
		return sSerialNo;
	}

	/**
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
		sSerialNo = serialNo;
	}

	/**
	 * @return
	 */
	public String getAccountInfo() {
		return sAccountInfo;
	}

	/**
	 * @param accountInfo
	 */
	public void setAccountInfo(String accountInfo) {
		sAccountInfo = accountInfo;
	}

	/**
	 * @return
	 */
	public ArrayList getAccountInfoList() {
		return aAccountInfoList;
	}

	/**
	 * @param accountInfoList
	 */
	public void setAccountInfoList(ArrayList accountInfoList) {
		aAccountInfoList = accountInfoList;
	}

	/**
	 * @return
	 */
	public ArrayList getAccountInfoNameList() {
		return aAccountInfoNameList;
	}

	/**
	 * @param accountInfoNameList
	 */
	public void setAccountInfoNameList(ArrayList accountInfoNameList) {
		aAccountInfoNameList = accountInfoNameList;
	}
	/**
	 * @return
	 */
	public ArrayList getExcelList() {
		return aExcelList;
	}

	/**
	 * @param excelList
	 */
	public void setExcelList(ArrayList excelList) {
		aExcelList = excelList;
	}

	/**
	 * @return
	 */
	public String getFile() {
		return sFile;
	}

	/**
	 * @param file
	 */
	public void setFile(String file) {
		sFile = file;
	}
	// NEW Added By Sania
	/**
	 * @return
	 */
	public String getNodeId() {
		return sNodeId;
	}

	/**
	 * @param nodeId
	 */
	public void setNodeId(String nodeId) {
		sNodeId = nodeId;
	}

	/**
	 * @return
	 */
	public String getNodeName() {
		return sNodeName;
	}

	/**
	 * @param nodeName
	 */
	public void setNodeName(String nodeName) {
		sNodeName = nodeName;
	}

	/**
	 * @return
	 */
	public String getParentId() {
		return sParentId;
	}

	/**
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		sParentId = parentId;
	}

	/**
	 * @return
	 */
	public String getUrl() {
		return sUrl;
	}

	/**
	 * @param url
	 */
	public void setUrl(String url) {
		sUrl = url;
	}
	
	/**
	 * @return
	 */
	public static ArrayList getMyBankMenu(String sUserID,String sSessionID){
		LoanAgainstDepositStatusDAO oTransactionDAO = new LoanAgainstDepositStatusDAO();
		ArrayList aList = oTransactionDAO.getMyBankMenu(sUserID, sSessionID);
		return aList;
	}
}
