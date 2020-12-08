/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Write Excel
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.utility;

import java.io.File;
import java.util.Locale;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.*;
import jxl.format.UnderlineStyle;
import com.ibank.dbconnection.application.DBCP;
import com.ibank.login.bo.IBankingUserLogInBO;

public class WriteExcelFile {
	private WritableCellFormat sTextArialBold;
	private WritableCellFormat sTextTime;
	private WritableCellFormat sNumberFloat;
	private WritableCellFormat sTextTimeBoldUnderline;
	DBCP oPool = DBCP.getInstance();

	public IBankingUserLogInBO getXLSFile() throws Exception {
		IBankingUserLogInBO oIBankingLogInBO = new IBankingUserLogInBO();
	  	try {
			String filename = "d:/InputNew.xls";
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filename), wbSettings);
			workbook.createSheet("MyBank-Report", 0);
			WritableSheet excelSheet = workbook.getSheet(0);
			createLabel(excelSheet);
			createContent(excelSheet);
			workbook.write();
			workbook.close();
	  	} catch (Exception e) {
			//e.printStackTrace();
	  	}
		return oIBankingLogInBO;
  	}
	private void createLabel(WritableSheet sheet)throws WriteException {
		
		// Create Times font
		WritableFont wTextTime = new WritableFont(WritableFont.TIMES, 10);
		sTextTime = new WritableCellFormat(wTextTime);
		sTextTime.setWrap(true);
	
		// Create Bold & Times font with unterlines
		WritableFont wTextTimeBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,UnderlineStyle.SINGLE);
		sTextTimeBoldUnderline = new WritableCellFormat(wTextTimeBoldUnderline);
		sTextTimeBoldUnderline.setWrap(true);
		
		// Create Bold & Arial font
		WritableFont wTextArialBold = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
		sTextArialBold = new WritableCellFormat(wTextArialBold);
		sTextArialBold.setWrap(true);
		
		// Create Number Float
		WritableCellFormat wNumberFloat = new WritableCellFormat(NumberFormats.FLOAT);
		sNumberFloat  = new WritableCellFormat(wNumberFloat);
		sNumberFloat.setWrap(true);
		
		CellView cCellView = new CellView();
		cCellView.setFormat(sTextTime);
		cCellView.setFormat(sTextArialBold);
		cCellView.setFormat(sNumberFloat);
		cCellView.setFormat(sTextTimeBoldUnderline);

		// Write a few headers
		addCaption(sheet, 0, 0, "Bank");
		addCaption(sheet, 1, 0, "Request ID");
		addCaption(sheet, 2, 0, "Account No");
		addCaption(sheet, 3, 0, "Name");
		addCaption(sheet, 4, 0, "Leaves Qty");
		addCaption(sheet, 5, 0, "Delivery Branch");
		addCaption(sheet, 6, 0, "Currency");
		addCaption(sheet, 7, 0, "StNo");
		addCaption(sheet, 8, 0, "EndNo");
		addCaption(sheet, 9, 0, "Routing");
		addCaption(sheet, 10, 0, "MICR Account");
		addCaption(sheet, 11, 0, "TC");
	}

	private void createContent(WritableSheet sheet) throws WriteException,RowsExceededException {
		for (int i = 1; i < 20; i++) {
			// First column
			addLabel(sheet, 0, i, "Boring text ");
			// Second column
			addLabel(sheet, 1, i, "Another text");
			// Second column
			addLabel(sheet, 2, i, "Another text");
			// Second column
			addLabel(sheet, 3, i, "Another text");
			// Second column
			String s ="053120090001";
			addLabel(sheet, 4, i, s);
			
			int iTC=100;
			addNumber(sheet, 5, i,iTC);
			
			String a ="-3.2519999999999992000"; 
			addFloat(sheet, 6, i,-3.25199959999999992000);
		}
	}
	private void addCaption(WritableSheet sheet, int column, int row, String sValue) throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, sValue, sTextArialBold);
		sheet.addCell(label);
	}
	private void addFloat(WritableSheet sheet, int column, int row, double dValue) throws WriteException, RowsExceededException {
		Number number;		
		number = new Number(column,row,dValue,sNumberFloat); 
		sheet.addCell(number);
	}
	private void addNumber(WritableSheet sheet, int column, int row, int iValue) throws WriteException, RowsExceededException {
		Number number;		
		number = new Number(column,row,iValue); 
		sheet.addCell(number);
	}
	private void addLabel(WritableSheet sheet, int column, int row, String sValue) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, sValue);
		sheet.addCell(label);
	}
}
