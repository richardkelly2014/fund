package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fiveBlueServiceImpl")
public class FiveBlueServiceImpl extends AbstractBlueService implements BlueService {

    private final static int[] status = {1, 2, 3, 4, 5};
    private final static int size = 5;

    @Override
    public double[][] transfor(List<Integer> blues) {

        int[] statusValueNum = new int[size];
        List<Integer> levels = Lists.newArrayList();

        for (Integer value : blues) {
            if (value <= 3) {
                statusValueNum[0]++;
                levels.add(1);
            } else if (value <= 6) {
                statusValueNum[1]++;
                levels.add(2);
            } else if (value <= 9) {
                statusValueNum[2]++;
                levels.add(3);
            } else if (value <= 12) {
                statusValueNum[3]++;
                levels.add(4);
            } else {
                statusValueNum[4]++;
                levels.add(5);
            }
        }

        return trans(size, statusValueNum, status, levels);
    }
}
