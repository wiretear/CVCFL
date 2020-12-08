/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Report Manager
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.utility;

import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.view.JasperViewer;

import com.ibank.dbconnection.application.DBCPNewConnection;

public class ReportManager extends HttpServlet {
//	DBCP oPool = DBCP.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public void printReport(HttpServletRequest request,HttpServletResponse response,HashMap hParameters,String sFileName) {
		Connection oConn = null;
		try {			
			oConn = DBCPNewConnection.getConnection();
			ServletContext sServletContext = request.getSession().getServletContext();
			String sReportPath = sServletContext.getRealPath(sFileName);
			JasperReport jReport = (JasperReport) JRLoader.loadObject(sReportPath);
			JasperPrint print = JasperFillManager.fillReport(jReport, hParameters, oConn);
			JasperViewer.viewReport(print, true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (oConn  != null) {
				DBCPNewConnection.releaseConnection(oConn);
			}			
		}
	}
	
	public void viewReport(HttpServletRequest request,HttpServletResponse response,Map hParameters,String sFileName) {
		Connection oConn = null;
		try {
	
			oConn = DBCPNewConnection.getConnection();
			ServletContext sServletContext = request.getSession().getServletContext();
			String sReportPath = sServletContext.getRealPath(sFileName);
			JasperReport jReport = (JasperReport) JRLoader.loadObject(sReportPath);
			JasperPrint jPrint = JasperFillManager.fillReport(jReport, hParameters, oConn);
			request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jPrint);
			RequestDispatcher oRequestDispatcher = request.getRequestDispatcher("servlets/pdf");
		  //RequestDispatcher oRequestDispatcher = request.getRequestDispatcher("servlets/xls");	
		  //RequestDispatcher oRequestDispatcher = request.getRequestDispatcher("servlets/rtf");	
			oRequestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (oConn  != null) {
				DBCPNewConnection.releaseConnection(oConn);
			}
			
		}
	}


	public void viewReportFormat(HttpServletRequest request, HttpServletResponse response, HashMap hParameters, String sFileName, String sReportFormat) {
		ServletOutputStream ouputStream = null;
		Connection oConn = null;		
		try {
			
			oConn = DBCPNewConnection.getConnection();
			ServletContext context = request.getSession().getServletContext();
			String reportPath = context.getRealPath(sFileName);
			JasperReport report = (JasperReport) JRLoader.loadObject(reportPath);
			JasperPrint print = JasperFillManager.fillReport(report, hParameters, oConn);
			JRExporter exporter = null;
			if (sReportFormat.equalsIgnoreCase("pdf")) {
				response.setContentType("application/pdf");
				ouputStream = response.getOutputStream();
				exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
			} else if (sReportFormat.equalsIgnoreCase("html")) {
				exporter = new JRHtmlExporter();
				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print);
				response.setContentType("text/html");
				PrintWriter outputWriter = response.getWriter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, outputWriter);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
			} else if (sReportFormat.equalsIgnoreCase("xls")) {
				exporter = new JExcelApiExporter();
				response.setContentType("application/xls");
				ouputStream = response.getOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
			}
			else if (sReportFormat.equalsIgnoreCase("rtf")) {
				exporter = new JRRtfExporter();
				response.setContentType("application/rtf");
				ouputStream = response.getOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
			}
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (oConn  != null) {
				DBCPNewConnection.releaseConnection(oConn);
			}			
		}
	}


	/*protected byte[] exportReportToRtf(JasperPrint jasperPrint) throws JRException{
	   JRRtfExporter exporter = new JRRtfExporter();
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();    
	   exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
	   exporter.exportReport(); 
	   return baos.toByteArray();
	}*/

	public void getDownloadReportPDF(HttpSession session,HttpServletRequest request,HttpServletResponse response,HashMap hParameters,String sFileName ,String sReportName) {
		Connection oConn = null;	
		try {   
		oConn = DBCPNewConnection.getConnection();
	//	Map params = getParameters(session);
		//Date  date  = new Date();
		String reportfileName = "report"+"-"+".pdf";
		//JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/pages/mybanking/foreigntrade/foreignimport/report/TestReport.jasper"));
		//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		//test block
		ServletContext sServletContext = request.getSession().getServletContext();
		String sReportPath = sServletContext.getRealPath(sFileName);
		JasperReport jReport = (JasperReport) JRLoader.loadObject(sReportPath);
		//JasperPrint print = JasperFillManager.fillReport(jReport, hParameters, oConn);

		JasperPrint jasperprint = JasperFillManager.fillReport(jReport, hParameters,oConn);
		JRAbstractExporter exporterPDF = new JRPdfExporter();
		exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
		exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		//response.setHeader("Content-Disposition", "inline;filename="+ reportfileName);
		response.setHeader("Content-disposition","attachment; filename=\"" +sReportName +"\"");
		response.setContentType("application/pdf");
		exporterPDF.exportReport();  
		
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
	}
	
	//JRPdfExporter exporter = new  JRPdfExporter();
	//exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();"); 
	/*public void getPrintReportPDF(HttpSession session,HttpServletRequest request,HttpServletResponse response,HashMap hParameters,String sFileName ) {
		Connection oConn = null;	
		try {   
		oConn = oPool.getConnection();
		String reportfileName = "report"+"-"+".pdf";
	
		ServletContext sServletContext = request.getSession().getServletContext();
		String sReportPath = sServletContext.getRealPath(sFileName);
		JasperReport jReport = (JasperReport) JRLoader.loadObject(sReportPath);
		//JasperPrint print = JasperFillManager.fillReport(jReport, hParameters, oConn);

		JasperPrint jasperprint = JasperFillManager.fillReport(jReport, hParameters,oConn);
		JRPdfExporter exporterPDF = new JRPdfExporter();
		//exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
		//exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporterPDF.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
		//response.setHeader("Content-Disposition", "inline;filename="+ reportfileName);
		//response.setHeader("Content-disposition","attachment; filename=\"" +"TestReport.pdf" +"\"");
		//response.setContentType("application/pdf");
		//exporterPDF.exportReport();  
		} catch(Exception exception) {
		}
		
	}*/
	
	}
