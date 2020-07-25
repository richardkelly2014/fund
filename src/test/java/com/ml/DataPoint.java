package com.ml;

import lombok.Data;

@Data
public class DataPoint {

    public float x;
    public float y;

    public DataPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
