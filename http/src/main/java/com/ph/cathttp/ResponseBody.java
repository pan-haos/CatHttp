package com.ph.cathttp;

import java.io.UnsupportedEncodingException;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-10-30  下午11:43
 */
public class ResponseBody {

    byte[] bytes;

    public ResponseBody(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] bytes() {
        return this.bytes;
    }

    public String string() {
        try {
            return new String(bytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("byte can't change to utf-8");
        }
    }
}
