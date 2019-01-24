package vip.wulang.spring.token.structure;

import java.util.*;

/**
 * This class is data of user. It is for saving in container.
 *
 * @author CoolerWu on 2019/1/14.
 * @version 1.0
 */
public class UserInfo {
    private static final int BOUND = 10000;
    private static Random random = new Random();

    private String username;
    private String password;
    private long loginTime;
    private int secretKey;

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
        loginTime = System.currentTimeMillis();
        secretKey = random.nextInt(BOUND);
    }

    public void updateSecretKey() {
        int secretKey;

        do {
            secretKey = random.nextInt(BOUND);
        } while (this.secretKey == secretKey);

        this.secretKey = secretKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return loginTime == userInfo.loginTime &&
                secretKey == userInfo.secretKey &&
                Objects.equals(username, userInfo.username) &&
                Objects.equals(password, userInfo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, loginTime, secretKey);
    }

    @Override
    public String toString() {
        return
                "username='" + username +
                "&password='" + password +
                "&loginTime=" + loginTime +
                "&secretKey=" + secretKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public int getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(int secretKey) {
        this.secretKey = secretKey;
    }
}
