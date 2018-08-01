package com.cegz.api.dao;


import org.springframework.data.jpa.repository.JpaRepository;
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
	
}
