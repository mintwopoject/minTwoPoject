/**
 * Copyright 2018
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.qujie.mintwo.ustils;

import com.baomidou.mybatisplus.plugins.Page;
import com.qujie.mintwo.config.SystemConfig;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页工具类
 */
public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数
	private Integer total;
	//每页记录数
	private Integer pageSize;
	//总页数
	private Integer totalPage;
	//当前页数
	private Integer currPage;
	//列表数据
	private List<?> rows;

	/**
	 * 分页
	 * @param rows        列表数据
	 * @param total  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtil(List<?> rows, int total, int pageSize, int currPage) {
		this.rows = rows;
		this.total = total;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)total/pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtil(Page<?> page) {
		this.rows = page.getRecords();
		this.total = (int)page.getTotal();
		this.pageSize = page.getSize();
		this.currPage = page.getCurrent();
		this.totalPage = (int)page.getPages();
	}

	/**
	 * 转换页面
	 */
	public PageUtil(Map<String, Object> params , SystemConfig systemConfig) {
		Object page = params.get("currentpagecount");
		this.currPage = Integer.valueOf(page.toString());
		this.pageSize = Integer.valueOf(systemConfig.getPagenumber());
	}

	/**
	 * 转换页面 导出
	 */
	public PageUtil(Map<String, Object> params ) {
		Object currentpagecount = params.get("currentpagecount");
		Object currentThisPage = params.get("currentThisPage");
		this.currPage = Integer.valueOf( currentpagecount.toString() );
		this.pageSize = Integer.valueOf( currentThisPage.toString() );
	}

	public PageUtil() {

	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
