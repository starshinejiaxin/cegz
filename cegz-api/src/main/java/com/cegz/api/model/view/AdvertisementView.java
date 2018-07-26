package com.cegz.api.model.view;
/**
 * 广告视图
 *
 * @author lijiaxin
 * @date 2018年7月25日
 */
public class AdvertisementView {
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 缩略图地址
	 */
	private String titleImgUrl;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 内容图片地址集合，用逗号分隔
	 */
	private String contentImages;
	
	/**
	 * 订单状态 0 审核中，1 进行中，2 完成，3 失败
	 */
	private int status;
	
	/**
	 * 原因
	 */
	private String reason;
	
	/**
	 * 车辆类型 1 网约车，2 教练车 , 3 网约车和教练车
	 */
	private int carType;

	/**
	 * 广告类型 1 图片广告，2 文字广告
	 */
	private Long adverType;
	
	
	
	public Long getAdverType() {
		return adverType;
	}

	public void setAdverType(Long adverType) {
		this.adverType = adverType;
	}

	public int getCarType() {
		return carType;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

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

	public String getTitleImgUrl() {
		return titleImgUrl;
	}

	public void setTitleImgUrl(String titleImgUrl) {
		this.titleImgUrl = titleImgUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentImages() {
		return contentImages;
	}

	public void setContentImages(String contentImages) {
		this.contentImages = contentImages;
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
	
	
}
