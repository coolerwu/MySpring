package vip.wulang.spring.token.structure;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is data of user. It is for saving in container.
 *
 * @author CoolerWu on 2019/1/14.
 * @version 1.0
 */
public class UserInfo {
    private String username;
    private String password;
    private long loginTime;
    private List<Map<String, Object>> extra = new ArrayList<>();

    public UserInfo(String username) {
        this.username = username;
        loginTime = Instant.now().getEpochSecond();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(username, userInfo.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
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

    public List<Map<String, Object>> getExtra() {
        return extra;
    }

    public void setExtra(List<Map<String, Object>> extra) {
        this.extra = extra;
    }
}
