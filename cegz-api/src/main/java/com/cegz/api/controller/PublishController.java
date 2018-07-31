package com.cegz.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Advertisement;
import com.cegz.api.model.Device;
import com.cegz.api.model.Order;
import com.cegz.api.model.PublishAdverRecord;
import com.cegz.api.model.Users;
import com.cegz.api.model.view.ListPublishAdverView;
import com.cegz.api.model.view.PublishAdverView;
import com.cegz.api.model.view.SocketMessage;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.AdvertiserService;
import com.cegz.api.service.DeviceService;
import com.cegz.api.service.PublishAdverService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;
import com.cegz.api.websocket.server.WebSocketServer;

/**
 * 发布广告
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
@RestController
@RequestMapping("publish")
public class PublishController {
	@Autowired
	private ServerAck serverAck;
	
	@Autowired
	private AdvertiserService adverService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private PublishAdverService publishAdverService;
	
	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;
	
	/**
	 * 通过订单发布广告
	 * @param orderId
	 * @param token
	 * @param version
	 * @return
	 * @author Administrator
	 * @date 2018年7月30日
	 */
	@PostMapping("advertisementByOrder")
	public ResultData publish(Long orderId, String token, String version) {
		if (orderId == null) {
			return serverAck.getEmptyData().setMessage("订单ID不能为空");
		}
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String [] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			Optional<Order> orderOptional = adverService.getOrderById(orderId);
			if (!orderOptional.isPresent()) {
				return serverAck.getParamError().setMessage("订单无效");
			}
			Order order = orderOptional.get();
			int status = order.getStatus();
			if (status != 1) {
				return serverAck.getParamError().setMessage("订单无效");
			}
		
			Advertisement advertisement = order.getAdvertisement();
			if (advertisement == null || advertisement.getId() == null) {
				return serverAck.getParamError().setMessage("订单无效");
			}
			// 投放天数
			int days = order.getDays();
			if (days <= 0) {
				return serverAck.getParamError().setMessage("投放长度不能小于等于0");
			}
			
			long length = order.getTotalAdvertisement();
			// 获取设备信息
			List<Device> deviceList = deviceService.listDevice(length);
			if (deviceList == null || deviceList.size() == 0) {
				return serverAck.getFailure().setMessage("当前可用设备不足");
			}
			if (length != deviceList.size()) {
				return serverAck.getFailure().setMessage("当前可用设备不足");
			}
			// 时间处理
			Calendar calendar = Calendar.getInstance();
			Date date = new Date();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, days);
			Long timestamp = calendar.getTimeInMillis();
			Date endDate = calendar.getTime();
			// 消息设置
			PublishAdverView adverView = new PublishAdverView();
			adverView.setTitle(advertisement.getTitle());
			adverView.setTitileImg(advertisement.getTitlePicUrl());
			adverView.setContent(advertisement.getContent());
			adverView.setContentImg(advertisement.getContentPicUrl().split(","));
			adverView.setType(advertisement.getAdvertisementType().getId());
			adverView.setTimestamp(timestamp);
			
			
			// 发布广告
			ConcurrentHashMap<String, WebSocketServer> socketMap = WebSocketServer.webSocketMap;
			for (int i = 0; i < length; i++) {
				// 发布消息
				String imei = deviceList.get(i).getImei();
				WebSocketServer socket = socketMap.get(imei);
				if (socket == null) {
					return serverAck.getFailure().setMessage("存在离线设备");
				}
				
				// 保存记录
				PublishAdverRecord publishAdverRecord = new PublishAdverRecord();
				publishAdverRecord.setAdvertisement(advertisement);
				publishAdverRecord.setCreateTime(date);
				publishAdverRecord.setStartTime(date);
				publishAdverRecord.setEndTime(endDate);
				publishAdverRecord.setCreateUserId(users);
				publishAdverRecord.setDevice(deviceList.get(i));
				publishAdverRecord.setOrder(order);
				publishAdverRecord.setPublishDay(days);
				publishAdverRecord.setCarType(order.getCarType());
				PublishAdverRecord record = publishAdverService.insertPublishRecord(publishAdverRecord);
				
				// 设置socket 广告发布消息
				ListPublishAdverView view = new ListPublishAdverView();
				List<PublishAdverView> listView = new ArrayList<>();
				adverView.setId(record.getId());
				listView.add(adverView);
				view.setList(listView);
				SocketMessage socketMessage = new SocketMessage();
				socketMessage.setHead("advertisement");
				socketMessage.setBody(view);
				// socket 发送
				String message = JSON.toJSONString(socketMessage);
				System.out.println(message);
				socket.sendMessage(message);
				 
			}
			
			return serverAck.getSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
