package com.ph.cathttp;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-10-28  上午10:23
 */
public class HttpCall implements Call {

    final Request request;

    final CatHttpClient.Config config;

    private IRequestHandler requestHandler = new RequestHandler();


    public HttpCall(CatHttpClient.Config config, Request request) {
        this.config = config;
        this.request = request;
    }


    @Override
    public Response execute() throws ExecutionException, InterruptedException {
        Callable<Response> task = new SyncTask();
        try {
            Response response = HttpThreadPool.getInstance().submit(task);
            return response;
        } catch (ExecutionException e) {
            throw e;
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public void enqueue(Callback callback) {
        Runnable runnable = new HttpTask(this, callback, requestHandler);
        HttpThreadPool.getInstance().execute(new FutureTask<>(runnable, null));
    }

    /**
     * 同步提交Callable
     */
    class SyncTask implements Callable<Response> {
        @Override
        public Response call() throws Exception {
            Response response = requestHandler.handlerRequest(HttpCall.this);
            return response;
        }
    }


}
