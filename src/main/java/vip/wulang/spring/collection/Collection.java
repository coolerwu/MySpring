package vip.wulang.spring.collection;

/**
 * 容器ADT.
 *
 * @author coolerwu
 * @version 1.0
 */
public interface Collection<T> {
    /**
     * It will find and return object according to index.
     * @param index index
     * @return T Object
     */
    T get(int index);

    boolean set(T putCollectionObject);

    boolean set(int index, T putCollectionObject);

    T remove(int index);

    boolean contain(T checkedObject);
}
