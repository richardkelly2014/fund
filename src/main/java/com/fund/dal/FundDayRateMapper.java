package com.fund.dal;

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
}
