package com.mbf.shiftmonth.utility;

public class AjaxResult {
	
	private boolean success;
	
	private Object result;
	
	private Object validationError;
	
	private AjaxException exception;
	
	private String securityProfile;
	
	public AjaxResult() { }
	
	public static AjaxResult success(Object result, String securityProfile){
		return new AjaxResult(result, securityProfile);
	}

	public static AjaxResult success(Object result){
		return new AjaxResult(result);
	}
	
	public static AjaxResult success(Boolean success){
		return new AjaxResult(success);
	}

	public static AjaxResult fail(Exception exception){
		return new AjaxResult(exception);
	}
	
	public AjaxResult validationError(Object validationError){
		setValidationError(validationError);
		return this;
	}
	
	public AjaxResult(Object result) {
		setSuccess(true);
		setResult(result);
	}
	
	public AjaxResult(Object result, String securityProfile) {
		setSecurityProfile(securityProfile);
		setSuccess(true);
		setResult(result);
	}
	
	public AjaxResult(Boolean success) {
		setSuccess(success);
	}
	
	public AjaxResult(Exception exception) {
		setSuccess(false);
		setException(new AjaxException(exception));
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public Object getResult() {
		return result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
	
	public AjaxException getException() {
		return exception;
	}
	
	public void setException(AjaxException exception) {
		this.exception = exception;
	}

	public Object getValidationError() {
		return validationError;
	}

	public void setValidationError(Object validationError) {
		this.validationError = validationError;
	}

	public String getSecurityProfile() {
		return securityProfile;
	}

	public void setSecurityProfile(String securityProfile) {
		this.securityProfile = securityProfile;
	}
	
}
