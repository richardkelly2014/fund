package com.fund.model;

import lombok.Data;

@Data
public class StockDailyAnalysisModel {

    private Integer id;

    private String tsCode;
    private String symbol;

    private String tradeDate;

    private Integer changeType; //1up 2 flat 3 down
    private Integer changeStop; //0.没有(涨/跌)停过,1有(涨/跌)停过但没封住,2(涨/跌)停
    private Integer changeStopNum; //连续(涨/跌)停次数

    private Integer lowDay;     //最低点天数
    private Integer highDay;    //最高点天数

}
