package com.ph.cathttp;

import android.util.LruCache;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 暂时不用
 * 时间：17-11-4  下午9:11
 */
public final class ResponseCache implements Cache {

    private LruCache<Request, Response> lruCache = new LruCache<Request, Response>((int) (Runtime.getRuntime().maxMemory() / 8)) {
        @Override
        protected int sizeOf(Request key, Response value) {
            return value.body.bytes.length;
        }
    };

    @Override
    public Response get(Request request) {
        return lruCache.get(request);
    }

    @Override
    public void put(Request key, Response value) {
        lruCache.put(key, value);
    }

    @Override
    public void remove(Request key) {
        lruCache.remove(key);
    }

    @Override
    public void clear() {
        lruCache.evictAll();
        lruCache = null;
    }

}
