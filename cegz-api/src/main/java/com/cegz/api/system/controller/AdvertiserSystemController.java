package com.cegz.api.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Advertiser;
import com.cegz.api.model.Order;
import com.cegz.api.model.Users;
import com.cegz.api.model.view.AdvertiserView;
import com.cegz.api.model.view.OrderView;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.AdvertiserService;
import com.cegz.api.system.service.AdvertiserSystemService;
import com.cegz.api.system.service.OrderSystemService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 广告主后台服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
@RestController
@RequestMapping("/system/advertiser")
public class AdvertiserSystemController {
	@Autowired
	private ServerAck serverAck;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AdvertiserSystemService advertiserSystemService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private OrderSystemService orderSystemService;

	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;

	/**
	 * 校验token and version
	 * 
	 * @param token
	 * @param version
	 * @return
	 */
	private ResultData checkTokenAndVersion(String token, String version) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
		}
		return null;
	}

	/**
	 * 获取 广告主待审核列表
	 * 
	 * @param token
	 * @param version
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	@PostMapping("getExamineList")
	public ResultData getExamineList(String token, String version) {
		if (checkTokenAndVersion(token, version) != null) {
			return checkTokenAndVersion(token, version);
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			// 广告主审核数据列表
			List<Advertiser> advertiserExamines = advertiserSystemService.listAdvertiserExamine();
			return serverAck.getSuccess().setData(advertiserExamines);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 通过id获取单条 广告主及广告订单信息审核数据
	 * 
	 * @param id
	 * @param pageSize  分页参数
	 * @param pageCount 分页参数
	 * @param token
	 * @param version
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	@PostMapping("getExamineById")
	public ResultData getExamineById(Long id, String token, String version) {
		if (id == null) {
			return serverAck.getParamError().setMessage("主键不能为空");
		}
		if (checkTokenAndVersion(token, version) != null) {
			return checkTokenAndVersion(token, version);
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			// 广告主信息审核数据
			Optional<Advertiser> advertisersOpt = advertiserService.getAdvertiserById(id);
			if (!advertisersOpt.isPresent()) {
				return serverAck.getParamError().setMessage("广告主ID有误");
			}
			Advertiser advertiser = advertisersOpt.get();
			AdvertiserView view = new AdvertiserView();
			view.setId(advertiser.getId());
			view.setCity(advertiser.getCity());
			view.setCompany(advertiser.getCompany());
			view.setBusinessLicense(advertiser.getBusinessLicense());
			view.setPictureUrl(advertiser.getPictureUrl());
			view.setName(advertiser.getName());
			view.setPhone(advertiser.getPhone());
			view.setEmail(advertiser.getEmail());
			view.setAddress(advertiser.getAddress());
			view.setAddress_detail(advertiser.getAddressDetail());
			// 广告创建者id，用于订单查询
			Long createUserId = advertiser.getCreateUserId().getId();

			List<OrderView> advertisementList = new ArrayList<>();
			if (createUserId != null) {// 未查询出创建人则不去查询订单
				// 广告订单审核数据列表
				List<Order> listOrder = advertiserService.listOrder(createUserId);
				if (listOrder != null && listOrder.size() > 0) {
					for (Order order : listOrder) {
						OrderView orderView = new OrderView();
						orderView.setId(order.getId());
						String carType = "";
						if (order.getCarType() == 1) {
							carType = "网约车";
						} else if (order.getCarType() == 2) {
							carType = "教练车";
						} else {
							carType = "网约车和教练车";
						}
						orderView.setAdvertisementTypeName(
								carType + order.getAdvertisement().getAdvertisementType().getName());
						orderView.setAdvertisementUrl(order.getAdvertisement().getContentPicUrl());
						orderView.setCarSum(order.getTotalAdvertisement() / 2);
						orderView.setRealMoney(order.getRealMoney());
						orderView.setTotalMoney(order.getTotalMoney());
						advertisementList.add(orderView);
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("view", view);
			map.put("advertisementList", advertisementList);

			return serverAck.getSuccess().setData(map);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 广告主信息状态审核
	 * 
	 * @param id
	 * @param token
	 * @param reason
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	@PostMapping("advertiserStatusExamine")
	public ResultData advertiserStatusExamine(Long id, Integer status, String reason, String token, String version) {
		if (id == null) {
			return serverAck.getParamError().setMessage("主键不能为空");
		}
		if (status == null || status == 0) {// 审核状态 0 审核中，1 通过，2 未通过
			return serverAck.getParamError().setMessage("审核状态必填且不为0");
		}
		if (status != null && status != 0 && status == 2 && StringUtil.isEmpty(reason)) {// 审核状态为未通过原因必填
			return serverAck.getParamError().setMessage("审核未通过时原因不能为空");
		}
		if (checkTokenAndVersion(token, version) != null) {
			return checkTokenAndVersion(token, version);
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			// 处理
			// 获取 广告主信息
			Optional<Advertiser> advertisersOpt = advertiserService.getAdvertiserById(id);
			if (!advertisersOpt.isPresent()) {
				return serverAck.getParamError().setMessage("广告主ID有误");
			}
			Advertiser advertiser = advertisersOpt.get();

			advertiser.setStatus(status.byteValue());
			advertiser.setReason(status.byteValue() == 1 ? null : reason);// 如果状态为1，原因置为null
			advertiser.setUpdateTime(new Date());
			int ret = advertiserService.save(advertiser);
			if (ret == 0) {
				return serverAck.getFailure();
			}
			return serverAck.getSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 订单信息状态审核
	 * 
	 * @param id
	 * @param token
	 * @param reason
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	@PostMapping("orderStatusExamine")
	public ResultData orderStatusExamine(Long id, Integer status, String reason, String token, String version) {
		if (id == null) {
			return serverAck.getParamError().setMessage("主键不能为空");
		}
		if (status == null || status == 0) {// 审核状态 0 审核中，1 通过，2 未通过
			return serverAck.getParamError().setMessage("审核状态必填且不为0");
		}
		if (status != null && status != 0 && status == 3 && StringUtil.isEmpty(reason)) {// 审核状态为未通过原因必填
			return serverAck.getParamError().setMessage("审核未通过时原因不能为空");
		}
		if (checkTokenAndVersion(token, version) != null) {
			return checkTokenAndVersion(token, version);
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			// 处理
			// 获取订单信息
			Optional<Order> orderOpt = advertiserService.getOrderById(id);
			if (!orderOpt.isPresent()) {
				return serverAck.getParamError().setMessage("订单ID有误");
			}
			Order order = orderOpt.get();
			if (order.getStatus() != 0) {// 订单已经审核过不允许继续审核，否则会影响增加钱包金额操作
				return serverAck.getParamError().setMessage("订单已审核，请勿重复提交");
			}
			order.setStatus(status.byteValue());
			order.setReason(status.byteValue() == 1 ? null : reason);// 如果状态为1，原因置为null
			order.setUpdateTime(new Date());
			int ret = orderSystemService.save(order);
			if (ret == 0) {
				return serverAck.getFailure();
			}
			return serverAck.getSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
