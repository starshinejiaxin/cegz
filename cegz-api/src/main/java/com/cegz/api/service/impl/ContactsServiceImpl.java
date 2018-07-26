package com.cegz.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cegz.api.dao.DriveRegistrationRepository;
import com.cegz.api.dao.ContactsRepository;
import com.cegz.api.model.Contacts;
import com.cegz.api.model.DrivingRegistration;
import com.cegz.api.service.ContactsService;

/**
 * 联系人系列服务
 * @author lijiaxin
 * @date 2018年7月20日
 */
@Service("contactsService")
@Transactional
public class ContactsServiceImpl implements ContactsService {
	@Autowired
	private ContactsRepository contactsRepository;

	@Autowired
	private DriveRegistrationRepository drivingRegistrationRepository;
	
		
	@Override
	public int insertData(Contacts contacts) {
		contactsRepository.save(contacts);
		return 1;
	}

	@Override
	public int insertContractDriveRegist(DrivingRegistration dr) {
		drivingRegistrationRepository.save(dr);
		return 1;
	}

	@Override
	public Optional<Contacts> getContactById(Long id) {
		return contactsRepository.findById(id);
	}
	
	
	
}
