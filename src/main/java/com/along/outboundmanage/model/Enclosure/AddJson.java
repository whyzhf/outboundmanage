package com.along.outboundmanage.model.Enclosure;

/**
 *  {
 *      "name": "测试围栏名称",
 *      "center": "115.672126,38.817129",
 *      "radius": "1000",
 *      "enable": "true",
 *      "valid_time": "2017-05-19",
 *      "repeat": "Mon,Tues,Wed,Thur,Fri,Sat,Sun",
 *      "time": "00:00,11:59;13:00,20:59",
 *      "desc": "测试围栏描述",
 *      "alert_condition": "enter;leave"
 *  }
 */
public class AddJson {
	private String name;//围栏名称  字母&数字&汉字 必填
	private String center;//圆形围栏中心点 longitude,latitude 圆形围栏必填与points互斥
	private String radius;//圆形围栏半径 单位：米。范围0~5000。 圆形围栏必填与points互斥
	private String points;//多边形围栏坐标点  on1,lat1;lon2,lat2;lon3,lat3（3<=点个数<=5000）。多边形围栏外接圆半径最大为5000米。多边形围栏必填
	private Boolean enable;//围栏监控状态 布尔类型 可选
	private String valid_time ;//过期日期 围栏有效截止日期，过期后不对此围栏进行维护（围栏数据过期删除）；
								//不能设定创建围栏时间点之前的日期；
								//格式yyyy-MM-dd； 请设置2055年之前的日期
								//可选
	private String repeat;//一周内围栏监控日期的重复模式
						//星期缩写列表，用","或“;”隔开。
						//样例："Mon,Sat"
						//表示周一和周六有效。
						//星期简称如下（首字母大写）：
						//Mon,Tues,Wed,Thur,Fri,Sat,Sun
						//repeat和fixed_date不能同时缺省或同时为空，二者所指出的监控日期为“或”关系
						//可选，repeat和fixed_date不能同时缺省或同时为空，二者所指出的监控日期为“或”关系
	private String fixed_date ;//指定日期列表
								//格式"date1;date2;date3"； date格式"yyyy-MM-dd"；
								//最大个数180，不能设定过去日期；
								//repeat和fixed_date不能同时缺省或同时为空，二者所指出的监控日期为“或”关系；
								// 可选，repeat和fixed_date不能同时缺省或同时为空，二者所指出的监控日期为“或”关系
	private String time;//一天内围栏监控时段
						// 开始时间和结束时间定义一时间段，可设置多个时间段，时间段按照时间顺序排列，各时间段不可重叠；
						//拼接字符串格式如："startTime1,endTime1;startTime2,endTime2"；
						//最大个数24；
						//不可为空；
						//范围00:00-23:59；
						//时间段不可重叠；
						//时间段长度>15min；
	private String desc;//围栏描述信息 可选
	private String alert_condition;//配置触发围栏所需动作 触发动作分号分割enter;leave（进入、离开触发） 可选

	public AddJson() {
	}

	@Override
	public String toString() {
		return "AddJson{" +
				"name='" + name + '\'' +
				", center='" + center + '\'' +
				", radius='" + radius + '\'' +
				", points='" + points + '\'' +
				", enable=" + enable +
				", valid_time='" + valid_time + '\'' +
				", repeat='" + repeat + '\'' +
				", fixed_date='" + fixed_date + '\'' +
				", time='" + time + '\'' +
				", desc='" + desc + '\'' +
				", alert_condition='" + alert_condition + '\'' +
				'}';
	}

	private AddJson(Builder builder) {
		setName(builder.name);
		setCenter(builder.center);
		setRadius(builder.radius);
		setPoints(builder.points);
		setEnable(builder.enable);
		setValid_time(builder.valid_time);
		setRepeat(builder.repeat);
		setFixed_date(builder.fixed_date);
		setTime(builder.time);
		setDesc(builder.desc);
		setAlert_condition(builder.alert_condition);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getValid_time() {
		return valid_time;
	}

	public void setValid_time(String valid_time) {
		this.valid_time = valid_time;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getFixed_date() {
		return fixed_date;
	}

	public void setFixed_date(String fixed_date) {
		this.fixed_date = fixed_date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAlert_condition() {
		return alert_condition;
	}

	public void setAlert_condition(String alert_condition) {
		this.alert_condition = alert_condition;
	}

	public static final class Builder {
		private String name;
		private String center;
		private String radius;
		private String points;
		private Boolean enable;
		private String valid_time;
		private String repeat;
		private String fixed_date;
		private String time;
		private String desc;
		private String alert_condition;

		public Builder() {
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder center(String center) {
			this.center = center;
			return this;
		}

		public Builder radius(String radius) {
			this.radius = radius;
			return this;
		}

		public Builder points(String points) {
			this.points = points;
			return this;
		}

		public Builder enable(Boolean enable) {
			this.enable = enable;
			return this;
		}

		public Builder valid_time(String valid_time) {
			this.valid_time = valid_time;
			return this;
		}

		public Builder repeat(String repeat) {
			this.repeat = repeat;
			return this;
		}

		public Builder fixed_date(String fixed_date) {
			this.fixed_date = fixed_date;
			return this;
		}

		public Builder time(String time) {
			this.time = time;
			return this;
		}

		public Builder desc(String desc) {
			this.desc = desc;
			return this;
		}

		public Builder alert_condition(String alert_condition) {
			this.alert_condition = alert_condition;
			return this;
		}

		public AddJson build() {
			return new AddJson(this);
		}
	}
}
