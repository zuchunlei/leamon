package leamon.datafilter;

/**
 * �������ӿ�
 */
public interface Operator {

    /**
     * ��ǰ��������ȼ��Ƿ���ڸ����������
     * 
     * @param op
     * @return
     */
    boolean underPriority(Operator op);

    /**
     * ��õ�ǰ����������ȼ�
     * 
     * @return
     */
    int getPriority();

    /**
     * ��ǰ������Ƿ�Ϊ������������Ҫֻ")"��"}"��
     * 
     * @return
     */
    boolean isMatch();

    /**
     * �����ǰ���������Ƿ�������������ƥ��
     * 
     * @param str
     * @return
     */
    boolean match(String str);

    /**
     * ����������������Ϣ����Ҫ���ط�������������"&&"��"||"��
     * 
     * @return
     */
    String getDescription();

    /**
     * ִ�й�������
     * 
     * @param left
     * @param right
     * @return
     */
    boolean doFilter(boolean left, boolean right);
}
