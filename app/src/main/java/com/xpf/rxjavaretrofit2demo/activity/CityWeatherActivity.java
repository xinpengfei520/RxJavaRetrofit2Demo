package com.xpf.rxjavaretrofit2demo.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.android.retrofit.RetrofitHelper;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.PM10Model;
import com.xpf.rxjavaretrofit2demo.bean.PM25Model;
import com.xpf.rxjavaretrofit2demo.bean.SO2Model;
import com.xpf.rxjavaretrofit2demo.bean.ZipObject;
import com.xpf.rxjavaretrofit2demo.service.WeatherService;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;
import com.xpf.rxjavaretrofit2demo.utils.RxJavaUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by x-sir on 2019-06-07 :)
 * Function:CityWeatherActivity
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class CityWeatherActivity extends AppCompatActivity {

    private static final String TAG = "CityWeatherActivity";
    private static final String CITY_ID = "shanghai";
    private static final String TOKEN = "5j1znBVAsnSf5xQyNQyq";
    private static final String API_PM25 = "http://www.pm25.in/api/querys/pm2_5.json";
    private static final String API_PM10 = "http://www.pm25.in/api/querys/pm10.json";
    private static final String API_SO2 = "http://www.pm25.in/api/querys/so2.json";

    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvQuality)
    TextView tvQuality;
    @BindView(R.id.tvPM25)
    TextView tvPM25;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {

        WeatherService weatherService = RetrofitHelper.getInstance().createService(WeatherService.class);

        Maybe<PM25Model> pm25ModelMaybe =
                weatherService
                        .pm25(API_PM25, CITY_ID, TOKEN)
                        .compose(RxJavaUtils.INSTANCE.maybeToMain())
                        // 让 filter 和 flatMap 也在 io 线程中进行
                        //.subscribeOn(Schedulers.io())
                        .filter(pm10Models -> pm10Models != null && pm10Models.size() > 0)
                        .flatMap((Function<List<PM25Model>, MaybeSource<PM25Model>>) pm25Models -> {
                            for (PM25Model pM25Model : pm25Models) {
                                if ("南门".equals(pM25Model.position_name)) {
                                    return Maybe.just(pM25Model);
                                }
                            }
                            return null;
                        });

        Maybe<PM10Model> pm10ModelMaybe =
                weatherService
                        .pm10(API_PM10, CITY_ID, TOKEN)
                        .compose(RxJavaUtils.INSTANCE.maybeToMain())
                        // 让 filter 和 flatMap 也在 io 线程中进行
                        //.subscribeOn(Schedulers.io())
                        .filter(pm10Models -> pm10Models != null && pm10Models.size() > 0)
                        .flatMap((Function<List<PM10Model>, MaybeSource<PM10Model>>) pm10Models -> {
                            for (PM10Model pM10Model : pm10Models) {
                                if ("南门".equals(pM10Model.position_name)) {
                                    return Maybe.just(pM10Model);
                                }
                            }
                            return null;
                        });

        Maybe<SO2Model> so2ModelMaybe =
                weatherService
                        .so2(API_SO2, CITY_ID, TOKEN)
                        .compose(RxJavaUtils.INSTANCE.maybeToMain())
                        // 让 filter 和 flatMap 也在 io 线程中进行
                        //.subscribeOn(Schedulers.io())
                        .filter(pm10Models -> pm10Models != null && pm10Models.size() > 0)
                        .flatMap((Function<List<SO2Model>, MaybeSource<SO2Model>>) so2Models -> {
                            for (SO2Model so2Model : so2Models) {
                                if ("南门".equals(so2Model.position_name)) {
                                    return Maybe.just(so2Model);
                                }
                            }
                            return null;
                        });

        // 合并多个网络请求
        Disposable disposable =
                Maybe.zip(pm25ModelMaybe, pm10ModelMaybe, so2ModelMaybe, (pm25Model, pm10Model, so2Model) -> {
                    ZipObject zipObject = new ZipObject();
                    zipObject.pm2_5_quality = pm25Model.quality;
                    zipObject.pm2_5 = pm25Model.pm2_5;
                    zipObject.pm2_5_24h = pm25Model.pm2_5_24h;
                    zipObject.pm10 = pm10Model.pm10;
                    zipObject.pm2_5_24h = pm10Model.pm10_24h;
                    zipObject.so2 = so2Model.so2;
                    zipObject.so2_24h = so2Model.so2_24h;
                    return zipObject;
                }).subscribe(zipObject -> {
                    if (zipObject != null) {
                        tvQuality.setText("空气质量指数：" + zipObject.pm2_5_quality);
                        tvPM25.setText("PM2.5：" + zipObject.pm2_5);
                    }
                }, throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()));

        compositeDisposable.add(disposable);

        // 重试机制
//        weatherService
//                .pm25(CITY_ID, TOKEN)
//                .retryWhen(new RetryWithDelay(3, 1000))
//                .compose(RxLifecycle.bind(this).toLifecycleTransformer());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
