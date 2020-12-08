<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.mybank.login.bo.LogInBO"%>
<%@ page language="java" 
		 import ="com.mybank.login.bo.*,java.util.ArrayList"
         session      = "true"
		 autoFlush    = "true"
		 isThreadSafe = "true" 
		 isErrorPage  = "false"
%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.mybank.dbconnection.application.DBCP" %>
<%	
	String message = (message=request.getParameter("message")) != null? message:"";
	String sUserID = (String) session.getAttribute("GSUserID");
	String sSessionID = (String) session.getAttribute("GSSessionID");
	String sHeaderName = (String) session.getAttribute("GSHeaderName");
	String sUserTitle = (String) session.getAttribute("GSUserTitle");
	String sHeaderLogIn = (String) session.getAttribute("GSHeaderLogIn");
	String sLastLogInDate = (String) session.getAttribute("GSLastLogInDate");
	String sBranchNameHeader = (String) session.getAttribute("GSBranchNameHeader");
	String sBranchName = (String) session.getAttribute("GSBranchName");
	String sBranchDateHeader = (String) session.getAttribute("GSBranchDateHeader");
	String sBranchOpenDateSPFormat = (String) session.getAttribute("GSBranchOpenDateSPFormat");
	String sFullPathName = LogInBO.getReadPath(sUserID,sSessionID);
ArrayList aListMyBank = LogInBO.getMyBankMenu(sUserID,sSessionID);

%>
<html lang="en" >
<head>
<bean:message key="label.metaDescription"/>
<bean:message key="label.metaKeywords"/>
<bean:message key="label.metaAuthor"/>
<bean:message key="label.metaExpires"/>
<bean:message key="label.metaImagetoolbar"/>
<bean:message key="label.metaRating"/>
<bean:message key="label.metaGenerator"/>
<bean:message key="label.metaCopyright"/>
<bean:message key="label.metaRobots"/>
<bean:message key="label.metaRevisitAfter"/>
<bean:message key="label.metaPragma"/>
<bean:message key="label.metaDocRights"/>
<bean:message key="label.metaMSSmartTagsPreventParsing"/>
<bean:message key="label.metaLanguage"/>
<bean:message key="label.metaContentType"/>
<bean:message key="label.metaContentStyleType"/>
<bean:message key="label.myBankTitle"/>
<meta charset='utf-8'>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="pages/menu/cssmenu/stylesHeader.css" type="text/css">
<link rel="stylesheet" href="pages/menu/cssmenu/styles.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/menu/cssmenu/jquery-latest.min.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/menu/cssmenu/script.js'/>"></script>
<link rel="StyleSheet" href="pages/menu/tree/tree.css" type="text/css">
<script language="javascript" type="text/javascript" src="pages/menu/tree/tree.js"></script>
<script language="javascript" type="text/javascript">
		var Tree = new Array;
		Tree[0]  = "1|0|Page 1|#";
	<%for(int i=0;i<aListMyBank.size();i++){
		LogInBO obj = (LogInBO)aListMyBank.get(i);
	%>
		Tree[<%=i%>] = "<%=obj.getNodeId()%>|<%=obj.getParentId()%>|<%=obj.getNodeName()%>|<%=obj.getUrl()%>";
	<%}%>
</script>
<link rel="stylesheet" href="pages/menu/switcher/styleswitcher.css" type="text/css">
<link rel="stylesheet" href="pages/menu/switcher/font-awesome.min.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/menu/switcher/styleswitcher.js'/>"></script>
<link rel="stylesheet" href="pages/menu/StyleMenu.css" type="text/css">
<script language="javascript" type="text/javascript">
	function   setBodyProperty() {
  		document.oncontextmenu = function(){return false};
  		document.onselectstart = function(){return false};
  		document.oncopy = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};
		document.onBeforePrint = function(){return false};
		document.onAfterPrint= function(){return false};
  	}

	function doAccountStatement(f){
		f.action="/mybank/transactionInfoEnquiry.do";
		f.submit();
	}
	function doChequeStatus(f){
		f.action="/mybank/chequeBookStatus.do";
		f.submit();
	}
	
	function doFundTransfer(f){
		f.action="/mybank/transferAgree.do";
		f.submit();
	}
	function doTalkTimeRecharge(f){
		f.action="/mybank/transferMobileFlexi.do";
		f.submit();
	}
	function doChequeLeafStop(f){
		f.action="/mybank/chequeBookStop.do";
		f.submit();
	}
	
	function doComposeNetSupport(f){
		f.action="/mybank/composeMessageNetSupport.do";
		f.submit();
	}
	function doPersonalProfile(f){
		f.action="/mybank/personalProfile.do";
		f.submit();
	}
	function doSearch(f){
		f.action="/mybank/transactionInfoEnquiry.do";
		f.submit();
	}
	function doLogOut(f){
		if(confirm("Are you sure? If you confirm press OK button")){
			f.action="/mybank/logOutMyBank.do";
			f.submit();
		}
	}
