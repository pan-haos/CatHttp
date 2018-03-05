package com.ph.cathttp;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.util.Map;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-10-27  上午12:05
 */
public class Request {

    final HttpMethod method;
    final String url;
    final Map<String, String> heads;
    final RequestBody body;

    public Request(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.heads = builder.heads;
        this.body = builder.body;
    }


    public static final class Builder {

        HttpMethod method;
        String url;
        Map<String, String> heads;
        RequestBody body;

        public Builder() {
            this.method = HttpMethod.GET;
            this.heads = new ArrayMap<>();
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder header(String name, String value) {
            Util.checkMap(name, value);
            heads.put(name, value);
            return this;
        }

        public Builder get() {
            method(HttpMethod.GET, null);
            return this;
        }

        public Builder post(RequestBody body) {
            method(HttpMethod.POST, body);
            return this;
        }

        public Builder put(RequestBody body) {
            method(HttpMethod.PUT, body);
            return this;
        }

        public Builder delete(RequestBody body) {
            method(HttpMethod.DELETE, body);
            return this;
        }

        public Builder method(HttpMethod method, RequestBody body) {
            Util.checkMethod(method, body);
            this.method = method;
            this.body = body;
            return this;
        }

        public Request build() {
            if (url == null) {
                throw new IllegalStateException("访问url不能为空");
            }
            if (body != null) {
                if (!TextUtils.isEmpty(body.contentType())) {
                    heads.put("Content-Type", body.contentType());
                }
            }
            heads.put("Connection", "Keep-Alive");
            heads.put("Charset", "UTF-8");
            return new Request(this);
        }
    }


    public enum HttpMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        public String methodValue = "";

        HttpMethod(String methodValue) {
            this.methodValue = methodValue;
        }

        public static boolean checkNeedBody(HttpMethod method) {
            return POST.equals(method) || PUT.equals(method);
        }

        public static boolean checkNoBody(HttpMethod method) {
            return GET.equals(method) || DELETE.equals(method);
        }
    }

}
