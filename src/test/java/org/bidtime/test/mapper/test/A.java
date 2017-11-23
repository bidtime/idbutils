package org.bidtime.test.mapper.test;

import java.util.List;

/**
 * Created by bidtim on 2015/9/23.
 */
public class A {
	
	private Integer id;
	
	private C c;
	
	public C getC() {
		return c;
	}

	public void setC(C c) {
		this.c = c;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<B> getList() {
		return list;
	}

	public void setList(List<B> list) {
		this.list = list;
	}

	private List<B> list;

}
