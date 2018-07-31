package com.cegz.api.system.service;

import java.util.List;

import com.cegz.api.model.Contacts;

/**
 * 联系人后台服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
public interface ContactsSystemService {
	/**
	 * 获取车主未审核列表
	 * 
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	List<Contacts> listContactsExamine();
}
