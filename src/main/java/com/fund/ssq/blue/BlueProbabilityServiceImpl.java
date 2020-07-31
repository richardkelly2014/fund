package com.fund.ssq.blue;

import com.fund.ssq.AbstractBlueService;
import com.fund.ssq.BlueProbabilityService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
            }
            else if(StringUtils.equalsIgnoreCase(value, "110")){
                statusValueNum[1]++;
                levels.add(2);
            }

        }


        return new double[0][];
    }
}
