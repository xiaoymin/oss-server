package com.xiaominfo.oss.exception;



public class UserNotLoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3173607918780955199L;
	
	
	public UserNotLoginException() {
		// TODO Auto-generated constructor stub
	}
	public UserNotLoginException(String msg){
		super(msg);
	}


}
