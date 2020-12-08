<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.TimeZone"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibank.login.bo.IBankingUserLogInBO"%>
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

<!--      new start-->
<link rel="stylesheet" href="pages/stylesheet/style_login.css" type="text/css" media="all">
<link rel="stylesheet" href="pages/stylesheet/font-awesome.css"> <!-- Font-Awesome-Icons-CSS -->
<link href="pages/stylesheet/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet" /> 
<link rel="stylesheet" href="pages/stylesheet/bootstrap.css" type="text/css" media="all"/>
<script type="text/javascript" src="<html:rewrite page='/pages/javascript/sweetalert/1.1.3/sweetalert.min.js'/>"></script>
<script language="javascript" type="text/javascript" src="pages/js/jquery.min.js"></script>
  <script language="javascript" type="text/javascript" src="pages/js/bootstrap.min.js"></script>
<script language="javascript" type="text/javascript" src="pages/js/scripts.js"></script>

<!--      new end-->
<!--<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">
<link rel="stylesheet" href="pages/scroller/scroller.css" type="text/css">
<link rel="stylesheet" href="pages/stylesheet/keyboard.css" type="text/css">
<script language="javascript" type="text/javascript" src="/pages/scroller/tools.js"></script>
<script language="javascript" type="text/javascript" src="/pages/scroller/scroller.js"></script>
<script language="javascript" type="text/javascript" src="/pages/javascript/keyboard.js"></script>-->
<link rel="shortcut icon" href="pages/images/favicon.ico" type="image/x-icon" />
<link rel="icon" href="pages/images/favicon.ico" type="image/x-icon" />
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Scientist Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />

<style>
.capt {
	color:#001b7c;
	font-size : 16pt;
	font-weight:bolder;
	/*font-family: Verdana, Tahoma, Arial, Helvetica, Sans-serif, sans-serif;*/
	font-family:"Monotype Corsiva";
}
.msg-err {
	color:#F00;
	font-size : 10pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, Sans-serif, sans-serif;
}

.msg-wel {
	color:#0080c0;
	font-size : 10pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, Sans-serif, sans-serif;
}
</style>

