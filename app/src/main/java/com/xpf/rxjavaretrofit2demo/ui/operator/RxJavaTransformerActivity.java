package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by x-sir on 2019-06-02 :)
 * Function:RxJava 2.x RxJavaTransformer (转换器，它能够将一个 Observable、Flowable、Single、Completable、
 * Maybe 对象转换成另一个 Observable、Flowable、Single、Completable、Maybe 对象)
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJavaTransformerActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaTransformer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_transformer);

        Observable.just(123, 456)
                .compose(transformer())
                .subscribe(s -> LogUtil.i(TAG, "onNext:" + s));
    }

    public static ObservableTransformer<Integer, java.lang.String> transformer() {
        return upstream -> upstream.map((Function<Integer, java.lang.String>) java.lang.String::valueOf);
    }

//    public Flowable<ContentModel> getContent(Fragment fragment, ContentParam param, String cacheKey) {
//        return RetrofitHelper
//                .getInstance()
//                .createService(FlowableService.class)
//                .loadVideoContent(param)
//                .compose(RxJavaUtils.INSTANCE.flowableToMain());
//    }
}
