package com.fund.model;

import lombok.Data;

@Data
public class StockDailyModel {

    private Integer id;

    private String tsCode;
    private String symbol;

    private String tradeDate;

    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Float preClose;
    private Float change;
    private Float pctChange;
    private Float vol;
    private Float amount;

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer week;
}
