package com.cegz.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cegz.api.dao.DriveRegistrationRepository;
import com.cegz.api.model.DrivingRegistration;
import com.cegz.api.service.DrivingRegistrationService;

/**
 * 行驶证服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
@Service("drivingRegistrationService")
@Transactional
public class DrivingRegistrationServiceImpl implements DrivingRegistrationService {
	@Autowired
	private DriveRegistrationRepository driveRegistrationRepository;

	@Override
	public DrivingRegistration getDrivingRegistrationByPlateNumber(String plateNumber) {
		return driveRegistrationRepository.getDrivingRegistrationByPlateNumber(plateNumber);
	}

	@Override
	public List<DrivingRegistration> listDrivingRegistration(Long sponsorId) {
		return driveRegistrationRepository.listDrivingRegistration(sponsorId);
	}

}
