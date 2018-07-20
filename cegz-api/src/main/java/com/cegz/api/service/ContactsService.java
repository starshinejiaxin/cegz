package com.cegz.api.service;

import com.cegz.api.model.Contacts;
import com.cegz.api.model.DrivingLicense;
import com.cegz.api.model.IdCard;

/**
 * 联系人服务
 * @author lijiaxin
 * @date 2018年7月20日
 */
public interface ContactsService {
	/**
	 * 添加联系人
	 * @param contacts 联系人信息
	 * @param card 身份证信息
	 * @param license 驾驶证信息
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	int insertData(Contacts contacts, IdCard card, DrivingLicense license);
}
