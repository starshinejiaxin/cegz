package com.cegz.api.controller;

import java.text.SimpleDateFormat;
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
import com.cegz.api.model.Contacts;
import com.cegz.api.model.DrivingLicense;
import com.cegz.api.model.DrivingRegistration;
import com.cegz.api.model.IdCard;
import com.cegz.api.model.Sponsor;
import com.cegz.api.model.Users;
import com.cegz.api.model.view.CarView;
import com.cegz.api.model.view.ContactView;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.ContactsService;
import com.cegz.api.service.SponsorService;
import com.cegz.api.util.Constant;
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
	
	@Autowired
	private SponsorService sponsorService;
	
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
	
	@PostMapping("regist")
	public ResultData insertContacts(String firstCardFile,
			String secondCardFile,
			String driveLicenseFile, String name, String phone, String token, String version) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
		}
		if (StringUtil.isEmpty(name)) {
			return serverAck.getParamError().setMessage("联系人名称不能为空");
		}
		if (StringUtil.isEmpty(phone)) {
			return serverAck.getParamError().setMessage("联系人电话不能为空");
		}
		if (StringUtil.isEmpty(firstCardFile) || StringUtil.isEmpty(secondCardFile)) {
			return serverAck.getParamError().setMessage("身份证照片不能为空");
					
		}
		if(StringUtil.isEmpty(driveLicenseFile)) {
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
			Contacts vaildContacts = users.getContact();
			if (vaildContacts != null && vaildContacts.getId() != null) {
				return serverAck.getFailure().setMessage("车主已经存在");
			}
			// 图片保存
//			String cardFilePath = ImageUtil.getIdCardImgDir();
//			String cardFileName = ImageUtil.saveImg(multipartCardFile, cardFilePath);
//			String secondCardFileName = ImageUtil.saveImg(multipartSecondCardFile, cardFilePath);
//			String licenseFilePath = ImageUtil.getDriveLicenseImgDir();
//			String licenseFileName = ImageUtil.saveImg(multipartLicenseFile, licenseFilePath);
			// 获取图片地址
//			String cardUrl = serverUrl + Constant.CARD_IMG_DRI + "/" + cardFileName;
//			String secondCardUrl = serverUrl + Constant.CARD_IMG_DRI + "/" + secondCardFileName;
//			String licenseUrl = serverUrl + Constant.DRIVE_LICENSE_IMG_DRI + "/" + licenseFileName;
			String cardUrl = baseImageUrl + firstCardFile;
			String secondCardUrl = baseImageUrl + secondCardFile;
			String licenseUrl = baseImageUrl + driveLicenseFile;
			IdCard cardInfo = new IdCard();
			cardInfo.setPictureUrl(cardUrl + "," + secondCardUrl);
			cardInfo.setCreateTime(new Date());
			cardInfo.setCreateUserId(users);
			// 设置驾驶证信息
			DrivingLicense licenseInfo = new DrivingLicense();
			licenseInfo.setPictureUrl(licenseUrl);
			licenseInfo.setCreateTime(new Date());
			licenseInfo.setCreateUserId(users);
			// 设置联系人信息
			Contacts contactsInfo = new Contacts();
			contactsInfo.setName(name);
			contactsInfo.setPhone(phone);
			contactsInfo.setCreateTime(new Date());
			contactsInfo.setCreateUserId(users);
			contactsInfo.setDrivingLicenseId(licenseInfo);
			contactsInfo.setIdcardId(cardInfo);
			int ret = contactsService.insertData(contactsInfo);
			if (ret <= 0) {
				return serverAck.getServerError();
			}
			return serverAck.getSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
	
	/**
	 * 录入车辆信息
	 * @param multipartFile 行驶证图片
	 * @param carNumber 车牌号
	 * @param birthDate 车辆出厂日期
	 * @param sponsorId 保荐方ID
	 * @param contactId 联系人ID
	 * @param carType 车辆品牌
	 * @param token 用户token
	 * @param version 版本
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月23日
	 */
	@PostMapping("drivingRegistration")
	public ResultData insertDriveRegistration(String partFile, String carNumber, String birthDate, Long sponsorId, Long contactId, String carType, String token, String version) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(carNumber)) {
			return serverAck.getParamError().setMessage("车牌号不能为空");
		}
		if (StringUtil.isEmpty(carType)) {
			return serverAck.getParamError().setMessage("车辆品牌类型不能为空");
		}
		if (StringUtil.isEmpty(birthDate)) {
			return serverAck.getParamError().setMessage("出厂日期不能为空");
		}
		if (sponsorId == null) {
			return serverAck.getParamError().setMessage("保荐方id不能为空");
		}
		if (StringUtil.isEmpty(partFile)) {
			return serverAck.getParamError().setMessage("行驶证照片不能为空");
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
			// 获取保荐方信息
			Optional<Sponsor> sponsorOpt = sponsorService.getSponSorById(sponsorId);
			if (!sponsorOpt.isPresent()) {
				return serverAck.getParamError().setMessage("保荐方ID有误");
			}
			Sponsor sponsor = sponsorOpt.get();
			// 获取车主信息
			Contacts contact = users.getContact();
			if (contact == null || contact.getId() == null) {
				return serverAck.getParamError().setMessage("车主ID有误");
			}
			
			// 图片保存
//			String driveRegisrationFilePath = ImageUtil.getDriveRegistrationImgDir();
//			String fileName = ImageUtil.saveImg(multipartFile, driveRegisrationFilePath);
			// 获取图片地址
			String pictureUrl = baseImageUrl + partFile;
			// 设置行驶证信息
			DrivingRegistration dr = new DrivingRegistration();
			dr.setPlateNumber(carNumber);
			dr.setModel(carType);
			dr.setPictureUrl(pictureUrl);
			dr.setCreateUserId(users);
			dr.setCreateTime(new Date());
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyyMMdd");
			Date birthday = sdf.parse(birthDate);
			dr.setCarBirthday(birthday);
			dr.setSponsor(sponsor);
			dr.setContact(contact);
			// 数据处理
			int ret = contactsService.insertContractDriveRegist(dr);
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
	 * 获取车主信息
	 * @param token
	 * @param version
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月26日
	 */
	@PostMapping("getContact")
	public ResultData getContactsByUser(String token, String version) {
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
			// 车主信息
			Contacts contact = users.getContact();
			if (contact == null || contact.getId() == null) {
				return serverAck.getEmptyData();
			}
			ContactView view = new ContactView();
			view.setName(contact.getName());
			view.setPhone(contact.getPhone());
			view.setDriveLicenseImageUrl(contact.getDrivingLicenseId().getPictureUrl());
			String [] pictures = contact.getIdcardId().getPictureUrl().split(",");
			if (pictures.length == 2) {
				view.setFirstImageUrl(pictures[0]);
				view.setSecondImageUrl(pictures[1]);
			}
			// 审核状态 0 审核中，1 成功，2失败
			int status = 0;
			int licenseStatus = contact.getDrivingLicenseId().getStatus();
			int cardStatus = contact.getIdcardId().getStatus();
			if (licenseStatus == 1 && cardStatus == 1) {
				status = 1;
			}
			if (cardStatus == 2 ) {
				status = 2;
				view.setReason(contact.getIdcardId().getReason());
			}
			if (licenseStatus == 2) {
				status = 2;
				view.setReason(view.getReason() + contact.getDrivingLicenseId().getReason());
			}
			view.setStatus(status);
			return serverAck.getSuccess().setData(view);
			
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
		
	}
	/**
	 * 获取车辆列表
	 * @param token
	 * @param version
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月26日
	 */
	@PostMapping("listCarNumber")
	public ResultData getCarNumberByUser(String token, String version) {
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
			// 车主信息
			Contacts contact = users.getContact();
			if (contact == null || contact.getId() == null) {
				return serverAck.getEmptyData();
			}
			List<DrivingRegistration> listDr = contact.getListDrivingRegistration();
			if (listDr == null || listDr.size() == 0) {
				return serverAck.getEmptyData();
			}
			List<CarView> resultList = new ArrayList<>();
			for (int i = 0; i < listDr.size(); i++) {
				CarView view = new CarView();
				DrivingRegistration dr = listDr.get(i);
				view.setId(dr.getId());
				view.setCarNumber(dr.getPlateNumber());
				view.setStatus(dr.getStatus());
				view.setCarType(dr.getSponsor().getType());
				resultList.add(view);
			}
			return serverAck.getSuccess().setData(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
	
	
	
}
