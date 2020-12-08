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
		<title>User SignUp</title>
		
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
		<link rel="stylesheet" href="pages/stylesheet/demo.css" type="text/css" media="all">
		<link rel="stylesheet" href="pages/stylesheet/skycolor.css" type="text/css" media="all">
		<!--[if lt IE 9]>
			<link rel="stylesheet" href="css/sky-forms-ie8.css">
		<![endif]-->
		
		<!--[if lt IE 10]>
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
			<script src="js/jquery.placeholder.min.js"></script>
		<![endif]-->		
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
			<script src="js/sky-forms-ie8.js"></script>
		<![endif]-->
        
        <script language="javascript" type="text/javascript">
        
        function doSubmitRegistration(){
        
		var f = document.ibankingUserLogInForm;
		
        f.action="/mybank/cancelPersonalProfile.do";
		f.submit();
        
        }       
      </script>  
        
	</head>
	
	<body class="bg-cyan">
		<div class="body body-s">
		
			<form action="/userDetailsSignUp" class="sky-form">
				<header>Online User Registration</header>
				
				<fieldset>					
					<section>
						<label class="input">
							<i class="icon-append icon-user"></i>
							<input type="text" placeholder="Customer Code" required>
							<b class="tooltip tooltip-bottom-right">Only Numbers</b>
						</label>
					</section>
                    
                    <section>
						<label class="input">
							<i class="icon-append icon-user"></i>
							<input type="text" placeholder="Customer Name" required>
							<b class="tooltip tooltip-bottom-right">Only characters</b>
						</label>
					</section>
					
					<section>
						<label class="input">
							<i class="icon-append icon-envelope-alt"></i>
							<input type="email" placeholder="Email address" size="30" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required>
							<b class="tooltip tooltip-bottom-right">Needed to verify your account</b>
						</label>
					</section>
					
					<section>
						<label class="input">
							<i class="icon-append icon-lock"></i>
							<input type="date" placeholder="Date of Birth" required>
							<b class="tooltip tooltip-bottom-right">Only Date</b>
						</label>
					</section>
					
					<section>
						<label class="input">
							<i class="icon-append icon-lock"></i>
							<input type="text" placeholder="Mobile Number" required>
							<b class="tooltip tooltip-bottom-right">Only numbers</b>
						</label>
					</section>
                    
                    
                    <section>
						<label class="select">
							<select>
								<option value="0">Secret Question</option>
								<option value="1">Male</option>
								<option value="2">Female</option>
								<option value="3">Other</option>
							</select>
							<i></i>
						</label>
					</section>
                    
                    <section>
						<label class="input">
							<i class="icon-append icon-lock"></i>
							<input type="password"  required>
							<b class="tooltip tooltip-bottom-right">Only numbers</b>
						</label>
					</section>
      
		</fieldset>
        
				<footer>
                    <button type="button" class="button">Clear</button>
					<button type="button" class="button" onClick="doSubmitRegistration()">Submit</button>                    
				</footer>
			</form>
			
		</div>
	</body>
</html>