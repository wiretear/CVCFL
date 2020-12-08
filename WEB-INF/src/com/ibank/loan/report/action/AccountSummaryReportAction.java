package com.ibank.loan.report.action;

import java.io.File;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibank.loan.report.bo.AccountSummaryReportBO;
import com.ibank.loan.report.dao.AccountSummaryReportDAO;
import com.ibank.loan.report.formbean.AccountSummaryReportForm;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

public class AccountSummaryReportAction  extends Action {

	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)throws Exception {
		AccountSummaryReportDAO oAccountSummaryReportDAO = new AccountSummaryReportDAO();
		AccountSummaryReportBO oAccountSummaryReportBO = new AccountSummaryReportBO();
		AccountSummaryReportForm oAccountSummaryReportForm = (AccountSummaryReportForm) form;
		AccountSummaryReportBO oAccountSummaryReportMessageBO = new AccountSummaryReportBO();  
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oAccountSummaryReportForm);
		String sActionPath = "";
		HttpSession session = request.getSession(true);
		sActionPath = mapping.getPath();
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sClientActionPath = "/ibankinguser/accountSummaryReport.do";
		String gsUserID = (String) session.getAttribute("GSUserID");
		String gsUserTitle = (String) session.getAttribute("GSUserTitle");
		String gsLastLogInDate = (String) session.getAttribute("GSLastLogInDate");
		String gsPresentDate = (String) session.getAttribute("GSPresentDate");
		String gsSessionID = (String) session.getAttribute("GSSessionID");
		String gsInternalCardID = (String) session.getAttribute("GSInternalCardID");
		String gsHeaderName = (String) session.getAttribute("GSHeaderName");
		String gsHeaderLogIn = (String) session.getAttribute("GSHeaderLogIn");
		String gsCompanyID = (String) session.getAttribute("GSCompanyhCode");
		String gsBranchID = (String) session.getAttribute("GSBranchCode");
		String gsStatementAccountNo = (String) session.getAttribute("GSStatementAccountNo");
		String gsStatementCompanyhCode = (String) session.getAttribute("GSStatementCompanyhCode");
		
		String gsDisplayLastRecord = (String) session.getAttribute("GSDisplayTransactionInfoEnquiry");
		String gsDisplayRequestLastRecord = (String) session.getAttribute("GSDisplayRequestTransactionInfoEnquiry");
		String gsFirstRecord = (String) session.getAttribute("GSDisplayFirstRecordTransactionInfoEnquiry");
		String gsLastRecord = (String) session.getAttribute("GSDisplayLastRecordTransactionInfoEnquiry");
		String gsRequestFirstRecord = (String) session.getAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry");
		String gsRequestLastRecord = (String) session.getAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry");
		String gsDisplayTotalRecord = (String) session.getAttribute("GSDisplayRecordTransactionInfoEnquiry");
		String gsRequestTotalRecord = (String) session.getAttribute("GSDisplayRequestTransactionInfoEnquiry");
		String gsCustomerCode = (String) session.getAttribute("GSCustomerCode");
		
		if (sActionPath.equals("/loanAccountSummaryReport")) {
			clearSession(session);
			session.setAttribute("oAccountSummaryReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oAccountSummaryReportStatementListBO", null);
			//session.setAttribute("oAccountSummaryReportBO", null);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);	
			oAccountSummaryReportForm.setErrorCode("");
			oAccountSummaryReportForm.setErrorMessage("");
			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oAccountSummaryReportForm.setErrorCode("");
			oAccountSummaryReportForm.setErrorMessage("");
			oAccountSummaryReportBO=oAccountSummaryReportDAO.getApiDetails("ibnk", request.getRemoteAddr(), gsUserID, gsUserID, "001", "I", "10");
			if(oAccountSummaryReportBO.getErrorCode().equals("0")) {
		

			//oAccountSummaryReportBO = oAccountSummaryReportDAO.firstLoad(gsUserID,gsSessionID);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			oAccountSummaryReportBO = oAccountSummaryReportDAO.getAccountData(gsCustomerCode);
			oAccountSummaryReportForm.setDateFrom(oAccountSummaryReportBO.getDateFrom());
			oAccountSummaryReportForm.setDateTo(oAccountSummaryReportBO.getDateTo());
			oAccountSummaryReportForm.setAccountInfoList(oAccountSummaryReportBO.getAccountInfoList());
			oAccountSummaryReportForm.setAccountInfoNameList(oAccountSummaryReportBO.getAccountInfoNameList());
			

					//System.out.println("oAccountSummaryReportBO.User ==>>> "+gsUserAccount);
					//	oAccountSummaryReportForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportForm.setErrorCode("1");
				oAccountSummaryReportForm.setErrorMessage(oAccountSummaryReportBO.getErrorMessage());
				sSuccess = sFailureAction; 
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		
		if (sActionPath.equals("/logOutibankLoanAccountSummary")) {
			clearSession(session);
			session.setAttribute("oAccountSummaryReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			session.setAttribute("oAccountSummaryReportStatementListBO", null);
	
			oAccountSummaryReportForm.setErrorCode("");
			oAccountSummaryReportForm.setErrorMessage("");
	
					//System.out.println("oAccountSummaryReportBO.User ==>>> "+gsUserAccount);


					//System.out.println("oAccountSummaryReportBO.User ==>>> "+gsUserAccount);
					//	oAccountSummaryReportForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
		}
		
		else if (sActionPath.equals("/accountNoAccountSummaryReportInfoEnquiry")) {
			session.setAttribute("AccountSummaryReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			oAccountSummaryReportBO = oAccountSummaryReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			if (oAccountSummaryReportBO.getErrorCode().equals("0")){
				oAccountSummaryReportBO = oAccountSummaryReportDAO.getCompanyCode(oAccountSummaryReportForm.getAccountInfo());			
				oAccountSummaryReportForm.setAccountNo(oAccountSummaryReportBO.getAccountNo());
				oAccountSummaryReportForm.setCompanyCode(oAccountSummaryReportBO.getCompanyCode());
				oAccountSummaryReportForm.setAccountInfo(oAccountSummaryReportForm.getAccountInfo());
				sSuccess = sSuccessAction;
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
				String sMessage = "";
				  sMessage = oAccountSummaryReportBO.getErrorMessage();
				  oAccountSummaryReportForm.setErrorCode("1");
				  oAccountSummaryReportForm.setErrorMessage(sMessage);
				oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction;
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}		
		
		
		else if (sActionPath.equals("/executeLoanAccountSummaryReport")) {
			  session.setAttribute("oAccountSummaryReportMessageBO"," ");
	
				oAccountSummaryReportBO = oAccountSummaryReportDAO.getApiDetails("ibnk", request.getRemoteAddr(), gsUserID, gsSessionID, "001", "I", "02");
				   if (oAccountSummaryReportBO.getErrorCode().equals("0")) {	
						oAccountSummaryReportBO = oAccountSummaryReportDAO.getExecuteStatementProAPI(oAccountSummaryReportBO.getTargetUrlApi(), gsUserID, gsSessionID, oAccountSummaryReportForm.getAccountNo(), oAccountSummaryReportForm.getDateFrom(), oAccountSummaryReportForm.getDateTo());													 
			   if (oAccountSummaryReportBO.getErrorCode().equals("0")) {				   	
			        	
			       	ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/loan/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
				
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);			
					hParameters.put("sAccountNo",oAccountSummaryReportForm.getAccountNo());
				//	hParameters.put("sServiceType", sServiceName);	
					hParameters.put("sCustomerName",oAccountSummaryReportForm.getCustomerName());
					hParameters.put("sAccountName",oAccountSummaryReportBO.getAccountName());
					hParameters.put("sAsOnDate",oAccountSummaryReportForm.getDateTo());
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/nbfilogo.png");
					hParameters.put("sAccountNo",oAccountSummaryReportForm.getAccountNo());
					hParameters.put("sImagePath",new File(sImagePath + "/nbfilogo.png"));
					String sFileName = sDirectory + "AccountSummaryReport.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);						

				     sSuccess = sSuccessAction;
					} 
					else if (oAccountSummaryReportBO.getErrorCode().equals("1")) {
						String sMessage = "";
					
						  sMessage = oAccountSummaryReportBO.getErrorMessage();
						  oAccountSummaryReportForm.setErrorCode("1");
						  oAccountSummaryReportForm.setErrorMessage(sMessage);
						String	UserActivityHistoryReportMessageBO =oAccountSummaryReportBO.getErrorMessage();							
						session.setAttribute("oUserActivityHistoryReportMessageBO",UserActivityHistoryReportMessageBO);
						
						sSuccess = sFailureAction;
					} 
					else if (oAccountSummaryReportBO.getErrorCode().equals("2")) {
						clearSession(session);
						sSuccess = sSessionExpireAction;
					} 
					else if (oAccountSummaryReportBO.getErrorCode().equals("3")) {
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					} 
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}									
				} 
		}
	
		
				
		else if (sActionPath.equals("/executeaaAccountSummaryReport")){
				oAccountSummaryReportForm.setErrorCode("");
				oAccountSummaryReportForm.setErrorMessage("");
				session.setAttribute("oAccountSummaryReportStatementListBO", null);
				
				//oAccountSummaryReportBO = oAccountSummaryReportDAO.getExecuteStatementProAPI(gsUserID, gsSessionID, "", "", "");
		
			 System.out.println("oIBankingUserLogInBO.getErrorCode() ===>>>>>> " + oAccountSummaryReportBO.getErrorCode());
			 
			 if (oAccountSummaryReportBO.getErrorCode().equals("0")) {
				 
				 oAccountSummaryReportBO.setStatementList(oAccountSummaryReportBO.getStatementList());				 
				  session.setAttribute("oAccountSummaryReportStatementListBO", oAccountSummaryReportBO);
				  sSuccess = sSuccessAction;
			 } else if (oAccountSummaryReportBO.getErrorCode().equals("1")) {
				
				  String sMessage = "";
				  sMessage = oAccountSummaryReportBO.getErrorMessage();
				  oAccountSummaryReportForm.setErrorCode("1");
				  oAccountSummaryReportForm.setErrorMessage(sMessage);
			      sSuccess = sFailureAction;
			
			}else {
				
				oAccountSummaryReportForm.setStatementList(oAccountSummaryReportBO.getStatementList());
				session.setAttribute("oIBankingUserLogInListBO",oAccountSummaryReportBO);						
				sSuccess = sSuccessAction;
			}
	
		
		}  
		else if (sActionPath.equals("/serachAccountSummaryReport")){
			//oAccountSummaryReportBO = oAccountSummaryReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			System.out.println("Eroor"+oAccountSummaryReportBO.getErrorCode()+"messge"+oAccountSummaryReportBO.getErrorMessage());
			if (oAccountSummaryReportBO.getErrorCode().equals("0")){
				oAccountSummaryReportBO = oAccountSummaryReportDAO.getExecuteStatementProAPI(oAccountSummaryReportBO.getTargetUrlApi(), gsUserID, gsSessionID, oAccountSummaryReportForm.getAccountNo(), oAccountSummaryReportForm.getDateFrom(), oAccountSummaryReportForm.getDateTo());
				//oAccountSummaryReportBO = oAccountSummaryReportDAO.getTransactionPreviewPro(gsUserID,gsSessionID,oAccountSummaryReportForm.getCompanyCode(),oAccountSummaryReportForm.getAccountNo(),oAccountSummaryReportForm.getDateFrom(),oAccountSummaryReportForm.getDateTo());
				if (oAccountSummaryReportBO.getErrorCode().equals("0")){
					ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/loan/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
					String sStatementDate ="Statement of Account for the Period : "+oAccountSummaryReportForm.getDateFrom()+" To "+oAccountSummaryReportForm.getDateTo();
					String sPresentDate = "Date : "+gsPresentDate;
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);
					hParameters.put("sFromDate", oAccountSummaryReportForm.getDateFrom());
					hParameters.put("sToDate", oAccountSummaryReportForm.getDateTo());
					hParameters.put("sStatementDate", sStatementDate);
					hParameters.put("sPresentDate", sPresentDate);
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/reportlogo.gif");
					String sFileName = sDirectory + "AccountSummaryReport.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);
					sSuccess = sSuccessAction;
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/fileAccountStatement")) {
			session.setAttribute("GSFilePath", null);
			session.setAttribute("GSFileName", null);
			session.setAttribute("oAccountSummaryReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO",	null);
			oAccountSummaryReportBO = oAccountSummaryReportDAO.getTransactionPreviewPro1(gsUserID,gsSessionID,
					oAccountSummaryReportForm.getCompanyCode(),
					oAccountSummaryReportForm.getAccountNo(),
					oAccountSummaryReportForm.getDateFrom(),
					oAccountSummaryReportForm.getDateTo());

			if (oAccountSummaryReportBO.getErrorCode().equals("0")) {
				oAccountSummaryReportBO =	oAccountSummaryReportDAO.getFileName();
				populateFileName(oAccountSummaryReportForm,oAccountSummaryReportBO);

				oAccountSummaryReportBO =oAccountSummaryReportDAO.getXLSFile(session,gsUserID,gsSessionID,
														   oAccountSummaryReportForm.getFile());

					String sFilePath ="/mybank/AccountStatement/BalanceDetailReport/"	+ gsSessionID + "/" + oAccountSummaryReportForm.getFile()+ ".xls";
					String sFileName = oAccountSummaryReportForm.getFile() + ".xls";
					session.setAttribute("GSFilePath", sFilePath);
					session.setAttribute("GSFileName", sFileName);

					String sMessageCode = "";
					sMessageCode = "1";
					String sSerialNo = "";
					sSerialNo = "1";
					String sMessage = "";
					sMessage = "Please download the generated file.";
					oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO",oAccountSummaryReportMessageBO);
					sSuccess = sSuccessAction;
				} 
			else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}				
				else if (oAccountSummaryReportBO.getErrorCode().equals("2")) {
					clearSession(session);
					sSuccess = sSessionExpireAction;
				} else if (oAccountSummaryReportBO.getErrorCode().equals("3")) {
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				} else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
		
		
		else if (sActionPath.equals("/cancelTransactionInfoEnquiry")) {
			oAccountSummaryReportBO = oAccountSummaryReportDAO.getMenuCheckPro(
											 gsUserID,
											 gsSessionID,
											 request.getRemoteAddr(),
											 sClientActionPath);	
			
			if (oAccountSummaryReportBO.getErrorCode().equals("0")){
				oAccountSummaryReportBO = oAccountSummaryReportDAO.getClearPro(gsUserID,gsSessionID);
				if (oAccountSummaryReportBO.getErrorCode().equals("0")){
					session.setAttribute("GSDisplayTransactionInfoEnquiry","");
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");
					session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
					session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
					session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");
					session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry","");
					session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry","");
					session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry","");
					session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry","");
					gsDisplayLastRecord = "";
					gsDisplayRequestLastRecord = "";
					session.setAttribute("oAccountSummaryReportBO", null);
					session.setAttribute("oAccountSummaryReportMessageBO", null);
					session.setAttribute("processTransaction",null);
					sSuccess = sSuccessAction;
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/displayTransactionInfoEnquiry")) {
			oAccountSummaryReportBO = oAccountSummaryReportDAO.getMaxRecord(gsUserID,gsSessionID);
			populateMaxRecord(oAccountSummaryReportForm,oAccountSummaryReportBO);
			String sRecord = oAccountSummaryReportForm.getDisplayTotalRecord();
			if (sRecord.equals("0")){
				oAccountSummaryReportBO = oAccountSummaryReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
				if (oAccountSummaryReportBO.getErrorCode().equals("0")){
					oAccountSummaryReportBO = oAccountSummaryReportDAO.getTransactionSearchPro(gsUserID,gsSessionID,oAccountSummaryReportForm.getCompanyCode(),oAccountSummaryReportForm.getAccountNo(),oAccountSummaryReportForm.getDateFrom(),oAccountSummaryReportForm.getDateTo());
					if (oAccountSummaryReportBO.getErrorCode().equals("0")){
						session.setAttribute("GSDisplayTransactionInfoEnquiry","");
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportBO = oAccountSummaryReportDAO.getMaxRecord(gsUserID,gsSessionID);
						populateMaxRecord(oAccountSummaryReportForm, oAccountSummaryReportBO);
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", oAccountSummaryReportForm.getDisplayTotalRecord());
					
						oAccountSummaryReportBO = oAccountSummaryReportDAO.getDisplayRecordPro("F",oAccountSummaryReportForm.getDisplayFrequency(),oAccountSummaryReportForm.getDisplayTotalRecord(),oAccountSummaryReportForm.getDisplayFrequency());
						if (oAccountSummaryReportBO.getErrorCode().equals("0")){
							session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
							session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

							session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oAccountSummaryReportBO.getDisplayOutMessage());
							session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oAccountSummaryReportBO.getDisplayOutFirstRecord());
							session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oAccountSummaryReportBO.getDisplayOutLastRecord());
							String sFirstRecord = oAccountSummaryReportBO.getDisplayOutFirstRecord(); 
							String sLastRecord = oAccountSummaryReportBO.getDisplayOutLastRecord(); 
							session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);

							oAccountSummaryReportBO = oAccountSummaryReportDAO.getMaxRecordRequest(gsUserID,gsSessionID);
							populateMaxRecordRequest(oAccountSummaryReportForm, oAccountSummaryReportBO);
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", oAccountSummaryReportForm.getDisplayRequestTotalRecord());
							oAccountSummaryReportBO = oAccountSummaryReportDAO.getDisplayRecordPro("F",oAccountSummaryReportForm.getDisplayRequestFrequency(),oAccountSummaryReportForm.getDisplayRequestTotalRecord(),oAccountSummaryReportForm.getDisplayRequestFrequency());
							if (oAccountSummaryReportBO.getErrorCode().equals("0")){
							
								session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oAccountSummaryReportBO.getDisplayOutMessage());
								session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oAccountSummaryReportBO.getDisplayOutFirstRecord());
								session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oAccountSummaryReportBO.getDisplayOutLastRecord());
							
								String sRequestFirstRecord = oAccountSummaryReportBO.getDisplayOutFirstRecord();
								String sRequestLastRecord = oAccountSummaryReportBO.getDisplayOutLastRecord();
								session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
							
								oAccountSummaryReportBO = oAccountSummaryReportDAO.getTransactionInformation(gsUserID,gsSessionID);
								populateSearch(oAccountSummaryReportForm, oAccountSummaryReportBO);
								session.setAttribute("oAccountSummaryReportBO", oAccountSummaryReportBO);
								 
								oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						//		oAccountSummaryReportBO = oAccountSummaryReportDAO.firstLoad(gsUserID,gsSessionID);
								sSuccess = sSuccessAction;
							}
							else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
								oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
								sSuccess = sFailureAction;
							}
							else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
								clearSession(session);
								sSuccess = sSessionExpireAction;
							}
							else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
								clearSession(session);
								sSuccess = sSessionMyBankMenuAction;
							}
							else {
								clearSession(session);
								sSuccess = sFatalErrorAction;
							}
						}	
						else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
							oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
							populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
							session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
							sSuccess = sFailureAction;
						}
						else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					}
					else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction;
					}
					else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else{
				session.setAttribute("oAccountSummaryReportBO", null);
				String sDisplayTypeAction = oAccountSummaryReportForm.getDisplayTypeAction();
				if (sDisplayTypeAction.equals("L")){
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					oAccountSummaryReportBO = oAccountSummaryReportDAO.getDisplayRecordPro(oAccountSummaryReportForm.getDisplayAction(),gsDisplayLastRecord,gsDisplayTotalRecord,oAccountSummaryReportForm.getDisplayFrequency());
					if (oAccountSummaryReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oAccountSummaryReportBO.getDisplayOutMessage());
						String sFirstRecord = oAccountSummaryReportBO.getDisplayOutFirstRecord();
						String sLastRecord = oAccountSummaryReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oAccountSummaryReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oAccountSummaryReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);				
						oAccountSummaryReportBO = oAccountSummaryReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchStatement(oAccountSummaryReportForm, oAccountSummaryReportBO);
						session.setAttribute("oAccountSummaryReportBO", oAccountSummaryReportBO);

						oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
			//			oAccountSummaryReportBO = oAccountSummaryReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (sDisplayTypeAction.equals("R")){
					session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
					oAccountSummaryReportBO = oAccountSummaryReportDAO.getDisplayRecordPro(oAccountSummaryReportForm.getDisplayAction(),gsDisplayRequestLastRecord,gsRequestTotalRecord,oAccountSummaryReportForm.getDisplayRequestFrequency());
					if (oAccountSummaryReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oAccountSummaryReportBO.getDisplayOutMessage());
						String sRequestFirstRecord = oAccountSummaryReportBO.getDisplayOutFirstRecord();
						String sRequestLastRecord = oAccountSummaryReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oAccountSummaryReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oAccountSummaryReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
	
						oAccountSummaryReportBO = oAccountSummaryReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchRequest(oAccountSummaryReportForm, oAccountSummaryReportBO);
						session.setAttribute("oAccountSummaryReportBO", oAccountSummaryReportBO);
					
						oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					//	oAccountSummaryReportBO = oAccountSummaryReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oAccountSummaryReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oAccountSummaryReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountSummaryReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oAccountSummaryReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAccountSummaryReportBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
			}	
		}			
		return mapping.findForward(sSuccess);
		
	}
	public void populate(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setDateFrom(oAccountSummaryReportBO.getDateFrom());
		oAccountSummaryReportForm.setDateTo(oAccountSummaryReportBO.getDateTo());
		oAccountSummaryReportForm.setAccountNo(oAccountSummaryReportBO.getAccountNo());
		oAccountSummaryReportForm.setAccountInfo(oAccountSummaryReportBO.getAccountInfo());
		oAccountSummaryReportForm.setAccountInfoList(oAccountSummaryReportBO.getAccountInfoList());
		oAccountSummaryReportForm.setAccountInfoNameList(oAccountSummaryReportBO.getAccountInfoNameList());
		oAccountSummaryReportForm.setCustomerInfoList(oAccountSummaryReportBO.getCustomerInfoList());
		oAccountSummaryReportForm.setStatementList(oAccountSummaryReportBO.getStatementList());
		oAccountSummaryReportForm.setRequestList(oAccountSummaryReportBO.getRequestList());
		oAccountSummaryReportForm.setErrorCode(oAccountSummaryReportBO.getErrorCode());
		oAccountSummaryReportForm.setErrorMessage(oAccountSummaryReportBO.getErrorMessage());
		oAccountSummaryReportForm.setList(oAccountSummaryReportBO.getList());

	}
	public void populateMessage(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setList(oAccountSummaryReportBO.getList());
		oAccountSummaryReportForm.setErrorCode(oAccountSummaryReportBO.getErrorCode());
		oAccountSummaryReportForm.setErrorMessage(oAccountSummaryReportBO.getErrorMessage());
	}
	public void populateMenu(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setMenu(oAccountSummaryReportBO.getMenu());
		oAccountSummaryReportForm.setMenuList(oAccountSummaryReportBO.getMenuList());
		oAccountSummaryReportForm.setMenuNameList(oAccountSummaryReportBO.getMenuNameList());
	}
	public void populateSearch(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setCustomerInfoList(oAccountSummaryReportBO.getCustomerInfoList());
		oAccountSummaryReportForm.setStatementList(oAccountSummaryReportBO.getRequestList());
		oAccountSummaryReportForm.setRequestList(oAccountSummaryReportBO.getRequestList());
		oAccountSummaryReportForm.setDisplayTotalRecord(oAccountSummaryReportBO.getDisplayTotalRecord());
	}
	public void populateSearchStatement(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setStatementList(oAccountSummaryReportBO.getStatementList());
		oAccountSummaryReportForm.setDisplayTotalRecord(oAccountSummaryReportBO.getDisplayTotalRecord());
	}
	public void populateSearchRequest(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setRequestList(oAccountSummaryReportBO.getRequestList());
		oAccountSummaryReportForm.setDisplayRequestTotalRecord(oAccountSummaryReportBO.getDisplayRequestTotalRecord());
	}
	public void populateCompany(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setAccountNo(oAccountSummaryReportBO.getAccountNo());
		oAccountSummaryReportForm.setCompanyCode(oAccountSummaryReportBO.getCompanyCode());
	}
	public void populateMaxRecord(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setDisplayTotalRecord(oAccountSummaryReportBO.getDisplayTotalRecord());
	}
	public void populateMaxRecordRequest(AccountSummaryReportForm oAccountSummaryReportForm, AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setDisplayRequestTotalRecord(oAccountSummaryReportBO.getDisplayRequestTotalRecord());
	}
	public void populateFileName(AccountSummaryReportForm oAccountSummaryReportForm,AccountSummaryReportBO oAccountSummaryReportBO) {
		oAccountSummaryReportForm.setFile(oAccountSummaryReportBO.getFile());
	}
	private void clearSession(HttpSession session) {
		session.setAttribute("oAccountSummaryReportBO", null);
		session.setAttribute("oAccountSummaryReportMessageBO", null);
		session.setAttribute("GSFilePath", null);
		session.setAttribute("GSFileName", null);

	}
}
