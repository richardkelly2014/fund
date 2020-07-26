package com.hmm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiangfei on 2020/7/26.
 */
public class TransProMatrix {

    public static void main(String[] args) {
        // 模拟30年降雨量毫米 mm 从1981-2010年 数据非真实
        double[] rainFall = new double[]{
                620, 680, 749.9, 943.3, 435.6, 734.5, 847.3, 534.6, 934.7, 673.7,
                500, 780, 649.9, 743.3, 835.6, 834.5, 547.3, 634.6, 834.7, 873.7,
                470, 590, 729.1, 823.5, 637.6, 834.8, 469.3, 734.5, 864.7, 872.1
        };
        // levelList 内的值是0、1组合 共有30个值
        List<Integer> levelList = new ArrayList<Integer>();
        // 把30年降雨量分为5个等级 枯水年、偏枯年、平水年、偏丰年、丰水年 对应0 1 2 3 4
        // 对应降水量分别为 <600 <700 <800 <900 >900
        int[] statusValue = new int[]{0, 1, 2, 3, 4};
        // 此数组用于存储每种状态对应出现的次数及其状态值 如 状态0 在所列数据中出现了5次
        int[] statusValueNum = new int[statusValue.length];
        for (int i = 0; i < rainFall.length; i++) {
            if (rainFall[i] < 600) {
                statusValueNum[0]++;
                levelList.add(0);
            } else if (rainFall[i] < 700) {
                statusValueNum[1]++;
                levelList.add(1);
            } else if (rainFall[i] < 800) {
                statusValueNum[2]++;
                levelList.add(2);
            } else if (rainFall[i] < 900) {
                statusValueNum[3]++;
                levelList.add(3);
            } else {
                statusValueNum[4]++;
                levelList.add(4);
            }
        }
        // 每年的降雨量对应的状态值
        System.out.println("每年的降雨量对应的状态值:\n" + levelList + "\n");
        System.out.println("状态0、1、2、3、4分别出现的次数:\n" + Arrays.toString(statusValueNum) + "\n");
        // 获取转移概率矩阵
        Double[][] transProbablityMatrix = statusTransProbablity(statusValueNum, levelList);
        System.out.println("一步转移概论矩阵结果：");
        for (int i = 0; i < transProbablityMatrix.length; i++) {
            for (int j = 0; j < transProbablityMatrix.length; j++) {
                System.out.printf("%.4f\t", transProbablityMatrix[i][j]);// 输出格式化后的数据
            }
            System.out.println();
        }
    }

    /* 一步转移概率矩阵 解释：状态有0 1 2 3 4 ,当 当前状态如0且下一状态为如1,称为一步状态转移 */
    public static Double[][] statusTransProbablity(int[] statusValueNum, List<Integer> levelList) {
        Double[][] transProbablityMatrix = new Double[5][5];
        // 一步转移概率矩阵
        // /**********************************遍历结果是一步状态转移概率矩阵*******************************/
        for (int s = 0; s < statusValueNum.length; s++) {
            // 控制数组的行数变化 s充当了状态值
            int status = 0;
            // 状态的类型取值为0 1 2 3 4 当每一次遍历完之后,status要重新变化
            for (int i = 0; i < statusValueNum.length; i++) {
                int index = 0; // 记录一步转移状态的次数 每一次遍历完毕清0
                for (int j = 0; j < levelList.size() - 1; j++) {
                    // levelList.size()-1 原因是k+1 已经可以达到链尾
                    int k = j;
                    // k值是为了在当前遍历中使用 如果当前状态值和下一次状态值相等遍历整个数据链 每次只查找一次状态的改变
                    // 例如先查找0转移到0，然后是0到1 依次类推
                    while (levelList.get(k).intValue() == s && levelList.get(k + 1).intValue() == status) {
                        index++;// 计数器加1
                        if (k < levelList.size() - 2) {// 减2解释 当k=28时 levelList.get(k+1) 此处会下标越界，已超出表述范围
                            k++;// 当前位置向后移动一位 即为了查询某个状态值连续出现的次数
                        } else {
                            break; // 当不满足当前条件，终止最近的循环体
                        }
                    }
                }
                // System.out.println("Trans[" + s + "][" + i + "] " + index);
                // 计算概率值
                transProbablityMatrix[s][i] = Double.valueOf(index) / Double.valueOf(statusValueNum[s]);
                status++;
            }
        }
        return transProbablityMatrix;
    }

}
