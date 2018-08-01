package com.cegz.api.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cegz.api.model.Agent;

/**
 * 
 * 代理商持久接口
 * @author lijiaxin
 * @date 2018年8月1日
 */
@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
	
	@Query(value = "select a.id, a.city, a.company, a.business, a.name, a.phone, a.email, a.address, a.addressDetail from Agent a where 1=1 and a.isDeleted = 0 and a.status = 0 or a.status = 2")
	public List<Agent> getAgentExamineList();
}