</script>
</head>
<body onLoad="setBodyProperty()">
<html:form action="/logIn" style="margin: 0">
  <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0" >
    <tr>
    <html:hidden property="userAccountNo"/>
    <html:hidden property="userCompanyCode"/>
      <td style="height:20%; width:100%;" class="header-menu"><div style="text-align:left;line-height:20px;padding-top:10px;padding-left:20px"><img src="pages/menu/images/logo.png"></div>
        <div style="text-align:right;margin-top:-30px"><img src="pages/menu/images/Log-out.png" onClick="doLogOut(logInForm)"></div></td>
    </tr>
    <tr><td><div id='cssmenu'>
            <%
	try {		
		DBCP oPool = DBCP.getInstance();
        Connection oConn = null;
		oConn = oPool.getConnection();
        Statement st = oConn.createStatement();
	
        ResultSet rs = st.executeQuery("select NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL from MYBANK.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and PARENTID='0'  ORDER BY SERNUM");
%>
            <ul>
              <%  		
        while (rs.next()) {
					
	%>
              <li> <a href="#"> <%= rs.getString("NODENAME")%> </a>
                <ul>
                  <%
			Statement st1 = oConn.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM MYBANK.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs.getString("NODEID") + "'");
                while (rs1.next()) {
      %>
                  <li> <a href="<%= rs1.getString("URL")%>"> <%= rs1.getString("NODENAME")%> </a>
                    <ul>
                      <%
					Statement st2 = oConn.createStatement();
                    ResultSet rs2 = st2.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM MYBANK.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs1.getString("NODEID") + "'");
					while (rs2.next()) {
          %>
                      <li> <a href="<%= rs2.getString("URL")%> "> <%= rs2.getString("NODENAME")%> </a>
                        <ul>
                          <%
						Statement st3 = oConn.createStatement();
                        ResultSet rs3 = st3.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM MYBANK.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs2.getString("NODEID") + "'");
                        while (rs3.next()) {
			  %>
                          <li> <a href="<%= rs3.getString("URL")%> "> <%= rs3.getString("NODENAME")%> </a>
                            <ul>
                              <%
							Statement st4 = oConn.createStatement();
                            ResultSet rs4 = st4.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM MYBANK.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs3.getString("NODEID") + "'");
                            while (rs4.next()) {
				  %>
                              <li> <a href="<%= rs4.getString("URL")%> "> <%= rs4.getString("NODENAME")%> </a>
                                <ul>
                                  <%
								Statement st5 = oConn.createStatement();
								ResultSet rs5 = st5.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM MYBANK.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs4.getString("NODEID") + "'");
								while (rs5.next()) {
                      %>
                                  <li> <a href="<%= rs5.getString("URL")%> "> <%= rs5.getString("NODENAME")%> </a> </li>
                                  <%
								}
                                 rs5.close();
                                 st5.close();
                      %>
                                </ul>
                              </li>
                              <%
							}
                            rs4.close();
                            st4.close();
                  %>
                            </ul>
                          </li>
                          <%
						}
                        rs3.close();

                        st3.close();
              %>
                        </ul>
                      </li>
                      <%
					}
                    rs2.close();
                    st2.close();
          %>
                    </ul>
                  </li>
                  <%
				}
                rs1.close();
                st1.close();
      %>
                </ul>
              </li>
              <%
		}
        rs.close();
        st.close();
        oConn.close();
	} catch (Exception e) {
  	out.println("Exception: "+e.getMessage()); 		
	}
  %>
              
            </ul>
          </div></td></tr>
    <tr >
      <td style="height:73%; width:100%;"><table style="width: 100%;" cellpadding="0" cellspacing="0" border="0" >
          <tr>
            <td><table style="width: 100%; align: center" border="0" height="60px">
                <%
				    if(session.getAttribute("oLogInMessageBO")!=null){
					LogInBO mainBO=(LogInBO)session.getAttribute("oLogInMessageBO");
					ArrayList list=mainBO.getList();
					if(list==null)list=new ArrayList();
					for(int i=0;i<list.size();i++){
					LogInBO bo=(LogInBO)list.get(i);
					String sErrorMessage = bo.getErrorMessage();
				%>
                <tr>
                  <td align="center" style="color:#3e7cbb;font-size:13pt;font-weight:bold;font-family:Tahoma, Geneva, sans-serif">&nbsp;</td>
                </tr>
                <%         
					}
		      		}
				%>
              </table></td>
          </tr>
          <tr>
            <td><table style="width: 100%; align: center" border="0">
                <tr>
                  <td width="5%">&nbsp;</td>
                  <td width="29%" valign="top"><div class="panel-border"><table style="width: 100%; align: center;" height="100%">
                      <tr>
                        <td class="panel-header"><img src="pages/menu/images/UserDetails2.png">&nbsp;<font style="font-size:15px;color:#FFF;vertical-align:top"> User Details</font></td>
                      </tr>
                      <tr>
                        <td><div id="tableContainer" class="tableContainer" style="height:300px;">
                            <table width="100%">
                              <tr>
                                <td align="center"><a href="#" onClick="doPersonalProfile(logInForm)"> <img src="<%=sFullPathName%>" width="90px" style="height:90px" alt="<%=sUserTitle%>" border="0"> </a></td>
                              </tr>
                              <tr>
                                <td><table width="100%" style="font-size:12px; margin-top:-6px;">
                                    <tr>
                                      <td width="31%">&nbsp;</td>
                                      <td  width="69%">&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td class="text_content_left" valign="top">Name</td>
                                      <td  class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSUserName"));%></td>
                                    </tr>
                                    <tr>
                                      <td class="text_content_left" valign="top">Address</td>
                                      <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSUserAddress"));%></td>
                                    </tr>
                                    <tr>
                                      <td class="text_content_left" valign="top">Phone no</td>
                                      <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSUserContact"));%></td>
                                    </tr>
                                    <tr>
                                      <td class="text_content_left" valign="top">E-mail</td>
                                      <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSUserEMail"));%></td>
                                    </tr>
                                    <tr>
                                      <td class="text_content_left" valign="top">National ID</td>
                                      <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSUserNationalID"));%></td>
                                    </tr>
                                    <tr>
                                      <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td colspan="2">&nbsp;</td>
                                    </tr>
                                  </table></td>
                              </tr>
                            </table>
                          </div></td>
                      </tr>
                    </table></div></td>
                  <td width="1%">&nbsp;</td>
                  <td width="30%" valign="top"><table style="width: 100%; align: center" border="0" height="100%">
                      <tr height="49%">
                        <td><div class="panel-border"><table style="width: 100%; align: center" border="0" height="100%">
                            <tr>
                              <td class="panel-header"><img src="pages/menu/images/balance.png">&nbsp;<font style="font-size:15px;color:#FFF;vertical-align:top"> User Accounts</font></td>
                            </tr>
                            <tr>
                              <td><div id="tableContainer" class="tableContainer" style="height:120px;">
                                  <table width="100%" style="font-size:12px;">
                                    <tr>
                                      <td width="31%" align="left" class="text_content_left_act" >&nbsp;Account No</td>
                                      <td width="69%" align="right" class="text_content_left_act" >Balance</td>
                                    </tr>
                                    <%
										if(session.getAttribute("oLogInUserBO")!=null){
										LogInBO mainBO=(LogInBO)session.getAttribute("oLogInUserBO");
										ArrayList aAccountList=mainBO.getUserAccountList();
										if(aAccountList==null)aAccountList=new ArrayList();
										for(int i=0;i<aAccountList.size();i++){
										LogInBO bo=(LogInBO)aAccountList.get(i);
										String sUserAccountNo = bo.getUserAccountNo();
										String sUserAccountTitle = bo.getUserAccountTitle();
										String sUserCurrentBalance = bo.getUserCurrentBalance();
										String sUserCurrency = bo.getUserCurrency();
										String sUserCompanyCode = bo.getUserCompanyCode();
										
										%>
                                    <tr>
                                      <td  align="left" class="text_content_left_act"><a href="#" onClick="doSearch(logInForm)">&nbsp;&nbsp;&nbsp;<%=sUserAccountNo%></a></td>
                                      <td  align="right" class="text_content_right"><a href="#" onClick="doSearch(logInForm)"><%=sUserCurrentBalance%> @ <%=sUserCurrency%></a></td>
                                    </tr>
                                    <%         
										}
										}
									  %>
                                    <tr>
                                      <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td colspan="2">&nbsp;</td>
                                    </tr>
                                  </table>
                                </div></td>
                            </tr>
                          </table></div></td>
                      </tr>
                      <tr height="2%">
                        <td>&nbsp;</td>
                      </tr>
                      <tr height="49%">
                        <td><div class="panel-border"><table style="width: 100%; align: center" border="0" height="100%">
                            <tr>
                              <td class="panel-header"><img src="pages/menu/images/LoginHistory2.png">&nbsp;<font style="font-size:15px;color:#FFF;vertical-align:top"> Login History</font></td>
                            </tr>
                            <tr>
                              <td><div id="tableContainer" class="tableContainer" style="height:124px;">
                                  <table width="100%" style="font-size:12px;">
                                    <tr>
                                      <td width="34%" class="text_content_left" valign="top">Today's Login</td>
                                      <td   width="66%" class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSLogInToDay"));%></td>
                                    </tr>
                                    <tr>
                                      <td class="text_content_left" valign="top">Total Login</td>
                                      <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSLogInTotal"));%></td>
                                    </tr>
                                    <tr>
                                      <td class="text_content_left" valign="top">Last Login</td>
                                      <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSLastLogInDate"));%></td>
                                    </tr>
                                  </table>
                                </div></td>
                            </tr>
                          </table></div></td>
                      </tr>
                    </table></td>
                  <td width="1%">&nbsp;</td>
                  <td width="29%" valign="top"><div class="panel-border"><table style="width: 100%; align: center" border="0" height="100%">
                      <tr>
                        <td class="panel-header"><img src="pages/menu/images/QuickLinks2.png">&nbsp;<font style="font-size:15px;color:#FFF;vertical-align:top"> Quick Links</font></td>
                      </tr>
                      <tr>
                        <td><div id="tableContainer" class="tableContainer" style="height:300px;">
                            <p class="text_content_left" style="font-size:13px;margin-bottom:132px">&nbsp;&nbsp;&nbsp;<i class="fa fa-play"></i>&nbsp;<a href="#" onClick="doAccountStatement(logInForm)"><font class="link-b-082">Account Statement</font></a><br/>
                              &nbsp;&nbsp;&nbsp;<i class="fa fa-play"></i>&nbsp;<a href="#" onClick="doFundTransfer(logInForm)"><font class="link-b-082">Fund Transfer</font></a><br/>
                              &nbsp;&nbsp;&nbsp;<i class="fa fa-play"></i>&nbsp;<a href="#" onClick="doChequeLeafStop(logInForm)"><font class="link-b-082">Cheque Leaf Stop</font></a><br/>
                              &nbsp;&nbsp;&nbsp;<i class="fa fa-play"></i>&nbsp;<a href="#" onClick="doChequeStatus(logInForm)"><font class="link-b-082">Status of a Cheque Book</font></a><br/>
                              &nbsp;&nbsp;&nbsp;<i class="fa fa-play"></i>&nbsp;<a href="#" onClick="doTalkTimeRecharge(logInForm)"><font class="link-b-082">Talk Time Recharge</font></a> </p>
                            <p>&nbsp;</p>
                          </div></td>
                      </tr>
                    </table></div></td>
                  <td width="5%">&nbsp;</td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
    <tr><td height="2%">&nbsp;</td></tr>
    <tr>
      <td style="height:5%; width:100%;" class="footer-menu"><p  style="color:#FFF;font-size:1em;font-weight:200;font-family:Verdana, Geneva, sans-serif;vertical-align:bottom;text-align:center;padding-bottom:-100px">
          <bean:message key="label.copyright"/>
        </p></td>
    </tr>
  </table>
</html:form>
</body>
</html>
