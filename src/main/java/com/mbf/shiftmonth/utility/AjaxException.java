package com.mbf.shiftmonth.utility;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AjaxException {
	
	private String stackTrace;
	
	private String message;
	
	public AjaxException() { }
	
	public AjaxException(Exception exception) { 
		setMessage(exception.getMessage());
		
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		
		setStackTrace(sw.toString());
	}
	
	public AjaxException(String message) {
		setMessage(message);
	}
	
	public AjaxException(String stackTrace, String message) {
		setStackTrace(stackTrace);
		setMessage(message);
	}
	
	public String getStackTrace() {
		return stackTrace;
	}
	
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
