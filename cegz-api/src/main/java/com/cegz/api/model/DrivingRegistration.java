package com.cegz.api.model;

import java.util.Date;

import javax.persistence.*;

/**
 * 行驶证实体类
 * @author lijiaxin
 * @date 2018年7月19日
 */
@Entity
@Table(name = "driving_registration")
public class DrivingRegistration {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 车牌号码
	 */
	@Column(name = "plate_number", nullable = false, length = 100)
	private String plateNumber;
	
	/**
	 * 车辆类型
	 */
	@Column(name = "vehicle_type", nullable = false, length = 100)
	private String vehicle_type;
	
	/**
	 * 所有者
	 */
	@Column(name = "owner", nullable = false, length = 100)
	private String owner;
	
	/**
	 * 住址
	 */
	@Column(name = "address", nullable = false, length = 100)
	private String address;
	
	/**
	 * 使用性质
	 */
	@Column(name = "use_character", nullable = false, length = 100)
	private String useCharacter;
	
	/**
	 * 使用性质
	 */
	@Column(name = "model", nullable = false, length = 100)
	private String model;
	
	/**
	 * 车架号
	 */
	@Column(name = "vin", nullable = false, length = 100)
	private String vin;
	
	/**
	 * 发动机号
	 */
	@Column(name = "engine_number", nullable = false, length = 100)
	private String engineNumber;
	
	/**
	 * 注册日期
	 */
	@Column(name = "register_date", nullable = false, length = 100)
	private Date registerDate;
	
	/**
	 * 发证日期
	 */
	@Column(name = "issue_date", nullable = false, length = 100)
	private Date issueDate;
	
	/**
	 * 识别度
	 */
	@Column(name = "accuracy", nullable = false, length = 100)
	private float accuracy;
	
	/**
	 * 图片地址
	 */
	@Column(name = "picture_url", nullable = false, length = 200)
	private String pictureUrl;
	
	/**
	 * 数据状态 0 有效 1 无效
	 */
	@Column(name = "is_deleted", nullable = false, length = 100)
	private byte isDeleted;
	
	/**
	 * 修改时间
	 */
	@Column(name = "update_time", nullable = false, length = 100)
	private Date updateTime;
	
	/**
	 * 车辆出厂日期
	 */
	@Column(name = "car_birthday", nullable = false, length = 100)
	private Date carBirthday;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false, length = 100)
	private Date createTime;
	
	/**
	 * 创建用户
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "create_user_id", nullable = false)
	private Users createUserId;
	
	/**
	 * 修改用户
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "update_user_id", nullable = false)
	private Users updateUserId;
	
	/**
	 * 保荐方信息
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "sponsor_id", nullable = false)
	private Sponsor sponsor;

	/**
	 * 联系人信息
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "contact_id", nullable = false)
	private Contacts contact;
	
	
	public Contacts getContact() {
		return contact;
	}

	public void setContact(Contacts contact) {
		this.contact = contact;
	}

	public Date getCarBirthday() {
		return carBirthday;
	}

	public void setCarBirthday(Date carBirthday) {
		this.carBirthday = carBirthday;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUseCharacter() {
		return useCharacter;
	}

	public void setUseCharacter(String useCharacter) {
		this.useCharacter = useCharacter;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
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
