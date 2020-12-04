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

    List<TestFundBasicModel> selectAllLimit(@Param("start") int start, @Param("end") int end);


    int updateFundBasic(@Param("fundCode") String fundCode,
                        @Param("fundTypeName") String fundTypeName,
                        @Param("investTypeName") String investTypeName,
                        @Param("riskName") String riskName,
                        @Param("companyName") String companyName);

    int updateFundBasicAndStatus(
            @Param("fundCode") String fundCode,
            @Param("fundTypeName") String fundTypeName,
            @Param("investTypeName") String investTypeName,
            @Param("riskName") String riskName,
            @Param("companyName") String companyName
    );

    //int updateFundBasicDate();

    int updateFundBasicStatus(@Param("fundCode") String fundCode,
                              @Param("status") int status);

    int batchInsertTheme(@Param("models") List<TestFundThemeModel> models);

    List<TestFundThemeModel> selectThemeAll();

    int batchInsertThemeFund(@Param("models") List<TestFundThemeFundModel> models);

    /**
     * 批量插入
     *
     * @param models
     * @return
     */
    int batchInsertFundDaily(@Param("models") List<TestFundDailyModel> models);

}
