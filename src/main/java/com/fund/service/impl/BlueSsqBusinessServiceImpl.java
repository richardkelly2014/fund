package com.fund.service.impl;

import com.fund.service.BlueSsqBusinessService;
import com.fund.service.SsqService;
import com.fund.ssq.BlueProbabilityService;
import com.fund.ssq.BlueService;
import com.fund.ssq.BlueTransModel;
import com.fund.ssq.util.Logistic;
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
    @Autowired
    private BlueService six10BlueServiceImpl;


    @Autowired
    private BlueProbabilityService blueProbabilityService;
    @Autowired
    private BlueService jiOuBlueServiceImpl;

    @Override
    public void learn1() {
        List<Integer> blue = ssqService.getBlues(0, 200);
        Collections.reverse(blue);

        BlueTransModel two = twoBlueServiceImpl.transfor(blue);
        BlueTransModel four = fourBlueServiceImpl.transfor(blue);
        BlueTransModel eight = eightBlueServiceImpl.transfor(blue);
        BlueTransModel jiou = jiOuBlueServiceImpl.transfor(blue);

        print(two);
        print(four);
        print(eight);
        print(jiou);
    }

    @Override
    public void learn() {
        int limit = 150;
        List<Integer> yuxianblue = ssqService.getBlues(0, limit);
        List<Integer> blue = ssqService.getBlues(150, 300);

        Collections.reverse(blue);
        Collections.reverse(yuxianblue);

        List<String> probabilityList = Lists.newArrayList();
        int[][] lr = new int[limit * 2][4];

        for (int j = 0; j < yuxianblue.size(); j++) {
            int k = j * 2;
            int b = yuxianblue.get(j);
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
                    lr[k][0] = 1;
                    lr[k + 1][0] = 0;
                } else {
                    value += "0";
                    lr[k][0] = 0;
                    lr[k + 1][0] = 1;
                }
            } else {
                double t1 = two.getProbability()[twoRowIndex][twoIndex];
                double t2 = two.getProbability()[twoRowIndex][twoIndex - 1];
                if (t1 >= t2) {
                    value += "1";
                    lr[k][0] = 1;
                    lr[k + 1][0] = 0;
                } else {
                    value += "0";
                    lr[k][0] = 0;
                    lr[k + 1][0] = 1;
                }
            }

            if (fourIndex == 0 || fourIndex == 2) {
                double t1 = four.getProbability()[fourRowIndex][fourIndex];
                double t2 = four.getProbability()[fourRowIndex][fourIndex + 1];
                if (t1 >= t2) {
                    value += "1";
                    lr[k][1] = 1;
                    lr[k + 1][1] = 0;
                } else {
                    value += "0";
                    lr[k][1] = 0;
                    lr[k + 1][1] = 1;
                }
            } else {
                double t1 = four.getProbability()[fourRowIndex][fourIndex];
                double t2 = four.getProbability()[fourRowIndex][fourIndex - 1];
                if (t1 >= t2) {
                    value += "1";
                    lr[k][1] = 1;
                    lr[k + 1][1] = 0;
                } else {
                    value += "0";
                    lr[k][1] = 0;
                    lr[k + 1][1] = 1;
                }
            }

            if (eightIndex == 0 || eightIndex == 2 || eightIndex == 4 || eightIndex == 6) {
                double t1 = eight.getProbability()[eightRowIndex][eightIndex];
                double t2 = eight.getProbability()[eightRowIndex][eightIndex + 1];
                if (t1 >= t2) {
                    value += "1";
                    lr[k][2] = 1;
                    lr[k + 1][2] = 0;

                    lr[k][3] = 1;
                    lr[k + 1][3] = 0;
                } else {
                    value += "0";
                    lr[k][2] = 0;
                    lr[k + 1][2] = 1;

                    lr[k][3] = 1;
                    lr[k + 1][3] = 0;
                }
            } else {
                double t1 = eight.getProbability()[eightRowIndex][eightIndex];
                double t2 = eight.getProbability()[eightRowIndex][eightIndex - 1];
                if (t1 >= t2) {
                    value += "1";
                    lr[k][2] = 1;
                    lr[k + 1][2] = 0;

                    lr[k][3] = 1;
                    lr[k + 1][3] = 0;
                } else {
                    value += "0";
                    lr[k][2] = 0;
                    lr[k + 1][2] = 1;

                    lr[k][3] = 1;
                    lr[k + 1][3] = 0;
                }
            }


            log.info("{},{},{},{},{}", b, (twoRowIndex + 1) + ":" + twoIndex,
                    (fourRowIndex + 1) + ":" + fourIndex,
                    (eightRowIndex + 1) + ":" + eightIndex, value);

            probabilityList.add(value);

            blue.add(b);
        }

        Logistic logistic = new Logistic(3);
        logistic.train(lr);

        log.info("1 1 1 :{}", logistic.classify1(new int[]{1, 1, 1}));
        log.info("1 1 0 :{}", logistic.classify1(new int[]{1, 1, 0}));
        log.info("1 0 1 :{}", logistic.classify1(new int[]{1, 0, 1}));
        log.info("1 0 0 :{}", logistic.classify1(new int[]{1, 0, 0}));
        log.info("0 1 1 :{}", logistic.classify1(new int[]{0, 1, 1}));
        log.info("0 1 0 :{}", logistic.classify1(new int[]{0, 1, 0}));
        log.info("0 0 1 :{}", logistic.classify1(new int[]{0, 0, 1}));
        log.info("0 0 0 :{}", logistic.classify1(new int[]{0, 0, 0}));

        BlueTransModel two = twoBlueServiceImpl.transfor(blue);
        BlueTransModel four = fourBlueServiceImpl.transfor(blue);
        BlueTransModel eight = eightBlueServiceImpl.transfor(blue);
        BlueTransModel six10 = six10BlueServiceImpl.transfor(blue);
        print(two);
        print(four);
        print(eight);
        print(six10);

//        double[][] p = blueProbabilityService.transfor(probabilityList);
//
//        for (int i = 0; i < p.length; i++) {
//            for (int j = 0; j < p.length; j++) {
//                System.out.printf("%.8f\t", p[i][j]);// 输出格式化后的数据
//            }
//            System.out.println();
//        }
//        System.out.println("\n");

    }

    private void print(BlueTransModel model) {
        System.out.println("=============================");
        double[][] probability = model.getProbability();

        System.out.println("last :" + model.getLast());
        for (int i = 0; i < probability.length; i++) {
            for (int j = 0; j < probability.length; j++) {
                System.out.printf("%.8f\t", probability[i][j]);// 输出格式化后的数据
            }
            System.out.println();
        }
        System.out.println("=============================");
    }
}
