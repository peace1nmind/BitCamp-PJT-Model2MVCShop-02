package com.model2.mvc.common;

import javax.servlet.ServletContext;

public class Search {
	
	///Field
	private int curruntPage = 1;
	private String searchCondition;
	private String searchKeyword;
	private int pageSize;
	
	///Constructor
	public Search(ServletContext servletContext) {
		if (servletContext != null) {
			pageSize = Integer.parseInt(servletContext.getInitParameter("pageSize"));
		} else {
			throw new NullPointerException("servletContext is null");
		}
	}
	
	///Method
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int paseSize) {
		this.pageSize = paseSize;
	}
	
	public int getCurrentPage() {
		return curruntPage;
	}
	public void setCurrentPage(int currentPage) {
		if (currentPage == 0) {
			currentPage = 1;
		}
		this.curruntPage = currentPage;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	@Override
	public String toString() {
		return String.format("SearchVO: [currentPage= %d, searchCondition= %s, searchKeyword= %s, pageSize= %d]",
				curruntPage, searchCondition, searchKeyword, pageSize);
	}
}