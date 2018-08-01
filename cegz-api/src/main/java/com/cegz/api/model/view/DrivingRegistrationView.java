package com.cegz.api.model.view;

import java.util.Date;

/**
 * 行驶证视图
 *
 * @author tenglong
 * @date 2018年7月31日
 */
public class DrivingRegistrationView {
	/**
	 * id
	 */
	private Long id;

	/**
	 * 保荐方
	 */
	private String company;

	/**
	 * 车牌号
	 */
	private String plateNumber;

	/**
	 * 品牌
	 */
	private String model;

	/**
	 * 出厂日期
	 */
	private Date carBirthday;

	/**
	 * 使用性质
	 */
	private String useCharacter;

	/**
	 * 行驶证照片
	 */
	private String pictureUrl;

	/**
	 * 状态 0 审核中，1 完成，2 失败
	 */
	private int Status;

	/**
	 * 行驶证照片
	 */
	private String reason;

	/**
	 * 安装时间
	 */
	private Date installTime;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 电话
	 */
	private String phone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getCarBirthday() {
		return carBirthday;
	}

	public void setCarBirthday(Date carBirthday) {
		this.carBirthday = carBirthday;
	}

	public String getUseCharacter() {
		return useCharacter;
	}

	public void setUseCharacter(String useCharacter) {
		this.useCharacter = useCharacter;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
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

}
