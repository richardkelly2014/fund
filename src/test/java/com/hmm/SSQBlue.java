package com.hmm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiangfei on 2020/7/26.
 */
public class SSQBlue {

    public static void main(String[] args) {

        double[] blues = new double[]{
                7, 3, 2, 2, 6, 1, 3, 16, 10, 8,
                7, 16, 4, 7, 7, 3, 3, 12, 6, 11,
                11, 5, 14, 9, 9, 3, 16, 14, 12, 8,
                7, 2, 14, 2, 11, 3, 3, 4, 9, 10, 16, 12, 9, 1, 9,
                14, 7, 1, 7, 4, 13, 3, 14, 7, 2, 8, 8, 2, 6, 6, 8, 5, 3,
                9, 13, 1, 3, 11, 6, 15, 15, 2, 4, 13, 3, 7, 7, 16, 8, 1,
                2, 4, 15, 8, 15, 1, 2, 12, 14, 3, 7, 14, 8, 4, 5, 14, 1, 8, 7, 8
        };
        List<Integer> levelList = new ArrayList<Integer>();

        int[] statusValue = new int[]{1, 2, 3, 4, 5};
        int[] statusValueNum = new int[statusValue.length];

        for (int i = 0; i < blues.length; i++) {
            if (blues[i] <= 3) {

                statusValueNum[0]++;
                levelList.add(1);

            } else if (blues[i] <= 6) {

                statusValueNum[1]++;
                levelList.add(2);

            } else if (blues[i] <= 9) {

                statusValueNum[2]++;
                levelList.add(3);

            } else if (blues[i] <= 12) {

                statusValueNum[3]++;
                levelList.add(4);

            } else {
                statusValueNum[4]++;
                levelList.add(5);
            }
        }
        // 每年的降雨量对应的状态值
        System.out.println("篮球对应的状态值:" + levelList.size() + "\n" + levelList + "\n");
        System.out.println("分别出现的次数:\n" + Arrays.toString(statusValueNum) + "\n");
        // 获取转移概率矩阵
        double[][] transProbablityMatrix = trans(statusValue.length, statusValueNum, statusValue, levelList);
//        Double[][] transProbablityMatrix = statusTransProbablity(statusValueNum, levelList);
        System.out.println("一步转移概论矩阵结果：");
        for (int i = 0; i < transProbablityMatrix.length; i++) {
            for (int j = 0; j < transProbablityMatrix.length; j++) {
                System.out.printf("%.4f\t", transProbablityMatrix[i][j]);// 输出格式化后的数据
            }
            System.out.println();
        }
    }

    // 状态出现次数，所有集合
    public static double[][] trans(int size, int[] statusValue, int[] baseStatus, List<Integer> level) {
        // 1 2 3 相互转换概率
        double[][] result = new double[size][size];

        for (int s = 0; s < size; s++) {
            int baseState = baseStatus[s];

            for (int i = 0; i < size; i++) {

                int state = baseStatus[i];

                int index = 0; //计数器
                for (int j = 0; j < level.size() - 1; j++) {

                    int k = j;
                    //1-1 1-2 1-3 出现的次数
                    while (level.get(k) == baseState && level.get(k + 1) == state) {
                        index++;
                        if (k < level.size() - 2) {
                            k++;
                        } else {
                            break;
                        }
                    }
                }
                result[s][i] = (double) index / (double) statusValue[s];
                //System.out.println(baseState + "->" + state + ":" + index + "/" + statusValue[s] + "=" + result[s][i]);
            }
        }

        return result;
    }

//    /* 一步转移概率矩阵 解释：状态有0 1 2 3 4 ,当 当前状态如0且下一状态为如1,称为一步状态转移 */
//    public static Double[][] statusTransProbablity(int[] statusValueNum, List<Integer> levelList) {
//        Double[][] transProbablityMatrix = new Double[16][16];
//        // 一步转移概率矩阵
//        // /**********************************遍历结果是一步状态转移概率矩阵*******************************/
//        for (int s = 0; s < statusValueNum.length; s++) {
//            // 控制数组的行数变化 s充当了状态值
//            int status = 0;
//            // 状态的类型取值为0 1 2 3 4 当每一次遍历完之后,status要重新变化
//            for (int i = 0; i < statusValueNum.length; i++) {
//                int index = 0; // 记录一步转移状态的次数 每一次遍历完毕清0
//                for (int j = 0; j < levelList.size() - 1; j++) {
//                    // levelList.size()-1 原因是k+1 已经可以达到链尾
//                    int k = j;
//                    // k值是为了在当前遍历中使用 如果当前状态值和下一次状态值相等遍历整个数据链 每次只查找一次状态的改变
//                    // 例如先查找0转移到0，然后是0到1 依次类推
//                    while (levelList.get(k).intValue() == s && levelList.get(k + 1).intValue() == status) {
//                        index++;// 计数器加1
//                        if (k < levelList.size() - 2) {// 减2解释 当k=28时 levelList.get(k+1) 此处会下标越界，已超出表述范围
//                            k++;// 当前位置向后移动一位 即为了查询某个状态值连续出现的次数
//                        } else {
//                            break; // 当不满足当前条件，终止最近的循环体
//                        }
//                    }
//                }
//                // System.out.println("Trans[" + s + "][" + i + "] " + index);
//                // 计算概率值
//                transProbablityMatrix[s][i] = Double.valueOf(index) / Double.valueOf(statusValueNum[s]);
//                status++;
//            }
//        }
//        return transProbablityMatrix;
//    }
}
