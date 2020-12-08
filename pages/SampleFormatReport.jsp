<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.mybank.mybanking.customerprofile.report.bo.KnowYourCustomerStatusReportBO"%>
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
<script language="JavaScript" src="<html:rewrite page='/pages/calender/CalendarPopup.js'/>"></script>
<script language="JavaScript">var cal = new CalendarPopup();</script>
<script language="javascript" type="text/javascript">
	function   setBodyProperty() {
  		document.oncontextmenu = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};
  		document.oncopy = function(){return false};
	}
	function doMainMenu(f){
		f.action="/mybank/cancelKnowYourCustomerStatusReport.do";
		f.submit();
	}
	function doLogOut(f){
		if(confirm("Are you sure? If you confirm press OK button")){
			f.action="/mybank/logOutMyBank.do";
			f.submit();
		}
	}
	function doClear(f){
		f.action="/mybank/knowYourCustomerStatusReport.do";
		f.submit();
	}
	function doMenuList(f){
		f.action=document.KnowYourCustomerStatusReportForm.menu.value;
		f.submit();
	}
	function doExecute(f){
		if(confirm("If you confirm press OK button")){
			f.action="/mybank/ReportKnowYourCustomerStatusReport.do";
			f.submit();
		}
	}
</script>
</head>
<body onLoad="setBodyProperty()">
<html:form action="/knowYourCustomerStatusReport" style="margin: 0">
  <table style="width: 100%; height: 100%; align: center; border: 0">
    <tr>
      <td style="height: 50"><table style="width: 100%; border: 0">
          <tr>
            <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="8%" valign="top"><bean:message key="label.myBankHeaderLogo"/></td>
                  <td width="92%" class="myBankHeader"><table width="100%" border="0" cellspacing="3" cellpadding="0">
                      <tr>
                        <td width="7%" align="left" valign="top" class="usr-6">&nbsp;</td>
                        <td width="70%" align="center" valign="top" class="usr-6"><%out.print(session.getAttribute("GSCompanyName"));%></td>
                        <td width="16%" align="right" valign="top"><font class="usr-5"> Operation ID : </font></td>
                        <td width="7%" align="left" valign="top"><font class="usr-1">
                          <%out.print(session.getAttribute("GSTellerID"));%>
                          </font></td>
                      </tr>
                      <tr>
                        <td align="left" valign="top" class="usr-4">&nbsp;</td>
                        <td align="center" valign="top" class="usr-4"><%out.print(session.getAttribute("GSBranchName"));%></td>
                        <td align="right" valign="top"><font class="usr-5"> Open Date : </font></td>
                        <td align="left" valign="top"><font class="usr-1">
                          <%out.print(session.getAttribute("GSBranchOpenDateDDFormat"));%>
                          </font></td>
                      </tr>
                      <tr>
                        <td align="left" valign="top">&nbsp;</td>
                        <td align="center" valign="top"><font class="usr-6">Customer Profile</font><font class="usr-1">
                          <bean:message key="label.-"/>
                          </font> <font class="usr-4">Customer Information</font></td>
                        <td colspan="2" align="right" valign="top"><a href="#" onClick="doMainMenu(KnowYourCustomerStatusReportForm)"><font class="usr-5">
                          <bean:message key="label.mainMenu"/>
                          </font></a> <font class="usr-1">
                          <bean:message key="label.|"/>
                          </font> <a href="#" onClick="doLogOut(KnowYourCustomerStatusReportForm)"><font class="usr-5">
                          <bean:message key="label.signOut"/>
                          </font></a></td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td colspan="2" valign="top"><a href="#" onClick="doExecute(KnowYourCustomerStatusReportForm)"><font class="btn">Execute</font></a> <font class="usr-1">
              <bean:message key="label.|"/>
              </font> <a href="#" onClick="doClear(KnowYourCustomerStatusReportForm)"><font class="btn">
              <bean:message key="label.clear"/>
              </font></a></td>
          </tr>
          <tr>
            <td colspan="2" valign="top"><table width="100%" border="0">
                <tr>
                  <td width="1%">&nbsp;</td>
                  <td width="98%" rowspan="2"><table style="width: 100%; border: 0" cellpadding="3" cellspacing="1" >
                      <%
					if(session.getAttribute("oKnowYourCustomerStatusReportMessageBO")!=null){
					KnowYourCustomerStatusReportBO mainBO=(KnowYourCustomerStatusReportBO)session.getAttribute("oKnowYourCustomerStatusReportMessageBO");
					ArrayList aList=mainBO.getList();
					if(aList==null)aList=new ArrayList();
					for(int i=0;i<aList.size();i++){
					KnowYourCustomerStatusReportBO bo=(KnowYourCustomerStatusReportBO)aList.get(i);
					String sErrorMessage = bo.getErrorMessage();
				
				  	%>
                      <tr>
                        <td align="center" valign="top" class="msg-00"><%=sErrorMessage%></td>
                      </tr>
                      <%         
					}
						}
				  	%>
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
      <td valign="top"><table style="width: 100%; border: 0">
          <tr>
            <td width="2%">&nbsp;</td>
            <td width="39%">&nbsp;</td>
            <td width="1%">&nbsp;</td>
            <td width="29%">&nbsp;</td>
            <td width="27%">&nbsp;</td>
            <td width="2%">&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Customer From </td>
            <td align="right" class="lbl-n">&nbsp;</td>
            <td><html:text property="customerFrom" styleClass="txt-n" style="width:210px"/></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Customer To</td>
            <td>&nbsp;</td>
            <td><html:text property="customerTo" styleClass="txt-n" style="width:210px"/></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Risk</td>
            <td>&nbsp;</td>
            <td><html:select property="risk"  style="width:210px">
                <html:option value="" styleClass="txt-n">
                  <bean:message key="label.pleaseSelectOne"/>
                </html:option>
                <html:options property="riskList" styleClass="txt-n" labelProperty="riskNameList"/>
              </html:select></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Created By </td>
            <td>&nbsp;</td>
            <td><html:text property="createdBy" styleClass="txt-n" style="width:210px"/></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Created Date From </td>
            <td>&nbsp;</td>
            <td class="lbl-n"><html:text property="createdDateFrom" styleClass="txt-n" style="width:189px"/>
              <a href="#" onClick="cal.select(document.KnowYourCustomerStatusReportForm.createdDateFrom,'anchor1','dd/MM/yyyy'); return false;" NAME="anchor1" ID="anchor1">
              <bean:message key="label.calenderImage"/>
              </a></td>
            <td class="lbl-n">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Created Date To </td>
            <td>&nbsp;</td>
            <td class="lbl-n"><html:text property="createdDateTo" styleClass="txt-n" style="width:189px"/>
              <a href="#" onClick="cal.select(document.KnowYourCustomerStatusReportForm.createdDateTo,'anchor2','dd/MM/yyyy'); return false;" NAME="anchor2" ID="anchor2">
              <bean:message key="label.calenderImage"/>
              </a></td>
            <td class="lbl-n">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Approved By </td>
            <td>&nbsp;</td>
            <td><html:text property="approvedBy" styleClass="txt-n" style="width:210px"/></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Approved Date From </td>
            <td>&nbsp;</td>
            <td class="lbl-n"><html:text property="approvedDateFrom" styleClass="txt-n" style="width:189px"/>
              <a href="#" onClick="cal.select(document.KnowYourCustomerStatusReportForm.approvedDateFrom,'anchor3','dd/MM/yyyy'); return false;" NAME="anchor3" ID="anchor3">
              <bean:message key="label.calenderImage"/>
              </a></td>
            <td class="lbl-n">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="right" class="lbl-n">Approved Date To </td>
            <td>&nbsp;</td>
            <td class="lbl-n"><html:text property="approvedDateTo" styleClass="txt-n" style="width:189px"/>
              <a href="#" onClick="cal.select(document.KnowYourCustomerStatusReportForm.approvedDateTo,'anchor4','dd/MM/yyyy'); return false;" NAME="anchor4" ID="anchor4">
              <bean:message key="label.calenderImage"/>
              </a></td>
            <td class="lbl-n">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td valign="bottom"><table style="width: 100%; border: 0">
          <tr>
            <td colspan="2" valign="middle" class="lbl-cb" align="center"><bean:message key="label.copyright"/></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html>

