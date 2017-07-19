package com.nicolasguo.express.entity;

import java.util.List;

public class Page<T> {

	public static final int DEFAULT_PAGE_SIZE = 10;

	// 结果集
	private List<T> data;

	// 查询记录总数
	private long recordsTotal;

	// 每页多少条记录
	private int pageSize = DEFAULT_PAGE_SIZE;

	// 第几页
	private int pageNo = 1;

	/**
	 * @return 总页数
	 */
	public int getTotalPages() {
		return (int) ((recordsTotal + pageSize - 1) / pageSize);
	}

	/**
	 * 计算当前页开始记录
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param currentPage
	 *            当前第几页
	 * @return 当前页开始记录号
	 */
	public int countOffset(int currentPage, int pageSize) {
		int offset = pageSize * (currentPage - 1);
		return offset;
	}

	/**
	 * @return 首页
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * @return 上一页
	 */
	public int getPreviousPageNo() {
		if (pageNo <= 1) {
			return 1;
		}
		return pageNo - 1;
	}

	/**
	 * @return 下一页
	 */
	public int getNextPageNo() {
		if (pageNo >= getBottomPageNo()) {
			return getBottomPageNo();
		}
		return pageNo + 1;
	}

	/**
	 * @return 尾页
	 */
	public int getBottomPageNo() {
		return getTotalPages();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
