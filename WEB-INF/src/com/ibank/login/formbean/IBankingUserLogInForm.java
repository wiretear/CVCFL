/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Sign In
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.login.formbean;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

public class IBankingUserLogInForm extends ValidatorForm{
	private String sUserID = "" ;
	private String sNewUserID = "" ;
	private String sUserTitle = "";
	private String sPassword = "" ;
	private String sLastLogIn = "" ;
	private String sPresentDate = "" ;
	private String sSessionID = "" ;
	private String sNodeId;
	private String sParentId;
	private String sNodeName="";
	private String sUrl="";
	private String sCompanyName = "";
	private String sCompanyCode = "";	
	private ArrayList aCompanyCodeList = new ArrayList();
	private ArrayList aCompanyCodeNameList = new ArrayList();
	private String sBranchCode = "";
	private ArrayList aBranchCodeList = new ArrayList();
	private ArrayList aBranchCodeNameList = new ArrayList();
	private String sErrorCode = "" ;
	private String sErrorMessage = "" ;
	private ArrayList aList = new ArrayList();
	private String sBranchOpenDateDDF = "";
	private String sBranchOpenDateSPF = "";
	private String sCurrentDateDDF = "";
	private String sCurrentDateSPF = "";
	private String sMyBankPassword = "";
	private String sLogInToDay = "";
	private String sLogInTotal = "";
	private String sBranchNameHeader = "";
	private String sBranchDateHeader = "";
	private String sPhysicalAddress = "";
	private String sIPAddressServer = "";
	private String sIPAddressClient = "";
	private String sHeaderName = "";
	private String sHeaderLogIn = "";
	private String sHelpMessage = "";
	private ArrayList aHelpList = new ArrayList();
	private String sMenu = "";
	private ArrayList aMenuList = new ArrayList();
	private ArrayList aMenuNameList = new ArrayList();
	private String sImageWritePath = "";
	private String sImageReadPath = "";
	private String sImageFileName = "";
	private String sScrollerInfo = "";
	private ArrayList aScrollerInfoList = new ArrayList();
	private String sStreet = "";
	private String sCity = "";
	private String sCountry = "";
	private String sContinent = "";
	private String sRegion = "";
	private String sAreaCode = "";
	private String sDmaCode = "";
	private String sLatitude = "";
	private String sLongitude = "";
	private String sTimeZone = "";
	private String sNameISP = "";
	private String sGeoFlag = "";
	private String sGeoActionFlag = "";
	private String sBranchName = "";
	private String sBranchAddress = "";
	private String sBranchCityName = "";
	private String sBranchCity = "";
	private ArrayList aBranchCityList = new ArrayList();
	private ArrayList aBranchCityNameList = new ArrayList();
	private String sBranchZipCode = "";
	private String sBranchContactNo = "";
	private String sBranchFaxNo = "";
	private String sBranchSwift = "";
	private String sBranchEmailAddress = "";
	private String sColumnTitle = "";
	private String sColumnValue = "";
	private ArrayList aColumnList = new ArrayList();
	private String sTellerID = "" ;
	private String sCapchaCode = "";
	private String sCapchaText = "";
	
	
	
	
	/**new added for Internet Banking**/
	
	private String sUserName = "";
	private String sUserEMail = "";
	private String sUserContact = "";
	private String sUserAddress = "";
	private String sUserNationalID = "";
	
	private String sUserAccountNo = "";
	private String sUserAccountTitle = "";
	private String sUserCurrentBalance = "";
	private String sUserCurrency = "";
	private String sUserCompanyCode = "";
	private ArrayList aUserAccountList = new ArrayList();
	private String sDisplayFrequency = "";
	private String sDisplayInTotalRecord = "";
	private String sDisplayOutFirstRecord = "";
	private String sDisplayOutLastRecord = "";
	private String sDisplayOutMessage = "";
	
