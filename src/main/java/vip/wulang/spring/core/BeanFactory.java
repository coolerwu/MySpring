package vip.wulang.spring.core;

/**
 * @author CoolerWu on 2018/12/11.
 * @version 1.0
 */
public interface BeanFactory {
    void createAllBean();

    Object getBeanByName(String name);

    <T> T getBeanByClass(Class<T> type);
}
