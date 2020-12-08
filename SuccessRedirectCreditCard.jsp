<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.TimeZone"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.paymentmanagement.login.bo.LogInPmsBO"%>
<html><head>
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
<bean:message key="label.paymentmanagementTitle"/>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<meta charset='utf-8'>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="HandheldFriendly" content="true">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<link rel="stylesheet" href="pages/stylesheet/keyboard.css" type="text/css">
<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">
<!--<link rel="stylesheet" href="pages/scroller/scroller.css" type="text/css">-->
<script language="javascript" type="text/javascript" src="/pages/scroller/tools.js"></script>
<!--<script language="javascript" type="text/javascript" src="/pages/scroller/scroller.js"></script>
--><script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.9.1.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.11.3.min'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/FitText.js-master/jquery.fittext'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-ui.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/calender/CalendarPopup.js'/>"></script>
<script language="JavaScript">var cal = new CalendarPopup();</script>
<script language="javascript" type="text/javascript" src="/pages/javascript/keyboard.js"></script>
<!--   -----------Slide Out Menu JS File-----------------   -->
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.3.2.js'/>"></script>

<style>
.capt {
	color:#ffffcc;
	font-size : 11pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, Sans-serif;
}
</style>

<script language="javascript" type="text/javascript">


	function setBodyProperty(f) {
  		document.oncontextmenu = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};	
		document.onselectstart = function(){return false};
  		document.oncopy = function(){return false};
		document.onBeforePrint = function(){return false};
		document.onAfterPrint= function(){return false};	

  	}
	
function doLogOut(f){
		if(confirm("Are you sure? If you confirm press OK button")){
			f.action="/paymentmanagement/logOutStudentPaymentmanagement.do";
			f.submit();
		}
	
}

		
	function doGenerateVoucher(f){
		
	if(document.logInPmsForm.voucherNumber.value==""){
			
			alert("Reference Number Required..");
			
		}else{	
		
	 if(confirm("If you confirm press OK button")){	
	
		f.action="/paymentmanagement/generateVoucherAfterSuccessRedirect.do";
		f.submit();
		}
	}
}
	function doClear(f){
		
		document.logInPmsForm.voucherNumber.value = "";
		
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
<body onLoad="setBodyProperty()">
<html:form action="/logIn" style="margin: 0"> 
<table width="100%" border="0">

                <tr>
                  <td width="1%">&nbsp;</td>
                  <td width="98%" rowspan="2"><table style="width: 100%; border: 0" cellpadding="3" cellspacing="1" >
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
         <tr>
            <td colspan="2"><div style="background-image:url(pages/images/banner.jpg); background-repeat:repeat-x;height:100px;width:100%">
            <div style="text-align:left;line-height:20px;padding-top:25px;padding-left:20px"><img src="pages/images/logo.png" alt="Bank Asia Limited" width="18%">
            <span style="padding-left:60px;font-size:3.5em;font-family:Monotype Corsiva;color:#DECBFC;text-align:center">The Institute of Chartered Accountants of Bangladesh</span></div>
            <div  class="link-b-081" style="text-align:left;line-height:30px;padding-left:30px;font-size:1.7em;">For A Better Tomorrow</div>
            <div style="text-align:right;margin-top:-85px;padding-right:10px;vertical-align:middle;"><img src="pages/images/logoICAB.png" alt="ICAB" height="80" width="73" ></div>
            </div></td>            
        </tr>
                </table></td>
            </tr>
                    <tr>
            <td valign="top" align="right">
            
             			 <a href="#" onClick="doLogOut(logInPmsForm)" ><font class="btn">
                        <bean:message key="label.signOut"/>
                          </font></a>
    		</td>
          </tr>
  
                      <tr>
                       <td class="msg-00" align="center" valign="top">&nbsp;<strong><%out.print(session.getAttribute("oLogInPmsMessageBO"));%></strong></td>
                      </tr>
    
    
    <tr>
            <td valign="top"><table width="100%" border="0">
      					 <tr>
                          <td width="2%" ></td>
                           <td width="25%" >&nbsp;</td>
                          <td width="2%" ></td>
                          <td width="20%" >&nbsp;</td>
                          <td width="2%" ></td>
                          <td width="20%" >&nbsp;</td>
                          <td width="2%" ></td>
                        </tr>
                 	 <tr>
                          <td class="lbl-n" align="right"></td>
                          <td>&nbsp;</td>
                          <td></td>
                          <td>&nbsp;</td>
                          <td></td>
                          <td>&nbsp;</td>
                          <td></td>
                        </tr>
                        <tr>
                          <td ></td>
                          <td class="lbl-n" align="right">Reference No.</td>
                          <td></td>
                          <td><html:text property="voucherNumber" styleClass="txt-n" style="width:210px"/></td>
                          <td></td>
                          <td>&nbsp;</td>
                          <td></td>
                        </tr>
   
                        <tr>
                          <td  class="lbl-n" align="right" ></td>
                          <td>&nbsp;</td>
                          <td></td>
                          <td>&nbsp;</td>
                          <td></td>
                          <td>&nbsp;</td>
                          <td></td>
                        </tr>
                        
                        <tr>
                          <td class="lbl-n" align="right" ></td>
                          <td>&nbsp;</td>
                          <td></td>
                          <td><html:button style="width:120px" onclick="doGenerateVoucher(logInPmsForm)" value="Generate Voucher" property="button"/>&nbsp;
            				  <html:button style="width:80px" onclick="doClear(logInPmsForm)" value="Clear" property="button"/></td>
                          <td></td>
                          <td>&nbsp;</td>
                          <td></td>
                        </tr>
                           
                        <tr>
                          <td></td>
                          <td>&nbsp;</td>
                          <td></td>
                          <td>&nbsp;</td>
                          <td></td>
                          <td></td>
                          <td></td>
                        </tr>               
   
                       
                    </table></td>                 
                </tr>
    
                    </table></td>
                  <td width="1%">&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                
           
              </table>

</html:form>

</body>
</html>

