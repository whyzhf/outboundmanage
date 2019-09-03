package com.along.outboundmanage.model.Enclosure;

public class SelectJson {
	private String  key; //请求服务权限标识
	private String  id; //围栏id 数字
	private String  gid;//围栏全局id 数字&字母&-
	private String  name;//围栏名称 支持字母&数字&汉字，40个字符以内
	private Integer page_no;//当前页码 数字
	private Integer page_size;//每页数量 数字
	private Boolean enable;//围栏激活状态 布尔类型
	private String start_time;//按创建时间查询的起始时间 yyyy-MM-dd HH:mm:ss
	private String end_time;//按创建时间查询的结束时间  yyyy-MM-dd HH:mm:ss

	@Override
	public String toString() {
		return "SelectJson{" +
				"key='" + key + '\'' +
				", id='" + id + '\'' +
				", gid='" + gid + '\'' +
				", name='" + name + '\'' +
				", page_no=" + page_no +
				", page_size=" + page_size +
				", enable=" + enable +
				", start_time='" + start_time + '\'' +
				", end_time='" + end_time + '\'' +
				'}';
	}

	public SelectJson() {
	}

	private SelectJson(Builder builder) {
		setKey(builder.key);
		setId(builder.id);
		setGid(builder.gid);
		setName(builder.name);
		setPage_no(builder.page_no);
		setPage_size(builder.page_size);
		setEnable(builder.enable);
		setStart_time(builder.start_time);
		setEnd_time(builder.end_time);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPage_no() {
		return page_no;
	}

	public void setPage_no(Integer page_no) {
		this.page_no = page_no;
	}

	public Integer getPage_size() {
		return page_size;
	}

	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public static final class Builder {
		private String key;
		private String id;
		private String gid;
		private String name;
		private Integer page_no;
		private Integer page_size;
		private Boolean enable;
		private String start_time;
		private String end_time;

		public Builder() {
		}

		public Builder key(String key) {
			this.key = key;
			return this;
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder gid(String gid) {
			this.gid = gid;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder page_no(Integer page_no) {
			this.page_no = page_no;
			return this;
		}

		public Builder page_size(Integer page_size) {
			this.page_size = page_size;
			return this;
		}

		public Builder enable(Boolean enable) {
			this.enable = enable;
			return this;
		}

		public Builder start_time(String start_time) {
			this.start_time = start_time;
			return this;
		}

		public Builder end_time(String end_time) {
			this.end_time = end_time;
			return this;
		}

		public SelectJson build() {
			return new SelectJson(this);
		}
	}
}
