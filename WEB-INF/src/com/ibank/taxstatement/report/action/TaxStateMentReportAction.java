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

import com.ibank.taxstatement.report.bo.TaxStateMentReportBO;
import com.ibank.taxstatement.report.dao.TaxStateMentReportDAO;
import com.ibank.taxstatement.report.formbaen.TaxStateMentReportForm;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

public class TaxStateMentReportAction extends Action {

	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)throws Exception {
		TaxStateMentReportDAO oTaxStateMentReportDAO = new TaxStateMentReportDAO();
		TaxStateMentReportBO oTaxStateMentReportBO = new TaxStateMentReportBO();
		TaxStateMentReportForm oTaxStateMentReportForm = (TaxStateMentReportForm) form;
		TaxStateMentReportBO oAccountSummaryReportMessageBO = new TaxStateMentReportBO();  
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oTaxStateMentReportForm);
		String sActionPath = "";
		HttpSession session = request.getSession(true);
		sActionPath = mapping.getPath();
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sClientActionPath = "/ibankinguser/taxStatementReport.do";
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
		
		if (sActionPath.equals("/taxStatementReport")) {
			clearSession(session);
			session.setAttribute("oTaxStateMentReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oAccountSummaryReportStatementListBO", null);
			//session.setAttribute("oTaxStateMentReportBO", null);

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oTaxStateMentReportForm.setErrorCode("");
			oTaxStateMentReportForm.setErrorMessage("");

		//	oTaxStateMentReportBO = oTaxStateMentReportDAO.firstLoad(gsUserID,gsSessionID);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			oTaxStateMentReportBO = oTaxStateMentReportDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
																				   gsUserID,gsSessionID,gsCompanyID,
																				   "I","10");
			if(oTaxStateMentReportBO.getErrorCode().equals("0")) {
			oTaxStateMentReportBO = oTaxStateMentReportDAO.getAccountData(gsCustomerCode,oTaxStateMentReportBO.getTagetUrl());
			oTaxStateMentReportForm.setDateFrom(oTaxStateMentReportBO.getDateFrom());
			oTaxStateMentReportForm.setDateTo(oTaxStateMentReportBO.getDateTo());
			oTaxStateMentReportForm.setAccountInfoList(oTaxStateMentReportBO.getAccountInfoList());
			oTaxStateMentReportForm.setAccountInfoNameList(oTaxStateMentReportBO.getAccountInfoNameList());

					//System.out.println("oTaxStateMentReportBO.User ==>>> "+gsUserAccount);
					//	oTaxStateMentReportForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
			}else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
							oTaxStateMentReportForm.setErrorCode("1");
							oTaxStateMentReportForm.setErrorMessage(oTaxStateMentReportBO.getErrorMessage());
							sSuccess = sFailureAction; 
						}
						else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					
		}else if (sActionPath.equals("/accountNoAccountSummaryReportInfoEnquiry")) {
			session.setAttribute("oTaxStateMentReportBO", null);
			session.setAttribute("oAccountSummaryReportMessageBO", null);
			oTaxStateMentReportBO = oTaxStateMentReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
			if (oTaxStateMentReportBO.getErrorCode().equals("0")){
				oTaxStateMentReportBO = oTaxStateMentReportDAO.getCompanyCode(oTaxStateMentReportForm.getAccountInfo());			
				oTaxStateMentReportForm.setAccountNo(oTaxStateMentReportBO.getAccountNo());
				oTaxStateMentReportForm.setCompanyCode(oTaxStateMentReportBO.getCompanyCode());
				oTaxStateMentReportForm.setAccountInfo(oTaxStateMentReportForm.getAccountInfo());
				sSuccess = sSuccessAction;
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction;
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}		 
		
				
	
		else if (sActionPath.equals("/taxStatementReportPDF")){
			oTaxStateMentReportBO = oTaxStateMentReportDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
																		   gsUserID,gsSessionID,gsCompanyID,
																		   "I","03");
			if (oTaxStateMentReportBO.getErrorCode().equals("0")){
				oTaxStateMentReportBO = oTaxStateMentReportDAO.ReportTransactionStatement(gsUserID,gsSessionID,oTaxStateMentReportForm.getAccountNo(),
																						  oTaxStateMentReportForm.getDateFrom(),
																						  oTaxStateMentReportForm.getDateTo(),
																						  oTaxStateMentReportBO.getTagetUrl());
				if (oTaxStateMentReportBO.getErrorCode().equals("0")){
					ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/taxstatement/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
					String sStatementDate ="Statement of Account for the Period : "+oTaxStateMentReportForm.getDateFrom()+" To "+oTaxStateMentReportForm.getDateTo();
					String sPresentDate = "Date : "+gsPresentDate;
					HashMap hParameters=new HashMap();
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);
					hParameters.put("sFromDate", oTaxStateMentReportForm.getDateFrom());
					hParameters.put("sToDate", oTaxStateMentReportForm.getDateTo());
					hParameters.put("sStatementDate", sStatementDate);
					hParameters.put("sStatementHeader", oTaxStateMentReportBO.getCustomerInfo());
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/nbfilogo.gif");
					String sFileName = sDirectory + "LoanTaxStatement.jasper";
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);
					sSuccess = sSuccessAction;
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
					oTaxStateMentReportForm.setErrorCode("1");
					oTaxStateMentReportForm.setErrorMessage(oTaxStateMentReportBO.getErrorMessage());
					sSuccess = sFailureAction; 
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
				oTaxStateMentReportForm.setErrorCode("1");
				oTaxStateMentReportForm.setErrorMessage(oTaxStateMentReportBO.getErrorMessage());
				sSuccess = sFailureAction; 
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		
		else if (sActionPath.equals("/logOutibank")) {
			     oTaxStateMentReportForm.setErrorCode("");
			     sSuccess = sSuccessAction;
		}
		
		else if (sActionPath.equals("/cancelTransactionInfoEnquiry")) {
			oTaxStateMentReportBO = oTaxStateMentReportDAO.getMenuCheckPro(
											 gsUserID,
											 gsSessionID,
											 request.getRemoteAddr(),
											 sClientActionPath);	
			
			if (oTaxStateMentReportBO.getErrorCode().equals("0")){
				oTaxStateMentReportBO = oTaxStateMentReportDAO.getClearPro(gsUserID,gsSessionID);
				if (oTaxStateMentReportBO.getErrorCode().equals("0")){
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
					session.setAttribute("oTaxStateMentReportBO", null);
					session.setAttribute("oAccountSummaryReportMessageBO", null);
					session.setAttribute("processTransaction",null);
					sSuccess = sSuccessAction;
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
				oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
				session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/displayTransactionInfoEnquiry")) {
			oTaxStateMentReportBO = oTaxStateMentReportDAO.getMaxRecord(gsUserID,gsSessionID);
			populateMaxRecord(oTaxStateMentReportForm,oTaxStateMentReportBO);
			String sRecord = oTaxStateMentReportForm.getDisplayTotalRecord();
			if (sRecord.equals("0")){
				oTaxStateMentReportBO = oTaxStateMentReportDAO.getSessionCheckPro(gsUserID,gsSessionID);
				if (oTaxStateMentReportBO.getErrorCode().equals("0")){
					oTaxStateMentReportBO = oTaxStateMentReportDAO.getTransactionSearchPro(gsUserID,gsSessionID,oTaxStateMentReportForm.getCompanyCode(),oTaxStateMentReportForm.getAccountNo(),oTaxStateMentReportForm.getDateFrom(),oTaxStateMentReportForm.getDateTo());
					if (oTaxStateMentReportBO.getErrorCode().equals("0")){
						session.setAttribute("GSDisplayTransactionInfoEnquiry","");
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oTaxStateMentReportBO = oTaxStateMentReportDAO.getMaxRecord(gsUserID,gsSessionID);
						populateMaxRecord(oTaxStateMentReportForm, oTaxStateMentReportBO);
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", oTaxStateMentReportForm.getDisplayTotalRecord());
					
						oTaxStateMentReportBO = oTaxStateMentReportDAO.getDisplayRecordPro("F",oTaxStateMentReportForm.getDisplayFrequency(),oTaxStateMentReportForm.getDisplayTotalRecord(),oTaxStateMentReportForm.getDisplayFrequency());
						if (oTaxStateMentReportBO.getErrorCode().equals("0")){
							session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
							session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

							session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oTaxStateMentReportBO.getDisplayOutMessage());
							session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oTaxStateMentReportBO.getDisplayOutFirstRecord());
							session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oTaxStateMentReportBO.getDisplayOutLastRecord());
							String sFirstRecord = oTaxStateMentReportBO.getDisplayOutFirstRecord(); 
							String sLastRecord = oTaxStateMentReportBO.getDisplayOutLastRecord(); 
							session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);

							oTaxStateMentReportBO = oTaxStateMentReportDAO.getMaxRecordRequest(gsUserID,gsSessionID);
							populateMaxRecordRequest(oTaxStateMentReportForm, oTaxStateMentReportBO);
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", oTaxStateMentReportForm.getDisplayRequestTotalRecord());
							oTaxStateMentReportBO = oTaxStateMentReportDAO.getDisplayRecordPro("F",oTaxStateMentReportForm.getDisplayRequestFrequency(),oTaxStateMentReportForm.getDisplayRequestTotalRecord(),oTaxStateMentReportForm.getDisplayRequestFrequency());
							if (oTaxStateMentReportBO.getErrorCode().equals("0")){
							
								session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oTaxStateMentReportBO.getDisplayOutMessage());
								session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oTaxStateMentReportBO.getDisplayOutFirstRecord());
								session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oTaxStateMentReportBO.getDisplayOutLastRecord());
							
								String sRequestFirstRecord = oTaxStateMentReportBO.getDisplayOutFirstRecord();
								String sRequestLastRecord = oTaxStateMentReportBO.getDisplayOutLastRecord();
								session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
							
								oTaxStateMentReportBO = oTaxStateMentReportDAO.getTransactionInformation(gsUserID,gsSessionID);
								populateSearch(oTaxStateMentReportForm, oTaxStateMentReportBO);
								session.setAttribute("oTaxStateMentReportBO", oTaxStateMentReportBO);
								 
								oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						//		oTaxStateMentReportBO = oTaxStateMentReportDAO.firstLoad(gsUserID,gsSessionID);
								sSuccess = sSuccessAction;
							}
							else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
								oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
								session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
								sSuccess = sFailureAction;
							}
							else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
								clearSession(session);
								sSuccess = sSessionExpireAction;
							}
							else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
								clearSession(session);
								sSuccess = sSessionMyBankMenuAction;
							}
							else {
								clearSession(session);
								sSuccess = sFatalErrorAction;
							}
						}	
						else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
							oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
							populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
							session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
							sSuccess = sFailureAction;
						}
						else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					}
					else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
						oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction;
					}
					else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
					oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
					session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else{
				session.setAttribute("oTaxStateMentReportBO", null);
				String sDisplayTypeAction = oTaxStateMentReportForm.getDisplayTypeAction();
				if (sDisplayTypeAction.equals("L")){
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					oTaxStateMentReportBO = oTaxStateMentReportDAO.getDisplayRecordPro(oTaxStateMentReportForm.getDisplayAction(),gsDisplayLastRecord,gsDisplayTotalRecord,oTaxStateMentReportForm.getDisplayFrequency());
					if (oTaxStateMentReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oTaxStateMentReportBO.getDisplayOutMessage());
						String sFirstRecord = oTaxStateMentReportBO.getDisplayOutFirstRecord();
						String sLastRecord = oTaxStateMentReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oTaxStateMentReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oTaxStateMentReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);				
						oTaxStateMentReportBO = oTaxStateMentReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchStatement(oTaxStateMentReportForm, oTaxStateMentReportBO);
						session.setAttribute("oTaxStateMentReportBO", oTaxStateMentReportBO);

						oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
			//			oTaxStateMentReportBO = oTaxStateMentReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
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
					oTaxStateMentReportBO = oTaxStateMentReportDAO.getDisplayRecordPro(oTaxStateMentReportForm.getDisplayAction(),gsDisplayRequestLastRecord,gsRequestTotalRecord,oTaxStateMentReportForm.getDisplayRequestFrequency());
					if (oTaxStateMentReportBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oTaxStateMentReportBO.getDisplayOutMessage());
						String sRequestFirstRecord = oTaxStateMentReportBO.getDisplayOutFirstRecord();
						String sRequestLastRecord = oTaxStateMentReportBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oTaxStateMentReportBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oTaxStateMentReportBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
	
						oTaxStateMentReportBO = oTaxStateMentReportDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchRequest(oTaxStateMentReportForm, oTaxStateMentReportBO);
						session.setAttribute("oTaxStateMentReportBO", oTaxStateMentReportBO);
					
						oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
					//	oTaxStateMentReportBO = oTaxStateMentReportDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oTaxStateMentReportBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

						oAccountSummaryReportMessageBO = oTaxStateMentReportDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTaxStateMentReportForm,oAccountSummaryReportMessageBO);
						session.setAttribute("oAccountSummaryReportMessageBO", oAccountSummaryReportMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oTaxStateMentReportBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oTaxStateMentReportBO.getErrorCode().equals("3")){
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
	public void populate(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setDateFrom(oTaxStateMentReportBO.getDateFrom());
		oTaxStateMentReportForm.setDateTo(oTaxStateMentReportBO.getDateTo());
		oTaxStateMentReportForm.setAccountNo(oTaxStateMentReportBO.getAccountNo());
		oTaxStateMentReportForm.setAccountInfo(oTaxStateMentReportBO.getAccountInfo());
		oTaxStateMentReportForm.setAccountInfoList(oTaxStateMentReportBO.getAccountInfoList());
		oTaxStateMentReportForm.setAccountInfoNameList(oTaxStateMentReportBO.getAccountInfoNameList());
		oTaxStateMentReportForm.setCustomerInfoList(oTaxStateMentReportBO.getCustomerInfoList());
		oTaxStateMentReportForm.setStatementList(oTaxStateMentReportBO.getStatementList());
		oTaxStateMentReportForm.setRequestList(oTaxStateMentReportBO.getRequestList());
		oTaxStateMentReportForm.setErrorCode(oTaxStateMentReportBO.getErrorCode());
		oTaxStateMentReportForm.setErrorMessage(oTaxStateMentReportBO.getErrorMessage());
		oTaxStateMentReportForm.setList(oTaxStateMentReportBO.getList());

	}
	public void populateMessage(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setList(oTaxStateMentReportBO.getList());
		oTaxStateMentReportForm.setErrorCode(oTaxStateMentReportBO.getErrorCode());
		oTaxStateMentReportForm.setErrorMessage(oTaxStateMentReportBO.getErrorMessage());
	}
	public void populateMenu(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setMenu(oTaxStateMentReportBO.getMenu());
		oTaxStateMentReportForm.setMenuList(oTaxStateMentReportBO.getMenuList());
		oTaxStateMentReportForm.setMenuNameList(oTaxStateMentReportBO.getMenuNameList());
	}
	public void populateSearch(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setCustomerInfoList(oTaxStateMentReportBO.getCustomerInfoList());
		oTaxStateMentReportForm.setStatementList(oTaxStateMentReportBO.getRequestList());
		oTaxStateMentReportForm.setRequestList(oTaxStateMentReportBO.getRequestList());
		oTaxStateMentReportForm.setDisplayTotalRecord(oTaxStateMentReportBO.getDisplayTotalRecord());
	}
	public void populateSearchStatement(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setStatementList(oTaxStateMentReportBO.getStatementList());
		oTaxStateMentReportForm.setDisplayTotalRecord(oTaxStateMentReportBO.getDisplayTotalRecord());
	}
	public void populateSearchRequest(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setRequestList(oTaxStateMentReportBO.getRequestList());
		oTaxStateMentReportForm.setDisplayRequestTotalRecord(oTaxStateMentReportBO.getDisplayRequestTotalRecord());
	}
	public void populateCompany(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setAccountNo(oTaxStateMentReportBO.getAccountNo());
		oTaxStateMentReportForm.setCompanyCode(oTaxStateMentReportBO.getCompanyCode());
	}
	public void populateMaxRecord(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setDisplayTotalRecord(oTaxStateMentReportBO.getDisplayTotalRecord());
	}
	public void populateMaxRecordRequest(TaxStateMentReportForm oTaxStateMentReportForm, TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setDisplayRequestTotalRecord(oTaxStateMentReportBO.getDisplayRequestTotalRecord());
	}
	public void populateFileName(TaxStateMentReportForm oTaxStateMentReportForm,TaxStateMentReportBO oTaxStateMentReportBO) {
		oTaxStateMentReportForm.setFile(oTaxStateMentReportBO.getFile());
	}
	private void clearSession(HttpSession session) {
		session.setAttribute("oTaxStateMentReportBO", null);
		session.setAttribute("oAccountSummaryReportMessageBO", null);
		session.setAttribute("GSFilePath", null);
		session.setAttribute("GSFileName", null);

	}
}
