package com.ph.cathttp;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-10-29  下午11:02
 */
public class Response {

    final ResponseBody body;
    final String message;
    final int code;

    public Response(Builder builder) {
        this.body = builder.body;
        this.message = builder.message;
        this.code = builder.code;
    }


    public ResponseBody body() {
        return this.body;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }


    static class Builder {

        private ResponseBody body;
        private String message;
        private int code;

        public Builder body(ResponseBody body) {
            this.body = body;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Response build() {
            if (message == null) throw new NullPointerException("response message == null");
            if (body == null) throw new NullPointerException("response body == null");
            return new Response(this);
        }

    }

}
