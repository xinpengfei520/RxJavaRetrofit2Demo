# RxJavaRetrofit2Demo

Retrofit2 + RxJava Demo

## 1.RxJava 知识结构图：

![示意图](http://upload-images.jianshu.io/upload_images/944365-4c1c1eb44ffe01e5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 1.1 入门教程

关于RxJava的入门教程，看这篇就够了

[Android：这是一篇 清晰 & 易懂的Rxjava 入门教程](http://www.jianshu.com/p/a406b94f3188)
[封装Retrofit2+RxJava2网络请求框架](https://www.jianshu.com/p/2e8b400909b7)


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

## 3.Open Source Rx Library

[RxLifecycle](https://github.com/trello/RxLifecycle)

[RxLifecycle](https://github.com/zhihu/RxLifecycle)

[AutoDispose](https://github.com/uber/AutoDispose)

[RxJavaInAction](https://github.com/fengzhizi715/RxJavaInAction)

[RxBinding](https://github.com/JakeWharton/RxBinding)

[sqlbrite](https://github.com/square/sqlbrite)

[sqlbrite3](https://square.github.io/sqlbrite/3.x/sqlbrite/)

[Android-ReactiveLocation](https://github.com/mcharmas/Android-ReactiveLocation)

[rx-preferences](https://github.com/f2prateek/rx-preferences)

[RxPermissions](https://github.com/tbruyelle/RxPermissions)

[ReactiveNetwork](https://github.com/pwittchen/ReactiveNetwork)

[RxDownload](https://github.com/ssseasonnn/RxDownload)

## 4.RxJava2.x 常用操作符列表

- All：判断 Observable 发射的所有的数据项是否都满足某个条件；
- Amb：给定多个 Observable，只让第一个发射数据的 Observable 发射全部数据；
- And/Then/When：通过模式（And条件）和计划（Then次序）组合两个或多个 Observable 发射的数据集；
- Average：计算 Observable发射的数据序列的平均值，然后发射这个结果；
- Buffer：缓存，可以简单理解为缓存，它定期从 Observable 收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个；
- Catch：捕获，继续序列操作，将错误替换为正常的数据，从 onError 通知中恢复；
- CombineLatest：当两个 Observables 中的任何一个发射了一个数据时，通过一个指定的函数组合每个 Observable 发射的最新数据（一共两个数据），然后发射这个函数的结果；
- Concat：不交错地连接多个 Observable 的数据；
- Connect：指示一个可连接的 Observable 开始发射数据给订阅者；
- Contains：判断 Observable 是否会发射一个指定的数据项；
- Count：计算 Observable 发射的数据个数，然后发射这个结果；
- Create：通过调用观察者的方法从头创建一个 Observable；
- Debounce：只有在空闲了一段时间后才发射数据，简单来说，就是如果一段时间没有操作，就执行一次操作；
- DefaultIfEmpty：发射来自原始 Observable 的数据，如果原始 Observable 没有发射数据，就发射一个默认数据；
- Defer：在观察者订阅之前不创建这个 Observable，为每一个观察者创建一个新的 Observable；
- Delay：延迟一段时间发射结果数据；
- Distinct：去重，过滤掉重复数据项；
- Do：注册一个动作占用一些 Observable 的生命周期事件，相当于 Mock 某个操作；
- Materialize/Dematerialize：将发射的数据和通知都当作数据发射，或者反过来；
- ElementAt：取值，取特定位置的数据项；
- Empty/Never/Throw：创建行为受限的特殊 Observable；
- Filter：过滤，过滤掉没有通过谓词测试的数据项，只发射通过测试的
- First：首项，只发射满足条件的第一条数据；
- flatMap：扁平映射，将 Observable 发射的数据转换为 Observables 集合，然后将这些 Observable 发射的数据平坦化地放进一个单独的 Observable，可以认为是一个将嵌套的数据结构展开的过程；
- From：将其他对象或数据结构转换为 Observable；
- GroupBy：分组，将原来的 Observable 拆分为 Observable 集合，将原始 Observable 发射的数据按 Key 分组，每一个 Observable 发射一组不同的数据；
- IgnoreElements：忽略所有的数据，只保留终止通知(onError 或 onCompleted)；
- Interval：创建一个定时发射整数序列的 Observable；
- Join：无论何时，如果一个 Observable 发射了一个数据项，只要在另一个 Observable 发射的数据项定义的时间窗口内，就将两个 Observable 发射的数据合并发射；
- Just：将对象或者对象集合转换为一个会发射这些对象的 Observable；
- Last：末项，只发射最后一条数据；
- Map：映射，对序列的每一项都应用一个函数变换 Observable 发射的数据，实质是对序列中的每一项执行一个函数，函数的参数就是这个数据项；
- Max：计算并发射数据序列的最大值；
- Merge：将两个 Observable 发射的数据组合并成一个；
- Min：计算并发射数据序列的最小值；
- ObserveOn：指定观察者观察 Observable 的调度程序（工作线程）；
- Publish：将一个普通的 Observable 转换为可连接的；
- Range：创建发射指定范围的整数序列的 Observable；
- Reduce：按顺序对数据序列的每一项数据应用某个函数，然后返回这个值；
- RefCount：使一个可连接的 Observable 表现得像一个普通的 Observable；
- Repeat：创建重复发射特定的数据或数据序列的 Observable；
- Replay：确保所有的观察者收到同样的数据序列，即使他们在 Observable 开始发射数据之后才订阅；
- Retry：重试，如果 Observable 发射了一个错误通知，重新订阅它，期待它正常终止辅助操作；
- Sample：取样，定期发射最新的数据，等同于数据抽样，有的实现中叫作 ThrottleFirst；
- Scan：扫描，对 Observable 发射的每一项数据应用一个函数，然后按顺序依次发射这些值；
- SequenceEqual：判断两个 Observable 是否按相同的数据序列；
- Serialize：强制 Observable 按次序发射数据并且功能是有效的；
- Skip：跳过前面的若干项数据；
- SkipLast：跳过后面的若干项数据；
- SkipUntil：丢弃原始 Observable 发射的数据，直到第二个 Observable 发射了一个数据，然后发射原始 Observable 的剩余数据；
- SkipWhile：丢弃原始Observable发射的数据，直到一个特定的条件为假，然后发射原始 Observable 剩余的数据；
- Start：创建发射一个函数返回值的 Observable；
- StartWith：在发射原来的 Observable 的数据序列之前，先发射一个指定的数据序列或数据项；
- Subscribe：收到 Observable 发射的数据和通知后执行的操作；
- SubscribeOn：指定 Observable 应该在哪个调度程序上执行；
- Sum：计算并发射数据序列的和；
- Switch：将一个发射 Observable 序列的 Observable 转换为这样一个 Observable，即它逐个发射那些 Observable 最近发射的数据；
- Take：只保留前面的若干项数据；
- TakeLast：只保留后面的若干项数据；
- TakeUntil：发射来自原始 Observable 的数据，直到第二个 Observable 发射了一个数据或一个通知；
- TakeWhile：发射原始 Observable 的数据，直到一个特定的条件为真，然后跳过剩余的数据；
- TimeInterval：将一个 Observable 转换为发射两个数据之间所耗费时间的 Observable；
- Timeout：添加超时机制，如果过了指定的一段时间没有发射数据，就发射一个错误通知；
- Timer：创建在一个指定的延迟之后发射单个数据的 Observable；
- Timestamp：给 Observable 发射的每个数据项添加一个时间戳；
- To：将 Observable 转换为其他对象或数据结构；
- Using：创建一个只在 Observable 生命周期内存在的一次性资源；
- Window：窗口，定期将来自 Observable 的数据拆分成一些 Observable 窗口，然后发射这些窗口，而不是每次发射一项；类似于 Buffer，但 Buffer 发射的是数据，Window 发射的是 Observable，每一个 Observable 发射原始 Observable 数据的一个子集；
- Zip：打包，使用一个指定的函数将多个 Observable 发射的数据组合在一起，然后将这个函数的结果作为单项数据发射；

## TODO

outside flexible config & RetrofitHelper need Application Context.