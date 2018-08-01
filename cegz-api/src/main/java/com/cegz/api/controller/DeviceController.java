package com.cegz.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Device;
import com.cegz.api.model.DrivingRegistration;
import com.cegz.api.model.Users;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.DeviceService;
import com.cegz.api.service.DrivingRegistrationService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 设备控制类
 * 
 * @author tenglong
 * @date 2018年8月1日
 */
@RestController
@RequestMapping("device")
public class DeviceController {
	@Autowired
	private ServerAck serverAck;

	@Autowired
	private AccountService accountService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private DrivingRegistrationService drivingRegistrationService;

	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;

	/**
	 * 设备绑定车辆
	 * 
	 * @param imei        设备号
	 * @param number      物联网卡号
	 * @param plateNumber 号牌号码
	 * @param token
	 * @param version
	 * @return
	 */
	@PostMapping("deviceBindingCar")
	public ResultData DeviceBindingCar(String imei, String number, String plateNumber, String token, String version) {
		if (StringUtils.isEmpty(imei)) {
			return serverAck.getEmptyData().setMessage("设备号不能为空");
		}
		if (StringUtils.isEmpty(number)) {
			return serverAck.getEmptyData().setMessage("物联网卡号不能为空");
		}
		if (StringUtils.isEmpty(plateNumber)) {
			return serverAck.getEmptyData().setMessage("车牌号不能为空");
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
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}

			// 通过设备号查询当前设备数据
			Device device = deviceService.getDeviceByImei(imei);
			if (device == null) {
				return serverAck.getParamError().setMessage("设备号无效");
			}
			// 如果此设备上已绑定车辆，提示不允许继续绑定
			if (device.getDrivingRegistration() != null) {
				return serverAck.getFailure().setMessage("此设备已绑定车辆，不允许重复绑定");
			}

			plateNumber = plateNumber.toUpperCase();// 处理车牌号，大小写转换
			// 通过车牌号查询行驶证数据
			DrivingRegistration drivingRegistration = drivingRegistrationService
					.getDrivingRegistrationByPlateNumber(plateNumber);
			if (drivingRegistration == null) {
				return serverAck.getParamError().setMessage("行驶证无效");
			}
			device.setDrivingRegistration(drivingRegistration);
			device.setNumber(number);
			deviceService.save(device);
			return serverAck.getSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
