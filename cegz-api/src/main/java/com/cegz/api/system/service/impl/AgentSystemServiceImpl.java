package com.cegz.api.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.AgentRepository;
import com.cegz.api.model.Agent;
import com.cegz.api.system.service.AgentSystemService;
/**
 * 代理服务
 * 
 * @author tenglong
 * @date 2018年8月1日
 */
@Service("agentSystemService")
public class AgentSystemServiceImpl implements AgentSystemService {
	
	@Autowired
	private AgentRepository agentRepository;

	@Override
	public List<Agent> listAgentExamine() {
		return agentRepository.getAgentExamineList();
	}

}
