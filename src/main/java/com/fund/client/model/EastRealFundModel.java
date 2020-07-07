package com.fund.client.model;

import lombok.Data;

@Data
public class EastRealFundModel {
    private String fundcode;
    private String name;
    //昨日净值
    private Float dwjz;
    //今日估算值
    private Float gsz;
    //今日估算涨幅
    private Float gszzl;
}
