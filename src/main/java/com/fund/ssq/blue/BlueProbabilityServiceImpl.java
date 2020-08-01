package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueProbabilityService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlueProbabilityServiceImpl extends AbstractBlueService implements BlueProbabilityService {

    private final static int[] status = {1, 2, 3, 4, 5, 6, 7, 8};
    private final static int size = 8;

    @Override
    public double[][] transfor(List<String> probabilitys) {

        int[] statusValueNum = new int[size];
        List<Integer> levels = Lists.newArrayList();

        for (String value : probabilitys) {

            if (StringUtils.equalsIgnoreCase(value, "111")) {
                statusValueNum[0]++;
                levels.add(1);
            } else if (StringUtils.equalsIgnoreCase(value, "110")) {
                statusValueNum[1]++;
                levels.add(2);
            } else if (StringUtils.equalsIgnoreCase(value, "101")) {
                statusValueNum[2]++;
                levels.add(3);
            } else if (StringUtils.equalsIgnoreCase(value, "100")) {
                statusValueNum[3]++;
                levels.add(4);
            } else if (StringUtils.equalsIgnoreCase(value, "011")) {
                statusValueNum[4]++;
                levels.add(5);
            } else if (StringUtils.equalsIgnoreCase(value, "010")) {
                statusValueNum[5]++;
                levels.add(6);
            } else if (StringUtils.equalsIgnoreCase(value, "001")) {
                statusValueNum[6]++;
                levels.add(7);
            } else {
                statusValueNum[7]++;
                levels.add(8);
            }
        }
        log.info("{},{}", levels.size(), levels);
        return trans(size, statusValueNum, status, levels);
    }
}
