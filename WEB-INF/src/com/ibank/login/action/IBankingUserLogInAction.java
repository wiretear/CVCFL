/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Sign In
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.login.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.ibank.login.bo.IBankingUserLogInBO;
import com.ibank.login.dao.IBankingUserLogInDAO;
import com.ibank.login.dao.MyStringRandomGen;
import com.ibank.login.formbean.IBankingUserLogInForm;
import com.ibank.utility.FindGeoLocation;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

import net.sf.json.JSONObject;

public class IBankingUserLogInAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		IBankingUserLogInDAO oIBankingUserLogInDAO = new IBankingUserLogInDAO();
		IBankingUserLogInBO oIBankingUserLogInBO = new IBankingUserLogInBO();
		IBankingUserLogInForm oIBankingUserLogInForm = (IBankingUserLogInForm) form;
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oIBankingUserLogInForm);
		IBankingUserLogInBO oIBankingUserLogInMessageBO = new IBankingUserLogInBO();
		HttpSession session = request.getSession(true);
		String sActionPath = "";
		sActionPath = mapping.getPath();
		String sSuccessPasswordChange = "changeMyPassword";
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sSessionExpireAction = "sessionExpire";
		String sFatalErrorAction = "fatalError";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSecretQuestion = "secretQuestion";
		String sMyPINExpire = "myPINExpire";
		String sSuccess = sFatalErrorAction;
		String gsUserID = (String) session.getAttribute("GSUserID");
		String gsUserTitle = (String) session.getAttribute("GSUserTitle");
		String gsLastLogInDate = (String) session.getAttribute("GSLastLogInDate");
		String gsPresentDate = (String) session.getAttribute("GSPresentDate");
		String gsSessionID = (String) session.getAttribute("GSSessionID");
		String gsInternalCardID = (String) session.getAttribute("GSInternalCardID");
		String gsLogInToDay = (String) session.getAttribute("GSLogInToDay");
		String gsLogInTotal = (String) session.getAttribute("GSLogInTotal");
		String gsBranchNameHeader = (String) session.getAttribute("GSBranchNameHeader");
		String gsBranchDateHeader = (String) session.getAttribute("GSBranchDateHeader");
		String gsTellerID = (String) session.getAttribute("GSTellerID");
		String gsCompanyCode = (String) session.getAttribute("GSCompanyCode");
		String gsBranchCode = (String) session.getAttribute("GSBranchCode");
		String gsHeaderName = (String) session.getAttribute("GSHeaderName");
		String gsHeaderLogIn = (String) session.getAttribute("GSHeaderLogIn");
		String sActionClientPath = "/mybank/logInMyBank.do";
		String sActionLogOutPath = "/mybank/logOutMyBankMenu.do";
		String sActionBranchPath = "";
		String gsMenuClientPath = (String) session.getAttribute("GSMenuClientPath");
		String gsMenuBranchPath = (String) session.getAttribute("GSMenuBranchPath");
		String gsGeoFlag = (String) session.getAttribute("GSGeoFlag");
		gsInternalCardID = "0";
		String sPhysicalAddress = "";
		String sServerOS = "SERVER";
		String sClientOS = "CLIENT";
		String sPhysicalAddressServer = "";
		String sPhysicalAddressClient = "";
		String sIPAddressServer = "";
		String sIPAddressClient = "";
		/*
		 * String gsDigit1 = (String) session.getAttribute("GSDigit1"); String gsDigit2
		 * = (String) session.getAttribute("GSDigit2"); String gsDigit3 = (String)
		 * session.getAttribute("GSDigit3"); String gsDigit4 = (String)
		 * session.getAttribute("GSDigit4"); String gsDigit5 = (String)
		 * session.getAttribute("GSDigit5"); String gsDigit6 = (String)
		 * session.getAttribute("GSDigit6"); String gsDigit7 = (String)
		 * session.getAttribute("GSDigit7");
		 */

		if (sActionPath.equals("/ibankingUserLogIn")) {
			session.setAttribute("oIBankingUserLogInBO", null);
			session.setAttribute("oIBankingUserLogInMessageBO", null);
			session.setAttribute("oCaptchaMessage", " ");
			session.setAttribute("GSUserID", "");
			session.setAttribute("GSUserTitle", "");
			session.setAttribute("GSSessionID", "");
			session.setAttribute("GSLastLogInDate", "");
			session.setAttribute("GSPresentDate", "");
			session.setAttribute("GSInternalCardID", "");
			session.setAttribute("GSLogInToDay", "");
			session.setAttribute("GSLogInTotal", "");
			session.setAttribute("GSBranchNameHeader", "");
			session.setAttribute("GSBranchDateHeader", "");
			session.setAttribute("GSTellerID", "");
			session.setAttribute("GSCompanyhCode", "");
			session.setAttribute("GSCompanyName", "");
			session.setAttribute("GSBranchCode", "");
			session.setAttribute("GSBranchName", "");
			session.setAttribute("GSBranchOpenDateDDFormat", "");
			session.setAttribute("GSBranchOpenDateSPFormat", "");
			session.setAttribute("GSCurrentDateDDFormat", "");
			session.setAttribute("GSCurrentDateSPFormat", "");
			session.setAttribute("GSCompanyCode", "");
			session.setAttribute("GSCompanyName", "");
			session.setAttribute("GSMenuClientPath", "");
			session.setAttribute("GSMenuBranchPath", "");
			session.setAttribute("GSImageWritePath", "");
			session.setAttribute("GSImageReadPath", "");
			session.setAttribute("GSImageFileName", "");
		//	geoLocationInformation(request.getRemoteAddr(), session, gsGeoFlag);
			// generateCaptchaCode(oIBankingUserLogInForm, session);

			// generate Captcha code
			MyStringRandomGen msr = new MyStringRandomGen();
			String a = msr.generateRandomString();
			String b = msr.generateRandomString();
			String c = msr.generateRandomString();
			String d = msr.generateRandomString();

			String code = (a + ' ' + b + ' ' + ' ' + c + ' ' + d);

			oIBankingUserLogInForm.setCapchaCode(code);
			session.setAttribute("GSDigit1", a + ' ');
			session.setAttribute("GSDigit2", b + ' ');
			session.setAttribute("GSDigit3", c + ' ');
			session.setAttribute("GSDigit4", d + ' ');
			// session.setAttribute("GSDigit7", g + ' ');
			// generate Captcha code
			sSuccess = sSuccessAction;
		} else if (sActionPath.equals("/userSignUp")) {

			sSuccess = sSuccessAction;
		} else if (sActionPath.equals("/reloadCaptcha")) {

			session.setAttribute("oIBankingUserLogInBO", null);
			session.setAttribute("oIBankingUserLogInMessageBO", null);
			session.setAttribute("oCaptchaMessage", " ");
			session.setAttribute("GSUserID", "");
			session.setAttribute("GSUserTitle", "");
			session.setAttribute("GSSessionID", "");
			session.setAttribute("GSLastLogInDate", "");
			session.setAttribute("GSPresentDate", "");
			session.setAttribute("GSInternalCardID", "");
			session.setAttribute("GSLogInToDay", "");
			session.setAttribute("GSLogInTotal", "");
			session.setAttribute("GSBranchNameHeader", "");
			session.setAttribute("GSBranchDateHeader", "");
			session.setAttribute("GSTellerID", "");
			session.setAttribute("GSCompanyhCode", "");
			session.setAttribute("GSCompanyName", "");
			session.setAttribute("GSBranchCode", "");
			session.setAttribute("GSBranchName", "");
			session.setAttribute("GSBranchOpenDateDDFormat", "");
			session.setAttribute("GSBranchOpenDateSPFormat", "");
			session.setAttribute("GSCurrentDateDDFormat", "");
			session.setAttribute("GSCurrentDateSPFormat", "");
			session.setAttribute("GSCompanyCode", "");
			session.setAttribute("GSCompanyName", "");
			session.setAttribute("GSMenuClientPath", "");
			session.setAttribute("GSMenuBranchPath", "");
			session.setAttribute("GSImageWritePath", "");
			session.setAttribute("GSImageReadPath", "");
			session.setAttribute("GSImageFileName", "");
			oIBankingUserLogInForm.setCapchaCode("");
			oIBankingUserLogInForm.setCapchaText("");
			// generate Captcha code
			JSONObject oJSONObject = new JSONObject();
			MyStringRandomGen msr = new MyStringRandomGen();
			String a = msr.generateRandomString();
			String b = msr.generateRandomString();
			String c = msr.generateRandomString();
			String d = msr.generateRandomString();

			// String code =(a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' ' + f);

			// oIBankingUserLogInForm.setCapchaCode(code);
			/*
			 * session.setAttribute("GSDigit1", a); session.setAttribute("GSDigit2", b);
			 * session.setAttribute("GSDigit3", c); session.setAttribute("GSDigit4",d);
			 * session.setAttribute("GSDigit5",e); session.setAttribute("GSDigit6", f);
			 */

			oJSONObject.put("GSDigit1", a + ' ');
			oJSONObject.put("GSDigit2", b + ' ');
			oJSONObject.put("GSDigit3", c + ' ');
			oJSONObject.put("GSDigit4", d + ' ');
			oJSONObject.put("CaptchaCode", a + b + c + d);

			response.getWriter().print(oJSONObject);

			// generate Captcha code
			// sSuccess = sSuccessAction;
			sSuccess = null;
		} else if (sActionPath.equals("/ibankingUserLogInSubmit")) {
			String sNewUserID;
			String sNewSessionID;
			session.setAttribute("GSUserID", "");
			session.setAttribute("GSUserTitle", "");
			session.setAttribute("GSSessionID", "");
			session.setAttribute("GSLastLogInDate", "");
			session.setAttribute("GSPresentDate", "");
			session.setAttribute("GSInternalCardID", "");
			session.setAttribute("GSLogInToDay", "");
			session.setAttribute("GSLogInTotal", "");
			session.setAttribute("GSBranchNameHeader", "");
			session.setAttribute("GSBranchDateHeader", "");
			session.setAttribute("GSTellerID", "");
			session.setAttribute("GSCompanyhCode", "");
			session.setAttribute("GSCompanyName", "");
			session.setAttribute("GSBranchCode", "");
			session.setAttribute("GSBranchName", "");
			session.setAttribute("GSBranchOpenDateDDFormat", "");
			session.setAttribute("GSBranchOpenDateSPFormat", "");
			session.setAttribute("GSCurrentDateDDFormat", "");
			session.setAttribute("GSCurrentDateSPFormat", "");
			session.setAttribute("GSCompanyCode", "");
			session.setAttribute("GSCompanyName", "");
			session.setAttribute("GSMenuClientPath", "");
			session.setAttribute("GSMenuBranchPath", "");
			session.setAttribute("oIBankingUserLogInBO", null);
			session.setAttribute("oIBankingUserLogInMessageBO", null);
			session.setAttribute("GSImageWritePath", "");
			session.setAttribute("GSImageReadPath", "");
			session.setAttribute("GSImageFileName", "");
			session.setAttribute("oCaptchaMessage", " ");
			session.setAttribute("GSCustomerCode", null);
			String sCaptchaText = "";
			String sCaptchaCode = "";
			String CaptchaCodeVal = "";
			String sLoginFlag = "";
			oIBankingUserLogInForm.setErrorCode("");
			oIBankingUserLogInForm.setErrorMessage("");

			sCaptchaText = oIBankingUserLogInForm.getCapchaText().replaceAll(" ", "");
			sCaptchaCode = oIBankingUserLogInForm.getCapchaCode();
			CaptchaCodeVal = sCaptchaCode.replaceAll(" ", "");

			if (CaptchaCodeVal.equals(sCaptchaText)) {
				oIBankingUserLogInBO = oIBankingUserLogInDAO.getPermissionMailIDCheckPro(gsInternalCardID,
						oIBankingUserLogInForm.getUserID(), oIBankingUserLogInForm.getPassword(),
						sPhysicalAddressServer, sPhysicalAddressClient, request.getRemoteAddr());

				sNewUserID = oIBankingUserLogInBO.getNewUserID();
				sNewSessionID = oIBankingUserLogInBO.getSessionID();
				oIBankingUserLogInForm.setErrorCode(oIBankingUserLogInBO.getErrorCode());
				sLoginFlag = oIBankingUserLogInBO.getLoginFlag();
			//	System.out.print("LoginFlag() ==>>>>>" +sLoginFlag);

			//	System.out.print("oIBankingUserLogInBO.getErrorCode ==>>>>>" + oIBankingUserLogInBO.getErrorCode());
			//	System.out.print("oIBankingUserLogInBO.getErrorMessge ==>>>>>" + oIBankingUserLogInBO.getErrorMessage());
				if (oIBankingUserLogInBO.getErrorCode().equals("0")) {	

				
					// System.out.print("getCustomerCode ==>>>>>" +oIBankingUserLogInBO.getCustomerCode());
					session.setAttribute("GSUserID", sNewUserID);
					session.setAttribute("GSSessionID", sNewSessionID);
					session.setAttribute("GSCustomerCode", oIBankingUserLogInBO.getCustomerCode());
					session.setAttribute("GSLastLogInDate", oIBankingUserLogInForm.getLastLogIn());
					session.setAttribute("GSPresentDate", oIBankingUserLogInForm.getPresentDate());
					session.setAttribute("GSInternalCardID", gsInternalCardID);
					session.setAttribute("GSUserTitle", oIBankingUserLogInForm.getUserTitle());
					session.setAttribute("GSHeaderName", oIBankingUserLogInForm.getHeaderName());
					session.setAttribute("GSHeaderLogIn", oIBankingUserLogInForm.getHeaderLogIn());
					session.setAttribute("GSBranchNameHeader", "");
					session.setAttribute("GSBranchDateHeader", "");
					session.setAttribute("GSLogInToDay", oIBankingUserLogInForm.getLogInToDay());
					session.setAttribute("GSLogInTotal", oIBankingUserLogInForm.getLogInTotal());

					// getServlet().getServletContext();
					// request.getContextPath();
					// Show Image from Database Start
					/*IBankingUserLogInBO oLogInImageBO = new IBankingUserLogInBO();
					oLogInImageBO = oIBankingUserLogInDAO.getUserImage(sNewUserID, sNewSessionID);
					populateImage(oIBankingUserLogInForm, oLogInImageBO);
					session.setAttribute("oLogInImageBO", oLogInImageBO);*/
					// Show Image from Database End

					/*
					 * IBankingUserLogInBO oLogInUserBO = new IBankingUserLogInBO(); oLogInUserBO
					 * =oIBankingUserLogInDAO.getBalanceInfoData(sNewUserID,sNewSessionID);
					 * populateBalanceInfo(oIBankingUserLogInForm, oLogInUserBO);
					 * session.setAttribute("oLogInUserBO", oLogInUserBO); oIBankingUserLogInBO*/
					oIBankingUserLogInBO = oIBankingUserLogInDAO.getCustomerProfileInfoPro(sNewUserID,sNewSessionID);
				//	 System.out.print("oIBankingUserLogInBO.getUserName ==>>>>>" +oIBankingUserLogInBO.getErrorCode());
					 
			if (oIBankingUserLogInBO.getErrorCode().equals("0")) {
				//	populateCustomerInfoList(oIBankingUserLogInForm, oIBankingUserLogInBO);
					session.setAttribute("GSUserName", oIBankingUserLogInBO.getUserName());
					session.setAttribute("GSUserAddress", oIBankingUserLogInBO.getUserAddress());
					session.setAttribute("GSUserContact", oIBankingUserLogInBO.getUserContact());
					session.setAttribute("GSUserEMail", oIBankingUserLogInBO.getUserEMail());
					session.setAttribute("GSUserNationalID", oIBankingUserLogInBO.getUserNationalID());
	//	System.out.print("LoginFlag() ==>>>>>" +oIBankingUserLogInBO.getLoginFlag());		
				if(sLoginFlag.equals("Y")){
			
					sSuccess = sSuccessAction;
					
					}else if(sLoginFlag.equals("N")){
					
						session.setAttribute("GSUserID", sNewUserID);
						session.setAttribute("GSTellerID", sNewUserID);
						session.setAttribute("GSSessionID", sNewSessionID);
						session.setAttribute("GSCompanyName", oIBankingUserLogInBO.getCompanyName());				
						session.setAttribute("GSBranchOpenDateDDFormat", oIBankingUserLogInBO.getBranchOpenDateDDF());
						session.setAttribute("GSCompanyCode", oIBankingUserLogInBO.getCompanyCode());
						session.setAttribute("GSBranchCode", oIBankingUserLogInBO.getBranchCode());				
						
					sSuccess = sSuccessPasswordChange;
					}
				} else if (oIBankingUserLogInBO.getErrorCode().equals("1")) {
				//	oIBankingUserLogInBO = oIBankingUserLogInDAO.getGlobalClientLastLogIn(sNewUserID, sNewSessionID);
					oIBankingUserLogInForm.setErrorCode("1");
					oIBankingUserLogInForm.setErrorMessage(oIBankingUserLogInBO.getErrorMessage());
							
					sSuccess = sFailureAction;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("2")) {
					oIBankingUserLogInMessageBO = oIBankingUserLogInDAO.getMessageInformation(gsUserID, gsSessionID);
					populateMessage(oIBankingUserLogInForm, oIBankingUserLogInBO);
					session.setAttribute("oIBankingUserLogInMessageBO", oIBankingUserLogInMessageBO);
					session.setAttribute("GSUserID", sNewUserID);
					session.setAttribute("GSSessionID", sNewSessionID);
			//		oIBankingUserLogInBO = oIBankingUserLogInDAO.getGlobalClient(sNewUserID, sNewSessionID);
					populateSessionClient(oIBankingUserLogInForm, oIBankingUserLogInBO);
					session.setAttribute("GSHeaderName", oIBankingUserLogInForm.getHeaderName());
					session.setAttribute("GSUserTitle", oIBankingUserLogInForm.getUserTitle());
					session.setAttribute("GSHeaderLogIn", oIBankingUserLogInForm.getHeaderLogIn());
					session.setAttribute("GSLastLogInDate", oIBankingUserLogInForm.getLastLogIn());
					sSuccess = sMyPINExpire;
				} else {
					session.setAttribute("oIBankingUserLogInBO", null);
					session.setAttribute("oIBankingUserLogInMessageBO", null);
					session.setAttribute("oCaptchaMessage", " ");
					sSuccess = sFatalErrorAction;
				}
			}else if (oIBankingUserLogInBO.getErrorCode().equals("1")) {
					// generate Captcha code
					MyStringRandomGen msr = new MyStringRandomGen();
					String a = msr.generateRandomString();
					String b = msr.generateRandomString();
					String c = msr.generateRandomString();
					String d = msr.generateRandomString();
					String code = (a + ' ' + b + ' ' + ' ' + c + ' ' + d);
					oIBankingUserLogInForm.setCapchaCode(code);

					session.setAttribute("GSDigit1", a + ' ');
					session.setAttribute("GSDigit2", b + ' ');
					session.setAttribute("GSDigit3", c + ' ');
					session.setAttribute("GSDigit4", d + ' ');

					// generate Captcha code
					// generateCaptchaCode(oIBankingUserLogInForm, session);
					oIBankingUserLogInForm.setErrorCode("1");
					oIBankingUserLogInForm.setErrorMessage(oIBankingUserLogInBO.getErrorMessage());
					sSuccess = sFailureAction;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("10")) {
				//	oIBankingUserLogInBO = oIBankingUserLogInDAO.getGlobalClientLastLogIn(sNewUserID, sNewSessionID);
					populateSessionLastLogIn(oIBankingUserLogInForm, oIBankingUserLogInBO);
					session.setAttribute("GSHeaderLogIn", oIBankingUserLogInForm.getHeaderLogIn());
					session.setAttribute("GSLastLogInDate", oIBankingUserLogInForm.getLastLogIn());

					// Show Image from Database Start
					IBankingUserLogInBO oLogInImageBO = new IBankingUserLogInBO();
					oLogInImageBO = oIBankingUserLogInDAO.getUserImage(sNewUserID, sNewSessionID);
					populateImage(oIBankingUserLogInForm, oLogInImageBO);
					session.setAttribute("oLogInImageBO", oLogInImageBO);

			
					session.setAttribute("GSUserID", sNewUserID);
					session.setAttribute("GSSessionID", sNewSessionID);
					sSuccess = sSecretQuestion;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("11")) {
					oIBankingUserLogInMessageBO = oIBankingUserLogInDAO.getMessageInformation(gsUserID, gsSessionID);
					populateMessage(oIBankingUserLogInForm, oIBankingUserLogInBO);
					session.setAttribute("oIBankingUserLogInMessageBO", oIBankingUserLogInMessageBO);
					session.setAttribute("GSUserID", sNewUserID);
					session.setAttribute("GSSessionID", sNewSessionID);
			//		oIBankingUserLogInBO = oIBankingUserLogInDAO.getGlobalClient(sNewUserID, sNewSessionID);
					populateSessionClient(oIBankingUserLogInForm, oIBankingUserLogInBO);
					session.setAttribute("GSHeaderName", oIBankingUserLogInForm.getHeaderName());
					session.setAttribute("GSUserTitle", oIBankingUserLogInForm.getUserTitle());
					session.setAttribute("GSHeaderLogIn", oIBankingUserLogInForm.getHeaderLogIn());
					session.setAttribute("GSLastLogInDate", oIBankingUserLogInForm.getLastLogIn());
					sSuccess = sMyPINExpire;
				} else {
					session.setAttribute("oIBankingUserLogInBO", null);
					session.setAttribute("oIBankingUserLogInMessageBO", null);
					session.setAttribute("oCaptchaMessage", " ");
					sSuccess = sFatalErrorAction;
				}
			}
			// check captcha
			else {

				oIBankingUserLogInForm.setCapchaText("");
				String sMessage = "";
				sMessage = "You entered wrong captcha code";
				//session.setAttribute("oCaptchaMessage", sMessage);				
				oIBankingUserLogInForm.setErrorCode("1");
				oIBankingUserLogInForm.setErrorMessage(sMessage);

				MyStringRandomGen msr = new MyStringRandomGen();
				String a = msr.generateRandomString();
				String b = msr.generateRandomString();
				String c = msr.generateRandomString();
				String d = msr.generateRandomString();
				String code = (a + ' ' + b + ' ' + ' ' + c + ' ' + d);

				oIBankingUserLogInForm.setCapchaCode(code);
				session.setAttribute("GSDigit1", a + ' ');
				session.setAttribute("GSDigit2", b + ' ');
				session.setAttribute("GSDigit3", c + ' ');
				session.setAttribute("GSDigit4", d + ' ');
				sSuccess = sFailureAction;
			}
			// check captcha
		} else if (sActionPath.equals("/myPINChange")) {
			clearSession(session);
			clearForm(oIBankingUserLogInForm);
			session.setAttribute("oIBankingUserLogInMessageBO", " ");
			sSuccess = sSuccessAction;
		}		
		else if (sActionPath.equals("/myPINUpdate")){
				session.setAttribute("oMyPINChangeBO", null);
				oIBankingUserLogInForm.setErrorCode("");
				oIBankingUserLogInForm.setErrorMessage("");
	
				oIBankingUserLogInBO = oIBankingUserLogInDAO.getPINChange(gsUserID,gsSessionID,gsCompanyCode,gsBranchCode,
																"U",
																oIBankingUserLogInForm.getUserID(),
																oIBankingUserLogInForm.getPasswordOld(),
																oIBankingUserLogInForm.getPasswordNew(),
																oIBankingUserLogInForm.getPasswordConf(),
																oIBankingUserLogInForm.getPasswordExpiredDays());
				if (oIBankingUserLogInBO.getErrorCode().equals("0")){
					oIBankingUserLogInForm.setErrorCode("0");
					oIBankingUserLogInForm.setErrorMessage(oIBankingUserLogInBO.getErrorMessage());
					sSuccess = sSuccessAction;
				}
				else if (oIBankingUserLogInBO.getErrorCode().equals("1")){
					
					oIBankingUserLogInForm.setErrorCode("1");
					oIBankingUserLogInForm.setErrorMessage(oIBankingUserLogInBO.getErrorMessage());
					sSuccess = sFailureAction;
				}
			  else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		} 
	else if (sActionPath.equals("/cancelLogInSubmit")) {
			
			sSuccess = sSuccessAction;
		}  else if (sActionPath.equals("/accountInfoStatement")) {		
			  session.setAttribute("oIBankingUserLogInListBO", null);
			  oIBankingUserLogInForm.setUserAccountNo("");
			  oIBankingUserLogInForm.setFromDate("");
			  oIBankingUserLogInForm.setToDate("");
			  oIBankingUserLogInForm.setErrorCode("");
			  oIBankingUserLogInForm.setErrorMessage("");
				  sSuccess = sSuccessAction;
			 } 
		
		 else if (sActionPath.equals("/goBackHomePage")) {		
			  session.setAttribute("oIBankingUserLogInListBO", null);
			  oIBankingUserLogInForm.setErrorCode("");
			  oIBankingUserLogInForm.setErrorMessage("");
				  sSuccess = sSuccessAction;
			 } 
		
		
		
	/* else if (sActionPath.equals("/ReportNBFTransactionStatement")){

				oIBankingUserLogInBO = oIBankingUserLogInDAO.getExecuteStatementProAPI(oIBankingUserLogInForm.getAccountNumber(),oIBankingUserLogInForm.getFromDate(),oIBankingUserLogInForm.getToDate());
				
				
				if (oIBankingUserLogInBO.getErrorCode().equals("1")){
					
					  String sMessage = "";
					  sMessage = oIBankingUserLogInBO.getErrorMessage();
					  oIBankingUserLogInBO = oIBankingUserLogInDAO.setMessagePro(gsUserID,gsSessionID,"1",sMessage,"1");
					  oIBankingUserLogInMessageBO = oIBankingUserLogInDAO.getMessageInformation(gsUserID,gsSessionID);
					  populateMessage(oIBankingUserLogInForm,oIBankingUserLogInMessageBO);
				      session.setAttribute("oIBankingUserLogInMessageBO", oIBankingUserLogInMessageBO);
				      sSuccess = sFailureAction; 	
	
					
				
		}else if (true){
			ServletContext context=request.getSession().getServletContext();
			String sImageDirectory ="/pages/images/";
			String sImagePath=context.getRealPath(sImageDirectory);
			String sDirectory ="/pages/menu/report/";
			String sSubReportPath=context.getRealPath(sDirectory);
			String sStatementDate ="Statement of Account for the Period : "+oIBankingUserLogInForm.getFromDate()+" To "+oIBankingUserLogInForm.getToDate();
			String sPresentDate = "Date : "+gsPresentDate;
			
			
	        ArrayList<String> aSerialNo = new ArrayList<String>();
	        ArrayList<String> aDocInfo = new ArrayList<String>();
	        ArrayList<String> aValueDate = new ArrayList<String>();
	        ArrayList<String> aParticulars = new ArrayList<String>();
	        ArrayList<String> aDebitAmount = new ArrayList<String>();
	        ArrayList<String> aCreditAmount = new ArrayList<String>();
	        ArrayList<String> aBalanceAmount = new ArrayList<String>();
	        
	        Map<String, Object> hParameters = new HashMap<String, Object>();
	        String SerialNo = "";
	        ArrayList aStatementList = oIBankingUserLogInBO.getStatementList();
			
			
	        for(int i=0;i<aStatementList.size();i++){
				IBankingUserLogInBO bo=(IBankingUserLogInBO)aStatementList.get(i);
				SerialNo = bo.getSerialNo();
				aSerialNo.add(bo.getSerialNo());	
				aDocInfo.add(bo.getDocnum());
				aValueDate.add(bo.getValueDate());
				aParticulars.add(bo.getRemarks());
				aDebitAmount.add(bo.getDebit());
				aCreditAmount.add(bo.getCredit());
				aBalanceAmount.add(bo.getBalance());
	        }
			
	        System.out.println("============>>>>>>>>>> "+aSerialNo);
			
		//	HashMap hParameters=new HashMap();
	        hParameters.put("SerialNo", String.valueOf(aSerialNo));
			hParameters.put("aSerialNo", String.valueOf(aSerialNo));
			hParameters.put("aDocInfo", String.valueOf(aDocInfo));
			hParameters.put("aValueDate", String.valueOf(aValueDate));
			hParameters.put("aParticulars", String.valueOf(aParticulars));
			hParameters.put("aDebitAmount", String.valueOf(aDebitAmount));
			hParameters.put("aCreditAmount", String.valueOf(aCreditAmount));
			hParameters.put("aBalanceAmount", String.valueOf(aBalanceAmount));
			
			hParameters.put("sFromDate", oIBankingUserLogInForm.getFromDate());
			hParameters.put("sToDate", oIBankingUserLogInForm.getToDate());
			hParameters.put("sStatementDate", sStatementDate);
			hParameters.put("sPresentDate", sPresentDate);
			hParameters.put("sSubReportPath", sSubReportPath+"/");
			hParameters.put("sImagePath", sImagePath+"/reportlogoNBF.gif");
			String sFileName = sDirectory + "Transaction.jasper";
			ReportManager oReportManager = new ReportManager();
			oReportManager.viewReport(request, response, hParameters, sFileName);
			sSuccess = sSuccessAction;
		
				}
				else if (oIBankingUserLogInBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oIBankingUserLogInBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
						
		}*/ else if (sActionPath.equals("/logOutMyBank")) {
			oIBankingUserLogInForm.setErrorCode("");
			oIBankingUserLogInForm.setErrorMessage("");
				oIBankingUserLogInBO = oIBankingUserLogInDAO.getLogOutMyBankPro(gsUserID, gsSessionID,
						request.getRemoteAddr());
				// test sania 28.03.2016
				oIBankingUserLogInForm.setErrorCode(oIBankingUserLogInBO.getErrorCode());
				// test sania 28.03.2016
				System.out.println("oIBankingUserLogInBO.getErrorCode ==>> "+oIBankingUserLogInBO.getErrorCode()+"--"+oIBankingUserLogInBO.getErrorCode());
				
				if (oIBankingUserLogInBO.getErrorCode().equals("0")) {
					
					oIBankingUserLogInForm.setUserID("");
					oIBankingUserLogInForm.setPassword("");
					session.setAttribute("GSUserID", "");
					session.setAttribute("GSSessionID", "");
					session.setAttribute("GSUserTitle", "");
					session.setAttribute("GSLastLogInDate", "");
					session.setAttribute("GSSessionID", "");
					session.setAttribute("GSInternalCardID", "");
					session.setAttribute("GSHeaderName", "");
					session.setAttribute("GSHeaderLogIn", "");
					session.setAttribute("GSBranchCode", "");
					session.setAttribute("GSLogInToDay", "");
					session.setAttribute("GSLogInTotal", "");
					session.setAttribute("GSCompanyName", "");
					session.setAttribute("GSBranchName", "");
					session.setAttribute("GSBranchOpenDateDDFormat", "");
					session.setAttribute("GSBranchOpenDateSPFormat", "");
					session.setAttribute("GSCurrentDateDDFormat", "");
					session.setAttribute("GSCurrentDateSPFormat", "");
					session.setAttribute("GSCompanyCode", "");
					session.setAttribute("GSCompanyName", "");
					session.setAttribute("GSMenuClientPath", "");
					session.setAttribute("GSMenuBranchPath", "");
					gsUserID = "";
					gsSessionID = "";

					// genearte Captcha Code
					MyStringRandomGen msr = new MyStringRandomGen();
					String a = msr.generateRandomString();
					String b = msr.generateRandomString();
					String c = msr.generateRandomString();
					String d = msr.generateRandomString();
					String code = (a + ' ' + b + ' ' + ' ' + c + ' ' + d);

					oIBankingUserLogInForm.setCapchaCode(code);
					session.setAttribute("GSDigit1", a + ' ');
					session.setAttribute("GSDigit2", b + ' ');
					session.setAttribute("GSDigit3", c + ' ');
					session.setAttribute("GSDigit4", d + ' ');
					// genearte Captcha Code
					sSuccess = sSuccessAction;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("1")) {
					oIBankingUserLogInForm.setErrorCode("1");
					oIBankingUserLogInForm.setErrorMessage(oIBankingUserLogInBO.getErrorMessage());
					sSuccess = sFailureAction;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("2")) {
					session.setAttribute("oIBankingUserLogInBO", null);
					session.setAttribute("oIBankingUserLogInMessageBO", null);
					session.setAttribute("oCaptchaMessage", " ");
					sSuccess = sSessionExpireAction;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("3")) {
					session.setAttribute("oIBankingUserLogInBO", null);
					session.setAttribute("oIBankingUserLogInMessageBO", null);
					session.setAttribute("oCaptchaMessage", " ");
					sSuccess = sSessionMyBankMenuAction;
				} else {
					session.setAttribute("oIBankingUserLogInBO", null);
					session.setAttribute("oIBankingUserLogInMessageBO", null);
					session.setAttribute("oCaptchaMessage", " ");
					sSuccess = sFatalErrorAction;
				}
			}  else if (sActionPath.equals("/logInMyBank")) {
			session.setAttribute("oIBankingUserLogInBO", null);
			session.setAttribute("oLogInBranchInfoBO", null);
			session.setAttribute("oIBankingUserLogInMessageBO", null);
			session.setAttribute("oCaptchaMessage", " ");
			oIBankingUserLogInForm.setMyBankPassword("");
			oIBankingUserLogInForm.setBranchCode("");
			oIBankingUserLogInBO = oIBankingUserLogInDAO.getPermissionUserCheckPro(gsInternalCardID, gsUserID,
					gsSessionID, request.getRemoteAddr(), sActionClientPath, gsCompanyCode, gsBranchCode,
					sActionBranchPath);
			if (oIBankingUserLogInBO.getErrorCode().equals("0")) {
				oIBankingUserLogInBO = oIBankingUserLogInDAO.getMenuList(gsUserID, gsSessionID);
				populateMenu(oIBankingUserLogInForm, oIBankingUserLogInBO);
				session.setAttribute("oIBankingUserLogInBO", oIBankingUserLogInBO);
				oIBankingUserLogInBO = oIBankingUserLogInDAO.getCompanyInfoPro(gsUserID, gsSessionID);
				if (oIBankingUserLogInBO.getErrorCode().equals("0")) {
					oIBankingUserLogInBO = oIBankingUserLogInDAO.getCompanyInfo(gsUserID, gsSessionID);
					populateCompany(oIBankingUserLogInForm, oIBankingUserLogInBO);
					session.setAttribute("oIBankingUserLogInBO", oIBankingUserLogInBO);
					oIBankingUserLogInForm.setPassword("");
					oIBankingUserLogInForm.setCompanyCode("");
					oIBankingUserLogInForm.setBranchCode("");
					oIBankingUserLogInForm.setMyBankPassword("");
					// generate Captcha code
					MyStringRandomGen msr = new MyStringRandomGen();
					String a = msr.generateRandomString();
					String b = msr.generateRandomString();
					String c = msr.generateRandomString();
					String d = msr.generateRandomString();
					String code = (a + ' ' + b + ' ' + ' ' + c + ' ' + d);
					oIBankingUserLogInForm.setCapchaCode(code);

					session.setAttribute("GSDigit1", a + ' ');
					session.setAttribute("GSDigit2", b + ' ');
					session.setAttribute("GSDigit3", c + ' ');
					session.setAttribute("GSDigit4", d + ' ');

					sSuccess = sSuccessAction;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("1")) {
					// generate Captcha code
					MyStringRandomGen msr = new MyStringRandomGen();
					String a = msr.generateRandomString();
					String b = msr.generateRandomString();
					String c = msr.generateRandomString();
					String d = msr.generateRandomString();
					String code = (a + ' ' + b + ' ' + ' ' + c + ' ' + d);
					oIBankingUserLogInForm.setCapchaCode(code);

					session.setAttribute("GSDigit1", a + ' ');
					session.setAttribute("GSDigit2", b + ' ');
					session.setAttribute("GSDigit3", c + ' ');
					session.setAttribute("GSDigit4", d + ' ');

					// generate Captcha code
					// generateCaptchaCode(oIBankingUserLogInForm, session);

					oIBankingUserLogInMessageBO = oIBankingUserLogInDAO.getMessageInformation(gsUserID, gsSessionID);
					populateMessage(oIBankingUserLogInForm, oIBankingUserLogInBO);
					session.setAttribute("oIBankingUserLogInMessageBO", oIBankingUserLogInMessageBO);
					sSuccess = sFailureAction;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("2")) {
					session.setAttribute("oIBankingUserLogInBO", null);
					session.setAttribute("oIBankingUserLogInMessageBO", null);
					session.setAttribute("oCaptchaMessage", " ");
					sSuccess = sSessionExpireAction;
				} else if (oIBankingUserLogInBO.getErrorCode().equals("3")) {
					session.setAttribute("oIBankingUserLogInBO", null);
					session.setAttribute("oIBankingUserLogInMessageBO", null);
					session.setAttribute("oCaptchaMessage", " ");
					sSuccess = sSessionMyBankMenuAction;
				} else {
					session.setAttribute("oIBankingUserLogInBO", null);
					session.setAttribute("oIBankingUserLogInMessageBO", null);
					session.setAttribute("oCaptchaMessage", " ");
					sSuccess = sFatalErrorAction;
				}
			} else if (oIBankingUserLogInBO.getErrorCode().equals("1")) {
				// generate Captcha code
				MyStringRandomGen msr = new MyStringRandomGen();
				String a = msr.generateRandomString();
				String b = msr.generateRandomString();
				String c = msr.generateRandomString();
				String d = msr.generateRandomString();
				String code = (a + ' ' + b + ' ' + ' ' + c + ' ' + d);
				oIBankingUserLogInForm.setCapchaCode(code);

				session.setAttribute("GSDigit1", a + ' ');
				session.setAttribute("GSDigit2", b + ' ');
				session.setAttribute("GSDigit3", c + ' ');
				session.setAttribute("GSDigit4", d + ' ');

				// generate Captcha code
				// generateCaptchaCode(oIBankingUserLogInForm, session);

				oIBankingUserLogInBO = oIBankingUserLogInDAO.getMessageInformation(gsUserID, gsSessionID);
				populateMessage(oIBankingUserLogInForm, oIBankingUserLogInBO);
				session.setAttribute("oIBankingUserLogInBO", oIBankingUserLogInBO);
				sSuccess = sFailureAction;
			} else if (oIBankingUserLogInBO.getErrorCode().equals("2")) {
				session.setAttribute("oIBankingUserLogInBO", null);
				session.setAttribute("oIBankingUserLogInMessageBO", null);
				session.setAttribute("oCaptchaMessage", " ");
				sSuccess = sSessionExpireAction;
			} else if (oIBankingUserLogInBO.getErrorCode().equals("3")) {
				session.setAttribute("oIBankingUserLogInBO", null);
				session.setAttribute("oIBankingUserLogInMessageBO", null);
				session.setAttribute("oCaptchaMessage", " ");
				sSuccess = sSessionMyBankMenuAction;
			} else {
				session.setAttribute("oIBankingUserLogInBO", null);
				session.setAttribute("oIBankingUserLogInMessageBO", null);
				session.setAttribute("oCaptchaMessage", " ");
				sSuccess = sFatalErrorAction;
			}
		} 

		return mapping.findForward(sSuccess);
	}
	
	private void clearSession(HttpSession session) {
		session.setAttribute("oIBankingUserLogInBO", null);
		session.setAttribute("oIBankingUserLogInMessageBO", null);
	}

	private void populateCustomerInfoList(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setUserName(oIBankingUserLogInBO.getUserName());
		oIBankingUserLogInForm.setUserAddress(oIBankingUserLogInBO.getUserAddress());
		oIBankingUserLogInForm.setUserContact(oIBankingUserLogInBO.getUserContact());
		oIBankingUserLogInForm.setUserEMail(oIBankingUserLogInBO.getUserEMail());
		oIBankingUserLogInForm.setUserNationalID(oIBankingUserLogInBO.getUserNationalID());
		oIBankingUserLogInForm.setStatusflag(oIBankingUserLogInBO.getStatusflag());

	}

	public void populateBalanceInfo(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setUserAccountNo(oIBankingUserLogInBO.getUserAccountNo());
		oIBankingUserLogInForm.setUserAccountTitle(oIBankingUserLogInBO.getUserAccountTitle());
		oIBankingUserLogInForm.setUserCurrentBalance(oIBankingUserLogInBO.getUserCurrentBalance());
		oIBankingUserLogInForm.setUserCurrency(oIBankingUserLogInBO.getUserCurrency());
		oIBankingUserLogInForm.setUserCompanyCode(oIBankingUserLogInBO.getUserCompanyCode());
		oIBankingUserLogInForm.setUserAccountList(oIBankingUserLogInBO.getUserAccountList());
	}

	public void populateMaxRecord(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setDisplayInTotalRecord(oIBankingUserLogInBO.getDisplayInTotalRecord());
	}

	/*
	 * private void populateUserDetails(IBankingUserLogInForm
	 * oIBankingUserLogInForm, IBankingUserLogInBO oIBankingUserLogInBO) {
	 * oIBankingUserLogInForm.setUserName(oIBankingUserLogInBO.getUserName());
	 * oIBankingUserLogInForm.setUserEMail(oIBankingUserLogInBO.getUserEMail());
	 * oIBankingUserLogInForm.setUserContact(oIBankingUserLogInBO.getUserContact());
	 * oIBankingUserLogInForm.setUserAddress(oIBankingUserLogInBO.getUserAddress());
	 * oIBankingUserLogInForm.setUserDOB(oIBankingUserLogInBO.getUserDOB()); }
	 */
	public void populate(IBankingUserLogInForm oIBankingUserLogInForm, IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setUserID(oIBankingUserLogInBO.getUserID());
		oIBankingUserLogInForm.setUserTitle(oIBankingUserLogInBO.getUserTitle());
		oIBankingUserLogInForm.setPassword(oIBankingUserLogInBO.getPassword());
		oIBankingUserLogInForm.setLastLogIn(oIBankingUserLogInBO.getLastLogIn());
		oIBankingUserLogInForm.setPresentDate(oIBankingUserLogInBO.getPresentDate());
		oIBankingUserLogInForm.setSessionID(oIBankingUserLogInBO.getSessionID());
		oIBankingUserLogInForm.setNodeId(oIBankingUserLogInBO.getNodeId());
		oIBankingUserLogInForm.setNodeName(oIBankingUserLogInBO.getNodeName());
		oIBankingUserLogInForm.setParentId(oIBankingUserLogInBO.getParentId());
		oIBankingUserLogInForm.setUrl(oIBankingUserLogInBO.getUrl());
		oIBankingUserLogInForm.setCompanyCode(oIBankingUserLogInBO.getCompanyCode());
		oIBankingUserLogInForm.setCompanyName(oIBankingUserLogInBO.getCompanyName());
		oIBankingUserLogInForm.setBranchCode(oIBankingUserLogInBO.getBranchCode());
		oIBankingUserLogInForm.setBranchCodeList(oIBankingUserLogInBO.getBranchCodeList());
		oIBankingUserLogInForm.setBranchCodeNameList(oIBankingUserLogInBO.getBranchCodeNameList());
		oIBankingUserLogInForm.setBranchName(oIBankingUserLogInBO.getBranchName());
		oIBankingUserLogInForm.setBranchOpenDateDDF(oIBankingUserLogInBO.getBranchOpenDateDDF());
		oIBankingUserLogInForm.setBranchOpenDateSPF(oIBankingUserLogInBO.getBranchOpenDateSPF());
		oIBankingUserLogInForm.setCurrentDateDDF(oIBankingUserLogInBO.getCurrentDateDDF());
		oIBankingUserLogInForm.setCurrentDateSPF(oIBankingUserLogInBO.getCurrentDateSPF());
		oIBankingUserLogInForm.setMyBankPassword(oIBankingUserLogInBO.getMyBankPassword());
		oIBankingUserLogInForm.setLogInToDay(oIBankingUserLogInBO.getLogInToDay());
		oIBankingUserLogInForm.setLogInTotal(oIBankingUserLogInBO.getLogInTotal());
		oIBankingUserLogInForm.setBranchNameHeader(oIBankingUserLogInBO.getBranchNameHeader());
		oIBankingUserLogInForm.setBranchDateHeader(oIBankingUserLogInBO.getBranchDateHeader());
		oIBankingUserLogInForm.setHeaderName(oIBankingUserLogInBO.getHeaderName());
		oIBankingUserLogInForm.setHeaderLogIn(oIBankingUserLogInBO.getHeaderLogIn());
		oIBankingUserLogInForm.setErrorCode(oIBankingUserLogInBO.getErrorCode());
		oIBankingUserLogInForm.setErrorMessage(oIBankingUserLogInBO.getErrorMessage());
		oIBankingUserLogInForm.setImageFileName(oIBankingUserLogInBO.getImageFileName());
		oIBankingUserLogInForm.setImageWritePath(oIBankingUserLogInBO.getImageWritePath());
		oIBankingUserLogInForm.setImageReadPath(oIBankingUserLogInBO.getImageReadPath());
		oIBankingUserLogInForm.setList(oIBankingUserLogInBO.getList());
		oIBankingUserLogInForm.setHelpList(oIBankingUserLogInBO.getHelpList());
		oIBankingUserLogInForm.setHelpMessage(oIBankingUserLogInBO.getHelpMessage());
	}

	public void populateSessionLastLogIn(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setLastLogIn(oIBankingUserLogInBO.getLastLogIn());
		oIBankingUserLogInForm.setHeaderLogIn(oIBankingUserLogInBO.getHeaderLogIn());
	}

	public void populateGeoActionFlag(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setGeoActionFlag(oIBankingUserLogInBO.getGeoActionFlag());
		oIBankingUserLogInForm.setGeoFlag(oIBankingUserLogInBO.getGeoFlag());
	}

	public void populateSessionClient(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setLastLogIn(oIBankingUserLogInBO.getLastLogIn());
		oIBankingUserLogInForm.setUserTitle(oIBankingUserLogInBO.getUserTitle());
		oIBankingUserLogInForm.setPresentDate(oIBankingUserLogInBO.getPresentDate());
		oIBankingUserLogInForm.setLogInToDay(oIBankingUserLogInBO.getLogInToDay());
		oIBankingUserLogInForm.setLogInTotal(oIBankingUserLogInBO.getLogInTotal());
		oIBankingUserLogInForm.setHeaderName(oIBankingUserLogInBO.getHeaderName());
		oIBankingUserLogInForm.setHeaderLogIn(oIBankingUserLogInBO.getHeaderLogIn());
	}

	public void populateMyBank(IBankingUserLogInForm oIBankingUserLogInForm, IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setCompanyCode(oIBankingUserLogInBO.getCompanyCode());
		oIBankingUserLogInForm.setCompanyCodeList(oIBankingUserLogInBO.getCompanyCodeList());
		oIBankingUserLogInForm.setCompanyCodeNameList(oIBankingUserLogInBO.getCompanyCodeNameList());
		oIBankingUserLogInForm.setCompanyCode(oIBankingUserLogInBO.getCompanyCode());
		oIBankingUserLogInForm.setCompanyName(oIBankingUserLogInBO.getCompanyName());
		oIBankingUserLogInForm.setBranchCode(oIBankingUserLogInBO.getBranchCode());
		oIBankingUserLogInForm.setBranchCodeList(oIBankingUserLogInBO.getBranchCodeList());
		oIBankingUserLogInForm.setBranchCodeNameList(oIBankingUserLogInBO.getBranchCodeNameList());
		oIBankingUserLogInForm.setBranchName(oIBankingUserLogInBO.getBranchName());
		oIBankingUserLogInForm.setBranchOpenDateDDF(oIBankingUserLogInBO.getBranchOpenDateDDF());
		oIBankingUserLogInForm.setBranchOpenDateSPF(oIBankingUserLogInBO.getBranchOpenDateSPF());
		oIBankingUserLogInForm.setCurrentDateDDF(oIBankingUserLogInBO.getCurrentDateDDF());
		oIBankingUserLogInForm.setCurrentDateSPF(oIBankingUserLogInBO.getCurrentDateSPF());
		oIBankingUserLogInForm.setBranchNameHeader(oIBankingUserLogInBO.getBranchNameHeader());
		oIBankingUserLogInForm.setBranchDateHeader(oIBankingUserLogInBO.getBranchDateHeader());
		oIBankingUserLogInForm.setTellerID(oIBankingUserLogInBO.getTellerID());
	}

	public void populateCompany(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setCompanyCode(oIBankingUserLogInBO.getCompanyCode());
		oIBankingUserLogInForm.setCompanyCodeList(oIBankingUserLogInBO.getCompanyCodeList());
		oIBankingUserLogInForm.setCompanyCodeNameList(oIBankingUserLogInBO.getCompanyCodeNameList());
	}

	public void populateBranch(IBankingUserLogInForm oIBankingUserLogInForm, IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setBranchCode(oIBankingUserLogInBO.getBranchCode());
		oIBankingUserLogInForm.setBranchCodeList(oIBankingUserLogInBO.getBranchCodeList());
		oIBankingUserLogInForm.setBranchCodeNameList(oIBankingUserLogInBO.getBranchCodeNameList());
		oIBankingUserLogInForm.setBranchName(oIBankingUserLogInBO.getBranchName());
		oIBankingUserLogInForm.setCompanyName(oIBankingUserLogInBO.getCompanyName());
	}

	public void populateAddress(IBankingUserLogInBO oIBankingUserLogInBO) {
		// oIBankingUserLogInBO.setPhysicalAddress(oIBankingUserLogInBO.getPhysicalAddress());
		oIBankingUserLogInBO.setIPAddressServer(oIBankingUserLogInBO.getIPAddressServer());
		oIBankingUserLogInBO.setIPAddressClient(oIBankingUserLogInBO.getIPAddressClient());
	}

	public void populateMessage(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		// oIBankingUserLogInForm.setErrorCode(oIBankingUserLogInBO.getErrorCode());
		oIBankingUserLogInForm.setErrorMessage(oIBankingUserLogInBO.getErrorMessage());
		oIBankingUserLogInForm.setList(oIBankingUserLogInBO.getList());
	}

	public void populateHelp(IBankingUserLogInForm oIBankingUserLogInForm, IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setHelpMessage(oIBankingUserLogInBO.getHelpMessage());
		oIBankingUserLogInForm.setHelpList(oIBankingUserLogInBO.getHelpList());
	}

	public void populateImage(IBankingUserLogInForm oIBankingUserLogInForm, IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setImageFileName(oIBankingUserLogInBO.getImageFileName());
		oIBankingUserLogInForm.setImageReadPath(oIBankingUserLogInBO.getImageReadPath());
		oIBankingUserLogInForm.setImageWritePath(oIBankingUserLogInBO.getImageWritePath());
	}

	public void populateMenu(IBankingUserLogInForm oIBankingUserLogInForm, IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setMenu(oIBankingUserLogInBO.getMenu());
		oIBankingUserLogInForm.setMenuList(oIBankingUserLogInBO.getMenuList());
		oIBankingUserLogInForm.setMenuNameList(oIBankingUserLogInBO.getMenuNameList());
	}

	public void populateScroller(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setScrollerInfo(oIBankingUserLogInBO.getMenu());
		oIBankingUserLogInForm.setScrollerInfoList(oIBankingUserLogInBO.getMenuList());
	}

	public void populateBranchAddress(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setBranchName(oIBankingUserLogInBO.getBranchName());
		oIBankingUserLogInForm.setBranchAddress(oIBankingUserLogInBO.getBranchAddress());
		oIBankingUserLogInForm.setBranchCityName(oIBankingUserLogInBO.getBranchCityName());
		oIBankingUserLogInForm.setBranchCity(oIBankingUserLogInBO.getBranchCity());
		oIBankingUserLogInForm.setBranchCityList(oIBankingUserLogInBO.getBranchCityList());
		oIBankingUserLogInForm.setBranchCityNameList(oIBankingUserLogInBO.getBranchCityNameList());
		oIBankingUserLogInForm.setBranchZipCode(oIBankingUserLogInBO.getBranchZipCode());
		oIBankingUserLogInForm.setBranchContactNo(oIBankingUserLogInBO.getBranchContactNo());
		oIBankingUserLogInForm.setBranchFaxNo(oIBankingUserLogInBO.getBranchFaxNo());
		oIBankingUserLogInForm.setBranchSwift(oIBankingUserLogInBO.getBranchSwift());
		oIBankingUserLogInForm.setBranchEmailAddress(oIBankingUserLogInBO.getBranchEmailAddress());
	}

	public void populateBranchInfo(IBankingUserLogInForm oIBankingUserLogInForm,
			IBankingUserLogInBO oIBankingUserLogInBO) {
		oIBankingUserLogInForm.setColumnList(oIBankingUserLogInBO.getColumnList());
	}
	
	public void clearForm(IBankingUserLogInForm oIBankingUserLogInForm) {
		oIBankingUserLogInForm.setPasswordOld("");
		oIBankingUserLogInForm.setPasswordNew("");
		oIBankingUserLogInForm.setPasswordConf("");
		oIBankingUserLogInForm.setPasswordExpiredDays("");
	}

		/*
	 * public void generateCaptchaCode(IBankingUserLogInForm oIBankingUserLogInForm,
	 * HttpSession session) throws IOException {
	 * 
	 * //generate Captcha code MyStringRandomGen msr = new MyStringRandomGen();
	 * String a = msr.generateRandomString(); String b = msr.generateRandomString();
	 * String c = msr.generateRandomString(); String d = msr.generateRandomString();
	 * String e = msr.generateRandomString(); String f = msr.generateRandomString();
	 * 
	 * String code = (a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' ' + f);
	 * oIBankingUserLogInForm.setCapchaCode(code); sCaptchaCode =
	 * oIBankingUserLogInForm.getCapchaCode();
	 * 
	 * session.setAttribute("GSDigit1", a + ' '); session.setAttribute("GSDigit2", b
	 * + ' '); session.setAttribute("GSDigit3", c + ' ');
	 * session.setAttribute("GSDigit4", d + ' '); session.setAttribute("GSDigit5", e
	 * + ' '); session.setAttribute("GSDigit6", f + ' '); //generate Captcha code }
	 */

}
