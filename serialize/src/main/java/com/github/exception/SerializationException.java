package com.github.exception;

/**
 * @author 吴海旭
 * Date: 2018-05-16
 * Time: 下午6:59
 */
public class SerializationException extends RuntimeException {
	private static final long serialVersionUID = 8729751979877029879L;

	public SerializationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SerializationException(String msg) {
		super(msg);
	}
}
