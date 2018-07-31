package com.cegz.api.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.DeviceRepository;
import com.cegz.api.dao.PublishAdverRecordRepository;
import com.cegz.api.model.Device;
import com.cegz.api.model.PublishAdverRecord;
import com.cegz.api.service.DeviceService;
/**
 * 
 * 设备服务
 * @author lijiaxin
 * @date 2018年7月30日
 */
@Service("deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private PublishAdverRecordRepository publishRecordRepository;
	@Override
	public List<Device> listDevice(Long size) {
		return deviceRepository.listDeviceByLimit(size);
	}
	@Override
	public Device getDeviceByImei(String imei) {
		return deviceRepository.getDeviceByImei(imei);
	}
	@Override
	public int save(Device device) {
		deviceRepository.save(device);
		return 1;
	}
	@Override
	public int updateByStatus(int status, Date updateTime, Long id) {
		return deviceRepository.updateByStatus(status, updateTime, id);	 
	}
	@Override
	public int updatePublishStatus(int status, Date updateTime, Long id) {
		return publishRecordRepository.updateByStatus(status, updateTime, id);
	}
	@Override
	public int countPublishRecordByDevice(Long id) {
		return publishRecordRepository.countDataByDevice(id);
	}
	@Override
	public List<PublishAdverRecord> listPublishRecordByDevice(Long id, int isDeleted) {
		return publishRecordRepository.findByDeviceIdAndIsDeleted(id, isDeleted);
	}

}