<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<script language="javascript" type="text/javascript">
/*
$(document).ready(function(){
    $("#myBtn").click(function(){
        $("#myModal").modal();
    });
});*/

	function setBodyProperty(f) {
  		document.oncontextmenu = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};
		/*document.onselectstart = function(){return false};
  		document.oncopy = function(){return false};
		document.onBeforePrint = function(){return false};
		document.onAfterPrint= function(){return false};*/

		document.ibankingUserLogInForm.userID.value = "era@bankasia.com.bd";
		document.ibankingUserLogInForm.password.value = "Test@123"; 
		document.ibankingUserLogInForm.capchaText.value = ""; 
		
		/*$('input').attr('autocomplete', 'off');
		if(document.ibankingUserLogInForm.errorCode.value == "0"){
			document.getElementById("ms-col").className = 'msg-wel';
		}
		else if(document.ibankingUserLogInForm.errorCode.value == "1"){
			document.getElementById("ms-col").className = 'msg-err';
		}
		else{
				document.getElementById("ms-col").className = 'msg-wel';
		}
		
		
		if(document.ibankingUserLogInForm.userID.value=="" ) {
			document.ibankingUserLogInForm.userID.focus();
		}	
		else if(document.ibankingUserLogInForm.password.value=="" ) {
			document.ibankingUserLogInForm.password.focus();		
		}
		
		*/
		
		if(document.ibankingUserLogInForm.errorCode.value=="1"){
			var v1 ="";
		redirectMobiAppsTransfere(ibankingUserLogInForm,v1);
	
		}
		
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
	
    function goPassword(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.ibankingUserLogInForm.userID.value=="" ) {
				alert('User ID Required');
				document.ibankingUserLogInForm.userID.focus();
			}
			else {
				document.ibankingUserLogInForm.password.focus();
			}
        }
     }
	 function goCaptchaCode(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.ibankingUserLogInForm.password.value=="" ) {
				alert('Password Required');
				document.ibankingUserLogInForm.password.focus();
			}
			else {
				document.ibankingUserLogInForm.capchaText.focus();
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
	window.addEvent('domready', function() {
		var opt = {
			slides: 'qslide2',
			duration: 3000,
			delay: 1000, 
			auto:true,
			direction: 'h',
			transition:Fx.Transitions.Quart.easeIn,
			onMouseEnter: function(){this.stop();},
			onMouseLeave: function(){this.play();}
		}
		var scroller = new QScroller('qscroller2',opt);
			scroller.load();
		}
	);

	function roll(img_name, img_src) {
   		document[img_name].src = img_src;
   	}
	function doSignIn(f) {
		if(document.ibankingUserLogInForm.userID.value=="" ) {
			alert('User ID Required');
			document.ibankingUserLogInForm.userID.focus();
		}
		else if(document.ibankingUserLogInForm.password.value=="" ) {
			alert('PIN Code Required');
			document.ibankingUserLogInForm.password.focus();
		}
		/*else if(document.getElementById('txtInput').value =="" ) {
			alert('Type Captcha Code');
			document.getElementById("txtInput").focus();
		}*/
		
		else{
            f.action="/ibankinguser/ibankingUserLogInSubmit.do";
			f.submit();
		}
	}	
	
	function doMyNewPINForget(f) {
		f.action="/ibankinguser/myNewPINForget.do";
		f.submit();
	}
	function doHelp(f) {
		f.action="/ibankinguser/help.do";
		f.submit();
	}
	function doAboutUs(f) {
		f.action="/ibankinguser/aboutUs.do";
		f.submit();
	}
	function doBranchInfo(f) {
		f.action="/ibankinguser/branchInfo.do";
		f.submit();
	}
	function doATM(f) {
		f.action="/ibankinguser/ATMKioskInformation.do";
		f.submit();
	}
	function doSmsBanking(f) {
		f.action="/ibankinguser/smsBanking.do";
		f.submit();
	}
	function doTeleBanking(f) {
		f.action="/ibankinguser/teleBanking.do";
		f.submit();
	}
	function doPrivacyPolicy(f) {
		f.action="/ibankinguser/privacyPolicy.do";
		f.submit();
	}
	function doSignUp(f) {
		f.action="/ibankinguser/userSignUp.do";
		f.submit();
	}
	function doCopyright(f) {
		f.action="/ibankinguser/copyright.do";
		f.submit();
	}
	function doContactUs(f) {
		f.action="/ibankinguser/contactUs.do";
		f.submit();
	}
	function reloadCaptcha(f) {

				
		//f.action="/ibankinguser/reloadCaptcha.do";
	   //	f.submit();
	$.ajax({
		type: 'post',
		url: '/ibankinguser/reloadCaptcha.do',
		dataType:'json',
		success: function (data) {

	     $("#GSDigit1").text(data.GSDigit1);
		 $("#GSDigit2").text(data.GSDigit2);
		 $("#GSDigit3").text(data.GSDigit3);
		 $("#GSDigit4").text(data.GSDigit4);
		 document.ibankingUserLogInForm.capchaCode.value = data.CaptchaCode;
		}
	});	
		
	}
	
</script>
</head>
<body onLoad="setBodyProperty()">
<html:form action="/ibankingUserLogIn" style="margin: 0">
<html:hidden property="errorCode"/>
<html:hidden property="errorMessage"/>
 <!-- <div id="page-background">
<img src="/ibankinguser/pages/images/corporate1.jpg" max-width="100%" height="100%" alt="My Bank - Explore More" ></div>
-->
<%
response.setHeader("Cache-Control","no-cache,no-store, must-revalidate"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<div class="page_background">

   <div  class="container">     
   
		<div class="logo">
		<a href="http://www.cvcflbd.com/">
			<img src="pages/menu/images/nbfilogo.png" height="80px" width="250px" alt="Bank Asia Limited">
		</a>      
		</div>
        
    <div  align="right"><img src="pages/images/My_Bank_Logo.png"/></div>
    
	<div class=" top-nav">
			<span class="menu"><img src="pages/images/menu.png" alt=""> </span>
				<ul>
					
                   <!-- <li class="active"><a href="index.html">Home</a></li>
 				 <li> <a class="hvr-sweep-to-bottom" href="#" onClick="doAboutUs(ibankingUserLogInForm)">About Us</a></li>
 				 <li> <a class="hvr-sweep-to-bottom" href="#" onClick="doContactUs(ibankingUserLogInForm)">Contact Us</a></li>
 				 <li> <a class="hvr-sweep-to-bottom" href="#" onClick="doBranchInfo(ibankingUserLogInForm)">Branch Info</a></li>
   				<li><a class="hvr-sweep-to-bottom" href="#" onClick="doATM(ibankingUserLogInForm)">ATM</a></li>
   				<li><a class="hvr-sweep-to-bottom" href="#" onClick="doSmsBanking(ibankingUserLogInForm)">SMS Banking</a></li>
                 <li> <a class="hvr-sweep-to-bottom" href="#" onClick="doTeleBanking(ibankingUserLogInForm)">Tele Banking</a></li>
                 <li>  <a class="hvr-sweep-to-bottom" href="#" onClick="doPrivacyPolicy(ibankingUserLogInForm)">Privacy Policy</a></li>
                 <li> <a class="hvr-sweep-to-bottom" href="#" onClick="doSignUp(ibankingUserLogInForm)">SignUp</a></li>-->
				</ul>
				<div class="clearfix"> </div>
                
						
					<script>
						$("span.menu").click(function(){
							$(".top-nav ul").slideToggle(500, function(){
							});
						});
				</script>					
		</div>
</div>
        
	<div class="container">
		<!--content-top-->
		<div class="content-top">
			<div class="content-top1">
			  	<div class=" col-md-4  grid-top">
					<div class="top-grid">
					<!-- <i class="glyphicon glyphicon-book"></i>-->
					  <div class="caption ">
						<h3>New Features</h3>
                        <ul>
						 <li><i class="glyphicon glyphicon-star"> </i>Talktime recharge facilities available.</li>
<li ><i class="glyphicon glyphicon-star"> </i>You will receive an OTP (One Time Password) for Fund transfer for current session once.</li>
<li><i class="glyphicon glyphicon-star"> </i>Check your Mobile message or e-mail (Inbox/Spam) to get current session OTP (One Time Password).</li>
</ul>
					 </div>
				</div>
				</div>
				<div class=" col-md-4  grid-top">
					<div class="top-grid ">
<!--					 <i class="glyphicon glyphicon-time home1 "></i>
-->					  <div class="caption">
						<h3>Security Guidelines</h3>
                        <ul >
  <li><i class="glyphicon glyphicon-alert"> </i>Use virtual keyboard for secure Login.</li>
  <li ><i class="glyphicon glyphicon-alert"> </i>Ensure the address bar URL with https.</li>
  <li ><i class="glyphicon glyphicon-alert"> </i>Change your password periodically.</li>

 </ul>
                              					  </div>
					</div>
				</div>
             
				<!--<div class=" col-md-4 grid-top">
					<div class="top-grid ">
					  <div class="caption">
						<h3>Contact Us</h3>
                        <ul><li><i class="glyphicon glyphicon-envelope"> </i>  For any query please contact directly to our 24/7 call centre <br>
                            &nbsp;16205, +8801713047887, +8801713388648 <br>
                              &nbsp; &nbsp;contactcenter@bankasia.com.bd</li></ul>
						
					  </div>
					</div>
				</div>-->
                <!--Log in box start-->
  
    
      <div class="col-sm-4 form-box  grid-top">
	                        	
<div class="top-grid form-bottom">

 <%
				    if(session.getAttribute("oLogInMessageBO")!=null) {
					IBankingUserLogInBO mainBO=(IBankingUserLogInBO)session.getAttribute("oLogInMessageBO");
					ArrayList list=mainBO.getList();
					if(list==null)list=new ArrayList();
					for(int i=0;i<list.size();i++) {
					IBankingUserLogInBO bo=(IBankingUserLogInBO)list.get(i);
					String sErrorMessage = bo.getErrorMessage();
					%>
                              <tr>
                                <td align="left" valign="top" id="ms-col">&nbsp;<%=sErrorMessage%></td>
                              </tr>
                              <%         
					}
		      		}
				    %>

				                    <form role="form" action="" method="post" class="login-form">
				                    	<div class="form-group">
				                    		<label class="sr-only" for="form-username">Username</label>
				                        	<input type="email" name="userID" placeholder="Username..." class="form-username form-control" id="form-username" value="era@ibankinguser.com">
				                        </div>
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-password">Password</label>
				                        	<input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password" value="A1@a">
				                        </div>
                                         <div class="form-group">
				                        	<label class="sr-only" for="form-captcha">Captcha</label>
				                        	<!--<input type="captcha" name="form-captcha"  class="form-captcha form-control-captcha" id="form-captcha" style="background-image:url(/ibankinguser/pages/images/blueshed.jpg); width:210px" border="0">-->
                                            <div >
                                           <table style="background-image:url(/ibankinguser/pages/images/blueshed.jpg); width:210px" border="0">
                              <tr>
                                <td width="24%" rowspan="2" align="right" valign="middle" class="capt" id="GSDigit1"><strong>
                                  <%
								  if(session.getAttribute("GSDigit1") != " "){
									  out.print(session.getAttribute("GSDigit1"));
								  }
								  else{
								  out.print(" ");
								  }
								  %>
                                  </strong></td>
                                <td width="27%" rowspan="2" align="center" valign="top" class="capt" id="GSDigit2"><strong>
                                  <%
								  if(session.getAttribute("GSDigit2") != " "){
									  out.print(session.getAttribute("GSDigit2"));
								  }
								  else{
								  out.print(" ");
								  }
								  %>
                                  </strong></td>
                                <td width="25%" rowspan="2" align="center" valign="middle" class="capt" id="GSDigit3"><strong>
                                  <%
								  if(session.getAttribute("GSDigit3") != " "){
									  out.print(session.getAttribute("GSDigit3"));
								  }
								  else{
								  out.print(" ");
								  }
								  %>
                                  </strong></td>
                                <td width="24%" rowspan="2" align="center" valign="bottom" class="capt" id="GSDigit4"><strong>
                                  <%
								  if(session.getAttribute("GSDigit4") != " "){
									  out.print(session.getAttribute("GSDigit4"));
								  }
								  else{
								  out.print(" ");
								  }
								  %>
                                  </strong></td>
                                <td width="29%">&nbsp;</td>
                              <td width="39%"  align="left"><img src="pages/images/refresh.gif" style="height:32px;width:32px;text-align:left;position:inherit" align="left"  onClick="reloadCaptcha(ibankingUserLogInForm)"/></td>  
                              </tr>
                              
                            </table>
                                            
                                            </div>
				                        </div>
                                        <div class="form-group">
				                        	<label class="sr-only" for="form-captcha">Captcha Code</label>
				                        	<input  type="captcha" name="capchaText" placeholder="Enter Above Code Here" class="form-captcha form-control" id="txtInput" >
				                        </div>
				                   <button type="submit" onClick="doSignIn(ibankingUserLogInForm)" class="btn"> <i class="glyphicon glyphicon-log-in " ></i>&nbsp;Sign in</button>
              </form>
            </div>
                                
                                
       </div>
    
  
          <!--Log in box end-->  
         
		
            
		</div>
		</div>
		 
	</div>
	

     <div >&nbsp; </div>
   

 <div class="footer">
	<div class="container  ">
    <div><marquee class="marq" scrollamount="10" scrolldelay="200">For any query please contact directly to our +880 (2) 9820038-39, +880 (2) 9820041 contact@cvcflbd.com </marquee></div>
		
		
		
       
		<div class="clearfix"> </div>
        <div class="col-md-12 footer-top2">
			<p >Copyright &copy; 2017. All rights reserved | Design by <a href="http://erainfotechbd.com/" target="_blank">ERA-InfoTech Limited</a> </p>
		</div>
       
	</div>
</div>       
        
        
        
        </div>
 
</html:form>
</body>
</html>
