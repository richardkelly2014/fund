package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueService;
import com.fund.ssq.BlueTransModel;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("eightBlueServiceImpl")
public class EightBlueServiceImpl extends AbstractBlueService implements BlueService {

    private final static int[] status = {1, 2, 3, 4, 5, 6, 7, 8};
    private final static int size = 8;

    @Override
    public BlueTransModel transfor(List<Integer> blues) {

        int[] statusValueNum = new int[size];
        List<Integer> levels = Lists.newArrayList();

        for (Integer value : blues) {
            if (value <= 2) {
                statusValueNum[0]++;
                levels.add(1);
            } else if (value <= 4) {
                statusValueNum[1]++;
                levels.add(2);
            } else if (value <= 6) {
                statusValueNum[2]++;
                levels.add(3);
            } else if (value <= 8) {
                statusValueNum[3]++;
                levels.add(4);
            } else if (value <= 10) {
                statusValueNum[4]++;
                levels.add(5);
            } else if (value <= 12) {
                statusValueNum[5]++;
                levels.add(6);
            } else if (value <= 14) {
                statusValueNum[6]++;
                levels.add(7);
            } else {
                statusValueNum[7]++;
                levels.add(8);
            }
        }

        double[][] value = trans(size, statusValueNum, status, levels);
        return BlueTransModel.builder()
                .type(8)
                .probability(value)
                .last(levels.get(levels.size() - 1))
                .build();
    }

    @Override
    public int transforIndex(int blue) {
        if (blue <= 2) {
            return 0;
        } else if (blue <= 4) {
            return 1;
        } else if (blue <= 6) {
            return 2;
        } else if (blue <= 8) {
            return 3;
        } else if (blue <= 10) {
            return 4;
        } else if (blue <= 12) {
            return 5;
        } else if (blue <= 14) {
            return 6;
        } else {
            return 7;
        }
    }
}
