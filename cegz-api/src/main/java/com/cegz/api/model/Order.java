package com.cegz.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * 订单列表
 * @author lijiaxin
 * @date 2018年7月24日
 */
@Entity
@Table(name = "advertisement_type")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 编号
	 */
	@Column(name = "order_no", nullable = false, length = 255)
	private String orderNo;
	
	/**
	 * 名称
	 */
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	/**
	 * 订单状态，0 无效，1 进行中，2 完成
	 */
	@Column(name = "staus", nullable = false, length = 255)
	private byte status;
	
	/**
	 * 发布广告的设备数量
	 */
	@Column(name = "total_advertisement", nullable = false, length = 255)
	private Double totalAdvertisement;
	
	/**
	 * 总金额
	 */
	@Column(name = "toatl_money", nullable = false, length = 255)
	private Double totalMoney;
	
	/**
	 * 实际总金额
	 */
	@Column(name = "real_money", nullable = false, length = 255)
	private Double realMoney;
	
	/**
	 * 开始投放时间
	 */
	@Column(name = "start_time", nullable = false, length = 255)
	private Date startTime;
	
	/**
	 * 结束投放时间
	 */
	@Column(name = "end_time", nullable = false, length = 255)
	private Date endTime;
	
	/**
	 * 价格信息
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "price_id", nullable = false)
	private Price price;

	
	/**
	 * 广告信息
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "advertisement_id", nullable = false)
	private Users advertisement;
	
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Double getTotalAdvertisement() {
		return totalAdvertisement;
	}

	public void setTotalAdvertisement(Double totalAdvertisement) {
		this.totalAdvertisement = totalAdvertisement;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(Double realMoney) {
		this.realMoney = realMoney;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Users getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Users advertisement) {
		this.advertisement = advertisement;
	}

	
}
