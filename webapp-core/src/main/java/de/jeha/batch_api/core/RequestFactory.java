package de.jeha.batch_api.core;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * @author jns
 */
class RequestFactory {

    public static HttpUriRequest create(String url, String method, String body) {
        if ("GET".equals(method)) {
            return new HttpGet(url);
        }
        if ("POST".equals(method)) {
            HttpPost post = new HttpPost(url);
            try {
                post.setEntity(new StringEntity(body, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return post;
        }
        if ("PUT".equals(method)) {
            HttpPut put = new HttpPut(url);
            try {
                put.setEntity(new StringEntity(body, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return put;
        }
        if ("DELETE".equals(method)) {
            return new HttpDelete(url);
        }
        if ("OPTIONS".equals(method)) {
            return new HttpOptions(url);
        }
        return null;
    }
}
