package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("eightBlueServiceImpl")
public class EightBlueServiceImpl extends AbstractBlueService implements BlueService {

    private final static int[] status = {1, 2, 3, 4, 5, 6, 7, 8};
    private final static int size = 8;

    @Override
    public double[][] transfor(List<Integer> blues) {

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

        return trans(size, statusValueNum, status, levels);
    }
}
