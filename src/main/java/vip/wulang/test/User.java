package vip.wulang.test;

import vip.wulang.spring.annotation.Component;

/**
 * @author CoolerWu on 2018/12/12.
 * @version 1.0
 */
@Component
public class User {
    private String username;
    private String password;

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
}
