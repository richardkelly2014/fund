package com.fund.client.model;

import lombok.Data;

import java.util.List;

@Data
public class TushareStockDailyModel extends TushareResponseModel {

    private Data data;

    @lombok.Data
    public static class Data{
        private List<String> fields;
        private List<List<String>> items;
    }
}
