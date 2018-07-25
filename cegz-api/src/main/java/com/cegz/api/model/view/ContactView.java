package com.cegz.api.model.view;
/**
 * 车主视图
 *
 * @author lijiaxin
 * @date 2018年7月25日
 */
public class ContactView {
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 身份证正面照片
	 */
	private String firstImageUrl;
	
	
	/**
	 * 身份证反面照片
	 */
	private String secondImageUrl;
	
    /**
     * 驾驶证照片
     */
	private String driveLicenseImageUrl;

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

	public String getFirstImageUrl() {
		return firstImageUrl;
	}

	public void setFirstImageUrl(String firstImageUrl) {
		this.firstImageUrl = firstImageUrl;
	}

	public String getSecondImageUrl() {
		return secondImageUrl;
	}

	public void setSecondImageUrl(String secondImageUrl) {
		this.secondImageUrl = secondImageUrl;
	}

	public String getDriveLicenseImageUrl() {
		return driveLicenseImageUrl;
	}

	public void setDriveLicenseImageUrl(String driveLicenseImageUrl) {
		this.driveLicenseImageUrl = driveLicenseImageUrl;
	}
	
}
