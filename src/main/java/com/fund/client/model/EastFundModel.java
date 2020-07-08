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
    private int unitValue;
    private int grandValue;
    private int type;
    private int rate;
}
