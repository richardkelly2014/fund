package com.fund.service.impl;

import com.fund.dal.FundBaseMapper;
import com.fund.model.FundBaseModel;
import com.fund.service.FundBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/27.
 */
@Service
public class FundBaseServiceImpl implements FundBaseService {
    @Autowired
    private FundBaseMapper fundBaseMapper;

    @Override
    public List<FundBaseModel> query() {
        return fundBaseMapper.selectAll();
    }

    @Override
    public int createFundBase(FundBaseModel model) {
        if (model.getId() != null && model.getId() > 0) {
            return fundBaseMapper.update(model);
        } else {
            return fundBaseMapper.insert(model);
        }
    }
}
