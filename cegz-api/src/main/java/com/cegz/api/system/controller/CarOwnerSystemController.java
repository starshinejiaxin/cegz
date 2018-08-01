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
import com.cegz.api.model.Contacts;
import com.cegz.api.model.DrivingRegistration;
import com.cegz.api.model.Users;
import com.cegz.api.model.view.ContactView;
import com.cegz.api.model.view.DrivingRegistrationView;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.ContactsService;
import com.cegz.api.system.service.ContactsSystemService;
import com.cegz.api.system.service.DrivingRegistrationSystemService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 车主后台服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
@RestController
@RequestMapping("/system/carOwner")
public class CarOwnerSystemController {
	@Autowired
	private ServerAck serverAck;

	@Autowired
	private ContactsService contactsService;

	@Autowired
	private ContactsSystemService contactsSystemService;

	@Autowired
	private DrivingRegistrationSystemService drivingRegistrationSystemService;

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
	 * 获取车主待审核列表
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
			// 车主审核数据列表
			List<Contacts> contactsExamines = contactsSystemService.listContactsExamine();
			return serverAck.getSuccess().setData(contactsExamines);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 通过id获取单条车主及车辆信息审核数据
	 * 
	 * @param id
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
			// 车主信息审核数据
			Optional<Contacts> contactsOpt = contactsService.getContactById(id);
			if (!contactsOpt.isPresent()) {
				return serverAck.getParamError().setMessage("车主ID有误");
			}
			Contacts contacts = contactsOpt.get();
			ContactView view = new ContactView();
			view.setId(contacts.getId());
			view.setName(contacts.getName());
			view.setPhone(contacts.getPhone());
			if (contacts.getDrivingLicenseId() != null) {// 驾驶证
				view.setDriveLicenseImageUrl(contacts.getDrivingLicenseId().getPictureUrl());
			}
			if (contacts.getIdcardId() != null) {// 身份证
				view.setFirstImageUrl(contacts.getIdcardId().getPictureUrl());
			}

			// 车辆信息审核数据列表
			List<DrivingRegistrationView> carList = new ArrayList<>();
			List<DrivingRegistration> listDr = contacts.getListDrivingRegistration();
			if (listDr != null && listDr.size() > 0) {
				for (DrivingRegistration dr : listDr) {
					DrivingRegistrationView drView = new DrivingRegistrationView();
					drView.setId(dr.getId());
					drView.setCompany(dr.getSponsor().getCompany());
					drView.setPlateNumber(dr.getPlateNumber());
					drView.setModel(dr.getModel());
					drView.setCarBirthday(dr.getCarBirthday());
					drView.setUseCharacter(dr.getUseCharacter());
					drView.setPictureUrl(dr.getPictureUrl());
					drView.setStatus(dr.getStatus());
					drView.setReason(dr.getReason());
					carList.add(drView);
				}
			}

			Map<String, Object> map = new HashMap<>();
			map.put("view", view);
			map.put("carList", carList);

			return serverAck.getSuccess().setData(map);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 车主信息状态审核
	 * 
	 * @param id
	 * @param token
	 * @param reason
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	@PostMapping("carOwnerStatusExamine")
	public ResultData carOwnerStatusExamine(Long id, Integer status, String reason, String token, String version) {
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
			// 获取车主信息
			Optional<Contacts> contactsOpt = contactsService.getContactById(id);
			if (!contactsOpt.isPresent()) {
				return serverAck.getParamError().setMessage("车主ID有误");
			}
			Contacts contacts = contactsOpt.get();

			contacts.setStatus(status.byteValue());
			contacts.setReason(status.byteValue() == 1 ? null : reason);// 如果状态为1，原因置为null
			contacts.setUpdateTime(new Date());
			int ret = contactsService.insertData(contacts);
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
	 * 车辆信息状态审核
	 * 
	 * @param id
	 * @param token
	 * @param reason
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	@PostMapping("carsStatusExamine")
	public ResultData carsStatusExamine(Long id, Integer status, String reason, String token, String version) {
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
			// 获取车辆信息
			Optional<DrivingRegistration> drOpt = drivingRegistrationSystemService.getDrivingRegistrationById(id);
			if (!drOpt.isPresent()) {
				return serverAck.getParamError().setMessage("行驶证ID有误");
			}
			DrivingRegistration drivingRegistration = drOpt.get();
			drivingRegistration.setStatus(status.byteValue());
			drivingRegistration.setReason(status.byteValue() == 1 ? null : reason);// 如果状态为1，原因置为null
			drivingRegistration.setUpdateTime(new Date());
			int ret = drivingRegistrationSystemService.save(drivingRegistration);
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
