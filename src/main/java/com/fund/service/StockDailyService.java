package com.fund.service;


public interface StockDailyService {

    int insert(String tsCode,
               String symbol,
               String tradeDate,
               String open,
               String high,
               String low,
               String close,
               String preClose,
               String change,
               String pctChg,
               String vol,
               String amount);
}
