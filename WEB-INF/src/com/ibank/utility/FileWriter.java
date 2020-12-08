/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : File Write
 * Created Date : 
 * Modify Date  :
 *
 */
 
package com.ibank.utility;

import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.servlet.ServletContext;
import java.io.File;
import javax.servlet.http.HttpSession; 

public class FileWriter{ 
	public void readWrite(String srcFile, HttpSession session, String sCreateDirectory, String dstFile)throws Exception{
		try{
			File fDirectoryName = new File(sCreateDirectory);
			boolean isDirectoryName = fDirectoryName.exists();
			if (isDirectoryName == false){
			  isDirectoryName = (new File(sCreateDirectory)).mkdirs();
			}
			ServletContext servletContext = session.getServletContext();
			File binaryFile = new File(srcFile);
			FileInputStream inStream = new FileInputStream(binaryFile);
			File reportFile = new File(servletContext.getRealPath(dstFile));
			OutputStream outStream = new FileOutputStream(reportFile);
			int data;
			while((data=inStream.read())!=-1){
				outStream.write(data);
			}
			inStream.close();
			outStream.close();
		}catch(Exception ex){
			//ex.printStackTrace();
		}
	}
}
