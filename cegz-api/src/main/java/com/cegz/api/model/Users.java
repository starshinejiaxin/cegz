package com.cegz.api.model;
/**
 * 用户实体类
 * @author lijiaxin
 * @date 2018年7月19日
 */

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * uuid
	 */
	@Column(name = "uuid", nullable = false, length = 100)
	private String uuid;
	
	/**
	 * 账号
	 */
	@Column(name = "username", nullable = false, length = 100)
	private String userName;
	
	/**
	 * 密码
	 */
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	
	/**
	 * 姓名
	 */
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	/**
	 * 手机号
	 */
	@Column(name = "phone", nullable = false, length = 100)
	private String phone;
	
	/**
	 * 邮箱地址
	 */
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	/**
	 * 头像地址
	 */
	@Column(name = "icon", nullable = false, length = 100)
	private String icon;
	
	/**
	 * 数据是否有效，0 有效，1无效
	 */
	@Column(name = "is_deleted", nullable = false, length = 100)
	private byte isDeleted;
	
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
	 * 创建用户
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	private Users createUserId;
	
	/**
	 * 修改用户
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user_id", nullable = false)
	private Users updateUserId;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy  = "createUserId", fetch = FetchType.LAZY)
	private Wallet wallet;
	
	

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
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
