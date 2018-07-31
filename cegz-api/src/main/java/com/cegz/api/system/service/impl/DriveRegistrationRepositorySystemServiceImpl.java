package com.cegz.api.system.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cegz.api.dao.DriveRegistrationRepository;
import com.cegz.api.model.DrivingRegistration;
import com.cegz.api.system.service.DriveRegistrationRepositorySystemService;

/**
 * 联系人系列服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
@Service("driveRegistrationRepositorySystemService")
@Transactional
public class DriveRegistrationRepositorySystemServiceImpl implements DriveRegistrationRepositorySystemService {
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
