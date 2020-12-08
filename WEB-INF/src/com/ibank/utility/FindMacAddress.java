/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : MAC & IP Address
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.utility;

import java.io.InputStream;
import java.io.BufferedInputStream;
 
public final class FindMacAddress {
	
	public final static String getMacAddress(String HostOS, String IPAddress) throws Exception {
		String OS = System.getProperty("os.name");
		Process ping = Runtime.getRuntime().exec("ping -n 3 " + IPAddress);
		Process arp = null;
		String outStream = "";
		StringBuffer buffer = new StringBuffer();
	try {
		if (HostOS.equals("SERVER")) {
			if (OS.startsWith("Windows")) {
				arp = Runtime.getRuntime().exec("arp -a " + IPAddress);
			} else if (OS.startsWith("Linux")) {
				arp = Runtime.getRuntime().exec("arp " + IPAddress);
			} else if (OS.startsWith("Solaris")) {
				arp = Runtime.getRuntime().exec("arp " + IPAddress);
			} else {
				outStream = "Unknown Operating System: " + OS;
			}
		} else if (HostOS.equals("CLIENT")) {
			if (OS.startsWith("Windows")) {
				arp = Runtime.getRuntime().exec("nbtstat -A " + IPAddress);
			} else if (OS.startsWith("Linux")) {
				arp = Runtime.getRuntime().exec("arp " + IPAddress);
			} else if (OS.startsWith("Solaris")) {
				arp = Runtime.getRuntime().exec("arp " + IPAddress);
			} else {
				outStream = "Unknown Operating System: " + OS;
			}
		} else {
			outStream = "Command not working.";
		}
		InputStream inStream = new BufferedInputStream(arp.getInputStream());
		for (;;) {
			int c = inStream.read();
			if (c == -1)
				break;
				buffer.append((char) c);
			}
		outStream = buffer.toString();
		inStream.close();
	  	} catch (Exception e) {
			//e.printStackTrace();
		}
  		return outStream;
	} 
}

