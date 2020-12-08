package com.ibank.taxstatement.report.action;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibank.taxstatement.report.bo.AdvanceIncomeTaxReportBO;
import com.ibank.taxstatement.report.dao.AdvanceIncomeTaxReportDAO;
import com.ibank.taxstatement.report.formbaen.AdvanceIncomeTaxReportForm;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

public class AdvanceIncomeTaxReportAction extends Action {

	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)throws Exception {
		AdvanceIncomeTaxReportDAO oAdvanceIncomeTaxReportDAO = new AdvanceIncomeTaxReportDAO();
		AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO = new AdvanceIncomeTaxReportBO();
		AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm = (AdvanceIncomeTaxReportForm) form;
		AdvanceIncomeTaxReportBO oAccountSummaryReportMessageBO = new AdvanceIncomeTaxReportBO();  
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oAdvanceIncomeTaxReportForm);
		String sActionPath = "";
		HttpSession session = request.getSession(true);
		sActionPath = mapping.getPath();
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sClientActionPath = "/ibankinguser/advanceIncomeTaxStatementReport.do";
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
		
		if (sActionPath.equals("/advanceIncomeTaxStatementReport")) {
			clearSession(session);
			session.setAttribute("oAdvanceIncomeTaxReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oAccountSummaryReportStatementListBO", null);
			//session.setAttribute("oAdvanceIncomeTaxReportBO", null);

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oAdvanceIncomeTaxReportForm.setErrorCode("");
			oAdvanceIncomeTaxReportForm.setErrorMessage("");

		//	oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.firstLoad(gsUserID,gsSessionID);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
																				   gsUserID,gsSessionID,gsCompanyID,
																				   "I","10");
 			if(oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")) {
			oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getAccountData(gsCustomerCode,oAdvanceIncomeTaxReportBO.getTagetUrl());
			oAdvanceIncomeTaxReportForm.setDateFrom(oAdvanceIncomeTaxReportBO.getDateFrom());
			oAdvanceIncomeTaxReportForm.setDateTo(oAdvanceIncomeTaxReportBO.getDateTo());
			oAdvanceIncomeTaxReportForm.setAccountInfoList(oAdvanceIncomeTaxReportBO.getAccountInfoList());
			oAdvanceIncomeTaxReportForm.setAccountInfoNameList(oAdvanceIncomeTaxReportBO.getAccountInfoNameList());

					//System.out.println("oAdvanceIncomeTaxReportBO.User ==>>> "+gsUserAccount);
					//	oAdvanceIncomeTaxReportForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
 			}
			else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
				oAdvanceIncomeTaxReportForm.setErrorCode("1");
				oAdvanceIncomeTaxReportForm.setErrorMessage(oAdvanceIncomeTaxReportBO.getErrorMessage());
				sSuccess = sFailureAction; 
			}
			else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
			
		}	 
		 
		else if (sActionPath.equals("/advanceIncomeTaxStatementPDF")){
			oAdvanceIncomeTaxReportForm.setErrorCode("");
			oAdvanceIncomeTaxReportForm.setErrorMessage("");
			oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
																		   gsUserID,gsSessionID,gsCompanyID,
																		   "I","09");
			
			if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){
				oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.ReportTransactionStatement(gsUserID,gsSessionID,
																									oAdvanceIncomeTaxReportForm.getAccountNo(),
																									oAdvanceIncomeTaxReportForm.getDateFrom(),
																									oAdvanceIncomeTaxReportForm.getDateTo(),
																									oAdvanceIncomeTaxReportBO.getTagetUrl());
				if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){
					ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/taxstatement/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
					String sStatementDate ="Statement of Account for the Period : "+oAdvanceIncomeTaxReportForm.getDateFrom()+" To "+oAdvanceIncomeTaxReportForm.getDateTo();
					String sPresentDate = "Date : "+gsPresentDate;
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);
					hParameters.put("sFromDate", oAdvanceIncomeTaxReportForm.getDateFrom());
					hParameters.put("sToDate", oAdvanceIncomeTaxReportForm.getDateTo());
					hParameters.put("sStatementDate", sStatementDate);
					hParameters.put("sStatementHeader", oAdvanceIncomeTaxReportBO.getCustomerInfo());
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/nbfilogo.gif");
					String sFileName = sDirectory + "DepositTaxStatement.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);
					sSuccess = sSuccessAction;
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
					oAdvanceIncomeTaxReportForm.setErrorCode("1");
					oAdvanceIncomeTaxReportForm.setErrorMessage(oAdvanceIncomeTaxReportBO.getErrorMessage());
					sSuccess = sFailureAction; 
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			
		}else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
				oAdvanceIncomeTaxReportForm.setErrorCode("1");
				oAdvanceIncomeTaxReportForm.setErrorMessage(oAdvanceIncomeTaxReportBO.getErrorMessage());
				sSuccess = sFailureAction; 
			}
			else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
	
		else if (sActionPath.equals("/logOutibank")) {
				 oAdvanceIncomeTaxReportForm.setErrorCode("");
		     sSuccess = sSuccessAction;
	}
		else if (sActionPath.equals("/cancelTransactionInfoEnquiry")) {
			oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getMenuCheckPro(
											 gsUserID,
											 gsSessionID,
											 request.getRemoteAddr(),
											 sClientActionPath);	
			
			if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){
				oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getClearPro(gsUserID,gsSessionID);
				if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){
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
					session.setAttribute("oAdvanceIncomeTaxReportBO", null);
					session.setAttribute("oAccountSummaryReportMessageBO", null);
					session.setAttribute("processTransaction",null);
					sSuccess = sSuccessAction;
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/displayTransactionInfoEnquiry")) {
			oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getMaxRecord(gsUserID,gsSessionID);
			populateMaxRecord(oAdvanceIncomeTaxReportForm,oAdvanceIncomeTaxReportBO);
			String sRecord = oAdvanceIncomeTaxReportForm.getDisplayTotalRecord();
			if (sRecord.equals("0")){
				oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
				if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){
					oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getTransactionSearchPro(gsUserID,gsSessionID,oAdvanceIncomeTaxReportForm.getCompanyCode(),oAdvanceIncomeTaxReportForm.getAccountNo(),oAdvanceIncomeTaxReportForm.getDateFrom(),oAdvanceIncomeTaxReportForm.getDateTo());
					if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){
						session.setAttribute("GSDisplayTransactionInfoEnquiry","");
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getMaxRecord(gsUserID,gsSessionID);
						populateMaxRecord(oAdvanceIncomeTaxReportForm, oAdvanceIncomeTaxReportBO);
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", oAdvanceIncomeTaxReportForm.getDisplayTotalRecord());
					
						oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getDisplayRecordPro("F",oAdvanceIncomeTaxReportForm.getDisplayFrequency(),oAdvanceIncomeTaxReportForm.getDisplayTotalRecord(),oAdvanceIncomeTaxReportForm.getDisplayFrequency());
						if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){
							session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
							session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

							session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oAdvanceIncomeTaxReportBO.getDisplayOutMessage());
							session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oAdvanceIncomeTaxReportBO.getDisplayOutFirstRecord());
							session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oAdvanceIncomeTaxReportBO.getDisplayOutLastRecord());
							String sFirstRecord = oAdvanceIncomeTaxReportBO.getDisplayOutFirstRecord(); 
							String sLastRecord = oAdvanceIncomeTaxReportBO.getDisplayOutLastRecord(); 
							session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);

							oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getMaxRecordRequest(gsUserID,gsSessionID);
							populateMaxRecordRequest(oAdvanceIncomeTaxReportForm, oAdvanceIncomeTaxReportBO);
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", oAdvanceIncomeTaxReportForm.getDisplayRequestTotalRecord());
							oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getDisplayRecordPro("F",oAdvanceIncomeTaxReportForm.getDisplayRequestFrequency(),oAdvanceIncomeTaxReportForm.getDisplayRequestTotalRecord(),oAdvanceIncomeTaxReportForm.getDisplayRequestFrequency());
							if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){
							
								session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oAdvanceIncomeTaxReportBO.getDisplayOutMessage());
								session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oAdvanceIncomeTaxReportBO.getDisplayOutFirstRecord());
								session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oAdvanceIncomeTaxReportBO.getDisplayOutLastRecord());
							
								String sRequestFirstRecord = oAdvanceIncomeTaxReportBO.getDisplayOutFirstRecord();
								String sRequestLastRecord = oAdvanceIncomeTaxReportBO.getDisplayOutLastRecord();
								session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
							
								oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getTransactionInformation(gsUserID,gsSessionID);
								populateSearch(oAdvanceIncomeTaxReportForm, oAdvanceIncomeTaxReportBO);
								session.setAttribute("oAdvanceIncomeTaxReportBO", oAdvanceIncomeTaxReportBO);
								 
								oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						//		oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.firstLoad(gsUserID,gsSessionID);
								sSuccess = sSuccessAction;
							}
							else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
								oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
								sSuccess = sFailureAction;
							}
							else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
								clearSession(session);
								sSuccess = sSessionExpireAction;
							}
							else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
								clearSession(session);
								sSuccess = sSessionMyBankMenuAction;
							}
							else {
								clearSession(session);
								sSuccess = sFatalErrorAction;
							}
						}	
						else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
							oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
							populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
							session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
							sSuccess = sFailureAction;
						}
						else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					}
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction;
					}
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else{
				session.setAttribute("oAdvanceIncomeTaxReportBO", null);
				String sDisplayTypeAction = oAdvanceIncomeTaxReportForm.getDisplayTypeAction();
				if (sDisplayTypeAction.equals("L")){
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getDisplayRecordPro(oAdvanceIncomeTaxReportForm.getDisplayAction(),gsDisplayLastRecord,gsDisplayTotalRecord,oAdvanceIncomeTaxReportForm.getDisplayFrequency());
					if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oAdvanceIncomeTaxReportBO.getDisplayOutMessage());
						String sFirstRecord = oAdvanceIncomeTaxReportBO.getDisplayOutFirstRecord();
						String sLastRecord = oAdvanceIncomeTaxReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oAdvanceIncomeTaxReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oAdvanceIncomeTaxReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);				
						oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchStatement(oAdvanceIncomeTaxReportForm, oAdvanceIncomeTaxReportBO);
						session.setAttribute("oAdvanceIncomeTaxReportBO", oAdvanceIncomeTaxReportBO);

						oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
			//			oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
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
					oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getDisplayRecordPro(oAdvanceIncomeTaxReportForm.getDisplayAction(),gsDisplayRequestLastRecord,gsRequestTotalRecord,oAdvanceIncomeTaxReportForm.getDisplayRequestFrequency());
					if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oAdvanceIncomeTaxReportBO.getDisplayOutMessage());
						String sRequestFirstRecord = oAdvanceIncomeTaxReportBO.getDisplayOutFirstRecord();
						String sRequestLastRecord = oAdvanceIncomeTaxReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oAdvanceIncomeTaxReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oAdvanceIncomeTaxReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
	
						oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchRequest(oAdvanceIncomeTaxReportForm, oAdvanceIncomeTaxReportBO);
						session.setAttribute("oAdvanceIncomeTaxReportBO", oAdvanceIncomeTaxReportBO);
					
						oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					//	oAdvanceIncomeTaxReportBO = oAdvanceIncomeTaxReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oAdvanceIncomeTaxReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oAdvanceIncomeTaxReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oAdvanceIncomeTaxReportBO.getErrorCode().equals("3")){
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
	public void populate(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setDateFrom(oAdvanceIncomeTaxReportBO.getDateFrom());
		oAdvanceIncomeTaxReportForm.setDateTo(oAdvanceIncomeTaxReportBO.getDateTo());
		oAdvanceIncomeTaxReportForm.setAccountNo(oAdvanceIncomeTaxReportBO.getAccountNo());
		oAdvanceIncomeTaxReportForm.setAccountInfo(oAdvanceIncomeTaxReportBO.getAccountInfo());
		oAdvanceIncomeTaxReportForm.setAccountInfoList(oAdvanceIncomeTaxReportBO.getAccountInfoList());
		oAdvanceIncomeTaxReportForm.setAccountInfoNameList(oAdvanceIncomeTaxReportBO.getAccountInfoNameList());
		oAdvanceIncomeTaxReportForm.setCustomerInfoList(oAdvanceIncomeTaxReportBO.getCustomerInfoList());
		oAdvanceIncomeTaxReportForm.setStatementList(oAdvanceIncomeTaxReportBO.getStatementList());
		oAdvanceIncomeTaxReportForm.setRequestList(oAdvanceIncomeTaxReportBO.getRequestList());
		oAdvanceIncomeTaxReportForm.setErrorCode(oAdvanceIncomeTaxReportBO.getErrorCode());
		oAdvanceIncomeTaxReportForm.setErrorMessage(oAdvanceIncomeTaxReportBO.getErrorMessage());
		oAdvanceIncomeTaxReportForm.setList(oAdvanceIncomeTaxReportBO.getList());

	}
	public void populateMessage(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setList(oAdvanceIncomeTaxReportBO.getList());
		oAdvanceIncomeTaxReportForm.setErrorCode(oAdvanceIncomeTaxReportBO.getErrorCode());
		oAdvanceIncomeTaxReportForm.setErrorMessage(oAdvanceIncomeTaxReportBO.getErrorMessage());
	}
	public void populateMenu(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setMenu(oAdvanceIncomeTaxReportBO.getMenu());
		oAdvanceIncomeTaxReportForm.setMenuList(oAdvanceIncomeTaxReportBO.getMenuList());
		oAdvanceIncomeTaxReportForm.setMenuNameList(oAdvanceIncomeTaxReportBO.getMenuNameList());
	}
	public void populateSearch(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setCustomerInfoList(oAdvanceIncomeTaxReportBO.getCustomerInfoList());
		oAdvanceIncomeTaxReportForm.setStatementList(oAdvanceIncomeTaxReportBO.getRequestList());
		oAdvanceIncomeTaxReportForm.setRequestList(oAdvanceIncomeTaxReportBO.getRequestList());
		oAdvanceIncomeTaxReportForm.setDisplayTotalRecord(oAdvanceIncomeTaxReportBO.getDisplayTotalRecord());
	}
	public void populateSearchStatement(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setStatementList(oAdvanceIncomeTaxReportBO.getStatementList());
		oAdvanceIncomeTaxReportForm.setDisplayTotalRecord(oAdvanceIncomeTaxReportBO.getDisplayTotalRecord());
	}
	public void populateSearchRequest(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setRequestList(oAdvanceIncomeTaxReportBO.getRequestList());
		oAdvanceIncomeTaxReportForm.setDisplayRequestTotalRecord(oAdvanceIncomeTaxReportBO.getDisplayRequestTotalRecord());
	}
	public void populateCompany(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setAccountNo(oAdvanceIncomeTaxReportBO.getAccountNo());
		oAdvanceIncomeTaxReportForm.setCompanyCode(oAdvanceIncomeTaxReportBO.getCompanyCode());
	}
	public void populateMaxRecord(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setDisplayTotalRecord(oAdvanceIncomeTaxReportBO.getDisplayTotalRecord());
	}
	public void populateMaxRecordRequest(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm, AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setDisplayRequestTotalRecord(oAdvanceIncomeTaxReportBO.getDisplayRequestTotalRecord());
	}
	public void populateFileName(AdvanceIncomeTaxReportForm oAdvanceIncomeTaxReportForm,AdvanceIncomeTaxReportBO oAdvanceIncomeTaxReportBO) {
		oAdvanceIncomeTaxReportForm.setFile(oAdvanceIncomeTaxReportBO.getFile());
	}
	private void clearSession(HttpSession session) {
		session.setAttribute("oAdvanceIncomeTaxReportBO", null);
		session.setAttribute("oAccountSummaryReportMessageBO", null);
		session.setAttribute("GSFilePath", null);
		session.setAttribute("GSFileName", null);

	}
}
