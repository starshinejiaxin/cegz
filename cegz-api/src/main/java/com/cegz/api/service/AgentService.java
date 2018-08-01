package com.cegz.api.service;

import java.util.Optional;

import com.cegz.api.model.Agent;

/**
 * 代理服务
 * 
 * @author lijiaxin
 * @date 2018年8月1日
 */
public interface AgentService {
	/**
	 * 录入代理商信息
	 * 
	 * @param agent
	 * @return
	 * @author Administrator
	 * @date 2018年8月1日
	 */
	Agent insert(Agent agent);

	/**
	 * 通过id获取数据
	 * 
	 * @param id
	 * @return
	 * @author tenglong
	 * @date 2018年8月1日
	 */
	Optional<Agent> getAgentById(Long id);
}
