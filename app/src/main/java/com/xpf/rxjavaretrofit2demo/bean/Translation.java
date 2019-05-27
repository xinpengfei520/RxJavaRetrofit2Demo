package com.xpf.rxjavaretrofit2demo.bean;

import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

/**
 * Created by x-sir on 2019-05-27 :)
 * Function:
 */
public class Translation {

    private static final String TAG = "Translation";
    private int status;
    private Content content;

    public static String getTAG() {
        return TAG;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    private static class Content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public int getErrNo() {
            return errNo;
        }

        public void setErrNo(int errNo) {
            this.errNo = errNo;
        }
    }

    public void show() {
        LogUtil.d(TAG, "RxJava2.x" + content.out);
    }
}
