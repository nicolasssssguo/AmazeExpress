package com.nicolasguo.express.entity;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class Result<T> implements Serializable {

	private static final long serialVersionUID = -7064589363978719546L;

	private int code = HttpStatus.OK.value();
	private String msg = "success";
	private T data;

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

	public Result() {

	}

	public Result(T data) {
		this.data = data;
	}

	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Result(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

}