<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.TimeZone"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.paymentmanagement.login.bo.LogInPmsBO"%>
<%String sHeaderName = (String) session.getAttribute("GSCompanyName");
  String sCompanyLogo = (String) session.getAttribute("GSCompanyCode");%>
<html lang="en"><head>
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
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-ui.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/tabber.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/calender/CalendarPopup.js'/>"></script>
<script language="JavaScript">var cal = new CalendarPopup();</script>
<script language="javascript" type="text/javascript" src="/pages/javascript/keyboard.js"></script>
<!-------------Slide Out Menu JS File------------------->
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.3.2.js'/>"></script>

<style>
.capt {
	color:#333;
	font-style:italic;
	font-size : 15pt;
	font-weight: bold;
	font-family: Verdana, Tahoma, Arial, Helvetica, Sans-serif, sans-serif;
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
		
		document.logInPmsForm.userID.value = "";
		document.logInPmsForm.password.value = ""; 
		document.logInPmsForm.capchaText.value = ""; 

		if(document.logInPmsForm.userID.value=="" ) {
			document.logInPmsForm.userID.focus();
		}	
		else if(document.logInPmsForm.password.value=="" ) {
			document.logInPmsForm.password.focus();		
		}
		
		if(document.logInPmsForm.user.value=="M"){
	
	document.getElementById("userName").innerHTML="Enrollment";
	}else if(document.logInPmsForm.user.value=="S"){
		
	document.getElementById("userName").innerHTML="Registration ID";	
		}
		
	$(function() {
		 $("#tabs").tabs({ active: document.logInPmsForm.tabIndex.value });    
	 });
	
	ComName();
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

    function goPassword(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.logInPmsForm.userID.value=="" ) {
				alert('User ID Required');
				document.logInPmsForm.userID.focus();
			}
			else {
				document.logInPmsForm.password.focus();
			}
        }
     }	
    function goSignIn(e,f) {
    	if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13){
		    doSignIn(f);
        }
     }

	
	function doSignIn(f) {
		if(document.logInPmsForm.user.value=="" ) {
			alert('User Category Required');
			document.logInPmsForm.user.focus();
		}
		else if(document.logInPmsForm.userID.value=="" ) {
			alert('User ID Required');
			document.logInPmsForm.userID.focus();
		}
		else if(document.logInPmsForm.password.value=="" ) {
			alert('PIN Code Required');
			document.logInPmsForm.password.focus();
		}
		else if(document.logInPmsForm.capchaText.value=="" ) {
			alert('Captcha Code Required');
			document.logInPmsForm.capchaText.focus();
		}

		else{
			/*  var string1 = removeSpaces(document.getElementById('mainCaptcha').value);
              var string2 = removeSpaces(document.getElementById('txtInput').value);
			*/	
                      if (document.logInPmsForm.user.value == "S"){
                     f.action="/paymentmanagement/logInSubmitStudent.do";
					 f.submit();
                      }else if(document.logInPmsForm.user.value == "M"){
						 f.action="/paymentmanagement/logInSubmitMember.do";
						 f.submit();
			
			}
		}     
		
	}
	function doSignUp(f) {
		f.action="/paymentmanagement/newUserRegistration.do";
		f.submit();
	}
	

	<!--dock menu JS options -->
	$.noConflict();
	
	$(document).ready(
		function()
		{
			$('#dock2').Fisheye(
				{
					maxWidth: 60,
					items: 'a',
					itemsText: 'span',
					container: '.dock-container2',
					itemWidth: 40,
					proximity: 80,
					alignment : 'left',
					valign: 'bottom',
					halign : 'center'
				}
			)
		}
	);
	
	<!--slide out  menu JS options -->
	
	  $(function() {
                $('#navigation a').stop().animate({'marginLeft':'-20px'},1000);

                $('#navigation > li').hover(
                    function () {
                        $('a',$(this)).stop().animate({'marginLeft':'-2px'},200);
                    },
                    function () {
                        $('a',$(this)).stop().animate({'marginLeft':'-20px'},200);
                    }
                );
            });
			

			
	function doChangeName(){
		
		if(document.logInPmsForm.user.value=="M"){			
			document.getElementById("userName").innerHTML="Enrollment ID";
			document.logInPmsForm.userID.focus();
			}else if(document.logInPmsForm.user.value=="S"){
			document.logInPmsForm.userID.focus();	
			document.getElementById("userName").innerHTML="Registration ID";	
				}
		}
		
	function goNewUserRegistrationForPayment(f){
		
		f.action="/paymentmanagement/goForNewUserPayment.do";
		f.submit();
		}

	function doRefreshCaptcha(f){	

	$.ajax({
		type: 'post',
		url: '/paymentmanagement/doReFreshCaptchaForPaymentmanagement.do',
		dataType:'json',
		success: function (data) {

	     $("#1").text(data.GSDigit1);
		 $("#2").text(data.GSDigit2);
		 $("#3").text(data.GSDigit3);
		 $("#4").text(data.GSDigit4);
		 $("#5").text(data.GSDigit5);
		 $("#6").text(data.GSDigit6);
	 
		}
	});
}

	function ComName(){	 

		document.getElementById('image').src = "pages/images/"+<%out.print(sCompanyLogo);%>+".png";
			
	}

