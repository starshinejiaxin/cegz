package com.cegz.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * 广告实体类
 * @author lijiaxin
 * @date 2018年7月24日
 */
@Entity
@Table(name = "advertisement")
public class Advertisement {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 标题
	 */
	@Column(name = "title", nullable = false, length = 255)
	private String title;
	
	/**
	 * 标题图片
	 */
	@Column(name = "title_pic_url", nullable = false, length = 255)
	private String titlePicUrl;
	
	/**
	 * 内容图片
	 */
	@Column(name = "content_pic_url", nullable = false, length = 255)
	private String contentPicUrl;
	
	/**
	 * 内容
	 */
	@Column(name = "content", nullable = false, length = 1000)
	private String content;
	
	/**
	 * 审核状态 0 审核中，1 完成， 2 失败
	 */
	@Column(name = "status", nullable = false, length = 10)
	private int status;
	
	/**
	 * 审核失败原因
	 */
	@Column(name = "reason", nullable = false, length = 255)
	private String reason;
	
	/**
	 * 广告类型
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "advertisement_type_id", nullable = false)
	private AdvertisementType advertisementType;
	
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitlePicUrl() {
		return titlePicUrl;
	}

	public void setTitlePicUrl(String titlePicUrl) {
		this.titlePicUrl = titlePicUrl;
	}

	public String getContentPicUrl() {
		return contentPicUrl;
	}

	public void setContentPicUrl(String contentPicUrl) {
		this.contentPicUrl = contentPicUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public AdvertisementType getAdvertisementType() {
		return advertisementType;
	}

	public void setAdvertisementType(AdvertisementType advertisementType) {
		this.advertisementType = advertisementType;
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
	
	
	
}
