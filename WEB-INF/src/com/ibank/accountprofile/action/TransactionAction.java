/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Account Profile
 * Created Date : 
 * Modify Date  :
 *
 */
 
package com.ibank.accountprofile.action;

import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.ibank.accountprofile.bo.TransactionBO;
import com.ibank.accountprofile.dao.TransactionDAO;
import com.ibank.accountprofile.formbean.TransactionForm;
import com.ibank.utility.RemoveNullValue;
import com.ibank.utility.ReportManager;

public class TransactionAction extends Action {

	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)throws Exception {
		TransactionDAO oTransactionDAO = new TransactionDAO();
		TransactionBO oTransactionBO = new TransactionBO();
		TransactionForm oTransactionForm = (TransactionForm) form;
		TransactionBO oTransactionMessageBO = new TransactionBO();  
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oTransactionForm);
		String sActionPath = "";
		HttpSession session = request.getSession(true);
		sActionPath = mapping.getPath();
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sClientActionPath = "/mybank/transactionInfoEnquiry.do";
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
		
		if (sActionPath.equals("/transactionInfoEnquiry")) {
			clearSession(session);
			session.setAttribute("oTransactionBO", null);
			session.setAttribute("oTransactionMessageBO", null);
			session.setAttribute("TransactionDateFrom", null);
			session.setAttribute("TransactionDateTo", null);
			session.setAttribute("oTransactionStatementListBO", null);
			//session.setAttribute("oTransactionBO", null);

			gsDisplayLastRecord = "";
			gsDisplayRequestLastRecord = "";
			oTransactionForm.setErrorCode("");
			oTransactionForm.setErrorMessage("");

		//	oTransactionBO = oTransactionDAO.firstLoad(gsUserID,gsSessionID);
			System.out.print("oCustomerCode ==>>>>>" +gsCustomerCode);
			oTransactionBO = oTransactionDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
															   gsUserID,gsSessionID,gsCompanyID,
															   "I","10");
			System.out.print("ErrorCode ==>>>>>" +oTransactionBO.getErrorCode()+"--"+oTransactionBO.getTagetUrl());
			if (oTransactionBO.getErrorCode().equals("0")){
				
			oTransactionBO = oTransactionDAO.getAccountData(gsCustomerCode,oTransactionBO.getTagetUrl());
			oTransactionForm.setDateFrom(oTransactionBO.getDateFrom());
			oTransactionForm.setDateTo(oTransactionBO.getDateTo());
			oTransactionForm.setAccountInfoList(oTransactionBO.getAccountInfoList());
			oTransactionForm.setAccountInfoNameList(oTransactionBO.getAccountInfoNameList());

					//System.out.println("oTransactionBO.User ==>>> "+gsUserAccount);
					//	oTransactionForm.setAccountInfo(gsUserAccount);
						sSuccess = sSuccessAction;
		}else if (oTransactionBO.getErrorCode().equals("1")){
				oTransactionForm.setErrorCode("1");
				oTransactionForm.setErrorMessage(oTransactionBO.getErrorMessage());
				sSuccess = sFailureAction; 
			}
			else if (oTransactionBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oTransactionBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
			
		else if (sActionPath.equals("/accountNoTransactionInfoEnquiry")) {
			session.setAttribute("oTransactionBO", null);
			session.setAttribute("oTransactionMessageBO", null);
			oTransactionBO = oTransactionDAO.getSessionCheckPro(gsUserID,gsSessionID);
			if (oTransactionBO.getErrorCode().equals("0")){
				oTransactionBO = oTransactionDAO.getCompanyCode(oTransactionForm.getAccountInfo());			
				oTransactionForm.setAccountNo(oTransactionBO.getAccountNo());
				oTransactionForm.setCompanyCode(oTransactionBO.getCompanyCode());
				oTransactionForm.setAccountInfo(oTransactionForm.getAccountInfo());
				sSuccess = sSuccessAction;
			}
			else if (oTransactionBO.getErrorCode().equals("1")){
				oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oTransactionForm,oTransactionMessageBO);
				session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
				sSuccess = sFailureAction;
			}
			else if (oTransactionBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oTransactionBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}		 
		
				
		else if (sActionPath.equals("/searchTransactionInfoEnquiry")){
				oTransactionForm.setErrorCode("");
				oTransactionForm.setErrorMessage("");
				session.setAttribute("oTransactionStatementListBO", null);
				
				oTransactionBO = oTransactionDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
						   										gsUserID,gsSessionID,gsCompanyID,
						   										"I","01");
				if (oTransactionBO.getErrorCode().equals("0")){
				
				oTransactionBO = oTransactionDAO.getExecuteStatementProAPI(oTransactionForm.getAccountNo(),
																		   oTransactionForm.getDateFrom(),
																		   oTransactionForm.getDateTo(),
																		   oTransactionBO.getTagetUrl());
		
			 System.out.println("oIBankingUserLogInBO.getErrorCode() ===>>>>>> " + oTransactionBO.getErrorCode());
			 
			 if (oTransactionBO.getErrorCode().equals("0")) {
				 
				 oTransactionBO.setStatementList(oTransactionBO.getStatementList());				 
				  session.setAttribute("oTransactionStatementListBO", oTransactionBO);
				  sSuccess = sSuccessAction;
			 } else if (oTransactionBO.getErrorCode().equals("1")) {
				
				  String sMessage = "";
				  sMessage = oTransactionBO.getErrorMessage();
				  oTransactionForm.setErrorCode("1");
				  oTransactionForm.setErrorMessage(sMessage);
			      sSuccess = sFailureAction;
			
			}else {
				
				oTransactionForm.setStatementList(oTransactionBO.getStatementList());
				session.setAttribute("oIBankingUserLogInListBO",oTransactionBO);						
				sSuccess = sSuccessAction;
			}	
		
		}   else if (oTransactionBO.getErrorCode().equals("1")) {
			
			  String sMessage = "";
			  sMessage = oTransactionBO.getErrorMessage();
			  oTransactionForm.setErrorCode("1");
			  oTransactionForm.setErrorMessage(sMessage);
		      sSuccess = sFailureAction;
		
		}else {
			
			oTransactionForm.setStatementList(oTransactionBO.getStatementList());
			session.setAttribute("oIBankingUserLogInListBO",oTransactionBO);						
			sSuccess = sSuccessAction;
		}	
	
	} 
		else if (sActionPath.equals("/ReportTransactionStatement")){
			oTransactionForm.setErrorCode("");
			oTransactionForm.setErrorMessage("");
			oTransactionBO = oTransactionDAO.getTargetAPIPro("IBNK",request.getRemoteAddr(),
																				   gsUserID,gsSessionID,gsCompanyID,
																				   "I","01");
			if (oTransactionBO.getErrorCode().equals("0")){
				oTransactionBO = oTransactionDAO.ReportTransactionStatement(gsUserID,gsSessionID,oTransactionForm.getAccountNo(),
																			oTransactionForm.getDateFrom(),
																			oTransactionForm.getDateTo(),
																			oTransactionBO.getTagetUrl());
				if (oTransactionBO.getErrorCode().equals("0")){
					ServletContext context=request.getSession().getServletContext();
					String sImageDirectory ="/pages/images/";
					String sImagePath=context.getRealPath(sImageDirectory);
					String sDirectory ="/pages/accountprofile/report/";
					String sSubReportPath=context.getRealPath(sDirectory);
					String sStatementDate ="Statement of Account for the Period : "+oTransactionForm.getDateFrom()+" To "+oTransactionForm.getDateTo();
					String sPresentDate = "Date : "+gsPresentDate;
					HashMap hParameters=new HashMap();
					
					hParameters.put("sAccountNo", oTransactionBO.getAccountNo());
					hParameters.put("sBranchName", oTransactionBO.getBranchName());
					hParameters.put("sCustomerCode", oTransactionBO.getCustomerCode());
					hParameters.put("sOpenDate", oTransactionBO.getOpenDate());
					hParameters.put("sAccountStatus", oTransactionBO.getAccountStatus());
					hParameters.put("sDisburseAmount", oTransactionBO.getDisburseAmount());
					hParameters.put("sDisburseRenewLevel", oTransactionBO.getDisburseRenewLevel());
					hParameters.put("sLastReschDate", oTransactionBO.getLastReschDate());
					hParameters.put("sLastReshDateLevel", oTransactionBO.getLastReshDateLevel());
					hParameters.put("sSanctionDate", oTransactionBO.getSanctionDate());
					hParameters.put("sSanMaturityDate", oTransactionBO.getSanMaturityDate());
					hParameters.put("sMaturitySanctionLevel", oTransactionBO.getMaturitySanctionLevel());
					hParameters.put("sTenure", oTransactionBO.getTenure());
					hParameters.put("sTitle", oTransactionBO.getTitle());
					hParameters.put("sIntRate", oTransactionBO.getIntRate());
					hParameters.put("sProductName", oTransactionBO.getProductName());
					hParameters.put("sPresentAdd", oTransactionBO.getPresentAdd());
					hParameters.put("sReshCountLevel", oTransactionBO.getReshCountLevel());
					hParameters.put("sRESH_COUNT", oTransactionBO.getReshCount());
					hParameters.put("sUserID", gsUserID);
					hParameters.put("sSessionID", gsSessionID);
					hParameters.put("sFromDate", oTransactionForm.getDateFrom());
					hParameters.put("sStatementDate", sStatementDate);
					hParameters.put("sPresentDate", sPresentDate);
					hParameters.put("sSubReportPath", sSubReportPath+"/");
					hParameters.put("sImagePath", sImagePath+"/nbfilogo.gif");
					String sFileName = "";
					if(oTransactionBO.getAccountType().equals("L")) {
						
					 sFileName = sDirectory + "Transaction.jasper";
					
					}else if(oTransactionBO.getAccountType().equals("A")) {
						
					 sFileName = sDirectory + "TransactionAsseet.jasper";
					}
					ReportManager oReportManager = new ReportManager();
					oReportManager.viewReport(request, response, hParameters, sFileName);
					sSuccess = sSuccessAction;
				}
				else if (oTransactionBO.getErrorCode().equals("1")){
					oTransactionForm.setErrorCode("1");
					oTransactionForm.setErrorMessage(oTransactionBO.getErrorMessage());
					sSuccess = sFailureAction; 
				}
				else if (oTransactionBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oTransactionBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oTransactionBO.getErrorCode().equals("1")){
				oTransactionForm.setErrorCode("1");
				oTransactionForm.setErrorMessage(oTransactionBO.getErrorMessage());
				sSuccess = sFailureAction; 
			}
			else if (oTransactionBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oTransactionBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		
		else if (sActionPath.equals("/logOutibank")) {
			oTransactionForm.setErrorCode("");
			sSuccess = sSuccessAction;
		}
		
		
		else if (sActionPath.equals("/fileAccountStatement")) {
			session.setAttribute("GSFilePath", null);
			session.setAttribute("GSFileName", null);
			session.setAttribute("oTransactionBO", null);
			session.setAttribute("oTransactionMessageBO",	null);
			oTransactionBO = oTransactionDAO.getTransactionPreviewPro1(gsUserID,gsSessionID,
					oTransactionForm.getCompanyCode(),
					oTransactionForm.getAccountNo(),
					oTransactionForm.getDateFrom(),
					oTransactionForm.getDateTo());

			if (oTransactionBO.getErrorCode().equals("0")) {
				oTransactionBO =	oTransactionDAO.getFileName();
				populateFileName(oTransactionForm,oTransactionBO);

				oTransactionBO =oTransactionDAO.getXLSFile(session,gsUserID,gsSessionID,
														   oTransactionForm.getFile());

					String sFilePath ="/mybank/AccountStatement/BalanceDetailReport/"	+ gsSessionID + "/" + oTransactionForm.getFile()+ ".xls";
					String sFileName = oTransactionForm.getFile() + ".xls";
					session.setAttribute("GSFilePath", sFilePath);
					session.setAttribute("GSFileName", sFileName);

					String sMessageCode = "";
					sMessageCode = "1";
					String sSerialNo = "";
					sSerialNo = "1";
					String sMessage = "";
					sMessage = "Please download the generated file.";
					oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oTransactionForm,oTransactionMessageBO);
					session.setAttribute("oTransactionMessageBO",oTransactionMessageBO);
					sSuccess = sSuccessAction;
				} 
			else if (oTransactionBO.getErrorCode().equals("1")){
						oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTransactionForm,oTransactionMessageBO);
						session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
						sSuccess = sFailureAction; 
					}				
				else if (oTransactionBO.getErrorCode().equals("2")) {
					clearSession(session);
					sSuccess = sSessionExpireAction;
				} else if (oTransactionBO.getErrorCode().equals("3")) {
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				} else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
		
		
		else if (sActionPath.equals("/cancelTransactionInfoEnquiry")) {
			oTransactionBO = oTransactionDAO.getMenuCheckPro(
											 gsUserID,
											 gsSessionID,
											 request.getRemoteAddr(),
											 sClientActionPath);	
			
			if (oTransactionBO.getErrorCode().equals("0")){
				oTransactionBO = oTransactionDAO.getClearPro(gsUserID,gsSessionID);
				if (oTransactionBO.getErrorCode().equals("0")){
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
					session.setAttribute("oTransactionBO", null);
					session.setAttribute("oTransactionMessageBO", null);
					session.setAttribute("processTransaction",null);
					sSuccess = sSuccessAction;
				}
				else if (oTransactionBO.getErrorCode().equals("1")){
					oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oTransactionForm,oTransactionMessageBO);
					session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oTransactionBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oTransactionBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else if (oTransactionBO.getErrorCode().equals("1")){
				oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
				populateMessage(oTransactionForm,oTransactionMessageBO);
				session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
				sSuccess = sFailureAction; 
			}
			else if (oTransactionBO.getErrorCode().equals("2")){
				clearSession(session);
				sSuccess = sSessionExpireAction;
			}
			else if (oTransactionBO.getErrorCode().equals("3")){
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			}
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}
		else if (sActionPath.equals("/displayTransactionInfoEnquiry")) {
			oTransactionBO = oTransactionDAO.getMaxRecord(gsUserID,gsSessionID);
			populateMaxRecord(oTransactionForm,oTransactionBO);
			String sRecord = oTransactionForm.getDisplayTotalRecord();
			if (sRecord.equals("0")){
				oTransactionBO = oTransactionDAO.getSessionCheckPro(gsUserID,gsSessionID);
				if (oTransactionBO.getErrorCode().equals("0")){
					oTransactionBO = oTransactionDAO.getTransactionSearchPro(gsUserID,gsSessionID,oTransactionForm.getCompanyCode(),oTransactionForm.getAccountNo(),oTransactionForm.getDateFrom(),oTransactionForm.getDateTo());
					if (oTransactionBO.getErrorCode().equals("0")){
						session.setAttribute("GSDisplayTransactionInfoEnquiry","");
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oTransactionBO = oTransactionDAO.getMaxRecord(gsUserID,gsSessionID);
						populateMaxRecord(oTransactionForm, oTransactionBO);
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", oTransactionForm.getDisplayTotalRecord());
					
						oTransactionBO = oTransactionDAO.getDisplayRecordPro("F",oTransactionForm.getDisplayFrequency(),oTransactionForm.getDisplayTotalRecord(),oTransactionForm.getDisplayFrequency());
						if (oTransactionBO.getErrorCode().equals("0")){
							session.setAttribute("GSDisplayRequestTransactionInfoEnquiry","");
							session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

							session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oTransactionBO.getDisplayOutMessage());
							session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oTransactionBO.getDisplayOutFirstRecord());
							session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oTransactionBO.getDisplayOutLastRecord());
							String sFirstRecord = oTransactionBO.getDisplayOutFirstRecord(); 
							String sLastRecord = oTransactionBO.getDisplayOutLastRecord(); 
							session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);

							oTransactionBO = oTransactionDAO.getMaxRecordRequest(gsUserID,gsSessionID);
							populateMaxRecordRequest(oTransactionForm, oTransactionBO);
							session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", oTransactionForm.getDisplayRequestTotalRecord());
							oTransactionBO = oTransactionDAO.getDisplayRecordPro("F",oTransactionForm.getDisplayRequestFrequency(),oTransactionForm.getDisplayRequestTotalRecord(),oTransactionForm.getDisplayRequestFrequency());
							if (oTransactionBO.getErrorCode().equals("0")){
							
								session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oTransactionBO.getDisplayOutMessage());
								session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oTransactionBO.getDisplayOutFirstRecord());
								session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oTransactionBO.getDisplayOutLastRecord());
							
								String sRequestFirstRecord = oTransactionBO.getDisplayOutFirstRecord();
								String sRequestLastRecord = oTransactionBO.getDisplayOutLastRecord();
								session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
							
								oTransactionBO = oTransactionDAO.getTransactionInformation(gsUserID,gsSessionID);
								populateSearch(oTransactionForm, oTransactionBO);
								session.setAttribute("oTransactionBO", oTransactionBO);
								 
								oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oTransactionForm,oTransactionMessageBO);
								session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
						//		oTransactionBO = oTransactionDAO.firstLoad(gsUserID,gsSessionID);
								sSuccess = sSuccessAction;
							}
							else if (oTransactionBO.getErrorCode().equals("1")){
								oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
								populateMessage(oTransactionForm,oTransactionMessageBO);
								session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
								sSuccess = sFailureAction;
							}
							else if (oTransactionBO.getErrorCode().equals("2")){
								clearSession(session);
								sSuccess = sSessionExpireAction;
							}
							else if (oTransactionBO.getErrorCode().equals("3")){
								clearSession(session);
								sSuccess = sSessionMyBankMenuAction;
							}
							else {
								clearSession(session);
								sSuccess = sFatalErrorAction;
							}
						}	
						else if (oTransactionBO.getErrorCode().equals("1")){
							oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
							populateMessage(oTransactionForm,oTransactionMessageBO);
							session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
							sSuccess = sFailureAction;
						}
						else if (oTransactionBO.getErrorCode().equals("2")){
							clearSession(session);
							sSuccess = sSessionExpireAction;
						}
						else if (oTransactionBO.getErrorCode().equals("3")){
							clearSession(session);
							sSuccess = sSessionMyBankMenuAction;
						}
						else {
							clearSession(session);
							sSuccess = sFatalErrorAction;
						}
					}
					else if (oTransactionBO.getErrorCode().equals("1")){
						oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTransactionForm,oTransactionMessageBO);
						session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
						sSuccess = sFailureAction;
					}
					else if (oTransactionBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oTransactionBO.getErrorCode().equals("3")){
						clearSession(session);
						sSuccess = sSessionMyBankMenuAction;
					}
					else {
						clearSession(session);
						sSuccess = sFatalErrorAction;
					}
				}
				else if (oTransactionBO.getErrorCode().equals("1")){
					oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
					populateMessage(oTransactionForm,oTransactionMessageBO);
					session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
					sSuccess = sFailureAction; 
				}
				else if (oTransactionBO.getErrorCode().equals("2")){
					clearSession(session);
					sSuccess = sSessionExpireAction;
				}
				else if (oTransactionBO.getErrorCode().equals("3")){
					clearSession(session);
					sSuccess = sSessionMyBankMenuAction;
				}
				else {
					clearSession(session);
					sSuccess = sFatalErrorAction;
				}
			}
			else{
				session.setAttribute("oTransactionBO", null);
				String sDisplayTypeAction = oTransactionForm.getDisplayTypeAction();
				if (sDisplayTypeAction.equals("L")){
					session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
					oTransactionBO = oTransactionDAO.getDisplayRecordPro(oTransactionForm.getDisplayAction(),gsDisplayLastRecord,gsDisplayTotalRecord,oTransactionForm.getDisplayFrequency());
					if (oTransactionBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", oTransactionBO.getDisplayOutMessage());
						String sFirstRecord = oTransactionBO.getDisplayOutFirstRecord();
						String sLastRecord = oTransactionBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayFirstRecordTransactionInfoEnquiry",oTransactionBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayLastRecordTransactionInfoEnquiry",oTransactionBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayTransactionInfoEnquiry",sLastRecord);				
						oTransactionBO = oTransactionDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchStatement(oTransactionForm, oTransactionBO);
						session.setAttribute("oTransactionBO", oTransactionBO);

						oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTransactionForm,oTransactionMessageBO);
						session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
			//			oTransactionBO = oTransactionDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oTransactionBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRecordTransactionInfoEnquiry", "");

						oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTransactionForm,oTransactionMessageBO);
						session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oTransactionBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oTransactionBO.getErrorCode().equals("3")){
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
					oTransactionBO = oTransactionDAO.getDisplayRecordPro(oTransactionForm.getDisplayAction(),gsDisplayRequestLastRecord,gsRequestTotalRecord,oTransactionForm.getDisplayRequestFrequency());
					if (oTransactionBO.getErrorCode().equals("0")){	
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", oTransactionBO.getDisplayOutMessage());
						String sRequestFirstRecord = oTransactionBO.getDisplayOutFirstRecord();
						String sRequestLastRecord = oTransactionBO.getDisplayOutLastRecord();
						session.setAttribute("GSDisplayRequestFirstRecordTransactionInfoEnquiry",oTransactionBO.getDisplayOutFirstRecord());
						session.setAttribute("GSDisplayRequestLastRecordTransactionInfoEnquiry",oTransactionBO.getDisplayOutLastRecord());
						session.setAttribute("GSDisplayRequestTransactionInfoEnquiry",sRequestLastRecord);
	
						oTransactionBO = oTransactionDAO.getTransactionInformation(gsUserID,gsSessionID);
						populateSearchRequest(oTransactionForm, oTransactionBO);
						session.setAttribute("oTransactionBO", oTransactionBO);
					
						oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTransactionForm,oTransactionMessageBO);
						session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
					//	oTransactionBO = oTransactionDAO.firstLoad(gsUserID,gsSessionID);
						session.setAttribute("GSAccountNo",""); 
						gsStatementAccountNo="";
						gsStatementCompanyhCode="";
						sSuccess = sSuccessAction;
					}	
					else if (oTransactionBO.getErrorCode().equals("1")){
						session.setAttribute("GSDisplayRequestPageTransactionInfoEnquiry", "");
						session.setAttribute("GSDisplayRequestRecordTransactionInfoEnquiry", "");

						oTransactionMessageBO = oTransactionDAO.getMessageInformation(gsUserID,gsSessionID);
						populateMessage(oTransactionForm,oTransactionMessageBO);
						session.setAttribute("oTransactionMessageBO", oTransactionMessageBO);
						sSuccess = sFailureAction; 
					}
					else if (oTransactionBO.getErrorCode().equals("2")){
						clearSession(session);
						sSuccess = sSessionExpireAction;
					}
					else if (oTransactionBO.getErrorCode().equals("3")){
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
	public void populate(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setDateFrom(oTransactionBO.getDateFrom());
		oTransactionForm.setDateTo(oTransactionBO.getDateTo());
		oTransactionForm.setAccountNo(oTransactionBO.getAccountNo());
		oTransactionForm.setAccountInfo(oTransactionBO.getAccountInfo());
		oTransactionForm.setAccountInfoList(oTransactionBO.getAccountInfoList());
		oTransactionForm.setAccountInfoNameList(oTransactionBO.getAccountInfoNameList());
		oTransactionForm.setCustomerInfoList(oTransactionBO.getCustomerInfoList());
		oTransactionForm.setStatementList(oTransactionBO.getStatementList());
		oTransactionForm.setRequestList(oTransactionBO.getRequestList());
		oTransactionForm.setErrorCode(oTransactionBO.getErrorCode());
		oTransactionForm.setErrorMessage(oTransactionBO.getErrorMessage());
		oTransactionForm.setList(oTransactionBO.getList());

	}
	public void populateMessage(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setList(oTransactionBO.getList());
		oTransactionForm.setErrorCode(oTransactionBO.getErrorCode());
		oTransactionForm.setErrorMessage(oTransactionBO.getErrorMessage());
	}
	public void populateMenu(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setMenu(oTransactionBO.getMenu());
		oTransactionForm.setMenuList(oTransactionBO.getMenuList());
		oTransactionForm.setMenuNameList(oTransactionBO.getMenuNameList());
	}
	public void populateSearch(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setCustomerInfoList(oTransactionBO.getCustomerInfoList());
		oTransactionForm.setStatementList(oTransactionBO.getRequestList());
		oTransactionForm.setRequestList(oTransactionBO.getRequestList());
		oTransactionForm.setDisplayTotalRecord(oTransactionBO.getDisplayTotalRecord());
	}
	public void populateSearchStatement(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setStatementList(oTransactionBO.getStatementList());
		oTransactionForm.setDisplayTotalRecord(oTransactionBO.getDisplayTotalRecord());
	}
	public void populateSearchRequest(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setRequestList(oTransactionBO.getRequestList());
		oTransactionForm.setDisplayRequestTotalRecord(oTransactionBO.getDisplayRequestTotalRecord());
	}
	public void populateCompany(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setAccountNo(oTransactionBO.getAccountNo());
		oTransactionForm.setCompanyCode(oTransactionBO.getCompanyCode());
	}
	public void populateMaxRecord(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setDisplayTotalRecord(oTransactionBO.getDisplayTotalRecord());
	}
	public void populateMaxRecordRequest(TransactionForm oTransactionForm, TransactionBO oTransactionBO) {
		oTransactionForm.setDisplayRequestTotalRecord(oTransactionBO.getDisplayRequestTotalRecord());
	}
	public void populateFileName(TransactionForm oTransactionForm,TransactionBO oTransactionBO) {
		oTransactionForm.setFile(oTransactionBO.getFile());
	}
	private void clearSession(HttpSession session) {
		session.setAttribute("oTransactionBO", null);
		session.setAttribute("oTransactionMessageBO", null);
		session.setAttribute("GSFilePath", null);
		session.setAttribute("GSFileName", null);

	}
}

