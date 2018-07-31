package com.cegz.api.service;

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
	 * @param record
	 * @return
	 * @author Administrator
	 * @date 2018年7月30日
	 */
	PublishAdverRecord insertPublishRecord(PublishAdverRecord record);
}
