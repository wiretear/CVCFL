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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import javax.servlet.ServletOutputStream;
import com.ibank.dbconnection.application.DBCP;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

public class ReportManager_New extends HttpServlet {
	DBCP oPool = DBCP.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public void printReport(HttpServletRequest request,HttpServletResponse response,HashMap hParameters,String sFileName) {
		Connection oConn = null;
        try {
			oConn = oPool.getConnection();
			ServletContext sServletContext = request.getSession().getServletContext();
			String sReportPath = sServletContext.getRealPath(sFileName);
			JasperReport jReport = (JasperReport) JRLoader.loadObject(sReportPath);
			JasperPrint print = JasperFillManager.fillReport(jReport, hParameters, oConn);
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewReport(HttpServletRequest request, HttpServletResponse response, HashMap hParameters, String sFileName) {
        Connection oConn = null;
		try {
			oConn = oPool.getConnection();
			ServletContext sServletContext = request.getSession().getServletContext();
			String sReportPath = sServletContext.getRealPath(sFileName);
//          File reportFile = new File(sReportPath);
//			JasperReport jReport = (JasperReport) JRLoader.loadObject(reportFile);
			JasperReport jReport = (JasperReport) JRLoader.loadObject(sReportPath);
			JasperPrint jPrint = JasperFillManager.fillReport(jReport, hParameters, oConn);
			request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jPrint);
			RequestDispatcher oRequestDispatcher = request.getRequestDispatcher("servlets/pdf");
			oRequestDispatcher.forward(request, response);
		} catch (Exception e) {
            e.printStackTrace();
		// Added by Zohir on 03-June-2013
        } finally {
            if(oConn != null) {
                oPool.releaseConnection(oConn);
            }
        }
	}

    public void viewReport1(HttpServletRequest request, HttpServletResponse response, String sFileName, Connection connection, HashMap hParameters) {
		try {
			ServletContext sServletContext = request.getSession().getServletContext();
			String sReportPath = sServletContext.getRealPath(sFileName);
			JasperReport jReport = (JasperReport) JRLoader.loadObject(sReportPath);
			JasperPrint jPrint = JasperFillManager.fillReport(jReport, hParameters, connection);
			request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jPrint);
			RequestDispatcher oRequestDispatcher = request.getRequestDispatcher("servlets/pdf");
			oRequestDispatcher.forward(request, response);
		} catch (Exception e) {
           e.printStackTrace();
		}
	}

	public void viewReportFormat(HttpServletRequest request, HttpServletResponse response, HashMap hParameters, String sFileName, String sReportFormat) {
		ServletOutputStream ouputStream = null;
		try {
			Connection oConn = null;
			oConn = oPool.getConnection();
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
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
