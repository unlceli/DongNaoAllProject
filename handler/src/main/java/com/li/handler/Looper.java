package com.li.handler;

class Looper {
    private static final String TAG = "Looper";
    final MessageQueue mMessageQueue;
    /**
     * 多线程情况下保证变量的隔离
     */
    private static ThreadLocal<Looper> mThreadLocals = new ThreadLocal<>();

    public Looper() {
        mMessageQueue = new MessageQueue();
    }

    /**
     * 初始化Looper
     */
    public static void prepare() {
        if (mThreadLocals.get() != null) {
            //当前线程已维护Looper
            throw new RuntimeException("同一个线程只可以维护一个Looper");
        }
        mThreadLocals.set(new Looper());
        System.out.println("初始化线程Looper");
    }

    /**
     * 获取当前线程维护的Looper对象
     *
     * @return 前线程维护的Looper对象
     */
    public static Looper myLooper() {
        return mThreadLocals.get();
    }

    public static void loop() {
        Looper looper = myLooper();
        Message msg;
        for (; ; ) {
            msg = looper.mMessageQueue.next();
            if (msg == null || msg.target == null) {
                continue;
            }
            System.out.println("分发消息");
            msg.target.dispatchMessage(msg);
        }
    }
}