package com.cegz.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Contacts;
import com.cegz.api.model.DrivingLicense;
import com.cegz.api.model.IdCard;
import com.cegz.api.model.Users;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.ContactsService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ImageUtil;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 联系人控制类
 * @author lijiaxin
 * @date 2018年7月20日
 */
@RestController
@RequestMapping("contacts")
public class ContactsController {
	@Autowired
	private ServerAck serverAck;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ContactsService contactsService;
	
	@PutMapping("regist")
	public ResultData insertContacts(@RequestParam("cardFile") MultipartFile multipartCardFile,
			@RequestParam("driveLicenseFile") MultipartFile multipartLicenseFile, String name, String phone, String token) {
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
		}
		if (StringUtil.isEmpty(name)) {
			return serverAck.getParamError().setMessage("联系人名称不能为空");
		}
		if (StringUtil.isEmpty(phone)) {
			return serverAck.getParamError().setMessage("联系人电话不能为空");
		}
		if (multipartCardFile.isEmpty()) {
			return serverAck.getParamError().setMessage("身份证照片不能为空");
					
		}
		if(multipartLicenseFile.isEmpty()) {
			return serverAck.getParamError().setMessage("驾驶证照片不能为空");
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
			String cardFilePath = ImageUtil.getIdCardImgDir();
			String cardFileName = ImageUtil.saveImg(multipartCardFile, cardFilePath);
			String licenseFilePath = ImageUtil.getDriveLicenseImgDir();
			String licenseFileName = ImageUtil.saveImg(multipartLicenseFile, licenseFilePath);
			// 获取图片地址
			String cardUrl = "http://localhost:8080" + Constant.CARD_IMG_DRI + "/" + cardFileName;
			String licenseUrl = "http://localhost:8080" + Constant.DRIVE_LICENSE_IMG_DRI + "/" + licenseFileName;
			// 设置身份证信息
			IdCard cardInfo = new IdCard();
			cardInfo.setPictureUrl(cardUrl);
			cardInfo.setCreateTime(new Date());
			cardInfo.setCreateUserId(users.getId());
			// 设置驾驶证信息
			DrivingLicense licenseInfo = new DrivingLicense();
			licenseInfo.setPictureUrl(licenseUrl);
			licenseInfo.setCreateTime(new Date());
			licenseInfo.setCreateUserId(users.getId());
			// 设置联系人信息
			Contacts contactsInfo = new Contacts();
			contactsInfo.setName(name);
			contactsInfo.setPhone(phone);
			contactsInfo.setCreateTime(new Date());
			contactsInfo.setCreateUserId(users.getId());
			int ret = contactsService.insertData(contactsInfo, cardInfo, licenseInfo);
			if (ret <= 0) {
				return serverAck.getServerError();
			}
			return serverAck.getSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
