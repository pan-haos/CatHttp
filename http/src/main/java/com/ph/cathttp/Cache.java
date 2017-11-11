package com.ph.cathttp;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-11-4  下午8:51
 */
public interface Cache {

    /**
     * 获取指定request的response
     *
     * @param request
     * @return
     */
    Response get(Request request);

    /**
     * 向缓存中添加元素
     *
     * @param key
     * @param value
     */
    void put(Request key, Response value);


    /**
     * 移除指定元素
     *
     * @param key
     */
    void remove(Request key);


    /**
     * 清空缓存
     */
    void clear();

}


