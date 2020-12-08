<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibank.loan.report.bo.LoanAgainstDepositStatusBO"%>
<%@ page language="java" 
		 import ="com.ibank.loan.report.bo.*,java.util.ArrayList"
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
<link href="pages/stylesheet/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet" /> 
<!--[if IE 6]>
<link rel="stylesheet" href="pages/menu/cssmenu/styles2.css" type="text/css">
<![endif]-->
<script type="text/javascript" src="<html:rewrite page='/pages/javascript/sweetalert/1.1.3/sweetalert.min.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/menu/cssmenu/jquery-latest.min.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/menu/cssmenu/script.js'/>"></script>
<link rel="StyleSheet" href="pages/menu/tree/tree.css" type="text/css">
<script language="javascript" type="text/javascript" src="pages/menu/tree/tree.js"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/calender/CalendarPopup.js'/>"></script>
<script language="JavaScript">var cal = new CalendarPopup();</script>
<link rel="stylesheet" href="pages/stylesheet/div-table.css" type="text/css">
<link rel="stylesheet" href="pages/assets/bootstrap/css/bootstrap.min.css">        
<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">
<link rel="stylesheet" href="pages/stylesheet/example.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/calender/CalendarPopup.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/tabber.js'/>"></script>
<link rel="stylesheet" href="pages/stylesheet/jquery-ui.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.9.1.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-ui.js'/>"></script> 
<link rel="stylesheet" href="pages/stylesheet/1.12.1themesbasejquery-ui.css" type="text/css">
<link rel="stylesheet" href="/resources/demos/style.css"> 

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
		
		if(document.loanAgainstDepositStatusReportForm.errorCode.value=="1"){
			var v1 ="";
		redirectMobiAppsTransfere(loanAgainstDepositStatusReportForm,v1);
	
		}
  	}
	
	
	function redirectMobiAppsTransfere(f,v1){
	  
	
	var ErrorMessage ="";
	if(v1==""){
		ErrorMessage = document.loanAgainstDepositStatusReportForm.errorMessage.value;
		}else{
		ErrorMessage = v1;
		}

	swal("Alert", ErrorMessage, "warning");
	}
	
	 $(document).ready(
                    /* This is the function that will get executed after the DOM is fully loaded */
	function () {
	
			$("#datepicker").datepicker({
				dateFormat: 'dd/mm/yy',//this option for formatting a Date
				changeMonth: true, //this option for allowing user to select month
				changeYear: true, //this option for allowing user to select from year range
				yearRange: "-50:+50"
			});
			
			$("#datepicker1").datepicker({
				dateFormat: 'dd/mm/yy',//this option for formatting a Date
				changeMonth: true, //this option for allowing user to select month
				changeYear: true, //this option for allowing user to select from year range
				yearRange: "-50:+50"
			});		
						
		   }
		);
	

	function doAccountStatement(f){
		f.action="/ibanking/transactionInfoEnquiry.do";
		f.submit();
	}
	function doChequeStatus(f){
		f.action="/ibanking/chequeBookStatus.do";
		f.submit();
	}
	function doFundTransfer(f){
		f.action="/ibanking/transferAgree.do";
		f.submit();
	}
	function doTalkTimeRecharge(f){
		f.action="/ibanking/transferMobileFlexi.do";
		f.submit();
	}
	function doChequeLeafStop(f){
		f.action="/ibanking/chequeBookStop.do";
		f.submit();
	}
	function doComposeNetSupport(f){
		f.action="/ibanking/composeMessageNetSupport.do";
		f.submit();
	}
	function doPersonalProfile(f){
		f.action="/ibanking/personalProfile.do";
		f.submit();
	}
	function doSearch(f,v1,v2){
		document.loanAgainstDepositStatusReportForm.userCompanyCode.value = v1;
		document.loanAgainstDepositStatusReportForm.userAccountNo.value = v2;
		f.action="/ibanking/balanceInfoLogIn.do";
		f.submit();
	}
	function doLogOut(f){
		if(confirm("Are you sure? If you confirm press OK button")){
			f.action="/ibankinguser/logOutibank.do";
			f.submit();
		}
	}
	
	
		function doExecutePro(f){
		if(confirm("Are you sure? If you confirm press OK button")){
			f.action="/ibankinguser/ReportLoanAgainstDepositStatus.do";
			f.submit();
		}
	}
	
	function doExecutePDF(f){
	
			f.action="/ibankinguser/ReportNBFTransactionStatement.do";
			f.submit();
		}
		
		function doClear(f){
		f.action="/ibankinguser/loanAgainstDepositStatusReport.do";
		f.submit();
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
	
	function goHomePage(f){
		if(confirm("Are you sure? If you confirm press OK button")){
			f.action="/ibankinguser/goBackHomePage.do";
			f.submit();
		}
	}
</script>


<style type="text/css">
.hiddenDiv {
	display: none;
}
.visibleDiv {
	display: block;
}
.show {
	display: compact;
}
.hideRow {
	display: none;
}

tr.spaceUnder>td {
  padding-bottom: 0.3em !important;
}

.ui-datepicker {
    background: #94A3DC;
    border: 1px solid #555;
    color: #EEE;
}
	
	
#table_box
{
    width:100%;
    font-size: 13px;
    border-collapse: collapse;
    text-align:center;
}
#table_box th
{
    padding:7px;
    color: #292929;
}


#table_box tr:nth-child(odd)  { background-color:#ffffff } /*odd*/
#table_box tr:nth-child(even) { background-color:#e6e6e6} /* even*/
#table_box tr:hover { background-color:#fffbae; } /* hovering */

</style>

<style>

.tabletrtd{
	
 cellpadding= 3;
 cellspacing= 1;
	
	}

.modelul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
	 border: 1px solid black;
	
}

