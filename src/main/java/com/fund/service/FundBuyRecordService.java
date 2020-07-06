package com.fund.service;

import com.fund.model.FundBuyRecordModel;
import com.fund.model.FundSumRecordModel;

import java.util.List;

/**
 * Created by jiangfei on 2020/7/4.
 */
public interface FundBuyRecordService {

    /**
     * 创建
     *
     * @param recordModel
     * @return
     */
    int createBuyRecord(FundBuyRecordModel recordModel);

    /**
     * 更新
     *
     * @param recordId
     * @param currentMoney
     * @param currentProfit
     * @return
     */
    int updateCurrent(int recordId, float currentMoney, float currentProfit);

    /**
     * 查询
     *
     * @return
     */
    List<FundBuyRecordModel> queryAll();

    /**
     * 查询统计
     *
     * @return
     */
    FundSumRecordModel querySumRecord();
}
