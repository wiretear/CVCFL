package com.ibank.deposit.report.action;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibank.deposit.report.bo.AccountStatementReportBO;
import com.ibank.deposit.report.dao.AccountStatementReportDAO;
import com.ibank.deposit.report.formbean.AccountStatementReportForm;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

public class AccountStatementReportAction  extends Action {

	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)throws Exception {
		AccountStatementReportDAO oAccountStatementReportDAO = new AccountStatementReportDAO();
		AccountStatementReportBO oAccountStatementReportBO = new AccountStatementReportBO();
		AccountStatementReportForm oAccountStatementReportForm = (AccountStatementReportForm) form;
		AccountStatementReportBO oAccountSummaryReportMessageBO = new AccountStatementReportBO();  
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oAccountStatementReportForm);
		String sActionPath = "";
		HttpSession session = request.getSession(true);
		sActionPath = mapping.getPath();
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sClientActionPath = "/ibankinguser/depositAccountStatementReport.do";
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
		
		if (sActionPath.equals("/depositAccountStatementReport")) {
			clearSession(session);
			session.setAttribute("oAccountStatementReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oAccountSummaryReportStatementListBO", null);
			//session.setAttribute("oAccountStatementReportBO", null);

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oAccountStatementReportForm.setErrorCode("");
			oAccountStatementReportForm.setErrorMessage("");

		//	oAccountStatementReportBO = oAccountStatementReportDAO.firstLoad(gsUserID,gsSessionID);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			oAccountStatementReportBO = oAccountStatementReportDAO.getAccountData(gsCustomerCode);
			oAccountStatementReportForm.setDateFrom(oAccountStatementReportBO.getDateFrom());
			oAccountStatementReportForm.setDateTo(oAccountStatementReportBO.getDateTo());
			oAccountStatementReportForm.setAccountInfoList(oAccountStatementReportBO.getAccountInfoList());
			oAccountStatementReportForm.setAccountInfoNameList(oAccountStatementReportBO.getAccountInfoNameList());

					//System.out.println("oAccountStatementReportBO.User ==>>> "+gsUserAccount);
					//	oAccountStatementReportForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
			
		}else if (sActionPath.equals("/accountNoAccountSummaryReportInfoEnquiry")) {
			session.setAttribute("oAccountStatementReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			oAccountStatementReportBO = oAccountStatementReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			if (oAccountStatementReportBO.getErrorCode().equals("0")){
				oAccountStatementReportBO = oAccountStatementReportDAO.getCompanyCode(oAccountStatementReportForm.getAccountInfo());			
				oAccountStatementReportForm.setAccountNo(oAccountStatementReportBO.getAccountNo());
				oAccountStatementReportForm.setCompanyCode(oAccountStatementReportBO.getCompanyCode());
				oAccountStatementReportForm.setAccountInfo(oAccountStatementReportForm.getAccountInfo());
				sSuccess = sSuccessAction;
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction;
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}		 
		
				
		else if (sActionPath.equals("/searchTransactionInfoEnquiry")){
				oAccountStatementReportForm.setErrorCode("");
				oAccountStatementReportForm.setErrorMessage("");
				session.setAttribute("oAccountSummaryReportStatementListBO", null);
				
				oAccountStatementReportBO = oAccountStatementReportDAO.getExecuteStatementProAPI(oAccountStatementReportForm.getAccountNo(),
																		   oAccountStatementReportForm.getDateFrom(),
																		   oAccountStatementReportForm.getDateTo());
		
			 System.out.println("oIBankingUserLogInBO.getErrorCode() ===>>>>>> " + oAccountStatementReportBO.getErrorCode());
			 
			 if (oAccountStatementReportBO.getErrorCode().equals("0")) {
				 
				 oAccountStatementReportBO.setStatementList(oAccountStatementReportBO.getStatementList());				 
				  session.setAttribute("oAccountSummaryReportStatementListBO", oAccountStatementReportBO);
				  sSuccess = sSuccessAction;
			 } else if (oAccountStatementReportBO.getErrorCode().equals("1")) {
				
				  String sMessage = "";
				  sMessage = oAccountStatementReportBO.getErrorMessage();
				  oAccountStatementReportForm.setErrorCode("1");
				  oAccountStatementReportForm.setErrorMessage(sMessage);
			      sSuccess = sFailureAction;
			
			}else {
				
				oAccountStatementReportForm.setStatementList(oAccountStatementReportBO.getStatementList());
				session.setAttribute("oIBankingUserLogInListBO",oAccountStatementReportBO);						
				sSuccess = sSuccessAction;
			}
	
		
		}  
		else if (sActionPath.equals("/ReportTransactionStatement")){
			oAccountStatementReportBO = oAccountStatementReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			if (oAccountStatementReportBO.getErrorCode().equals("0")){
				oAccountStatementReportBO = oAccountStatementReportDAO.getTransactionPreviewPro(gsUserID,gsSessionID,oAccountStatementReportForm.getCompanyCode(),oAccountStatementReportForm.getAccountNo(),oAccountStatementReportForm.getDateFrom(),oAccountStatementReportForm.getDateTo());
				if (oAccountStatementReportBO.getErrorCode().equals("0")){
					ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/accountprofile/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
					String sStatementDate ="Statement of Account for the Period : "+oAccountStatementReportForm.getDateFrom()+" To "+oAccountStatementReportForm.getDateTo();
					String sPresentDate = "Date : "+gsPresentDate;
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);
					hParameters.put("sFromDate", oAccountStatementReportForm.getDateFrom());
					hParameters.put("sToDate", oAccountStatementReportForm.getDateTo());
					hParameters.put("sStatementDate", sStatementDate);
					hParameters.put("sPresentDate", sPresentDate);
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/reportlogo.gif");
					String sFileName = sDirectory + "Transaction.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);
					sSuccess = sSuccessAction;
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("3")){
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
			session.setAttribute("oAccountStatementReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO",	null);
			oAccountStatementReportBO = oAccountStatementReportDAO.getTransactionPreviewPro1(gsUserID,gsSessionID,
					oAccountStatementReportForm.getCompanyCode(),
					oAccountStatementReportForm.getAccountNo(),
					oAccountStatementReportForm.getDateFrom(),
					oAccountStatementReportForm.getDateTo());

			if (oAccountStatementReportBO.getErrorCode().equals("0")) {
				oAccountStatementReportBO =	oAccountStatementReportDAO.getFileName();
				populateFileName(oAccountStatementReportForm,oAccountStatementReportBO);

				oAccountStatementReportBO =oAccountStatementReportDAO.getXLSFile(session,gsUserID,gsSessionID,
														   oAccountStatementReportForm.getFile());

					String sFilePath ="/mybank/AccountStatement/BalanceDetailReport/"	+ gsSessionID + "/" + oAccountStatementReportForm.getFile()+ ".xls";
					String sFileName = oAccountStatementReportForm.getFile() + ".xls";
					session.setAttribute("GSFilePath", sFilePath);
					session.setAttribute("GSFileName", sFileName);

					String sMessageCode = "";
					sMessageCode = "1";
					String sSerialNo = "";
					sSerialNo = "1";
					String sMessage = "";
					sMessage = "Please download the generated file.";
					oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO",oAccountSummaryReportMessageBO);
					sSuccess = sSuccessAction;
				} 
			else if (oAccountStatementReportBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}				
				else if (oAccountStatementReportBO.getErrorCode().equals("2")) {
					clearSession(session);
					sSuccess = sSessionExpireAction;
				} else if (oAccountStatementReportBO.getErrorCode().equals("3")) {
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				} else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
		
		
		else if (sActionPath.equals("/cancelTransactionInfoEnquiry")) {
			oAccountStatementReportBO = oAccountStatementReportDAO.getMenuCheckPro(
											 gsUserID,
											 gsSessionID,
											 request.getRemoteAddr(),
											 sClientActionPath);	
			
			if (oAccountStatementReportBO.getErrorCode().equals("0")){
				oAccountStatementReportBO = oAccountStatementReportDAO.getClearPro(gsUserID,gsSessionID);
				if (oAccountStatementReportBO.getErrorCode().equals("0")){
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
					session.setAttribute("oAccountStatementReportBO", null);
					session.setAttribute("oAccountSummaryReportMessageBO", null);
					session.setAttribute("processTransaction",null);
					sSuccess = sSuccessAction;
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAccountStatementReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/displayTransactionInfoEnquiry")) {
			oAccountStatementReportBO = oAccountStatementReportDAO.getMaxRecord(gsUserID,gsSessionID);
			populateMaxRecord(oAccountStatementReportForm,oAccountStatementReportBO);
			String sRecord = oAccountStatementReportForm.getDisplayTotalRecord();
			if (sRecord.equals("0")){
				oAccountStatementReportBO = oAccountStatementReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
				if (oAccountStatementReportBO.getErrorCode().equals("0")){
					oAccountStatementReportBO = oAccountStatementReportDAO.getTransactionSearchPro(gsUserID,gsSessionID,oAccountStatementReportForm.getCompanyCode(),oAccountStatementReportForm.getAccountNo(),oAccountStatementReportForm.getDateFrom(),oAccountStatementReportForm.getDateTo());
					if (oAccountStatementReportBO.getErrorCode().equals("0")){
						session.setAttribute("GSDisplayTransactionInfoEnquiry","");
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAccountStatementReportBO = oAccountStatementReportDAO.getMaxRecord(gsUserID,gsSessionID);
						populateMaxRecord(oAccountStatementReportForm, oAccountStatementReportBO);
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", oAccountStatementReportForm.getDisplayTotalRecord());
					
						oAccountStatementReportBO = oAccountStatementReportDAO.getDisplayRecordPro("F",oAccountStatementReportForm.getDisplayFrequency(),oAccountStatementReportForm.getDisplayTotalRecord(),oAccountStatementReportForm.getDisplayFrequency());
						if (oAccountStatementReportBO.getErrorCode().equals("0")){
							session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
							session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

							session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oAccountStatementReportBO.getDisplayOutMessage());
							session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oAccountStatementReportBO.getDisplayOutFirstRecord());
							session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oAccountStatementReportBO.getDisplayOutLastRecord());
							String sFirstRecord = oAccountStatementReportBO.getDisplayOutFirstRecord(); 
							String sLastRecord = oAccountStatementReportBO.getDisplayOutLastRecord(); 
							session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);

							oAccountStatementReportBO = oAccountStatementReportDAO.getMaxRecordRequest(gsUserID,gsSessionID);
							populateMaxRecordRequest(oAccountStatementReportForm, oAccountStatementReportBO);
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", oAccountStatementReportForm.getDisplayRequestTotalRecord());
							oAccountStatementReportBO = oAccountStatementReportDAO.getDisplayRecordPro("F",oAccountStatementReportForm.getDisplayRequestFrequency(),oAccountStatementReportForm.getDisplayRequestTotalRecord(),oAccountStatementReportForm.getDisplayRequestFrequency());
							if (oAccountStatementReportBO.getErrorCode().equals("0")){
							
								session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oAccountStatementReportBO.getDisplayOutMessage());
								session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oAccountStatementReportBO.getDisplayOutFirstRecord());
								session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oAccountStatementReportBO.getDisplayOutLastRecord());
							
								String sRequestFirstRecord = oAccountStatementReportBO.getDisplayOutFirstRecord();
								String sRequestLastRecord = oAccountStatementReportBO.getDisplayOutLastRecord();
								session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
							
								oAccountStatementReportBO = oAccountStatementReportDAO.getTransactionInformation(gsUserID,gsSessionID);
								populateSearch(oAccountStatementReportForm, oAccountStatementReportBO);
								session.setAttribute("oAccountStatementReportBO", oAccountStatementReportBO);
								 
								oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						//		oAccountStatementReportBO = oAccountStatementReportDAO.firstLoad(gsUserID,gsSessionID);
								sSuccess = sSuccessAction;
							}
							else if (oAccountStatementReportBO.getErrorCode().equals("1")){
								oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
								sSuccess = sFailureAction;
							}
							else if (oAccountStatementReportBO.getErrorCode().equals("2")){
								clearSession(session);
								sSuccess = sSessionExpireAction;
							}
							else if (oAccountStatementReportBO.getErrorCode().equals("3")){
								clearSession(session);
								sSuccess = sSessionMyBankMenuAction;
							}
							else {
								clearSession(session);
								sSuccess = sFatalErrorAction;
							}
						}	
						else if (oAccountStatementReportBO.getErrorCode().equals("1")){
							oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
							populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
							session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
							sSuccess = sFailureAction;
						}
						else if (oAccountStatementReportBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oAccountStatementReportBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					}
					else if (oAccountStatementReportBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction;
					}
					else if (oAccountStatementReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAccountStatementReportBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAccountStatementReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else{
				session.setAttribute("oAccountStatementReportBO", null);
				String sDisplayTypeAction = oAccountStatementReportForm.getDisplayTypeAction();
				if (sDisplayTypeAction.equals("L")){
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					oAccountStatementReportBO = oAccountStatementReportDAO.getDisplayRecordPro(oAccountStatementReportForm.getDisplayAction(),gsDisplayLastRecord,gsDisplayTotalRecord,oAccountStatementReportForm.getDisplayFrequency());
					if (oAccountStatementReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oAccountStatementReportBO.getDisplayOutMessage());
						String sFirstRecord = oAccountStatementReportBO.getDisplayOutFirstRecord();
						String sLastRecord = oAccountStatementReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oAccountStatementReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oAccountStatementReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);				
						oAccountStatementReportBO = oAccountStatementReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchStatement(oAccountStatementReportForm, oAccountStatementReportBO);
						session.setAttribute("oAccountStatementReportBO", oAccountStatementReportBO);

						oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
			//			oAccountStatementReportBO = oAccountStatementReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oAccountStatementReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oAccountStatementReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAccountStatementReportBO.getErrorCode().equals("3")){
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
					oAccountStatementReportBO = oAccountStatementReportDAO.getDisplayRecordPro(oAccountStatementReportForm.getDisplayAction(),gsDisplayRequestLastRecord,gsRequestTotalRecord,oAccountStatementReportForm.getDisplayRequestFrequency());
					if (oAccountStatementReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oAccountStatementReportBO.getDisplayOutMessage());
						String sRequestFirstRecord = oAccountStatementReportBO.getDisplayOutFirstRecord();
						String sRequestLastRecord = oAccountStatementReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oAccountStatementReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oAccountStatementReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
	
						oAccountStatementReportBO = oAccountStatementReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchRequest(oAccountStatementReportForm, oAccountStatementReportBO);
						session.setAttribute("oAccountStatementReportBO", oAccountStatementReportBO);
					
						oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					//	oAccountStatementReportBO = oAccountStatementReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oAccountStatementReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oAccountStatementReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAccountStatementReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oAccountStatementReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAccountStatementReportBO.getErrorCode().equals("3")){
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
	public void populate(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setDateFrom(oAccountStatementReportBO.getDateFrom());
		oAccountStatementReportForm.setDateTo(oAccountStatementReportBO.getDateTo());
		oAccountStatementReportForm.setAccountNo(oAccountStatementReportBO.getAccountNo());
		oAccountStatementReportForm.setAccountInfo(oAccountStatementReportBO.getAccountInfo());
		oAccountStatementReportForm.setAccountInfoList(oAccountStatementReportBO.getAccountInfoList());
		oAccountStatementReportForm.setAccountInfoNameList(oAccountStatementReportBO.getAccountInfoNameList());
		oAccountStatementReportForm.setCustomerInfoList(oAccountStatementReportBO.getCustomerInfoList());
		oAccountStatementReportForm.setStatementList(oAccountStatementReportBO.getStatementList());
		oAccountStatementReportForm.setRequestList(oAccountStatementReportBO.getRequestList());
		oAccountStatementReportForm.setErrorCode(oAccountStatementReportBO.getErrorCode());
		oAccountStatementReportForm.setErrorMessage(oAccountStatementReportBO.getErrorMessage());
		oAccountStatementReportForm.setList(oAccountStatementReportBO.getList());

	}
	public void populateMessage(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setList(oAccountStatementReportBO.getList());
		oAccountStatementReportForm.setErrorCode(oAccountStatementReportBO.getErrorCode());
		oAccountStatementReportForm.setErrorMessage(oAccountStatementReportBO.getErrorMessage());
	}
	public void populateMenu(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setMenu(oAccountStatementReportBO.getMenu());
		oAccountStatementReportForm.setMenuList(oAccountStatementReportBO.getMenuList());
		oAccountStatementReportForm.setMenuNameList(oAccountStatementReportBO.getMenuNameList());
	}
	public void populateSearch(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setCustomerInfoList(oAccountStatementReportBO.getCustomerInfoList());
		oAccountStatementReportForm.setStatementList(oAccountStatementReportBO.getRequestList());
		oAccountStatementReportForm.setRequestList(oAccountStatementReportBO.getRequestList());
		oAccountStatementReportForm.setDisplayTotalRecord(oAccountStatementReportBO.getDisplayTotalRecord());
	}
	public void populateSearchStatement(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setStatementList(oAccountStatementReportBO.getStatementList());
		oAccountStatementReportForm.setDisplayTotalRecord(oAccountStatementReportBO.getDisplayTotalRecord());
	}
	public void populateSearchRequest(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setRequestList(oAccountStatementReportBO.getRequestList());
		oAccountStatementReportForm.setDisplayRequestTotalRecord(oAccountStatementReportBO.getDisplayRequestTotalRecord());
	}
	public void populateCompany(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setAccountNo(oAccountStatementReportBO.getAccountNo());
		oAccountStatementReportForm.setCompanyCode(oAccountStatementReportBO.getCompanyCode());
	}
	public void populateMaxRecord(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setDisplayTotalRecord(oAccountStatementReportBO.getDisplayTotalRecord());
	}
	public void populateMaxRecordRequest(AccountStatementReportForm oAccountStatementReportForm, AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setDisplayRequestTotalRecord(oAccountStatementReportBO.getDisplayRequestTotalRecord());
	}
	public void populateFileName(AccountStatementReportForm oAccountStatementReportForm,AccountStatementReportBO oAccountStatementReportBO) {
		oAccountStatementReportForm.setFile(oAccountStatementReportBO.getFile());
	}
	private void clearSession(HttpSession session) {
		session.setAttribute("oAccountStatementReportBO", null);
		session.setAttribute("oAccountSummaryReportMessageBO", null);
		session.setAttribute("GSFilePath", null);
		session.setAttribute("GSFileName", null);

	}
}
