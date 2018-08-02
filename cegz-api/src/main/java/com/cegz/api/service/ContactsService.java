package com.cegz.api.service;

import java.util.Optional;

import com.cegz.api.model.Contacts;
import com.cegz.api.model.DrivingRegistration;

/**
 * 联系人服务
 * 
 * @author lijiaxin
 * @date 2018年7月20日
 */
public interface ContactsService {
	/**
	 * 添加联系人
	 * 
	 * @param contacts 联系人信息
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	int insertData(Contacts contacts);

	/**
	 * 添加行驶证
	 * 
	 * @param cdr
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月23日
	 */
	int insertContractDriveRegist(DrivingRegistration cdr);

	/**
	 * 通过ID获取联系人信息
	 * 
	 * @param id
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月23日
	 */
	Optional<Contacts> getContactById(Long id);
	
	/**
	 * 获取行驶证信息
	 * @param id
	 * @return
	 * @author Administrator
	 * @date 2018年8月2日
	 */
	Optional<DrivingRegistration> getRegistrationById(Long id);
}
