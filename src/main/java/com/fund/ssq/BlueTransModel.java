package com.fund.ssq;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlueTransModel {
    private int type; // 8 , 4 ,2
    private int last;
    private double[][] probability;
}
