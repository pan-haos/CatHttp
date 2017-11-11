package com.ph.cathttp;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-11-2  下午8:58
 */
public class FormBody extends RequestBody {

    // 限制参数不要过多(ArrayMap效率，而且很少需要破k的参数)
    public static final int MAX_FROM = 1000;

    final Map<String, String> map;

    public FormBody(Builder builder) {
        this.map = builder.map;
    }

    @Override
    public String contentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    public void writeTo(OutputStream ous) throws IOException {
        try {
            ous.write(transToString(map).getBytes("UTF-8"));
            ous.flush();
        } finally {
            if (ous != null) {
                ous.close();
            }
        }
    }

    /**
     * 拼接请求参数
     *
     * @param map
     * @return
     */
    private String transToString(Map<String, String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(key, "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(map.get(key), "UTF-8"));
        }
        return sb.toString();
    }


    public static class Builder {
        private Map<String, String> map;

        public Builder() {
            map = new ArrayMap<>();
        }

        public Builder add(String key, String value) {
            if (map.size() > MAX_FROM) throw new IndexOutOfBoundsException(" 请求参数过多");
            Util.checkMap(key, value);
            map.put(key, value);
            return this;
        }

        public Builder map(Map<String, String> map) {
            if (map.size() > MAX_FROM) throw new IndexOutOfBoundsException(" 请求参数过多");
            this.map = map;
            return this;
        }

        public FormBody build() {
            return new FormBody(this);
        }

    }


}
