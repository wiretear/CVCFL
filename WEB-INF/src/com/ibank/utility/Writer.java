/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Write File
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.utility;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import javax.servlet.ServletContext;
import java.io.File;
import org.apache.struts.upload.FormFile;
import javax.servlet.http.HttpSession; 

public class Writer{ 

	public void readWrite(FormFile file,HttpSession session, String sCreateDirectory, String sTempFile)throws Exception{
		try{
			File fDirectoryName = new File(sCreateDirectory);
			boolean isDirectoryName = fDirectoryName.exists();
			if (isDirectoryName == false){
			  isDirectoryName = (new File(sCreateDirectory)).mkdirs();
			}
			ServletContext servletContext = session.getServletContext();
			File reportFile = new File(servletContext.getRealPath(sTempFile));
			InputStream inStream=file.getInputStream();
			OutputStream outStream=new FileOutputStream(reportFile);
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