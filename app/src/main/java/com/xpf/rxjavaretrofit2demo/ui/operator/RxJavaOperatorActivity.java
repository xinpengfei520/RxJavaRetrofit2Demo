package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.Student;
import com.xpf.rxjavaretrofit2demo.bean.User;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by x-sir on 2019-05-21 :)
 * Function:RxJava2.x 创建、转换操作符
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJavaOperatorActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaOperatorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_operator);
        ButterKnife.bind(this);

        //createObservable();
        //iterable();
        //fromFuture();
        //repeat();
        //repeatWhen();
        //repeatUntil();
        //flatMap();
        //flatMap2();
        //flatMap3();
        flatMap4();
        //concatMap();
        //buffer();
        //window();
        //first();
        //last();
        //take();
        //takeLast();
        //skip();
        //elementAt();
        //distinct();
        //distinctUntilChanged();
        //debounce();
    }

    private void flatMap4() {
        Student student1 = new Student();

        List<Student.Course> list1 = new ArrayList<>();
        list1.add(new Student.Course("语文1"));
        list1.add(new Student.Course("数学1"));
        list1.add(new Student.Course("英语1"));
        list1.add(new Student.Course("物理1"));
        list1.add(new Student.Course("化学1"));

        student1.setName("张三");
        student1.setCourses(list1);

        Student student2 = new Student();

        List<Student.Course> list2 = new ArrayList<>();
        list2.add(new Student.Course("语文2"));
        list2.add(new Student.Course("数学2"));
        list2.add(new Student.Course("英语2"));

        student2.setName("李四");
        student2.setCourses(list2);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        Observable.just(studentList)
                .flatMap((Function<List<Student>, ObservableSource<Student>>) students -> Observable.fromIterable(students))
                .flatMap((Function<Student, ObservableSource<Student.Course>>) student -> {
                    LogUtil.i(TAG, "student name===" + student.getName());
                    return Observable.fromIterable(student.getCourses());
                })
                .subscribe(course -> LogUtil.i(TAG, "course===" + course.getCourseName()));


        Observable.just(studentList)
                .concatMap((Function<List<Student>, ObservableSource<Student>>) students -> Observable.fromIterable(students))
                .concatMap((Function<Student, ObservableSource<Student.Course>>) student -> {
                    LogUtil.i(TAG, "student name===" + student.getName());
                    return Observable.fromIterable(student.getCourses());
                })
                .subscribe(course -> LogUtil.i(TAG, "course===" + course.getCourseName()));
    }

    /**
     * 采用flatMap（）变换操作符
     * 输出的顺序可能是交叉的
     */
    private void flatMap3() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
        }).flatMap((Function<Integer, ObservableSource<String>>) integer -> {
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("我是事件 " + integer + "拆分后的子事件" + i);
                // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                // 最终合并，再发送给被观察者
            }
            return Observable.fromIterable(list);
        }).subscribe(s -> LogUtil.i(TAG, "s===" + s));
    }

    /**
     * 采用RxJava基于事件流的链式操作，采用concatMap（）变换操作符
     * 输出的顺序和发射的顺序是一致的
     */
    private void concatMap() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
        }).concatMap((Function<Integer, ObservableSource<String>>) integer -> {
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("我是事件 " + integer + "拆分后的子事件" + i);
                // 通过concatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                // 最终合并，再发送给被观察者
            }
            return Observable.fromIterable(list);
        }).subscribe(s -> LogUtil.d(TAG, s));
    }

    /**
     * 根据输入的字符串获取网站列表，并打印出网站名称
     */
    private void flatMap2() {
        query("Hello, world!")
                .flatMap(urls -> Observable.fromIterable(urls))
                .flatMap(url -> getTitle(url))
                .subscribe(title -> LogUtil.i(TAG, "onNext():" + title));
    }

    private Observable<String> getTitle(String url) {
        String title = "网站名称";
        if ("https://www.baidu.com".equals(url)) {
            title = "百度";
        } else if ("https://www.google.com".equals(url)) {
            title = "谷歌";
        }
        return Observable.just(title);
    }

    private Observable<List<String>> query(String text) {
        List<String> list = new ArrayList<>();
        list.add("https://www.baidu.com");
        list.add("https://www.google.com");
        return Observable.just(list);
    }

    /**
     * 仅在过了一段指定的时间还没发射数据时才发射一个数据
     * 还有一个使用 Function 函数来限制发送的数据
     * 跟他类似的是 throttleWithTimeout 操作符，它之用时间参数来限流的 debounce 的功能相同
     */
    private void debounce() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            if (emitter.isDisposed()) {
                return;
            }

            try {
                for (int i = 0; i < 10; i++) {
                    emitter.onNext(i);
                    Thread.sleep(i * 100);
                }
                emitter.onComplete();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(integer -> LogUtil.i(TAG, "onNext():" + integer),
                        throwable -> LogUtil.e(TAG, "onError():" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete():")
                );
    }

    /**
     * 判断数据的前驱是否相同，如果相邻的数据相同，则会过滤掉
     */
    private void distinctUntilChanged() {
        Observable.just(1, 2, 1, 2, 3, 4, 5, 5, 6)
                .distinctUntilChanged()
                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));
    }

    /**
     * 只允许还没有发射过的数据项通过
     * 还可以接受一个 Function 参数，这个函数根据原始的 Observable 发射的数据项产生一个 Key，然后比较这些 key而不是
     * 数据本身，来判定两个数据是否相等
     */
    private void distinct() {
        Observable.just(1, 2, 1, 2, 3, 4, 5, 5, 6)
                .distinct()
                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));
    }

    /**
     * 获取原始数据序列指定索引位置的数据，如果索引为负数，则会抛 IndexOutOfBoundsException
     * elementAt 还提供了一个带默认值的重载方法，它返回一个 Single 类型，如果 index 超出了
     * 索引范围，那么取默认值
     */
    private void elementAt() {
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(3)
                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));

        // ignoreElements 忽略所有发射的原始数据，只会调用 onError 或 onComplete, 确保永远不会调 onNext 方法
        Observable.just(1, 2, 3, 4, 5)
                .ignoreElements()
                .subscribe(() -> LogUtil.i(TAG, "onComplete:"), integer -> LogUtil.i(TAG, "accept:" + integer));
    }

    /**
     * 忽略发射的前 n 项数据，只保留之后的数据，也可以接受一个时间单位，
     * 还有 skipLast 操作符，忽略后面的数据，只保留前面的数据
     */
    private void skip() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(3)
                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));
    }

    /**
     * 发送最后 3 个数字，同 take 相反，重载方法也相反
     */
    private void takeLast() {
        Observable.just(1, 2, 3, 4, 5)
                .takeLast(3)
                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));
    }

    /**
     * 返回前面 n 项数据，发射完成通知，忽略剩余的数据，当发射的数据少于 n 项时，不会抛异常，而是有多少就发射多少
     * take 的重载方法可以接收一个时长参数，它会丢掉发射 Observable 开始的那段时间发射的数据，时长和单位通过参数指定
     */
    private void take() {
//        Observable.just(1,2,3,4,5)
//                .take(6)
//                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));

        // 发送 0-9 数字，1 秒发送一个
        Observable.intervalRange(0, 10, 1, 1, TimeUnit.SECONDS)
                // 此重载方法默认在 computation 调度器上执行，也可以用参数指定其他调度器
                .take(3, TimeUnit.SECONDS)
                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));
    }

    /**
     * 只发射最后一个（或者满足某个条件的最后一项）数据
     * 使用了 last() 会返回 Single 类型
     * 还有 lastElement 和 lastOrError
     */
    private void last() {
        Observable.just(1, 2, 3)
                .last(3)
                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));
    }

    /**
     * 只发射第一项（或者满足某个条件的第一项）数据
     * 使用了 first() 会返回 Single 类型
     * 还有 firstElement 操作符表示只取第一个数据，没有默认值，firstOrError 操作符表示要么能取到第一个数据，
     * 要么执行 onError 方法，他们分别返回 Maybe 类型和 Single 类型
     */
    private void first() {
//        Observable.just(1, 2, 3)
//                .first(1)
//                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer),
//                        throwable -> LogUtil.e(TAG, "onError():" + throwable.getMessage()));

        // 如果 Observable 不发射任何数据，那么 first 操作符的默认值就起了作用
        Observable.<Integer>empty()
                .first(1)
                .subscribe(integer -> LogUtil.i(TAG, "accept:" + integer),
                        throwable -> LogUtil.e(TAG, "onError():" + throwable.getMessage()));
    }

    /**
     * 定期将来自原始 Observable 的数据分解为一个 Observable 窗口，发射这些窗口，而不是每一次发射一项数据
     */
    private void window() {
        Observable.range(1, 10)
                .window(2)
                .subscribe(integerObservable -> {
                            LogUtil.i(TAG, "onNext():");
                            integerObservable.subscribe(integer -> LogUtil.i(TAG, "accept:" + integer));
                        },
                        throwable -> LogUtil.e(TAG, "onError():" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete():"));
    }

    /**
     * buffer 会定期收集 Observable 数据并放进一个数据包裹，然后发射这些数据包裹，然后发射这些数据包裹，而不是一次发射一个值
     */
    private void buffer() {
        Observable.range(1, 10)
                .buffer(2, 1)
                .subscribe(integers -> LogUtil.i(TAG, "onNext:" + integers),
                        throwable -> LogUtil.e(TAG, "onError():" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete():"));
    }

    /**
     * 将一个 Observable 拆分为一些 Observables 集合，他们中的每一个都发射原始 Observable 的一个子序列
     */
    private void groupBy() {
        Observable.range(1, 8)
                .groupBy(integer -> (integer % 2 == 0) ? "欧数组" : "奇数组")
                .subscribe(stringIntegerGroupedObservable -> {
                            LogUtil.i(TAG, "group name:" + stringIntegerGroupedObservable.getKey());
                            if ("奇数组".equals(stringIntegerGroupedObservable.getKey())) {
                                stringIntegerGroupedObservable.subscribe(integer ->
                                        LogUtil.i(TAG, "key:" + stringIntegerGroupedObservable.getKey() + ",integer:" + integer));
                            }
                        }
                );
    }

    /**
     * 将一个发射数据的 Observable 变换为多个 Observables，然后将他们发射的数据合并后放进一个单独的 Observable
     */
    private void flatMap() {
        User user = new User();
        user.name = "Jack";
        user.addressList = new ArrayList<>();

        User.Address address1 = new User.Address();
        address1.city = "Shanghai";
        address1.street = "nanjing road.";
        user.addressList.add(address1);

        User.Address address2 = new User.Address();
        address2.city = "Shanghai";
        address2.street = "xizang road.";
        user.addressList.add(address2);

        Observable.just(user)
                .flatMap((Function<User, ObservableSource<User.Address>>) user1 -> Observable.fromIterable(user1.addressList))
                .subscribe(address -> LogUtil.i(TAG, "city:" + address.city + ",street:" + address.street));
    }

    private void repeatUntil() {
        final long currentTimeMillis = System.currentTimeMillis();
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .take(3)
                .repeatUntil(() -> System.currentTimeMillis() - currentTimeMillis > 5000)
                .subscribe(aLong -> LogUtil.i(TAG, "aLong===" + aLong));
    }

    private void repeatWhen() {
        Observable.range(0, 4)
                .repeatWhen(objectObservable -> Observable.timer(2, TimeUnit.SECONDS))
                .subscribe(integer -> LogUtil.i(TAG, "integer===" + integer));
    }

    private void repeat() {
        Observable.just("Hello World!")
                .repeat(3)
                .subscribe(s -> LogUtil.i(TAG, s));
    }

    private void fromFuture() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(new MyCallable());

        Observable.fromFuture(future, 2, TimeUnit.SECONDS)
                .subscribe(s -> LogUtil.i(TAG, "任务运行的结果：" + s)
                        , throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()));
    }

    private void iterable() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i);
        }

        Observable.just(list).subscribe((Consumer<List>) list1 -> LogUtil.i(TAG, "just():" + Arrays.toString(list1.toArray())));

        Observable.fromIterable(list).subscribe(integer -> LogUtil.i(TAG, "fromIterable():" + integer));

        Observable.just(1, 2, 3, 4, 5).subscribe(integer -> LogUtil.i(TAG, "just():" + integer));
    }

    private void createObservable() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            try {
                if (!emitter.isDisposed()) {
                    for (int i = 0; i < 10; i++) {
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribe(integer -> LogUtil.i(TAG, "onNext():" + integer),
                throwable -> LogUtil.i(TAG, "onError():" + throwable.getMessage()),
                () -> LogUtil.i(TAG, "onComplete()"));
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            LogUtil.i(TAG, "call():在这里模拟一些耗时操作...");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i <= 100; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
