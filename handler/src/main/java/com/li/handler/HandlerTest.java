package com.li.handler;

import java.util.UUID;

public class HandlerTest {

    //相当于activityThrad类中的main方法。
    public static void main(String[] args) {
        //初始化一个Looper
        Looper.prepare();

        //在主线程做事。

          final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("线程" + Thread.currentThread() + "接受数据" + msg.obj);
            }
        };

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Message message = new Message();
                    message.obj = UUID.randomUUID().toString();
                    handler.sendMessage(message);
                    System.out.println("线程" + Thread.currentThread() + "发送数据" + message.obj);
                }

            }
        }.start();

        Looper.loop();
    }


}
