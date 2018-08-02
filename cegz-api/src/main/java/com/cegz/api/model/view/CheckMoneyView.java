package com.cegz.api.model.view;

/**
 * 账单明细视图
 *
 * @author tenglong
 * @date 2018年8月2日
 */
public class CheckMoneyView {
	private Long id;
	/**
	 * 金额
	 */
	private Double money;

	/**
	 * 描述
	 */
	private String remark;

	/**
	 * 账单类型 1 支出， 2 收入
	 */
	private int type;

	/**
	 * 数据是否有效 0 有效，1无效
	 */
	private byte isDeleted;

	/**
	 * 创建时间
	 */
	private String createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
