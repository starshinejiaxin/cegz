package com.cegz.api.model.view;
/**
 * 发布广告实体类
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
public class PublishAdverView {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 广告类型 1 图片广告，2 文字广告
	 */
	private Long type;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 标题图片
	 */
	private String titileImg;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 图片数组
	 */
	private String [] contentImg;
	
	/**
	 * 结束时间时间戳
	 */
	private Long timestamp;
	
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long long1) {
		this.id = long1;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitileImg() {
		return titileImg;
	}
	public void setTitileImg(String titileImg) {
		this.titileImg = titileImg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getContentImg() {
		return contentImg;
	}
	public void setContentImg(String[] contentImg) {
		this.contentImg = contentImg;
	}
	
}