	private String sOneTimePassword = "";
	private String sStatusflag = "";
	private String sQuickLinkName = "";
	private String sQuickLinkUrl = "";
	private ArrayList aQuickLinkList = new ArrayList();
	
	
	/*For Account statement*/

	private String sAccountNumber = "";
	private String sFromDate = "";
	private String sToDate = "";
	private String sCredit = "";
	private String sDebit = "";
	private String sDocnum = "";
	private String sValueDate = "";
	private String sBalance = "";
	private String sRemarks = "";
	private ArrayList aStatementList =new ArrayList();
	private String sSerialNo="";
	private String sCustomerCode = "";
	private String sLoginFlag="";
	private String sPasswordOld = "";
	private String sPasswordNew = "";
	private String sPasswordConf = "";
	private String sPasswordExpiredDays = "";
	
	public String getPasswordOld() {
		return sPasswordOld;
	}
	/**
	 * @param passwordOld
	 */
	public void setPasswordOld(String passwordOld) {
		sPasswordOld = passwordOld;
	}
	/**
	 * @return
	 */
	public String getPasswordNew() {
		return sPasswordNew;
	}
	/**
	 * @param passwordNew
	 */
	public void setPasswordNew(String passwordNew) {
		sPasswordNew = passwordNew;
	}
	/**
	 * @return
	 */
	public String getPasswordConf() {
		return sPasswordConf;
	}
	/**
	 * @param sPasswordConf
	 */
	public void setPasswordConf(String passwordConf) {
		sPasswordConf = passwordConf;
	}
	/**
	 * @return
	 */
	public String getPasswordExpiredDays() {
		return sPasswordExpiredDays;
	}

	/**
	 * @param passwordExpiredDays
	 */
	public void setPasswordExpiredDays(String passwordExpiredDays) {
		sPasswordExpiredDays = passwordExpiredDays;
	}
	
	public String getLoginFlag() {
		return sLoginFlag;
	}
	public void setLoginFlag(String loginFlag) {
		this.sLoginFlag = loginFlag;
	}
	
	public String getCustomerCode() {
		return sCustomerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.sCustomerCode = customerCode;
	}
	public String getSerialNo() {
		return sSerialNo;
	}
	public void setSerialNo(String serialNo) {
		this.sSerialNo = serialNo;
	}	
	public ArrayList getStatementList() {
		return aStatementList;
	}
	public void setStatementList(ArrayList statementList) {
		this.aStatementList = statementList;
	}	
	public String getCredit() {
		return sCredit;
	}
	public void setsCredit(String credit) {
		this.sCredit = credit;
	}
	public String getDebit() {
		return sDebit;
	}
	public void setsDebit(String debit) {
		this.sDebit = debit;
	}
	public String getDocnum() {
		return sDocnum;
	}
	public void setDocnum(String docnum) {
		this.sDocnum = docnum;
	}
	public String getValueDate() {
		return sValueDate;
	}
	public void setValueDate(String valueDate) {
		this.sValueDate = valueDate;
	}
	public String getBalance() {
		return sBalance;
	}
	public void setBalance(String balance) {
		this.sBalance = balance;
	}
	public String getRemarks() {
		return sRemarks;
	}
	public void setRemarks(String remarks) {
		this.sRemarks = remarks;
	}
	
 
	
	public String getAccountNumber() {
		return sAccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.sAccountNumber = accountNumber;
	}
	public String getFromDate() {
		return sFromDate;
	}
	public void setFromDate(String fromDate) {
		this.sFromDate = fromDate;
	}
	public String getToDate() {
		return sToDate;
	}
	public void setToDate(String toDate) {
		this.sToDate = toDate;
	}
	/**
	* * @return
	*/
	public IBankingUserLogInForm(){
	}
	/**
	 * @return
	 */
	public ArrayList getBranchCodeList() {
		return aBranchCodeList;
	}

