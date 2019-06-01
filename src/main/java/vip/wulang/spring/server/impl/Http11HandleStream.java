package vip.wulang.spring.server.impl;

import vip.wulang.spring.server.IHandleStream;
import vip.wulang.spring.server.request.EncloseStream;
import vip.wulang.spring.server.request.impl.http.HttpMethod;
import vip.wulang.spring.server.request.impl.http.HttpRequest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author coolerwu
 * @version 1.0
 */
public class Http11HandleStream implements IHandleStream {
    @Override
    public String protocol() {
        return "http/1.1";
    }

    @Override
    public EncloseStream handle(InputStream is, OutputStream os) throws IOException {
        HttpRequest request = encloseRequest(is);
        return new EncloseStream(request, null);
    }

    private HttpRequest encloseRequest(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        HttpRequest request = new HttpRequest();
        String line = br.readLine();

        int count = 0;

        while (true) {
            if (line != null) {
                break;
            }

            count++;
            line = br.readLine();

            if (count >= 50) {
                return null;
            }
        }

        String[] wordArr = line.split(" ");

        switch (wordArr[0].toLowerCase()) {
            case "get":
                request.setMethod(HttpMethod.GET);
                break;
            case "put":
                request.setMethod(HttpMethod.PUT);
                break;
            case "post":
                request.setMethod(HttpMethod.POST);
                break;
            case "delete":
                request.setMethod(HttpMethod.DELETE);
                break;
        }

        request.setUri(wordArr[1]);
        request.setVersion(wordArr[2]);
        Map<String, String> headers = new HashMap<>();

        while (!"".equals((line = br.readLine()) == null ? null : line.trim())) {
            if (line == null) {
                return request;
            }

            wordArr = line.split(":");
            headers.put(wordArr[0], wordArr[1]);
        }

        request.setHeaders(headers);
        return request;
    }
}
