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

import com.ibank.loan.report.bo.PDChequeStatusReportBO;
import com.ibank.loan.report.dao.PDChequeStatusReportDAO;
import com.ibank.loan.report.formbean.PDChequeStatusReportForm;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

public class PDChequeStatusReportAction extends Action {

	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)throws Exception {
		PDChequeStatusReportDAO oPDChequeStatusReportDAO = new PDChequeStatusReportDAO();
		PDChequeStatusReportBO oPDChequeStatusReportBO = new PDChequeStatusReportBO();
		PDChequeStatusReportForm oPDChequeStatusReportForm = (PDChequeStatusReportForm) form;
		PDChequeStatusReportBO oAccountSummaryReportMessageBO = new PDChequeStatusReportBO();  
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oPDChequeStatusReportForm);
		String sActionPath = "";
		HttpSession session = request.getSession(true);
		sActionPath = mapping.getPath();
		String sSuccessAction = "success";
		String sActionPathName = "";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sClientActionPath = "/ibankinguser/pDChequeStatusReport.do";
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
		
		if (sActionPath.equals("/pDChequeStatusReport")) {
			clearSession(session);
			session.setAttribute("oPDChequeStatusReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oAccountSummaryReportStatementListBO", null);
			//session.setAttribute("oPDChequeStatusReportBO", null);
			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oPDChequeStatusReportForm.setErrorCode("");
			oPDChequeStatusReportForm.setErrorMessage("");
			oPDChequeStatusReportBO=oPDChequeStatusReportDAO.getApiDetails("ibnk", request.getRemoteAddr(), gsUserID, gsUserID, "001", "I", "10");
			if(oPDChequeStatusReportBO.getErrorCode().equals("0")) {
		

			//oPDChequeStatusReportBO = oPDChequeStatusReportDAO.firstLoad(gsUserID,gsSessionID);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getAccountData(gsCustomerCode);
			oPDChequeStatusReportForm.setDateFrom(oPDChequeStatusReportBO.getDateFrom());
			oPDChequeStatusReportForm.setDateTo(oPDChequeStatusReportBO.getDateTo());
			oPDChequeStatusReportForm.setAccountInfoList(oPDChequeStatusReportBO.getAccountInfoList());
			oPDChequeStatusReportForm.setAccountInfoNameList(oPDChequeStatusReportBO.getAccountInfoNameList());
			

					//System.out.println("oPDChequeStatusReportBO.User ==>>> "+gsUserAccount);
					//	oPDChequeStatusReportForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
				oPDChequeStatusReportForm.setErrorCode("1");
				oPDChequeStatusReportForm.setErrorMessage(oPDChequeStatusReportBO.getErrorMessage());
				sSuccess = sFailureAction; 
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
			
		}
		else if (sActionPath.equals("/logOutibankPDChequeStatus")) {
			clearSession(session);
			session.setAttribute("oPDChequeStatusReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oAccountSummaryReportStatementListBO", null);
			//session.setAttribute("oPDChequeStatusReportBO", null);

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oPDChequeStatusReportForm.setErrorCode("");
			oPDChequeStatusReportForm.setErrorMessage("");
						sSuccess = sSuccessAction;
			
		}
		else if (sActionPath.equals("/accountNoAccountSummaryReportInfoEnquiry")) {
			session.setAttribute("oPDChequeStatusReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
				oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getCompanyCode(oPDChequeStatusReportForm.getAccountInfo());			
				oPDChequeStatusReportForm.setAccountNo(oPDChequeStatusReportBO.getAccountNo());
				oPDChequeStatusReportForm.setCompanyCode(oPDChequeStatusReportBO.getCompanyCode());
				oPDChequeStatusReportForm.setAccountInfo(oPDChequeStatusReportForm.getAccountInfo());
				sSuccess = sSuccessAction;
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
				  String sMessage = "";
				  sMessage = oPDChequeStatusReportBO.getErrorMessage();
				  oPDChequeStatusReportForm.setErrorCode("1");
				  oPDChequeStatusReportForm.setErrorMessage(sMessage);
			      sSuccess = sFailureAction;
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}		 
		else if (sActionPath.equals("/executePDChequeStatusReport")) {
			  session.setAttribute("oUserActivityHistoryReportMessageBO"," ");
			  oPDChequeStatusReportBO=oPDChequeStatusReportDAO.getApiDetails("ibnk", request.getRemoteAddr(), gsUserID, gsUserID, "001", "I", "04");
			  System.out.println("api resposnse code"+oPDChequeStatusReportBO.getErrorCode());
			   if (oPDChequeStatusReportBO.getErrorCode().equals("0")) {	
					oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getExecuteStatementProAPI(oPDChequeStatusReportBO.getTargetUrlApi(), gsUserID, gsSessionID, oPDChequeStatusReportForm.getAccountNo(), oPDChequeStatusReportForm.getDateFrom(), oPDChequeStatusReportForm.getDateTo());
					   if (oPDChequeStatusReportBO.getErrorCode().equals("0")) {				   	
					        	
					       	ServletContext context=request.getSession().getServletContext();
							String sImageDirectory ="/pages/images/";
							String sImagePath=context.getRealPath(sImageDirectory);
							String sDirectory ="/pages/loan/report/";
							String sSubReportPath=context.getRealPath(sDirectory);
							HashMap hParameters=new HashMap();
							hParameters.put("sUserID", gsUserID);
							hParameters.put("sSessionID", gsSessionID);
							hParameters.put("sAccountNo",oPDChequeStatusReportBO.getAccountName());
							hParameters.put("sAccountName",oPDChequeStatusReportForm.getAccountNo());
						//	hParameters.put("sServiceType", sServiceName);	
							hParameters.put("sSubReportPath", sSubReportPath+"/");
							hParameters.put("sImagePath", sImagePath+"/nbfilogo.png");
							hParameters.put("sImagePath",new File(sImagePath + "/nbfilogo.png"));
							String sFileName = sDirectory + "PDChequeStatusReport.jasper";
							ReportManager oReportManager = new ReportManager();
							oReportManager.viewReport(request, response, hParameters, sFileName);						
						     sSuccess = sSuccessAction;
							} 
					   else if (oPDChequeStatusReportBO.getErrorCode().equals("1")) {
						   System.out.println("api resposnse code"+oPDChequeStatusReportBO.getErrorCode());
							String	UserActivityHistoryReportMessageBO =oPDChequeStatusReportBO.getErrorMessage();	
							  String sMessage = "";
							  sMessage = oPDChequeStatusReportBO.getErrorMessage();
							  oPDChequeStatusReportForm.setErrorCode("1");
							  oPDChequeStatusReportForm.setErrorMessage(sMessage);
						      sSuccess = sFailureAction;
							session.setAttribute("oUserActivityHistoryReportMessageBO",UserActivityHistoryReportMessageBO);
							
							sSuccess = sFailureAction;
						}
			   }
			
					else if (oPDChequeStatusReportBO.getErrorCode().equals("1")) {
						
						String	UserActivityHistoryReportMessageBO =oPDChequeStatusReportBO.getErrorMessage();							
						session.setAttribute("oUserActivityHistoryReportMessageBO",UserActivityHistoryReportMessageBO);
						
						sSuccess = sFailureAction;
					} 
					else if (oPDChequeStatusReportBO.getErrorCode().equals("2")) {
						clearSession(session);
						sSuccess = sSessionExpireAction;
					} 
					else if (oPDChequeStatusReportBO.getErrorCode().equals("3")) {
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					} 
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}									
				} 
				
		
 
		else if (sActionPath.equals("/serachPDChequeStatusReport")){
			//oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			System.out.println("Eroor"+oPDChequeStatusReportBO.getErrorCode()+"messge"+oPDChequeStatusReportBO.getErrorMessage());
			if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
				//oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getExecuteStatementProAPI(gsUserID, gsSessionID, "", "", "");
				//oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getTransactionPreviewPro(gsUserID,gsSessionID,oPDChequeStatusReportForm.getCompanyCode(),oPDChequeStatusReportForm.getAccountNo(),oPDChequeStatusReportForm.getDateFrom(),oPDChequeStatusReportForm.getDateTo());
				if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
					ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/loan/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
					String sStatementDate ="Statement of Account for the Period : "+oPDChequeStatusReportForm.getDateFrom()+" To "+oPDChequeStatusReportForm.getDateTo();
					String sPresentDate = "Date : "+gsPresentDate;
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);
					hParameters.put("sFromDate", oPDChequeStatusReportForm.getDateFrom());
					hParameters.put("sToDate", oPDChequeStatusReportForm.getDateTo());
					hParameters.put("sStatementDate", sStatementDate);
					hParameters.put("sPresentDate", sPresentDate);
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/reportlogo.gif");
					String sFileName = sDirectory + "PDChequeStatusReport.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);
					sSuccess = sSuccessAction;
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/cancelPDChequeStatusReport")) {
			session.setAttribute("oPDChequeStatusReportMessageBO",null);
			oPDChequeStatusReportBO =oPDChequeStatusReportDAO.getMenuCheckPro(  gsUserID,
																					  gsSessionID,
																					  gsCompanyID,
																					  gsBranchID);

			if (oPDChequeStatusReportBO.getErrorCode().equals("0")) {
				clearSession(session);
				String	MobileAppsFinalApprovalMessageBO =oPDChequeStatusReportBO.getErrorMessage();							
				session.setAttribute("oPDChequeStatusReportMessageBO",MobileAppsFinalApprovalMessageBO);
				sSuccess = sSuccessAction;
			} 
			else if (oPDChequeStatusReportBO.getErrorCode().equals("1")) {
				String	MobileAppsFinalApprovalMessageBO =oPDChequeStatusReportBO.getErrorMessage();							
				session.setAttribute("oPDChequeStatusReportMessageBO",MobileAppsFinalApprovalMessageBO);
				sSuccess = sFailureAction;
			} 
			else if (oPDChequeStatusReportBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oPDChequeStatusReportBO.getErrorCode().equals("3")) {
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
			session.setAttribute("oPDChequeStatusReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO",	null);
			oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getTransactionPreviewPro1(gsUserID,gsSessionID,
					oPDChequeStatusReportForm.getCompanyCode(),
					oPDChequeStatusReportForm.getAccountNo(),
					oPDChequeStatusReportForm.getDateFrom(),
					oPDChequeStatusReportForm.getDateTo());

			if (oPDChequeStatusReportBO.getErrorCode().equals("0")) {
				oPDChequeStatusReportBO =	oPDChequeStatusReportDAO.getFileName();
				populateFileName(oPDChequeStatusReportForm,oPDChequeStatusReportBO);

				oPDChequeStatusReportBO =oPDChequeStatusReportDAO.getXLSFile(session,gsUserID,gsSessionID,
														   oPDChequeStatusReportForm.getFile());

					String sFilePath ="/mybank/AccountStatement/BalanceDetailReport/"	+ gsSessionID + "/" + oPDChequeStatusReportForm.getFile()+ ".xls";
					String sFileName = oPDChequeStatusReportForm.getFile() + ".xls";
					session.setAttribute("GSFilePath", sFilePath);
					session.setAttribute("GSFileName", sFileName);

					String sMessageCode = "";
					sMessageCode = "1";
					String sSerialNo = "";
					sSerialNo = "1";
					String sMessage = "";
					sMessage = "Please download the generated file.";
					oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO",oAccountSummaryReportMessageBO);
					sSuccess = sSuccessAction;
				} 
			else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}				
				else if (oPDChequeStatusReportBO.getErrorCode().equals("2")) {
					clearSession(session);
					sSuccess = sSessionExpireAction;
				} else if (oPDChequeStatusReportBO.getErrorCode().equals("3")) {
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				} else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
		
		
		else if (sActionPath.equals("/cancelTransactionInfoEnquiry")) {
			oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getMenuCheckPro(
											 gsUserID,
											 gsSessionID,
											 request.getRemoteAddr(),
											 sClientActionPath);	
			
			if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
				oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getClearPro(gsUserID,gsSessionID);
				if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
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
					session.setAttribute("oPDChequeStatusReportBO", null);
					session.setAttribute("oAccountSummaryReportMessageBO", null);
					session.setAttribute("processTransaction",null);
					sSuccess = sSuccessAction;
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/displayTransactionInfoEnquiry")) {
			oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getMaxRecord(gsUserID,gsSessionID);
			populateMaxRecord(oPDChequeStatusReportForm,oPDChequeStatusReportBO);
			String sRecord = oPDChequeStatusReportForm.getDisplayTotalRecord();
			if (sRecord.equals("0")){
				oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
				if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
					oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getTransactionSearchPro(gsUserID,gsSessionID,oPDChequeStatusReportForm.getCompanyCode(),oPDChequeStatusReportForm.getAccountNo(),oPDChequeStatusReportForm.getDateFrom(),oPDChequeStatusReportForm.getDateTo());
					if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
						session.setAttribute("GSDisplayTransactionInfoEnquiry","");
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getMaxRecord(gsUserID,gsSessionID);
						populateMaxRecord(oPDChequeStatusReportForm, oPDChequeStatusReportBO);
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", oPDChequeStatusReportForm.getDisplayTotalRecord());
					
						oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getDisplayRecordPro("F",oPDChequeStatusReportForm.getDisplayFrequency(),oPDChequeStatusReportForm.getDisplayTotalRecord(),oPDChequeStatusReportForm.getDisplayFrequency());
						if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
							session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
							session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

							session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oPDChequeStatusReportBO.getDisplayOutMessage());
							session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oPDChequeStatusReportBO.getDisplayOutFirstRecord());
							session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oPDChequeStatusReportBO.getDisplayOutLastRecord());
							String sFirstRecord = oPDChequeStatusReportBO.getDisplayOutFirstRecord(); 
							String sLastRecord = oPDChequeStatusReportBO.getDisplayOutLastRecord(); 
							session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);

							oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getMaxRecordRequest(gsUserID,gsSessionID);
							populateMaxRecordRequest(oPDChequeStatusReportForm, oPDChequeStatusReportBO);
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", oPDChequeStatusReportForm.getDisplayRequestTotalRecord());
							oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getDisplayRecordPro("F",oPDChequeStatusReportForm.getDisplayRequestFrequency(),oPDChequeStatusReportForm.getDisplayRequestTotalRecord(),oPDChequeStatusReportForm.getDisplayRequestFrequency());
							if (oPDChequeStatusReportBO.getErrorCode().equals("0")){
							
								session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oPDChequeStatusReportBO.getDisplayOutMessage());
								session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oPDChequeStatusReportBO.getDisplayOutFirstRecord());
								session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oPDChequeStatusReportBO.getDisplayOutLastRecord());
							
								String sRequestFirstRecord = oPDChequeStatusReportBO.getDisplayOutFirstRecord();
								String sRequestLastRecord = oPDChequeStatusReportBO.getDisplayOutLastRecord();
								session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
							
								oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getTransactionInformation(gsUserID,gsSessionID);
								populateSearch(oPDChequeStatusReportForm, oPDChequeStatusReportBO);
								session.setAttribute("oPDChequeStatusReportBO", oPDChequeStatusReportBO);
								 
								oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						//		oPDChequeStatusReportBO = oPDChequeStatusReportDAO.firstLoad(gsUserID,gsSessionID);
								sSuccess = sSuccessAction;
							}
							else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
								oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
								sSuccess = sFailureAction;
							}
							else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
								clearSession(session);
								sSuccess = sSessionExpireAction;
							}
							else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
								clearSession(session);
								sSuccess = sSessionMyBankMenuAction;
							}
							else {
								clearSession(session);
								sSuccess = sFatalErrorAction;
							}
						}	
						else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
							oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
							populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
							session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
							sSuccess = sFailureAction;
						}
						else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					}
					else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction;
					}
					else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else{
				session.setAttribute("oPDChequeStatusReportBO", null);
				String sDisplayTypeAction = oPDChequeStatusReportForm.getDisplayTypeAction();
				if (sDisplayTypeAction.equals("L")){
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getDisplayRecordPro(oPDChequeStatusReportForm.getDisplayAction(),gsDisplayLastRecord,gsDisplayTotalRecord,oPDChequeStatusReportForm.getDisplayFrequency());
					if (oPDChequeStatusReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oPDChequeStatusReportBO.getDisplayOutMessage());
						String sFirstRecord = oPDChequeStatusReportBO.getDisplayOutFirstRecord();
						String sLastRecord = oPDChequeStatusReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oPDChequeStatusReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oPDChequeStatusReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);				
						oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchStatement(oPDChequeStatusReportForm, oPDChequeStatusReportBO);
						session.setAttribute("oPDChequeStatusReportBO", oPDChequeStatusReportBO);

						oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
			//			oPDChequeStatusReportBO = oPDChequeStatusReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
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
					oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getDisplayRecordPro(oPDChequeStatusReportForm.getDisplayAction(),gsDisplayRequestLastRecord,gsRequestTotalRecord,oPDChequeStatusReportForm.getDisplayRequestFrequency());
					if (oPDChequeStatusReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oPDChequeStatusReportBO.getDisplayOutMessage());
						String sRequestFirstRecord = oPDChequeStatusReportBO.getDisplayOutFirstRecord();
						String sRequestLastRecord = oPDChequeStatusReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oPDChequeStatusReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oPDChequeStatusReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
	
						oPDChequeStatusReportBO = oPDChequeStatusReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchRequest(oPDChequeStatusReportForm, oPDChequeStatusReportBO);
						session.setAttribute("oPDChequeStatusReportBO", oPDChequeStatusReportBO);
					
						oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					//	oPDChequeStatusReportBO = oPDChequeStatusReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oPDChequeStatusReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oPDChequeStatusReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oPDChequeStatusReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oPDChequeStatusReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oPDChequeStatusReportBO.getErrorCode().equals("3")){
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
	public void populate(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setDateFrom(oPDChequeStatusReportBO.getDateFrom());
		oPDChequeStatusReportForm.setDateTo(oPDChequeStatusReportBO.getDateTo());
		oPDChequeStatusReportForm.setAccountNo(oPDChequeStatusReportBO.getAccountNo());
		oPDChequeStatusReportForm.setAccountInfo(oPDChequeStatusReportBO.getAccountInfo());
		oPDChequeStatusReportForm.setAccountInfoList(oPDChequeStatusReportBO.getAccountInfoList());
		oPDChequeStatusReportForm.setAccountInfoNameList(oPDChequeStatusReportBO.getAccountInfoNameList());
		oPDChequeStatusReportForm.setCustomerInfoList(oPDChequeStatusReportBO.getCustomerInfoList());
		oPDChequeStatusReportForm.setStatementList(oPDChequeStatusReportBO.getStatementList());
		oPDChequeStatusReportForm.setRequestList(oPDChequeStatusReportBO.getRequestList());
		oPDChequeStatusReportForm.setErrorCode(oPDChequeStatusReportBO.getErrorCode());
		oPDChequeStatusReportForm.setErrorMessage(oPDChequeStatusReportBO.getErrorMessage());
		oPDChequeStatusReportForm.setList(oPDChequeStatusReportBO.getList());

	}
	public void populateMessage(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setList(oPDChequeStatusReportBO.getList());
		oPDChequeStatusReportForm.setErrorCode(oPDChequeStatusReportBO.getErrorCode());
		oPDChequeStatusReportForm.setErrorMessage(oPDChequeStatusReportBO.getErrorMessage());
	}
	public void populateMenu(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setMenu(oPDChequeStatusReportBO.getMenu());
		oPDChequeStatusReportForm.setMenuList(oPDChequeStatusReportBO.getMenuList());
		oPDChequeStatusReportForm.setMenuNameList(oPDChequeStatusReportBO.getMenuNameList());
	}
	public void populateSearch(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setCustomerInfoList(oPDChequeStatusReportBO.getCustomerInfoList());
		oPDChequeStatusReportForm.setStatementList(oPDChequeStatusReportBO.getRequestList());
		oPDChequeStatusReportForm.setRequestList(oPDChequeStatusReportBO.getRequestList());
		oPDChequeStatusReportForm.setDisplayTotalRecord(oPDChequeStatusReportBO.getDisplayTotalRecord());
	}
	public void populateSearchStatement(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setStatementList(oPDChequeStatusReportBO.getStatementList());
		oPDChequeStatusReportForm.setDisplayTotalRecord(oPDChequeStatusReportBO.getDisplayTotalRecord());
	}
	public void populateSearchRequest(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setRequestList(oPDChequeStatusReportBO.getRequestList());
		oPDChequeStatusReportForm.setDisplayRequestTotalRecord(oPDChequeStatusReportBO.getDisplayRequestTotalRecord());
	}
	public void populateCompany(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setAccountNo(oPDChequeStatusReportBO.getAccountNo());
		oPDChequeStatusReportForm.setCompanyCode(oPDChequeStatusReportBO.getCompanyCode());
	}
	public void populateMaxRecord(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setDisplayTotalRecord(oPDChequeStatusReportBO.getDisplayTotalRecord());
	}
	public void populateMaxRecordRequest(PDChequeStatusReportForm oPDChequeStatusReportForm, PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setDisplayRequestTotalRecord(oPDChequeStatusReportBO.getDisplayRequestTotalRecord());
	}
	public void populateFileName(PDChequeStatusReportForm oPDChequeStatusReportForm,PDChequeStatusReportBO oPDChequeStatusReportBO) {
		oPDChequeStatusReportForm.setFile(oPDChequeStatusReportBO.getFile());
	}
	private void clearSession(HttpSession session) {
		session.setAttribute("oPDChequeStatusReportBO", null);
		session.setAttribute("oAccountSummaryReportMessageBO", null);
		session.setAttribute("GSFilePath", null);
		session.setAttribute("GSFileName", null);

	}
}

