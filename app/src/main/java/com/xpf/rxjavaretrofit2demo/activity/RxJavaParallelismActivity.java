package com.xpf.rxjavaretrofit2demo.activity;

import android.annotation.TargetApi;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by x-sir on 2019-06-03 :)
 * Function:RxJava 2.x 并行编程
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJavaParallelismActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaParallelism";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_parallelism);

        //parallelismWithJava8();
        //parallelismWithRxJavaFlatMap();
        //parallelismWithRoundRobin();
        parallelFlowable();
    }

    /**
     * 并非所有的顺序操作在并行中都是有意义的，目前 ParallelFlowable 只支持如下操作：
     * map、filter、flatMap、concatMap、reduce、collect、sorted、toSortedList、compose、fromArray、
     * doOnCancel、doOnError、doOnComplete、doOnNext、doAfterNext、doOnSubscribe、doAfterTerminated、
     * doOnRequest
     * 优先推荐使用 ParallelFlowable 实现并行，对于无法使用 ParallelFlowable 的操作符，则使用 flatMap 来实现
     */
    private void parallelFlowable() {
        ParallelFlowable<Integer> parallelFlowable = Flowable.range(1, 100).parallel();

        parallelFlowable
                .runOn(Schedulers.io())
                .map(integer -> integer.toString())
                .sequential()
                .subscribe(s -> LogUtil.i(TAG, "s===" + s));
    }

    /**
     * Round-Robin 算法是最简单的一种负载均衡算法，它的原理是把来自用户的请求轮流分配给内部的服务器，从服务器1开始，直到
     * 服务器 N，然后重新开始循环，也被成为哈希取模法，是非常常用的数据分片方法，它简洁，无需记录状态，是一种无状态调度。
     */
    private void parallelismWithRoundRobin() {
        // 这里是把数据按线程分组，分成5组，每组数据个数相同，一起发送处理，这样做的目的是可以减少 Observable 的创建，
        // 节省系统资源，但会增加处理时间，它可以看做是对时间和空间的综合考虑。
        int threadNum = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        final Scheduler scheduler = Schedulers.from(executorService);

        final AtomicInteger batch = new AtomicInteger(0);

        Observable.range(1, 100)
                .groupBy(integer -> batch.getAndIncrement() % 5)
                .flatMap((Function<GroupedObservable<Integer, Integer>, ObservableSource<?>>) integerIntegerGroupedObservable ->
                        integerIntegerGroupedObservable
                                //.observeOn(Schedulers.io())
                                .observeOn(scheduler)
                                .map(integer -> integer.toString()))
                .subscribe(o -> LogUtil.i(TAG, "o===" + o));
    }

    private void parallelismWithRxJavaFlatMap() {
        int threadNum = Runtime.getRuntime().availableProcessors() + 1;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        final Scheduler scheduler = Schedulers.from(executorService);

        Observable.range(1, 100)
                .flatMap((Function<Integer, ObservableSource<String>>) integer ->
                        Observable.just(integer)
                                //.subscribeOn(Schedulers.computation())
                                .subscribeOn(scheduler)
                                .map(integer1 -> integer1.toString()))
                .doFinally(() -> executorService.shutdown())
                .subscribe(s -> LogUtil.i(TAG, "s===" + s));
    }

    /**
     * 并发（concurrency）是指一个处理器同时处理多个任务，并行（parallelismWithJava8）是多个处理器或者是多核处理器同时处理多个
     * 不同的任务，并行是同时发生的多个并发事件，具有并发的含义，而并发不一定是并行。
     */
    @TargetApi(24)
    private void parallelismWithJava8() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i <= 100; i++) {
            list.add(i);
        }

        list.parallelStream()
                .map(Object::toString)
                .forEach(s -> LogUtil.i(TAG, "s=" + s + ",Current Thread Name=" + Thread.currentThread().getName()));

        // 上面的结果交错输出，Java 8 借助了 JDK 的 fork/join 框架来实现并行编程
    }


}
