package com.ph.cathttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 * 作者：潘浩
 * 项目：CatHttp
 * 时间：17-11-2  下午10:39
 */
public class RequestHandler implements IRequestHandler {

    @Override
    public Response handlerRequest(HttpCall call) throws IOException {

        HttpURLConnection connection = mangeConfig(call);

        if (!call.request.heads.isEmpty()) addHeaders(connection, call.request);

        if (call.request.body != null) writeContent(connection, call.request.body);

        if (!connection.getDoOutput()) connection.connect();

        //解析返回内容
        int responseCode = connection.getResponseCode();

        if (responseCode >= 200 && responseCode < 400) {
            byte[] bytes = new byte[1024];
            int len;
            InputStream ins = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = ins.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            Response response = new Response
                    .Builder()
                    .code(responseCode)
                    .message(connection.getResponseMessage())
                    .body(new ResponseBody(baos.toByteArray()))
                    .build();
            try {
                ins.close();
                connection.disconnect();
            } finally {
                if (ins != null) ins.close();
                if (connection != null) connection.disconnect();
            }
            return response;
        }
        throw new IOException(String.valueOf(connection.getResponseCode()));
    }

    /**
     * 用
     *
     * @param connection
     * @param body
     * @throws IOException
     */
    private void writeContent(HttpURLConnection connection, RequestBody body) throws IOException {
        OutputStream ous = connection.getOutputStream();
        body.writeTo(ous);
    }

    /**
     * HttpUrlConnection基本参数的配置
     *
     * @param call
     * @return
     * @throws IOException
     */
    private HttpURLConnection mangeConfig(HttpCall call) throws IOException {
        URL url = new URL(call.request.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(call.config.connTimeout);
        connection.setReadTimeout(call.config.readTimeout);
        connection.setDoInput(true);
        if (call.request.body != null && Request.HttpMethod.checkNeedBody(call.request.method)) {
            connection.setDoOutput(true);
        }
        return connection;
    }

    /**
     * 给对象添加请求头
     *
     * @param connection
     * @param request
     */
    private void addHeaders(HttpURLConnection connection, Request request) {
        Set<String> keys = request.heads.keySet();
        for (String key : keys) {
            connection.addRequestProperty(key, request.heads.get(key));
        }
    }

}
