package com.fund.client;

import com.fund.client.model.EastFundModel;
import com.fund.client.model.EastRealFundModel;

import java.util.List;

public interface FundClient {

    /**
     * 最近20天的
     *
     * @param code
     * @return
     */
    List<EastFundModel> findFundHistory(String code);

    /**
     * 今天 到 开始时间
     *
     * @param code
     * @param sdate
     * @return
     */
    List<EastFundModel> findFundHistory(String code, String sdate);

    /**
     * 向前同步
     *
     * @param code
     * @param edate
     * @return
     */
    List<EastFundModel> findFundNextHistory(String code, String edate);

    /**
     * 查询基金实时数据
     *
     * @param code
     * @return
     */
    EastRealFundModel findFundReal(String code);
}
