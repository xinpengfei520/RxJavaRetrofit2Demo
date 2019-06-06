package com.xpf.rxjavaretrofit2demo.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;
import com.xpf.rxjavaretrofit2demo.utils.RxUtils;
import com.xpf.rxjavaretrofit2demo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by x-sir on 2019-06-03 :)
 * Function:RxBindingRecyclerView
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxBindingRecyclerView extends AppCompatActivity {

    private static final String TAG = "RxBindingRecyclerView";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding_recycler_view);
        ButterKnife.bind(this);

        rxPermissions = new RxPermissions(RxBindingRecyclerView.this);

        initData();
        scrollState();
        multipleClickEvent();
        toCall();
    }

    private void toCall() {
        // 方式一
//        RxView.clicks(tv2)
//                .subscribe(o -> rxPermissions
//                        .request(Manifest.permission.CALL_PHONE)
//                        .subscribe(granted -> {
//                            if (granted) {
//                                Intent intent = new Intent(Intent.ACTION_CALL);
//                                intent.setData(Uri.parse("tel:" + "10086"));
//                                startActivity(intent);
//                            } else {
//                                ToastUtil.showShort("授权失败！");
//                            }
//                        }));

        // 方式二 使用 compose 操作符
        RxView.clicks(tv2)
                // 此处也可以添加多个权限
                .compose(rxPermissions.ensure(Manifest.permission.CALL_PHONE))
                .subscribe(granted -> {
                    if (granted) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + "10086"));
                        startActivity(intent);
                    } else {
                        ToastUtil.showShort("授权失败！");
                    }
                });
    }

    /**
     * 在 Android App 中食使用 RxJava 存在一个最大的缺点，即不完整的订阅会导致内存泄露，因此系统尝试销毁正在运行时的
     * Observable 的 Activity/Fragment 时，会发生内存泄露，由于 Observable 正在运行，因此会持有其引用，因此系统
     * 无法对其回收，导致内存泄露，可以使用 RxLifecycle 来解决内存问题
     */
    private void multipleClickEvent() {
        Observable observable =
                RxView.clicks(tv1)
                        .compose(RxUtils.useRxViewTransformer(this))
                        .share();

        observable.subscribe(o -> ToastUtil.showShort("第一次监听"));

        observable.subscribe(o -> ToastUtil.showShort("第二次监听"));
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(String.valueOf(i));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(new MyAdapter(list));
    }

    private void scrollState() {
        // 观察 RecyclerView 的滚动状态
        RxRecyclerView
                //.childAttachStateChangeEvents(recyclerView)
                //.scrollEvents(recyclerView)
                .scrollStateChanges(recyclerView)
                .subscribe(integer -> LogUtil.i(TAG, "scrollState===" + integer));
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<String> mDatas;

        public MyAdapter(List<String> data) {
            this.mDatas = data;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final String item = mDatas.get(position);
            holder.textView.setText(item);
            RxView.clicks(holder.textView)
                    .subscribe(o -> ToastUtil.showShort(String.valueOf(item)));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView textView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}
