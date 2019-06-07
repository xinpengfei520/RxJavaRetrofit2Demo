package com.xpf.rxjavaretrofit2demo.utils

import androidx.annotation.NonNull
import io.reactivex.Flowable
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

/**
 * Created by x-sir on 2019-06-07 :)
 * Function:重试机制，与 retryWhen 操作符搭配使用
 */
class RetryWithDelay(private val maxRetries: Int, private val retryDelayMillis: Int)
    : Function<Flowable<out Throwable>, Publisher<*>> {

    private var retryCount: Int = 0

    init {
        this.retryCount = 0
    }

    @Throws(Exception::class)
    override fun apply(@NonNull attempts: Flowable<out Throwable>): Publisher<*> {
        return attempts.flatMap { throwable ->

            if (++retryCount <= maxRetries) {
                Flowable.timer(retryDelayMillis.toLong(), TimeUnit.MILLISECONDS)
            } else {
                // max retries hit, just pass the error along.
                Flowable.error<Any>(throwable)
            }
        }
    }
}