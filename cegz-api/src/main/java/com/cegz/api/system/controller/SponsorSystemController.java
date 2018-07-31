package com.cegz.api.system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Sponsor;
import com.cegz.api.model.Users;
import com.cegz.api.model.view.SponsorView;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.SponsorService;
import com.cegz.api.system.service.SponsorSystemService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 保荐方服务
 * 
 * @author tenglong
 * @date 2018年7月30日
 */
@RestController
@RequestMapping("/system/sponsor")
public class SponsorSystemController {
	@Autowired
	private ServerAck serverAck;

	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private SponsorSystemService sponsorSystemService;

	@Autowired
	private AccountService accountService;
	/**
	 * 服务url
	 */
	@Value("${server.url}")
	private String serverUrl;

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
	 * 校验token and version
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
	 * 获取保荐方待审核列表
	 * 
	 * @param token
	 * @param version
	 * @return
	 * @author tenglong
	 * @date 2018年7月30日
	 */
	@PostMapping("getExamineList")
	public ResultData getExamineList(String token, String version) {
		if(checkTokenAndVersion(token, version) != null) {
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
			// 保荐方审核数据列表
			List<Sponsor> sponsorExamines = sponsorSystemService.listSponsorExamine();
			return serverAck.getSuccess().setData(sponsorExamines);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 通过id获取单条保荐方审核数据
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
		if(checkTokenAndVersion(token, version) != null) {
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
			// 保荐方单条审核数据
			Optional<Sponsor> sponsorOpt = sponsorService.getSponSorById(id);
			if (!sponsorOpt.isPresent()) {
				return serverAck.getParamError().setMessage("保荐方ID有误");
			}
			Sponsor sponsor = sponsorOpt.get();
			SponsorView view = new SponsorView();
			view.setId(sponsor.getId());
			view.setAddress(sponsor.getAddress());
			view.setAddressDetail(sponsor.getAddressDetail());
			view.setBusinessNumber(sponsor.getBusinessLicense());
			view.setCompany(sponsor.getCompany());
			view.setName(sponsor.getName());
			view.setPhone(sponsor.getPhone());
			view.setLicenseImgUrl(sponsor.getPictureUrl());
			view.setProvince(sponsor.getProvince());
			view.setStatus(sponsor.getStatus());
			view.setType(sponsor.getType());
			view.setEmail(sponsor.getEmail());
			view.setReason(sponsor.getReason());
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
	 * @date 2018年7月30日
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
		if(checkTokenAndVersion(token, version) != null) {
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
			// 获取保荐方信息
			Optional<Sponsor> sponsorOpt = sponsorService.getSponSorById(id);
			if (!sponsorOpt.isPresent()) {
				return serverAck.getParamError().setMessage("保荐方ID有误");
			}
			Sponsor sponsor = sponsorOpt.get();
			sponsor.setStatus(status.byteValue());
			sponsor.setReason(status.byteValue() == 1 ? null : reason);// 如果状态为1，原因置为null
			int ret = sponsorService.save(sponsor);
			if (ret == 0) {
				return serverAck.getFailure();
			}
			return serverAck.getSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
