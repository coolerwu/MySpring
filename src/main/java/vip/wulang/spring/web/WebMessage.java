package vip.wulang.spring.web;

import java.util.HashMap;
import java.util.Map;

/**
 * It is used for returning to front end.
 *
 * @author CoolerWu on 2019/1/24.
 * @version 1.0
 */
public class WebMessage {
    private int code;
    private Map<String, Object> extra = new HashMap<>();

    private WebMessage() {}

    public static WebMessage success() {
        WebMessage webMessage = new WebMessage();
        webMessage.code = 200;
        return webMessage;
    }

    public static WebMessage fail() {
        WebMessage webMessage = new WebMessage();
        webMessage.code = 400;
        return webMessage;
    }

    public WebMessage add(String key, Object value) {
        this.extra.put(key, value);
        return this;
    }

    public WebMessage addReason(Object value) {
        this.extra.put("reason", value);
        return this;
    }

    /**
     * This is used for returning failure code.
     *
     * @param status status code.
     * @return WebMessage object.
     */
    public static WebMessage addFailCodeStatus(long status) {
        WebMessage webMessage = new WebMessage();
        webMessage.code = 400;
        webMessage.extra.put("status", "0x" + Long.toHexString(status).toUpperCase());
        return webMessage;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        return "WebMessage{" +
                "code=" + code +
                ", extra=" + extra +
                '}';
    }
}
