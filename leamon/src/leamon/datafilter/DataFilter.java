package leamon.datafilter;

import java.util.Map;

/**
 * ���ݹ��������Դ����dataMap���л��ڹ���Ĺ��˲���
 */
public interface DataFilter {

	/**
	 * ��dataMap���й��˲�����ֻ��Map�е���������������ͨ��
	 * 
	 * @param dataMap
	 * @return
	 */
	public boolean doFilter(Map<Object, Object> dataMap);

}
