package com.fund.dal;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created by jiangfei on 2020/7/29.
 */
@Mapper
public interface SsqModelMapper {

    int insert(int red1, int red2, int red3, int red4, int red5, int red6, int blue, int issue);

    int getLastIssue();

    int getFirstIssue();
}
