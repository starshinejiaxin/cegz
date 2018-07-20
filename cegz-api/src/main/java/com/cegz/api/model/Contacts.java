package com.cegz.api.model;

import java.util.Date;

import javax.persistence.*;

/**
 * 联系人实体类
 * @author lijiaxin
 * @date 2018年7月19日
 */
@Entity
@Table(name = "contacts")
public class Contacts {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	/**
	 * 手机号
	 */
	@Column(name = "phone", nullable = false, length = 20)
	private String phone;
	@OneToOne(cascade = CascadeType.ALL)
	  
	/**
	 * 驾驶证id
	 */
	@JoinColumn(name = "driving_license_id")
	private Integer drivingLicenseId;
	
	/**
	 * 姓名
	 */
	@Column(name = "name", nullable = false, length = 200)
	private String name;
	
	/**
	 * 身份证id
	 */
	@JoinColumn(name = "idcard_id")
	private Integer idcardId;
	
	/**
	 * 审核状态
	 */
	@Column(name = "status", nullable = false, length = 10)
	private byte status;
	
	/**
	 * 审核结论
	 */
	@Column(name = "reason", nullable = false, length = 200)
	private String reason;
	
	
	/**
	 * 数据是否有效 0 有效，1无效
	 */
	@Column(name = "is_deleted", nullable = false, length = 10)
	private byte isDeleted;
	
	/**
	 * 创建用户id
	 */
	@Column(name = "create_user_id", nullable = false, length = 20)
	private Integer createUserId;
	
	/**
	 * 修改用户ID
	 */
	@Column(name = "update_user_id", nullable = false, length = 20)
	private Integer updateUserId;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getDrivingLicenseId() {
		return drivingLicenseId;
	}

	public void setDrivingLicenseId(Integer drivingLicenseId) {
		this.drivingLicenseId = drivingLicenseId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdcardId() {
		return idcardId;
	}

	public void setIdcardId(Integer idcardId) {
		this.idcardId = idcardId;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
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
