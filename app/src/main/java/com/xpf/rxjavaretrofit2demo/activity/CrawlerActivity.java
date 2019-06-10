package com.xpf.rxjavaretrofit2demo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cv4j.piccrawler.PicCrawlerClient;
import com.cv4j.piccrawler.download.strategy.FileGenType;
import com.cv4j.piccrawler.download.strategy.FileStrategy;
import com.xpf.rxjavaretrofit2demo.R;

/**
 * Created by x-sir on 2019-06-10 :)
 * Function:简单图片爬虫
 */
public class CrawlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawler);

        //downloadSingle();
    }

    private void downloadSingle() {
        // 图片的地址
        String url = "http://www.designerspics.com/";
        PicCrawlerClient.get()
                .timeOut(6000)
                .fileStrategy(new FileStrategy() {

                    @Override
                    public String filePath() {
                        return "temp";
                    }

                    @Override
                    public String picFormat() {
                        return "png";
                    }

                    @Override
                    public FileGenType genType() {
                        return FileGenType.AUTO_INCREMENT;
                    }
                })
                //.repeat(200) // 重复200次
                .build()
                .downloadWebPageImages(url);
    }
}
