package com.ibank.deposit.report.action;

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

import com.ibank.deposit.report.bo.BEFTNIncomeSchemeReportBO;
import com.ibank.deposit.report.dao.BEFTNIncomeSchemeReportDAO;
import com.ibank.deposit.report.formbean.BEFTNIncomeSchemeReportForm;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

public class BEFTNIncomeSchemeReportAction extends Action {

	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)throws Exception {
		BEFTNIncomeSchemeReportDAO oBEFTNIncomeSchemeReportDAO = new BEFTNIncomeSchemeReportDAO();
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO = new BEFTNIncomeSchemeReportBO();
		BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm = (BEFTNIncomeSchemeReportForm) form;
		BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportMessageBO = new BEFTNIncomeSchemeReportBO();  
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oBEFTNIncomeSchemeReportForm);
		String sActionPath = "";
		HttpSession session = request.getSession(true);
		sActionPath = mapping.getPath();
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sClientActionPath = "/ibankinguser/bEFTNIncomeSchemeReport.do";
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
		
		if (sActionPath.equals("/bEFTNIncomeSchemeReport")) {
			clearSession(session);
			session.setAttribute("oBEFTNIncomeSchemeReportBO", null);
			session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oBEFTNIncomeSchemeReportStatementListBO", null);
			//session.setAttribute("oBEFTNIncomeSchemeReportBO", null);

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oBEFTNIncomeSchemeReportForm.setErrorCode("");
			oBEFTNIncomeSchemeReportForm.setErrorMessage("");
			oBEFTNIncomeSchemeReportBO=oBEFTNIncomeSchemeReportDAO.getApiDetails("ibnk", request.getRemoteAddr(), gsUserID, gsUserID, "001", "I", "10");
			if(oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")) {
		

			//oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.firstLoad(gsUserID,gsSessionID);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getAccountData(gsCustomerCode);
			oBEFTNIncomeSchemeReportForm.setDateFrom(oBEFTNIncomeSchemeReportBO.getDateFrom());
			oBEFTNIncomeSchemeReportForm.setDateTo(oBEFTNIncomeSchemeReportBO.getDateTo());
			oBEFTNIncomeSchemeReportForm.setAccountInfoList(oBEFTNIncomeSchemeReportBO.getAccountInfoList());
			oBEFTNIncomeSchemeReportForm.setAccountInfoNameList(oBEFTNIncomeSchemeReportBO.getAccountInfoNameList());
			

					//System.out.println("oBEFTNIncomeSchemeReportBO.User ==>>> "+gsUserAccount);
					//	oBEFTNIncomeSchemeReportForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
				oBEFTNIncomeSchemeReportForm.setErrorCode("1");
				oBEFTNIncomeSchemeReportForm.setErrorMessage(oBEFTNIncomeSchemeReportBO.getErrorMessage());
				sSuccess = sFailureAction; 
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
			
		}
		else if (sActionPath.equals("/logOutibankBEFTNIncomeScheme")) {
			clearSession(session);
			session.setAttribute("oBEFTNIncomeSchemeReportBO", null);
			session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oBEFTNIncomeSchemeReportStatementListBO", null);
			//session.setAttribute("oBEFTNIncomeSchemeReportBO", null);

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oBEFTNIncomeSchemeReportForm.setErrorCode("");
			oBEFTNIncomeSchemeReportForm.setErrorMessage("");

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oBEFTNIncomeSchemeReportForm.setErrorCode("");
			oBEFTNIncomeSchemeReportForm.setErrorMessage("");
;

					//System.out.println("oBEFTNIncomeSchemeReportBO.User ==>>> "+gsUserAccount);
					//	oBEFTNIncomeSchemeReportForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
			
		}
		
		else if (sActionPath.equals("/accountNoBEFTNIncomeSchemeReportInfoEnquiry")) {
			session.setAttribute("oBEFTNIncomeSchemeReportBO", null);
			session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", null);
			oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
				oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getCompanyCode(oBEFTNIncomeSchemeReportForm.getAccountInfo());			
				oBEFTNIncomeSchemeReportForm.setAccountNo(oBEFTNIncomeSchemeReportBO.getAccountNo());
				oBEFTNIncomeSchemeReportForm.setCompanyCode(oBEFTNIncomeSchemeReportBO.getCompanyCode());
				oBEFTNIncomeSchemeReportForm.setAccountInfo(oBEFTNIncomeSchemeReportForm.getAccountInfo());
				sSuccess = sSuccessAction;
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
				
				populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
				session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
				sSuccess = sFailureAction;
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}		 
		else if (sActionPath.equals("/executeBEFTNIncomeSchemeReport")) {
			  session.setAttribute("oUserActivityHistoryReportMessageBO"," ");
				System.out.print("I am in");
				oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getApiDetails("ibnk",request.getRemoteAddr(), gsUserID, gsSessionID, "001", "I", "08");
				System.out.println("api resposnse code"+oBEFTNIncomeSchemeReportBO.getErrorCode());
				   if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")) {	
					   oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getExecuteStatementProAPI(oBEFTNIncomeSchemeReportBO.getTargetUrlApi(), gsUserID, gsSessionID, oBEFTNIncomeSchemeReportForm.getAccountNo(), oBEFTNIncomeSchemeReportForm.getDateFrom(), oBEFTNIncomeSchemeReportForm.getDateTo());											 
			   if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")) {				   	
			        	
			       	ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/deposit/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
				
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);			
					hParameters.put("sAccountNo",oBEFTNIncomeSchemeReportForm.getAccountNo());
				//	hParameters.put("sServiceType", sServiceName);	
					hParameters.put("sBeftn_Branch", oBEFTNIncomeSchemeReportBO.getBeftnName());
					hParameters.put("sBeftn_Product", oBEFTNIncomeSchemeReportBO.getBeftnProduct());	
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/nbfilogo.png");
					hParameters.put("sImagePath",new File(sImagePath + "/nbfilogo.png"));
					String sFileName = sDirectory + "BEFTNIncomeSchemeReport.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);						

				     sSuccess = sSuccessAction;
					} 
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")) {
						 String sMessage = "";
						  sMessage = oBEFTNIncomeSchemeReportBO.getErrorMessage();
						  oBEFTNIncomeSchemeReportForm.setErrorCode("1");
						  oBEFTNIncomeSchemeReportForm.setErrorMessage(sMessage);
						oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
						String	UserActivityHistoryReportMessageBO =oBEFTNIncomeSchemeReportBO.getErrorMessage();							
						session.setAttribute("oUserActivityHistoryReportMessageBO",UserActivityHistoryReportMessageBO);
						
						sSuccess = sFailureAction;
					} 
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")) {
						clearSession(session);
						sSuccess = sSessionExpireAction;
					} 
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")) {
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					} 
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}	
				   }
											
				} 
				
		else if (sActionPath.equals("/searchTransactionInfoEnquiry")){
				oBEFTNIncomeSchemeReportForm.setErrorCode("");
				oBEFTNIncomeSchemeReportForm.setErrorMessage("");
				session.setAttribute("oBEFTNIncomeSchemeReportStatementListBO", null);
			
		
			 System.out.println("oIBankingUserLogInBO.getErrorCode() ===>>>>>> " + oBEFTNIncomeSchemeReportBO.getErrorCode());
			 
			 if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")) {
				 
				 oBEFTNIncomeSchemeReportBO.setStatementList(oBEFTNIncomeSchemeReportBO.getStatementList());				 
				  session.setAttribute("oBEFTNIncomeSchemeReportStatementListBO", oBEFTNIncomeSchemeReportBO);
				  sSuccess = sSuccessAction;
			 } else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")) {
				
				  String sMessage = "";
				  sMessage = oBEFTNIncomeSchemeReportBO.getErrorMessage();
				  oBEFTNIncomeSchemeReportForm.setErrorCode("1");
				  oBEFTNIncomeSchemeReportForm.setErrorMessage(sMessage);
			      sSuccess = sFailureAction;
			
			}else {
				
				oBEFTNIncomeSchemeReportForm.setStatementList(oBEFTNIncomeSchemeReportBO.getStatementList());
				session.setAttribute("oIBankingUserLogInListBO",oBEFTNIncomeSchemeReportBO);						
				sSuccess = sSuccessAction;
			}
	
		
		}  
		else if (sActionPath.equals("/ReportTransactionStatement")){
			oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
				oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getTransactionPreviewPro(gsUserID,gsSessionID,oBEFTNIncomeSchemeReportForm.getCompanyCode(),oBEFTNIncomeSchemeReportForm.getAccountNo(),oBEFTNIncomeSchemeReportForm.getDateFrom(),oBEFTNIncomeSchemeReportForm.getDateTo());
				if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
					ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/accountprofile/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
					String sStatementDate ="Statement of Account for the Period : "+oBEFTNIncomeSchemeReportForm.getDateFrom()+" To "+oBEFTNIncomeSchemeReportForm.getDateTo();
					String sPresentDate = "Date : "+gsPresentDate;
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);
					hParameters.put("sFromDate", oBEFTNIncomeSchemeReportForm.getDateFrom());
					hParameters.put("sToDate", oBEFTNIncomeSchemeReportForm.getDateTo());
					hParameters.put("sStatementDate", sStatementDate);
					hParameters.put("sPresentDate", sPresentDate);
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/reportlogo.gif");
					String sFileName = sDirectory + "Transaction.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);
					sSuccess = sSuccessAction;
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
					oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
					session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
				oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
				session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
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
			session.setAttribute("oBEFTNIncomeSchemeReportBO", null);
			session.setAttribute("oBEFTNIncomeSchemeReportMessageBO",	null);
			oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getTransactionPreviewPro1(gsUserID,gsSessionID,
					oBEFTNIncomeSchemeReportForm.getCompanyCode(),
					oBEFTNIncomeSchemeReportForm.getAccountNo(),
					oBEFTNIncomeSchemeReportForm.getDateFrom(),
					oBEFTNIncomeSchemeReportForm.getDateTo());

			if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")) {
				oBEFTNIncomeSchemeReportBO =	oBEFTNIncomeSchemeReportDAO.getFileName();
				populateFileName(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportBO);

				oBEFTNIncomeSchemeReportBO =oBEFTNIncomeSchemeReportDAO.getXLSFile(session,gsUserID,gsSessionID,
														   oBEFTNIncomeSchemeReportForm.getFile());

					String sFilePath ="/mybank/AccountStatement/BalanceDetailReport/"	+ gsSessionID + "/" + oBEFTNIncomeSchemeReportForm.getFile()+ ".xls";
					String sFileName = oBEFTNIncomeSchemeReportForm.getFile() + ".xls";
					session.setAttribute("GSFilePath", sFilePath);
					session.setAttribute("GSFileName", sFileName);

					String sMessageCode = "";
					sMessageCode = "1";
					String sSerialNo = "";
					sSerialNo = "1";
					String sMessage = "";
					sMessage = "Please download the generated file.";
					oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
					session.setAttribute("oBEFTNIncomeSchemeReportMessageBO",oBEFTNIncomeSchemeReportMessageBO);
					sSuccess = sSuccessAction;
				} 
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
						oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
						session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
						sSuccess = sFailureAction; 
					}				
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")) {
					clearSession(session);
					sSuccess = sSessionExpireAction;
				} else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")) {
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				} else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
		
		
		else if (sActionPath.equals("/cancelTransactionInfoEnquiry")) {
			oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getMenuCheckPro(
											 gsUserID,
											 gsSessionID,
											 request.getRemoteAddr(),
											 sClientActionPath);	
			
			if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
				oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getClearPro(gsUserID,gsSessionID);
				if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
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
					session.setAttribute("oBEFTNIncomeSchemeReportBO", null);
					session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", null);
					session.setAttribute("processTransaction",null);
					sSuccess = sSuccessAction;
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
					oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
					session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
				oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
				session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/displayTransactionInfoEnquiry")) {
			oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getMaxRecord(gsUserID,gsSessionID);
			populateMaxRecord(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportBO);
			String sRecord = oBEFTNIncomeSchemeReportForm.getDisplayTotalRecord();
			if (sRecord.equals("0")){
				oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
				if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
					oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getTransactionSearchPro(gsUserID,gsSessionID,oBEFTNIncomeSchemeReportForm.getCompanyCode(),oBEFTNIncomeSchemeReportForm.getAccountNo(),oBEFTNIncomeSchemeReportForm.getDateFrom(),oBEFTNIncomeSchemeReportForm.getDateTo());
					if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
						session.setAttribute("GSDisplayTransactionInfoEnquiry","");
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getMaxRecord(gsUserID,gsSessionID);
						populateMaxRecord(oBEFTNIncomeSchemeReportForm, oBEFTNIncomeSchemeReportBO);
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", oBEFTNIncomeSchemeReportForm.getDisplayTotalRecord());
					
						oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getDisplayRecordPro("F",oBEFTNIncomeSchemeReportForm.getDisplayFrequency(),oBEFTNIncomeSchemeReportForm.getDisplayTotalRecord(),oBEFTNIncomeSchemeReportForm.getDisplayFrequency());
						if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
							session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
							session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

							session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oBEFTNIncomeSchemeReportBO.getDisplayOutMessage());
							session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oBEFTNIncomeSchemeReportBO.getDisplayOutFirstRecord());
							session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oBEFTNIncomeSchemeReportBO.getDisplayOutLastRecord());
							String sFirstRecord = oBEFTNIncomeSchemeReportBO.getDisplayOutFirstRecord(); 
							String sLastRecord = oBEFTNIncomeSchemeReportBO.getDisplayOutLastRecord(); 
							session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);

							oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getMaxRecordRequest(gsUserID,gsSessionID);
							populateMaxRecordRequest(oBEFTNIncomeSchemeReportForm, oBEFTNIncomeSchemeReportBO);
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", oBEFTNIncomeSchemeReportForm.getDisplayRequestTotalRecord());
							oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getDisplayRecordPro("F",oBEFTNIncomeSchemeReportForm.getDisplayRequestFrequency(),oBEFTNIncomeSchemeReportForm.getDisplayRequestTotalRecord(),oBEFTNIncomeSchemeReportForm.getDisplayRequestFrequency());
							if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){
							
								session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oBEFTNIncomeSchemeReportBO.getDisplayOutMessage());
								session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oBEFTNIncomeSchemeReportBO.getDisplayOutFirstRecord());
								session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oBEFTNIncomeSchemeReportBO.getDisplayOutLastRecord());
							
								String sRequestFirstRecord = oBEFTNIncomeSchemeReportBO.getDisplayOutFirstRecord();
								String sRequestLastRecord = oBEFTNIncomeSchemeReportBO.getDisplayOutLastRecord();
								session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
							
								oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getTransactionInformation(gsUserID,gsSessionID);
								populateSearch(oBEFTNIncomeSchemeReportForm, oBEFTNIncomeSchemeReportBO);
								session.setAttribute("oBEFTNIncomeSchemeReportBO", oBEFTNIncomeSchemeReportBO);
								 
								oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
								session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
						//		oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.firstLoad(gsUserID,gsSessionID);
								sSuccess = sSuccessAction;
							}
							else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
								oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
								session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
								sSuccess = sFailureAction;
							}
							else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
								clearSession(session);
								sSuccess = sSessionExpireAction;
							}
							else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
								clearSession(session);
								sSuccess = sSessionMyBankMenuAction;
							}
							else {
								clearSession(session);
								sSuccess = sFatalErrorAction;
							}
						}	
						else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
							oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
							populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
							session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
							sSuccess = sFailureAction;
						}
						else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					}
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
						oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
						session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
						sSuccess = sFailureAction;
					}
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
					oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
					session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else{
				session.setAttribute("oBEFTNIncomeSchemeReportBO", null);
				String sDisplayTypeAction = oBEFTNIncomeSchemeReportForm.getDisplayTypeAction();
				if (sDisplayTypeAction.equals("L")){
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getDisplayRecordPro(oBEFTNIncomeSchemeReportForm.getDisplayAction(),gsDisplayLastRecord,gsDisplayTotalRecord,oBEFTNIncomeSchemeReportForm.getDisplayFrequency());
					if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oBEFTNIncomeSchemeReportBO.getDisplayOutMessage());
						String sFirstRecord = oBEFTNIncomeSchemeReportBO.getDisplayOutFirstRecord();
						String sLastRecord = oBEFTNIncomeSchemeReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oBEFTNIncomeSchemeReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oBEFTNIncomeSchemeReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);				
						oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchStatement(oBEFTNIncomeSchemeReportForm, oBEFTNIncomeSchemeReportBO);
						session.setAttribute("oBEFTNIncomeSchemeReportBO", oBEFTNIncomeSchemeReportBO);

						oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
						session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
			//			oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
						session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
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
					oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getDisplayRecordPro(oBEFTNIncomeSchemeReportForm.getDisplayAction(),gsDisplayRequestLastRecord,gsRequestTotalRecord,oBEFTNIncomeSchemeReportForm.getDisplayRequestFrequency());
					if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oBEFTNIncomeSchemeReportBO.getDisplayOutMessage());
						String sRequestFirstRecord = oBEFTNIncomeSchemeReportBO.getDisplayOutFirstRecord();
						String sRequestLastRecord = oBEFTNIncomeSchemeReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oBEFTNIncomeSchemeReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oBEFTNIncomeSchemeReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
	
						oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchRequest(oBEFTNIncomeSchemeReportForm, oBEFTNIncomeSchemeReportBO);
						session.setAttribute("oBEFTNIncomeSchemeReportBO", oBEFTNIncomeSchemeReportBO);
					
						oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
						session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
					//	oBEFTNIncomeSchemeReportBO = oBEFTNIncomeSchemeReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

						oBEFTNIncomeSchemeReportMessageBO = oBEFTNIncomeSchemeReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oBEFTNIncomeSchemeReportForm,oBEFTNIncomeSchemeReportMessageBO);
						session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", oBEFTNIncomeSchemeReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oBEFTNIncomeSchemeReportBO.getErrorCode().equals("3")){
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
	public void populate(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setDateFrom(oBEFTNIncomeSchemeReportBO.getDateFrom());
		oBEFTNIncomeSchemeReportForm.setDateTo(oBEFTNIncomeSchemeReportBO.getDateTo());
		oBEFTNIncomeSchemeReportForm.setAccountNo(oBEFTNIncomeSchemeReportBO.getAccountNo());
		oBEFTNIncomeSchemeReportForm.setAccountInfo(oBEFTNIncomeSchemeReportBO.getAccountInfo());
		oBEFTNIncomeSchemeReportForm.setAccountInfoList(oBEFTNIncomeSchemeReportBO.getAccountInfoList());
		oBEFTNIncomeSchemeReportForm.setAccountInfoNameList(oBEFTNIncomeSchemeReportBO.getAccountInfoNameList());
		oBEFTNIncomeSchemeReportForm.setCustomerInfoList(oBEFTNIncomeSchemeReportBO.getCustomerInfoList());
		oBEFTNIncomeSchemeReportForm.setStatementList(oBEFTNIncomeSchemeReportBO.getStatementList());
		oBEFTNIncomeSchemeReportForm.setRequestList(oBEFTNIncomeSchemeReportBO.getRequestList());
		oBEFTNIncomeSchemeReportForm.setErrorCode(oBEFTNIncomeSchemeReportBO.getErrorCode());
		oBEFTNIncomeSchemeReportForm.setErrorMessage(oBEFTNIncomeSchemeReportBO.getErrorMessage());
		oBEFTNIncomeSchemeReportForm.setList(oBEFTNIncomeSchemeReportBO.getList());

	}
	public void populateMessage(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setList(oBEFTNIncomeSchemeReportBO.getList());
		oBEFTNIncomeSchemeReportForm.setErrorCode(oBEFTNIncomeSchemeReportBO.getErrorCode());
		oBEFTNIncomeSchemeReportForm.setErrorMessage(oBEFTNIncomeSchemeReportBO.getErrorMessage());
	}
	public void populateMenu(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setMenu(oBEFTNIncomeSchemeReportBO.getMenu());
		oBEFTNIncomeSchemeReportForm.setMenuList(oBEFTNIncomeSchemeReportBO.getMenuList());
		oBEFTNIncomeSchemeReportForm.setMenuNameList(oBEFTNIncomeSchemeReportBO.getMenuNameList());
	}
	public void populateSearch(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setCustomerInfoList(oBEFTNIncomeSchemeReportBO.getCustomerInfoList());
		oBEFTNIncomeSchemeReportForm.setStatementList(oBEFTNIncomeSchemeReportBO.getRequestList());
		oBEFTNIncomeSchemeReportForm.setRequestList(oBEFTNIncomeSchemeReportBO.getRequestList());
		oBEFTNIncomeSchemeReportForm.setDisplayTotalRecord(oBEFTNIncomeSchemeReportBO.getDisplayTotalRecord());
	}
	public void populateSearchStatement(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setStatementList(oBEFTNIncomeSchemeReportBO.getStatementList());
		oBEFTNIncomeSchemeReportForm.setDisplayTotalRecord(oBEFTNIncomeSchemeReportBO.getDisplayTotalRecord());
	}
	public void populateSearchRequest(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setRequestList(oBEFTNIncomeSchemeReportBO.getRequestList());
		oBEFTNIncomeSchemeReportForm.setDisplayRequestTotalRecord(oBEFTNIncomeSchemeReportBO.getDisplayRequestTotalRecord());
	}
	public void populateCompany(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setAccountNo(oBEFTNIncomeSchemeReportBO.getAccountNo());
		oBEFTNIncomeSchemeReportForm.setCompanyCode(oBEFTNIncomeSchemeReportBO.getCompanyCode());
	}
	public void populateMaxRecord(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setDisplayTotalRecord(oBEFTNIncomeSchemeReportBO.getDisplayTotalRecord());
	}
	public void populateMaxRecordRequest(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm, BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setDisplayRequestTotalRecord(oBEFTNIncomeSchemeReportBO.getDisplayRequestTotalRecord());
	}
	public void populateFileName(BEFTNIncomeSchemeReportForm oBEFTNIncomeSchemeReportForm,BEFTNIncomeSchemeReportBO oBEFTNIncomeSchemeReportBO) {
		oBEFTNIncomeSchemeReportForm.setFile(oBEFTNIncomeSchemeReportBO.getFile());
	}
	private void clearSession(HttpSession session) {
		session.setAttribute("oBEFTNIncomeSchemeReportBO", null);
		session.setAttribute("oBEFTNIncomeSchemeReportMessageBO", null);
		session.setAttribute("GSFilePath", null);
		session.setAttribute("GSFileName", null);

	}
}

