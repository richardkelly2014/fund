package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueService;
import com.fund.ssq.BlueTransModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangfei on 2020/8/1.
 */
@Slf4j
@Service("jiOuBlueServiceImpl")
public class JiOuBlueServiceImpl extends AbstractBlueService implements BlueService {
    private final static int[] status = {1, 2};
    private final static int size = 2;

    @Override
    public BlueTransModel transfor(List<Integer> blues) {
        int[] statusValueNum = new int[size];
        List<Integer> levels = Lists.newArrayList();

        for (Integer value : blues) {
            if (value % 2 != 0) {
                statusValueNum[0]++;
                levels.add(1);
            } else {
                statusValueNum[1]++;
                levels.add(2);
            }
        }
        log.info("{}", levels);
        double[][] value = trans(size, statusValueNum, status, levels);
        return BlueTransModel.builder()
                .type(2)
                .probability(value)
                .last(levels.get(levels.size() - 1))
                .build();
    }

    @Override
    public int transforIndex(int blue) {
        return 0;
    }
}
