package com.li.handler;

public class Message {

    /**
     * //将消息和handler进行绑定，在Handler里面 sendMessage方法中，将Message和Handler进行绑定。
     */
    public Handler target;

    public int what;

    public Object obj;
}
