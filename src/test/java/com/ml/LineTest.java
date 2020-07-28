package com.ml;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class LineTest {
    /**
     * https://www.cnblogs.com/datamining-bio/p/9240378.html
     * <p>
     * 为了计算对于给定数据点的最小方差回线，需要计算SumX,SumY,SumXX,SumXY; (注：SumXX = Sum (X^2))
     * 回归直线方程如下： f(x)=a1x+a0
     * <p>
     * 斜率和截距的计算公式如下：
     * n: 数据点个数
     * a1=(n(SumXY)-SumX*SumY)/(n*SumXX-(SumX)^2)
     * a0=(SumY - SumY * a1)/n
     * (也可表达为a0=averageY-a1*averageX)
     * 画线的原理：两点成一直线，只要能确定两个点即可
     * <p>
     * SSE=sum((Yi-Y)^2) SST=sumYY - (sumY*sumY)/n;
     */

    @Test
    public void test1() {
        List<Integer> blue100 = Lists.newArrayList(
                7, 3, 2, 2, 6, 1, 3, 16, 10, 8,
                7, 16, 4, 7, 7, 3, 3, 12, 6, 11,
                11, 5, 14, 9, 9, 3, 16, 14, 12, 8,
                7, 2, 14, 2, 11, 3, 3, 4, 9, 10, 16, 12, 9, 1, 9,
                14, 7, 1, 7, 4, 13, 3, 14, 7, 2, 8, 8, 2, 6, 6, 8, 5, 3,
                9, 13, 1, 3, 11, 6, 15, 15, 2, 4, 13, 3, 7, 7, 16, 8, 1,
                2, 4, 15, 8, 15, 1, 2, 12, 14, 3, 7, 14, 8, 4, 5, 14, 1, 8, 7, 8, 10
        );

        List<DataPoint> list = Lists.newArrayList();
        for (int i = 0; i < blue100.size(); i++) {
            list.add(new DataPoint(i + 1, blue100.get(i)));
        }
        
        RegressionLine line = new RegressionLine(list);
        printSums(line);
        printLine(line);

        System.out.println(line.getA1() * 12 + line.getA0());

//        System.out.println();
//
//        log.info("{}", LinearRegression.calculate(list));

    }


    private static void printSums(RegressionLine line) {
        System.out.println("\n数据点个数 n = " + line.getDataPointCount());
        System.out.println("\nSum x  = " + line.getSumX());
        System.out.println("Sum y  = " + line.getSumY());
        System.out.println("Sum xx = " + line.getSumXX());
        System.out.println("Sum xy = " + line.getSumXY());
        System.out.println("Sum yy = " + line.getSumYY());
    }

    private static void printLine(RegressionLine line) {
        System.out.println("\n回归线公式:  y = " + line.getA1() + "x + "
                + line.getA0());

        System.out.println("误差：     R^2 = " + line.getR());
    }
}

