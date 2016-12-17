package com.xpf.rxjavaretrofit2demo.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xpf.rxjavaretrofit2demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VolleyDemoActivity extends Activity {

    @BindView(R.id.Volley)
    Button btn;
    @BindView(R.id.tv)
    TextView tv;
    private final String BASE_URL = "https://api.github.com/";
    private RequestQueue queue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_demo);
        ButterKnife.bind(this);

        queue = Volley.newRequestQueue(this);
        request = new StringRequest(Request.Method.GET, BASE_URL,
                new ResponseSuccessListener(), new ResponseFailListener());
    }

    @OnClick(R.id.Volley)
    public void onClick() {
        queue.add(request);
    }

    private class ResponseSuccessListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            tv.setText(response);
        }
    }

    private class ResponseFailListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(VolleyDemoActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
