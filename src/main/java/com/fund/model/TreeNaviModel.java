package com.fund.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by jiangfei on 2020/6/26.
 */
@Data
@Builder
public class TreeNaviModel {

    private String name;
    private String code;


    @Override
    public String toString() {
        return this.name;
    }
}
