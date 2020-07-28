package com.hmm;

import com.fund.ssq.blue.EightBlueServiceImpl;
import com.fund.ssq.blue.FiveBlueServiceImpl;
import com.fund.ssq.blue.Six10BlueServiceImpl;
import com.fund.ssq.blue.ThreeBlueServiceImpl;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SSQBlueTest {


    private ThreeBlueServiceImpl threeBlueService;
    private FiveBlueServiceImpl fiveBlueService;
    private EightBlueServiceImpl eightBlueService;
    private Six10BlueServiceImpl six10BlueService;

    @Before
    public void setup() {
        threeBlueService = new ThreeBlueServiceImpl();
        fiveBlueService = new FiveBlueServiceImpl();
        eightBlueService = new EightBlueServiceImpl();
        six10BlueService = new Six10BlueServiceImpl();
    }

    @Test
    public void test() {

        List<Integer> blue100 = Lists.newArrayList(
                7, 3, 2, 2, 6, 1, 3, 16, 10, 8,
                7, 16, 4, 7, 7, 3, 3, 12, 6, 11,
                11, 5, 14, 9, 9, 3, 16, 14, 12, 8,
                7, 2, 14, 2, 11, 3, 3, 4, 9, 10, 16, 12, 9, 1, 9,
                14, 7, 1, 7, 4, 13, 3, 14, 7, 2, 8, 8, 2, 6, 6, 8, 5, 3,
                9, 13, 1, 3, 11, 6, 15, 15, 2, 4, 13, 3, 7, 7, 16, 8, 1,
                2, 4, 15, 8, 15, 1, 2, 12, 14, 3, 7, 14, 8, 4, 5, 14, 1, 8, 7, 8, 10
        );

        double[][] three = threeBlueService.transfor(blue100);
        System.out.println("3转移概论矩阵结果：");
        System.out.println("1-5\t6-10\t11-16");
        for (int i = 0; i < three.length; i++) {
            for (int j = 0; j < three.length; j++) {
                System.out.printf("%.4f\t", three[i][j]);// 输出格式化后的数据
            }
            System.out.println();
        }

        double[][] five = fiveBlueService.transfor(blue100);
        System.out.println("5转移概论矩阵结果：");
        System.out.println("1-3\t4-6\t7-9\t10-12\t13-16\t");
        for (int i = 0; i < five.length; i++) {
            for (int j = 0; j < five.length; j++) {
                System.out.printf("%.4f\t", five[i][j]);// 输出格式化后的数据
            }
            System.out.println();
        }

        double[][] eight = eightBlueService.transfor(blue100);
        System.out.println("8转移概论矩阵结果：");
        System.out.println("  1-2  \t3-4\t5-6\t7-8\t9-10\t11-12\t13-14\t15-16\t");
        for (int i = 0; i < eight.length; i++) {
            for (int j = 0; j < eight.length; j++) {
                System.out.printf("%.4f\t", eight[i][j]);// 输出格式化后的数据
            }
            System.out.println();
        }


        double[][] six10 = six10BlueService.transfor(blue100);
        System.out.println("16转移概论矩阵结果：");
        System.out.println("  1-2  \t3-4\t5-6\t7-8\t9-10\t11-12\t13-14\t15-16\t");
        for (int i = 0; i < six10.length; i++) {
            for (int j = 0; j < six10.length; j++) {
                System.out.printf("%.4f\t", six10[i][j]);// 输出格式化后的数据
            }
            System.out.println();
        }

    }
}
