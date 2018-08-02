package com.cegz.api.service;

import java.util.Date;

import java.util.List;

import com.cegz.api.model.PublishAdverRecord;

/**
 * 发布广告服务
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
public interface PublishAdverService {
	/**
	 * 保存发布信息
	 * 
	 * @param record
	 * @return
	 * @author Administrator
	 * @date 2018年7月30日
	 */
	PublishAdverRecord insertPublishRecord(PublishAdverRecord record);

	/**
	 * 修改过期广告
	 * 
	 * @param status
	 * @param time
	 * @return
	 * @author Administrator
	 * @date 2018年8月2日
	 */
	int updatePublishRecord(Date time);

	/**
	 * 通过设备id获取广告发布数据
	 * 
	 * @param deviceId 设备id
	 * @return
	 * @author tenglong
	 * @date 2018年8月2日
	 */
	List<PublishAdverRecord> listPublishAdverByDeviceId(Long deviceId);

}
