package com.cegz.api.model.view;
/**
 * 保荐方视图
 *
 * @author lijiaxin
 * @date 2018年7月26日
 */
public class SponsorView {
	private Long id;
	
	/**
	 * 身份名称
	 */
	private String province;
	
	/**
	 * 公司名称
	 */
	private String company;
	
	/**
	 * 执照编号
	 */
	private String businessNumber;
	
	/**
	 * 执照图片
	 */
	private String licenseImgUrl;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 电子邮箱
	 */
	private String email;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 详细地址
	 */
	private String addressDetail;
	
	/**
	 * 审核状态 0 审核中，1 成功，2 失败
	 */
	private int status;
	
	/**
	 * 原因
	 */
	private String reason;

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public String getLicenseImgUrl() {
		return licenseImgUrl;
	}

	public void setLicenseImgUrl(String licenseImgUrl) {
		this.licenseImgUrl = licenseImgUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	
}
