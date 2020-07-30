package com.fund.service.impl;

import com.fund.client.SsqClient;
import com.fund.dal.SsqModelMapper;
import com.fund.service.SsqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SsqServiceImpl implements SsqService {

    @Autowired
    private SsqModelMapper ssqModelMapper;

    @Autowired
    private SsqClient ssqClient;

    @Override
    public int create(int issue, int blue, int red1, int red2, int red3, int red4, int red5, int red6) {
        return ssqModelMapper.insert(red1, red2, red3, red4, red5, red6, blue, issue);
    }

    @Override
    public int lastIssue() {
        Integer value = ssqModelMapper.getLastIssue();
        if (value != null) {
            return value;
        } else {
            return 0;
        }
    }

    @Override
    public int firstIssue() {
        Integer value = ssqModelMapper.getFirstIssue();
        if (value != null) {
            return value;
        } else {
            return 0;
        }
    }

    @Override
    public void sync() {
        int current = ssqClient.current();
        if (current > 0) {
            int last = lastIssue();
            last = last == 0 ? current - 10 : last;

            while (last <= current) {
                log.info("get {},{}", last, ssqClient.get(last));
            }
        }


    }
}
