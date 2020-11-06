package com.fund.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiangfei on 2020/11/5.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TestFundThemeFundModel {
    private Integer id;
    private String dataId;
    private String themeName;

    private String fundCode;
    private String fundName;
}
