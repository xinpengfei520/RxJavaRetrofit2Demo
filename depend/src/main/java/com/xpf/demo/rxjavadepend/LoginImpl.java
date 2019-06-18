package com.xpf.demo.rxjavadepend;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public class LoginImpl {

    public void login(final String name, final String password) {
        new Thread() {
            @Override
            public void run() {
                //初始化App目录
                //每次登陆的时候必须做的步骤
                initAppFiles(new InitSystemListener() {
                    @Override
                    public void onSuccess(File file) {
                        //初始化App目录
                        //每次登陆的时候必须先初始化好app所有的目录后
                        // 才能初始化数据库。先有文件系统再有数据库文件
                        initSqliteData(new InitSqliteListener() {
                            @Override
                            public void onSuccess() {
                                //数据库初始化成功
                                //获取后台服务器的配置信息，并存入本地
                                getAppConfigFromWeb("http:www.192.168.1.102/getConfig", new IAppConfigListener() {
                                    @Override
                                    public void onSuccess(List<AppConfig> list) {
                                        //存入本地的配置文件
                                    }

                                    //得到正确的外网地址才能进行登陆
                                    @Override
                                    public void getWanUrl(String wanUrl) {
                                        reallyLogin(wanUrl, name, password, new ILoginListener() {
                                            @Override
                                            public void onSuccuess(User user) {
                                                /**
                                                 * 新建当前用户的数据库
                                                 */
                                                insertSqlite(user, new ILoadSqliteListener() {
                                                    @Override
                                                    public void onLoadSucess(int code) {
                                                        /**
                                                         * 跳转到主界面
                                                         */
                                                    }

                                                    @Override
                                                    public void onFail() {

                                                    }
                                                });
                                            }

                                            //登陆失败
                                            @Override
                                            public void onFaile(int code) {

                                            }

                                            //用户在其他地方登陆了
                                            @Override
                                            public void onUserOther() {

                                            }

                                            /**
                                             * 没有当前用户
                                             * @param code
                                             */
                                            @Override
                                            public void noUser(int code) {

                                            }

                                            /**
                                             * 密码不正确
                                             */
                                            @Override
                                            public void passWordErro() {

                                            }
                                        });


                                    }

                                    @Override
                                    public void onFail(int code) {

                                    }
                                });

                            }

                            @Override
                            public void onFail() {

                            }
                        });
                    }

                    @Override
                    public void onFaile() {

                    }
                });


            }
        }.start();
    }

    public void rxLogin(final String name, final String password) {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            File file = initAppFiles();
            emitter.onNext(file.toString());
        }).map(s -> initSqliteData())
                .map(s -> login()).subscribeOn(Schedulers.io())
                .subscribe(s -> {

                }, throwable -> {

                }, () -> {

                });
    }

    private String initAppFiles(String path) {
        return null;
    }

    private String login() {
        return null;
    }

    private String initSqliteData() {
        return null;
    }

    private String downLoad(String path) {
        return null;
    }

    private void insertSqlite(User user, ILoadSqliteListener iLoadSqliteListener) {

    }

    private void reallyLogin(String wanUrl, String name, String password, ILoginListener iLoginListener) {

    }

    private void getAppConfigFromWeb(String s, IAppConfigListener iAppConfigListener) {

    }

    private void initSqliteData(InitSqliteListener initSqliteListener) {


    }

    private File initAppFiles() {
        return null;
    }

    private void initAppFiles(InitSystemListener initSystemListener) {


    }


}
