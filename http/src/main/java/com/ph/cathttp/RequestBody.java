package com.ph.cathttp;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-10-29  下午9:23
 */
public abstract class RequestBody {

    /**
     * body的类型
     *
     * @return
     */
    abstract String contentType();

    /**
     * 将内容写出去
     *
     * @param ous
     */
    abstract void writeTo(OutputStream ous) throws IOException;


}
