package com.along.outboundmanage.model;

public class PubParam {
	private Integer pageNum=1;//第几页
	private Integer pageSize=10;//每页几条
	private Integer areaId=0;//地区id
	private Integer taskId=0;//任务id
	private String  ids;//多个id逗号连接
	private Integer userId=0;//userId
	private Integer roleId=0;//userId
	@Override
	public String toString() {
		return "PubParam{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", areaId=" + areaId +
				'}';
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
}
