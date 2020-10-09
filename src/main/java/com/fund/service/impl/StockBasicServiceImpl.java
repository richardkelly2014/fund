package com.fund.service.impl;

import com.fund.client.StockClient;
import com.fund.dal.StockBasicMapper;
import com.fund.model.StockBasicModel;
import com.fund.service.StockBasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockBasicServiceImpl implements StockBasicService {

    @Autowired
    @Qualifier("tushareClient")
    private StockClient stockClient;

    @Autowired
    private StockBasicMapper stockBasicMapper;

    @Override
    public List<StockBasicModel> loadAllStock() {
        
        return stockBasicMapper.selectAll();
    }

    @Override
    public void firstSync() {

        List<List<String>> stocks = stockClient.loadAllStock();
        if (stocks != null && stocks.size() > 0) {

            stocks.stream().forEach(stock -> {

                String tsCode = stock.get(0);
                String symbol = stock.get(1);
                String name = stock.get(2);
                String area = stock.get(3);
                String industry = stock.get(4);
                String market = stock.get(5);
                String listDate = stock.get(6);

                int id = stockBasicMapper.insert(tsCode, symbol, name, area, industry, market, listDate);

                log.info("first insert {},{},{},{},{},{},{}={}",
                        tsCode, symbol, name, area, industry, market, listDate, id);

            });
        }

    }
}
