package com.cegz.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.DeviceRepository;
import com.cegz.api.model.Device;
import com.cegz.api.service.DeviceService;
/**
 * 
 * 设备服务
 * @author lijiaxin
 * @date 2018年7月30日
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;
	@Override
	public List<Device> listDevice(Long size) {
		return deviceRepository.listDeviceByLimit(size);
	}

}
