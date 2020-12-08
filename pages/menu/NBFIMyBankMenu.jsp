<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibank.login.bo.IBankingUserLogInBO"%>
<%@ page language="java" 
		 import ="com.ibank.login.bo.*,java.util.ArrayList"
         session      = "true"
		 autoFlush    = "true"
		 isThreadSafe = "true" 
		 isErrorPage  = "false"
%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibank.dbconnection.application.DBCPNewConnection" %>
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
	String sFullPathName = IBankingUserLogInBO.getReadPath(sUserID,sSessionID);
ArrayList aListMyBank = IBankingUserLogInBO.getMyBankMenu(sUserID,sSessionID);

%>
<html lang="en">
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

<meta charset='utf-8'>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="HandheldFriendly" content="true">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="pages/menu/cssmenu/stylesHeader.css" type="text/css">
<link rel="stylesheet" href="pages/menu/cssmenu/styles.css" type="text/css">

	<link rel="stylesheet" type="text/css" href="https://wowslider.com/sliders/demo-18/engine1/style.css" />
<!--[if IE 6]>
<link rel="stylesheet" href="pages/menu/cssmenu/styles2.css" type="text/css">
<![endif]-->
<script language="JavaScript" src="<html:rewrite page='/pages/menu/cssmenu/jquery-latest.min.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/menu/cssmenu/script.js'/>"></script>
<link rel="StyleSheet" href="pages/menu/tree/tree.css" type="text/css">
<script language="javascript" type="text/javascript" src="pages/menu/tree/tree.js"></script>
<script language="javascript" type="text/javascript">
		var Tree = new Array;
		Tree[0]  = "1|0|Page 1|#";
	<%for(int i=0;i<aListMyBank.size();i++){
		IBankingUserLogInBO obj = (IBankingUserLogInBO)aListMyBank.get(i);
	%>
		Tree[<%=i%>] = "<%=obj.getNodeId()%>|<%=obj.getParentId()%>|<%=obj.getNodeName()%>|<%=obj.getUrl()%>";
	<%}%>
</script>
<link rel="stylesheet" href="pages/menu/switcher/styleswitcher.css" type="text/css">
<link rel="stylesheet" href="pages/menu/switcher/font-awesome.min.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/menu/switcher/styleswitcher.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.9.1.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.3.2.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-ui.js'/>"></script>

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
	function doSearch(f,v1,v2){
		document.ibankingUserLogInForm.userCompanyCode.value = v1;
		document.ibankingUserLogInForm.userAccountNo.value = v2;
		f.action="/mybank/balanceInfoLogIn.do";
		f.submit();
	}
	function doLogOut(f){
		if(confirm("Are you sure? If you confirm press OK button")){
			f.action="/ibankinguser/logOutibank.do";
			f.submit();
		}
	}
	function fontResize(){
		var perc = parseInt($(window).width())/110;
		$('body').css('font-size',perc);
		}
		
		$(document).ready(function (){
			fontResize();
		});
		
		$(window).resize(function () {
			fontResize();
	});
