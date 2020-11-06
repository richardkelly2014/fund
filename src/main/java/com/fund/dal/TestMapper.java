package com.fund.dal;

import com.fund.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jiangfei on 2020/10/28.
 */
@Mapper
public interface TestMapper {

    int insert(TestModel testModel);

    int batchInsert(@Param("models") List<Test2Model> models);

    List<TestFundBasicModel> selectAll();

    int batchInsertTheme(@Param("models")List<TestFundThemeModel> models);

    List<TestFundThemeModel> selectThemeAll();

    int batchInsertThemeFund(@Param("models") List<TestFundThemeFundModel> models);
}