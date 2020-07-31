package com.fund.service;

import java.util.List;

public interface SsqService {

    int create(int issue, int blue, int red1, int red2, int red3, int red4, int red5, int red6);

    int lastIssue();

    int firstIssue();

    List<Integer> getBlues(int start, int limit);

    void sync();
}
