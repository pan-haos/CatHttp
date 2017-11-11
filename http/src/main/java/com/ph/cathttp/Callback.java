package com.ph.cathttp;

import java.io.IOException;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-10-27  上午12:04
 */
public interface Callback {

    /**
     * 当成功拿到结果时返回
     *
     * @param response 　返回结果
     */
    void onResponse(Response response);


    /**
     * 当获取结果失败时
     *
     * @param request 　请求
     * @param e       　Http请求过程中可能产生的异常
     */
    void onFail(Request request, IOException e);

}
