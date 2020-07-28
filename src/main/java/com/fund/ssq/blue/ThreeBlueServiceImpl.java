package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("threeBlueServiceImpl")
public class ThreeBlueServiceImpl extends AbstractBlueService implements BlueService {

    private final static int[] status = {1, 2, 3};
    private final static int size = 3;


    @Override
    public double[][] transfor(List<Integer> blues) {

        int[] statusValueNum = new int[size];
        List<Integer> levels = Lists.newArrayList();

        for (Integer value : blues) {
            if (value <= 5) {
                statusValueNum[0]++;
                levels.add(1);
            } else if (value <= 10) {
                statusValueNum[1]++;
                levels.add(2);
            } else {
                statusValueNum[2]++;
                levels.add(3);
            }
        }

        return trans(size, statusValueNum, status, levels);
    }
}
