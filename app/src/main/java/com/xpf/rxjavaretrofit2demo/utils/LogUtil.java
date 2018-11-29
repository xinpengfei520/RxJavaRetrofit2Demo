package com.xpf.rxjavaretrofit2demo.utils;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.xpf.rxjavaretrofit2demo.BuildConfig;

import java.util.Collection;


/**
 * Created by x-sir on 2018-07-31 :)
 * Function:logger printer.
 */
public class LogUtil {

    private static final String DEFAULT_TAG = "TAG";
    private static boolean IS_NEED_PRINT_LOG = BuildConfig.DEBUG; // default

    public static void v(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.v(DEFAULT_TAG, msg);
        }
    }

    public static void d(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.d(DEFAULT_TAG, msg);
        }
    }

    public static void i(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.i(DEFAULT_TAG, msg);
        }
    }

    public static void w(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.w(DEFAULT_TAG, msg);
        }
    }

    public static void e(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.e(DEFAULT_TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.d(tag, msg);
        }
    }

    public static void iLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).i(msg);
        }
    }

    public static void wLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).w(msg);
        }
    }

    public static void eLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).e(msg);
        }
    }

    public static void vLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).v(msg);
        }
    }

    public static void dLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).d(msg);
        }
    }

    public static void json(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).json(msg);
        }
    }

    public static void xml(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).xml(msg);
        }
    }

    public static void collection(String tag, Collection<?> collection) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).d(collection);
        }
    }
}
