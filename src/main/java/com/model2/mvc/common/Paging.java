package com.model2.mvc.common;
// W D 

import javax.servlet.ServletContext;

public class Paging {

	// Field
	private int start = 1;
	private int end = 1;
	private int total;
	private int totalPage;
	private int currentPage = 1;
	private boolean left;
	private boolean right;
	int pageSize;			// 페이지당 보여줄 행 개수
	int pageUnit;			// 하단 보여질 페이지 최대 개수
	
	
	// Constructor
	public Paging(ServletContext servletContext) {
		
		System.out.println("\ncom.model2.mvc.service");
		System.out.println("Paging");
		
		pageUnit = Integer.parseInt(servletContext.getInitParameter("pageUnit"));
		pageSize = Integer.parseInt(servletContext.getInitParameter("pageSize"));
	}

	
	// Method
	// 총 페이지수와 현재페이지수를 통해서 보여줄 시작페이지, 끝페이지, 왼쪽이동, 오른쪽이동 계산
	public void calculatePage (int total, int currentPage) {
		
		System.out.println("Paging().calculatePage");
		System.out.println("\ttotal= "+total);
		this.total = total;
		System.out.println("\tcurrentPage= "+currentPage);
		this.currentPage = currentPage;
		
		if (total > 0) {
			totalPage = (int) Math.ceil(total*1.0 / pageUnit); 
		}
		System.out.println("\ttotalPage= "+totalPage);
		
		if (totalPage > 0) {
			
			end = (int) Math.ceil(currentPage * 1.0 / pageUnit) * pageUnit;
			if (end > totalPage) {
				end = totalPage;
			}
			
			start = (int) (Math.ceil(currentPage * 1.0 / pageUnit) -1 ) * pageUnit + 1;
			
			left = currentPage > pageUnit;
			right = end < totalPage;
			
		}
		
		System.out.println("\tstartPage= "+start);
		System.out.println("\tendPage= "+end);
		System.out.println("\tleft?= "+left);
		System.out.println("\trigt?= "+right);
		
	}
	
	
	// Getter
	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public int getPageUnit() {
		return pageUnit;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
}
// class end
