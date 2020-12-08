<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jstl-core.tld" prefix="c"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.TimeZone"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibanking.login.bo.IbankingLogInBO"%>
<%String sHeaderName = (String) session.getAttribute("GSCompanyName");
  String sCompanyLogo = (String) session.getAttribute("GSCompanyCode");%>
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
<bean:message key="label.iBankingTitle"/>
<meta charset='utf-8'>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="HandheldFriendly" content="true">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="stylesheet" href="pages/stylesheet/keyboard.css" type="text/css">
<script language="javascript" type="text/javascript" src="/pages/javascript/keyboard.js"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-ui.js'/>"></script>

      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<!-------------Slide Out Menu JS File------------------->

<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.9.1.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jsbootstrap.js'/>"></script>
<link rel="stylesheet" href="pages/stylesheet/bootstrap.css" type="text/css">
<script type="text/javascript" src="js/respond.min.js"></script>
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
<style>
.style-4 {
	padding: 10px;
	border: none !important;
	border-bottom: solid 2px #4EA6EA !important;
	transition: border 0.3s !important;
}
.style-4 :focus, .style-4 .focus {
	border-bottom: solid 2px #4EA6EA;
}
</style>
<style>
 .close,h3 {
	background-color: #003366;
	color:white !important;
	text-align: center;
	font-size: 30px;
	height:33px;
    border-radius: 20px;
	vertical-align: top;
}

.modal-header{
	background-color: #003366;
	color:white !important;
	text-align: center;
	font-size: 30px;
	height:33px;  
	vertical-align: top;
}

.modal-footer {
	background-color: #003366;
}

.rcorners2 {
    border-radius: 20px;
    border: 2px solid #73AD21;
    padding: 5px; 
	
	
	#input_container {
	
    position:relative;

}

#input_img {
    position:!important;
    bottom:2px;
    left:5px;
    width:24px;
    height:24px;
}


</style>
</head>
<body onLoad="setBodyProperty()">
<html:form action="/ibankingLogIn" style="margin: 0">
  <div id="page-background"> <img src="pages/images/img-body-new.jpg" width="100%" height="99%" alt="Bank Asia Limited"> </div>
  <!--------Header START--------->
  
  <div style="height:100%; width:100%; margin-left:auto;margin-right:auto">
    <div style="width:100%;height:15%">
      <div >
        <div style="text-align:left;line-height:20px;padding-top:25px;padding-left:20px"><img src="pages/images/logo.png" alt="Bank Asia Limited"></div>
        <div style="text-align:right;margin-top:-50px;padding-right:20px"><img src="pages/images/My_Bank_Logo.png" alt="Mybank Explore More" height="90px" width="100px"></div>
      </div>
    </div>
    
    
