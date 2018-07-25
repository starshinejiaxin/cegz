package com.cegz.api.model;

import java.util.Date;

import javax.persistence.*;

/**
 * 价格实体类
 * @author lijiaxin
 * @date 2018年7月24日
 */
@Entity
@Table(name = "price")
public class Price {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 名称
	 */
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	/**
	 * 价格编号
	 */
	@Column(name = "price_no", nullable = false, length = 255)
	private String priceNo;
	
	/**
	 * 价格
	 */
	@Column(name = "price", nullable = false, length = 255)
	private Double price;
	
	/**
	 * 折扣
	 */
	@Column(name = "discount", nullable = false, length = 255)
	private Double discount;
	
	/**
	 * 描述
	 */
	@Column(name = "remark", nullable = false, length = 255)
	private String remark;
	
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
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPriceNo() {
		return priceNo;
	}

	public void setPriceNo(String priceNo) {
		this.priceNo = priceNo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	
	
}
