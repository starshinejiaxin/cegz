package com.cegz.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Advertiser;
import com.cegz.api.model.Users;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.AdvertiserService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ImageUtil;
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
	@PutMapping("regist")
	public ResultData insertAdvertiser(@RequestParam("businessFile") MultipartFile businessFile,String name,
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
			String filePath = ImageUtil.getAdvertiserDir();
			String fileName = ImageUtil.saveImg(businessFile, filePath);
			String imageUrl = serverUrl + Constant.ADVERTISER_IMG_DRI + "/" + fileName;
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
}
