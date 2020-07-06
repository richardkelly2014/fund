package com.fund.service;

import com.fund.client.model.EastFundModel;
import com.fund.model.FundDayRateModel;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/30.
 */
public interface FundDayRateService {

    List<FundDayRateModel> queryByBaseId(final Integer baseId);

    List<FundDayRateModel> queryByCodeAndDay(final String code, final String day);

    FundDayRateModel queryLastFundDayRate(final Integer baseId);

    /**
     * first
     *
     * @param baseId
     * @return
     */
    FundDayRateModel queryFirstFundDayRate(final Integer baseId);

    /**
     * 创建
     *
     * @param baseId
     * @param code
     * @param model
     * @return
     */
    int createFundDayRate(final Integer baseId, final String code, final EastFundModel model);
}
