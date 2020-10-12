package com.fund.model.help;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryStockDailyParams {

    private String tsCode;

    private String startDate;
    private String endDate;
}
