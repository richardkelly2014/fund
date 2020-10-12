package com.fund.dal;

import com.fund.model.StockDailyModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockDailyMapper {

    /**
     * 插入
     *
     * @param tsCode
     * @param symbol
     * @param tradeDate
     * @param open
     * @param high
     * @param low
     * @param close
     * @param preClose
     * @param change
     * @param pctChg
     * @param vol
     * @param amount
     * @return
     */
    int insert(@Param("tsCode") String tsCode,
               @Param("symbol") String symbol,
               @Param("tradeDate") String tradeDate,
               @Param("open") String open,
               @Param("high") String high,
               @Param("low") String low,
               @Param("close") String close,
               @Param("preClose") String preClose,
               @Param("change") String change,
               @Param("pctChg") String pctChg,
               @Param("vol") String vol,
               @Param("amount") String amount);

    /**
     * update 日期
     *
     * @param id
     * @param year
     * @param month
     * @param day
     * @param week
     * @return
     */
    int updateYYYYMMDDWW(@Param("id") Integer id,
                         @Param("year") Integer year,
                         @Param("month") Integer month,
                         @Param("day") Integer day,
                         @Param("week") Integer week);


    List<StockDailyModel> selectAll();



}
