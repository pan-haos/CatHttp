# CatHttp

CatHttp是一款轻量级的android网络访问框架。调用方式参考了OkHttp框架，内部使用的是HttpUrlConnection。想要使用OkHttp的同学请点传送门：[Okhttp](https://github.com/square/okhttp)

## 基本功能

 - 支持 get、post、put、delete 等请求方式
 - 同步和异步的方式来构建请求
 - 表单上传
 - 单/多  文件上传（可同时携带参数）


## 同步请求

```
public void syncClick(View view) {
        Request request = new Request.Builder()
                .url("http://192.168.31.34:8080/API/upkeep")
                .build();

        Response response = client.newCall(request).execute();

        if (response.code() == 200) {
            Logger.e("response msg " + response.body().string());
        }

    }
```

## 异步请求

```
public void asyncClick(View view) {

        Request request = new Request.Builder()
                .url("http://192.168.31.34:8080/API/upkeep")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) {
                if (response.code() == 200) {
                    // 执行在主线程中，建议不要直接持有activity引用，利用weak+static
                    String msg = response.body().string();
                    Logger.e("response msg = " + msg);
                }
            }

            @Override
            public void onFail(Request request, IOException e) {
                e.printStackTrace();
            }
        });
        
    }
```

## GET方式

```
public void getClick(View view) {
        Request request = new Request.Builder()
                .url("http://192.168.31.34:8080/API/upkeep?username=qq&pwd=123")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        
        if (response.code() == 200) {
            Logger.e(response.body().string());
        }
    }
```

## POST提交表单

```
public void postClick(View view) {
        FormBody body = new FormBody.Builder()
                .add("username", "浩哥")
                .add("pwd", "abc")
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.31.34:8080/API/upkeep")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        if (response.code() == 200) {
            Logger.e("msg" + response.body().string());
        }

    }
```

## 文件+参数提交

```
public void multipartClick(View view) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File file = new File(path + "/tempCropped.jpeg");
        File file1 = new File(path + "/head_image.png");
        File file2 = new File(path + "/shumei.txt");
        File file3 = new File(path + "/IMG_0358.MP4");

        MultipartBody body = new MultipartBody.Builder()
                .addForm("username", "123")
                .addPart("image/jpeg", "temp.jpg", file)
                .addPart("image/png", "head_image.png", file1)
                .addPart("text/plain", "1.txt", file2)
                .addPart("video/mpeg4", "2.mp4", file3)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.31.34:8080/API/upload")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        
        if (response != null && response.code() == 200) {
            Logger.e("msg== " + response.body().string());
        }
        
    }
```
# 文件格式

对于文件格式CatHttp目前没有进行处理，使用过程中实际传入的文件和对应格式请参照  [Content Type  文件格式对照表](http://tool.oschina.net/commons/)。
