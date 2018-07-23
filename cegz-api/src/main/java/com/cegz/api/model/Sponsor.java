package com.cegz.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * 联系人实体类
 * @author lijiaxin
 * @date 2018年7月19日
 */
@Entity
@Table(name = "contacts")
public class Sponsor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 手机号
	 */
	@Column(name = "phone", nullable = false, length = 20)
	private String phone;
	
	/**
	 * 保荐方类型 1 网约车，2 驾校
	 */
	@Column(name = "type", nullable = false, length = 20)
	private Integer type;
	
	/**
	 * 姓名
	 */
	@Column(name = "name", nullable = false, length = 200)
	private String name;
	
	/**
	 * 省份
	 */
	@Column(name = "province", nullable = false, length = 20)
	private String province;
	
	/**
	 * 公司名称
	 */
	@Column(name = "company", nullable = false, length = 20)
	private String company;
	
	/**
	 * 营业执照编号
	 */
	@Column(name = "business_license", nullable = false, length = 20)
	private String businessLicense;
	
	/**
	 * 住址
	 */
	@Column(name = "address", nullable = false, length = 20)
	private String address;
	
	/**
	 * email 邮箱
	 */
	@Column(name = "email", nullable = false, length = 20)
	private String email;
	
	/**
	 * 详细住址
	 */
	@Column(name = "address_detail", nullable = false, length = 20)
	private String addressDetail;
	
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
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "create_user_id", nullable = false)
	private Users createUserId;
	
	/**
	 * 修改用户ID
	 */
	@OneToOne(cascade = CascadeType.ALL)
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
	
	/**
	 * 车辆信息列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sponsor")
	private List<DrivingRegistration> listDrivingRegistration;

	
	public List<DrivingRegistration> getListDrivingRegistration() {
		return listDrivingRegistration;
	}

	public void setListDrivingRegistration(List<DrivingRegistration> listDrivingRegistration) {
		this.listDrivingRegistration = listDrivingRegistration;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
    

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
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
