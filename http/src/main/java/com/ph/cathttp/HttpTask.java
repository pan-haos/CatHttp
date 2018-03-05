package com.ph.cathttp;

import java.io.IOException;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-10-26  下午11:37
 */
public class HttpTask implements Runnable {

    private HttpCall call;
    private Callback callback;
    private IRequestHandler requestHandler;
    private IResponseHandler handler = IResponseHandler.RESPONSE_HANDLER;

    public HttpTask(HttpCall call, Callback callback, IRequestHandler requestHandler) {
        this.call = call;
        this.callback = callback;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            Response response = requestHandler.handlerRequest(call);
            handler.handlerSuccess(callback, response);
        } catch (IOException e) {
            handler.handFail(callback, call.request, e);
        }
    }


}
