package com.fund.service.impl;

import com.fund.service.BlueSsqBusinessService;
import com.fund.service.SsqService;
import com.fund.ssq.BlueService;
import com.fund.ssq.BlueTransModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BlueSsqBusinessServiceImpl implements BlueSsqBusinessService {

    @Autowired
    private SsqService ssqService;

    @Autowired
    private BlueService twoBlueServiceImpl;

    @Autowired
    private BlueService fourBlueServiceImpl;

    @Autowired
    private BlueService eightBlueServiceImpl;

    @Override
    public void learn() {
        List<Integer> yuxianblue = ssqService.getBlues(10, 40);
        List<Integer> blue = ssqService.getBlues(50, 100);

        Collections.reverse(blue);
        Collections.reverse(yuxianblue);

        List<String> probabilityList = Lists.newArrayList();


        for (int k = 0; k < yuxianblue.size(); k++) {

            int b = yuxianblue.get(k);
            BlueTransModel two = twoBlueServiceImpl.transfor(blue);
            BlueTransModel four = fourBlueServiceImpl.transfor(blue);
            BlueTransModel eight = eightBlueServiceImpl.transfor(blue);

            String value = "";
            int twoIndex = twoBlueServiceImpl.transforIndex(b);
            int fourIndex = fourBlueServiceImpl.transforIndex(b);
            int eightIndex = eightBlueServiceImpl.transforIndex(b);

            int twoRowIndex = two.getLast() - 1;
            int fourRowIndex = four.getLast() - 1;
            int eightRowIndex = eight.getLast() - 1;

            if (twoIndex == 0) {
                double t1 = two.getProbability()[twoRowIndex][twoIndex];
                double t2 = two.getProbability()[twoRowIndex][twoIndex + 1];
                if (t1 >= t2) {
                    value += "1";
                } else {
                    value += "0";
                }
            } else {
                double t1 = two.getProbability()[twoRowIndex][twoIndex];
                double t2 = two.getProbability()[twoRowIndex][twoIndex - 1];
                if (t1 >= t2) {
                    value += "1";
                } else {
                    value += "0";
                }
            }

            if (fourIndex == 0 || fourIndex == 2) {
                double t1 = four.getProbability()[fourRowIndex][fourIndex];
                double t2 = four.getProbability()[fourRowIndex][fourIndex + 1];
                if (t1 >= t2) {
                    value += "1";
                } else {
                    value += "0";
                }
            } else {
                double t1 = four.getProbability()[fourRowIndex][fourIndex];
                double t2 = four.getProbability()[fourRowIndex][fourIndex - 1];
                if (t1 >= t2) {
                    value += "1";
                } else {
                    value += "0";
                }
            }

            if (eightIndex == 0 || eightIndex == 2 || eightIndex == 4 || eightIndex == 6) {
                double t1 = eight.getProbability()[eightRowIndex][eightIndex];
                double t2 = eight.getProbability()[eightRowIndex][eightIndex + 1];
                if (t1 >= t2) {
                    value += "1";
                } else {
                    value += "0";
                }
            } else {
                double t1 = eight.getProbability()[eightRowIndex][eightIndex];
                double t2 = eight.getProbability()[eightRowIndex][eightIndex - 1];
                if (t1 >= t2) {
                    value += "1";
                } else {
                    value += "0";
                }
            }


            log.info("{},{},{},{},{}", b, (twoRowIndex + 1) + ":" + twoIndex,
                    (fourRowIndex + 1) + ":" + fourIndex,
                    (eightRowIndex + 1) + ":" + eightIndex, value);

            probabilityList.add(value);

            blue.add(b);
        }
    }
}
