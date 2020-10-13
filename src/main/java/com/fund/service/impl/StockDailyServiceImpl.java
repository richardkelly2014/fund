package com.fund.service.impl;

import com.fund.dal.StockDailyMapper;
import com.fund.model.StockDailyModel;
import com.fund.model.help.QueryStockDailyParams;
import com.fund.service.StockDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDailyServiceImpl implements StockDailyService {

    @Autowired
    private StockDailyMapper stockDailyMapper;

    @Override
    public int insert(String tsCode, String symbol, String tradeDate, String open,
                      String high, String low, String close, String preClose, String change,
                      String pctChg, String vol, String amount,
                      int year, int month, int day, int week) {

        return stockDailyMapper.insert(tsCode, symbol, tradeDate, open, high, low, close, preClose,
                change, pctChg, vol, amount, year, month, day, week);
    }

    @Override
    public int changeDate(Integer id, Integer year, Integer month, Integer day, Integer week) {

        return stockDailyMapper.updateYYYYMMDDWW(id, year, month, day, week);
    }

    @Override
    public List<StockDailyModel> loadAllDailyData() {

        return stockDailyMapper.selectAll();
    }

    @Override
    public List<StockDailyModel> loadDailyByCode(String tsCode, boolean sort) {

        return loadDailyByCode(tsCode, null, null, sort);

    }

    @Override
    public List<StockDailyModel> loadDailyByCode(String tsCode, String start, String end, boolean sort) {

        final QueryStockDailyParams params = QueryStockDailyParams.builder()
                .tsCode(tsCode)
                .startDate(start)
                .endDate(end)
                .build();
        int sortVal = 1;
        if (sort) {
            sortVal = 2;
        }

        return stockDailyMapper.selectByParams(params, sortVal, 0, 0);
    }
}
