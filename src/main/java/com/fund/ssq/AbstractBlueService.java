package com.fund.ssq;

import java.util.List;

public abstract class AbstractBlueService {


    protected double[][] trans(int size, int[] statusValue, int[] baseStatus, List<Integer> level) {
        // 1 2 3 相互转换概率
        System.out.println("篮球对应的状态值:" + level.size() + "\n" + level + "\n");
        double[][] result = new double[size][size];

        for (int s = 0; s < size; s++) {
            int baseState = baseStatus[s];

            for (int i = 0; i < size; i++) {

                int state = baseStatus[i];

                int index = 0; //计数器
                for (int j = 0; j < level.size() - 1; j++) {

                    int k = j;
                    if (level.get(k) == baseState && level.get(k + 1) == state) {
                        index++;
                    }
                    //1-1 1-2 1-3 出现的次数
//                    while (level.get(k) == baseState && level.get(k + 1) == state) {
//                        index++;
//                        if (k < level.size() - 2) {
//                            k++;
//                        } else {
//                            break;
//                        }
//                    }

                }
                result[s][i] = (double) index / (double) statusValue[s];
            }
        }

        return result;
    }

}
