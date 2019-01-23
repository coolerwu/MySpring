package vip.wulang.spring.token;

/**
 * This class is used to producing token, updating token, deleting token and querying token.
 *
 * @author CoolerWu on 2019/1/14.
 * @version 1.0
 */
public interface ITokenFactory {
    void create();

    void delete();

    void update();

    void query();
}
