package com.cegz.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cegz.api.model.Sponsor;

/**
 * 保荐方持久化接口
 * 
 * @author tenglong
 * @date 2018年7月30日
 */
@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
	@Query(value = "select s.id, s.province, s.company, s.businessLicense, s.name, s.phone, s.email, s.address, s.addressDetail from Sponsor s where 1=1 and s.isDeleted = 0 and s.status = 0 or s.status = 2")
	public List<Sponsor> getSponsorExamineList();
}
