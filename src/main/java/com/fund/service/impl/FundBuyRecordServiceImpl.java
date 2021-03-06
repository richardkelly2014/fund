package com.fund.service.impl;

import com.fund.dal.FundBuyRecordMapper;
import com.fund.model.FundBuyRecordModel;
import com.fund.model.FundSumRecordModel;
import com.fund.service.FundBuyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangfei on 2020/7/4.
 */
@Service
public class FundBuyRecordServiceImpl implements FundBuyRecordService {
    @Autowired
    private FundBuyRecordMapper fundBuyRecordMapper;

    @Override
    public int createBuyRecord(FundBuyRecordModel recordModel, Integer myBuyId, Integer type) {

        if (recordModel.getConfirmMoney() != null) {
            recordModel.setCurrentMoney(recordModel.getConfirmMoney());
            recordModel.setCurrentProfitValue(0);
        }
        recordModel.setMyBuyId(myBuyId);
        recordModel.setType(type);
        recordModel.setStatus(1);

        return fundBuyRecordMapper.insert(recordModel);
    }

    @Override
    public int updateCurrent(int recordId, float currentMoney, float currentProfit) {

        return fundBuyRecordMapper.updateCurrent(recordId, currentMoney, currentProfit);
    }

    @Override
    public List<FundBuyRecordModel> queryAll() {
        return fundBuyRecordMapper.select();
    }

    @Override
    public FundSumRecordModel querySumRecord() {

        return fundBuyRecordMapper.selectSumRecord();
    }
}
