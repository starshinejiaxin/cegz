package com.cegz.api.model.view;

/**
 * 车辆视图
 *
 * @author tenglong
 * @date 2018年7月31日
 */
public class OrderView {
	/**
	 * id
	 */
	private Long id;

	/**
	 * 广告类型名称
	 */
	private String advertisementTypeName;

	/**
	 * 广告图
	 */
	private String advertisementUrl;

	/**
	 * 车辆数
	 */
	private Integer carSum;

	/**
	 * 实际金额
	 */
	private Double realMoney;

	/**
	 * 总金额
	 */
	private Double totalMoney;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdvertisementTypeName() {
		return advertisementTypeName;
	}

	public void setAdvertisementTypeName(String advertisementTypeName) {
		this.advertisementTypeName = advertisementTypeName;
	}

	public String getAdvertisementUrl() {
		return advertisementUrl;
	}

	public void setAdvertisementUrl(String advertisementUrl) {
		this.advertisementUrl = advertisementUrl;
	}

	public Integer getCarSum() {
		return carSum;
	}

	public void setCarSum(Integer carSum) {
		this.carSum = carSum;
	}

	public Double getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(Double realMoney) {
		this.realMoney = realMoney;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

}
