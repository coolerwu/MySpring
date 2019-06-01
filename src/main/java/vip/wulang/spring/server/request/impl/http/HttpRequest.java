package vip.wulang.spring.server.request.impl.http;

import vip.wulang.spring.server.request.CastType;
import vip.wulang.spring.server.request.IRequest;

import java.util.Map;

/**
 * This class is used for data object.
 *
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class HttpRequest implements IRequest {
    private HttpMethod method;
    private String uri;
    private String version;
    private Map<String, String> headers;
    private String extra;

    public HttpRequest() {
    }

    public HttpRequest(HttpMethod method, String uri, String version, Map<String, String> headers, String extra) {
        this.method = method;
        this.uri = uri;
        this.version = version;
        this.headers = headers;
        this.extra = extra;
    }

    @Override
    public CastType castType() {
        return CastType.HTTP11;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
