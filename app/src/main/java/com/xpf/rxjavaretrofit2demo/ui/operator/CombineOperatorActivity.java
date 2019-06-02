package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;
import com.xpf.rxjavaretrofit2demo.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by x-sir on 2019-05-31 :)
 * Function:RxJava2.x 组合操作符
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class CombineOperatorActivity extends AppCompatActivity {

    private static final String TAG = "CombineOperatorActivity";
    private EditText etName;
    private EditText etAge;
    private EditText etGender;
    private Button btnCommit;
    /**
     * 模拟内存缓存 & 磁盘缓存中的数据
     */
    private String memoryCache = null;
    private String diskCache = "我是从磁盘缓存中获取的数据。";
    /**
     * 用于存放最终展示的数据
     */
    private String result = "数据源来自===";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_operator);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etGender = findViewById(R.id.etGender);
        btnCommit = findViewById(R.id.btnCommit);

        initListener();
        //threeLevelCache();
        //mergeDataSource();
        combineLatest();
    }

    private void initListener() {
        RxView.clicks(btnCommit)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    ToastUtil.showShort("提交成功~");
                });

        /*
         * 说明
         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，输入字符时都会发送数据事件（此处不会马上发送，因为使用了debounce（））
         * 3. 采用skip(1)原因：跳过 第1次请求 = 初始输入框的空字符状态
         **/
        RxTextView.textChanges(etName)
                .debounce(1, TimeUnit.SECONDS).skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        LogUtil.i(TAG, "发送给服务器的字符 = " + charSequence.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "对Error事件作出响应");

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void combineLatest() {
        /*
         * 步骤2：为每个EditText设置被观察者，用于发送监听事件
         * 说明：
         * 1. 此处采用了RxBinding，需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
         * 3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
         **/
        Observable<CharSequence> nameObservable = RxTextView.textChanges(etName).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(etAge).skip(1);
        Observable<CharSequence> genderObservable = RxTextView.textChanges(etGender).skip(1);

        /*
         * 步骤3：通过combineLatest（）合并事件 & 联合判断
         **/
        Observable.combineLatest(nameObservable, ageObservable, genderObservable, (charSequence, charSequence2, charSequence3) -> {

            /*
             * 步骤4：规定表单信息输入不能为空
             **/
            // 1. 姓名信息
            boolean isUserNameValid = !TextUtils.isEmpty(etName.getText().toString().trim());
            // 除了设置为空，也可设置长度限制
            // boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) && (name.getText().toString().length() > 2 && name.getText().toString().length() < 9);

            // 2. 年龄信息
            boolean isUserAgeValid = !TextUtils.isEmpty(etAge.getText().toString().trim());
            // 3. 职业信息
            boolean isUserJobValid = !TextUtils.isEmpty(etGender.getText().toString().trim());

            /*
             * 步骤5：返回信息 = 联合判断，即3个信息同时已填写，"提交按钮"才可点击
             **/
            return isUserNameValid && isUserAgeValid && isUserJobValid;
        }).subscribe(s -> {
            // 步骤6：返回结果 & 设置按钮可点击样式
            LogUtil.e(TAG, "提交按钮是否可点击：" + s);
            btnCommit.setEnabled(s);
        });
    }

    /**
     * 合并数据源操作符
     */
    private void mergeDataSource() {
        // 设置第1个Observable：通过网络获取数据，此处仅作网络请求的模拟
        Observable<String> network = Observable.just("网络");

        // 设置第2个Observable：通过本地文件获取数据，此处仅作本地文件请求的模拟
        Observable<String> file = Observable.just("本地文件");

        // 通过merge（）合并事件 & 同时发送事件
        Observable.merge(network, file)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        LogUtil.d(TAG, "onNext():" + value);
                        result += value + "+";
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "onError():" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "onComplete():" + result);
                    }
                });
    }

    /**
     * 模拟三级缓存的实现
     */
    private void threeLevelCache() {
        // 设置第1个Observable：检查内存缓存是否有该数据的缓存
        Observable<String> memory = Observable.create(emitter -> {
            // 先判断内存缓存有无数据
            if (memoryCache != null) {
                // 若有该数据，则发送
                emitter.onNext(memoryCache);
            } else {
                // 若无该数据，则直接发送结束事件
                emitter.onComplete();
            }
        });

        // 设置第2个Observable：检查磁盘缓存是否有该数据的缓存
        Observable<String> disk = Observable.create(emitter -> {
            // 先判断磁盘缓存有无数据
            if (diskCache != null) {
                // 若有该数据，则发送
                emitter.onNext(diskCache);
            } else {
                // 若无该数据，则直接发送结束事件
                emitter.onComplete();
            }
        });

        // 设置第3个Observable：通过网络获取数据(此处仅作网络请求的模拟)
        Observable<String> network = Observable.just("我是从网络中获取的数据。");

        /*
         * 通过concat（） 和 firstElement（）操作符实现缓存功能
         **/

        // 1. 通过concat（）合并 memory、disk、network 3个被观察者的事件（即检查内存缓存、
        // 磁盘缓存 & 发送网络请求）并将它们按顺序串联成队列
        Observable.concat(memory, disk, network)
                // 2. 通过firstElement()，从串联队列中取出并发送第1个有效事件（Next事件），即依次判断检查memory、disk、network
                .firstElement()
                // 即本例的逻辑为：
                // a. firstElement()取出第1个事件 = memory，即先判断内存缓存中有无数据缓存；由于memoryCache = null，即内存缓存中无数据，所以发送结束事件（视为无效事件）
                // b. firstElement()继续取出第2个事件 = disk，即判断磁盘缓存中有无数据缓存：由于diskCache ≠ null，即磁盘缓存中有数据，所以发送Next事件（有效事件）
                // c. 即firstElement()已发出第1个有效事件（disk事件），所以停止判断。
                // 3. 观察者订阅
                .subscribe(s -> LogUtil.d(TAG, "最终获取的数据来源===" + s));
    }
}
