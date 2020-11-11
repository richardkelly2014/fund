package com.fund.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestFundDailyModel {
    private Integer id;
    private String fundCode;

    private Integer unitValue;
    private Integer grandValue;

    private Integer rateType;
    private Integer rate;

    private String day;
    private Integer week;
}
