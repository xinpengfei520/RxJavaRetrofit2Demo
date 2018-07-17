package com.xpf.rxjavaretrofit2demo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.api.RequestUrl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// Okhttp3异步请求的回调不在UI线程当中
public class Okhttp3DemoActivity extends Activity {

    private final String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "test.jpg";
    private Context mContext;
    private OkHttpClient client;
    private Request request;
    private ProgressDialog progressDialog;
    private Call downloadCall;

    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.get)
    Button get;
    @BindView(R.id.post)
    Button post;
    @BindView(R.id.downloadFile)
    Button downloadFile;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.image)
    ImageView image;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            loading.setVisibility(View.GONE);
            switch (msg.what) {
                case 100:
                    Object e = msg.obj;
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 200:
                    String response = (String) msg.obj;
                    tv.setText(response);
                    break;
                case 300:
                    int percent = msg.arg1;
                    Log.e("TAG", "percent===" + percent);
                    if (percent < 100) {
                        progressDialog.setProgress(percent);
                    } else {
                        progressDialog.dismiss();
                        Glide.with(mContext).load(FILE_PATH).into(image);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_okhttp3_demo);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
                downloadCall.cancel();
            }
        });
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("下载文件");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @OnClick({R.id.get, R.id.post, R.id.downloadFile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get:
                getMethod();
                break;
            case R.id.post:
                postMethod();
                break;
            case R.id.downloadFile:
                downLoad();
                break;
        }
    }

    private void downLoad() {
        progressDialog.show();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            client = new OkHttpClient();
            request = new Request.Builder()
                    .url(RequestUrl.DOWNLOAD_URL)
                    .build();
            downloadCall = client.newCall(request);
            downloadCall.enqueue(new DownloadCallback());
        }
    }

    private void postMethod() {
        tv.setText("");
        loading.setVisibility(View.VISIBLE);

        client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
//                        .add("page", "1")
                .build();
        request = new Request
                .Builder()
                .url(RequestUrl.BASE_URL)
                .post(formBody)
                .build();
        Call mCall = client.newCall(request);
        mCall.enqueue(new MyCallback());
    }

    private void getMethod() {
        tv.setText("");
        loading.setVisibility(View.VISIBLE);
        client = new OkHttpClient();
        Request.Builder builder =
                new Request
                        .Builder()
                        .url(RequestUrl.BASE_URL)
                        .method("GET", null);

        request = builder.build();
        Call mCall = client.newCall(request);
        mCall.enqueue(new MyCallback());
    }

    class MyCallback implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {
            Message msg = Message.obtain();
            msg.what = 100;
            msg.obj = e;
            handler.sendMessage(msg);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Message msg = Message.obtain();
            msg.what = 200;
            msg.obj = response.body().string();
            handler.sendMessage(msg);
        }
    }

    class DownloadCallback implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            File file = new File(FILE_PATH);
            FileOutputStream fileOutputStream;
            InputStream inputStream;
            inputStream = response.body().byteStream();
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[2048];

            long fileSize = response.body().contentLength();
            long temp = 0;
            int len;

            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
                temp = temp + len;
                int percent = (int) (temp * 100.0f / fileSize);
                Message msg = new Message();
                msg.what = 300;
                msg.arg1 = percent;
                handler.sendMessage(msg);
            }
            fileOutputStream.flush();
        }
    }
}
