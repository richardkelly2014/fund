package com.fund.dal;

import com.fund.model.FundRecordProfitModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by jiangfei on 2020/7/5.
 */
@Mapper
public interface FundRecordProfitMapper {

    /**
     * 插入
     *
     * @param model
     * @return
     */
    int insert(FundRecordProfitModel model);

    /**
     * count
     *
     * @param recordId
     * @param day
     * @return
     */
    int countByRecordAndDay(final @Param("recordId") Integer recordId, final @Param("day") String day);

    /**
     * 查询 最后条记录
     *
     * @param recordId
     * @return
     */
    FundRecordProfitModel selectLastRecord(final @Param("recordId") Integer recordId);
}
