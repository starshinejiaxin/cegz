package com.cegz.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Users;
import com.cegz.api.model.Wallet;
import com.cegz.api.model.view.WalletView;
import com.cegz.api.service.AccountService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 钱包控制类
 * @author tenglong
 * @date 2018年8月2日
 */
@RestController
@RequestMapping("/wallet")
public class WalletController {
	@Autowired
	private ServerAck serverAck;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;
	
	/**
	 * 获取钱包信息
	 * @param token
	 * @param version
	 * @return
	 * @author tenglong
	 * @date 2018年8月2日
	 */
	@PostMapping("queryWallet")
	public ResultData queryWallet(String token, String version) {
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
			Wallet wallet = users.getWallet();
			if (wallet == null) {
				return serverAck.getEmptyData();
			}
			WalletView view = new WalletView();
			view.setId(wallet.getId());
			view.setIsDeleted(wallet.getIsDeleted());
			view.setMoney(wallet.getMoney());
			view.setRemark(wallet.getRemark());
			view.setWalletNo(wallet.getWalletNo());
			return serverAck.getSuccess().setData(view);
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
