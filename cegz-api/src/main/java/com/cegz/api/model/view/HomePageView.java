package com.cegz.api.model.view;

/**
 * 首页视图
 *
 * @author tenglong
 * @date 2018年8月2日
 */
public class HomePageView {
	private Long id;

	/**
	 * 图片地址
	 */
	private String titlePicUrl;

	/**
	 * 内容图片
	 */
	private String contentPicUrl;

	/**
	 * 车辆类型 1 网约车 2 教练车
	 */
	private int carType;

	/**
	 * 发布状态 , 0 无效，1 成功
	 */
	private int status;

	/**
	 * 发布天数
	 */
	private int publishDay;

	/**
	 * 发布开始时间
	 */
	private String startTime;

	/**
	 * 发布结束时间
	 */
	private String endTime;

	public Long getId() {
		return id;
	}

	public String getTitlePicUrl() {
		return titlePicUrl;
	}

	public void setTitlePicUrl(String titlePicUrl) {
		this.titlePicUrl = titlePicUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentPicUrl() {
		return contentPicUrl;
	}

	public void setContentPicUrl(String contentPicUrl) {
		this.contentPicUrl = contentPicUrl;
	}

	public int getPublishDay() {
		return publishDay;
	}

	public void setPublishDay(int publishDay) {
		this.publishDay = publishDay;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

	public int getCarType() {
		return carType;
	}

}
