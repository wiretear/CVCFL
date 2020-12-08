<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.TimeZone"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.mybank.loginbank.bo.LogInBankBO"%>
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
<bean:message key="label.myBankTitle"/>
<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">
<link rel="stylesheet" href="pages/scroller/scroller.css" type="text/css">
<script language="javascript" type="text/javascript" src="/pages/scroller/tools.js"></script>
<script language="javascript" type="text/javascript" src="/pages/javascript/mybank.js"></script>
<script language="javascript" type="text/javascript" src="/pages/scroller/scroller.js"></script>
<script language="javascript" type="text/javascript">

var progress = new MyBank.SubmitionProgress();
	
//Sample Index Bank
	function setBodyProperty(f) {
  		document.oncontextmenu = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};
		if(document.logInBankForm.userID.value=="" ) {
			document.getElementById("userID").focus();
		}	
		else if(document.logInBankForm.password.value=="" ) {
			document.getElementById("password").focus();		
		}
		
  	}
    function goPassword(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.logInBankForm.userID.value=="" ) {
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
		if(document.logInBankForm.userID.value=="" ) {
			alert('User ID Required');
			document.getElementById("userID").focus();
		}
		else if(document.logInBankForm.password.value=="" ) {
			alert('PIN Code Required');
			document.getElementById("password").focus();
		}
		else{
			f.action="/mybank/logInSubmit.do";
			progress.doSubmit();
			f.submit();
		}
	}
	function doSignUp(f) {
		f.action="/mybank/newUserRegistration.do";
		f.submit();
	}
	function doMyNewPINForget(f) {
		f.action="/mybank/myNewPINForget.do";
		f.submit();
	}
	function doHelp(f) {
		f.action="/mybank/help.do";
		f.submit();
	}
	function doAboutUs(f) {
		f.action="/mybank/aboutUs.do";
		f.submit();
	}
	function doBranchInfo(f) {
		f.action="/mybank/branchInfo.do";
		f.submit();
	}
	function doATM(f) {
		f.action="/mybank/ATMKioskInformation.do";
		f.submit();
	}
	function doSmsBanking(f) {
		f.action="/mybank/smsBanking.do";
		f.submit();
	}
	function doTeleBanking(f) {
		f.action="/mybank/teleBanking.do";
		f.submit();
	}
	function doPrivacyPolicy(f) {
		f.action="/mybank/privacyPolicy.do";
		f.submit();
	}
	function doTermsConditions(f) {
		f.action="/mybank/termsConditions.do";
		f.submit();
	}
	function doCopyright(f) {
		f.action="/mybank/copyright.do";
		f.submit();
	}
	function doContactUs(f) {
		f.action="/mybank/contactUs.do";
		f.submit();
	}

	
</script>
</head>
<body onLoad="setBodyProperty()">
<html:form action="/logIn" style="margin: 0">
  <div id="page-background">
    <bean:message key="label.backGroundImage"/>
  </div>
  <table style="width: 100%; height: 100%; align: center; border: 0">
    <tr>
      <td colspan="2" valign="top"><table style="width: 100%; border: 0">
          <tr valign="top">
            <td><a href="#" onClick="doCopyright(logInBankForm)">
              <bean:message key="label.myBankLogoImage"/>
              </a> </td>
            <td align="right"><a href="#">
              <bean:message key="label.bankLogoImage"/>
              </a></td>
          </tr>
          <tr>
            <td width="50%" align="left" valign="top" class="lbl-cn"><bean:message key="label.logInUpdate"/></td>
            <td align="right" class="lbl-cn"><%= 
  new SimpleDateFormat("EEEE MMMM dd, hh:mm:ss a yyyy").format(new Date( new Date().getTime() - TimeZone.getDefault().getRawOffset() - TimeZone.getDefault().getDSTSavings()))
%></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td valign="top"><table style="width: 100%; border: 0">
          <tr>
            <td colspan="2" align="right" valign="top" class="lbl-bl-12"><bean:message key="label.signIn"/></td>
            <td width="64%">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
            <td width="64%" rowspan="3" valign="middle"><table style="width: 100%; align: center" border="0">
                <%
				    if(session.getAttribute("oLogInMessageBO")!=null) {
					LogInBankBO mainBO=(LogInBankBO)session.getAttribute("oLogInMessageBO");
					ArrayList list=mainBO.getList();
					if(list==null)list=new ArrayList();
					for(int i=0;i<list.size();i++) {
					LogInBankBO bo=(LogInBankBO)list.get(i);
					String sErrorMessage = bo.getErrorMessage();
					%>
                <tr>
                  <td class="msg-10"><%=sErrorMessage%></td>
                </tr>
                <%         
					}
		      		}
				    %>
              </table></td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="3"><table width="100%" border="0">
                <tr valign="top">
                  <td width="25%" valign="top"><table width="100%" border="0">
                      <tr>

                      </tr>
                      <tr>
 
                        <td width="40%">&nbsp;</td>
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
                        
                      </tr>
                      <tr>

                      </tr>
                    </table></td>
                  <td width="35%"><table width="100%" border="0">
                      <tr>
                        <td width="28%" class="lbl-n"><bean:message key="label.idLogin"/></td>
                        <td colspan="2" onKeyPress="goPassword(event);"><html:text property="userID" tabindex="1" style="txt-n; width:210px" onkeyup="javascript:this.value=this.value.toLowerCase();"/>
                        </td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td colspan="2" valign="top" class="lbl-cn"><bean:message key="label.indexgelp2"/></td>
                      </tr>
                      <tr>
                        <td class="lbl-n"><bean:message key="label.password"/></td>
                        <td colspan="2" onKeyPress="goSignIn(event,logInBankForm);"><html:password property="password" tabindex="2" style="txt-n; width:210px"/></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td colspan="2" valign="top" class="lbl-cn"><bean:message key="label.caseSensitive"/></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td colspan="2" valign="top" class="lbl-cn">Test</td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td width="15%">&nbsp;</td>
                        <td><a href="#" tabindex="3" onClick="doSignIn(logInBankForm)"
