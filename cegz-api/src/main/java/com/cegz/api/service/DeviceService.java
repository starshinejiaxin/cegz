package com.cegz.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.cegz.api.model.Advertisement;
import com.cegz.api.model.Device;
import com.cegz.api.model.PublishAdverRecord;

/**
 * 设备服务
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
public interface DeviceService {
	/**
	 * 获取设备列表
	 * 
	 * @param size
	 * @return
	 * @author Administrator
	 * @date 2018年7月30日
	 */
	List<Device> listDevice(Long size);

	/**
	 * 获取设备通过设备号
	 * 
	 * @param imei
	 * @return
	 * @author Administrator
	 * @date 2018年7月31日
	 */
	Device getDeviceByImei(String imei);

	/**
	 * 保存或修改设备
	 * 
	 * @param device
	 * @return
	 * @author Administrator
	 * @date 2018年7月31日
	 */
	int save(Device device);

	/**
	 * 修改设备状态
	 * 
	 * @param status
	 * @param updateTime
	 * @param imei
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月31日
	 */
	int updateByStatus(int status, Date updateTime, Long id);

	/**
	 *
	 * @param status
	 * @param updateTime
	 * @param id
	 * @return
	 * @author Administrator
	 * @date 2018年7月31日
	 */
	int updatePublishStatus(int status, Date updateTime, Long id);

	/**
	 * 通过设备号获取有效
	 * 
	 * @return
	 * @author Administrator
	 * @date 2018年7月31日
	 */
	int countPublishRecordByDevice(Long id);

	/**
	 * 获取发布列表
	 * 
	 * @param id
	 * @param isDeleted
	 * @return
	 * @author Administrator
	 * @date 2018年7月31日
	 */
	List<PublishAdverRecord> listPublishRecordByDevice(Long id, Byte isDeleted);

	/**
	 * 获取广告
	 * 
	 * @param id
	 * @return
	 * @author lijiaxin
	 * @date 2018年8月1日
	 */
	Optional<Advertisement> getAdvertisementById(Long id);

	/**
	 * 通过行驶证id获取设备
	 * 
	 * @param drivingRegistrationId 行驶证id
	 * @return
	 * @author tenglong
	 * @date 2018年8月2日
	 */
	Device getDeviceByDrivingRegistrationId(Long drivingRegistrationId);

}