	/**
	 * @param branchCodeList
	 */
	public void setBranchCodeList(ArrayList branchCodeList) {
		aBranchCodeList = branchCodeList;
	}

	/**
	 * @return
	 */
	public ArrayList getBranchCodeNameList() {
		return aBranchCodeNameList;
	}

	/**
	 * @param branchCodeNameList
	 */
	public void setBranchCodeNameList(ArrayList branchCodeNameList) {
		aBranchCodeNameList = branchCodeNameList;
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
	public String getBranchCode() {
		return sBranchCode;
	}

	/**
	 * @param branchCode
	 */
	public void setBranchCode(String branchCode) {
		sBranchCode = branchCode;
	}

	/**
	 * @return
	 */
	public String getCompanyName() {
		return sCompanyName;
	}

	/**
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		sCompanyName = companyName;
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
	public String getLastLogIn() {
		return sLastLogIn;
	}

	/**
	 * @param lastLogIn
	 */
	public void setLastLogIn(String lastLogIn) {
		sLastLogIn = lastLogIn;
	}

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
	public String getPassword() {
		return sPassword;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		sPassword = password;
	}

	/**
	 * @return
	 */
	public String getPresentDate() {
		return sPresentDate;
	}

	/**
	 * @param presentDate
	 */
	public void setPresentDate(String presentDate) {
		sPresentDate = presentDate;
	}

	/**
	 * @return
	 */
	public String getSessionID() {
		return sSessionID;
	}

	/**
	 * @param sessionID
	 */
	public void setSessionID(String sessionID) {
		sSessionID = sessionID;
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
	public String getUserID() {
		return sUserID;
	}

	/**
	 * @param userID
	 */
	public void setUserID(String userID) {
		sUserID = userID;
	}

	/**
	 * @return
	 */
	public String getUserTitle() {
		return sUserTitle;
	}

	/**
	 * @param userTitle
	 */
	public void setUserTitle(String userTitle) {
		sUserTitle = userTitle;
	}
	/**
	 * @return
	 */
	public String getNewUserID() {
		return sNewUserID;
	}

	/**
	 * @param newUserID
	 */
	public void setNewUserID(String newUserID) {
		sNewUserID = newUserID;
	}

	/**
	 * @return
	 */
	public String getBranchName() {
		return sBranchName;
	}

	/**
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		sBranchName = branchName;
	}

	/**
	 * @return
	 */
	public String getBranchOpenDateDDF() {
		return sBranchOpenDateDDF;
	}

	/**
	 * @param branchOpenDateDDF
	 */
	public void setBranchOpenDateDDF(String branchOpenDateDDF) {
		sBranchOpenDateDDF = branchOpenDateDDF;
	}

	/**
	 * @return
	 */
	public String getBranchOpenDateSPF() {
		return sBranchOpenDateSPF;
	}

	/**
	 * @param branchOpenDateSPF
	 */
	public void setBranchOpenDateSPF(String branchOpenDateSPF) {
		sBranchOpenDateSPF = branchOpenDateSPF;
	}

	/**
	 * @return
	 */
	public String getCurrentDateDDF() {
		return sCurrentDateDDF;
	}

	/**
	 * @param currentDateDDF
	 */
	public void setCurrentDateDDF(String currentDateDDF) {
		sCurrentDateDDF = currentDateDDF;
	}

	/**
	 * @return
	 */
	public String getCurrentDateSPF() {
		return sCurrentDateSPF;
	}

	/**
	 * @param currentDateSPF
	 */
	public void setCurrentDateSPF(String currentDateSPF) {
		sCurrentDateSPF = currentDateSPF;
	}

	/**
	 * @return
	 */
	public String getMyBankPassword() {
		return sMyBankPassword;
	}

	/**
	 * @param myBankPassword
	 */
	public void setMyBankPassword(String myBankPassword) {
		sMyBankPassword = myBankPassword;
	}

	/**
	 * @return
	 */
	public String getLogInToDay() {
		return sLogInToDay;
	}

	/**
	 * @param logInToDay
	 */
	public void setLogInToDay(String logInToDay) {
		sLogInToDay = logInToDay;
	}

	/**
	 * @return
	 */
	public String getLogInTotal() {
		return sLogInTotal;
	}

	/**
	 * @param logInTotal
	 */
	public void setLogInTotal(String logInTotal) {
		sLogInTotal = logInTotal;
	}

	/**
	 * @return
	 */
	public String getBranchDateHeader() {
		return sBranchDateHeader;
	}

	/**
	 * @param branchDateHeader
	 */
	public void setBranchDateHeader(String branchDateHeader) {
		sBranchDateHeader = branchDateHeader;
	}

	/**
	 * @return
	 */
	public String getBranchNameHeader() {
		return sBranchNameHeader;
	}

	/**
	 * @param branchNameHeader
	 */
	public void setBranchNameHeader(String branchNameHeader) {
		sBranchNameHeader = branchNameHeader;
	}
	/**
	 * @return
	 */
	public String getHeaderName() {
		return sHeaderName;
	}

	/**
	 * @param headerName
	 */
	public void setHeaderName(String headerName) {
		sHeaderName = headerName;
	}

	/**
	 * @return
	 */
	public String getHeaderLogIn() {
		return sHeaderLogIn;
	}

	/**
	 * @param headerLogIn
	 */
	public void setHeaderLogIn(String headerLogIn) {
		sHeaderLogIn = headerLogIn;
	}

	/**
	 * @return
	 */
	public ArrayList getHelpList() {
		return aHelpList;
	}

	/**
	 * @param helpList
	 */
	public void setHelpList(ArrayList helpList) {
		aHelpList = helpList;
	}

	/**
	 * @return
	 */
	public String getHelpMessage() {
		return sHelpMessage;
	}

	/**
	 * @param helpMessage
	 */
	public void setHelpMessage(String helpMessage) {
		sHelpMessage = helpMessage;
	}


	/**
	 * @return
	 */
	public String getIPAddressServer() {
		return sIPAddressServer;
	}

	/**
	 * @param addressServer
	 */
	public void setIPAddressServer(String addressServer) {
		sIPAddressServer = addressServer;
	}

	/**
	 * @return
	 */
	public String getIPAddressClient() {
		return sIPAddressClient;
	}

	/**
	 * @param addressClient
	 */
	public void setIPAddressClient(String addressClient) {
		sIPAddressClient = addressClient;
	}

	/**
	 * @return
	 */
	public String getPhysicalAddress() {
		return sPhysicalAddress;
	}

	/**
	 * @param physicalAddress
	 */
	public void setPhysicalAddress(String physicalAddress) {
		sPhysicalAddress = physicalAddress;
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
	public String getImageFileName() {
		return sImageFileName;
	}

	/**
	 * @param imageFileName
	 */
	public void setImageFileName(String imageFileName) {
		sImageFileName = imageFileName;
	}

	/**
	 * @return
	 */
	public String getImageReadPath() {
		return sImageReadPath;
	}

	/**
	 * @param imageReadPath
	 */
	public void setImageReadPath(String imageReadPath) {
		sImageReadPath = imageReadPath;
	}

	/**
	 * @return
	 */
	public String getImageWritePath() {
		return sImageWritePath;
	}

	/**
	 * @param imageWritePath
	 */
	public void setImageWritePath(String imageWritePath) {
		sImageWritePath = imageWritePath;
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
	public ArrayList getCompanyCodeList() {
		return aCompanyCodeList;
	}

	/**
	 * @param companyCodeList
	 */
	public void setCompanyCodeList(ArrayList companyCodeList) {
		aCompanyCodeList = companyCodeList;
	}

	/**
	 * @return
	 */
	public ArrayList getCompanyCodeNameList() {
		return aCompanyCodeNameList;
	}

	/**
	 * @param companyCodeNameList
	 */
	public void setCompanyCodeNameList(ArrayList companyCodeNameList) {
		aCompanyCodeNameList = companyCodeNameList;
	}
	/**
	 * @return
	 */
	public String getScrollerInfo() {
		return sScrollerInfo;
	}

	/**
	 * @param scrollerInfo
	 */
	public void setScrollerInfo(String scrollerInfo) {
		sScrollerInfo = scrollerInfo;
	}

	/**
	 * @return
	 */
	public ArrayList getScrollerInfoList() {
		return aScrollerInfoList;
	}

	/**
	 * @param scrollerInfoList
	 */
	public void setScrollerInfoList(ArrayList scrollerInfoList) {
		aScrollerInfoList = scrollerInfoList;
	}
	/**
	 * @return
	 */
	public String getCity() {
		return sCity;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		sCity = city;
	}

	/**
	 * @return
	 */
	public String getCountry() {
		return sCountry;
	}

	/**
	 * @param country
	 */
	public void setCountry(String country) {
		sCountry = country;
	}

	/**
	 * @return
	 */
	public String getContinent() {
		return sContinent;
	}

	/**
	 * @param continent
	 */
	public void setContinent(String continent) {
		sContinent = continent;
	}
	/**
	 * @return
	 */
	public String getGeoFlag() {
		return sGeoFlag;
	}

	/**
	 * @param geoFlag
	 */
	public void setGeoFlag(String geoFlag) {
		sGeoFlag = geoFlag;
	}

	/**
	 * @return
	 */
	public String getStreet() {
		return sStreet;
	}

	/**
	 * @param street
	 */
	public void setStreet(String street) {
		sStreet = street;
	}

	/**
	 * @return
	 */
	public String getAreaCode() {
		return sAreaCode;
	}

	/**
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		sAreaCode = areaCode;
	}

	/**
	 * @return
	 */
	public String getDmaCode() {
		return sDmaCode;
	}

	/**
	 * @param dmaCode
	 */
	public void setDmaCode(String dmaCode) {
		sDmaCode = dmaCode;
	}

	/**
	 * @return
	 */
	public String getGeoActionFlag() {
		return sGeoActionFlag;
	}

	/**
	 * @param geoActionFlag
	 */
	public void setGeoActionFlag(String geoActionFlag) {
		sGeoActionFlag = geoActionFlag;
	}

	/**
	 * @return
	 */
	public String getLatitude() {
		return sLatitude;
	}

	/**
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		sLatitude = latitude;
	}

	/**
	 * @return
	 */
	public String getLongitude() {
		return sLongitude;
	}

	/**
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		sLongitude = longitude;
	}

	/**
	 * @return
	 */
	public String getNameISP() {
		return sNameISP;
	}

	/**
	 * @param nameISP
	 */
	public void setNameISP(String nameISP) {
		sNameISP = nameISP;
	}

	/**
	 * @return
	 */
	public String getRegion() {
		return sRegion;
	}

	/**
	 * @param region
	 */
	public void setRegion(String region) {
		sRegion = region;
	}

	/**
	 * @return
	 */
	public String getTimeZone() {
		return sTimeZone;
	}

	/**
	 * @param timeZone
	 */
	public void setTimeZone(String timeZone) {
		sTimeZone = timeZone;
	}
	/**
	 * @return
	 */
	public String getBranchAddress() {
		return sBranchAddress;
	}

	/**
	 * @param branchAddress
	 */
	public void setBranchAddress(String branchAddress) {
		sBranchAddress = branchAddress;
	}

	/**
	 * @return
	 */
	public String getBranchCity() {
		return sBranchCity;
	}

	/**
	 * @param branchCity
	 */
	public void setBranchCity(String branchCity) {
		sBranchCity = branchCity;
	}

	/**
	 * @return
	 */
	public String getBranchContactNo() {
		return sBranchContactNo;
	}

	/**
	 * @param branchContactNo
	 */
	public void setBranchContactNo(String branchContactNo) {
		sBranchContactNo = branchContactNo;
	}

	/**
	 * @return
	 */
	public String getBranchEmailAddress() {
		return sBranchEmailAddress;
	}

	/**
	 * @param branchEmailAddress
	 */
	public void setBranchEmailAddress(String branchEmailAddress) {
		sBranchEmailAddress = branchEmailAddress;
	}

	/**
	 * @return
	 */
	public String getBranchFaxNo() {
		return sBranchFaxNo;
	}

	/**
	 * @param branchFaxNo
	 */
	public void setBranchFaxNo(String branchFaxNo) {
		sBranchFaxNo = branchFaxNo;
	}

	/**
	 * @return
	 */
	public String getBranchSwift() {
		return sBranchSwift;
	}

	/**
	 * @param branchSwift
	 */
	public void setBranchSwift(String branchSwift) {
		sBranchSwift = branchSwift;
	}

	/**
	 * @return
	 */
	public String getBranchZipCode() {
		return sBranchZipCode;
	}

	/**
	 * @param branchZipCode
	 */
	public void setBranchZipCode(String branchZipCode) {
		sBranchZipCode = branchZipCode;
	}
	/**
	 * @return
	 */
	public ArrayList getBranchCityList() {
		return aBranchCityList;
	}

	/**
	 * @param branchCityList
	 */
	public void setBranchCityList(ArrayList branchCityList) {
		aBranchCityList = branchCityList;
	}

	/**
	 * @return
	 */
	public ArrayList getBranchCityNameList() {
		return aBranchCityNameList;
	}

	/**
	 * @param branchCityNameList
	 */
	public void setBranchCityNameList(ArrayList branchCityNameList) {
		aBranchCityNameList = branchCityNameList;
	}
	/**
	 * @return
	 */
	public String getBranchCityName() {
		return sBranchCityName;
	}

	/**
	 * @param branchCityName
	 */
	public void setBranchCityName(String branchCityName) {
		sBranchCityName = branchCityName;
	}
	/**
	 * @return
	 */
	public String getColumnTitle() {
		return sColumnTitle;
	}

	/**
	 * @param columnTitle
	 */
	public void setColumnTitle(String columnTitle) {
		sColumnTitle = columnTitle;
	}

	/**
	 * @return
	 */
	public String getColumnValue() {
		return sColumnValue;
	}

	/**
	 * @param columnValue
	 */
	public void setColumnValue(String columnValue) {
		sColumnValue = columnValue;
	}
	/**
	 * @return
	 */
	public ArrayList getColumnList() {
		return aColumnList;
	}

	/**
	 * @param columnList
	 */
	public void setColumnList(ArrayList columnList) {
		aColumnList = columnList;
	}
	/**
	 * @return
	 */
	public String getTellerID() {
		return sTellerID;
	}

	/**
	 * @param tellerID
	 */
	public void setTellerID(String tellerID) {
		sTellerID = tellerID;
	}
	/**
	 * @return
	 */
	public String getCapchaCode() {
		return sCapchaCode;
	}

	/**
	 * @param capchaCode
	 */
	public void setCapchaCode(String capchaCode) {
		sCapchaCode = capchaCode;
	}

	/**
	 * @return
	 */
	public String getUserAddress() {
		return sUserAddress;
	}

	/**
	 * @param userAddress
	 */
	public void setUserAddress(String userAddress) {
		sUserAddress = userAddress;
	}

	/**
	 * @return
	 */
	public String getUserContact() {
		return sUserContact;
	}

	/**
	 * @param userContact
	 */
	public void setUserContact(String userContact) {
		sUserContact = userContact;
	}

	/**
	 * @return
	 */
	public String getUserEMail() {
		return sUserEMail;
	}

	/**
	 * @param userEMail
	 */
	public void setUserEMail(String userEMail) {
		sUserEMail = userEMail;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return sUserName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		sUserName = userName;
	}
	
	/**
	 * @return
	 */
	public String getUserAccountNo() {
		return sUserAccountNo;
	}

	/**
	 * @param userAccountNo
	 */
	public void setUserAccountNo(String userAccountNo) {
		sUserAccountNo = userAccountNo;
	}

	/**
	 * @return
	 */
	public String getUserAccountTitle() {
		return sUserAccountTitle;
	}

	/**
	 * @param userAccountTitle
	 */
	public void setUserAccountTitle(String userAccountTitle) {
		sUserAccountTitle = userAccountTitle;
	}

	/**
	 * @return
	 */
	public String getUserCurrentBalance() {
		return sUserCurrentBalance;
	}

	/**
	 * @param userCurrentBalance
	 */
	public void setUserCurrentBalance(String userCurrentBalance) {
		sUserCurrentBalance = userCurrentBalance;
	}

	/**
	 * @return
	 */
	public ArrayList getUserAccountList() {
		return aUserAccountList;
	}

	/**
	 * @param userAccountList
	 */
	public void setUserAccountList(ArrayList userAccountList) {
		aUserAccountList = userAccountList;
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
	public String getDisplayInTotalRecord() {
		return sDisplayInTotalRecord;
	}

	/**
	 * @param displayInTotalRecord
	 */
	public void setDisplayInTotalRecord(String displayInTotalRecord) {
		sDisplayInTotalRecord = displayInTotalRecord;
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
	public String getUserCurrency() {
		return sUserCurrency;
	}

	/**
	 * @param userCurrency
	 */
	public void setUserCurrency(String userCurrency) {
		sUserCurrency = userCurrency;
	}

	/**
	 * @return
	 */
	public String getUserCompanyCode() {
		return sUserCompanyCode;
	}

	/**
	 * @param userCompanyCode
	 */
	public void setUserCompanyCode(String userCompanyCode) {
		sUserCompanyCode = userCompanyCode;
	}

	/**
	 * @return
	 */
	public String getUserNationalID() {
		return sUserNationalID;
	}

	/**
	 * @param userNationalID
	 */
	public void setUserNationalID(String userNationalID) {
		sUserNationalID = userNationalID;
	}

	/**
	 * @return
	 */
	public String getCapchaText() {
		return sCapchaText;
	}

	/**
	 * @param capchaText
	 */
	public void setCapchaText(String capchaText) {
		sCapchaText = capchaText;
	}
	/**
	 * @return
	 */
	public String getOneTimePassword() {
		return sOneTimePassword;
	}

	/**
	 * @param oneTimePassword
	 */
	public void setOneTimePassword(String oneTimePassword) {
		sOneTimePassword = oneTimePassword;
	}
	/**
	 * @return
	 */
	public String getStatusflag() {
		return sStatusflag;
	}

	/**
	 * @param statusflag
	 */
	public void setStatusflag(String statusflag) {
		sStatusflag = statusflag;
	}	

	/**
	 * @return
	 */
	public String getQuickLinkName() {
		return sQuickLinkName;
	}

	/**
	 * @param quickLinkName
	 */
	public void setQuickLinkName(String quickLinkName) {
		sQuickLinkName = quickLinkName;
	}	

	/**
	 * @return
	 */
	public String getQuickLinkUrl() {
		return sQuickLinkUrl;
	}

	/**
	 * @param quickLinkUrl
	 */
	public void setQuickLinkUrl(String quickLinkUrl) {
		sQuickLinkUrl = quickLinkUrl;
	}	
	/**
	 * @return
	 */
	public ArrayList getQuickLinkList() {
		return aQuickLinkList;
	}

	/**
	 * @param quickLinkList
	 */
	public void setQuickLinkList(ArrayList quickLinkList) {
		aQuickLinkList = quickLinkList;
	}	

}