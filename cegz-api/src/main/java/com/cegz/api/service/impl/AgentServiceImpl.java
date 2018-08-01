package com.cegz.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.AgentRepository;
import com.cegz.api.model.Agent;
import com.cegz.api.service.AgentService;

/**
 * 代理服务
 * 
 * @author lijiaxin
 * @date 2018年8月1日
 */
@Service("agentService")
public class AgentServiceImpl implements AgentService {

	@Autowired
	private AgentRepository agentRepository;

	@Override
	public Agent insert(Agent agent) {
		return agentRepository.save(agent);
	}

	@Override
	public Optional<Agent> getAgentById(Long id) {
		return agentRepository.findById((Long) id);
	}
}
