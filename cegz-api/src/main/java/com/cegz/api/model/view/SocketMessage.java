package com.cegz.api.model.view;
/**
 * 消息封装
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
public class SocketMessage {
	/**
	 * 消息头
	 */
	private String head;
	
	/**
	 * 消息体
	 */
	private Object body;

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
}
