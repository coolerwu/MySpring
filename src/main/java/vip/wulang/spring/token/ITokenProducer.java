package vip.wulang.spring.token;

import vip.wulang.spring.token.structure.UserInfo;

/**
 * The interface is used for producing token. We should follow it and can replace
 * {@link vip.wulang.spring.token.DefaultTokenProducer}.
 *
 * @author CoolerWu on 2018/1/24.
 * @version 1.0.
 */
public interface ITokenProducer {

    /**
     * The method is used for producing token by username and password.
     *
     * @param username username.
     * @param password password.
     * @return token
     */
    String productToken(String username, String password);

    /**
     * Determines whether the value exists.
     *
     * @param tokenHeader token which originates from javax.servlet.ServletRequest.
     * @return boolean.
     */
    boolean whetherHaveTokenStorageData(String tokenHeader);

    /**
     * Deletes info of token which originates from javax.servlet.ServletRequest.
     *
     * @param tokenHeader token which originates from javax.servlet.ServletRequest.
     */
    void deleteTokenStorageData(String tokenHeader);

    /**
     * Gets info of token which originates from javax.servlet.ServletRequest.
     *
     * @param tokenHeader token which originates from javax.servlet.ServletRequest.
     * @return info of user.
     */
    UserInfo getTokenStorageData(String tokenHeader);

    /**
     * Update token data.
     *
     * @param tokenHeader token which originates from javax.servlet.ServletRequest.
     */
    void updateTokenStorageData(String tokenHeader);
}
