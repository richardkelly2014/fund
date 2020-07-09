package com.fund.dal;

import com.fund.model.FundMyBuyModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiangfei on 2020/7/9.
 */
@Mapper
public interface FundMyBuyMapper {

    /**
     * 插入
     *
     * @param model
     * @return
     */
    int insert(FundMyBuyModel model);

    /**
     * 跟新
     *
     * @param model
     * @return
     */
    int update(FundMyBuyModel model);

    /**
     * select
     *
     * @param code
     * @return
     */
    FundMyBuyModel selectByCode(final @Param("code") String code);

    /**
     * 查询所有
     *
     * @return
     */
    List<FundMyBuyModel> selectAll();
}
