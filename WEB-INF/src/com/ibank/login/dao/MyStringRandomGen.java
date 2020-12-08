/*
 * Created on Mar 23, 2016
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibank.login.dao;

import java.io.IOException;
import java.util.Random;

/**
 * @author Ashraful
 *
 */
public class MyStringRandomGen {
	
	
	private static final String CHAR_LIST =
						   //"abcdefghijklmnopqrstuvwxyz12345ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
					"1234567890";
					   private static final int RANDOM_STRING_LENGTH = 1;
     

	public String  generateRandomString() throws IOException{
         
						   StringBuffer randStr = new StringBuffer();
						   for(int i=0; i<RANDOM_STRING_LENGTH; i++){
							   int number = getRandomNumber();
							   char ch = CHAR_LIST.charAt(number);
							 
								randStr.append(ch);
						
						   }
						   return randStr.toString();
					   }
     
					   /**
						* This method generates random numbers
						* @return int
						*/
	private int  getRandomNumber() {
						   int randomInt = 0;
						   Random randomGenerator = new Random();
						   randomInt = randomGenerator.nextInt(CHAR_LIST.length());
						   if (randomInt - 1 == -1) {
							   return randomInt;
						   } else {
							   return randomInt - 1;
						  }
					   }
		          

    }


