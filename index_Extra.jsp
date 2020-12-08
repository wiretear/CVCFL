<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.TimeZone"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.paymentmanagement.login.bo.LogInBO"%>
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

<link rel="stylesheet" href="pages/stylesheet/keyboard.css" type="text/css">
<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">
<!--<link rel="stylesheet" href="pages/scroller/scroller.css" type="text/css">-->
<script language="javascript" type="text/javascript" src="/pages/scroller/tools.js"></script>
<!--<script language="javascript" type="text/javascript" src="/pages/scroller/scroller.js"></script>
--><script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.9.1.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-ui.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/calender/CalendarPopup.js'/>"></script>
<script language="JavaScript">var cal = new CalendarPopup();</script>
<script language="javascript" type="text/javascript" src="/pages/javascript/keyboard.js"></script>
<!--   -----------Slide Out Menu JS File-----------------   -->
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.3.2.js'/>"></script>



<script language="javascript" type="text/javascript">


	function setBodyProperty(f) {
  		document.oncontextmenu = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};		

		if(document.logInForm.userID.value=="" ) {
			document.getElementById("userID").focus();
		}	
		else if(document.logInForm.password.value=="" ) {
			document.getElementById("password").focus();		
		}
	Captcha();	
  	}
	
	 function Captcha(){
                     var alpha = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','5','6','7','8','9');
                 //     var fontType = [ "Arial", "Mistsal", "Helvetica", "Courier", "italic"];
					 var i;
                     for (i=0;i<6;i++){
                       var a = alpha[Math.floor(Math.random() * alpha.length)];
                       var b = alpha[Math.floor(Math.random() * alpha.length)];
                       var c = alpha[Math.floor(Math.random() * alpha.length)];
                       var d = alpha[Math.floor(Math.random() * alpha.length)];
                       var e = alpha[Math.floor(Math.random() * alpha.length)];
                       var f = alpha[Math.floor(Math.random() * alpha.length)];
                       var g = alpha[Math.floor(Math.random() * alpha.length)];
                      }
					 
				 var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' '+ f + ' ' + g;						 
							
                //   	alert(code);			
				//	 document.getElementById("mainCaptcha").style.fontFamily ="italic";		
                     document.getElementById("mainCaptcha").value = code
					 //fontType[Math.floor(Math.random() * fontType.length)];
					// document.logInForm.capchaCode.value = code;				
                  }

     
                  function removeSpaces(string){
                    return string.split(' ').join('');
                  }
	
	
	
    function goPassword(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.logInForm.userID.value=="" ) {
				alert('User ID Required');
				document.getElementById("userID").focus();
			}
			else {
				document.getElementById("password").focus();
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
	 
	/*window.addEvent('domready', function() {
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
   	}*/
	
	
	function doSignIn(f) {
		if(document.logInForm.userID.value=="" ) {
			alert('User ID Required');
			document.getElementById("userID").focus();
		}
		else if(document.logInForm.password.value=="" ) {
			alert('PIN Code Required');
			document.getElementById("password").focus();
		}
		else{
			  var string1 = removeSpaces(document.getElementById('mainCaptcha').value);
              var string2 = removeSpaces(document.getElementById('txtInput').value);
			
                      if (string1 == string2){
                     f.action="/paymentmanagement/logInSubmit.do";
					 f.submit();
                      }
                      else{        
                alert("You Typed Wrong....");
				Captcha();	
				document.logInForm.capchaText.value="";
            }	
		}
	}
	function doSignUp(f) {
		f.action="/paymentmanagement/newUserRegistration.do";
		f.submit();
	}
	function doMyNewPINForget(f) {
		f.action="/paymentmanagement/myNewPINForget.do";
		f.submit();
	}
	function doHelp(f) {
		f.action="/paymentmanagement/help.do";
		f.submit();
	}
	function doAboutUs(f) {
		f.action="/paymentmanagement/aboutUs.do";
		f.submit();
	}
	function doBranchInfo(f) {
		f.action="/paymentmanagement/branchInfo.do";
		f.submit();
	}
	function doATM(f) {
		f.action="/paymentmanagement/ATMKioskInformation.do";
		f.submit();
	}
	function doSmsBanking(f) {
		f.action="/paymentmanagement/smsBanking.do";
		f.submit();
	}
	function doTeleBanking(f) {
		f.action="/paymentmanagement/teleBanking.do";
		f.submit();
	}
	function doPrivacyPolicy(f) {
		f.action="/paymentmanagement/privacyPolicy.do";
		f.submit();
	}
	function doTermsConditions(f) {
		f.action="/paymentmanagement/termsConditions.do";
		f.submit();
	}
	function doCopyright(f) {
		f.action="/paymentmanagement/copyright.do";
		f.submit();
	}
	function doContactUs(f) {
		f.action="/paymentmanagement/contactUs.do";
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
			
		
</script>
		
</head>
<body onLoad="setBodyProperty()">
<html:form action="/logIn" style="margin: 0">
  <div id="page-background" style="background-image:url(pages/images/Abstract-Blue-backgrounds-37.jpg)">
<!-- <img src="pages/images/270711-aqua-blue.jpg" width="100%" height="100%" alt="My Bank - Explore More"> -->
 
  <table style="width: 100%; height: 100%; align: center; border: 0" >
  <!--------Header START--------->
    <tr height="10%">
      <td colspan="2" valign="top"><table width="100%" style="width: 100%; border: 0">
          <tr>
            <td colspan="2"><div style="background-image:url(pages/images/index.png); background-repeat:repeat-x;height:100%;width:100%">
                <div style="text-align:left;line-height:20px;padding-top:25px;padding-left:20px"><img src="pages/images/logo.png" alt="Bank Asia Limited"></div>
                <div id="pulse" class="link-b-081" style="text-align:left;line-height:30px;padding-left:30px;font-size:17px;">For A Better Tomorrow</div>
                <div style="text-align:right;margin-top:-80px;padding-right:0px; padding-top:0px"><img src="pages/images/globe.gif" alt="Mybank Explore More" height="95" width="100px"><div style="text-align:right;margin-top:-60px;padding-right:20px; padding-top:0px"><img src="pages/images/My_Bank_Logo.png" alt="Mybank Explore More" height="62" width="73" ></div></div>
              </div></td>
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
    <div>
    <marquee  align="middle" width="62%" behavior="slide" direction="right" height="40px" scrollamount="200" scrolldelay="500" class="usr-3"></marquee>
    
</div></td>
         </tr>
          
          <tr>
            <td><table width="100%" border="0">
             <tr valign="top">
            <td width="24%" style="height: 100%" valign="top" class="lbl-08"><ul id="navigation" style="width:30%">
            <li class="home"><a href="http://www.bankasia-bd.com/" title="Bank Asia Site"></a></li>
            <li class="about"><a href="https://www.google.com/" title="Google Search"></a></li>
            <li class="search"><a href="https://www.facebook.com/" title="Facebook"></a></li>
            <li class="rssfeed"><a href="https://twitter.com/" title="Twitter "></a></li>
            <li class="podcasts"><a  href="#" title="FeedBack"  onClick="doContactUs(logInForm)" ></a></li>        
        </ul>

                  </td>
                  
                  <!------------LOGIN BOX------------>
                  <td width="55%"  >
                   <!-- <table width="66%" height="80%" style="background-image:url(pages/images/LoginBackground.jpeg); background-repeat:no-repeat;border:1px solid #66CCFF" align="center" >
-->
                    <table width="66%" height="100%" style="background-image:url(pages/images/loginBg.jpg); background-size:100%; border:1px; solid #66CCFF" align="center"  >
                      <tr>
  <!--                    <td colspan="3" valign="top" bgcolor="#FFFFFF" align="left"><img src="pages/images/My_Bank_Logo.png" width="20%" height="90" /> <img src="pages/images/LoginHeader.jpeg" width="70%" height="90" />
</td>-->
                        <td colspan="3" valign="top"><img src="pages/images/the-word-loginIllustration.jpg" width="100%" height="60" /> <!--<div style="text-align:right;margin-top:-80px;padding-right:0px; padding-top:0px"><img src="pages/images/My_Bank_Logo.png" alt="Mybank Explore More" height="90" width="83"></div>-->
</td>
                        </tr>
                      <tr>
                          <td align="center" colspan="3">&nbsp;</td>
                        </tr>
                        <tr>
                          <td colspan="3"><table style="width: 100%; align: center" border="0">
                              <%
				    if(session.getAttribute("oLogInMessageBO")!=null) {
					LogInBO mainBO=(LogInBO)session.getAttribute("oLogInMessageBO");
					ArrayList list=mainBO.getList();
					if(list==null)list=new ArrayList();
					for(int i=0;i<list.size();i++) {
					LogInBO bo=(LogInBO)list.get(i);
					String sErrorMessage = bo.getErrorMessage();
					%>
                              <tr>
                                <td class="msg-10" align="left" valign="top" colspan="3">&nbsp;<%=sErrorMessage%></td>
                            </tr>
                              <%         
					}
		      		}
				    %>
                            </table></td>
                        </tr>
                        
                       
                              <tr>
                          <td width="30%" class="lbl-n" align="right" ><img src="pages/images/user.png" width="34" height="20" style="vertical-align:middle"/>User ID</td>
                           <td width="2%" >&nbsp;</td>
                          <td width="68%" id="inter"  onKeyPress="goPassword(event);"><html:text property="userID" value="era@mybank.com" tabindex="1" style="txt-n; width:210px" 
                          onkeyup="javascript:this.value=this.value.toLowerCase();" styleClass="keyboardInput"/>
                          </td>
                        </tr>
                        <tr>
                          
                          <td width="100%" colspan="3" class="lbl-n" align="center">(e.g. username@domainname)</td>
                        </tr>
                        <tr>
                          <td width="30%" class="lbl-n" align="right"><img src="pages/images/key.png" width="34" height="20" style="vertical-align:middle"/>Password</td>
                           <td width="2%" >&nbsp;</td>
                          <td width="68%" onKeyPress="goSignIn(event,logInForm);"><html:password property="password" value="A2@a" tabindex="2" style="txt-n; width:210px" styleClass="keyboardInput"/></td>
                        </tr>
                            <tr>
                          <td colspan="3" class="lbl-n" align="center">(Password is case-sensitive)</td>
                        </tr>
                        <tr>
                        <tr>
                          <td width="30%" class="lbl-n" align="right">Capcha</td>
                           <td width="2%" >&nbsp;</td>
                          <td width="68%"><html:text property="capchaCode" tabindex="2" styleId="mainCaptcha" style="width:210px;height:40px;text-align:center;color:#009;background-color:#999;font-size:25px;font-family:Colonna MT" readonly="true"/></td>
                        </tr>
                    
                     <tr>
                          <td width="30%" class="lbl-n" align="right"></td>
                           <td width="2%" >&nbsp;</td>
                          <td width="68%" onKeyPress="goSignIn(event, logInForm);"><html:text property="capchaText" styleId="txtInput" tabindex="2" style="txt-n; width:210px"/></td>
                        </tr>
                       <tr>
                          <td colspan="3" valign="top" class="lbl-cn">&nbsp;</td>
                        </tr>
                          
                        
                        <tr>
                          <td height="40%" colspan="3"><p class="p-container" align="center"><a href="#" onClick="doSignIn(logInForm)" ><img src="pages/images/loginBDBL.png" height="35px" width="158px" /></a></p></td>
                        </tr>
                        <tr>
                          <td align="center" colspan="3" >&nbsp;</td>
                        </tr>
                        
                      </table>
                  </td>
                 <td width="21%" class="news-demo"> 
                 
                 <!--<div class="css-slideshow"> 
  <figure> 
    <img src="images/AboutUs.png" width="100" height="100" /> 
     <figcaption><strong>CSS3:</strong> CSS3 delivers a...</figcaption> 
  </figure> 
  <figure> 
    <img src="images/home.png" width="100" height="100" /> 
    <figcaption><strong>Semantics:</strong> Giving meaning to...</figcaption> 
  </figure> 
  ...more figures... 
</div>-->
      
			</td> 
                </tr>
              </table></td>
          </tr>
          
        </table></td>
    </tr>
      <!--------BODY END--------->
      <!--------FOOTER START--------->
  <tr  align="center" width="100%" height="15%" >
  <td  colspan="3"><table width="100%" align="center" class="dock" id="dock2" ><tr><td class="dock-container2">
  <a class="dock-item2" href="#" onClick="doAboutUs(logInForm)"><span> <bean:message key="label.aboutUs"/></span><img src="images/AboutUs.png" alt="AboutUs" /></a> 
  <a class="dock-item2" href="#" onClick="doBranchInfo(logInForm)"><span><bean:message key="label.about10"/></span><img src="images/home.png" alt="home" /></a> 
  <a class="dock-item2" href="#" onClick="doATM(logInForm)"><span> <bean:message key="label.eBanking"/></span><img src="images/atms.png" alt="atm" /></a> 
  <a class="dock-item2" href="#" onClick="doSmsBanking(logInForm)"><span> <bean:message key="label.smsBanking"/></span><img src="images/smsNew.png" alt="sms" /></a> 
  <a class="dock-item2" href="#" onClick="doTeleBanking(logInForm)"><span> <bean:message key="label.teleBanking"/></span><img src="images/TeleBanking.png" alt="TeleBanking" /></a> 
  <a class="dock-item2" href="#" onClick="doPrivacyPolicy(logInForm)"><span> <bean:message key="label.privacyPolicy"/></span><img src="images/PrivacyPolicy.png" alt="PrivacyPolicy" /></a> 
  <a class="dock-item2" href="#" onClick="doTermsConditions(logInForm)"><span><bean:message key="label.termsConditions"/></span><img src="images/termscondition.png" alt="termscondition" /></a>
  <a class="dock-item2" href="#" onClick="doContactUs(logInForm)"><span><bean:message key="label.contactus"/></span><img src="images/Contact.png" alt="Contact" /></a>
 
        </td></tr>     </table> </td>
    </tr>
    <tr height="5%">
      <td valign="bottom"><table width="100%"   class="footer-menu" style="width: 100%; border: 0;" >
                <tr style="background-image:url(/pages/images/index.png);background-repeat:repeat-x;height:100%;width:100%;">
                  <td width="6%">&nbsp;</td>
                  <td width="84%" align="center" valign="middle" class="link-b-081"><!--<bean:message key="label.contactus1"/>
                    <a href="#" onClick="doContactUs(logInForm)"><font class="link-b-082">
                    <bean:message key="label.contactus"/>
                    </font></a>-->
                   
                    <a href="#" onClick="doCopyright(logInForm)"><font class="fot-01">
                    <bean:message key="label.copyright"/>
                    </font></a></td>
                  <td width="10%"  align="center" ><img src="pages/images/verisign.png" height="41" width="136" align="right"></td>
                </tr>
            </table></td>
    </tr>
          <!--------FOOTER END--------->

  </table>
  </div>
</html:form>
<!----------For Slider-------------->

  <!--   -----------Dock Menu JS File-----------------   -->
<script type="text/javascript" src="pages/js/jquery.js"></script>
<script type="text/javascript" src="pages/js/interface.js"></script>
</body>
</html>

