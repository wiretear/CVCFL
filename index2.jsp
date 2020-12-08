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
<bean:message key="label.iBankingTitle"/>

  <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Bank Asia - Internet Banking Login</title>
      <!-- CSS -->
      <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:400,700">
      <link rel="stylesheet" href="pages/assets/bootstrap/css/bootstrap.min.css">
      <link rel="stylesheet" href="pages/assets/font-awesome/css/font-awesome.min.css">
      <link rel="stylesheet" href="pages/assets/css/animate.css">
      <link rel="stylesheet" href="pages/style.css">
      <link rel="stylesheet" href="pages/assets/css/media-queries.css">
      <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
      <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
       
      <!-- Favicon and touch icons -->
      <link rel="shortcut icon" href="assets/ico/favicon.png">
      <link rel="apple-touch-icon-precomposed" sizes="144x144" href="pages/assets/ico/apple-touch-icon-144-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="114x114" href="pages/assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="pages/assets/ico/apple-touch-icon-72-precomposed.png">
      <link rel="apple-touch-icon-precomposed" href="pages/assets/ico/apple-touch-icon-57-precomposed.png">


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
	</script>
	</head>
	
    <body>    
         <header>
         <nav class="navbar navbar-fixed-top">
            <div class="container-fluid">
               <div class="navbar-header">
                   
                  <a class="navbar-brand" href="https://mybank.bankasia-bd.com" target="_blank"></a>
               </div>
                <div>
                  <a href="https://mybank.bankasia-bd.com" target="_blank"><img style="float: right;" src="pages/assets/img/mybanklogo.gif" alt="MyBank"></a>
               </div>
            </div>
         </nav>
      </header>
       
      <!-- Top content -->
      <div class="top-content">
         <div class="container-fluid">
            <div class="row">
               <div class="col-md-8">
                  <div class="row">
                     <div class="col-sm-12 welcome-section text wow fadeInLeft">
                       <p> <%= new SimpleDateFormat("EEEE MMMM dd, yyyy").format(new Date( new Date().getTime() - TimeZone.getDefault().getRawOffset() - TimeZone.getDefault().getDSTSavings()))%>	</p>
                          <span style="font-size:22px;color:#FFF">Welcome To Bank Asia </span></br>
       					 <span style="font-size:32px;color:#0CF">Internet Banking</span></br>
        				<span style="margin-left:23%"><img src="pages/images/9.png" style="width:60px;height:20px" ></span>

                     </div>
                  </div>
                  <div class="row">
                      <div class="col-md-12 wow fadeInLeft mobile-app">
                          <h1 style="font-size:25px;">Mobile Apps Available</h1>
                          <a href="https://play.google.com/store/apps/details?id=era.bankasia.bdinternetbanking.apps&hl=en" target="_blank"><img src="pages/assets/img/android.png" alt=""></a>
                          <a href="https://itunes.apple.com/us/app/bank-asia-smart-app/id1156397604?mt=8" target="_blank"><img src="pages/assets/img/apple.png" alt=""></a>
                      </div>
                  </div>
                  <div class="row">
                     <div class="col-sm-4  wow fadeInUp">
                        <div class="howto-bg">
                           <h3>Security Guideline</h3>
                           <p class="fa fa-caret-square-o-right"> Never disclose your password or any security information to anyone.</p>
                           <p class="fa fa-caret-square-o-right"> Don't use the "save password option" on your computer.</p>
                           <p class="fa fa-caret-square-o-right"> Don't use your social media password.</p>
                           <p class="fa fa-caret-square-o-right"> After 5 failed attempt your ID will be locked.</p>
                           <p class="fa fa-caret-square-o-right"> Use virtual keyboard for secure Login.</p>
                           <p class="fa fa-caret-square-o-right"> Ensure the address bar URL with https.</p>
                           <p class="fa fa-caret-square-o-right"> Change your password periodically.</p>
                        </div>
                     </div>
                     <div  class="col-sm-4 wow fadeInDown">
                        <div class="howto-bg">
                           <h3>Need Help</h3>
                           <p class="fa fa-caret-square-o-right"> 24/7 Phone Assistance.</p>
                           <p class="fa fa-caret-square-o-right"> Please call: 16205, +8801713047887, +8801713388648</p>
                           <p class="fa fa-caret-square-o-right"> Email us: contact.center@bankasia-bd.com</p>
                           <p class="fa fa-caret-square-o-right"> URL: bankasia.net</p>
                        </div>
                     </div>
                     <div class="col-sm-4 wow fadeInUp">
                        <div class="howto-bg">
                           <h3>New Features</h3>
                           <p class="fa fa-caret-square-o-right"> Talktime recharge facilities available.</p>
                           <p class="fa fa-caret-square-o-right"> OTP (One Time Password) for Fund transfer for current session once.</p>
                           <p class="fa fa-caret-square-o-right"> Email us: headoffice@ebek-rdcd.gov.bd</p>
                           <p class="fa fa-caret-square-o-right"> Check your Mobile message or e-mail (Inbox/Spam) to get current session OTP (One Time Password).</p>
                        </div>
                     </div>
                  </div>
               </div>
                
               <div class="col-md-4" style="margin-top: 47px;">
                  <div class="c-form-1-box wow fadeInRight">
                     <div class="c-form-1-top">
                        <div class="c-form-1-top-left">
                           <h3>Login</h3>
                           <p>Internet Banking Credential</p>
                        </div>
                        <div class="c-form-1-top-right">
                           <i class="fa fa-sign-in"></i>
                        </div>
                     </div>
                     <div class="c-form-1-bottom">
                        <form role="form" method="post">
                           <div class="form-group">
                              <label class="sr-only" for="c-form-1-name">User ID</label>
                              <input type="text" name="userid" placeholder="User ID..." class="c-form-1-name form-control" id="c-form-1-name" required>
                           </div>
                           <div class="form-group">
                              <label class="sr-only" for="c-form-1-email">Password</label>
                              <input type="text" name="password" placeholder="Password..." class="c-form-1-email form-control" id="c-form-1-email" required>
                           </div>
                           <div class="form-group">
                              <label class="sr-only" for="c-form-1-message">Chaptcha</label>
                              <textarea name="chaptcha" placeholder="Chaptcha..." class="c-form-1-message form-control" id="c-form-1-message"></textarea>
                           </div>
                           <div class="form-group">
                              <label class="sr-only" for="c-form-1-subject">Chaptcha Code</label>
                              <input type="text" name="chaptcha-code" placeholder="Chaptcha..." class="c-form-1-subject form-control" id="c-form-1-subject" required>
                           </div>
                           <button type="submit" class="btn">Login</button>
                        </form>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
      <!-- How it works -->
      <!-- Footer -->
      <footer>
         <div class="container">
            <div class="col-sm-6 footer-menu" style="text-align: left">
               Copyright &copy; 2017. <a href="http://www.erainfotechbd.com" target="_blank">ERA-InfoTech Limited.</a> All Rights Reserved
            </div>
            <div class="col-sm-6 footer-menu">
               <ul>
                  <li><a  href="#top-content">About Us</a></li>
                  <li><a  href="#features">Branches</a></li>
                  <li><a  href="#how-it-works">ATM</a></li>
                  <li><a  href="#pricing">SMS Banking</a></li>
                  <li><a class="launch-modal" href="#" data-modal-id="modal-terms">Terms & Conditions</a></li>
               </ul>
            </div>
         </div>
      </footer>
       
      <!-- MODAL: Terms and Conditions -->
      <div class="modal fade" id="modal-terms" tabindex="-1" role="dialog" aria-labelledby="modal-terms-label" aria-hidden="true">
         <div class="modal-dialog">
            <div class="modal-content">
               <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">
                  <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                  </button>
                  <h2 class="modal-title" id="modal-terms-label">Terms and Conditions</h2>
               </div>
               <div class="modal-body">
                  <p>Please read carefully the terms and conditions for using our product below:</p>
                  <h3>1. Dolor sit amet</h3>
                  <p>
                     Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                     labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.
                  </p>
                  <ul>
                     <li>Easy To Use</li>
                     <li>Awesome Design</li>
                     <li>Cloud Based</li>
                  </ul>
                  <p>
                     Ut wisi enim ad minim veniam, <a href="#">quis nostrud exerci tation</a> ullamcorper suscipit lobortis nisl ut aliquip ex ea
                     commodo consequat nostrud tation.
                  </p>
                  <h3>2. Sed do eiusmod</h3>
                  <p>
                     Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                     labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.
                  </p>
                  <h3>3. Nostrud exerci tation</h3>
                  <p>
                     Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea
                     commodo consequat nostrud tation.
                  </p>
                  <p>
                     Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                     labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.
                  </p>
               </div>
               <div class="modal-footer">
                  <button type="button" class="btn" data-dismiss="modal">I Read it &amp; I Agree</button>
               </div>
            </div>
         </div>
      </div>
      <!-- Javascript -->
      <script src="pages/assets/js/jquery-1.11.1.min.js"></script>
      <script src="pages/assets/bootstrap/js/bootstrap.min.js"></script>
      <script src="pages/assets/js/jquery.backstretch.min.js"></script>
      <script src="pages/assets/js/wow.min.js"></script>
      <script src="pages/assets/js/retina-1.1.0.min.js"></script>
      <script src="pages/assets/js/waypoints.min.js"></script>
      <script src="pages/assets/js/scripts.js"></script>
      <script src="pages/assets/js/placeholder.js"></script>
   </body>
</html>
