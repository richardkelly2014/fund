package com.fund.service;

import com.fund.model.FundBuyRecordModel;

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

    int updateCurrent(int recordId,float currentMoney,float currentProfit);

    List<FundBuyRecordModel> queryAll();
}
