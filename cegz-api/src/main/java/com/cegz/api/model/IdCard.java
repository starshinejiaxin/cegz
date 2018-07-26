package com.cegz.api.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 身份证实体类
 * @author lijiaxin
 * @date 2018年7月19日
 */
@Entity
@Table(name = "id_card")
public class IdCard {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 姓名
	 */
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	/**
	 * 性别
	 */
	@Column(name = "sex", nullable = false, length = 100)
	private String sex;
	
	/**
	 * 民族
	 */
	@Column(name = "nation", nullable = false, length = 100)
	private String nation;
	
	/**
	 * 生日
	 */
	@Column(name = "birth", nullable = false, length = 100)
	private Date birth;
	
	/**
	 * 住址
	 */
	@Column(name = "address", nullable = false, length = 200)
	private String address;
	
	/**
	 * 证件号
	 */
	@Column(name = "num", nullable = false, length = 100)
	private String num;
	
	/**
	 * 发证机关
	 */
	@Column(name = "authority", nullable = false, length = 100)
	private String authority;
	
	/**
	 * 证件有效期
	 */
	@Column(name = "valid_date", nullable = false, length = 100)
	private String vaildDate;
	
	/**
	 * 证件图片地址
	 */
	@Column(name = "picture_url", nullable = false, length = 100)
	private String pictureUrl;
	
	/**
	 * 数据状态 0 有效，1无效
	 */
	@Column(name = "is_deleted", nullable = false, length = 100)
	private byte isDeleted;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false, length = 100)
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	@Column(name = "update_time", nullable = false, length = 100)
	private Date updateTime;
	
	/**
	 * 创建用户
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	private Users createUserId;
	
	/**
	 * 修改用户
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user_id", nullable = false)
	private Users updateUserId;

	/**
	 * 审核状态 0 审核中，1 成功，2失败
	 */
	@Column(name = "status", nullable = false, length = 11)
	private int status;

	/**
	 * 审核结果原因
	 */
	@Column(name = "reason", nullable = false, length = 11)
	private String reason;
	
	
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getVaildDate() {
		return vaildDate;
	}

	public void setVaildDate(String vaildDate) {
		this.vaildDate = vaildDate;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	
}
