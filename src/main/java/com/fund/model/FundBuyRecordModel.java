package com.fund.model;

import lombok.Data;

/**
 * Created by jiangfei on 2020/7/4.
 */
@Data
public class FundBuyRecordModel {

    private Integer id;
    private String fundName;
    private String fundCode;

    private Float buyMoney;
    private String buyDateTime;

    private Float currentMoney;
    private Float currentProfitValue;

    private Float confirmMoney;
    private Float confirmPortion;
    private Float confirmUnit;
    private Float confirmDay;

    private Integer status;
}
