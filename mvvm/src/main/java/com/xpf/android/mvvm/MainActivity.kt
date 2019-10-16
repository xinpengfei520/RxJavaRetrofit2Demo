package com.xpf.android.mvvm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xpf.android.mvvm.activity.SearchActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                    finish()
                }
    }
}
