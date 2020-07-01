package com.fund.dal;

import com.fund.model.FundBaseModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/27.
 */
@Mapper
public interface FundBaseMapper {

    List<FundBaseModel> selectAll();

    int insert(FundBaseModel model);

    int update(FundBaseModel model);
}
