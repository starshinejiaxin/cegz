package com.cegz.api.model.view;

/**
 * 车辆视图
 *
 * @author lijiaxin
 * @date 2018年7月26日
 */
public class WalletView {
	private Long id;
	/**
	 * 编号
	 */
	private String walletNo;

	/**
	 * 金额
	 */
	private double money;

	/**
	 * 描述
	 */
	private Integer remark;

	/**
	 * 数据是否有效 0 有效，1无效
	 */
	private byte isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWalletNo() {
		return walletNo;
	}

	public void setWalletNo(String walletNo) {
		this.walletNo = walletNo;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Integer getRemark() {
		return remark;
	}

	public void setRemark(Integer remark) {
		this.remark = remark;
	}

	public byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

}
