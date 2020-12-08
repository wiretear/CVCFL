/*
 *
 * Author       : Net Support Team
 * Project Name : My Bank - Net Banking
 * Package Name : Null Value Remove
 * Created Date : 
 * Modify Date  :
 *
 */

package com.ibank.utility;

import java.lang.reflect.Method;

public class RemoveNullValue {
	public void removeNullValue(Object oObjectName) {
		if (oObjectName !=null){
			Class cClassInfo = oObjectName.getClass();
			Method[] mMethods = cClassInfo.getDeclaredMethods();
			for (int i=0;i<mMethods.length;i++) {
				Method mMethod = mMethods[i];
				Class cMethodReturnType = mMethod.getReturnType();
				if (cMethodReturnType == String.class) {
					try {
						String sMethodName = mMethod.getName();
						String sSetterName = "set"+sMethodName.substring(3);
						Object[] oObject = new Object[0];
						Object oMethodReturnedValue = mMethod.invoke(oObjectName, oObject);
						if (oMethodReturnedValue == null) {
							try {
								Class[] cSetterValueType = new Class[]{String.class};
								Method mSetterMethod = cClassInfo.getDeclaredMethod(sSetterName,cSetterValueType);
								Object[] oObjectNullValue = new Object[]{""};
								mSetterMethod.invoke(oObjectName, oObjectNullValue);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
