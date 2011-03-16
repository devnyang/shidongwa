package com.stone.exceptionHandle;

import java.io.IOException;

public interface MySubInterface extends MyInterfaceWithException {
	public void method1();
	
	/**
	 * exception can't be assumed as a method signature, just like return type. un-comment 
	 * below codes will get compiling exception "duplicated method".
	 */
	//public void method1() throws NullPointerException;
	
	
	
//	public void method2();
}
