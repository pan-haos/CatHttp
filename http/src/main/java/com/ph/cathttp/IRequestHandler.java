package com.ph.cathttp;

import java.io.IOException;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-11-2  下午10:38
 */
public interface IRequestHandler {

    /**
     * 处理请求
     *
     * @param call 　一次请求发起
     * @return 应答
     * @throws IOException 　网络连接或者其它异常
     */
    Response handlerRequest(HttpCall call) throws IOException;

}
