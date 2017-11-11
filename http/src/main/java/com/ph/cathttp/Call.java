package com.ph.cathttp;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-10-28  上午10:04
 */
public interface Call {


    /**
     * 同步执行
     *
     * @return response
     */
    Response execute();

    /**
     * 异步执行
     *
     * @param callback 回调接口
     */
    void enqueue(Callback callback);

}
