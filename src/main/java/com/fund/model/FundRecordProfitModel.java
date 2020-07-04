package com.fund.model;

import lombok.Data;

/**
 * Created by jiangfei on 2020/7/4.
 */
@Data
public class FundRecordProfitModel {
    private Integer id;

    private Integer buyRecordId;
    private String profitDay;

    private Integer profitWeek;
    private Integer profitType;

    private Float profitRate;
    private Float profitValue;
}
