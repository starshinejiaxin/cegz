package com.cegz.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Agent;
import com.cegz.api.model.Users;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.AgentService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 代理控制类
 * 
 * @author lijiaxin
 * @date 2018年8月1日
 */       
@RestController
@RequestMapping("agent")
public class AgentController {
	@Autowired
	private ServerAck serverAck;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AgentService agentService;
	
	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;
	
	/**
	 * 图片根地址
	 */
	@Value("${server.image-url}")
	private String baseImageUrl;
	
	/**
	 * 录入代理商
	 * @param businessFile
	 * @param name
	 * @param phone
	 * @param email
	 * @param address
	 * @param addressDetail
	 * @param province
	 * @param company
	 * @param businessLicense
	 * @param token
	 * @param type
	 * @param version
	 * @return
	 * @author Administrator
	 * @date 2018年8月1日
	 */
	@PostMapping("regist")
	public ResultData insertsponsor(String businessFile,String name,
			String phone,
			String email,
			String address,
			String addressDetail,
			String city,
			String company,
			String businessLicense,
			String token,
			String type,
			String version, Long id) {
		if (StringUtil.isEmpty(name)) {
			return serverAck.getParamError().setMessage("姓名不能为空");
		}
		if (StringUtil.isEmpty(phone)) {
			return serverAck.getParamError().setMessage("手机号不能为空");
		}
		if (!StringUtil.isMobile(phone)) {
			return serverAck.getParamError().setMessage("手机号格式有误");
		}
		if (StringUtil.isEmpty(address)) {
			return serverAck.getParamError().setMessage("地址不能为空");
		}
		if (StringUtil.isEmpty(addressDetail)) {
			return serverAck.getParamError().setMessage("详细地址不能为空");
		}
		if (StringUtil.isEmpty(city)) {
			return serverAck.getParamError().setMessage("城市不能为空");
		}
		if (StringUtil.isEmpty(company)) {
			return serverAck.getParamError().setMessage("公司名称不能为空");
		}
		if (StringUtil.isEmpty(businessLicense)) {
			return serverAck.getParamError().setMessage("营业执照编号不能为空");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
		}
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (StringUtil.isEmpty(type)) {
			return serverAck.getParamError().setMessage("类型不能为空");
		}
		if (!"12".contains(type)) {
			return serverAck.getParamError().setMessage("类型有吴");
		}
		if (StringUtil.isEmpty(businessFile)) {
			return serverAck.getParamError().setMessage("营业执照图片不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String [] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			Agent vaildAgent = users.getAgent();
			if (id == null) {
				if (vaildAgent != null && vaildAgent.getId() != null) {
					return serverAck.getParamError().setMessage("代理商已经存在");
				}
			} else {
				if (id.equals(vaildAgent.getId())) {
					return serverAck.getParamError().setMessage("ID错误");
				}
			}
			
			// 图片保存
//			String filePath = ImageUtil.getSponsorDir();
//			String fileName = ImageUtil.saveImg(businessFile, filePath);
//			String imageUrl = serverUrl + Constant.SPONSOR_IMG_DRI + "/" + fileName;
			String imageUrl = baseImageUrl + businessFile;
			// 设置代理商信息
			Agent agent = null;
			if (id == null) {
				agent = new Agent();
				agent.setCreateTime(new Date());
			} else {
				agent = vaildAgent;
				agent.setUpdateTime(new Date());
			}
			
			agent.setAddress(address);
			agent.setAddressDetail(addressDetail);
			agent.setName(name);
			agent.setPhone(phone);
			agent.setCity(city);
			agent.setCompany(company);
			agent.setBusiness(businessLicense);
			agent.setEmail(email);
			agent.setCreateUserId(users);
			agent.setPictureUrl(imageUrl);
			// 处理
			Agent retAgent = agentService.insert(agent);
			if (retAgent != null && retAgent.getId() != null) {
				return serverAck.getSuccess();
			}
			return serverAck.getFailure();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
