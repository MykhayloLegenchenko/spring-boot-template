package com.softreactor.template.domain;

import java.util.List;

/**
 * Represents search result.  
 */
public class SearchResult<T> {
	private List<T> result;
	private int total;

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
