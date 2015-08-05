package com.mini.entity;

import java.sql.Date;

/**
 * 复查项
 * 
 */
public class Physicalinit {
	
	private int id;/* 体检id */

	private String physicalplan;/*体检计划 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhysicalplan() {
		return physicalplan;
	}

	public void setPhysicalplan(String physicalplan) {
		this.physicalplan = physicalplan;
	}
	
}
