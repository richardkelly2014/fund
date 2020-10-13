package com.fund.service.impl;

import com.fund.dal.StockDailyAnalysisMapper;
import com.fund.model.StockDailyAnalysisModel;
import com.fund.service.StockDailyAnalysisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockDailyAnalysisServiceImpl implements StockDailyAnalysisService {

    @Autowired
    private StockDailyAnalysisMapper stockDailyAnalysisMapper;

    @Override
    public int create(String tsCode, String symbol, String tradeDate,
                      Integer changeType, Integer changeStop, Integer changeStopNum,
                      Integer lowDay, Integer highDay) {

        return stockDailyAnalysisMapper.insert(tsCode, symbol, tradeDate,
                changeType, changeStop, changeStopNum, lowDay, highDay);
    }

    @Override
    public StockDailyAnalysisModel loadByTradeDate(String tsCode, String tradeDate) {
        if (StringUtils.isNotBlank(tsCode) && StringUtils.isNotBlank(tradeDate)) {
            return stockDailyAnalysisMapper.selectOneByTradeDate(tsCode, tradeDate);
        } else {
            return null;
        }
    }
}
