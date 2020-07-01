package com.fund.service;

import com.fund.model.FundNaviModel;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/26.
 */
public interface FundNaviService {

    List<FundNaviModel> selectNavi(final Integer parentId);
}
