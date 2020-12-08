<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibank.login.bo.IBankingUserLogInBO"%>
<%String sHeaderName = (String) session.getAttribute("GSCompanyName");
  String sCompanyLogo = (String) session.getAttribute("GSCompanyCode");%>
<html>
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
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <link href="pages/stylesheet/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">
<link rel="stylesheet" href="pages/stylesheet/example.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/calender/CalendarPopup.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/tabber.js'/>"></script>
<link rel="stylesheet" href="pages/stylesheet/jquery-ui.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.9.1.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-ui.js'/>"></script>
<script language="javascript" type="text/javascript">
	function   setBodyProperty() {
  		document.oncontextmenu = function(){return false};
  		document.oncopy = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};
		
		/*document.ibankingUserLogInForm.passwordOld.value = "";
		document.ibankingUserLogInForm.passwordNew.value = "";
		document.ibankingUserLogInForm.passwordConf.value = "";
		document.ibankingUserLogInForm.passwordExpiredDays.value = "";
		*/
		if(document.ibankingUserLogInForm.passwordOld.value=="" ) {
			document.ibankingUserLogInForm.passwordOld.focus();
		}
		
	if(document.ibankingUserLogInForm.errorCode.value=="1"){
			var v1 ="";
		redirectMobiAppsTransfere(ibankingUserLogInForm,v1);
	
		}else if(document.ibankingUserLogInForm.errorCode.value=="0"){
			
			redirectMobiAppsSuccess(ibankingUserLogInForm,v1);
			}
		
		//ComName();
	}
	
 function redirectMobiAppsTransfere(f,v1){	  
	
	var ErrorMessage ="";
	if(v1==""){
		ErrorMessage = document.ibankingUserLogInForm.errorMessage.value;
		}else{
		ErrorMessage = v1;
		}

	swal("Alert", ErrorMessage, "warning");
	}
	
	function redirectMobiAppsSuccess(f,v1){	  
	
	var ErrorMessage ="";
	if(v1==""){
		ErrorMessage = document.ibankingUserLogInForm.errorMessage.value;
		}else{
		ErrorMessage = v1;
		}

	swal("Alert", ErrorMessage, "success");
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



	function doMainMenu(f){
		f.action="/mybank/cancelMyPINChange.do";
		f.submit();
	}
	function doLogOut(f){
		if(confirm("Are you sure? If you confirm press OK button")){
			f.action="/paymentmanagement/logOutStudentPaymentmanagement.do";
			f.submit();
		}
	}
	function doUpdate(f){
				alert("ghgj");
		alert(document.ibankingUserLogInForm.passwordOld.value);
		alert(document.ibankingUserLogInForm.passwordNew.value);
		alert(document.ibankingUserLogInForm.passwordConf.value);
	   if(document.ibankingUserLogInForm.passwordOld.value=="" ) {
			swal("Alert", "Old PIN Code Required !", "warning");
			document.ibankingUserLogInForm.passwordOld.focus();
		}
		else if(document.ibankingUserLogInForm.passwordNew.value=="" ) {
			swal("Alert", "New PIN Code Required !", "warning");
			document.ibankingUserLogInForm.passwordNew.focus();
		}
		else if(document.ibankingUserLogInForm.passwordConf.value=="" ) {
			swal("Alert", "Confirm PIN Code Required !", "warning");
			document.ibankingUserLogInForm.passwordConf.focus();
		}
		else {
			if(document.ibankingUserLogInForm.passwordNew.value==document.ibankingUserLogInForm.passwordConf.value) {
				
					f.action="/ibankinguser/myPINUpdate.do";
					f.submit();
				
			}
			else{
				swal("Alert", "Confirm PIN Code not match !", "warning");
				document.ibankingUserLogInForm.passwordConf.focus();
			}	
		}
	}
	 function goOldPIN(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.ibankingUserLogInForm.userID.value=="" ) {
				alert('User ID Required');
				document.ibankingUserLogInForm.userID.focus();
			}
			else {
				document.ibankingUserLogInForm.passwordOld.focus();
			}
        }
    }	
	
    function goNewPIN(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.ibankingUserLogInForm.passwordOld.value=="" ) {
				alert('Old PIN Code Required');
				document.ibankingUserLogInForm.passwordOld.focus();
			}
			else {
				document.ibankingUserLogInForm.passwordNew.focus();
			}
        }
    }	
    function goConfirmNewPIN(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.ibankingUserLogInForm.passwordNew.value=="" ) {
				alert('New PIN Code Required');
				document.ibankingUserLogInForm.passwordNew.focus();
			}
			else {
				document.ibankingUserLogInForm.passwordConf.focus();
			}
        }
    }	
    function goExpiredDate(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.ibankingUserLogInForm.passwordConf.value=="" ) {
				alert('Confirm PIN Code Required');
				document.ibankingUserLogInForm.passwordConf.focus();
			}
			else {
				if(document.ibankingUserLogInForm.passwordNew.value==document.ibankingUserLogInForm.passwordConf.value ) {

					document.ibankingUserLogInForm.passwordExpiredDays.focus()
				}
				else{
					alert('Confirm PIN Code not match');
					document.ibankingUserLogInForm.passwordConf.focus();
				}	
			}
        }
    }	
    function goUpdate(e,f) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.ibankingUserLogInForm.passwordExpiredDays.value=="" ) {
				swal("Alert", "No of expiry days Required!", "warning");
				document.ibankingUserLogInForm.passwordExpiredDays.focus();
			}
			else {
				doUpdate(f);
			}
        }
    }	
	
	function doClear(f){
		f.action="/paymentmanagement/myPINChange.do";
		f.submit();
	}
	
	function doMenuList(f){
		f.action=document.ibankingUserLogInForm.menu.value;
		f.submit();
	}
	
	function ComName(f){	 
		alert(document.ibankingUserLogInForm.passwordOld.value);
		alert(document.ibankingUserLogInForm.passwordNew.value);
		alert(document.ibankingUserLogInForm.passwordConf.value);
		//document.getElementById('image').src = "pages/images/"+<%out.print(sCompanyLogo);%>+".png";
			
	}
