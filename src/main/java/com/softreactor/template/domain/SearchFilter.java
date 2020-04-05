package com.softreactor.template.domain;

/**
 * Holds search filter settings.
 *
 */
public class SearchFilter {

	private int offset;
	private int limit;
	private String search;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
