package com.fund.dal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by jiangfei on 2020/7/29.
 */
@Mapper
public interface SsqModelMapper {

    /**
     * insert
     *
     * @param red1
     * @param red2
     * @param red3
     * @param red4
     * @param red5
     * @param red6
     * @param blue
     * @param issue
     * @return
     */
    int insert(@Param("red1") int red1,
               @Param("red2") int red2,
               @Param("red3") int red3,
               @Param("red4") int red4,
               @Param("red5") int red5,
               @Param("red6") int red6,
               @Param("blue") int blue,
               @Param("issue") int issue);

    Integer getLastIssue();

    Integer getFirstIssue();
}