<div style="height:50%; width:40%;padding-right:20px;float:right; vertical-align:top">
   <div  id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-header" style="padding:35px 50px;background-image:url(pages/images/images.jpg)">         
         <span></span>
        </div>
      
        <div class="modal-body" style="padding:20px 50px; background-color:#FFF">
          <form role="form">
            <div class="form-group" id="input_container">
             <img src="pages/images/user.png" style="height:23px" id="input_img" />  
             <label for="usrname"><span class="glyphicon"></span> Username</label>                        
              <input type="text"  class="form-control" id="usrname" placeholder="Enter email" styleClass="keyboardInput"  required>       
            </div>
            
             <div class="form-group" id="input_container">
            <img src="pages/images/pass-2.png" style="height:23px" id="input_img" />
              <label for="psw"><span class="glyphicon"></span> Password</label>
              <input type="password" class="form-control" id="psw" placeholder="Enter password" styleClass="keyboardInput"  required> 
              </div>            
              
                <div class="form-group">
                  <table class="form-area"  border="0">
                    <tr>
                      <td width="5%" rowspan="2" align="center" valign="middle" class="capt" id="1" ><strong>
                        <%out.print(session.getAttribute("GSDigit1"));%>
                        </strong></td>
                      <td width="5%" rowspan="2" align="center" valign="top" class="capt" id="2"><strong>
                        <%out.print(session.getAttribute("GSDigit2"));%>
                        </strong></td>
                      <td width="5%" rowspan="2" align="center" valign="middle" class="capt" id="3"><strong>
                        <%out.print(session.getAttribute("GSDigit3"));%>
                        </strong></td>
                      <td width="5%" rowspan="2" align="center" valign="bottom" class="capt" id="4"><strong>
                        <%out.print(session.getAttribute("GSDigit4"));%>
                        </strong></td>
                      <td width="1%">&nbsp;</td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                </div>
                <div class="form-group col-md-3 form-label">
                  <label for="captcha">Captcha</label>
                </div>
                <div class="form-group col-md-9">
                  <input type="text" class="form-control" placeholder="Enter Captcha Code" required>
                </div>
                <div cla<div class="checkbox">
              <label><input type="checkbox" value="" checked>Remember me</label>
            </div>
              <button type="submit" class="btn btn-success btn-block"  onclick="doSubmit()"> <span class="glyphicon"></span> <img src="pages/images/pass-3.png" id="input_img" /> Login</button>
          </form>
        </div>
                    <div class="modal-footer">
              <button type="submit" class="btn btn-danger btn-default pull-left btn-size" data-dismiss="modal"><span class="glyphicon"></span><img src="pages/images/clear-1.png" id="input_img" /> Clear</button>
              <p style="color:#FFF">Not a member? <a href="#">Sign Up</a></p>
              <p style="color:#FFF"; onMouseOver="">Forgot <a href="#">Password?</a></p>
            </div>

      </div>
      
 
  </div> 
    </div>
    <div style="float:left;width:60%;height:45%">
      <div style="margin-left:280px; margin-top:10px;color:#FFF"> 
	  <%= new SimpleDateFormat("EEEE MMMM dd, yyyy").format(new Date( new Date().getTime() - TimeZone.getDefault().getRawOffset() - TimeZone.getDefault().getDSTSavings()))%> </br>		
        <span style="font-size:22px;color:#FFF">Welcome To Bank Asia </span></br>
        <span style="font-size:32px;color:#0CF">Internet Banking</span></br>
        <span style="margin-left:30%"><img src="pages/images/9.png" style="width:60px;height:20px" ></span> </div>
      <div class="rcorners2" style="float:left; margin-left:30px;margin-top:9%;height:100%;width:40%;background-color:#FFF;border:2px">
      <span><img src="pages/images/1.png" style="height:70px;margin-left:10px;margin-top:10px"> </span></br>
     
        <h3>New Features</h3>
      <hr/>
        <p>=> Use virtual keyboard for secure login.</p>
        <p>=> Ensure the address bar URL with https.</p>
        <p>=> Change your password periodically.</p>
        <p>=> For any query please contact directly to our 24/7 call centre.</p>
        <p>=> 16205, +8801713047887, +8801713388648</p>
        <p>=> contact.center@bankasia-bd.com</p>
      </div>
      <div class="rcorners2" style="float:left; margin-left:30px;margin-top:9%;height:100%;width:40%;background-color:#FFF;border:2px">
      
        <span><img src="pages/images/2.png" style="height:70px;margin-left:10px;margin-top:10px"> </span></br>
        <div>
          <h3>Security Guidelines</h3>
         <hr/>
        <p>=> Talktime recharge facilities available</p>
        <p>=> You will receive an OTP(One time password) for fund transfer for current session once</p>
        <p>=>Check your Mobile message or e-mail(Inbox/Spam) to get current session OTP(one time password)</p>
        </div>
      </div>
    </div>
 
  <table>
    <tr>
      <td valign="bottom"><table  class="footer-menu" style="width: 100%; border: 0;" >
          <tr style="background-image:url(/pages/images/index.png);background-repeat:repeat-x;height:100%;width:100%;">
            <td width="6%">&nbsp;</td>
            <td width="84%" align="center" valign="middle" class="link-b-081"><a href="#" onClick="doCopyright(ibankingLogInForm)"><font class="fot-01">
              <bean:message key="label.copyright"/>
              </font></a></td>
            <td width="10%"  align="center" ><img src="pages/images/eraLogo.bmp" height="41" width="136" align="right"></td>
          </tr>
        </table></td>
    </tr>
  </table>
 </div>
</html:form>
</body>
</html>
