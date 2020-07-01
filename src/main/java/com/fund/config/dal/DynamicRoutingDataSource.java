package com.fund.config.dal;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {


    /**
     * 实现该方法即可实现动态切换数据源
     *
     * @return 数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

}
