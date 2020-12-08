package com.ibank.loan.report.action;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibank.loan.report.bo.LoanAgainstDepositStatusBO;
import com.ibank.loan.report.dao.LoanAgainstDepositStatusDAO;
import com.ibank.loan.report.formbean.LoanAgainstDepositStatusForm;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

public class LoanAgainstDepositStatusAction extends Action {

	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)throws Exception {
		LoanAgainstDepositStatusDAO oLoanAgainstDepositStatusDAO = new LoanAgainstDepositStatusDAO();
		LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO = new LoanAgainstDepositStatusBO();
		LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm = (LoanAgainstDepositStatusForm) form;
		LoanAgainstDepositStatusBO oAccountSummaryReportMessageBO = new LoanAgainstDepositStatusBO();  
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oLoanAgainstDepositStatusForm);
		String sActionPath = "";
		HttpSession session = request.getSession(true);
		sActionPath = mapping.getPath();
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sClientActionPath = "/ibankinguser/loanAgainstDepositStatusReport.do";
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
		
		if (sActionPath.equals("/loanAgainstDepositStatusReport")) {
			clearSession(session);
			session.setAttribute("oLoanAgainstDepositStatusBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oAccountSummaryReportStatementListBO", null);
			//session.setAttribute("oLoanAgainstDepositStatusBO", null);

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oLoanAgainstDepositStatusForm.setErrorCode("");
			oLoanAgainstDepositStatusForm.setErrorMessage("");

		//	oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.firstLoad(gsUserID,gsSessionID);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
																				   gsUserID,gsSessionID,gsCompanyID,
																				   "I","10");
			
 			if(oLoanAgainstDepositStatusBO.getErrorCode().equals("0")) {
			oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getAccountData(gsCustomerCode,oLoanAgainstDepositStatusBO.getTagetUrl());
			oLoanAgainstDepositStatusForm.setAsOnDate(oLoanAgainstDepositStatusBO.getDateFrom());
			oLoanAgainstDepositStatusForm.setAccountInfoList(oLoanAgainstDepositStatusBO.getAccountInfoList());
			oLoanAgainstDepositStatusForm.setAccountInfoNameList(oLoanAgainstDepositStatusBO.getAccountInfoNameList());

					//System.out.println("oLoanAgainstDepositStatusBO.User ==>>> "+gsUserAccount);
					//	oLoanAgainstDepositStatusForm.setAccountInfo(gsUserAccount);
				sSuccess = sSuccessAction;
 			}else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
 					oLoanAgainstDepositStatusForm.setErrorCode("1");
 					oLoanAgainstDepositStatusForm.setErrorMessage(oLoanAgainstDepositStatusBO.getErrorMessage());
					sSuccess = sFailureAction;
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}					
			
		}	 
		
	
		else if (sActionPath.equals("/ReportLoanAgainstDepositStatus")){
			oLoanAgainstDepositStatusForm.setErrorCode("");
			oLoanAgainstDepositStatusForm.setErrorMessage("");
			oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
																		   gsUserID,gsSessionID,gsCompanyID,
																		   "I","05");
			
			if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){
				
				oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.ReportTransactionStatement(  gsUserID,gsSessionID,
																										oLoanAgainstDepositStatusForm.getAccountNo(),
																										oLoanAgainstDepositStatusForm.getAsOnDate(),
																										oLoanAgainstDepositStatusBO.getTagetUrl());
				if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){
					ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/loan/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
					String sStatementDate ="Statement of Account for As On Date : "+oLoanAgainstDepositStatusForm.getAsOnDate();
					String sPresentDate = "Date : "+gsPresentDate;
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);
					hParameters.put("sAccountNo", oLoanAgainstDepositStatusForm.getAccountNo());
					hParameters.put("sToDate", oLoanAgainstDepositStatusForm.getAsOnDate());
					hParameters.put("sStatementDate", sStatementDate);
					hParameters.put("sPresentDate", sPresentDate);
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/nbfilogo.gif");
					String sFileName = sDirectory + "LoanAgainstDepositStatusReport.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);
					sSuccess = sSuccessAction;
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
						oLoanAgainstDepositStatusForm.setErrorCode("1");
						oLoanAgainstDepositStatusForm.setErrorMessage(oLoanAgainstDepositStatusBO.getErrorMessage());
					sSuccess = sFailureAction; 
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/logOutibank")) {
			oLoanAgainstDepositStatusForm.setErrorCode("");
	     sSuccess = sSuccessAction;
}
		
		
		else if (sActionPath.equals("/cancelTransactionInfoEnquiry")) {
			oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getMenuCheckPro(
											 gsUserID,
											 gsSessionID,
											 request.getRemoteAddr(),
											 sClientActionPath);	
			
			if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){
				oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getClearPro(gsUserID,gsSessionID);
				if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){
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
					session.setAttribute("oLoanAgainstDepositStatusBO", null);
					session.setAttribute("oAccountSummaryReportMessageBO", null);
					session.setAttribute("processTransaction",null);
					sSuccess = sSuccessAction;
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/displayTransactionInfoEnquiry")) {
			oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getMaxRecord(gsUserID,gsSessionID);
			populateMaxRecord(oLoanAgainstDepositStatusForm,oLoanAgainstDepositStatusBO);
			String sRecord = oLoanAgainstDepositStatusForm.getDisplayTotalRecord();
			if (sRecord.equals("0")){
				oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getSessionCheckPro(gsUserID,gsSessionID);
				if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){
					oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getTransactionSearchPro(gsUserID,gsSessionID,oLoanAgainstDepositStatusForm.getCompanyCode(),oLoanAgainstDepositStatusForm.getAccountNo(),oLoanAgainstDepositStatusForm.getDateFrom(),oLoanAgainstDepositStatusForm.getDateTo());
					if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){
						session.setAttribute("GSDisplayTransactionInfoEnquiry","");
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getMaxRecord(gsUserID,gsSessionID);
						populateMaxRecord(oLoanAgainstDepositStatusForm, oLoanAgainstDepositStatusBO);
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", oLoanAgainstDepositStatusForm.getDisplayTotalRecord());
					
						oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getDisplayRecordPro("F",oLoanAgainstDepositStatusForm.getDisplayFrequency(),oLoanAgainstDepositStatusForm.getDisplayTotalRecord(),oLoanAgainstDepositStatusForm.getDisplayFrequency());
						if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){
							session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
							session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

							session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oLoanAgainstDepositStatusBO.getDisplayOutMessage());
							session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oLoanAgainstDepositStatusBO.getDisplayOutFirstRecord());
							session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oLoanAgainstDepositStatusBO.getDisplayOutLastRecord());
							String sFirstRecord = oLoanAgainstDepositStatusBO.getDisplayOutFirstRecord(); 
							String sLastRecord = oLoanAgainstDepositStatusBO.getDisplayOutLastRecord(); 
							session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);

							oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getMaxRecordRequest(gsUserID,gsSessionID);
							populateMaxRecordRequest(oLoanAgainstDepositStatusForm, oLoanAgainstDepositStatusBO);
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", oLoanAgainstDepositStatusForm.getDisplayRequestTotalRecord());
							oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getDisplayRecordPro("F",oLoanAgainstDepositStatusForm.getDisplayRequestFrequency(),oLoanAgainstDepositStatusForm.getDisplayRequestTotalRecord(),oLoanAgainstDepositStatusForm.getDisplayRequestFrequency());
							if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){
							
								session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oLoanAgainstDepositStatusBO.getDisplayOutMessage());
								session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oLoanAgainstDepositStatusBO.getDisplayOutFirstRecord());
								session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oLoanAgainstDepositStatusBO.getDisplayOutLastRecord());
							
								String sRequestFirstRecord = oLoanAgainstDepositStatusBO.getDisplayOutFirstRecord();
								String sRequestLastRecord = oLoanAgainstDepositStatusBO.getDisplayOutLastRecord();
								session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
							
								oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getTransactionInformation(gsUserID,gsSessionID);
								populateSearch(oLoanAgainstDepositStatusForm, oLoanAgainstDepositStatusBO);
								session.setAttribute("oLoanAgainstDepositStatusBO", oLoanAgainstDepositStatusBO);
								 
								oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						//		oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.firstLoad(gsUserID,gsSessionID);
								sSuccess = sSuccessAction;
							}
							else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
								oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
								sSuccess = sFailureAction;
							}
							else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
								clearSession(session);
								sSuccess = sSessionExpireAction;
							}
							else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
								clearSession(session);
								sSuccess = sSessionMyBankMenuAction;
							}
							else {
								clearSession(session);
								sSuccess = sFatalErrorAction;
							}
						}	
						else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
							oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
							populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
							session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
							sSuccess = sFailureAction;
						}
						else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					}
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction;
					}
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else{
				session.setAttribute("oLoanAgainstDepositStatusBO", null);
				String sDisplayTypeAction = oLoanAgainstDepositStatusForm.getDisplayTypeAction();
				if (sDisplayTypeAction.equals("L")){
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getDisplayRecordPro(oLoanAgainstDepositStatusForm.getDisplayAction(),gsDisplayLastRecord,gsDisplayTotalRecord,oLoanAgainstDepositStatusForm.getDisplayFrequency());
					if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oLoanAgainstDepositStatusBO.getDisplayOutMessage());
						String sFirstRecord = oLoanAgainstDepositStatusBO.getDisplayOutFirstRecord();
						String sLastRecord = oLoanAgainstDepositStatusBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oLoanAgainstDepositStatusBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oLoanAgainstDepositStatusBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);				
						oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchStatement(oLoanAgainstDepositStatusForm, oLoanAgainstDepositStatusBO);
						session.setAttribute("oLoanAgainstDepositStatusBO", oLoanAgainstDepositStatusBO);

						oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
			//			oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
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
					oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getDisplayRecordPro(oLoanAgainstDepositStatusForm.getDisplayAction(),gsDisplayRequestLastRecord,gsRequestTotalRecord,oLoanAgainstDepositStatusForm.getDisplayRequestFrequency());
					if (oLoanAgainstDepositStatusBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oLoanAgainstDepositStatusBO.getDisplayOutMessage());
						String sRequestFirstRecord = oLoanAgainstDepositStatusBO.getDisplayOutFirstRecord();
						String sRequestLastRecord = oLoanAgainstDepositStatusBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oLoanAgainstDepositStatusBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oLoanAgainstDepositStatusBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
	
						oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchRequest(oLoanAgainstDepositStatusForm, oLoanAgainstDepositStatusBO);
						session.setAttribute("oLoanAgainstDepositStatusBO", oLoanAgainstDepositStatusBO);
					
						oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					//	oLoanAgainstDepositStatusBO = oLoanAgainstDepositStatusDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oLoanAgainstDepositStatusDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oLoanAgainstDepositStatusForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oLoanAgainstDepositStatusBO.getErrorCode().equals("3")){
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
	public void populate(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setDateFrom(oLoanAgainstDepositStatusBO.getDateFrom());
		oLoanAgainstDepositStatusForm.setDateTo(oLoanAgainstDepositStatusBO.getDateTo());
		oLoanAgainstDepositStatusForm.setAccountNo(oLoanAgainstDepositStatusBO.getAccountNo());
		oLoanAgainstDepositStatusForm.setAccountInfo(oLoanAgainstDepositStatusBO.getAccountInfo());
		oLoanAgainstDepositStatusForm.setAccountInfoList(oLoanAgainstDepositStatusBO.getAccountInfoList());
		oLoanAgainstDepositStatusForm.setAccountInfoNameList(oLoanAgainstDepositStatusBO.getAccountInfoNameList());
		oLoanAgainstDepositStatusForm.setCustomerInfoList(oLoanAgainstDepositStatusBO.getCustomerInfoList());
		oLoanAgainstDepositStatusForm.setStatementList(oLoanAgainstDepositStatusBO.getStatementList());
		oLoanAgainstDepositStatusForm.setRequestList(oLoanAgainstDepositStatusBO.getRequestList());
		oLoanAgainstDepositStatusForm.setErrorCode(oLoanAgainstDepositStatusBO.getErrorCode());
		oLoanAgainstDepositStatusForm.setErrorMessage(oLoanAgainstDepositStatusBO.getErrorMessage());
		oLoanAgainstDepositStatusForm.setList(oLoanAgainstDepositStatusBO.getList());

	}
	public void populateMessage(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setList(oLoanAgainstDepositStatusBO.getList());
		oLoanAgainstDepositStatusForm.setErrorCode(oLoanAgainstDepositStatusBO.getErrorCode());
		oLoanAgainstDepositStatusForm.setErrorMessage(oLoanAgainstDepositStatusBO.getErrorMessage());
	}
	public void populateMenu(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setMenu(oLoanAgainstDepositStatusBO.getMenu());
		oLoanAgainstDepositStatusForm.setMenuList(oLoanAgainstDepositStatusBO.getMenuList());
		oLoanAgainstDepositStatusForm.setMenuNameList(oLoanAgainstDepositStatusBO.getMenuNameList());
	}
	public void populateSearch(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setCustomerInfoList(oLoanAgainstDepositStatusBO.getCustomerInfoList());
		oLoanAgainstDepositStatusForm.setStatementList(oLoanAgainstDepositStatusBO.getRequestList());
		oLoanAgainstDepositStatusForm.setRequestList(oLoanAgainstDepositStatusBO.getRequestList());
		oLoanAgainstDepositStatusForm.setDisplayTotalRecord(oLoanAgainstDepositStatusBO.getDisplayTotalRecord());
	}
	public void populateSearchStatement(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setStatementList(oLoanAgainstDepositStatusBO.getStatementList());
		oLoanAgainstDepositStatusForm.setDisplayTotalRecord(oLoanAgainstDepositStatusBO.getDisplayTotalRecord());
	}
	public void populateSearchRequest(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setRequestList(oLoanAgainstDepositStatusBO.getRequestList());
		oLoanAgainstDepositStatusForm.setDisplayRequestTotalRecord(oLoanAgainstDepositStatusBO.getDisplayRequestTotalRecord());
	}
	public void populateCompany(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setAccountNo(oLoanAgainstDepositStatusBO.getAccountNo());
		oLoanAgainstDepositStatusForm.setCompanyCode(oLoanAgainstDepositStatusBO.getCompanyCode());
	}
	public void populateMaxRecord(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setDisplayTotalRecord(oLoanAgainstDepositStatusBO.getDisplayTotalRecord());
	}
	public void populateMaxRecordRequest(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm, LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setDisplayRequestTotalRecord(oLoanAgainstDepositStatusBO.getDisplayRequestTotalRecord());
	}
	public void populateFileName(LoanAgainstDepositStatusForm oLoanAgainstDepositStatusForm,LoanAgainstDepositStatusBO oLoanAgainstDepositStatusBO) {
		oLoanAgainstDepositStatusForm.setFile(oLoanAgainstDepositStatusBO.getFile());
	}
	private void clearSession(HttpSession session) {
		session.setAttribute("oLoanAgainstDepositStatusBO", null);
		session.setAttribute("oAccountSummaryReportMessageBO", null);
		session.setAttribute("GSFilePath", null);
		session.setAttribute("GSFileName", null);

	}
}


