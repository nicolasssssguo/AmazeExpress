package com.nicolasguo.express.entity;

import java.util.List;

public class Page<T> {

	public static final int DEFAULT_PAGE_SIZE = 10;

	// 结果集
	private List<T> data;

	// 查询记录总数
	private long recordsTotal;

	private long recordsFiltered;

	private int start;

	// 每页多少条记录
	private int length = DEFAULT_PAGE_SIZE;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
		setRecordsFiltered(recordsTotal);
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
