package com.cegz.api.system.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cegz.api.dao.DriveRegistrationRepository;
import com.cegz.api.model.DrivingRegistration;
import com.cegz.api.system.service.DrivingRegistrationSystemService;

/**
 * 行驶证后台服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
@Service("drivingRegistrationSystemService")
@Transactional
public class DrivingRegistrationSystemServiceImpl implements DrivingRegistrationSystemService {
	@Autowired
	private DriveRegistrationRepository driveRegistrationRepository;

	@Override
	public Optional<DrivingRegistration> getDrivingRegistrationById(Long id) {
		return driveRegistrationRepository.findById(id);
	}

	@Override
	public int save(DrivingRegistration drivingRegistration) {
		driveRegistrationRepository.save(drivingRegistration);
		return 1;
	}

}
