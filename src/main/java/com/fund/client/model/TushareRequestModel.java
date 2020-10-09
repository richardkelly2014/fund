package com.fund.client.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TushareRequestModel {

    @JSONField(name = "api_name")
    private String apiName;

    private String token;

    private Object params;
}
