package com.cegz.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cegz.api.dao.ContactsRepository;
import com.cegz.api.dao.DriveLicenseRepository;
import com.cegz.api.dao.IdCardRepository;
import com.cegz.api.model.Contacts;
import com.cegz.api.model.DrivingLicense;
import com.cegz.api.model.IdCard;
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
	private IdCardRepository cardRepository;
	
	@Autowired
	private DriveLicenseRepository licenseRepository;
	
	@Override
	public int insertData(Contacts contacts, IdCard card, DrivingLicense license) {
		cardRepository.save(card);
		licenseRepository.save(license);
		contactsRepository.save(contacts);
		return 1;
	}
	
	
}
