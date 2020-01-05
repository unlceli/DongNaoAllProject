package com.li.handler;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MessageQueue {
    private static final String TAG = "MessageQueue";
    Message[] messages;
    Lock mLock;
    Condition getCondition;
    Condition addCondition;
    int mCount;
    int mPutIndex;
    int mTakeIndex;

    public MessageQueue() {
        this.messages = new Message[50];
        this.mLock = new ReentrantLock();
        this.getCondition = mLock.newCondition();
        this.addCondition = mLock.newCondition();
    }

    /**
     * 出队
     *
     * @return
     */
    Message next() {
        Message message = null;
        try {
            mLock.lock();
            while (mCount <= 0) {
                System.out.println("空队列，读锁阻塞");
                getCondition.await();
            }
            message = messages[mTakeIndex];
            messages[mTakeIndex] = null;
            mTakeIndex = ++mTakeIndex >= messages.length ? 0 : mTakeIndex;
            mCount--;
            addCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }
        return message;
    }

    /**
     * 进消息队列
     *
     * @param message
     */
    public void enqueueMessage(Message message) {
        try {
            mLock.lock();
            while (mCount >= messages.length) {
                System.out.println("队列已满，写锁阻塞");
                //队列已满
                addCondition.await();
            }
            messages[mPutIndex] = message;
            //防止越界
            mPutIndex = (++mPutIndex >= messages.length) ? 0 : mPutIndex;
            mCount++;
            getCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }
    }
}
