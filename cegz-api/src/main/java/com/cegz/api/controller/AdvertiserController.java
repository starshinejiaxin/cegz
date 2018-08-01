package com.cegz.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Advertisement;
import com.cegz.api.model.AdvertisementType;
import com.cegz.api.model.Advertiser;
import com.cegz.api.model.Order;
import com.cegz.api.model.Price;
import com.cegz.api.model.Users;
import com.cegz.api.model.Wallet;
import com.cegz.api.model.view.AdvertisementView;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.AdvertiserService;
import com.cegz.api.service.PriceService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 广告方控制器
 * @author lijiaxin
 * @date 2018年7月24日
 */
@RestController
@RequestMapping("advertisement")
public class AdvertiserController {
	
	@Autowired
	private ServerAck serverAck;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AdvertiserService advertiserService;
	
	@Autowired
	private PriceService priceService;
	
	/**
	 * 图片根地址
	 */
	@Value("${server.image-url}")
	private String baseImageUrl;
	
	/**
	 * 服务url
	 */
	@Value("${server.url}")
	private String serverUrl;
	
	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;
	
	/**
	 * 广告方信息录入
	 * @param name
	 * @param phone
	 * @param email
	 * @param address
	 * @param addressDetail
	 * @param city
	 * @param company
	 * @param businessLicense
	 * @param businessFile
	 * @param token
	 * @param version
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月24日
	 */
	@PostMapping("regist")
	public ResultData insertAdvertiser(String businessFile,String name,
			String phone,
			String email,
			String address,
			String addressDetail,
			String city,
			String company,
			String businessLicense,
			String token,
			String version) {
		if (StringUtil.isEmpty(name)) {
			return serverAck.getParamError().setMessage("姓名不能为空");
		}
		if (StringUtil.isEmpty(phone)) {
			return serverAck.getParamError().setMessage("手机号不能为空");
		}
		if (!StringUtil.isMobile(phone)) {
			return serverAck.getParamError().setMessage("手机号格式有误");
		}
		if (StringUtil.isEmpty(address)) {
			return serverAck.getParamError().setMessage("地址不能为空");
		}
		if (StringUtil.isEmpty(addressDetail)) {
			return serverAck.getParamError().setMessage("详细地址不能为空");
		}
		if (StringUtil.isEmpty(city)) {
			return serverAck.getParamError().setMessage("城市名不能为空");
		}
		if (StringUtil.isEmpty(company)) {
			return serverAck.getParamError().setMessage("公司名称不能为空");
		}
		if (StringUtil.isEmpty(businessLicense)) {
			return serverAck.getParamError().setMessage("营业执照编号不能为空");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
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
			// 图片保存
//			String filePath = ImageUtil.getAdvertiserDir();
//			String fileName = ImageUtil.saveImg(businessFile, filePath);
//			String imageUrl = serverUrl + Constant.ADVERTISER_IMG_DRI + "/" + fileName;
			String imageUrl = baseImageUrl + businessFile;
			// 设置广告方信息
			Advertiser advertiser = new Advertiser();
			advertiser.setAddress(address);
			advertiser.setAddressDetail(addressDetail);
			advertiser.setName(name);
			advertiser.setPhone(phone);
			advertiser.setCity(city);
			advertiser.setCompany(company);
			advertiser.setBusinessLicense(businessLicense);
			//advertiser.setEmail(email);
			advertiser.setCreateTime(new Date());
			advertiser.setCreateUserId(users);
			advertiser.setPictureUrl(imageUrl);
			// 处理
			int ret = advertiserService.save(advertiser);
			if (ret == 0) {
				return  serverAck.getFailure();
			}
			return serverAck.getSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
	
	@PostMapping("publish")
	public ResultData insertAdvertiser(String titles, String titleImages, String contentImages, String days ,String types,
			String contents, String pricesIds, String numbers, String token, String version) {
		if (StringUtil.isEmpty(titles)) {
			return serverAck.getParamError().setMessage("标题不能为空");
		}
		if (StringUtil.isEmpty(titleImages)) {
			return serverAck.getParamError().setMessage("标题图片不能为空");
		}
		if (StringUtil.isEmpty(contentImages)) {
			return serverAck.getParamError().setMessage("内容图片不能为空");
		}
		if (StringUtil.isEmpty(days)) {
			return serverAck.getParamError().setMessage("时间不能为空");
		}
		if (StringUtil.isEmpty(types)) {
			return serverAck.getParamError().setMessage("车辆类型不能为空");
		}
		if (StringUtil.isEmpty(contents)) {
			return serverAck.getParamError().setMessage("内容不能为空");
		}
		if (StringUtil.isEmpty(pricesIds)) {
			return serverAck.getParamError().setMessage("套餐不能为空");
		}
		if (StringUtil.isEmpty(numbers)) {
			return serverAck.getParamError().setMessage("投放汽车数不能为空");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
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
			String split = ":";
			String [] priceIdArray = pricesIds.split(split);
			String [] numbersArray = numbers.split(split);
			String [] titleArray = titles.split(split);
			String [] titleImagesArray = titleImages.split(split);
			String [] contentArray = contents.split(split);
			String [] contentImagesArray = contentImages.split(split);
			String [] dayArray = days.split(split);
			String [] typeArray = types.split(split);
			// 获取套餐
			double sumMoney = 0;
			List<Advertisement> listAdver = new ArrayList<>();
			List<Order> listOrder = new ArrayList<>();
			
			for (int i = 0; i < priceIdArray.length; i++) {
				if (StringUtil.isEmpty(priceIdArray[i])) {
					continue;
				}
				Advertisement adver = new Advertisement();
				Optional<Price> priceOpt = priceService.getPriceById(Long.parseLong(priceIdArray[i]));
				if (!priceOpt.isPresent()) {
					return serverAck.getParamError().setMessage("套餐错误");
				}
				Price price = priceOpt.get();
				adver.setTitle(titleArray[i]);
				// 获取广告类型  图片广告默认ID为1 文字广告ID默认为2
				if (!StringUtil.isEmpty(titleImagesArray[i])) {
					Optional<AdvertisementType> adverTypeOpt = advertiserService.getTypeById((long)1);
					if (!adverTypeOpt.isPresent()) {
						return serverAck.getServerError();
					}
					AdvertisementType adverType = adverTypeOpt.get();
					adver.setAdvertisementType(adverType);
				} else {
					Optional<AdvertisementType> adverTypeOpt = advertiserService.getTypeById((long)2);
					if (!adverTypeOpt.isPresent()) {
						return serverAck.getServerError();
					}
					AdvertisementType adverType = adverTypeOpt.get();
					adver.setAdvertisementType(adverType);
				}
				adver.setContent(contentArray[i]);
				adver.setTitlePicUrl(titleImagesArray[i]);
				adver.setContentPicUrl(contentImagesArray[i]);
				adver.setCreateUserId(users);
				adver.setCreateTime(new Date());
				// 订单
				Order order = new Order();
				double money = price.getPrice();
				// 车辆数
				int number = Integer.parseInt(numbersArray[i]);
				// 投放天数
				int day = Integer.parseInt(dayArray[i]);
				// 屏幕数
				int totalAdverNumber = 2 * number;
				double totalMoney = money * day * number;
				order.setAdvertisement(adver);
				order.setDays(day);
				order.setPrice(price);
				order.setOrderNo(TokenUtil.getUUID());
				order.setCarType(Integer.parseInt(typeArray[i]));
				order.setTotalMoney(totalMoney);
				order.setRealMoney(totalMoney);
				order.setCreateUserId(users);
				order.setTotalAdvertisement(totalAdverNumber);
				order.setCreateTime(new Date());
				// 计算总金额
				sumMoney += totalMoney;
				listAdver.add(adver);
				listOrder.add(order);
				
			}
			Wallet wallet = users.getWallet();
			if (wallet == null) {
				wallet = new Wallet();
				wallet.setCreateTime(new Date());
				wallet.setCreateUserId(users);
				wallet.setWalletNo(TokenUtil.getUUID());
			}
			
			// 设置金额
			double realMoney = wallet.getMoney() - sumMoney;
			wallet.setMoney(realMoney);
			// 数据存储
			int ret = advertiserService.publishAdver(listAdver, listOrder, wallet);
			if (ret == 0) {
				return  serverAck.getFailure();
			}
			return serverAck.getSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
	/**
	 * 获取广告列表
	 * @param token
	 * @param version
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月26日
	 */
	@PostMapping("listAdvertisement")
	public ResultData listAdvertisement(String token, String version) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
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
			// 订单信息
			List<Order> listOrder = users.getListOrder();
			if (listOrder == null || listOrder.size() == 0) {
				return serverAck.getEmptyData();
			}
			List<AdvertisementView> resultList = new ArrayList<>();
			for (int i = 0; i < listOrder.size(); i++) {
				AdvertisementView view = new AdvertisementView();
				Order order = listOrder.get(i);
				view.setId(order.getAdvertisement().getId());
				view.setTitle(order.getAdvertisement().getTitle());
				view.setTitleImgUrl(order.getAdvertisement().getTitlePicUrl());
				view.setContent(order.getAdvertisement().getContent());
				view.setContentImages(order.getAdvertisement().getContentPicUrl());
				view.setStatus(order.getStatus());
				view.setReason(order.getAdvertisement().getReason());
				view.setCarType(order.getCarType());
				view.setAdverType(order.getAdvertisement().getAdvertisementType().getId());
				resultList.add(view);
						
			}
			return serverAck.getSuccess().setData(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
	
}
