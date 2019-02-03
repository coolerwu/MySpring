package vip.wulang.spring.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.wulang.spring.token.structure.UserInfo;
import vip.wulang.spring.token.utils.TokenUtil;
import vip.wulang.spring.utils.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class represents token producer.
 * notes: It is recommended that only low-level projects be tested.
 *
 * @author CoolerWu on 2018/1/24.
 * @version 1.0
 */
public class DefaultTokenProducer implements ITokenProducer {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTokenProducer.class);
    private Map<String, UserInfo> tokenStorage = new ConcurrentHashMap<>();
    private Map<String, String> usernameToTokenStorage = new ConcurrentHashMap<>();

    @Override
    public String productToken(String username, String password) {
        UserInfo user = new UserInfo(username, password);
        String token = doProductToken(user);

        if (!StringUtils.isEmpty(token)) {
            if (Objects.nonNull(usernameToTokenStorage.get(username))) {
                String tokenOriginal = usernameToTokenStorage.get(username);
                usernameToTokenStorage.remove(username);
                tokenStorage.remove(tokenOriginal);
            }

            while (Objects.nonNull(tokenStorage.get(token))) {
                user.updateSecretKey();
                token = doProductToken(user);
            }

            tokenStorage.put(token, user);
            usernameToTokenStorage.put(username, token);
            return token;
        }

        return null;
    }

    private String doProductToken(UserInfo user) {
        String tokenContains = user.toString();
        String token = "";
        boolean isException = false;

        try {
            token = TokenUtil.messageDigest(tokenContains);
        } catch (NoSuchAlgorithmException e) {
            isException = true;
        }

        if (isException) {
            LOGGER.error("NoSuchAlgorithmException Exception");
        }

        return token;
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
            String token = doProductToken(user);

            while (Objects.nonNull(tokenStorage.get(token))) {
                user.updateSecretKey();
                token = doProductToken(user);
            }

            if (!StringUtils.isEmpty(token)) {
                tokenStorage.put(token, user);
                usernameToTokenStorage.put(user.getUsername(), token);
            }
        }
    }
}