.modelli {
    float: left;
}

.modelli{
    display: block;
    color: #000; 
    padding: 16px;
    text-decoration: none;
	
}

.modalHeight{
	
	padding-top:60px !important;
	
	}
.modal-header{
	
	background-color:#103370 !important;
	color:#FFF !important;
	
	}

.body-clr{
	
	background-color:#e6e6e6 !important;
	
	}
	
#modal-terms-label{
	
	color:#FFF;
	}

</style>

</head>
<body onLoad="setBodyProperty()" style="background-color:#DCE1FC">
<!--DCE1FC--><!--cbe8fa--><!--E8FFF8-->
<html:form action="/loanAgainstDepositStatusReport" style="margin: 0;">

  <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <html:hidden property="errorCode"/>
		<html:hidden property="errorMessage"/>

      <td style="height:20%; width:100%;" class="header-menu"><div style="text-align:left;line-height:20px;padding-top:10px;padding-left:20px"><img src="pages/menu/images/nbfilogo.png"></div>
        <div style="text-align:right;margin-top:-30px"><img src="pages/menu/images/Log-out.png" onClick="doLogOut(loanAgainstDepositStatusReportForm)"></div></td>
    </tr>
    <tr>
      <td><div id='cssmenu'>
          <%
	try {		
	//	DBCP oPool = DBCP.getInstance();
        Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
        Statement st = oConn.createStatement();
        ResultSet rs = st.executeQuery("select NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL from ibnkdb.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and PARENTID='0'  ORDER BY SERNUM");
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
            ResultSet rs1 = st1.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnkdb.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs.getString("NODEID") + "'");
                while (rs1.next()) {
      %>
                <li> <a href="<%= rs1.getString("URL")%>"><%= rs1.getString("NODENAME")%> </a>
                  <ul>
                    <%
					Statement st2 = oConn.createStatement();
                    ResultSet rs2 = st2.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnkdb.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs1.getString("NODEID") + "'");
					while (rs2.next()) {
          %>
                    <li> <a href="<%= rs2.getString("URL")%> "> <%= rs2.getString("NODENAME")%> </a>
                      <ul>
                        <%
						Statement st3 = oConn.createStatement();
                        ResultSet rs3 = st3.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnkdb.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs2.getString("NODEID") + "'");
                        while (rs3.next()) {
			  %>
                        <li> <a href="<%= rs3.getString("URL")%> "> <%= rs3.getString("NODENAME")%> </a>
                          <ul>
                            <%
							Statement st4 = oConn.createStatement();
                            ResultSet rs4 = st4.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnkdb.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs3.getString("NODEID") + "'");
                            while (rs4.next()) {
				  %>
                            <li> <a href="<%= rs4.getString("URL")%> "> <%= rs4.getString("NODENAME")%> </a>
                              <ul>
                                <%
								Statement st5 = oConn.createStatement();
								ResultSet rs5 = st5.executeQuery("SELECT NODEID, PARENTID, TRIM(SUBSTR(NODENAME,INSTR(NODENAME,'-')+1)) NODENAME, URL FROM ibnkdb.SY_MAINMENU WHERE MAILID = '"+sUserID +"' AND SESSIONID='"+sSessionID +"' and  PARENTID='" + rs4.getString("NODEID") + "'");
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
  	//out.println("Exception: "+e.getMessage()); 		
	}
  %>
          </ul>
        </div></td>
    </tr>
    <tr>
      <td style="height:73%; width:100%;">
      <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td><table style="width: 100%; align: center" border="0" height="22px">
               <tr>
                          <td class="panel-header" align="center">
                       &nbsp;<font style="font-size:1.2em;color:#FFF;vertical-align:top">Loan Against Deposit (LAD) Status</font></td></tr>
              </table></td>
          </tr>
          <tr>
            <td><table style="width: 100%; align: center" border="0" height="22px">
               <tr>
                          <td align="right"><a href="#" onClick="goHomePage(loanAgainstDepositStatusReportForm)">
                    <font style="font-size:1.2em;color:#0000ff;vertical-align:top">Home</font></a></td></tr>
              </table></td>
          </tr>
          <tr>
            <td><table style="width: 100%; align: center" border="0">
                <tr>
                  <td width="5%"></td>
                  
                  <td width="1%">&nbsp;</td>
                  <td width="10%" valign="top"><div>
                      <table style="width: 100%; align: center;" height="60%">
                        
                        <tr>
                          <td>
                              <table width="100%" style="font-size:1em;">
                              
                             <tr>
                                  <td width="35%">&nbsp;</td>
                                  <td width="3%">&nbsp;</td>
                                  <td width="60%">&nbsp;</td>
                                </tr>
                                
                             <tr>
                                  <td>&nbsp;</td>
                                  <td>&nbsp;</td>
                                  <td>&nbsp;</td>
                                </tr>
                                
                                
                                <tr class="spaceUnder">
                                  <td align="right">&nbsp;Account No</td>
                                  <td></td>
                                  <td align="left"><html:select property="accountNo" style="width:210px">
                                                <html:option value="" styleClass="txt-n">
                                                  <bean:message key="label.pleaseSelectOne"/>
                                                </html:option>
                                                <html:options property="accountInfoList" styleClass="txt-n" labelProperty="accountInfoNameList"/>
                                              </html:select>
             				 </td>
                                </tr>
                                <tr class="spaceUnder">
                                 <td align="right">As On Date</td>
                                  <td>&nbsp;</td>
                                   <td><html:text property="asOnDate" styleClass="txt-n" styleId="datepicker" style="width:210px; text-align: left;"/></td>
                                </tr>
                                
                                <tr>
                                 <td>&nbsp;</td>
                                  <td>&nbsp;</td>
                                   <td>&nbsp;</td>
                                </tr>
                                <tr>
                                  <td align="right"><!--<input type="button" value="PDF"   onClick="doExecutePDF(loanAgainstDepositStatusReportForm)" style="width:100px;text-align:center;color:#0000ff; font-size : 10pt;font-weight:bold" /> --></td>
                                  <td>&nbsp;</td>
                                   <td><input type="button" value="Submit"   onClick="doExecutePro(loanAgainstDepositStatusReportForm)" style="width:100px;text-align:center;color:#0000ff; font-size : 10pt;font-weight:bold" />
            <input type="button" value="Clear"  onClick="doClear(loanAgainstDepositStatusReportForm)" style="width:100px;text-align:center;color:#0000ff; font-size : 10pt;font-weight:bold"/></td>
                                </tr>
                                 <tr class="spaceUnder">
                                  <td align="right">&nbsp;</td>
                                  <td></td>
                                  <td align="left"></td>
                                </tr>
                              </table>
                            </td>
                        </tr>
                      </table>
                    </div></td>
                  <td width="1%">&nbsp;</td>
                  
                  <td width="5%">&nbsp;</td>
                </tr>
              </table></td>
          </tr>
        </table></td>
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
