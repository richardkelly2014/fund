package com.fund.config;

import javafx.application.Preloader;
import lombok.ToString;

/**
 * 与加载 消息
 * Created by jiangfei on 2020/6/21.
 */
@ToString
public class MessagePreloaderNotification implements Preloader.PreloaderNotification {
    private double progress;
    private String detail;

    public MessagePreloaderNotification(double p, String d) {
        this.progress = p;
        this.detail = d;
    }

    public double getProgress() {
        return progress;
    }

    public String getDetail() {
        return detail;
    }
}