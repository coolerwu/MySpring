package vip.wulang.spring.server.request.impl.http;

import vip.wulang.spring.server.request.CastType;
import vip.wulang.spring.server.request.IResponse;

import java.util.Map;

/**
 * This class is used for data object.
 *
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class HttpResponse implements IResponse {
    private String version;
    private int code;
    private String status;
    private Map<String, String> headers;
    private String message;

    public HttpResponse() {
    }

    @Override
    public CastType castType() {
        return CastType.HTTP11;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
