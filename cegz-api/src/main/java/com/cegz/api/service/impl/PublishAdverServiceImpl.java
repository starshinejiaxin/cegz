package com.cegz.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.PublishAdverRecordRepository;
import com.cegz.api.model.PublishAdverRecord;
import com.cegz.api.service.PublishAdverService;
/**
 * 发布广告服务
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
@Service("publishAdverService")
public class PublishAdverServiceImpl implements PublishAdverService{

	@Autowired
	private PublishAdverRecordRepository publishRepository;
	@Override
	public PublishAdverRecord insertPublishRecord(PublishAdverRecord record) {
		return publishRepository.save(record);
	}
	
}
