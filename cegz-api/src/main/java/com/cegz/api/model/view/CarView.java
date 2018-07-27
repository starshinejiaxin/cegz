package com.cegz.api.model.view;
/**
 * 车辆视图
 *
 * @author lijiaxin
 * @date 2018年7月26日
 */
public class CarView {
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 车牌号
	 */
	private String carNumber;
	
	/**
	 * 状态 0 审核中，1 完成，2 失败
	 */
	private int Status;
	
	/**
	 * 车辆类型，1 网约车，2 教练车
	 */
	private int carType;
	
	
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

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}
	
	
}
