package com.fund.service.impl;

import com.fund.dal.FundRecordProfitMapper;
import com.fund.model.FundRecordProfitModel;
import com.fund.service.FundRecordProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangfei on 2020/7/5.
 */
@Service
public class FundRecordProfitServiceImpl implements FundRecordProfitService {
    @Autowired
    private FundRecordProfitMapper fundRecordProfitMapper;

    @Override
    public FundRecordProfitModel queryLastRecord(Integer recordId) {
        if (recordId != null && recordId > 0) {
            return fundRecordProfitMapper.selectLastRecord(recordId);
        }
        return null;
    }

    @Override
    public int createRecordProfit(int recordId, String day, int week, int type, float rate, float value) {
        FundRecordProfitModel model = new FundRecordProfitModel();
        model.setBuyRecordId(recordId);
        model.setProfitDay(day);
        model.setProfitWeek(week);
        model.setProfitType(type);
        model.setProfitRate(rate);
        model.setProfitValue(value);
        return fundRecordProfitMapper.insert(model);
    }
}
