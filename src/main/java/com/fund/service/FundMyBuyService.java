package com.fund.service;

import com.fund.model.FundBuyRecordModel;
import com.fund.model.FundMyBuyModel;

import java.util.List;

/**
 * Created by jiangfei on 2020/7/9.
 */
public interface FundMyBuyService {

    /**
     * 创建
     *
     * @param model
     * @return
     */
    int createMyBuy(FundBuyRecordModel model);

    /**
     * 查询
     *
     * @return
     */
    List<FundMyBuyModel> queryAll();

}
