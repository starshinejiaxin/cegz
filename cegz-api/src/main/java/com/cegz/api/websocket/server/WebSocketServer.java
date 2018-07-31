package com.cegz.api.websocket.server;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cegz.api.model.Device;
import com.cegz.api.mongo.MongoDB;
import com.cegz.api.service.DeviceService;
import com.cegz.api.util.StringUtil;

/**
 * websocket服务 
 * @author lijiaxin
 * @date 2018年7月26日
 */
@ServerEndpoint(value = "/websocket/{imei}")
@Component
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
    
    /**
     * mongodb数据操作
     */
    private static MongoDB mongoDb;
    
    private static DeviceService deviceService;
    
    @Autowired
	public void setMongoDb(MongoDB mongoDb) {
    	WebSocketServer.mongoDb = mongoDb;
	}
    
    @Autowired
    public void setDeviceService(DeviceService deviceService) {
		WebSocketServer.deviceService = deviceService;
	}

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
        webSocketMap.put(imei, this);  
        // 设备设置
        Device device = deviceService.getDeviceByImei(imei);
        if (device == null) {
        	device = new Device();
        	device.setImei(imei);
        	device.setCreateTime(new Date());
        }else {
        	device.setStatus(0);
        	device.setUpdateTime(new Date());
        }
        addOnlineCount();          
        System.out.println("有新连接加入！当前在线设备数为" + getOnlineCount());
        try {
        	 sendMessage("{head:'hello', body:''}");
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
    	webSocketMap.remove(this.imei); 
    	Device device = deviceService.getDeviceByImei(imei);
        if (device == null) {
        	device = new Device();
        	device.setStatus(1);
        	device.setImei(imei);
        	device.setCreateTime(new Date());
        }else {
        	device.setStatus(1);
        	device.setUpdateTime(new Date());
        }
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
    	// 数据存储
    	if (!StringUtil.isEmpty(message)) {
    		JSONObject jsonObject = JSONObject.parseObject(message);
    		if (!jsonObject.isEmpty()) {
    			if (!"GPS".equals(jsonObject.getString("head"))) {
    				return;
    			}
    			jsonObject = jsonObject.getJSONObject("body"); 
    			Map<String, Object> map = new HashMap<>(16);
    			// 设备号
    			map.put("imei", jsonObject.getString("imei"));
    			// 高德经度
    			map.put("lng", jsonObject.getString("lng"));
    			// 高德纬度
    			map.put("lat", jsonObject.getString("lat"));
    			// 上传时间
    			map.put("time", jsonObject.getString("time"));
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    			// 服务器时间
    			map.put("create_time", sdf.format(new Date()));
    			mongoDb.dataInsert("gps", map);
    		}
    	}
    	 	
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