</script>
</head>
<body onLoad="setBodyProperty()">
<html:form action="/myPINChange" style="margin: 0">
<html:hidden property="errorCode"/>
<html:hidden property="errorMessage"/>
  <table style="width: 100%; height: 100%; align: center; border: 0">
    <tr>
      <td style="height: 50"><table style="width: 100%; align: center">
          <tr valign="top">
           <td  valign="top" width="100%">
  		 <div style="background-image:url(pages/images/banner.jpg); background-repeat:repeat-x;height:100px;width:100%">
        <div style="text-align:left;line-height:20px;padding-top:25px;padding-left:20px"><img src="pages/menu/images/nbfilogo.png" alt="Bank Asia Limited" width="18%" height="30%">
        <span style="padding-left:60px;font-size:3.5em;font-family:Monotype Corsiva;color:#DECBFC;text-align:center"><%out.print(sHeaderName);%></span></div>
        <div  class="link-b-081" style="text-align:left;line-height:30px;padding-left:30px;font-size:1.7em;">For A Better Tomorrow</div>
 		<div style="text-align:right;margin-top:-85px;padding-right:10px;vertical-align:middle;"><img src="ComName()" id="image" alt="ICAB Explore More" height="80" width="73" ></div>
              </div></td>
          </tr>
 <%----%><tr>            
            <td  align="right">            
		 <a href="#" onClick="doLogOut(ibankingUserLogInForm)">
         <font class="usr-0">
              <bean:message key="label.signOut"/>
              </font></a>
			  </td>
          </tr>
          <tr>
            <td colspan="2" valign="top"><table style="width: 100%; align: center" border="0">
                <tr>
                  <td colspan="3"></td>
                </tr>
                <tr>
                  <td width="3%" valign="top"></td>
                  <td valign="top" class="lbl-n"><div align="justify">A strong PIN code is one that's difficult for others to determine by guessing or by using automated programs. A strong PIN code is an important first step in protecting your personal information.</div></td>
                  <td width="3%"></td>
                </tr>
                <tr>
                  <td width="3%">&nbsp;</td>
                  <td valign="top" class="lbl-n">New PIN Code can not blank space, same as Old PIN Code. It should contain four or more characters. Can not same as your ID.</td>
                  <td width="3%">&nbsp;</td>
                </tr>
                <tr>
                  <td width="3%">&nbsp;</td>
                  <td valign="top" class="lbl-n">A strong PIN code should uses at least one uppercase letters (<strong>A, B, C</strong>), lowercase letters (<strong>a, b, c</strong>), numerals (<strong>1, 2, 3</strong>)</td>
                  <td width="3%">&nbsp;</td>
                </tr>
                <tr>
                  <td width="3%">&nbsp;</td>
                  <td valign="top" class="lbl-n">and special characters (<strong>~ ` ! @ # $ % [ { ( ) } ] \ | , ; _ : + * / -</strong>).</td>
                  <td width="3%">&nbsp;</td>
                </tr>
                <tr>
                  <td colspan="3"></td>
                </tr>
                <tr>
                  <td width="3%">&nbsp;</td>
                  <td valign="top" class="lbl-n">Please keep in mind that PIN code is case sensitive.</td>
                  <td width="3%">&nbsp;</td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td colspan="4" valign="top"><table style="width: 100%; align: center" border="0">
                <tr>
                  <td width="1%">&nbsp;</td>
                  <td width="98%" rowspan="2"><table style="width: 100%; align: center" border="0">
              <td class="msg-10" align="center" valign="top"><strong><%out.print(session.getAttribute("oMyPINChangeMessageBO"));%></strong></td>
                    </table></td>
                  <td width="1%">&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td valign="top"><table style="width: 100%; align: center" border="0">
           <tr>
            <td width="23%">&nbsp;</td>
            <td width="17%" class="lbl-n">User ID </td>
            <td width="35%" onKeyPress="goOldPIN(event);"><html:text property="userID"  styleClass="txt-n" style="width:210px"/>
            </td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td width="23%">&nbsp;</td>
            <td width="17%" class="lbl-n"><bean:message key="label.oldPassword"/></td>
            <td width="35%" onKeyPress="goNewPIN(event);"><html:password property="passwordOld"  styleClass="txt-n" style="width:210px"/>
            </td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td class="lbl-n"><bean:message key="label.newPassword"/></td>
            <td onKeyPress="goConfirmNewPIN(event);"><html:password property="passwordNew"  styleClass="txt-n" style="width:210px"/>
            </td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td class="lbl-n"><bean:message key="label.conformPassword"/></td>
            <td onKeyPress="goExpiredDate(event);"><html:password property="passwordConf" styleClass="txt-n" style="width:210px"/>
            </td>
            <td>&nbsp;</td>
          </tr>
          
          <tr>
            <td colspan="4"><table style="width: 100%; align: center" border="0">
                <td align="center"><a href="#" onClick="doUpdate(ibankingUserLogInForm)"><font class="btn">
                    <bean:message key="label.submit"/>
                    </font></a> <font class="usr-1">
                    <bean:message key="label.|"/>
                    </font> <a href="#" onClick="doClear(ibankingUserLogInForm)"><font class="btn">
                    <bean:message key="label.clear"/>
                    </font></a> </td>
              </table></td>
          </tr>
        </table></td>
    </tr>
    <tr valign="bottom">
      <td colspan="4"><table style="width: 100%; align: center" border="0">
          <tr>
            <td width="3%" valign="top">&nbsp;</td>
            <td width="94%" valign="top" class="lbl-b"><div align="justify">It is strongly recommended that you should change your PIN code at regular interval. You can set PIN Code expiry days between 1 and 90 days.</div></td>
            <td width="3%" valign="top">&nbsp;</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td valign="bottom"><table style="width: 100%; align: center" border="0">
          <tr>
            <td colspan="2" valign="middle" class="lbl-cb" align="center"><bean:message key="label.copyright"/></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
<script type="text/javascript" src="<html:rewrite page='/pages/javascript/sweetalert/1.1.3/jquery.min.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/pages/javascript/sweetalert/1.1.3/sweetalert.min.js'/>"></script>
</html>
