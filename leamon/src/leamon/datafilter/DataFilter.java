package leamon.datafilter;

import java.util.Map;

/**
 * 数据过滤器，对传入的dataMap进行基于规则的过滤操作
 */
public interface DataFilter {

	/**
	 * 对dataMap进行过滤操作，只有Map中的数据满足规则过滤通过
	 * 
	 * @param dataMap
	 * @return
	 */
	public boolean doFilter(Map<String, Object> dataMap);

}
