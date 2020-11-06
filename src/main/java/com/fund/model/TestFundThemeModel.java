package com.fund.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiangfei on 2020/11/5.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestFundThemeModel {
    private Integer id;
    private String dataId;
    private String dataField;
    private String themeName;
}
