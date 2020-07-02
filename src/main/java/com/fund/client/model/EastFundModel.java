package com.fund.client.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by jiangfei on 2020/7/1.
 */
@Data
@Builder
public class EastFundModel {
    private String day;
    private int week;
    private float unitValue;
    private float grandValue;
    private int type;
    private float rate;
}
