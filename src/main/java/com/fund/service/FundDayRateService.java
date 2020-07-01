package com.fund.service;

import com.fund.model.FundDayRateModel;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/30.
 */
public interface FundDayRateService {

    List<FundDayRateModel> queryByBaseId(final Integer baseId);
}
