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
}
