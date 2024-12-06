package com.voicemate.usermanagementservice.common;

public class Result {

	private boolean status;
	private Object returnObject;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public Result() {
		status = true;
		returnObject = null;
	}

	public Result(boolean status) {
		this.status = status;
		returnObject = null;
	}

	public Result(boolean status, Object returnObject) {
		this.status = status;
		this.returnObject = returnObject;
	}

}
