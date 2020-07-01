package com.fund.dal;

import com.fund.model.FundNaviModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/26.
 */
@Mapper
public interface FundNaviMapper {
    /**
     * 查询列表
     *
     * @param parentId
     * @return
     */
    List<FundNaviModel> selectByParentId(final @Param("parentId") Integer parentId);
}
