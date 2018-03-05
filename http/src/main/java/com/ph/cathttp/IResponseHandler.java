package com.ph.cathttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-11-2  下午8:48
 */
public interface IResponseHandler {

    /**
     * 线程切换,http请求成功时的回调
     *
     * @param callback 　回调接口
     * @param response 　返回结果
     */
    void handlerSuccess(Callback callback, Response response);

    /**
     * 线程切换,http请求失败时候的回调
     *
     * @param callback 　回调接口
     * @param request  　请求
     * @param e        　可能产生的异常
     */
    void handFail(Callback callback, Request request, IOException e);


    IResponseHandler RESPONSE_HANDLER = new IResponseHandler() {

        Handler HANDLER = new Handler(Looper.getMainLooper());

        @Override
        public void handlerSuccess(final Callback callback, final Response response) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(response);
                }
            };
            execute(runnable);
        }

        @Override
        public void handFail(final Callback callback, final Request request, final IOException e) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callback.onFail(request, e);
                }
            };
            execute(runnable);
        }


        /**
         * 移除所有消息
         */
        public void removeAllMessage() {
            HANDLER.removeCallbacksAndMessages(null);
        }

        /**
         * 线程切换
         * @param runnable
         */
        private void execute(Runnable runnable) {
            HANDLER.post(runnable);
        }

    };

}
