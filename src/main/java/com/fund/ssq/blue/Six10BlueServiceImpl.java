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
            statusValueNum[value - 1]++;
            levels.add(value);
        }

        double[][] value = trans(size, statusValueNum, status, levels);
        return BlueTransModel.builder()
                .type(16)
                .probability(value)
                .last(levels.get(levels.size() - 1))
                .build();
    }

    @Override
    public int transforIndex(int blue) {
        return blue - 1;
    }
}
