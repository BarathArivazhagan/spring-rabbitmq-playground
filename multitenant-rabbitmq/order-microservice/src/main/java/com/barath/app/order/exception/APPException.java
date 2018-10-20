package com.barath.app.order.exception;

import com.barath.app.order.error.GlobalErrorStatus;

public class APPException extends Exception {

	private static final long serialVersionUID = 6153289978346088249L;

	public APPException() {
		super();

	}

	public APPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public APPException(String message, Throwable cause) {

		super(message, cause);

	}

	public APPException(String message) {
		super(message);

	}

	public APPException(GlobalErrorStatus status) {

		super(String.valueOf(status.value()));

	}

	public APPException(Throwable cause) {
		super(cause);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
