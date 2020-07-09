package com.fund.service.impl;

import com.fund.dal.FundMyBuyMapper;
import com.fund.model.FundBuyRecordModel;
import com.fund.model.FundMyBuyModel;
import com.fund.service.FundBuyRecordService;
import com.fund.service.FundMyBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangfei on 2020/7/9.
 */
@Service
public class FundMyBuyServiceImpl implements FundMyBuyService {
    @Autowired
    private FundMyBuyMapper fundMyBuyMapper;
    @Autowired
    private FundBuyRecordService fundBuyRecordService;

    @Override
    public int createMyBuy(FundBuyRecordModel model) {
        String code = model.getFundCode();
        FundMyBuyModel myModel = fundMyBuyMapper.selectByCode(code);
        if (myModel == null) {
            myModel = new FundMyBuyModel();
            myModel.setFundName(model.getFundName());
            myModel.setFundCode(model.getFundCode());

            myModel.setBuyMoney(model.getBuyMoney());
            myModel.setBuyUnit(model.getConfirmUnit());

            //持有金额
            myModel.setHoldMoney(model.getConfirmMoney());
            myModel.setHoldProfit(0);
            //份额
            myModel.setHoldPortion(model.getConfirmPortion());

            fundMyBuyMapper.insert(myModel);

            fundBuyRecordService.createBuyRecord(model, myModel.getId(), 1);

            return myModel.getId();
        } else {

        }

        return 0;
    }

    @Override
    public List<FundMyBuyModel> queryAll() {
        return null;
    }
}
