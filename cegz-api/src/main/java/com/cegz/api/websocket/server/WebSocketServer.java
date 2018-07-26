package com.cegz.api.websocket.server;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;
@Component
@ServerEndpoint(value = "/websocket/{imei}")
/**
 * websocket服务
 *
 * @author lijiaxin
 * @date 2018年7月26日
 */
public class WebSocketServer {
	
	/**
	 * 设备号
	 */
	private String imei;
	/**
	 *  应该把它设计成线程安全的。
	 */
    public static int onlineCount = 0;
    /**
     *  concurrent包的线程安全hashMap，用来存放每个客户端对应的MyWebSocket对象。
     */
    public static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
 
    /**
     *  与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    
    
    public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * 成功建立连接
	 * @param session
	 * @param imei
	 * @author lijiaxin
	 * @date 2018年7月26日
	 */
    @OnOpen
    public void onOpen(Session session, @PathParam("imei") String imei){
        this.session = session;
        this.imei = imei;
        webSocketMap.put(imei, this);  //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线设备数为" + getOnlineCount());
        try {
        	 sendMessage("连接成功");
        	 System.out.println("设备号:" + imei);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 关闭连接
     * 
     * @author lijiaxin
     * @date 2018年7月26日
     */
    @OnClose
    public void onClose() {
    	webSocketMap.remove(this.imei);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线设备数为" + getOnlineCount());
    }
 
    /**
     * 接受客户端消息
     * @param message
     * @param session
     * @author lijiaxin
     * @date 2018年7月26日
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	System.out.println("来自客户端的消息:" + message);
    }
 
	/**
	 * 出现错误
	 * @param session
	 * @param error
	 * @author lijiaxin
	 * @date 2018年7月26日
	 */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
 
    /**
     * 发送消息
     * @param message
     * @throws IOException
     * @author lijiaxin
     * @date 2018年7月26日
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
 
 
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
 
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
