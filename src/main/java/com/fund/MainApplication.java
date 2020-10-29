package com.fund;

import com.fund.config.AbstractFxApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by jiangfei on 2020/6/25.
 */
//@SpringBootApplication
public class MainApplication extends AbstractFxApplication {
    public static void main(String[] args) {
        run(MainApplication.class, args, MapView.class);
    }
}