</script>
</head>
<body onLoad="setBodyProperty()" style="background-color:#DCE1FC">
<!--DCE1FC--><!--cbe8fa--><!--E8FFF8-->
<html:form action="/ibankingUserLogIn" style="margin: 0;">
  <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <html:hidden property="statusflag"/>
       <html:hidden property="userAccountNo"/>
      <html:hidden property="userCompanyCode"/>
      <td style="height:20%; width:100%;" class="header-menu"><div style="text-align:left;line-height:20px;padding-top:10px;padding-left:20px"><img src="pages/menu/images/nbfilogo.png"></div>
        <div style="text-align:right;margin-top:-30px"><img src="pages/menu/images/Log-out.png" onClick="doLogOut(ibankingUserLogInForm)"></div></td>
    </tr>
    <tr>
      <td><div id='cssmenu'>
          <%
	try {		
		//DBCPNewConnection oPool = DBCPNewConnection.getConnection();
        Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
        Statement st = oConn.createStatement();
        ResultSet rs = st.executeQuery("select NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL from ibnk.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and PARENTID='0'  ORDER BY SERNUM");
	%>
          <ul>
            <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="pages/menu/images/home.png" height="19px" width="17px" alt="Home" align="middle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
            <%  		
    	while (rs.next()) {
		%>
            <li><a href="#"><%= rs.getString("NODENAME")%> </a>
              <ul>
                <%
			Statement st1 = oConn.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnk.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs.getString("NODEID") + "'");
                while (rs1.next()) {
      %>
                <li> <a href="<%= rs1.getString("URL")%>"><%= rs1.getString("NODENAME")%> </a>
                  <ul>
                    <%
					Statement st2 = oConn.createStatement();
                    ResultSet rs2 = st2.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnk.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs1.getString("NODEID") + "'");
					while (rs2.next()) {
          %>
                    <li> <a href="<%= rs2.getString("URL")%> "> <%= rs2.getString("NODENAME")%> </a>
                      <ul>
                        <%
						Statement st3 = oConn.createStatement();
                        ResultSet rs3 = st3.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnk.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs2.getString("NODEID") + "'");
                        while (rs3.next()) {
			  %>
                        <li> <a href="<%= rs3.getString("URL")%> "> <%= rs3.getString("NODENAME")%> </a>
                          <ul>
                            <%
							Statement st4 = oConn.createStatement();
                            ResultSet rs4 = st4.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnk.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs3.getString("NODEID") + "'");
                            while (rs4.next()) {
				  %>
                            <li> <a href="<%= rs4.getString("URL")%> "> <%= rs4.getString("NODENAME")%> </a>
                              <ul>
                                <%
								Statement st5 = oConn.createStatement();
								ResultSet rs5 = st5.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnk.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs4.getString("NODEID") + "'");
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
        </div></td>
    </tr>
    <tr>
      <td style="height:73%; width:100%;"><table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td><table style="width: 100%; align: center" border="0" height="60px">
               
                <tr>
                 <td align="center" style="color:#3e7cbb;font-size:13pt;font-weight:bold;font-family:Tahoma, Geneva, sans-serif">&nbsp;</td>
                </tr>
               
              </table></td>
          </tr>
          <tr>
            <td><table style="width: 100%; align: center" border="0">
                <tr>
                  <td width="5%"><div id="style-switcher"><a class="close icon-chevron-left">&nbsp;Menu</a>
                      <div id="tableContainer" class="switcherContainer">
                        <div class="tree"><script language="javascript" type="text/javascript">createTree(Tree);</script></div>
                      </div>
                    </div></td>
                
                  <td width="30%" valign="top"><div class="panel-border">
                      <table style="width: 100%; align: center;" height="100%">
                        <tr>
                          <td><table style="width: 100%; align: center;" height="100%">
                                <tr>
                                  <td class="panel-header"><img src="pages/menu/images/UserDetails2.png" height="22px" width="22px">&nbsp;<font style="font-size:1.2em;color:#FFF;vertical-align:top"> User Details</font></td>
                                </tr>
                                <tr>
                                  <td><div id="tableContainer" class="tableContainer" style="height:320px;">
                                      <table width="100%" height="100%">
                                        <tr>
                                          <td align="center"><a href="#" onClick="doPersonalProfile(ibankingUserLogInForm)"><img src="<%=sFullPathName%>" alt="<%=sUserTitle%>" width="90px" height="90px" border="0"></a></td>
                                        </tr>
                                        <tr>
                                          <td><table width="100%" style="font-size:1em; margin-top:-6px;">
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
                                                <td class="text_content_left" valign="top">Today's Login</td>
                                                <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSLogInToDay"));%></td>
                                              </tr>
                                              <tr>
                                                <td class="text_content_left" valign="top">Total Login</td>
                                                <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSLogInTotal"));%></td>
                                              </tr>
                                              <tr>
                                                <td class="text_content_left" valign="top">Last Login</td>
                                                <td class="text_content_right" valign="middle"><%out.print(session.getAttribute("GSLastLogInDate"));%></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                      </table>
                                    </div></td>
                                </tr>
                              </table></td>
                        </tr>
                      </table>
                    </div></td>
                  <td width="3%">&nbsp;</td>
         <td width="25%">   
           <div id="wowslider-container1">
	<div class="ws_images"><ul>
		
		<li><img src="pages/assets/slider/CVCFI.jpg" height="70%" width="100%" alt="css slider code" title="London" id="wows1_0" /></li>		
		<li><img src="pages/assets/slider/SME.jpg" height="70%" alt="css3 slider" title="Shanghai" id="wows1_1" /></li>
        <li><img src="pages/assets/slider/SMEFI.jpg" height="70%" width="100%" alt="css3 slider" title="Shanghai2" id="wows1_2" /></li>
        <li><img src="pages/assets/slider/SustFI.jpg" height="70%" alt="css3 slider" title="Shanghai3" id="wows1_3" /></li>
        <li><a href="http://cssslider.com/"><img src="https://www.bankasia-bd.com/assets/slider/agentBanking.jpg" height="70%" alt="CSS3 Slider" title="New York" id="wows1_4" /></a></li>
        
        
	</ul></div>
	</div>
      
      </td>



                     <td width="1%">&nbsp;</td>
              
                  <td width="3%">&nbsp;</td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td height="2%">&nbsp;</td>
    </tr>
    <tr>
      <td style="height:1%; width:100%;" class="footer-menu" ><p  style="color:#FFF;font-size:1em;font-weight:200;font-family:Verdana, Geneva, sans-serif;vertical-align:top;text-align:center;margin-top:-8px">
          <bean:message key="label.copyright"/>
        </p></td>
    </tr>
  </table>
</html:form>
</body>
</html>
