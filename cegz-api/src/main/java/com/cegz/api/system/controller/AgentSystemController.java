package com.cegz.api.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Agent;
import com.cegz.api.model.Users;
import com.cegz.api.model.view.AgentView;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.AgentService;
import com.cegz.api.system.service.AgentSystemService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 代理商后台服务
 * 
 * @author tenglong
 * @date 2018年8月1日
 */
@RestController
@RequestMapping("/system/agent")
public class AgentSystemController {
	@Autowired
	private ServerAck serverAck;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentSystemService agentSystemService;

	@Autowired
	private AccountService accountService;

	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;

	/**
	 * 校验token and version
	 * 
	 * @param token
	 * @param version
	 * @return
	 */
	private ResultData checkTokenAndVersion(String token, String version) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
		}
		return null;
	}

	/**
	 * 获取代理商待审核列表
	 * 
	 * @param token
	 * @param version
	 * @return
	 * @author tenglong
	 * @date 2018年8月1日
	 */
	@PostMapping("getExamineList")
	public ResultData getExamineList(String token, String version) {
		if (checkTokenAndVersion(token, version) != null) {
			return checkTokenAndVersion(token, version);
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			// 代理商审核数据列表
			List<Agent> agentExamines = agentSystemService.listAgentExamine();
			return serverAck.getSuccess().setData(agentExamines);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 通过id获取单条代理商审核数据
	 * 
	 * @param id
	 * @param token
	 * @param version
	 * @return
	 * @author tenglong
	 * @date 2018年7月30日
	 */
	@PostMapping("getExamineById")
	public ResultData getExamineById(Long id, String token, String version) {
		if (id == null) {
			return serverAck.getParamError().setMessage("主键不能为空");
		}
		if (checkTokenAndVersion(token, version) != null) {
			return checkTokenAndVersion(token, version);
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			// 代理商单条审核数据
			Optional<Agent> agentOpt = agentService.getAgentById(id);
			if (!agentOpt.isPresent()) {
				return serverAck.getParamError().setMessage("代理商ID有误");
			}
			Agent agent = agentOpt.get();
			AgentView view = new AgentView();
			view.setId(agent.getId());
			view.setCity(agent.getCity());
			view.setCompany(agent.getCompany());
			view.setBusinessNumber(agent.getBusiness());
			view.setLicenseImgUrl(agent.getPictureUrl());
			view.setName(agent.getName());
			view.setPhone(agent.getPhone());
			view.setEmail(agent.getEmail());
			view.setAddress(agent.getAddress());
			view.setAddressDetail(agent.getAddressDetail());
			view.setStatus(agent.getStatus());
			view.setReason(agent.getReason());
			return serverAck.getSuccess().setData(view);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 状态审核
	 * 
	 * @param id
	 * @param token
	 * @param reason
	 * @return
	 * @author tenglong
	 * @date 2018年8月1日
	 */
	@PostMapping("statusExamine")
	public ResultData statusExamine(Long id, Integer status, String reason, String token, String version) {
		if (id == null) {
			return serverAck.getParamError().setMessage("主键不能为空");
		}
		if (status == null || status == 0) {// 审核状态 0 审核中，1 通过，2 未通过
			return serverAck.getParamError().setMessage("审核状态必填且不为0");
		}
		if (status != null && status != 0 && status == 2 && StringUtil.isEmpty(reason)) {// 审核状态为未通过原因必填
			return serverAck.getParamError().setMessage("审核未通过时原因不能为空");
		}
		if (checkTokenAndVersion(token, version) != null) {
			return checkTokenAndVersion(token, version);
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			// 处理
			// 获取代理商信息
			Optional<Agent> agentOpt = agentService.getAgentById(id);
			if (!agentOpt.isPresent()) {
				return serverAck.getParamError().setMessage("代理商ID有误");
			}
			Agent agent = agentOpt.get();
			agent.setStatus(status.byteValue());
			agent.setReason(status.byteValue() == 1 ? null : reason);// 如果状态为1，原因置为null
			agent.setUpdateTime(new Date());
			agent = agentService.insert(agent);
			if (agent == null || agent.getId() == null) {
				return serverAck.getFailure();
			}
			return serverAck.getSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