</script>
		
</head>
<body onLoad="setBodyProperty()">
<html:form action="/memberLogin" style="margin: 0">


<%
response.setHeader("Cache-Control","no-cache,no-store, must-revalidate"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

%>
 <div id="page-background">
  <img src="/paymentmanagement/pages/images/Abstract%20Blue%20backgrounds%2037.jpg" width="100%" height="99%" alt="Bank Asia Limited">
  </div> 
  <table style="width: 100%; height: 100%; align: center; border: 0" >
  <!--------Header START--------->
    <tr height="10%">
     <html:hidden property="tabIndex"/>
      <td colspan="2" valign="top"><table style="width: 100%; border: 0">
          <tr>
            <td colspan="2"><div style="background-image:url(pages/images/index.png); background-repeat:repeat-x;height:100px;width:100%">
                <div style="text-align:left;line-height:20px;padding-top:25px;padding-left:20px"><img src="pages/images/logo.png" alt="Bank Asia Limited" width="18%">
                <span style="padding-left:60px;font-size:3.5em;font-family:Monotype Corsiva;color:#DECBFC;text-align:center"><%out.print(sHeaderName);%></span></div>
                <div  class="link-b-081" style="text-align:left;line-height:30px;padding-left:30px;font-size:1.7em;">For A Better Tomorrow</div>
                <div style="text-align:right;margin-top:-85px;padding-right:10px;vertical-align:middle;"><img src="ComName()" id="image" alt="Bank Asia Limited" height="80" width="73" ></div> </div></td>
          </tr>
          <tr>
            
            <td align="right" class="link-b-081" style="color:#000" colspan="2"><%=new SimpleDateFormat("EEEE MMMM dd, hh:mm:ss a yyyy").format(new Date( new Date().getTime() - TimeZone.getDefault().getRawOffset() - TimeZone.getDefault().getDSTSavings()))
%></td>
          </tr>
      </table></td>
    </tr>
  <!-----------HEADER END--------->
  <!--------BODY START--------->

    <tr  height="75%" style="width:100%;padding-right:6px;padding-left:0px" > <!--<div id="page-backgroundmiddle">
<img src="/paymentmanagement/pages/images/transparent.jpg" width="100%" height="99%" alt="My Bank - Explore More">CEECF5  </div>-->
      <td valign="top"><table style="width: 100%; border: 0">
          <tr>
            <td colspan="3" height="50" valign="top" >
	
	<!--<div class="marquee up">
		<p>Welcome To Internet Banking</p>
		<p>Automate Your Dropbox Files With Actions</p>
	</div>-->
    </td>
         </tr>
          
          <tr>
            <td><table width="100%" border="0">
             <tr valign="top">
            <td width="24%" style="height: 100%" valign="top" class="lbl-08">
                  </td>                  
                  <!------------LOGIN BOX------------>
                  <td width="55em">				
                    <table width="100em" style="background-image:url(pages/images/Blue-lines-background.jpg); background-size:100em; height:3.0em; width:60%; border:1px; solid #66CCFF" align="center">
                     <tr>
                        <td colspan="3" valign="top"><img src="pages/images/Login3.jpg" width="100%" height="50em" style="padding-left:0px"/></td>
                     </tr>
                      
                        
                        <tr>
                          <td colspan="3"><table style="width: 100%; align: center" border="0">                    
                          <td class="msg-10" align="center" valign="top">&nbsp;<strong><%out.print(session.getAttribute("oLogInPmsMessageBO"));%></strong></td>                        
                          </table>
                          </td>
                        </tr>
                        
                  <tr>
                   <td width="30%" class="lbl-n" align="right" >User Category</td>
                   <td width="2%" >&nbsp;</td>
                   <td width="68%">
                   <html:select property="user" styleClass="txt-n" style="width:210px"  onchange="doChangeName()">
                   <html:option value="" styleClass="txt-n">
                   <bean:message key="label.pleaseSelectOne"/>
                   </html:option>
                   <html:options property="userTypeList" styleClass="txt-n" labelProperty="userNameList"/>
                   </html:select>
                     </td>
                    </tr>
                        
                       
                  <tr>
                    <td width="30%" class="lbl-n" align="right" ><img src="pages/images/user.png" width="34" height="20" style="vertical-align:middle"/><span id="userName">User ID</span></td>
                    <td width="2%" >&nbsp;</td>
                    <td width="68%" id="inter"  onKeyPress="goPassword(event);"><html:text property="userID"   style="width:210px" tabindex="1"  styleClass="keyboardInput;txt-n"/> </td>
                 </tr>
                        <tr>                          
                          <td width="100%" colspan="3" class="lbl-n" style="font-size:10px;color:#69F" align="center">(e.g. username@domainname)</td>
                        </tr>
                        
                       <tr>
                        <td width="30%" class="lbl-n" align="right"><img src="pages/images/key.png" width="34" height="20" style="vertical-align:middle"/>Password</td>
                        <td width="2%" >&nbsp;</td>
                        <td width="68%" onKeyPress="goSignIn(event,logInPmsForm);"><html:password property="password" tabindex="2" style="txt-n; width:210px" styleClass="keyboardInput"/></td>
                       </tr>
                           
                     <tr>
                        <td colspan="3" class="lbl-n" style="font-size:10px;color:#69F" align="center">(Password is case-sensitive)</td>
                     </tr>
                     
                 <tr>
                  <tr>
                       <td width="30%" class="lbl-n" align="right">Captcha</td>
                       <td width="2%" >&nbsp;</td>       				 
                       <td colspan="2" width="80%"><table width="100%" border="0">
                     	
                     <tr>
                        <td width="54%" align="left">                       
                        <table style="background-image:url(pages/images/back_captcha.jpg); width:210px" border="0">
                              <tr>                              
                                <td width="10%" rowspan="2" align="center" valign="middle" class="capt" id="1" ><strong>
                                  <%out.print(session.getAttribute("GSDigit1"));%>
                                  </strong></td>
                                <td width="10%" rowspan="2" align="center" valign="top" class="capt" id="2"><strong>
                                  <%out.print(session.getAttribute("GSDigit2"));%>
                                  </strong></td>
                                <td width="10%" rowspan="2" align="center" valign="middle" class="capt" id="3"><strong>
                                  <%out.print(session.getAttribute("GSDigit3"));%>
                                  </strong></td>
                                <td width="10%" rowspan="2" align="center" valign="bottom" class="capt" id="4"><strong>
                                  <%out.print(session.getAttribute("GSDigit4"));%>
                                  </strong></td>                   
                               
                               <td width="1%">&nbsp;</td>
                              </tr>
                          <tr>
                             <td>&nbsp;</td>
                          </tr>                
                            </table>                        
                            </td>
                        <td width="46%" align="left" onClick="doRefreshCaptcha(logInPmsForm)"><img src="pages/images/refresh.gif" height="30px" width="30px"></td>
                     </tr>                   
                                                  
                           </table> </td>                           
                        </tr>
                    
                        <tr>
                          <td width="30%" class="lbl-n" align="right"></td>
                           <td width="2%" >&nbsp;</td>
                          <td width="68%" onKeyPress="goSignIn(event, logInPmsForm);"><html:text property="capchaText" styleId="txtInput" tabindex="3" style="txt-n; width:210px"/></td>
                        </tr>
                        
                       <tr>
                          <td colspan="3" valign="top" class="lbl-cn">&nbsp;</td>
                       </tr>
                                                  
                        <tr>
                          <td height="40%" colspan="3"><p class="p-container" align="center"><a href="#" onClick="doSignIn(logInPmsForm)" ><img src="pages/images/loginBDBL.png" height="35px" width="158px" /></a></p></td>
                        </tr>
                        
                        <tr>
                          <td align="right" colspan="3" ><a href="#" onClick="goNewUserRegistrationForPayment(logInPmsForm)" style="color:#F00;text-decoration:underline">Payment For New Student</a></td>
                        </tr>                        
                      </table>
                  </td>
                 <td width="21%" class="news-demo"></td> 
                </tr>
              </table></td>
          </tr>          
        </table></td>
    </tr>

    <tr>
      <td valign="bottom">
      		<table width="100%"   class="footer-menu" style="width: 100%; border: 0;" >
                <tr style="background-image:url(/pages/images/index.png);background-repeat:repeat-x;height:100%;width:100%;">
                  <td width="6%">&nbsp;</td>
                  <td width="84%" align="center" valign="middle" class="link-b-081">                   
                    <a href="#" onClick="doCopyright(logInPmsForm)"><font class="fot-01"><bean:message key="label.copyright"/></font></a></td>
                  <td width="10%"  align="center" ><img src="pages/images/eraLogo.bmp" height="41" width="136" align="right"></td>
                </tr>
            </table>
      </td>
    </tr>
          <!--------FOOTER END--------->

  </table>
  
</html:form>
</body>
</html>
