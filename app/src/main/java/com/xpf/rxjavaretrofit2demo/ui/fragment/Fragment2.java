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
import com.xpf.rxjavaretrofit2demo.bean.Fragment1Event;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends BaseFragment {

    private TextView textView2;
    private Button btnSend2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment2.
     */
    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        textView2 = view.findViewById(R.id.textView2);
        btnSend2 = view.findViewById(R.id.btnSend2);
        initViews();
        return view;
    }

    private void initViews() {
        RxView.clicks(btnSend2)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> mRxBus.post(new Fragment1Event()));
    }

    public TextView getTextView2() {
        return textView2;
    }

}
