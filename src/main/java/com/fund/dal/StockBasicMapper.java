package com.fund.dal;

import com.fund.model.StockBasicModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockBasicMapper {

    /**
     * 插入
     * @param tsCode
     * @param symbol
     * @param name
     * @param area
     * @param industry
     * @param market
     * @param listDate
     * @return
     */
    int insert(@Param("tsCode") String tsCode,
               @Param("symbol") String symbol,
               @Param("name") String name,
               @Param("area") String area,
               @Param("industry") String industry,
               @Param("market") String market,
               @Param("listDate") String listDate);


    List<StockBasicModel> selectAll();

}