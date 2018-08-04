package com.xpf.rxjavaretrofit2demo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.api.RequestUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by x-sir on 2016-12-21 :)
 * Function:HttpURLConnection + AsyncTask 实现异步网络请求
 */
public class HttpUrlConnectionActivity extends Activity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url_connection_acitivity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {
        new MyAsyncTask().execute(RequestUrl.BASE_URL);
    }

    class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            InputStream is = null;
            HttpURLConnection conn = getHttpUrlConnection(params[0]);
            String result = "";
            try {
                conn.connect();
                int responseCode = conn.getResponseCode();
                String message = conn.getResponseMessage();
                is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                StringBuffer sb = new StringBuffer();
                String len = null;

                while ((len = br.readLine()) != null) {
                    sb.append(len + "\n");
                }
                result = "responseCode=" + responseCode + ",message=" + message + "," + sb.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);
        }
    }

    private HttpURLConnection getHttpUrlConnection(String url) {
        HttpURLConnection conn = null;
        try {
            URL mUrl = new URL(url);
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Charset", "utf-8");
            conn.setRequestProperty("Content-Length", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
