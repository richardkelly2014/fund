package com.fund.model;

import lombok.Data;

@Data
public class StockBasicModel {
    private Integer id;
    private String tsCode;
    private String symbol;
    private String name;

    private String area;
    private String industry;
    private String market;
    private String listDate;
}
