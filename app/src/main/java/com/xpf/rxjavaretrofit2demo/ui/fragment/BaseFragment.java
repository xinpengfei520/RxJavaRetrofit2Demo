package com.xpf.rxjavaretrofit2demo.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.xpf.rxjavaretrofit2demo.eventbus.RxBus;


/**
 * Created by x-sir on 2017/6/8.
 * Function:BaseFragment
 */
public class BaseFragment extends Fragment {

    /**
     * Fragment 所在的 FragmentActivity
     */
    public Activity mContext;
    protected RxBus mRxBus;

    /**
     * Deprecated on API 23
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23) {
            this.mContext = activity;
        }
    }

    @TargetApi(23)
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mContext = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxBus = RxBus.get();
    }
}
