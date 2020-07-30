package com.fund.client;


import java.util.List;

public interface SsqClient {

    int current();

    List<Integer> get(int lssueNo);
}
