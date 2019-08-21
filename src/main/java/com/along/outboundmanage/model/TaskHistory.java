package com.along.outboundmanage.model;

public class TaskHistory {

	private int userId;
	private int taskId;
	private int areaId;
	public String firjson;
	public String secjson;

	@Override
	public String toString() {
		return "TaskHistory{" +
				"userId=" + userId +
				", taskId=" + taskId +
				", areaId=" + areaId +
				", firjson='" + firjson + '\'' +
				", secjson='" + secjson + '\'' +
				'}';
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getFirjson() {
		return firjson;
	}

	public void setFirjson(String firjson) {
		this.firjson = firjson;
	}

	public String getSecjson() {
		return secjson;
	}

	public void setSecjson(String secjson) {
		this.secjson = secjson;
	}
}
