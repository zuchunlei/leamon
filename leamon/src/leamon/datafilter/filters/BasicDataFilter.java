package leamon.datafilter.filters;

import java.util.Map;

import leamon.datafilter.DataFilter;

/**
 * 基础数据过滤器
 */
public class BasicDataFilter implements DataFilter {

	private String express;

	public BasicDataFilter(String express) {
		this.express = express;
	}

	@Override
	public boolean doFilter(Map<String, Object> dataMap) {
		return dataMap.containsKey(express);
	}

}
