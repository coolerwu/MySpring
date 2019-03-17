package vip.wulang.spring.token;

import vip.wulang.spring.token.structure.UserInfo;
import vip.wulang.spring.token.utils.TokenUtil;
import vip.wulang.spring.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class represents default token producer.
 * notes: It is recommended that only low-level projects be tested.
 *
 * update: by CoolerWu on 2018/3/8.
 * @author CoolerWu on 2018/1/24.
 * @version 1.0
 */
public class DefaultTokenProducer implements ITokenProducer {
    private Map<String, UserInfo> tokenStorage = new ConcurrentHashMap<>();
    private Map<String, String> usernameToTokenStorage = new ConcurrentHashMap<>();

    @Override
    public String productToken(String username, String password) {
        UserInfo user = new UserInfo(username, password);
        String token = TokenUtil.createToken(user);

        if (!StringUtils.isEmpty(token)) {
            // username may exist.
            if (Objects.nonNull(usernameToTokenStorage.get(username))) {
                String tokenOriginal = usernameToTokenStorage.get(username);
                usernameToTokenStorage.remove(username);
                tokenStorage.remove(tokenOriginal);
            }

            while (Objects.nonNull(tokenStorage.get(token))) {
                user.updateSecretKey();
                // add secret
                token = TokenUtil.createToken(user);
            }

            tokenStorage.put(token, user);
            usernameToTokenStorage.put(username, token);
            return token;
        }

        return null;
    }

    @Override
    public boolean whetherHaveTokenStorageData(String tokenHeader) {
        UserInfo user;

        if (Objects.nonNull(user = tokenStorage.get(tokenHeader))) {
            long now = System.currentTimeMillis();

            if (now - user.getLoginTime() <= 604800000L) {
                return true;
            }

            clearAboutUser(user);
            return false;
        }

        return false;
    }

    @Override
    public void deleteTokenStorageData(String tokenHeader) {
        UserInfo user;

        if (Objects.nonNull(user = tokenStorage.get(tokenHeader))) {
            clearAboutUser(user);
        }
    }

    @Override
    public UserInfo getTokenStorageData(String tokenHeader) {
        UserInfo user;

        if (Objects.nonNull(user = tokenStorage.get(tokenHeader))) {
            return user;
        }

        return null;
    }

    private void clearAboutUser(UserInfo user) {
        String token = usernameToTokenStorage.get(user.getUsername());
        usernameToTokenStorage.remove(user.getUsername());
        tokenStorage.remove(token);
    }

    @Override
    public void updateTokenStorageData(String tokenHeader) {
        UserInfo user;

        if (Objects.nonNull(user = tokenStorage.get(tokenHeader))) {
            clearAboutUser(user);
            user.updateSecretKey();
            user.setLoginTime(System.currentTimeMillis());
            String token = TokenUtil.createToken(user);

            while (Objects.nonNull(tokenStorage.get(token))) {
                user.updateSecretKey();
                token = TokenUtil.createToken(user);
            }

            if (!StringUtils.isEmpty(token)) {
                tokenStorage.put(token, user);
                usernameToTokenStorage.put(user.getUsername(), token);
            }
        }
    }
}
