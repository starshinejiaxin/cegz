package com.cegz.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Sponsor;
import com.cegz.api.model.Users;
import com.cegz.api.model.view.SponsorView;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.SponsorService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 保荐方服务
 * @author lijiaxin
 * @date 2018年7月24日
 */
@RestController
@RequestMapping("sponsor")
public class SponsorController {
	@Autowired
	private ServerAck serverAck;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private AccountService accountService;
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
	 * 图片根地址
	 */
	@Value("${server.image-url}")
	private String baseImageUrl;
	
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
	public ResultData insertsponsor(String businessFile,String name,
			String phone,
			String email,
			String address,
			String addressDetail,
			String province,
			String company,
			String businessLicense,
			String token,
			String type,
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
		if (StringUtil.isEmpty(province)) {
			return serverAck.getParamError().setMessage("省份名不能为空");
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
		if (StringUtil.isEmpty(type)) {
			return serverAck.getParamError().setMessage("类型不能为空");
		}
		if (!"12".contains(type)) {
			return serverAck.getParamError().setMessage("类型有吴");
		}
		if (StringUtil.isEmpty(businessFile)) {
			return serverAck.getParamError().setMessage("营业执照图片不能为空");
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
//			String filePath = ImageUtil.getSponsorDir();
//			String fileName = ImageUtil.saveImg(businessFile, filePath);
//			String imageUrl = serverUrl + Constant.SPONSOR_IMG_DRI + "/" + fileName;
			String imageUrl = baseImageUrl + businessFile;
			// 设置广告方信息
			Sponsor sponsor = new Sponsor();
			sponsor.setAddress(address);
			sponsor.setAddressDetail(addressDetail);
			sponsor.setName(name);
			sponsor.setPhone(phone);
			sponsor.setProvince(province);
			sponsor.setCompany(company);
			sponsor.setBusinessLicense(businessLicense);
			sponsor.setEmail(email);
			sponsor.setCreateTime(new Date());
			sponsor.setCreateUserId(users);
			sponsor.setPictureUrl(imageUrl);
			sponsor.setType(Integer.parseInt(type));
			// 处理
			int ret = sponsorService.save(sponsor);
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
	 * 获取保荐方信息
	 * @param token
	 * @param version
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月26日
	 * 
	 */
	@PostMapping("getSponsor")
	public ResultData getSponsorByUser(String token, String version) {
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
			Sponsor sponsor = users.getSponsor();
			if (sponsor == null) {
				return serverAck.getEmptyData();
			}
			SponsorView view = new SponsorView();
			view.setAddress(sponsor.getAddress());
			view.setAddressDetail(sponsor.getAddressDetail());
			view.setBusinessNumber(sponsor.getBusinessLicense());
			view.setCompany(sponsor.getCompany());
			view.setEmail(sponsor.getEmail());
			view.setId(sponsor.getId());
			view.setLicenseImgUrl(sponsor.getPictureUrl());
			view.setName(sponsor.getName());
			view.setAddress(sponsor.getPhone());
			view.setProvince(sponsor.getProvince());
			view.setStatus(sponsor.getStatus());
			view.setReason(sponsor.getReason());
			view.setType(sponsor.getType());
			return serverAck.getSuccess().setData(view);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
	/**
	 * 获取所有保荐方
	 * @param token
	 * @param type
	 * @param version
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月27日
	 */
	@PostMapping("listSponsor")
	public ResultData listSponsor(String token, String version) {
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
			List<Sponsor> list = sponsorService.listSponsor();
			if (list == null || list.size() == 0) {
				return serverAck.getEmptyData();
			}
			List<SponsorView> resultList = new ArrayList<>();
			int length = list.size();
			for (int i = 0; i < length; i++) {
				Sponsor sponsor = list.get(i);
				SponsorView view = new SponsorView();
				view.setAddress(sponsor.getAddress());
				view.setAddressDetail(sponsor.getAddressDetail());
				view.setBusinessNumber(sponsor.getBusinessLicense());
				view.setCompany(sponsor.getCompany());
				view.setEmail(sponsor.getEmail());
				view.setId(sponsor.getId());
				view.setLicenseImgUrl(sponsor.getPictureUrl());
				view.setName(sponsor.getName());
				view.setAddress(sponsor.getPhone());
				view.setProvince(sponsor.getProvince());
				view.setStatus(sponsor.getStatus());
				view.setReason(sponsor.getReason());
				view.setType(sponsor.getType());
				resultList.add(view);
			}
			return serverAck.getSuccess().setData(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
