package com.cegz.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * 广告类型实体类
 * @author lijiaxin
 * @date 2018年7月24日
 */
@Entity
@Table(name = "advertisement_type")
public class AdvertisementType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 编号
	 */
	@Column(name = "type_no", nullable = false, length = 20)
	private String typeNo;
	
	/**
	 * 描述
	 */
	@Column(name = "remark", nullable = false, length = 20)
	private Integer remark;
	
	/**
	 * 姓名
	 */
	@Column(name = "name", nullable = false, length = 200)
	private String name;
	
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

	public String getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public Integer getRemark() {
		return remark;
	}

	public void setRemark(Integer remark) {
		this.remark = remark;
	}
	
	
	
	
}
