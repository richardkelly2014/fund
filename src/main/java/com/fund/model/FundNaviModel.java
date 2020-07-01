package com.fund.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiangfei on 2020/6/26.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundNaviModel {
    private Integer id;
    private String name;
    private String code;
    private Integer type;

    private String view;
    private Integer show;
    private Integer status;
    private Integer parentId;
}
