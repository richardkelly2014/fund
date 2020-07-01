package com.fund.config.dal;

import java.util.function.Supplier;

/**
 * 数据源
 */
public enum DataSourceKey implements Supplier<String> {
    master,
    slave,;

    @Override
    public String get() {
        return this.name();
    }

}
