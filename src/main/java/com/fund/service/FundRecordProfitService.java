package com.fund.service;

import com.fund.model.FundRecordProfitModel;

/**
 * Created by jiangfei on 2020/7/5.
 */
public interface FundRecordProfitService {

    /**
     * 查询最近一次
     *
     * @param recordId
     * @return
     */
    FundRecordProfitModel queryLastRecord(final Integer recordId);

    int createRecordProfit(final int recordId, final String day, int week,
                           final int type, final float rate, final float value);
}
