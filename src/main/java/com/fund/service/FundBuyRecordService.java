package com.fund.service;

import com.fund.model.FundBuyRecordModel;

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
}
