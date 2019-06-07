package com.xpf.rxjavaretrofit2demo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by x-sir on 2019-06-07 :)
 * Function:RxAndroidActivity
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxAndroidActivity extends AppCompatActivity {

    private static final String TAG = "RxAndroidActivity";
    private static final String IMAGE_URL = "http://image.x-sir.com/FhaQ3TFtgnEnaDxOllrIjS-VPTAn";
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);
        ButterKnife.bind(this);

        loadImage();
    }

    private void loadImage() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> emitter.onNext(IMAGE_URL))
                // 设置图片加载在主线程中进行
                .subscribeOn(AndroidSchedulers.mainThread())
                // 设置加载数据在子线程中进行
                .observeOn(Schedulers.io())
                .map(s -> getBitmap(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap);
                            }
                        },
                        throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete()"));
    }

    private Bitmap getBitmap(String imgUrl) {
        HttpURLConnection connection;
        try {
            URL url = new URL(imgUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(20000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeResource(getResources(), R.drawable.img_place_holder);
    }
}
