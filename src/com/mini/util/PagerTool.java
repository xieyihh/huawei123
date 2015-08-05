package com.mini.util;

/**
 * 实现分页功能
 */
public final class PagerTool
{
	private int totalRows;		 // 记录总数
	private int totalPages;		 // 总页数
	private int pageSize; 		 // 每页显示数据条数，默认为3条记录
	private int currentPage;	 // 当前页数
	private boolean hasPrevious;	 // 是否有上一页
	private boolean hasNext;		 // 是否有下一页

	public PagerTool()
	{
	}
	
	public void init(int totalRows, int pageSize, int currentPage, boolean hasPrevious, boolean hasNext)
	{
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.hasPrevious = hasPrevious;
		this.hasNext = hasNext;
		if (((double)totalRows / (double)pageSize) > (totalRows / pageSize))
		{
			totalPages = totalRows / pageSize + 1;
		}
		else
		{
			totalPages = totalRows / pageSize;
		}
		refresh(); // 刷新当前页面信息
	}

	// 取得上一页（重新设定当前页面即可）
	public int previous()
	{
		if (currentPage > 1)
		{
			return currentPage - 1;
		}
		return 1;
	}

	// 取得下一页
	public int next()
	{
		if (currentPage < totalPages)
		{
			return currentPage + 1;
		}
		return totalPages;
	}

	// 刷新当前页面信息
	public void refresh()
	{
		if (totalPages <= 1)
		{
			hasPrevious = false;
			hasNext = false;
		}
		else if (currentPage == 1 && totalPages > 1)
		{
			hasPrevious = false;
			hasNext = true;
		}
		else if (currentPage == totalPages)
		{
			hasPrevious = true;
			hasNext = false;
		}
		else
		{
			hasPrevious = true;
			hasNext = true;
		}
	}

	// Setters and Getters
	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		if (currentPage <= totalPages)
		{
			this.currentPage = currentPage;
		}
		this.refresh();
	}

	public boolean isHasNext()
	{
		return hasNext;
	}

	public void setHasNext(boolean hasNext)
	{
		this.hasNext = hasNext;
	}

	public boolean isHasPrevious()
	{
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious)
	{
		this.hasPrevious = hasPrevious;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
		this.refresh();
	}

	public int getTotalPages()
	{
		return totalPages;
	}

	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
		this.refresh();
	}

	public int getTotalRows()
	{
		return totalRows;
	}

	public void setTotalRows(int totalRows)
	{
		this.totalRows = totalRows;
		this.refresh();
	}

}
