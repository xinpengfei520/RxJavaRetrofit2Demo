package com.xpf.android.mvvm.net

import com.xpf.android.mvvm.MyApplication
import com.xpf.android.mvvm.constants.RequestUrl
import com.xpf.android.mvvm.utils.RxThreadUtils
import io.reactivex.Observable
import okhttp3.Request

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:
 */
class NetRequest private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: NetRequest? = null

        fun getInstance(): NetRequest {
            return INSTANCE ?: synchronized(NetRequest::class) {
                INSTANCE ?: NetRequest()
            }
        }
    }

    fun getRequestInfo(): Observable<String> {
        return Observable.create<String> {
            val builder = Request.Builder()
            val request = builder.url(RequestUrl.DATA_URL).build()
            val call = MyApplication.instance?.client!!.newCall(request)
            val response = call.execute()
            if (response.isSuccessful) {
                it.onNext(response.body!!.string())
            } else {
                it.onError(Exception("出现异常"))
            }
        }.compose(RxThreadUtils.observableToMain())
    }
}