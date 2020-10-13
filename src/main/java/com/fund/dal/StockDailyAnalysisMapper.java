package com.fund.dal;

import com.fund.model.StockDailyAnalysisModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockDailyAnalysisMapper {

    /**
     * 插入
     *
     * @param tsCode
     * @param symbol
     * @param tradeDate
     * @param changeType
     * @param changeStop
     * @param changeStopNum
     * @param lowDay
     * @param highDay
     */
    int insert(@Param("tsCode") String tsCode,
               @Param("symbol") String symbol,
               @Param("tradeDate") String tradeDate,
               @Param("changeType") Integer changeType,
               @Param("changeStop") Integer changeStop,
               @Param("changeStopNum") Integer changeStopNum,
               @Param("lowDay") Integer lowDay,
               @Param("highDay") Integer highDay);


    /**
     * 根据交易日查询
     *
     * @param tsCode
     * @param tradeDate
     * @return
     */
    StockDailyAnalysisModel selectOneByTradeDate(@Param("tsCode") String tsCode,
                                                 @Param("tradeDate") String tradeDate);
}
