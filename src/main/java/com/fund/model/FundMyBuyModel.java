package com.fund.model;

import lombok.Data;

/**
 * Created by jiangfei on 2020/7/9.
 */
@Data
public class FundMyBuyModel {
    private Integer id;
    private String fundName;
    private String fundCode;
    //购买金额
    private Integer buyMoney;
    //成本净值
    private Integer buyUnit;
    //持有金额
    private Integer holdMoney;
    //持有收益
    private Integer holdProfit;
    //持有份额
    private Integer holdPortion;

    private Integer status;
}
