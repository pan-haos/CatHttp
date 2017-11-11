package com.ph.cathttp;

import java.io.UnsupportedEncodingException;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-11-2  下午9:02
 */
public class Util {

    public static void checkMap(String key, String value) {
        if (key == null) throw new NullPointerException("key == null");
        if (key.isEmpty()) throw new NullPointerException("key is empty");
        if (value == null) throw new NullPointerException("value == null");
        if (value.isEmpty()) throw new NullPointerException("value is empty");
    }

    public static void checkMethod(Request.HttpMethod method, RequestBody body) {
        if (method == null)
            throw new NullPointerException("method == null");
        if (body != null && Request.HttpMethod.checkNoBody(method))
            throw new IllegalStateException("方法" + method + "不能有请求体");
        if (body == null && Request.HttpMethod.checkNeedBody(method))
            throw new IllegalStateException("方法" + method + "必须有请求体");
    }


    /**
     * 转换成file的头
     *
     * @param key
     * @param fileName
     * @return
     */
    public static String trans2FileHead(String key, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(MultipartBody.disposition)
                .append("name=")//name=

                .append("\"").append(key).append("\"").append(";").append(" ")//"key";

                .append("filename=")//filename

                .append("\"").append(fileName).append("\"")//"filename"

                .append("\r\n");

        return sb.toString();
    }

    /**
     * 转换成表单形式
     *
     * @param key
     * @return
     */
    public static String trans2FormHead(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(MultipartBody.disposition)
                .append("name=")//name=

                .append("\"").append(key).append("\"") //"key"

                .append("\r\n");//next line

        return sb.toString();
    }

    public static byte[] getUTF8Bytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }


}
