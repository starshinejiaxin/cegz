package com.cegz.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.model.Advertisement;
import com.cegz.api.model.Device;
import com.cegz.api.model.PublishAdverRecord;
import com.cegz.api.model.view.ListPublishAdverView;
import com.cegz.api.model.view.PublishAdverView;
import com.cegz.api.mongo.MongoDB;
import com.cegz.api.redis.RedisUtil;
import com.cegz.api.service.DeviceService;
import com.cegz.api.websocket.server.WebSocketServer;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	private MongoDB mongoDb;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private DeviceService deviceService;
	@GetMapping("mongo")
	public String get() {
		//MongoUtil.getMongoReadClient();
//		System.out.println(mongoDb);
//		ConcurrentHashMap<String, WebSocketServer> map = WebSocketServer.webSocketMap;
//		System.out.println(map.size());

		// Optional<Advertisement> adver = deviceService.getAdvertisementById((long)34);
		Device device = deviceService.getDeviceByImei("123456");
		device.setUpdateTime(new Date());
		deviceService.save(device);
		//int count = deviceService.countPublishRecordByDevice((long)9);
		return  "";
	}
}
