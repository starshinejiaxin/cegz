package com.cegz.api.model;

import java.util.Date;

import javax.persistence.*;

/**
 * 驾驶证实体类
 * @author lijiaxin
 * @date 2018年7月19日
 */
@Entity
@Table(name = "driving_license")
public class DrivingLicense {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	/**
	 * 证件号
	 */
	@Column(name = "certificate_number", nullable = false, length = 100)
	private String certificateNumber;
	
	/**
	 * 姓名
	 */
	@Column(name = "user_name", nullable = false, length = 100)
	private String userName;
	
	/**
	 * 性别
	 */
	@Column(name = "sex", nullable = false, length = 10)
	private byte sex;
	
	/**
	 * 国籍
	 */
	@Column(name = "country", nullable = false, length = 10)
	private String country;
	
	/**
	 * 住址
	 */
	@Column(name = "address", nullable = false, length = 10)
	private String address;
	
	/**
	 * 出生日期
	 */
	@Column(name = "birthday", nullable = false, length = 100)
	private Date birthday;
	
	/**
	 * 初次领证日期
	 */
	@Column(name = "first", nullable = false, length = 100)
	private String first;
	
	/**
	 * 准驾车型
	 */
	@Column(name = "car_type", nullable = false, length = 100)
	private String carType;
	
	/**
	 * 开始有效日期
	 */
	@Column(name = "valid_start", nullable = false, length = 100)
	private Date vaildStart;
	
	/**
	 * 结束有效日期
	 */
	@Column(name = "valid_end", nullable = false, length = 100)
	private Date vaildEnd;
	
	/**
	 * 发证机关
	 */
	@Column(name = "organization", nullable = false, length = 200)
	private String organization;
	
	/**
	 * 图片地址
	 */
	@Column(name = "picture_url", nullable = false, length = 200)
	private String pictureUrl;
	
	/**
	 * 是否有效，0有效，1无效
	 */
	@Column(name = "is_deleted", nullable = false, length = 100)
	private Date isDeleted;
	
	/**
	 * 开始有效日期
	 */
	@Column(name = "accuracy", nullable = false, length = 100)
	private float accuracy;
	
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
	 * 创建userId
	 */
	@Column(name = "create_user_id", nullable = false, length = 11)
	private Integer createUserId;
	
	/**
	 * 修改userId
	 */
	@Column(name = "update_user_id", nullable = false, length = 11)
	private Integer updateUserId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Date getVaildStart() {
		return vaildStart;
	}

	public void setVaildStart(Date vaildStart) {
		this.vaildStart = vaildStart;
	}

	public Date getVaildEnd() {
		return vaildEnd;
	}

	public void setVaildEnd(Date vaildEnd) {
		this.vaildEnd = vaildEnd;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Date getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Date isDeleted) {
		this.isDeleted = isDeleted;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	
}
