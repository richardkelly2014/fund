package com.fund.service;

import com.fund.model.StockBasicModel;

import java.util.List;

public interface StockBasicService {

    void firstSync();

    List<StockBasicModel> loadAllStock();
}
