package com.guhanjie.mapper;

import org.apache.ibatis.annotations.Param;

import com.guhanjie.model.Position;

public interface PositionMapper {
	
	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    int insert(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    int insertSelective(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    Position selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    int updateByPrimaryKeySelective(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbggenerated Thu Sep 01 14:34:30 CST 2016
     */
    int updateByPrimaryKey(Position record);
    
    //------------------------- custom add -----------------------------
	Position selectByGeo(@Param("lng")String lng, @Param("lat")String lat);
    //--------------------------------------------------------------------
    
}