package com.cegz.api.service;

import java.util.List;

import com.cegz.api.model.Device;

/**
 * 设备服务
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
public interface DeviceService {
	/**
	 * 获取设备列表
	 * @param size
	 * @return
	 * @author Administrator
	 * @date 2018年7月30日
	 */
	List<Device> listDevice(Long size);
	
	/**
	 * 获取设备通过设备号
	 * @param imei
	 * @return
	 * @author Administrator
	 * @date 2018年7月31日
	 */
	Device getDeviceByImei(String imei);
	
	/**
	 * 保存或修改设备
	 * @param device
	 * @return
	 * @author Administrator
	 * @date 2018年7月31日
	 */
	int save(Device device);
}
