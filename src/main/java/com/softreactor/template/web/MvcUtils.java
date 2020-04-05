package com.softreactor.template.web;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.softreactor.template.domain.SearchFilter;
import com.softreactor.template.domain.SearchResult;

/**
 * MVC related utility methods. 
 */
@Component
public class MvcUtils {
	private static Properties configProperties;
	
	public MvcUtils(@Qualifier("configProperties") Properties configProperties) {
		MvcUtils.configProperties = configProperties;
	}

	/**
	 * Creates search filter from request. 
	 */
	public static SearchFilter getSearchFilter(HttpServletRequest request) {
		SearchFilter filter = new SearchFilter();

		String start = request.getParameter("start");
		if (start != null) {
			filter.setOffset(Integer.parseInt(start));
		}

		String length = request.getParameter("length");
		if (length != null) {
			filter.setLimit(Integer.parseInt(length));
		}

		int maxLimit = Integer.valueOf(configProperties.getProperty("search.maxLimit"));
		if (filter.getLimit() < 0 || filter.getLimit() > maxLimit) {
			filter.setLimit(maxLimit);
		}

		return filter;
	}

	public static class JsonSearchResult {
		private int draw;
		private int recordsTotal;
		private List<?> data;

		public int getDraw() {
			return draw;
		}

		public void setDraw(int draw) {
			this.draw = draw;
		}

		public int getRecordsTotal() {
			return recordsTotal;
		}

		public void setRecordsTotal(int recordsTotal) {
			this.recordsTotal = recordsTotal;
		}
		
		public int getRecordsFiltered() {
			return recordsTotal;
		}

		public List<?> getData() {
			return data;
		}

		public void setData(List<?> data) {
			this.data = data;
		}
	}

	/**
	 * Returns JSON search result object. 
	 */
	public static <T> JsonSearchResult jsonSearchResult(
			HttpServletRequest request,
			SearchFilter filter,
			SearchResult<T> searchResult,
			Function<T, ?> dataMapper) {
		JsonSearchResult result = new JsonSearchResult();
		
		String draw = request.getParameter("draw");
		if (draw != null) {
			try {
				result.setDraw(Integer.parseInt(draw));
			}
			catch (NumberFormatException ex) {
			}
		}

		result.setRecordsTotal(searchResult.getTotal());
		result.setData(searchResult.getResult().stream().map(dataMapper).collect(Collectors.toList()));

		return result;
	}

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(
			FormatStyle.LONG,
			FormatStyle.MEDIUM);

	/**
	 * Formats date.
	 */
	public static String formatDateTime(Instant date) {
		return dateTimeFormatter.format(date);
	}

}
