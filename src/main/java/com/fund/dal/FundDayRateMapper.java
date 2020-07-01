package com.fund.dal;

import com.fund.client.model.EastFundModel;
import com.fund.model.FundDayRateModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/30.
 */
@Mapper
public interface FundDayRateMapper {

    /**
     * select
     *
     * @param baseId
     * @return
     */
    List<FundDayRateModel> selectByBaseId(final @Param("baseId") Integer baseId);

    /**
     * 最近一次数据
     *
     * @param baseId
     * @return
     */
    FundDayRateModel lastFundDayRate(final @Param("baseId") Integer baseId);

    /**
     * count
     *
     * @param baseId
     * @param day
     * @return
     */
    int countBaseIdByDay(final @Param("baseId") Integer baseId, final @Param("day") String day);

    /**
     * 插入
     *
     * @param eastFundModel
     */
    int insert(final @Param("baseId") int baseId, final @Param("code") String code,
               final @Param("model") EastFundModel eastFundModel);
}
