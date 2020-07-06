package com.fund.service.impl;

import com.fund.client.model.EastFundModel;
import com.fund.dal.FundDayRateMapper;
import com.fund.model.FundDayRateModel;
import com.fund.service.FundDayRateService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/30.
 */
@Service
public class FundDayRateServiceImpl implements FundDayRateService {
    @Autowired
    private FundDayRateMapper fundDayRateMapper;

    @Override
    public List<FundDayRateModel> queryByBaseId(Integer baseId) {
        if (baseId != null && baseId > 0) {
            return fundDayRateMapper.selectByBaseId(baseId);
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public List<FundDayRateModel> queryByCodeAndDay(String code, String day) {

        return fundDayRateMapper.selectByCodeAndDay(code, day);
    }

    @Override
    public FundDayRateModel queryLastFundDayRate(Integer baseId) {
        if (baseId != null && baseId > 0) {
            return fundDayRateMapper.lastFundDayRate(baseId);
        }
        return null;
    }

    @Override
    public FundDayRateModel queryFirstFundDayRate(Integer baseId) {
        if (baseId != null && baseId > 0) {
            return fundDayRateMapper.firstFundDayRate(baseId);
        }
        return null;
    }

    @Override
    public int createFundDayRate(Integer baseId, String code, EastFundModel model) {
        String day = model.getDay();
        int count = fundDayRateMapper.countBaseIdByDay(baseId, day);
        if (count > 0) {
            return -1;
        } else {
            return fundDayRateMapper.insert(baseId, code, model);
        }
    }
}
