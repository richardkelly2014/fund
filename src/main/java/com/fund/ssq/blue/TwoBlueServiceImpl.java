package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangfei on 2020/7/28.
 */
@Service("twoBlueServiceImpl")
public class TwoBlueServiceImpl extends AbstractBlueService implements BlueService {
    private final static int[] status = {1, 2};
    private final static int size = 2;


    @Override
    public double[][] transfor(List<Integer> blues) {

        int[] statusValueNum = new int[size];
        List<Integer> levels = Lists.newArrayList();

        for (Integer value : blues) {
            if (value <= 8) {
                statusValueNum[0]++;
                levels.add(1);
            } else {
                statusValueNum[1]++;
                levels.add(2);
            }
        }

        return trans(size, statusValueNum, status, levels);
    }
}
