package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueService;
import com.fund.ssq.BlueTransModel;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("six10BlueServiceImpl")
public class Six10BlueServiceImpl extends AbstractBlueService implements BlueService {
    private final static int[] status = {1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16};
    private final static int size = 16;

    @Override
    public BlueTransModel transfor(List<Integer> blues) {

        int[] statusValueNum = new int[size];
        List<Integer> levels = Lists.newArrayList();

        for (Integer value : blues) {
            if (value <= 1) {
                statusValueNum[0]++;
                levels.add(1);
            } else if (value <= 2) {
                statusValueNum[1]++;
                levels.add(2);
            } else if (value <= 3) {
                statusValueNum[2]++;
                levels.add(3);
            } else if (value <= 4) {
                statusValueNum[3]++;
                levels.add(4);
            } else if (value <= 5) {
                statusValueNum[4]++;
                levels.add(5);
            } else if (value <= 6) {
                statusValueNum[5]++;
                levels.add(6);
            } else if (value <= 7) {
                statusValueNum[6]++;
                levels.add(7);
            } else if (value <= 8) {
                statusValueNum[7]++;
                levels.add(8);
            } else if (value <= 9) {
                statusValueNum[8]++;
                levels.add(9);
            } else if (value <= 10) {
                statusValueNum[9]++;
                levels.add(10);
            } else if (value <= 11) {
                statusValueNum[10]++;
                levels.add(11);
            } else if (value <= 12) {
                statusValueNum[11]++;
                levels.add(12);
            } else if (value <= 13) {
                statusValueNum[12]++;
                levels.add(13);
            } else if (value <= 14) {
                statusValueNum[13]++;
                levels.add(14);
            } else if (value <= 15) {
                statusValueNum[14]++;
                levels.add(15);
            } else {
                statusValueNum[15]++;
                levels.add(16);
            }
        }

        double[][] value = trans(size, statusValueNum, status, levels);
        return BlueTransModel.builder()
                .type(16)
                .probability(value)
                .last(levels.get(levels.size() - 1))
                .build();
    }
}
