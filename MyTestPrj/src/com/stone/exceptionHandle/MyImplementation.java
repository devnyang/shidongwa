package com.stone.exceptionHandle;

/**
 * test purpose:
 * 1. though the signature in interface has exception specified, the implement class is 
 * allowed to not "throw" this exception in method signature. Cause in this case, there 
 * is no possibility of a exception at all  
 * 
 * 2. During interface extension, sub interface can throw away(omit) exception specified 
 * in parent interface. This allow us to copy a corresponding business interface which we
 * don't need to handling complex such as remote exception
 * 
 * @author shidong
 *
 */
public class MyImplementation implements MySubInterface {

	public void method1() {
		// TODO Auto-generated method stub

	}

}
