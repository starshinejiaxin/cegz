package com.cegz.api.service.impl;

import java.util.Date;

import java.util.List;
import javax.transaction.Transactional;

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
@Transactional
public class PublishAdverServiceImpl implements PublishAdverService {

	@Autowired
	private PublishAdverRecordRepository publishRepository;

	@Override
	public PublishAdverRecord insertPublishRecord(PublishAdverRecord record) {
		return publishRepository.save(record);
	}

	@Override
	public int updatePublishRecord(Date time) {
		return publishRepository.updateStatusByDate(time);
	}

	@Override
	public List<PublishAdverRecord> listPublishAdverByDeviceId(Long deviceId) {
		return publishRepository.listPublishAdverByDeviceId(deviceId);
	}

}
