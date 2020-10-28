package com.fund.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiangfei on 2020/10/28.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestModel {
    private Integer id;
    private String industryName;
    private String industryCode;
    private Integer source;
    private Integer type;
}
