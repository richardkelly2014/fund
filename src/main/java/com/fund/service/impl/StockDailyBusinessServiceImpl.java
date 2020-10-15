package com.fund.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.fund.client.StockClient;
import com.fund.model.StockDailyAnalysisModel;
import com.fund.model.StockDailyModel;
import com.fund.service.StockDailyAnalysisService;
import com.fund.service.StockDailyBusinessService;
import com.fund.service.StockDailyService;
import com.fund.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDailyBusinessServiceImpl implements StockDailyBusinessService {

    private final static double h99 = (1 + 0.0991);
    private final static double h10 = (1 + 0.1);
    private final static double h20 = (1 + 0.2);

    private final static double l99 = (1 - 0.0991);
    private final static double l10 = (1 - 0.1);
    private final static double l20 = (1 - 0.2);

    @Autowired
    private StockClient stockClient;
    @Autowired
    private StockDailyService stockDailyService;
    @Autowired
    private StockDailyAnalysisService stockDailyAnalysisService;

    @Override
    public void syncOneDate(String tsCode, String tradeDate) {

        List<List<String>> items = stockClient.loadStockByDay(tsCode, tradeDate, null, null);
        if (items != null && items.size() > 0) {
            process(items);
        }
    }

    @Override
    public void syncDate(String tsCode, String startDate, String endDate) {
        List<List<String>> items = stockClient.loadStockByDay(tsCode, null, startDate, endDate);
        if (items != null && items.size() > 0) {
            process(items);
        }
    }

    /**
     * 分析
     *
     * @param tsCode    tsCode
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    @Override
    public void analysis(String tsCode, String startDate, String endDate) {
        //1.找出开始时间前一条日行情，和日行情分析
        StockDailyModel preDailyModel = stockDailyService.loadDailyLastByTradeDate(tsCode, startDate);
        StockDailyAnalysisModel preDailyAnalysisModel = null;
        if (preDailyModel != null) {
            preDailyAnalysisModel = stockDailyAnalysisService.loadByTradeDate(tsCode, preDailyModel.getTradeDate());
        }

        //2.查询【start，end】日行情数据
        List<StockDailyModel> dailys = stockDailyService.loadDailyByCode(tsCode, startDate, endDate, false);

        //3.处理
        if (dailys != null && dailys.size() > 0) {

            //初始化 pre 上一次日行情分析
            int index = 0;
            if (preDailyAnalysisModel == null) {
                //上一条日行情分析，不存在，初始化第一条
                preDailyModel = dailys.get(0);

                preDailyAnalysisModel = new StockDailyAnalysisModel();

                initPreDailyAnalysis(preDailyModel, preDailyAnalysisModel);

                //保存第一条日行情分析
                stockDailyAnalysisService.create(preDailyAnalysisModel);
                index = index + 1;
            }

            for (int size = dailys.size(); index < size; index++) {

                //1处理涨跌
                StockDailyModel currentDailyModel = dailys.get(index);
                StockDailyAnalysisModel currentDailyAnalysisModel = new StockDailyAnalysisModel();
                initPreDailyAnalysis(currentDailyModel, currentDailyAnalysisModel);

                if (currentDailyAnalysisModel.getChangeStop() == 1) {
                    //如果本次是涨跌停
                    //上一次涨跌停次数+本次
                    int changeStopNum = currentDailyAnalysisModel.getChangeStopNum() + preDailyAnalysisModel.getChangeStopNum();
                    currentDailyAnalysisModel.setChangeStopNum(changeStopNum);
                }

                float preLow = preDailyModel.getLow();
                float preHigh = preDailyModel.getHigh();

                float currentLow = currentDailyModel.getLow();
                float currentHigh = currentDailyModel.getHigh();

                if (currentLow < preLow) {
                    //地点比上次更低
                    currentDailyAnalysisModel.setLowDay(preDailyAnalysisModel.getLowDay() + 1);
                }
                if (currentHigh > preHigh) {
                    //高点比上次高
                    currentDailyAnalysisModel.setHighDay(preDailyAnalysisModel.getHighDay() + 1);
                }

                //保存
                stockDailyAnalysisService.create(currentDailyAnalysisModel);

                //替换上次
                preDailyAnalysisModel = currentDailyAnalysisModel;
                preDailyModel = currentDailyModel;

            }
        }

    }

    /**
     * 初始化 日行情分析
     *
     * @param dailyModel
     * @param dailyAnalysisModel
     */
    private void initPreDailyAnalysis(StockDailyModel dailyModel, StockDailyAnalysisModel dailyAnalysisModel) {

        dailyAnalysisModel.setTsCode(dailyModel.getTsCode());
        dailyAnalysisModel.setSymbol(dailyModel.getSymbol());
        dailyAnalysisModel.setTradeDate(dailyModel.getTradeDate());

        float open = dailyModel.getOpen();
        //今日收盘
        float close = dailyModel.getClose();
        //今日最高
        float high = dailyModel.getHigh();
        //今日最低
        float low = dailyModel.getLow();

        // 昨收盘
        float preClose = dailyModel.getPreClose();

        float reachUp = NumberUtil.round(NumberUtil.mul(preClose, h99), 2).floatValue();
        //今日涨停价
        float up = NumberUtil.round(NumberUtil.mul(preClose, h10), 2).floatValue();

        float reachDown = NumberUtil.round(NumberUtil.mul(preClose, l99), 2).floatValue();
        //今日跌停价
        float down = NumberUtil.round(NumberUtil.mul(preClose, l10), 2).floatValue();


        //根据涨幅判断，是涨/跌/平
        if (dailyModel.getChange() > 0) {
            //涨
            dailyAnalysisModel.setChangeType(1);

            if (high - reachUp >= 0) {
                //触达
                dailyAnalysisModel.setChangeStopReach(1);
            } else {
                dailyAnalysisModel.setChangeStopReach(0);
            }

            if (close - up == 0) {
                //涨停
                dailyAnalysisModel.setChangeStop(1);
                dailyAnalysisModel.setChangeStopNum(1);
            } else {
                //无
                dailyAnalysisModel.setChangeStop(0);
                dailyAnalysisModel.setChangeStopNum(0);
            }
        } else if (dailyModel.getChange() < 0) {
            //跌
            dailyAnalysisModel.setChangeType(3);

            if (low - reachDown <= 0) {
                //触达
                dailyAnalysisModel.setChangeStopReach(1);
            } else {
                dailyAnalysisModel.setChangeStopReach(0);
            }

            if (close - down == 0) {
                //跌停
                dailyAnalysisModel.setChangeStop(1);
                dailyAnalysisModel.setChangeStopNum(1);
            } else {
                dailyAnalysisModel.setChangeStopNum(0);
                dailyAnalysisModel.setChangeStop(0);
            }

        } else {
            //平
            dailyAnalysisModel.setChangeType(2);

            dailyAnalysisModel.setChangeStop(0);
            dailyAnalysisModel.setChangeStopReach(0);
            dailyAnalysisModel.setChangeStopNum(0);

        }

        float star = NumberUtil.round(NumberUtil.div(NumberUtil.sub(open, close), close), 4).floatValue();
        dailyAnalysisModel.setCrossStar(star);

        dailyAnalysisModel.setLowDay(0);
        dailyAnalysisModel.setHighDay(0);

    }


    private void process(List<List<String>> items) {
        items.stream().forEach(item -> {

            String tsCode = item.get(0);
            String symbol = StringUtils.split(tsCode, ".")[0];
            String tradeDate = item.get(1);
            String open = item.get(2);
            String high = item.get(3);
            String low = item.get(4);
            String close = item.get(5);
            String preClose = item.get(6);
            String change = item.get(7);
            String pctChange = item.get(8);

            String vol = item.get(9);
            String amount = item.get(10);

            int[] result = DateTimeUtil.getYearMonthDayWeek(tradeDate, "yyyyMMdd");
            int year = result[0];
            int month = result[1];
            int day = result[2];
            int week = result[3];

            stockDailyService.insert(tsCode, symbol, tradeDate, open, high, low, close, preClose, change, pctChange,
                    vol, amount, year, month, day, week);
        });
    }

}
