package com.ml;

import java.util.ArrayList;
import java.util.List;

public class RegressionLine {
    /**
     * sum of x
     */
    private double sumX;

    /**
     * sum of y
     */
    private double sumY;

    /**
     * sum of x*x
     */
    private double sumXX;

    /**
     * sum of x*y
     */
    private double sumXY;

    /**
     * sum of y*y
     */
    private double sumYY;

    /**
     * sum of yi-y
     */
    private double sumDeltaY;
    /**
     * sum of sumDeltaY^2
     */
    private double sumDeltaY2;

    /**
     * 误差
     */
    private double sse;

    private double sst;

    private double E;

    private String[] xy;

    private ArrayList listX;

    private ArrayList listY;

    private int XMin, XMax, YMin, YMax;

    /**
     * line coefficient a0
     */
    private float a0;

    /**
     * line coefficient a1
     */
    private float a1;

    /**
     * number of data points
     */
    private int pn;

    /**
     * true if coefficients valid
     */
    private boolean coefsValid;

    public RegressionLine() {
        XMax = 0;
        YMax = 0;
        pn = 0;

        xy = new String[2];
        listX = new ArrayList();
        listY = new ArrayList();
    }

    public RegressionLine(List<DataPoint> data) {
        pn = 0;

        xy = new String[2];
        listX = new ArrayList();
        listY = new ArrayList();

        for (int i = 0; i < data.size(); ++i) {
            addDataPoint(data.get(i));
        }
    }

    public int getDataPointCount() {
        return pn;
    }

    public float getA0() {
        validateCoefficients();
        return a0;
    }

    public float getA1() {
        validateCoefficients();
        return a1;
    }

    public double getSumX() {
        return sumX;
    }

    public double getSumY() {
        return sumY;
    }

    public double getSumXX() {
        return sumXX;
    }

    public double getSumXY() {
        return sumXY;
    }

    public double getSumYY() {
        return sumYY;
    }


    public void addDataPoint(DataPoint dataPoint) {
        sumX += dataPoint.x;
        sumY += dataPoint.y;

        sumXX += dataPoint.x * dataPoint.x;

        sumXY += dataPoint.x * dataPoint.y;

        sumYY += dataPoint.y * dataPoint.y;

        if (dataPoint.x > XMax) {
            XMax = (int) dataPoint.x;
        }
        if (dataPoint.y > YMax) {
            YMax = (int) dataPoint.y;
        }

        xy[0] = (int) dataPoint.x + "";
        xy[1] = (int) dataPoint.y + "";

        //if (dataPoint.x != 0 && dataPoint.y != 0) {
        System.out.print(xy[0] + ",");
        System.out.println(xy[1]);

        try {
            listX.add(pn, xy[0]);
            listY.add(pn, xy[1]);
        } catch (Exception e) {
        }
        //}
        ++pn;
        coefsValid = false;
    }

    /**
     * 返回x处值
     *
     * @param x
     * @return
     */
    public float at(int x) {
        if (pn < 2)
            return Float.NaN;
        validateCoefficients();
        return a0 + a1 * x;
    }

    public void reset() {
        pn = 0;
        sumX = sumY = sumXX = sumXY = 0;
        coefsValid = false;
    }

    /**
     * Validate the coefficients. 计算方程系数 y=ax+b 中的a
     */
    private void validateCoefficients() {
        if (coefsValid)
            return;

        if (pn >= 2) {
            float xBar = (float) sumX / pn;
            float yBar = (float) sumY / pn;
            a1 = (float) ((pn * sumXY - sumX * sumY) / (pn * sumXX - sumX * sumX));
            a0 = (float) (yBar - a1 * xBar);
        } else {
            a0 = a1 = Float.NaN;
        }
        coefsValid = true;
    }

    public double getR() {
        for (int i = 0; i < pn - 1; i++) {
            float Yi = (float) Integer.parseInt(listY.get(i).toString());
            float Y = at(Integer.parseInt(listX.get(i).toString()));
            float deltaY = Yi - Y;
            float deltaY2 = deltaY * deltaY;

            sumDeltaY2 += deltaY2;
        }
        sst = sumYY - (sumY * sumY) / pn;
        E = 1 - sumDeltaY2 / sst;
        return E;
    }
}
