package com.fund.dal;

import com.fund.model.Test2Model;
import com.fund.model.TestModel;
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
}
