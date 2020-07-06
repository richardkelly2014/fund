package com.fund.dal;

import com.fund.model.FundBuyRecordModel;
import com.fund.model.FundSumRecordModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiangfei on 2020/7/4.
 */
@Mapper
public interface FundBuyRecordMapper {

    /**
     * insert
     *
     * @param model
     * @return
     */
    int insert(FundBuyRecordModel model);

    /**
     * update current
     *
     * @param id
     * @param currentMoney
     * @param currentProfitValue
     * @return
     */
    int updateCurrent(final @Param("id") Integer id,
                      final @Param("currentMoney") Float currentMoney,
                      final @Param("currentProfitValue") Float currentProfitValue);

    /**
     * update status
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(final @Param("id") Integer id, final @Param("status") Integer status);

    /**
     * select all
     *
     * @return
     */
    List<FundBuyRecordModel> select();

    /**
     * 查询 统计
     *
     * @return
     */
    FundSumRecordModel selectSumRecord();
}