onmouseover="roll('SignIn', '/mybank/pages/images/logindown.gif')"
onmouseout="roll('SignIn', '/mybank/pages/images/loginup.gif')"> <img src="/mybank/pages/images/loginup.gif" name="SignIn" alt="Sign In" border="0"/></a></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>

                      </tr>
                    </table></td>
                  <td width="40%"><table style="width: 100%; align: center" border="0">
                      <tr>
                        <td class="lbl-n"><div id="qscroller2" class="qscroller"></div>
                          <div class="hide">
                            <div class="qslide2" align="left"> <img src="/pages/images/Islamic Banking.jpg" style="width: 100; height: 165" alt="Islamic Banking">
                              <h1> <a href="#" onClick="doAboutUs(logInBankForm)"><font class="link-b-08">Islamic 
                                Banking</font></a> </h1>
                              Bank Asia has introduced Islamic banking to meet the 
                              growing needs of the customers for Shariah-based banking. 
                              It offers a wide range of financial services that 
                              covers the entire spectrum of banking operations. 
                              Islamic Banking Client can also access Net Banking through this site. </div>
                            <div class="qslide2" align="left"> <img src="/pages/images/SME Banking.jpg" alt="SME Banking">
                              <h1> <a href="#" onClick="doAboutUs(logInBankForm)"><font class="link-b-08">SME 
                                Banking</font></a> </h1>
                              From the perspective 
                              of SME banking, there is no doubt that the world is getting flatter 
                              every day. Bank Asia launched SME banking to deliver the highest 
                              standard of service to its clientele.</div>
                            <div class="qslide2" align="left"> <img src="/pages/images/Corporate Banking.jpg" alt="Corporate Banking">
                              <h1> <a href="#" onClick="doAboutUs(logInBankForm)"><font class="link-b-08">Corporate 
                                Banking</font></a> </h1>
                              Bangladesh economy 
                              is changing every day. Changes are more and more visible and sometimes 
                              it seems they are happening overnight. The economical challenges 
                              now a days require flexibility and adjustment capacity. To face 
                              the challenges and business opportunities Bank Asia delivers cash 
                              Management and Lending Solutions match for specific business and 
                              requests.</div>
                            <div class="qslide2" align="left"> <img src="/pages/images/Master Card.jpg" style="width: 100; height: 165" alt="Master and VISA card">
                              <h1> <a href="#" onClick="doAboutUs(logInBankForm)"><font class="link-b-08">Master 
                                and VISA card</font></a> </h1>
                              Credit Cards facilitate your lifestyle in all facets 
                              like shopping, dining, accommodation, traveling, hospitalization, 
                              purchasing furniture &amp; equipment for a interest 
                              free time, gift cards and so on that would be determined 
                              in needs of time. </div>
                            <div class="qslide2" align="left"> <img src="/pages/images/Mobile Banking.jpg" style="width: 100; height: 165" alt="Mobile Banking">
                              <h1> <a href="#" onClick="doSmsBanking(logInBankForm)"><font class="link-b-08">Mobile 
                                Banking</font></a> </h1>
                              <strong>Short Message Service</strong> (SMS) is a 
                              communications protocol allowing the interchange of 
                              short text messages between mobile telephone devices. 
                              SMS text messaging is the most widely used data application. 
                              Now Bank Asia offering <strong>6969</strong> service 
                              for any local mobile operator (<strong>BA</strong> prefix 
                              required). </div>
                            <div class="qslide2" align="left"> <img src="/pages/images/Telephone Banking.jpg" style="width: 100; height: 165" alt="Telephone Banking">
                              <h1> <a href="#" onClick="doTeleBanking(logInBankForm)"><font class="link-b-08">Telephone 
                                Banking</font></a> </h1>
                              Banking by telephone remains a popular trend and is 
                              a major consideration for banks wishing to position 
                              themselves as leaders in their market space. Telephone 
                              Banking offers technology that is flexible, dependable 
                              and cost effective. Dial <strong>(880)-2-8852506</strong> phone number 
                              for phone banking. </div>
                            <div class="qslide2" align="left"> <img src="/pages/images/Lockers.jpg" style="width: 100; height: 165" alt="Lockers">
                              <h1> <a href="#" onClick="doAboutUs(logInBankForm)"><font class="link-b-08">Lockers</font></a> </h1>
                              Bank Asia brings to you a very user-friendly locker 
                              service that is a fine blend of security and confidentiality. 
                              Also have a well appointed rest room where you can 
                              beautify yourself or you may also take your own time 
                              to go through your valuable documents. </div>
                          </div></td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td valign="bottom"><table style="width: 100%; border: 0">
          <tr>
            <td width="10%" rowspan="2" valign="bottom">&nbsp;</td>
            
          </tr>
          <tr>
            <td colspan="4" align="center"><a href="#" onClick="doCopyright(logInBankForm)"><font class="fot-01">
              <bean:message key="label.copyright"/>
              </font></a> </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html>
