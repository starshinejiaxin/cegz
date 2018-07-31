package com.cegz.api.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cegz.api.dao.ContactsRepository;
import com.cegz.api.model.Contacts;
import com.cegz.api.system.service.ContactsSystemService;

/**
 * 联系人系列服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
@Service("contactsSystemService")
@Transactional
public class ContactsSystemServiceImpl implements ContactsSystemService {
	@Autowired
	private ContactsRepository contactsRepository;

	@Override
	public List<Contacts> listContactsExamine() {
		return contactsRepository.getContactsExamineList();
	}
	
}
