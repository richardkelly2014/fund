package com.fund.service.impl;

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
}
