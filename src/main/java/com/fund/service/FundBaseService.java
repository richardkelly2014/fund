package com.fund.service;

import com.fund.model.FundBaseModel;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/27.
 */
public interface FundBaseService {

    List<FundBaseModel> query();

    int createFundBase(FundBaseModel model);
}
