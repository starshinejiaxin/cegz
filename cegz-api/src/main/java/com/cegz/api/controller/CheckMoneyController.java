package com.cegz.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.CheckMoney;
import com.cegz.api.model.Users;
import com.cegz.api.model.view.CheckMoneyView;
import com.cegz.api.service.AccountService;
import com.cegz.api.service.CheckMoneyService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 账单明细控制类
 * 
 * @author tenglong
 * @date 2018年8月2日
 */
@RestController
@RequestMapping("/checkMoney")
public class CheckMoneyController {
	@Autowired
	private ServerAck serverAck;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CheckMoneyService checkMoneyService;

	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;

	/**
	 * 获取账单明细信息列表
	 * 
	 * @param token
	 * @param version
	 * @return
	 * @author tenglong
	 * @date 2018年8月2日
	 */
	@PostMapping("listCheckMoney")
	public ResultData listCheckMoney(String token, String version) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
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
			// 通过用户id获取账单明细
			List<CheckMoney> list = checkMoneyService.listCheckMoneyByUserId(users.getId());
			if (list == null || list.size() <= 0) {
				return serverAck.getEmptyData();
			}
			List<CheckMoneyView> resultList = new ArrayList<>();
			for (CheckMoney checkMoney : list) {
				CheckMoneyView view = new CheckMoneyView();
				view.setId(checkMoney.getId());
				SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm");
				view.setCreateTime(sdf.format(checkMoney.getCreateTime() == null ? new Date() : checkMoney.getCreateTime()));
				view.setIsDeleted(checkMoney.getIsDeleted());
				view.setMoney(checkMoney.getMoney());
				view.setRemark(checkMoney.getRemark());
				view.setType(checkMoney.getType());
				resultList.add(view);
			}
			return serverAck.getSuccess().setData(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
