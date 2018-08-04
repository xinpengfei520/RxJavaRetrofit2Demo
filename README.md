# RxJavaRetrofit2Demo

Retrofit2 + RxJava Demo

## 1.RxJava 知识结构图：

![示意图](http://upload-images.jianshu.io/upload_images/944365-4c1c1eb44ffe01e5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 1.1 入门教程
关于RxJava的入门教程，看这篇就够了

[Android：这是一篇 清晰 & 易懂的Rxjava 入门教程](http://www.jianshu.com/p/a406b94f3188)


### 1.2 操作符

`RxJava`如此受欢迎的原因，在于其**提供了丰富 & 功能强大的操作符，几乎能完成所有的功能需求**

[Android RxJava：最基础的操作符详解 - 创建操作符](http://www.jianshu.com/p/e19f8ed863b1)

[Android RxJava：变换操作符](http://www.jianshu.com/p/904c14d253ba)

[Android RxJava：组合 / 合并操作符](http://www.jianshu.com/p/c2a7c03da16d)

### 3.案例分析

[Android RxJava 实际应用案例讲解：网络请求轮询](http://www.jianshu.com/p/11b3ec672812)

[Android RxJava 实际应用案例讲解：网络请求嵌套回调](http://www.jianshu.com/p/5f5d61f04f96)

[Android RxJava 实际应用讲解：合并数据源 & 同时展示](http://www.jianshu.com/p/fc2e551b907c)

[Android RxJava 实际应用讲解：从磁盘 / 内存缓存中 获取缓存数据](http://www.jianshu.com/p/6f3b6b934787)

[Android RxJava 实际应用讲解：联合判断多个事件](http://www.jianshu.com/p/2becc0eaedab)

## 2.Retrofit

**retrofit-library** 是对 Retrofit 进行的封装，让使用更加方便，更加通用，可以配置缓存、Cookie、设置拦截器等。

**createService(ApiService.class)** 中的 **ApiService.class** 可以省略，即默认为 **ApiService.class**，或者传入自己定义的 Service.

### 2.1 Post Map Request

Map 作为请求的 Body

```
	RetrofitHelper
                .getInstance()
                .createService(ApiService.class)
                .postMapBody(RequestUrl.url, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            try {
                                LogUtil.i(TAG, body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.getMessage();
                        t.printStackTrace();
                    }
                });
```

### 2.2 Post RequestBody

首先调用如下方法将对象转换为 RequestBody 对象

```

	RequestBody body = Converter.toBody(object);

	RetrofitHelper
                .getInstance()
                .createService(ApiService.class)
                .postRequestBody(RequestUrl.url, body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            try {
                                LogUtil.i(TAG, body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.getMessage();
                        t.printStackTrace();
                    }
                });
```

### 2.3 Post formData

提交表单数据

```
	RetrofitHelper
                .getInstance()
                .createService(ApiService.class)
                .postFormData(RequestUrl.url, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            try {
                                LogUtil.i(TAG, body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.getMessage();
                        t.printStackTrace();
                    }
                });
```

### 2.4 post dynamic url

可以动态传入 URL (不同域的url) 如果是传入的是 Path，则会和 Base_url 拼接，如果是全域url，则会忽略Base_url(覆盖掉);注意仅仅是本次请求!

```
	RetrofitHelper
                .getInstance()
                .createService(ApiService.class)
                .postUrlBody(RequestUrl.url, body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            try {
                                LogUtil.i(TAG, body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.getMessage();
                        t.printStackTrace();
                    }
                });
```

### 2.5 不带参数的 get 请求

```
	RetrofitHelper
                .getInstance()
                .createService(ApiService.class)
                .getRequest(RequestUrl.url)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            try {
                                LogUtil.i(TAG, body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.getMessage();
                        t.printStackTrace();
                    }
                });
```

### 2.6 带参数的 get 请求

```
	RetrofitHelper
                .getInstance()
                .createService(ApiService.class)
                .getMapParam(RequestUrl.url, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            try {
                                LogUtil.i(TAG, body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.getMessage();
                        t.printStackTrace();
                    }
                });
```
