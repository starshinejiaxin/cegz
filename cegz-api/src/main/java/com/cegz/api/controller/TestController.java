package com.cegz.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.model.Advertisement;
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
		List<PublishAdverRecord> list = deviceService.listPublishRecordByDevice((long)9, (byte)0);
		Advertisement advertisement = list.get(0).getAdvertisement();
		// 时间处理
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(list.get(0).getEndTime());   		
		Long timestamp = calendar.getTimeInMillis();
		// 消息设置
		PublishAdverView adverView = new PublishAdverView();
		adverView.setTitle(advertisement.getTitle());
		adverView.setTitileImg(advertisement.getTitlePicUrl());
		adverView.setContent(advertisement.getContent());
		adverView.setContentImg(advertisement.getContentPicUrl().split(","));
		adverView.setType(advertisement.getAdvertisementType().getId());
		adverView.setTimestamp(timestamp);
		// 设置socket 广告发布消息
		ListPublishAdverView view = new ListPublishAdverView();
		List<PublishAdverView> listView = new ArrayList<>();
		adverView.setId(list.get(0).getId());
		listView.add(adverView);
		view.setList(listView);
		//int count = deviceService.countPublishRecordByDevice((long)9);
		return  "";
	}
}
