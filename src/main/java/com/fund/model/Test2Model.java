package com.fund.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiangfei on 2020/10/28.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test2Model {
    private Integer id;
    private String industryName;
    private String industryCode;

    private String stockName;
    private String stockCode;

    private Integer industryType;
}
