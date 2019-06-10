package com.xpf.rxjavaretrofit2demo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.jakewharton.rxbinding2.view.RxView;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.Fragment2Event;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends BaseFragment {

    private TextView textView1;
    private Button btnSend1;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment1.
     */
    public static Fragment1 newInstance() {
        return new Fragment1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        textView1 = view.findViewById(R.id.textView1);
        btnSend1 = view.findViewById(R.id.btnSend1);
        initViews();
        return view;
    }

    private void initViews() {
        RxView.clicks(btnSend1)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> mRxBus.post(new Fragment2Event()));
    }

    public TextView getTextView1() {
        return textView1;
    }
}
