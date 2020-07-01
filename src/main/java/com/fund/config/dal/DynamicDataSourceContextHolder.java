package com.fund.config.dal;

import lombok.extern.slf4j.Slf4j;

/**
 * 存储当前线程中使用的数据源标识
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<Object> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.master);

    public static void useMasterDataSource() {

        CONTEXT_HOLDER.set(DataSourceKey.master);
    }

    public static String getDataSourceKey() {

        return CONTEXT_HOLDER.get().toString();
    }

    public static void clearDataSourceKey() {

        CONTEXT_HOLDER.remove();
    }

}
