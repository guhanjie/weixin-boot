package com.guhanjie.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.amount
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private BigDecimal amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.tip
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private BigDecimal tip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.from_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private Integer fromId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.waypoints_ids
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private String waypointsIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.to_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private Integer toId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.distance
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private BigDecimal distance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.vehicle
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private Short vehicle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.status
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.user_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.contactor
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private String contactor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.phone
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.remark
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.start_time
     *
     * @mbggenerated Sat Sep 10 14:15:30 CST 2016
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.create_time
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.update_time
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    private Date updateTime;
    
    //------------------------- custom add -----------------------------
    private User user;
    private Position from;
    private Position to;
    private List<Position> waypoints;
    
    public User getUser() {
    	return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
    
    public Position getFrom() {
    	return from;
    }
    
    public void setFrom(Position from) {
    	this.from = from;
    }
    
    public Position getTo() {
    	return to;
    }
    
    public void setTo(Position to) {
    	this.to = to;
    }
    
    public List<Position> getWaypoints() {
    	return waypoints;
    }
    
    public void setWaypoints(List<Position> waypoints) {
    	this.waypoints = waypoints;
    }
    //--------------------------------------------------------------------

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.id
     *
     * @return the value of order.id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.id
     *
     * @param id the value for order.id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.amount
     *
     * @return the value of order.amount
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.amount
     *
     * @param amount the value for order.amount
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.tip
     *
     * @return the value of order.tip
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public BigDecimal getTip() {
        return tip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.tip
     *
     * @param tip the value for order.tip
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setTip(BigDecimal tip) {
        this.tip = tip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.from_id
     *
     * @return the value of order.from_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public Integer getFromId() {
        return fromId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.from_id
     *
     * @param fromId the value for order.from_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.waypoints_ids
     *
     * @return the value of order.waypoints_ids
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public String getWaypointsIds() {
        return waypointsIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.waypoints_ids
     *
     * @param waypointsIds the value for order.waypoints_ids
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setWaypointsIds(String waypointsIds) {
        this.waypointsIds = waypointsIds == null ? null : waypointsIds.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.to_id
     *
     * @return the value of order.to_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public Integer getToId() {
        return toId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.to_id
     *
     * @param toId the value for order.to_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setToId(Integer toId) {
        this.toId = toId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.distance
     *
     * @return the value of order.distance
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public BigDecimal getDistance() {
        return distance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.distance
     *
     * @param distance the value for order.distance
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.vehicle
     *
     * @return the value of order.vehicle
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public Short getVehicle() {
        return vehicle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.vehicle
     *
     * @param vehicle the value for order.vehicle
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setVehicle(Short vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.status
     *
     * @return the value of order.status
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.status
     *
     * @param status the value for order.status
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.user_id
     *
     * @return the value of order.user_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.user_id
     *
     * @param userId the value for order.user_id
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.contactor
     *
     * @return the value of order.contactor
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public String getContactor() {
        return contactor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.contactor
     *
     * @param contactor the value for order.contactor
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setContactor(String contactor) {
        this.contactor = contactor == null ? null : contactor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.phone
     *
     * @return the value of order.phone
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.phone
     *
     * @param phone the value for order.phone
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.remark
     *
     * @return the value of order.remark
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.remark
     *
     * @param remark the value for order.remark
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.start_time
     *
     * @return the value of order.start_time
     *
     * @mbggenerated Sat Sep 10 14:15:30 CST 2016
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.start_time
     *
     * @param startTime the value for order.start_time
     *
     * @mbggenerated Sat Sep 10 14:15:30 CST 2016
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.create_time
     *
     * @return the value of order.create_time
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.create_time
     *
     * @param createTime the value for order.create_time
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.update_time
     *
     * @return the value of order.update_time
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.update_time
     *
     * @param updateTime the value for order.update_time
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * Class Name:		VehicleEnum<br/>
     * Description:		[车型（1：小面车， 2：金杯车，3：全顺）]
     * @time				2016年9月1日 下午5:15:47
     * @author			GUHANJIE
     * @version			1.0.0 
     * @since 			JDK 1.7
     */
    public static enum VehicleEnum {
    	XIAOMIAN((short)1, "小面车型"),
    	JINBEI((short)2, "金杯车型"),
    	QUANSHUN((short)3, "全顺/依维轲");
    	
    	private short code;
    	private String desc;
    	
    	private VehicleEnum(short code, String desc) {
    		this.code = code;
    		this.desc = desc;
    	}
    	
    	public short code() {
    		return code;
    	}
    	
    	public String desc() {
    		return desc;
    	}
    }
    
    /**
     * Class Name:		StatusEnum<br/>
     * Description:		[订单状态，采用位表示法，第1-2位：是否下单成功，第3-4位：是否开始送货，第5-6位：是否支付完成]
     * @time				2016年9月1日 下午5:15:08
     * @author			GUHANJIE
     * @version			1.0.0 
     * @since 			JDK 1.7
     */
    public static enum StatusEnum {
    	//位表示法
    	NEW((short)0x01, "新建订单"),
    	SENDING((short)0x05, "开始送货"),
    	FINISH((short)0x19, "支付完成");
    	
    	private short code;
    	private String desc;
    	
    	private StatusEnum(short code, String desc) {
    		this.code = code;
    		this.desc = desc;
    	}
    	
    	public short code() {
    		return code;
    	}
    	
    	public String desc() {
    		return desc;
    	}
    }
}