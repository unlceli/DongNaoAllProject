package com.example.dongnaoallproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {



    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化当前线程Looper
                Looper.prepare();
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        System.out.println(Thread.currentThread().getName() + "收到消息：" + message.obj);
                    }
                };
                //开启消息循环
                Looper.loop();
            }
        });
        mThread.setName("曹操");
        mThread.start();


    }

    public void sendCustomMessage(View view) {
        Thread mThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Message mMessage = new Message();
                mMessage.obj = Thread.currentThread().getName() + "发送消息";
                mHandler.sendMessage(mMessage);
            }
        });

        Thread mThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Message mMessage = new Message();
                mMessage.obj = Thread.currentThread().getName() + "发送消息";
                mHandler.sendMessage(mMessage);
            }
        });

        mThread1.setName("典韦");
        mThread2.setName("许褚");
        mThread1.start();
        mThread2.start();
    }



}
