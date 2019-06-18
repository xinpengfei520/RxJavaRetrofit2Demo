package com.xpf.demo.rxjavadepend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginImpl login = new LoginImpl();
        login.login("david", "123456");
        login.rxLogin("david", "123456");
    }

    public void rxJava(View view) {
        // 创建observable
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            Log.i(TAG, "1.开始通知事件");
            emitter.onNext("http://192.168.1.102:8080/getImage");
        }).map(s -> {
            Log.i(TAG, "2.url 地址转化为 Bitmap");
            return downLoad(s);
        }).subscribe(bitmap -> Log.i(TAG, " 观察者接受通知，图片宽度：" + bitmap.getWidth() + "，图片高度：" + bitmap.getHeight())
                , throwable -> {
                    Log.e(TAG, "onError:" + throwable.getMessage());
                }, () -> {
                    Log.i(TAG, "onComplete");
                });
    }

    private Bitmap downLoad(String s) {
        Log.i(TAG, "正在下载");
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }
}
