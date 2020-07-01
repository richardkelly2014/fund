package com.fund.service.impl;

import com.fund.dal.FundNaviMapper;
import com.fund.model.FundNaviModel;
import com.fund.service.FundNaviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/26.
 */
@Service
public class FundNaviServiceImpl implements FundNaviService {
    @Autowired
    private FundNaviMapper fundNaviMapper;

    @Override
    public List<FundNaviModel> selectNavi(Integer parentId) {
        int pId = 0;
        if (parentId != null) {
            pId = parentId;
        }
        return fundNaviMapper.selectByParentId(pId);
    }
}
