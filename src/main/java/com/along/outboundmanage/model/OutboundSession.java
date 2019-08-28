package com.along.outboundmanage.model;

public class OutboundSession {
	//用户信息
	private int userId;
	private String userName;
	private String trueName;
	private String card;
	//用户地域信息
	private int areaId;
	private String areaName;
	private int parId;
	private int type;
	private int level;
	//用户角色信息
	private int roleId;
	private String roleName;



	public OutboundSession() {
	}

	private OutboundSession(Builder builder) {
		setUserId(builder.userId);
		setUserName(builder.userName);
		setTrueName(builder.trueName);
		setCard(builder.card);
		setAreaId(builder.areaId);
		setAreaName(builder.areaName);
		setParId(builder.parId);
		setType(builder.type);
		setLevel(builder.level);
		setRoleId(builder.roleId);
		setRoleName(builder.roleName);
	}

	@Override
	public String toString() {
		return "OutboundSession{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", trueName='" + trueName + '\'' +
				", card='" + card + '\'' +
				", areaId=" + areaId +
				", areaName='" + areaName + '\'' +
				", parId=" + parId +
				", type=" + type +
				", level=" + level +
				", roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				'}';
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getUserid() {
		return userId;
	}

	public void setUserid(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getParId() {
		return parId;
	}

	public void setParId(int parId) {
		this.parId = parId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public static final class Builder {
		private int userId;
		private String userName;
		private String trueName;
		private String card;
		private int areaId;
		private String areaName;
		private int parId;
		private int type;
		private int level;
		private int roleId;
		private String roleName;

		public Builder() {
		}

		public Builder userId(int userId) {
			this.userId = userId;
			return this;
		}

		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder trueName(String trueName) {
			this.trueName = trueName;
			return this;
		}

		public Builder card(String card) {
			this.card = card;
			return this;
		}

		public Builder areaId(int areaId) {
			this.areaId = areaId;
			return this;
		}

		public Builder areaName(String areaName) {
			this.areaName = areaName;
			return this;
		}

		public Builder parId(int parId) {
			this.parId = parId;
			return this;
		}

		public Builder type(int type) {
			this.type = type;
			return this;
		}

		public Builder level(int level) {
			this.level = level;
			return this;
		}

		public Builder roleId(int roleId) {
			this.roleId = roleId;
			return this;
		}

		public Builder roleName(String roleName) {
			this.roleName = roleName;
			return this;
		}

		public OutboundSession build() {
			return new OutboundSession(this);
		}
	}
}
