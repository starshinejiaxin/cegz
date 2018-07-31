package com.cegz.api.controller;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.mongo.MongoDB;
import com.cegz.api.redis.RedisUtil;
import com.cegz.api.websocket.server.WebSocketServer;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	private MongoDB mongoDb;
	@Autowired
	private RedisUtil redisUtil;
	@GetMapping("mongo")
	public String get() {
		//MongoUtil.getMongoReadClient();
//		System.out.println(mongoDb);
//		ConcurrentHashMap<String, WebSocketServer> map = WebSocketServer.webSocketMap;
//		System.out.println(map.size());
		redisUtil.set("lijiaxin", "123");
		return "1";
	}
}
