package leamon.datafilter.filters;

import java.util.Map;

import leamon.datafilter.DataFilter;

/**
 * �������ݹ�����
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
