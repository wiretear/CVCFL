<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.TimeZone"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibanking.login.bo.IbankingLogInBO"%>
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
<bean:message key="label.iBankingTitle"/>

<meta charset='utf-8'>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="HandheldFriendly" content="true">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">

<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.9.1.js'/>"></script>  
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jsbootstrap.js'/>"></script>
<script type="text/javascript" src="js/respond.min.js"></script>
<link rel="stylesheet" href="pages/stylesheet/bootstrap.css" type="text/css">

<!--   -----------Slide Out Menu JS File-----------------   -->
  <style>
  
  .modal-header, h3, .close {
      background-color: #003366;
     color:white !important;
      text-align: center;	
      font-size: 30px;
	  height:15px;
	  vertical-align: top;
  }
  .modal-footer {
      background-color: #003366;
	 
  }
  </style>

<style>
.capt {
	color:#ffffcc;
	font-size : 11pt;
	font-family: Verdana, Tahoma, Arial, Helvetica, Sans-serif, sans-serif;
}
</style>



<script language="javascript" type="text/javascript">

var CompanyName;
	function setBodyProperty(f) {
  		//document.oncontextmenu = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};	
		document.onselectstart = function(){return false};
  		document.oncopy = function(){return false};
		document.onBeforePrint = function(){return false};
		document.onAfterPrint= function(){return false};	



  	}
	
	
	/*
  function removeSpaces(String){
		  
		  return String.split(,).join('');
	     }
	*/
	
   $(document).ready(function(){

        $("#myModal").modal('show');
		
		
    });


   $(document).ready(function () {
        $('#body').click(function () {
            $('#myModal').modal({
                backdrop: 'static',
                keyboard: false,
            })
        });
 
    });
	
    function goPassword(e) {
   		if (window.event) { 
			e = window.event; 
		}
        if (e.keyCode == 13 ){
			if(document.ibankingLogInForm.userID.value=="" ) {
				alert('User ID Required..');
				document.ibankingLogInForm.userID.focus();
			}
			else {
				document.ibankingLogInForm.password.focus();
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
		if(document.ibankingLogInForm.userID.value=="" ) {
			alert('User ID Required..');
			document.ibankingLogInForm.userID.focus();
		}
		else if(document.ibankingLogInForm.password.value=="" ) {
			alert('Password Required..');
			document.ibankingLogInForm.password.focus();
		}
		else{
			 f.action="/ibanking/logInSubmit.do";
			 f.submit();
		}		
	}
	
	function doSubmit(){
	
		var f = document.ibankingLogInForm;
		 f.action="/ibanking/logInSubmit.do";
		 f.submit();
		}
	
	function doSignUp(f) {
		f.action="/ibanking/newUserRegistration.do";
		f.submit();
	}

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

<style>
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
<body onLoad="setBodyProperty()" id="body">
<html:form action="/ibankingLogIn" style="margin: 0" >
<%
response.setHeader("Cache-Control","no-cache,no-store, must-revalidate"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

%>

  <table style="width: 100%; height: 100%; align: center; border: 0" >
  <!--------Header START--------->
    <tr height="10%">

      <td colspan="2" valign="top"><table  style="width: 100%;height:100%; border: 0">
          <tr>
            <td colspan="2"><div style="background-image:url(pages/images/index.png); background-repeat:repeat-x;height:100px;width:100%">
                <div style="text-align:left;line-height:20px;padding-top:25px;padding-left:20px"><img src="pages/images/logo.png" alt="Bank Asia Limited" width="18%">
                <span style="padding-left:60px;font-size:3.5em;font-family:Monotype Corsiva;color:#DECBFC;text-align:center"><%out.print(sHeaderName);%></span></div>
                <div  class="link-b-081" style="text-align:left;line-height:30px;padding-left:30px;font-size:1.7em;">For A Better Tomorrow</div>
                <div style="text-align:right;margin-top:-85px;padding-right:10px;vertical-align:middle;"><img src="ComName()" id="image" alt="Bank Asia Limited" height="80" width="73" ></div>
              </div></td>
          </tr>
          <tr>
            
            <td align="right" class="link-b-081" style="color:#000" colspan="2"><%=new SimpleDateFormat("EEEE MMMM dd, hh:mm:ss a yyyy").format(new Date( new Date().getTime() - TimeZone.getDefault().getRawOffset() - TimeZone.getDefault().getDSTSavings()))%></td>
          </tr>
      </table></td>
    </tr>
  <!-----------HEADER END--------->
  <!--------BODY START--------->

    <tr  height="75%" style="width:100%;padding-right:6px;padding-left:0px" >
    
      <td valign="top"><table style="width: 100%; border: 0">
          <tr>
            <td colspan="3" height="50" valign="top" >

    <div>
    <marquee  align="middle" width="62%" behavior="slide" direction="right" height="40px" scrollamount="200" scrolldelay="500" class="usr-3"></marquee>
    
</div></td>
         </tr>
          
          <tr>
            <td><table width="100%" border="0">
             <tr valign="top">
            <td width="24%" style="height: 100%" valign="top" class="lbl-08">

                  </td>
                  
                  <!------------LOGIN BOX------------>
                  <td width="55%"  >
                   <!-- <table width="66%" height="80%" style="background-image:url(pages/images/LoginBackground.jpeg); background-repeat:no-repeat;border:1px solid #66CCFF" align="center" >
-->

<div class="container">

  <div  id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="padding:35px 50px;">         
          <h4><span class="glyphicon" style="font-family:Verdana, Geneva, sans-serif"></span> Bank Asia</h4>
        </div>
        <div class="modal-body" style="padding:40px 50px;">
          <form role="form">
            <div class="form-group" id="input_container">
             <img src="pages/images/user.png" id="input_img" />  
             <label for="usrname"><span class="glyphicon"></span> Username</label>                        
              <input type="text"  class="form-control" id="usrname" placeholder="Enter email"  required>          
         
            
            </div>
            <div class="form-group">
            <img src="pages/images/pass-2.png" id="input_img" />
              <label for="psw"><span class="glyphicon"></span> Password</label>
              <input type="password" class="form-control" id="psw" placeholder="Enter password" required>
            </div>
          
          
         <!--  
  <div class="form-group">
      <label><b>Username</b></label>
      <input type="text" placeholder="Enter Username" class="form-control" id="uname" >

      <label><b>Password</b></label>
      <input type="text" class="form-control" placeholder="Enter Password" id="psw" >

    </div>
         -->
          
          
            <div class="checkbox">
              <label><input type="checkbox" value="" checked>Remember me</label>
            </div>
              <button type="submit" class="btn btn-success btn-block"  onclick="doSubmit()"> <span class="glyphicon"></span> <img src="pages/images/login-2.png" id="input_img" /> Login</button>
          </form>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon"></span><img src="pages/images/clear-1.png" id="input_img" /> Clear</button>
          <p style="color:#FFF">Not a member? <a href="#">Sign Up</a></p>
          <p style="color:#FFF"; onMouseOver="">Forgot <a href="#">Password?</a></p>
        </div>
      </div>
      
    </div>
  </div> 
 
       </div>           </td>
                 <td width="21%" class="news-demo"> 

			</td> 
                </tr>
              </table></td>
          </tr>
          
        </table></td>
    </tr>
      <!--------BODY END--------->
      
    <tr height="5%">
      <td valign="bottom"><table width="100%"   class="footer-menu" style="width: 100%; border: 0;" >
                <tr style="background-image:url(/pages/images/index.png);background-repeat:repeat-x;height:100%;width:100%;">
                  <td width="6%">&nbsp;</td>
                  <td width="84%" align="center" valign="middle" class="link-b-081"><!--<bean:message key="label.contactus1"/>
                    <a href="#" onClick="doContactUs(ibankingLogInForm)"><font class="link-b-082">
                    <bean:message key="label.contactus"/>
                    </font></a>-->                   
                    <a href="#" onClick="doCopyright(ibankingLogInForm)"><font class="fot-01">
                    <bean:message key="label.copyright"/>
                    </font></a></td>
                  <td width="10%"  align="center" ><img src="pages/images/eraLogo.bmp" height="41" width="136" align="right"></td>
                </tr>
            </table></td>
    </tr>
          <!--------FOOTER END--------->

  </table>


</html:form>


</body>
</html>

