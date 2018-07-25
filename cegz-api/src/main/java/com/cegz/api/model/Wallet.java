package com.cegz.api.model;

import java.util.Date;

import javax.persistence.*;

/**
 * 钱包实体类
 *
 * @author lijiaxin
 * @date 2018年7月25日
 */
@Entity
@Table(name = "wallet")
public class Wallet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 编号
	 */
	@Column(name = "wallet_no", nullable = false, length = 20)
	private String walletNo;
	
	/**
	 * 金额
	 */
	@Column(name = "money", nullable = false, length = 20)
	private double money;
	
	/**
	 * 描述
	 */
	@Column(name = "remark", nullable = false, length = 20)
	private Integer remark;
	
	/**
	 * 数据是否有效 0 有效，1无效
	 */
	@Column(name = "is_deleted", nullable = false, length = 10)
	private byte isDeleted;
	
	/**
	 * 创建用户id
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "create_user_id", nullable = false)
	private Users createUserId;
	
	/**
	 * 修改用户ID
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "update_user_id", nullable = false)
	private Users updateUserId;
	
	/**
	 * 修改时间
	 */
	@Column(name = "update_time", nullable = false, length = 50)
	private Date updateTime;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false, length = 50)
	private Date createTime;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}
    
	public Users getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Users createUserId) {
		this.createUserId = createUserId;
	}

	public Users getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Users updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWalletNo() {
		return walletNo;
	}

	public void setWalletNo(String walletNo) {
		this.walletNo = walletNo;
	}

	public Integer getRemark() {
		return remark;
	}

	public void setRemark(Integer remark) {
		this.remark = remark;
	}
	
	
	
	
}
