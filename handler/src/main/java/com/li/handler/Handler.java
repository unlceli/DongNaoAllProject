package com.li.handler;

class Handler {
    private Looper mLooper;
    private MessageQueue mMessageQueue;

    public Handler() {
        this.mLooper = Looper.myLooper();
        if(mLooper==null){
            throw new RuntimeException("当前线程未初始化Looper");
        }
        this.mMessageQueue = mLooper.mMessageQueue;
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(Message message) {
        message.target = this;
        mMessageQueue.enqueueMessage(message);
    }

    /**
     * 分发消息
     * @param message
     */
    public void dispatchMessage(Message message) {
        handleMessage(message);
    }

    public void handleMessage(Message message) {

    }
}