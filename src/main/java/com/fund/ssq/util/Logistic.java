package com.fund.ssq.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by jiangfei on 2020/8/1.
 */
@Slf4j
public class Logistic {
    /**
     * the learning rate
     */
    private double rate;

    /**
     * the weight to learn
     */
    private double[] weights;

    /**
     * the number of iterations
     */
    private int ITERATIONS = 1000;

    public Logistic(int n) {
        this.rate = 0.1;
        weights = new double[n];
    }

    private static double sigmoid(double z) {

        return 1.0 / (1.0 + Math.exp(-z));
    }

    public void train(int[][] lr) {
        List<Instance> dataset = new ArrayList<Instance>();
        for (int i = 0; i < lr.length; i++) {

            int[] t_lr = lr[i];

            int[] data = new int[t_lr.length - 1];
            for (int j = 0; j < t_lr.length - 1; j++) {
                data[j] = t_lr[j];
            }
            int label = t_lr[t_lr.length - 1];
            Instance instance = new Instance(label, data);
            dataset.add(instance);
        }

        train(dataset);
    }

    private void train(List<Instance> instances) {
        for (int n = 0; n < ITERATIONS; n++) {
            double lik = 0.0;
            for (int i = 0; i < instances.size(); i++) {
                int[] x = instances.get(i).x;

                double predicted = classify(x); //预测
                int label = instances.get(i).label;

                for (int j = 0; j < weights.length; j++) {
                    weights[j] = weights[j] + rate * (label - predicted) * x[j];
                }
                // not necessary for learning
                lik += label * Math.log(classify(x)) + (1 - label) * Math.log(1 - classify(x));
            }
            System.out.println("iteration: " + n + " " + Arrays.toString(weights) + " mle: " + lik);
        }
        //test(instances);
    }

    private void test(List<Instance> instances) {
        for (int i = 0; i < instances.size(); i++) {
            int[] x = instances.get(i).x;
            double tmp = 0;
            for (int j = 0; j < x.length; j++) {
                tmp += x[j] * weights[j];
            }

            log.info("{} ,{} ,{}", x, instances.get(i).label, tmp);
        }
    }

    public double classify(int[] x) {
        double logit = .0;
        for (int i = 0; i < weights.length; i++) {
            logit += weights[i] * x[i];
        }
        return sigmoid(logit);
    }

    public double classify1(int[] x) {
        double logit = .0;
        for (int i = 0; i < weights.length; i++) {
            logit += weights[i] * x[i];
        }
        return logit;
    }

    public static class Instance {
        public int label;
        public int[] x;

        public Instance(int label, int[] x) {
            this.label = label;
            this.x = x;
        }
    }

}
