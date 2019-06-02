package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by x-sir on 2019-05-29 :)
 * Function:RxJava2.x 条件操作符（会根据条件进行数据发射或变换被观察者），布尔操作符（返回的结果全部为 boolean 值）
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJava2ConditionOperatorActivity extends AppCompatActivity {

    private static final String TAG = "RxJava2ConditionOperator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2_condition_operator);

        //all();
        //contains();
        //isEmpty();
        //amb();
        //defaultEmpty();
        //switchIfEmpty();
        //sequenceEqual();
        //skipUntil();
        //skipWhile();
        //takeUntil();
        takeWhile();
    }

    /**
     * 发射原始 Observable 发射的数据，直到某个指定的条件不成立
     * takeWhile 默认不在任何特定的调度器上执行
     */
    private void takeWhile() {
        Observable.just(1, 2, 3, 4, 5)
                .takeWhile(integer -> integer <= 2)
                .subscribe(integer -> LogUtil.i(TAG, "integer:" + integer));

        // 结果 3 4 5
    }

    /**
     * 当第二个 Observable 发射了一项数据或者终止时，丢弃原始 Observable 发射的任何数据
     * takeUntil 默认不在任何特定的调度器上执行
     */
    private void takeUntil() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .takeUntil(integer -> integer == 5)
                .subscribe(integer -> LogUtil.i(TAG, "integer:" + integer));

        // 结果 1 2 3 4 5
    }

    /**
     * 丢弃原始 Observable 发射的数据，直到一个指定的条件不成立
     * skipWhile 默认不在任何特定的调度器上执行
     */
    private void skipWhile() {
        Observable.just(1, 2, 3, 4, 5)
                .skipWhile(integer -> integer <= 2)
                .subscribe(integer -> LogUtil.i(TAG, "integer:" + integer));

        // 结果 3 4 5
    }

    /**
     * 丢弃原始 Observable 发射的数据，直到第二个 Observable 发射了一项数据
     * skipUntil 默认不在任何特定的调度器上执行
     */
    private void skipUntil() {
        Observable.intervalRange(1, 9, 0, 1, TimeUnit.MILLISECONDS)
                .skipUntil(Observable.timer(5, TimeUnit.MILLISECONDS))
                .subscribe(aLong -> LogUtil.i(TAG, "aLong:" + aLong));

        // 它会发射 5ms 之后的数据

        // 结果 6 7 8 9
    }

    /**
     * 判定两个 Observable 是否发射相同的数据序列
     * sequenceEqual 默认不在任何特定的调度器上执行
     */
    private void sequenceEqual() {
        Observable.sequenceEqual(Observable.just(1, 2, 3),
                Observable.just(1, 2, 3)
        ).subscribe(aBoolean -> LogUtil.i(TAG, "aBoolean:" + aBoolean));

        // 结果 true

        Observable.sequenceEqual(Observable.just(1, 2, 3),
                Observable.just(1, 2, 3, 4)
        ).subscribe(aBoolean -> LogUtil.i(TAG, "aBoolean:" + aBoolean));

        // 结果 false

        // 3 个参数的重载方法，可以传递一个函数用于比较两个数据项是否相同
        Observable.sequenceEqual(Observable.just(1, 2, 3),
                Observable.just(1, 2, 3),
                (integer, integer2) -> integer.equals(integer2)
        ).subscribe(aBoolean -> LogUtil.i(TAG, "aBoolean:" + aBoolean));

        // 结果 true
    }

    /**
     * 它与 defaultEmpty 的区别是，defaultEmpty 只能在被观察者不发送数据时发送一个默认的数据，
     * 如果想要发送更多的数据，则可以使用 switchIfEmpty 发送自定义的被观察者作为替代
     */
    private void switchIfEmpty() {
        Observable.empty()
                .switchIfEmpty(Observable.just(1, 2, 3))
                .subscribe(o -> LogUtil.i(TAG, "default value:" + o));

        // 结果 1 2 3
    }

    /**
     * 发射来自原始 Observable 的值，如果原始 Observable 没有发射任何值，就发射一个默认值
     * 其内部调用的是 switchIfEmpty 操作符，看源码就可知道
     */
    private void defaultEmpty() {
        Observable.empty()
                .defaultIfEmpty(8)
                .subscribe(o -> LogUtil.i(TAG, "default value:" + o));

        // 结果 8
    }

    /**
     * 给定两个或多个 Observable，它只发射首先发射数据或通知的那个 Observable 的所有数据，
     * 不管发射的是一个数据还是 onError、onComplete 通知，amb 将忽略和丢掉其他所有 Observable 的发射物
     */
    private void amb() {
        Observable.ambArray(Observable.just(1, 2, 3),
                Observable.just(4, 5, 6)
        ).subscribe(integer -> LogUtil.i(TAG, "integer:" + integer));

        // 结果 1 2 3

        Observable.ambArray(Observable.just(1, 2, 3).delay(1, TimeUnit.SECONDS),
                Observable.just(4, 5, 6)
        ).subscribe(integer -> LogUtil.i(TAG, "integer:" + integer));

        // 结果 4 5 6
    }

    /**
     * 判定 Observable 是否未发送任何数据
     */
    private void isEmpty() {
        Observable.just(2, 30, 22, 5, 60, 1)
                .isEmpty()
                .subscribe(aBoolean -> LogUtil.i(TAG, "aBoolean:" + aBoolean));

        // 结果 false
    }

    /**
     * 判定 Observable 是否发送了一个特定的值
     */
    private void contains() {
        Observable.just(2, 30, 22, 5, 60, 1)
                .contains(22)
                .subscribe(aBoolean -> LogUtil.i(TAG, "aBoolean:" + aBoolean));

        // 结果 true
    }

    /**
     * 判定 Observable 发射的所有数据是否都满足某个条件
     */
    private void all() {
        Observable.just(1, 2, 3, 4, 5)
                .all(integer -> integer < 10)
                .subscribe(aBoolean -> LogUtil.i(TAG, "aBoolean:" + aBoolean));

        // 结果 true

        Observable.just(1, 2, 3, 4, 5)
                .all(integer -> integer > 3)
                .subscribe(aBoolean -> LogUtil.i(TAG, "aBoolean:" + aBoolean));

        // 结果 false
    }
}
