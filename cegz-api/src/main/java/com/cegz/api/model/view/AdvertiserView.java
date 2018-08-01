package com.cegz.api.model.view;

/**
 * 广告主视图
 *
 * @author tenglong
 * @date 2018年7月30日
 */
public class AdvertiserView {
	private Long id;
	/**
	 * 城市
	 */
	private String city;

	/**
	 * 公司名称
	 */
	private String company;

	/**
	 * 营业执照号
	 */
	private String businessLicense;

	/**
	 * 营业执照
	 */
	private String pictureUrl;

	/**
	 * 联系人
	 */
	private String name;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 联系地址
	 */
	private String address;

	/**
	 * 详细地址
	 */
	private String address_detail;

	/**
	 * 审核状态 0 审核中，1 进行中，2 完成，3 失败
	 */
	private int status;

	/**
	 * 原因
	 */
	private String reason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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

	public String getAddress_detail() {
		return address_detail;
	}

	public void setAddress_detail(String address_detail) {
		this.address_detail = address_detail;
	}

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

}
