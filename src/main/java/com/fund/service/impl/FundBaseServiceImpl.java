package com.fund.service.impl;

import com.fund.client.FundClient;
import com.fund.client.model.EastRealFundModel;
import com.fund.dal.FundBaseMapper;
import com.fund.model.FundBaseModel;
import com.fund.service.FundBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/27.
 */
@Service
public class FundBaseServiceImpl implements FundBaseService {
    @Autowired
    private FundBaseMapper fundBaseMapper;

    @Autowired
    private FundClient fundClient;

    @Override
    public List<FundBaseModel> query() {
        List<FundBaseModel> result = fundBaseMapper.selectAll();

        result.stream().forEach(model -> {
            String code = model.getCode();
            EastRealFundModel realFundModel = fundClient.findFundReal(code);
            if (realFundModel != null) {
                model.setDayRate(realFundModel.getGszzl());
            }
        });

        return result;
    }

    @Override
    public int createFundBase(FundBaseModel model) {
        if (model.getId() != null && model.getId() > 0) {
            return fundBaseMapper.update(model);
        } else {
            return fundBaseMapper.insert(model);
        }
    }
}
