package vip.wulang.spring.server.request;

/**
 * @author coolerwu
 * @version 1.0
 */
public class EncloseStream {
    private IRequest request;
    private IResponse response;

    public IRequest getRequest() {
        return request;
    }

    public void setRequest(IRequest request) {
        this.request = request;
    }

    public IResponse getResponse() {
        return response;
    }

    public void setResponse(IResponse response) {
        this.response = response;
    }

    public EncloseStream(IRequest request, IResponse response) {
        this.request = request;
        this.response = response;
    }
}
